package io.github.hellovie.core.domain;

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
 * Entity 基类. <br>
 *
 * @author hellovie <br>
 * @version 1.0.0 2023/4/20 <br>
 * @Email hellovie@foxmail.com <br>
 * @since JDK 1.8
 */
@Data
@MappedSuperclass
public abstract class BaseEntity {
    /** 主键 */
    @Id
    @GeneratedValue(generator = "ksuid")
    @GenericGenerator(name = "ksuid", strategy = "io.github.hellovie.core.util.KsuidIdentifierGenerator")
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
