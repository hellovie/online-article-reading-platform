package io.github.hellovie.core.domain;

import lombok.Data;

import java.util.Date;

/**
 * Entity基类
 *
 * @author hellovie
 * @Email hellovie@foxmail.com
 * @createTime 2023/4/20 17:04
 */
@Data
public abstract class BaseEntity {
    /** 主键 */
    private String id;

    /** 创建时间 */
    private Date gmtCreate;

    /** 更新时间 */
    private Date gmtModified;
}
