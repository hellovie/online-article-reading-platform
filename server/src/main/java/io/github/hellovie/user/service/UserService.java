package io.github.hellovie.user.service;

import io.github.hellovie.user.domain.dto.UserDTO;
import io.github.hellovie.user.domain.request.LoginRequest;
import io.github.hellovie.user.domain.request.RegisterRequest;
import io.github.hellovie.user.domain.request.UserStatusRequest;

import java.util.Map;

/**
 * 用户服务接口. <br>
 *
 * @author hellovie <br>
 * @version 1.0.0 2023/4/20 <br>
 * @Email hellovie@foxmail.com <br>
 * @since JDK 1.8
 */
public interface UserService {
    /**
     * 获取当前的访问用户.
     *
     * @return 用户 DTO.
     */
    UserDTO getCurrentUser();

    /**
     * 登录账号.
     *
     * @param request 用户登录所需信息.
     * @param ip      访问的 IP 地址.
     * @return (" user " : 用户信息), ("token": token 令牌)
     */
    Map<String, Object> login(LoginRequest request, String ip);

    /**
     * 注册账号 (普通用户).
     *
     * @param request 注册用户所需信息.
     * @param ip      访问的 IP 地址.
     * @return (" user " : 用户信息), ("token": token 令牌)
     */
    Map<String, Object> register(RegisterRequest request, String ip);

    /**
     * 设置用户状态 (启用/禁用, 锁定/解锁).
     *
     * @param request 要修改的用户 ID 和设置的状态.
     */
    void changeUserStatus(UserStatusRequest request);
}
