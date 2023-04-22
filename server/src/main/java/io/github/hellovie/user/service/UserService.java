package io.github.hellovie.user.service;

import io.github.hellovie.user.domain.dto.LoginDTO;
import io.github.hellovie.user.domain.request.LoginRequest;
import io.github.hellovie.user.domain.request.RegisterRequest;

/**
 * 用户服务接口
 *
 * @author hellovie
 * @Email hellovie@foxmail.com
 * @createTime 2023/4/20 19:56
 */
public interface UserService {
    /**
     * 登录账号
     *
     * @param request 用户登录所需信息
     * @param ip 访问的IP地址
     * @return 包含少量用户信息和token令牌的DTO
     */
    LoginDTO login(LoginRequest request, String ip);

    /**
     * 注册账号(普通用户)
     *
     * @param request 注册用户所需信息
     * @param ip 访问的IP地址
     * @return 包含少量用户信息和token令牌的DTO
     */
    LoginDTO register(RegisterRequest request, String ip);
}
