package io.github.hellovie.user.service.auth;

/**
 * 用户账号注册或登录的策略的注册表 Key. <br>
 *
 * @author hellovie <br>
 * @version 1.0.0 2023/5/6 <br>
 * @Email hellovie@foxmail.com <br>
 * @since JDK 1.8
 */
public enum AuthStrategyType {
    /** 默认策略 (username + password) */
    DEFAULT_AUTH_STRATEGY,
    /** 邮箱策略 */
    EMAIL_AUTH_STRATEGY,
    /** 手机策略 */
    PHONE_AUTH_STRATEGY
}
