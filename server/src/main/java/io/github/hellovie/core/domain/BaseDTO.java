package io.github.hellovie.core.domain;

import lombok.Data;

import java.util.Date;

/**
 * Data Transfer Object基类
 *
 * @author hellovie
 * @Email hellovie@foxmail.com
 * @createTime 2023/4/20 17:04
 */
@Data
public abstract class BaseDTO {
    /** 主键 */
    protected String id;

    /** 创建时间 */
    protected Date gmtCreate;

    /** 更新时间 */
    protected Date gmtModified;
}
