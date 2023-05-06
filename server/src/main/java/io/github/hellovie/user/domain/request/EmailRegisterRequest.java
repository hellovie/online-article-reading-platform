package io.github.hellovie.user.domain.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * 封装用户邮箱注册所需数据的 Request Object. <br>
 *
 * @author hellovie <br>
 * @version 1.0.0 2023/5/6 <br>
 * @Email hellovie@foxmail.com <br>
 * @since JDK 1.8
 */
@ApiModel("用户邮箱注册请求")
@Data
public class EmailRegisterRequest {
    /** 邮箱地址 */
    @ApiModelProperty(value = "邮箱地址", required = true)
    @NotBlank(message = "邮箱地址不能为空！")
    @Pattern(regexp = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$",
            message = "无效的邮箱地址！")
    private String email;

    /** 密码 */
    @ApiModelProperty(value = "密码", required = true, example = "12345678")
    @NotBlank(message = "密码不能为空！")
    @Size(max = 16, min = 8, message = "密码长度应该在8到16之间！")
    @Pattern(regexp = "^(?![a-zA-Z]+$)(?!\\d+$)(?![^\\da-zA-Z\\s]+$).*$",
            message = "密码必须由字母、数字和特殊字符中的任意2种组成！")
    private String password;
}
