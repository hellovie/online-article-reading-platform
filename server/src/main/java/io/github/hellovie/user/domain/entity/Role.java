package io.github.hellovie.user.domain.entity;

import io.github.hellovie.core.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

/**
 * 角色实体
 *
 * @author hellovie
 * @Email hellovie@foxmail.com
 * @createTime 2023/4/20 20:12
 */
@Data
@Entity
public class Role extends BaseEntity {
    /** 角色标识 */
    private String roleKey;

    /** 角色昵称 */
    private String roleName;
}
