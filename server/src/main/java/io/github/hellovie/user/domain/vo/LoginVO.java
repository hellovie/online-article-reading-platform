package io.github.hellovie.user.domain.vo;

import io.github.hellovie.core.BaseVO;
import io.github.hellovie.user.domain.entity.Role;
import lombok.Data;

import java.util.List;

/**
 * 登录用户后得到的VO
 *
 * @author hellovie
 * @Email hellovie@foxmail.com
 * @createTime 2023/4/20 20:36
 */
@Data
public class LoginVO extends BaseVO {
    /** 用户名 */
    private String username;

    /** 用户昵称 */
    private String nickname;

    /** 角色列表 */
    private List<Role> roles;

    /** token令牌 */
    private String token;
}
