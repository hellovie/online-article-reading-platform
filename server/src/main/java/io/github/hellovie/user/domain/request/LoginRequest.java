package io.github.hellovie.user.domain.request;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 封装用户登录所需数据的Request Object
 *
 * @author hellovie
 * @Email hellovie@foxmail.com
 * @createTime 2023/4/20 20:44
 */
@Data
public class LoginRequest {
    /** 账号 */
    @NotBlank(message = "用户名不能为空！")
    @Length(max = 16, min = 8, message = "用户名长度应该在8到16之间！")
    private String username;

    /** 密码 */
    @NotBlank(message = "密码不能为空！")
    @Length(max = 16, min = 8, message = "密码长度应该在8到16之间！")
    private String password;
}
