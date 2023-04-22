package io.github.hellovie.core;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * View Object基类
 *
 * @author hellovie
 * @Email hellovie@foxmail.com
 * @createTime 2023/4/20 17:03
 */
@Data
public abstract class BaseVO {
    /** 主键 */
    protected String id;

    /** 创建时间 */
    @JsonFormat(timezone = "GMT+8", pattern= "yyyy-MM-dd HH:mm:ss")
    protected Date gmtCreate;

    /** 更新时间 */
    @JsonFormat(timezone = "GMT+8", pattern= "yyyy-MM-dd HH:mm:ss")
    protected Date gmtModified;
}
