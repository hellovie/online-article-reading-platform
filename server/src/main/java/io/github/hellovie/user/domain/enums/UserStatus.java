package io.github.hellovie.user.domain.enums;

/**
 * 用户状态 (启用/禁用，锁定/解锁). <br>
 *
 * @author hellovie <br>
 * @version 1.0.0 2023/4/22 <br>
 * @Email hellovie@foxmail.com <br>
 * @since JDK 1.8
 */
public enum UserStatus {
    /** 锁定 */
    LOCK,
    /** 解锁 */
    UNLOCK,
    /** 启用 */
    ENABLE,
    /** 禁用 */
    DISABLE
}
