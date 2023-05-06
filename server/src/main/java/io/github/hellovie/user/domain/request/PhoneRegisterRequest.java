package io.github.hellovie.user.domain.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * 封装用户手机注册所需数据的 Request Object. <br>
 *
 * @author hellovie <br>
 * @version 1.0.0 2023/5/6 <br>
 * @Email hellovie@foxmail.com <br>
 * @since JDK 1.8
 */
@ApiModel("用户手机注册请求")
@Data
public class PhoneRegisterRequest {
    /** 手机号码 */
    @ApiModelProperty(value = "手机号码", required = true)
    @NotBlank(message = "手机号码不能为空！")
    @Pattern(regexp = "/^1(3\\d|4[5-9]|5[0-35-9]|6[567]|7[0-8]|8\\d|9[0-35-9])\\d{8}$/",
            message = "无效的手机号码！")
    private String phone;

    /** 密码 */
    @ApiModelProperty(value = "密码", required = true, example = "12345678")
    @NotBlank(message = "密码不能为空！")
    @Size(max = 16, min = 8, message = "密码长度应该在8到16之间！")
    @Pattern(regexp = "^(?![a-zA-Z]+$)(?!\\d+$)(?![^\\da-zA-Z\\s]+$).*$",
            message = "密码必须由字母、数字和特殊字符中的任意2种组成！")
    private String password;
}
