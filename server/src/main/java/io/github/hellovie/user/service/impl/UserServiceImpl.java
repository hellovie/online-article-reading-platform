package io.github.hellovie.user.service.impl;

import io.github.hellovie.exception.business.DatabaseFieldConflictException;
import io.github.hellovie.exception.business.DatabaseFieldNotFoundException;
import io.github.hellovie.exception.business.DatabaseFieldVerifyException;
import io.github.hellovie.exception.business.ForbiddenException;
import io.github.hellovie.security.CustomUser;
import io.github.hellovie.security.util.TokenUtil;
import io.github.hellovie.user.domain.dto.UserDTO;
import io.github.hellovie.user.domain.entity.Role;
import io.github.hellovie.user.domain.entity.User;
import io.github.hellovie.user.domain.request.LoginRequest;
import io.github.hellovie.user.domain.request.RegisterRequest;
import io.github.hellovie.user.domain.request.UserStatusRequest;
import io.github.hellovie.user.mapper.UserMapper;
import io.github.hellovie.user.repository.UserRepository;
import io.github.hellovie.user.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

import static io.github.hellovie.user.domain.enums.RolesConstant.ROLE_SUPER_ADMIN_KEY;
import static io.github.hellovie.user.domain.enums.RolesConstant.ROLE_USER_ID;
import static io.github.hellovie.user.domain.enums.UserExceptionType.*;
import static io.github.hellovie.user.domain.enums.UserStatus.*;

/**
 * 用户服务实现类
 *
 * @author hellovie
 * @Email hellovie@foxmail.com
 * @createTime 2023/4/20 19:57
 */
@Service("userServiceImpl")
@Log4j2
public class UserServiceImpl implements UserService, UserDetailsService {
    @Resource(name = "userRepository")
    private UserRepository userRepository;
    @Resource
    private UserMapper userMapper;
    @Resource(name = "passwordEncoder")
    private PasswordEncoder passwordEncoder;

    /**
     * 获取当前的访问用户
     *
     * @return 用户DTO
     */
    @Override
    public UserDTO getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userMapper.toDto(checkUserByUsername(authentication.getName()));
    }

    /**
     * 验证请求登录的用户信息
     *
     * @param username 用户名
     * @return CustomUser
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = checkUserByUsername(username);
        List<Role> roles = user.getRoles();
        List<GrantedAuthority> authorities = new ArrayList<>(roles.size());
        roles.forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getRoleKey())));

        CustomUser customUser = new CustomUser(user.getUsername(), user.getPassword(), authorities);
        customUser.setLocked(user.getLocked());
        customUser.setEnabled(user.getEnabled());
        return customUser;
    }

    /**
     * 登录账号
     *
     * @param request 用户登录所需信息
     * @param ip      访问的IP地址
     * @return (" user " : 用户信息), (" token " : token令牌)
     */
    @Override
    public Map<String, Object> login(LoginRequest request, String ip) {
        String username = request.getUsername();
        String password = request.getPassword();
        // 验证用户是否存在
        User user = checkUserByUsername(username);
        // 校验密码
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new DatabaseFieldVerifyException(LOGIN_FAILED);
        }
        // 记录最后登录时间和IP
        user.setLastLoginTime(new Date());
        user.setLastLoginIp(ip);

        User saveUser = userRepository.save(user);

        HashMap<String, Object> map = new HashMap<>(2);
        map.put("user", userMapper.toDto(saveUser));
        map.put("token", TokenUtil.createToken(saveUser.getUsername()));
        return map;
    }

    /**
     * 注册账号(普通用户)
     *
     * @param request 注册用户所需信息
     * @param ip      访问的IP地址
     * @return (" user " : 用户信息), (" token " : token令牌)
     */
    @Override
    public Map<String, Object> register(RegisterRequest request, String ip) {
        String username = request.getUsername();
        // 用户存在抛出异常
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isPresent()) {
            throw new DatabaseFieldConflictException(USER_EXIST);
        }

        // 密文存储
        String password = passwordEncoder.encode(request.getPassword());

        // 注册用户
        User user = new User();
        user.setNickname(username);
        user.setUsername(username);
        user.setPassword(password);
        // 设置最后登录IP和最后登录时间
        user.setLastLoginTime(new Date());
        user.setLastLoginIp(ip);
        // 绑定普通用户角色身份
        ArrayList<Role> roles = new ArrayList<>();
        Role role = new Role();
        role.setId(ROLE_USER_ID);
        roles.add(role);
        user.setRoles(roles);

        User saveUser = userRepository.save(user);

        HashMap<String, Object> map = new HashMap<>(2);
        map.put("user", userMapper.toDto(saveUser));
        map.put("token", TokenUtil.createToken(saveUser.getUsername()));
        return map;
    }

    /**
     * 设置用户状态(启用/禁用，锁定/解锁)
     * 自己无法更改自己的状态。
     *
     * @param request 要修改的用户ID和设置的状态
     */
    @Override
    public void changeUserStatus(UserStatusRequest request) {
        UserDTO currentUser = getCurrentUser();
        // 不允许用户修改自己的状态
        if (request.getUserId() == null || currentUser.getId() == null ||
            request.getUserId().equals(currentUser.getId())) {
            throw new ForbiddenException(NO_PERMISSION);
        }

        Set<String> lowStatus = new HashSet<>(2);
        lowStatus.add(LOCK.name());
        lowStatus.add(UNLOCK.name());
        Set<String> highStatus = new HashSet<>(2);
        highStatus.add(ENABLE.name());
        highStatus.add(DISABLE.name());

        String status = request.getStatus();
        // 查询用户是否存在，存在则开始设置状态
        User changeUser = checkUserById(request.getUserId());

        // 低权限(锁定和解锁)，直接进行设置
        if (lowStatus.contains(status)) {
            changeUser.setLocked(LOCK.name().equals(status));
            userRepository.save(changeUser);
            return;
        }

        // 高权限(禁用和启用)
        // 当前请求的用户是否为超级管理员，只有超级管理员才能启用或禁用用户。
        List<String> rolesKey = new ArrayList<>();
        currentUser.getRoles().forEach(role -> {
            rolesKey.add(role.getRoleKey());
        });
        // 属于超级管理员同时是执行启用或禁用用户，才能执行操作。
        boolean isPermission = rolesKey.contains(ROLE_SUPER_ADMIN_KEY) && highStatus.contains(status);
        if (isPermission) {
            changeUser.setEnabled(ENABLE.name().equals(status));
            userRepository.save(changeUser);
            return;
        } else {
            throw new ForbiddenException(NO_PERMISSION);
        }
    }

    /**
     * 判断用户是否存在，不存在则抛出异常
     *
     * @param username 用户名
     * @return 用户信息
     * @throw DatabaseFieldNotFoundException 用户不存在
     */
    private User checkUserByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        if (!user.isPresent()) {
            throw new DatabaseFieldNotFoundException(USER_NOT_FOUND);
        }
        return user.get();
    }

    /**
     * 判断用户是否存在，不存在则抛出异常
     *
     * @param id ID
     * @return 用户信息
     * @throw DatabaseFieldNotFoundException 用户不存在
     */
    private User checkUserById(String id) {
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()) {
            throw new DatabaseFieldNotFoundException(USER_NOT_FOUND);
        }
        return user.get();
    }
}
