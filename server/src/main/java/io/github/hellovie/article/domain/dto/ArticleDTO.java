package io.github.hellovie.article.domain.dto;

import io.github.hellovie.article.domain.enums.ArticleStatus;
import io.github.hellovie.article.domain.enums.CreationType;
import io.github.hellovie.core.domain.BaseDTO;
import io.github.hellovie.file.domain.dto.FileDTO;
import io.github.hellovie.user.domain.dto.UserDTO;
import lombok.Data;

/**
 * 文章 DTO. <br>
 *
 * @author hellovie <br>
 * @version 1.0.0 2023/5/19 <br>
 * @Email hellovie@foxmail.com <br>
 * @since JDK 1.8
 */
@Data
public class ArticleDTO extends BaseDTO {
    /** 标题 */
    private String title;

    /** 封面 */
    private FileDTO cover;

    /** 摘要 */
    private String articleAbstract;

    /** 文章主体 */
    private String body;

    /** 状态 */
    private ArticleStatus status;

    /** 作者 */
    private UserDTO author;

    /** 创作类型 */
    private CreationType creationType;

    /** 版权 */
    private String copyright;
}
