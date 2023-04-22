package io.github.hellovie.user.service;

import io.github.hellovie.user.domain.dto.UserDTO;
import io.github.hellovie.user.domain.enums.UserStatus;
import io.github.hellovie.user.domain.request.LoginRequest;
import io.github.hellovie.user.domain.request.RegisterRequest;
import io.github.hellovie.user.domain.request.UserStatusRequest;

import java.util.Map;

/**
 * 用户服务接口
 *
 * @author hellovie
 * @Email hellovie@foxmail.com
 * @createTime 2023/4/20 19:56
 */
public interface UserService {
    /**
     * 获取当前的访问用户
     *
     * @return 用户DTO
     */
    UserDTO getCurrentUser();

    /**
     * 登录账号
     *
     * @param request 用户登录所需信息
     * @param ip 访问的IP地址
     * @return ("user": 用户信息),("token" :token令牌)
     */
    Map<String, Object> login(LoginRequest request, String ip);

    /**
     * 注册账号(普通用户)
     *
     * @param request 注册用户所需信息
     * @param ip 访问的IP地址
     * @return ("user": 用户信息),("token" :token令牌)
     */
    Map<String, Object> register(RegisterRequest request, String ip);

    /**
     * 设置用户状态(启用/禁用，锁定/解锁)
     *
     * @param request 要修改的用户ID和设置的状态
     */
    void changeUserStatus(UserStatusRequest request);
}
