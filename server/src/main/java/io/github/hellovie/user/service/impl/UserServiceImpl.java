package io.github.hellovie.user.service.impl;

import io.github.hellovie.exception.business.DatabaseFieldNotFoundException;
import io.github.hellovie.exception.business.ForbiddenException;
import io.github.hellovie.security.CustomUser;
import io.github.hellovie.user.domain.dto.UserDTO;
import io.github.hellovie.user.domain.entity.Role;
import io.github.hellovie.user.domain.entity.User;
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
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

import static io.github.hellovie.user.domain.enums.RolesConstant.ROLE_SUPER_ADMIN_KEY;
import static io.github.hellovie.user.domain.enums.UserExceptionType.NO_PERMISSION;
import static io.github.hellovie.user.domain.enums.UserExceptionType.USER_NOT_FOUND;
import static io.github.hellovie.user.domain.enums.UserStatus.*;

/**
 * 用户服务实现类. <br>
 *
 * @author hellovie <br>
 * @version 1.0.0 2023/4/20 <br>
 * @Email hellovie@foxmail.com <br>
 * @since JDK 1.8
 */
@Service("userServiceImpl")
@Log4j2
public class UserServiceImpl implements UserService, UserDetailsService {
    @Resource(name = "userRepository")
    private UserRepository userRepository;
    @Resource
    private UserMapper userMapper;

    /**
     * 获取当前的访问用户.
     *
     * @return 用户 DTO.
     */
    @Override
    public UserDTO getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userMapper.toDTO(checkUserByUsername(authentication.getName()));
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
        customUser.setLocked(user.getLocked()).setEnabled(user.getEnabled());
        return customUser;
    }

    /**
     * 设置用户状态, 自己无法更改自己的状态. (启用/禁用, 锁定/解锁)
     *
     * @param request 要修改的用户 ID 和设置的状态.
     */
    @Override
    public void changeUserStatus(UserStatusRequest request) {
        UserDTO currentUser = getCurrentUser();
        // 不允许用户修改自己的状态.
        if (request.getUserId() == null || currentUser.getId() == null || request.getUserId().equals(currentUser.getId())) {
            throw new ForbiddenException(NO_PERMISSION);
        }

        Set<String> lowStatus = new HashSet<>(2);
        lowStatus.add(LOCK.name());
        lowStatus.add(UNLOCK.name());
        Set<String> highStatus = new HashSet<>(2);
        highStatus.add(ENABLE.name());
        highStatus.add(DISABLE.name());

        String status = request.getStatus();
        // 查询用户是否存在, 存在则开始设置状态.
        User changeUser = checkUserById(request.getUserId());

        // 低权限 (锁定和解锁), 直接进行设置.
        if (lowStatus.contains(status)) {
            changeUser.setLocked(LOCK.name().equals(status));
            userRepository.save(changeUser);
            return;
        }

        // 高权限 (禁用和启用)
        // 当前请求的用户是否为超级管理员, 只有超级管理员才能启用或禁用用户.
        List<String> rolesKey = new ArrayList<>();
        currentUser.getRoles().forEach(role -> {
            rolesKey.add(role.getRoleKey());
        });
        // 属于超级管理员同时是执行启用或禁用用户, 才能执行操作.
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
     * 获取用户账号信息 (仅用户个人能够获取).
     *
     * @return 用户个人账号信息.
     */
    @Override
    public UserDTO getUserAccountInfoByToken() {
        return getCurrentUser();
    }

    /**
     * 判断用户是否存在, 不存在则抛出异常.
     *
     * @param username 用户名.
     * @return 用户信息.
     * @throw DatabaseFieldNotFoundException 用户不存在.
     */
    private User checkUserByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        if (!user.isPresent()) {
            throw new DatabaseFieldNotFoundException(USER_NOT_FOUND);
        }
        return user.get();
    }

    /**
     * 判断用户是否存在, 不存在则抛出异常.
     *
     * @param id ID
     * @return 用户信息.
     * @throw DatabaseFieldNotFoundException 用户不存在.
     */
    private User checkUserById(String id) {
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()) {
            throw new DatabaseFieldNotFoundException(USER_NOT_FOUND);
        }
        return user.get();
    }
}
