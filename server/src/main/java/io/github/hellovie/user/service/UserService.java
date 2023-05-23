package io.github.hellovie.user.service;

import io.github.hellovie.user.domain.dto.UserDTO;
import io.github.hellovie.user.domain.request.UserStatusRequest;

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
     * 设置用户状态 (启用/禁用, 锁定/解锁).
     *
     * @param request 要修改的用户 ID 和设置的状态.
     */
    void changeUserStatus(UserStatusRequest request);

    /**
     * 获取用户账号信息 (仅用户个人能够获取).
     *
     * @return 用户个人账号信息.
     */
    UserDTO getUserAccountInfoByToken();

    /**
     * 设置用户头像.
     *
     * @param userId 用户 ID.
     * @param fileId 头像文件 ID.
     */
    void setAvatar(String userId, String fileId);
}
