package io.github.hellovie.core.specs;

/**
 * 模糊查询类型枚举. <br>
 *
 * @author hellovie <br>
 * @version 1.0.0 2023/5/19 <br>
 * @Email hellovie@foxmail.com <br>
 * @since JDK 1.8
 */
public enum SearchOperation {
    /** 大于 */
    GREATER_THAN,
    /** 小于 */
    LESS_THAN,
    /** 大于等于 */
    GREATER_THAN_EQUAL,
    /** 小于相等 */
    LESS_THAN_EQUAL,
    /** 不等于 */
    NOT_EQUAL,
    /** 等于 */
    EQUAL,
    /** 模糊查询 */
    MATCH,
    /** 模糊前查询 */
    MATCH_START,
    /** 模糊后查询 */
    MATCH_END,
    /** 包含 */
    IN,
    /** 不包含 */
    NOT_IN
}
