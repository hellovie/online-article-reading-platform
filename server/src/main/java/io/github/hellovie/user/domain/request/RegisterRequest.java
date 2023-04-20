package io.github.hellovie.user.domain.request;

import lombok.Data;

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
    private String username;

    /** 密码 */
    private String password;
}
