package io.github.hellovie.article.domain.vo;

import io.github.hellovie.article.domain.enums.ArticleStatus;
import io.github.hellovie.article.domain.enums.CreationType;
import io.github.hellovie.core.domain.BaseVO;
import io.github.hellovie.user.domain.vo.UserVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 文章 VO. <br>
 *
 * @author hellovie <br>
 * @version 1.0.0 2023/5/19 <br>
 * @Email hellovie@foxmail.com <br>
 * @since JDK 1.8
 */
@ApiModel("文章视图")
@Data
public class ArticleVO extends BaseVO {
    /** 标题 */
    @ApiModelProperty(value = "标题")
    private String title;

    /** 封面 */
    @ApiModelProperty(value = "封面")
    private String cover;

    /** 摘要 */
    @ApiModelProperty(value = "摘要")
    private String articleAbstract;

    /** 文章主体 */
    @ApiModelProperty(value = "文章主体")
    private String body;

    /** 状态 */
    @ApiModelProperty(value = "状态")
    private ArticleStatus status;

    /** 作者 */
    @ApiModelProperty(value = "作者")
    private UserVO author;

    /** 创作类型 */
    @ApiModelProperty(value = "创作类型")
    private CreationType creationType;

    /** 版权 */
    @ApiModelProperty(value = "版权")
    private String copyright;
}
