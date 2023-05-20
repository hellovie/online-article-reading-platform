package io.github.hellovie.core.specs;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 搜索条件. <br>
 *
 * @author hellovie <br>
 * @version 1.0.0 2023/5/19 <br>
 * @Email hellovie@foxmail.com <br>
 * @since JDK 1.8
 */
@Data
@AllArgsConstructor
public class SearchCriteria {
    /** 模糊匹配的字段 */
    private String key;

    /** 查询关键字值 */
    private Object value;

    /** 模糊匹配操作 */
    private SearchOperation operation;
}
