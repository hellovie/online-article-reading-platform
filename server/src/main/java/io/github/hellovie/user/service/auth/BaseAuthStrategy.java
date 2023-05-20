package io.github.hellovie.user.service.auth;

import io.github.hellovie.exception.business.DatabaseFieldConflictException;
import io.github.hellovie.exception.business.DatabaseFieldNotFoundException;
import io.github.hellovie.exception.business.DatabaseFieldVerifyException;
import io.github.hellovie.file.domain.entity.File;
import io.github.hellovie.security.util.TokenUtil;
import io.github.hellovie.user.domain.entity.Role;
import io.github.hellovie.user.domain.entity.User;
import io.github.hellovie.user.mapper.UserMapper;
import io.github.hellovie.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;

import static io.github.hellovie.user.domain.enums.DefaultProperties.DEFAULT_AVATAR;
import static io.github.hellovie.user.domain.enums.DefaultProperties.DEFAULT_ROLE;
import static io.github.hellovie.user.domain.enums.UserExceptionType.*;

/**
 * 用户账号注册或登录的认证策略基类. <br>
 *
 * @author hellovie <br>
 * @version 1.0.0 2023/5/6 <br>
 * @Email hellovie@foxmail.com <br>
 * @since JDK 1.8
 */
public abstract class BaseAuthStrategy {
    @Resource(name = "userRepository")
    private UserRepository userRepository;
    @Resource
    private UserMapper userMapper;
    @Resource(name = "passwordEncoder")
    private PasswordEncoder passwordEncoder;
    @PersistenceContext
    private EntityManager entityManager;

    /**
     * 登录账号.
     *
     * @param request 用户登录所需信息.
     * @param ip      访问的 IP 地址.
     * @return user (用户信息), token (token 令牌)
     */
    public abstract Map<String, Object> loginStrategy(Object request, String ip);

    /**
     * 注册账号 (普通用户).
     *
     * @param request 注册用户所需信息.
     * @param ip      访问的 IP 地址.
     * @return user (用户信息), token (token 令牌)
     */
    public abstract Map<String, Object> registerStrategy(Object request, String ip);

    /**
     * 登录账号 (默认登录方式, 利用用户名和密码).
     * <P>只负责校验用户是否存在, 校验密码, 记录最后登录 IP 和最后登录时间.</P>
     *
     * @param username 用户登录用户名.
     * @param password 用户登录密码.
     * @param ip       访问的 IP 地址.
     * @return user (用户信息), token (token 令牌)
     */
    protected Map<String, Object> baseLogin(String username, String password, String ip) {
        // 验证用户是否存在
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (!userOptional.isPresent()) {
            throw new DatabaseFieldNotFoundException(USER_NOT_FOUND);
        }
        User user = userOptional.get();

        // 校验密码
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new DatabaseFieldVerifyException(LOGIN_FAILED);
        }
        // 记录最后登录时间和IP
        user.setLastLoginTime(new Date()).setLastLoginIp(ip);

        User saveUser = userRepository.save(user);

        HashMap<String, Object> map = new HashMap<>(2);
        map.put("user", userMapper.toDTO(saveUser));
        map.put("token", TokenUtil.createToken(saveUser.getUsername()));
        return map;
    }

    /**
     * 注册账号 (普通用户) (默认注册方式, 最低要求是包含 username, nickname 和 password).
     * <P>只负责校验用户是否存在, 加密密码, 记录最后登录 IP 和最后登录时间.</P>
     *
     * @param user 用户注册信息.
     * @param ip   访问的 IP 地址.
     * @return user (用户信息), token (token 令牌)
     */
    protected Map<String, Object> baseRegister(User user, String ip) {
        // 用户存在抛出异常
        Optional<User> userOptional = userRepository.findByUsername(user.getUsername());
        if (userOptional.isPresent()) {
            throw new DatabaseFieldConflictException(USER_EXIST);
        }

        // 密文存储
        String encodePassword = passwordEncoder.encode(user.getPassword());

        // 设置加密后的密码、最后登录IP、最后登录时间。
        user.setPassword(encodePassword).setLastLoginTime(new Date()).setLastLoginIp(ip);
        // 设置默认头像
        File file = new File();
        file.setId(DEFAULT_AVATAR);
        user.setAvatar(file);
        // 绑定普通用户角色身份
        ArrayList<Role> roles = new ArrayList<>();
        Role role = new Role();
        role.setId(DEFAULT_ROLE);
        roles.add(role);
        user.setRoles(roles);

        userRepository.save(user);
        // 刷新一级缓存, 获取最新数据. 否则 JPA 只会从缓存拿数据, 获取不到最新的数据.
        entityManager.clear();
        // 再取一次值.
        User newUser = userRepository.findByUsername(user.getUsername()).get();

        HashMap<String, Object> map = new HashMap<>(2);
        map.put("user", userMapper.toDTO(newUser));
        map.put("token", TokenUtil.createToken(newUser.getUsername()));
        return map;
    }

    /**
     * 校验用户使用用户名创建账号时的用户名规范.
     * <p>用户自定义的用户名不能含有第三方注册生成的用户名的前缀, 以确保用户名的唯一性.</p>
     *
     * @param username 用户自定义的用户名
     */
    protected void checkUsernameRegisterByUserSelf(String username) {
        int usernameMaxLen = 16;
        if (username.length() == usernameMaxLen) {
            for (UsernamePrefixType value : UsernamePrefixType.values()) {
                if (username.startsWith(value.name())) {
                    throw new DatabaseFieldConflictException(USER_EXIST);
                }
            }
        }
    }
}
