package io.github.hellovie.user.domain.entity;

import io.github.hellovie.core.domain.BaseEntity;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * 用户实体
 *
 * @author hellovie
 * @Email hellovie@foxmail.com
 * @createTime 2023/4/20 19:46
 */
@Data
@Entity(name = "User")
public class User extends BaseEntity {
    /** 用户名 */
    @Column(updatable = false)
    private String username;

    /** 用户昵称 */
    private String nickname;

    /** 密码 */
    private String password;

    /** 是否锁定(true: 锁定) */
    private Boolean locked = false;

    /** 是否启用(true: 启用) */
    private Boolean enabled = true;

    /** 最后登录IP */
    private String lastLoginIp;

    /** 最后登录时间 */
    private Date lastLoginTime;

    /** 角色列表 */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private List<Role> roles;
}
