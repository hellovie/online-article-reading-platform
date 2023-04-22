package io.github.hellovie.user.domain.entity;

import io.github.hellovie.core.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * 角色实体
 *
 * @author hellovie
 * @Email hellovie@foxmail.com
 * @createTime 2023/4/20 20:12
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
