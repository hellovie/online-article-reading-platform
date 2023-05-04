package io.github.hellovie.user.domain.entity;

import io.github.hellovie.core.domain.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * 角色实体. <br>
 *
 * @author hellovie <br>
 * @version 1.0.0 2023/4/20 <br>
 * @Email hellovie@foxmail.com <br>
 * @since JDK 1.8
 */
@Data
@Entity(name = "Role")
public class Role extends BaseEntity {
    /** 角色标识 */
    @Column(updatable = false)
    private String roleKey;

    /** 角色昵称 */
    private String roleName;
}
