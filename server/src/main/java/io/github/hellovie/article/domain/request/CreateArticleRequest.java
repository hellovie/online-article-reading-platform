package io.github.hellovie.article.domain.request;

import io.github.hellovie.article.domain.enums.CreationType;
import io.github.hellovie.core.validation.EnumValid;
import io.github.hellovie.user.domain.enums.UserStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 创建文章请求. <br>
 *
 * @author hellovie <br>
 * @version 1.0.0 2023/5/24 <br>
 * @Email hellovie@foxmail.com <br>
 * @since JDK 1.8
 */
@ApiModel("创建文章请求")
@Data
public class CreateArticleRequest {
    /** 标题 */
    @ApiModelProperty(value = "标题", required = true)
    @NotBlank(message = "标题不能为空！")
    @Size(max = 50, min = 5, message = "文章标题长度应该在5到50个字符之间！")
    private String title;

    /** 封面 */
    private String coverId;

    /** 摘要 */
    @ApiModelProperty(value = "摘要", required = true)
    @NotBlank(message = "摘要不能为空！")
    @Size(max = 255, min = 150, message = "文章的摘要长度应该在150到255个字符之间！")
    private String articleAbstract;

    /** 文章主体 */
    @ApiModelProperty(value = "文章主体", required = true)
    @NotBlank(message = "文章主体不能为空！")
    @Size(min = 500, message = "文章的主体长度应该大于500个字符！")
    private String body;

    /** 创作类型 */
    @ApiModelProperty(value = "创作类型", required = true)
    @EnumValid(enumClass = CreationType.class, message = "无效的创作类型！")
    private String creationType;

    /** 引用或转载的信息 */
    @ApiModelProperty(value = "引用或转载的信息", required = false)
    private String quote;
}
