package io.github.hellovie.user.domain.request;

import lombok.Data;

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
    private String username;

    /** 密码 */
    private String password;
}
