package io.github.hellovie.user.service;

import io.github.hellovie.user.service.auth.AuthStrategyType;

import java.util.Map;

/**
 * 认证服务接口 (用户账号注册或登录的策略上下文). <br>
 *
 * @author hellovie <br>
 * @version 1.0.0 2023/5/6 <br>
 * @Email hellovie@foxmail.com <br>
 * @since JDK 1.8
 */
public interface AuthService {
    /**
     * 登录账号.
     *
     * @param strategyType 运用的策略类型.
     * @param request      用户登录所需信息.
     * @param ip           访问的 IP 地址.
     * @return user (用户信息), token (token 令牌)
     */
    Map<String, Object> login(AuthStrategyType strategyType, Object request, String ip);

    /**
     * 注册账号 (普通用户).
     *
     * @param strategyType 运用的策略类型.
     * @param request      注册用户所需信息.
     * @param ip           访问的 IP 地址.
     * @return user (用户信息), token (token 令牌)
     */
    Map<String, Object> register(AuthStrategyType strategyType, Object request, String ip);
}
