package io.github.hellovie.user.domain.request;

import io.github.hellovie.user.domain.enums.UserStatus;
import io.github.hellovie.core.validation.EnumValid;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 设置用户状态的请求(启用/禁用，锁定/解锁)
 *
 * @author hellovie
 * @Email hellovie@foxmail.com
 * @createTime 2023/4/22 22:38
 */
@ApiModel("用户状态更改请求")
@Data
public class UserStatusRequest {
    @ApiModelProperty(value = "用户ID", required = true, example = "2OmvNk1qLJbO7Yo1kLVz2rJX8Rn")
    /** 要修改的用户ID */
    @NotBlank(message = "用户ID不能为空！")
    private String userId;

    /** 要设置成的状态 */
    @ApiModelProperty(value = "用户状态", required = true, example = "有效值只包含“LOCK”、“UNLOCK”、“ENABLE”、“DISABLE”。")
    @EnumValid(enumClass = UserStatus.class, message = "无效的用户状态！")
    private String status;
}
