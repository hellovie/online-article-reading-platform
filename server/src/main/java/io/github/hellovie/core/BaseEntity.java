package io.github.hellovie.core;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
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
    @Column(updatable = false)
    protected String id;

    /** 创建时间 */
    @CreationTimestamp
    @Column(updatable = false)
    protected Date gmtCreate;

    /** 更新时间 */
    @UpdateTimestamp
    protected Date gmtModified;
}
