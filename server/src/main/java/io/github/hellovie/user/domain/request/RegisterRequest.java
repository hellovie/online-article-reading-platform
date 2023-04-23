package io.github.hellovie.user.domain.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


/**
 * 封装用户注册所需数据的Request Object
 *
 * @author hellovie
 * @Email hellovie@foxmail.com
 * @createTime 2023/4/20 21:28
 */
@Data
public class RegisterRequest {
    /** 用户名 */
    @NotBlank(message = "用户名不能为空！")
    @Size(max = 16, min = 8, message = "用户名长度应该在8到16之间！")
    private String username;

    /** 密码 */
    @NotBlank(message = "密码不能为空！")
    @Size(max = 16, min = 8, message = "密码长度应该在8到16之间！")
    private String password;
}
