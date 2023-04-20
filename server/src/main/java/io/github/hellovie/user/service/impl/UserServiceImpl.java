package io.github.hellovie.user.service.impl;

import io.github.hellovie.exception.business.DatabaseFieldConflictException;
import io.github.hellovie.exception.business.DatabaseFieldNotFoundException;
import io.github.hellovie.exception.business.DatabaseFieldVerifyException;
import io.github.hellovie.security.CustomUser;
import io.github.hellovie.security.util.TokenUtil;
import io.github.hellovie.user.domain.enums.UserExceptionType;
import io.github.hellovie.user.repository.UserRepository;
import io.github.hellovie.user.domain.dto.LoginDTO;
import io.github.hellovie.user.domain.dto.UserDTO;
import io.github.hellovie.user.domain.entity.Role;
import io.github.hellovie.user.domain.entity.User;
import io.github.hellovie.user.domain.request.LoginRequest;
import io.github.hellovie.user.domain.request.RegisterRequest;
import io.github.hellovie.user.mapper.UserMapper;
import io.github.hellovie.user.service.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * 用户服务实现类
 *
 * @author hellovie
 * @Email hellovie@foxmail.com
 * @createTime 2023/4/20 19:57
 */
@Service("userServiceImpl")
public class UserServiceImpl implements UserService, UserDetailsService {
    @Resource(name = "userRepository")
    private UserRepository userRepository;
    @Resource
    private UserMapper userMapper;
    @Resource(name = "passwordEncoder")
    private PasswordEncoder passwordEncoder;

    /**
     * 验证请求登录的用户信息
     *
     * @param username 用户名
     * @return CustomUser
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.getByUsername(username);
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
     * @throw DatabaseFieldVerifyException 账号或密码错误
     * @return 包含少量用户信息和token令牌的DTO
     */
    @Override
    public LoginDTO login(LoginRequest request) {
        String username = request.getUsername();
        String password = request.getPassword();
        // 验证用户是否存在
        User user = checkUser(username);
        // 校验密码
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new DatabaseFieldVerifyException(UserExceptionType.LOGIN_FAILED);
        }
        LoginDTO loginDTO = userMapper.toDto(user);
        loginDTO.setToken(TokenUtil.createToken(user.getUsername()));
        return loginDTO;
    }

    /**
     * 注册账号
     *
     * @param request 注册用户所需信息
     * @throw DatabaseFieldConflictException 用户已存在
     * @return 用户信息
     */
    @Override
    public UserDTO register(RegisterRequest request) {
        String username = request.getUsername();
        // 用户存在抛出异常
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isPresent()) {
            throw new DatabaseFieldConflictException(UserExceptionType.USER_EXIST);
        }

        // 密文存储
        String password = passwordEncoder.encode(request.getPassword());

        // 注册用户
        User user = new User();
        user.setNickname(username);
        user.setUsername(username);
        user.setPassword(password);
        Date now = new Date();
        user.setGmtCreate(now);
        user.setGmtModified(now);
        userRepository.save(user);
        return userMapper.toEntity(user);
    }

    /**
     * 判断用户是否存在，不存在则抛出异常
     *
     * @param username 用户名
     * @throw DatabaseFieldNotFoundException 用户不存在
     * @return 用户信息
     */
    private User checkUser(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        if (!user.isPresent()) {
            throw new DatabaseFieldNotFoundException(UserExceptionType.USER_NOT_FOUND);
        }
        return user.get();
    }
}
