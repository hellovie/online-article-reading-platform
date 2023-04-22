package io.github.hellovie.user.domain.enums;

/**
 * 用户状态(启用/禁用，锁定/解锁)
 *
 * @author hellovie
 * @Email hellovie@foxmail.com
 * @createTime 2023/4/22 22:26
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
