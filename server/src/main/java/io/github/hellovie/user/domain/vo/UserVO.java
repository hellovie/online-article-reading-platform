package io.github.hellovie.user.domain.vo;

import io.github.hellovie.core.BaseVO;
import io.github.hellovie.user.domain.entity.Role;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 去除敏感数据的用户VO
 *
 * @author hellovie
 * @Email hellovie@foxmail.com
 * @createTime 2023/4/20 20:36
 */
@Data
public class UserVO extends BaseVO {
    /** 用户名 */
    private String username;

    /** 用户昵称 */
    private String nickname;

    /** 最后登录IP */
    private String lastLoginIp;

    /** 最后登录时间 */
    private Date lastLoginTime;

    /** 角色列表 */
    private List<Role> roles;
}
