package io.github.hellovie.user.domain.dto;

import io.github.hellovie.core.BaseDTO;
import io.github.hellovie.user.domain.entity.Role;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 登录DTO
 *
 * @author hellovie
 * @Email hellovie@foxmail.com
 * @createTime 2023/4/20 20:38
 */
@Data
public class LoginDTO extends BaseDTO {
    /** 用户名 */
    private String username;

    /** 用户昵称 */
    private String nickname;

    /** 是否锁定(true: 锁定) */
    private Boolean locked;

    /** 是否启用(true: 启用) */
    private Boolean enabled;

    /** 最后登录IP */
    private String lastLoginIp;

    /** 最后登录时间 */
    private Date lastLoginTime;

    /** 角色列表 */
    private List<Role> roles;

    /** token令牌 */
    private String token;
}
