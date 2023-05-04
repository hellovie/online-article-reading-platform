package io.github.hellovie.user.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.github.hellovie.core.domain.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 去除敏感数据的用户 VO. <br>
 *
 * @author hellovie <br>
 * @version 1.0.0 2023/4/20 <br>
 * @Email hellovie@foxmail.com <br>
 * @since JDK 1.8
 */
@ApiModel("用户账号信息视图")
@Data
public class UserVO extends BaseVO {
    /** 用户名 */
    @ApiModelProperty(value = "用户名")
    private String username;

    /** 用户昵称 */
    @ApiModelProperty(value = "用户昵称")
    private String nickname;

    /** 最后登录 IP */
    @ApiModelProperty(value = "最后登录IP")
    private String lastLoginIp;

    /** 最后登录时间 */
    @ApiModelProperty(value = "最后登录时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastLoginTime;
}
