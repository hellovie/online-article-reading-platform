package io.github.hellovie.user.service.auth;

/**
 * 用户名前缀枚举. <br>
 * 对于第三方或者手机号码、邮箱等注册方式, 生成的用户名为避免重复, 采用统一前缀. <br>
 * 用户自己创建的用户名不能包含这些前缀. <br>
 *
 * @author hellovie <br>
 * @version 1.0.0 2023/5/6 <br>
 * @Email hellovie@foxmail.com <br>
 * @since JDK 1.8
 */
public enum UsernamePrefixType {
    /** 邮箱注册 */
    EMAIL__,
    /** 手机注册 */
    PHONE__,
}
