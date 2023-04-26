package io.github.hellovie.user.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.github.hellovie.core.domain.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 登录用户后得到的VO(注册用户自动登录)
 *
 * @author hellovie
 * @Email hellovie@foxmail.com
 * @createTime 2023/4/20 20:36
 */
@ApiModel("用户登录视图")
@Data
public class LoginVO extends BaseVO {
    /** 用户名 */
    @ApiModelProperty(value = "用户名")
    private String username;

    /** 用户昵称 */
    @ApiModelProperty(value = "用户昵称")
    private String nickname;

    /** 最后登录IP */
    @ApiModelProperty(value = "最后登录IP")
    private String lastLoginIp;

    /** 最后登录时间 */
    @ApiModelProperty(value = "最后登录时间")
    @JsonFormat(timezone = "GMT+8", pattern= "yyyy-MM-dd HH:mm:ss")
    private Date lastLoginTime;

    /** token令牌 */
    @ApiModelProperty(value = "Token令牌")
    private String token;
}
