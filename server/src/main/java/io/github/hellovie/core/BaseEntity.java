package io.github.hellovie.core;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.MappedSuperclass;
import java.util.Date;

/**
 * Entity基类
 *
 * @author hellovie
 * @Email hellovie@foxmail.com
 * @createTime 2023/4/20 17:04
 */
@Data
@MappedSuperclass
public abstract class BaseEntity {
    /** 主键 */
    @Id
    @GeneratedValue(generator = "ksuid")
    @GenericGenerator(name = "ksuid", strategy = "io.github.hellovie.core.KsuidIdentifierGenerator")
    private String id;

    /** 创建时间 */
    private Date gmtCreate;

    /** 更新时间 */
    private Date gmtModified;
}
