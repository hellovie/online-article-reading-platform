package io.github.hellovie.core.domain;

import lombok.Data;

import java.util.Date;

/**
 * Data Transfer Object 基类. <br>
 *
 * @author hellovie <br>
 * @version 1.0.0 2023/4/20 <br>
 * @Email hellovie@foxmail.com <br>
 * @since JDK 1.8
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
