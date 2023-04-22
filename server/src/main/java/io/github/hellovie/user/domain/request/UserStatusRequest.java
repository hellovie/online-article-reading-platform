package io.github.hellovie.user.domain.request;

import io.github.hellovie.user.domain.enums.UserStatus;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 设置用户状态的请求(启用/禁用，锁定/解锁)
 *
 * @author hellovie
 * @Email hellovie@foxmail.com
 * @createTime 2023/4/22 22:38
 */
@Data
public class UserStatusRequest {
    /** 要修改的用户ID */
    @NotBlank(message = "用户ID不能为空！")
    private String userId;

    /** 要设置成的状态 */
    private UserStatus status;
}
