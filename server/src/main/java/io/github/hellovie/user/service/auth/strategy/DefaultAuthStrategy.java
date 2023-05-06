package io.github.hellovie.user.service.auth.strategy;

import io.github.hellovie.user.domain.entity.User;
import io.github.hellovie.user.domain.request.LoginRequest;
import io.github.hellovie.user.domain.request.RegisterRequest;
import io.github.hellovie.user.service.auth.BaseAuthStrategy;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 用户账号注册或登录的认证策略的默认实现 (username + password). <br>
 *
 * @author hellovie <br>
 * @version 1.0.0 2023/5/6 <br>
 * @Email hellovie@foxmail.com <br>
 * @since JDK 1.8
 */
@Component("DEFAULT_AUTH_STRATEGY")
public class DefaultAuthStrategy extends BaseAuthStrategy {
    /**
     * 登录账号.
     *
     * @param request 用户登录所需信息.
     * @param ip      访问的 IP 地址.
     * @return user (用户信息), token (token 令牌)
     */
    @Override
    public Map<String, Object> loginStrategy(Object request, String ip) {
        LoginRequest loginRequest = (LoginRequest) request;
        return baseLogin(loginRequest.getUsername(), loginRequest.getPassword(), ip);
    }

    /**
     * 注册账号 (普通用户).
     *
     * @param request 注册用户所需信息.
     * @param ip      访问的 IP 地址.
     * @return user (用户信息), token (token 令牌)
     */
    @Override
    public Map<String, Object> registerStrategy(Object request, String ip) {
        RegisterRequest registerRequest = (RegisterRequest) request;
        // 只有用户使用用户名注册的时候才需要验证用户名的合法性, 避免和其他第三方生成的用户名冲突.
        checkUsernameRegisterByUserSelf(registerRequest.getUsername());
        User user = new User();
        user.setUsername(registerRequest.getUsername())
                .setNickname(registerRequest.getUsername())
                .setPassword(registerRequest.getPassword());
        return baseRegister(user, ip);
    }
}
