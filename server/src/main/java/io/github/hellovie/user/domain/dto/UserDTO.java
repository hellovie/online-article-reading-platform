package io.github.hellovie.user.domain.dto;

import io.github.hellovie.core.domain.BaseDTO;
import io.github.hellovie.file.domain.dto.FileDTO;
import io.github.hellovie.user.domain.entity.Role;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 去除敏感数据的用户 DTO. <br>
 *
 * @author hellovie <br>
 * @version 1.0.0 2023/4/20 <br>
 * @Email hellovie@foxmail.com <br>
 * @since JDK 1.8
 */
@Data
public class UserDTO extends BaseDTO {
    /** 用户名 */
    private String username;

    /** 用户头像 */
    private FileDTO avatar;

    /** 用户昵称 */
    private String nickname;

    /** 是否锁定 (true: 锁定) */
    private Boolean locked;

    /** 是否启用 (true: 启用) */
    private Boolean enabled;

    /** 最后登录 IP */
    private String lastLoginIp;

    /** 最后登录时间 */
    private Date lastLoginTime;

    /** 角色列表 */
    private List<Role> roles;
}