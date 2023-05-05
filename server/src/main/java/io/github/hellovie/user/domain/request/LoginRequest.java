package io.github.hellovie.user.domain.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * 封装用户登录所需数据的 Request Object. <br>
 *
 * @author hellovie <br>
 * @version 1.0.0 2023/4/20 <br>
 * @Email hellovie@foxmail.com <br>
 * @since JDK 1.8
 */
@ApiModel("用户登录请求")
@Data
public class LoginRequest {
    /** 账号 */
    @ApiModelProperty(value = "用户名", required = true, example = "henryhwang")
    @NotBlank(message = "用户名不能为空！")
    @Size(max = 16, min = 8, message = "用户名长度应该在8到16之间！")
    @Pattern(regexp = "^[a-zA-Z]{1}\\w*$",
            message = "用户名必须以字母开头，只包含字母、数字和下划线！")
    private String username;

    /** 密码 */
    @ApiModelProperty(value = "密码", required = true, example = "12345678")
    @NotBlank(message = "密码不能为空！")
    @Size(max = 16, min = 8, message = "密码长度应该在8到16之间！")
    @Pattern(regexp = "^(?![a-zA-Z]+$)(?!\\d+$)(?![^\\da-zA-Z\\s]+$).*$",
            message = "密码必须由字母、数字和特殊字符中的任意2种组成！")
    private String password;
}
