package io.github.hellovie.article.domain.entity;

import io.github.hellovie.article.domain.enums.ArticleStatus;
import io.github.hellovie.article.domain.enums.CreationType;
import io.github.hellovie.core.domain.BaseEntity;
import io.github.hellovie.file.domain.entity.File;
import io.github.hellovie.user.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * 文章实体. <br>
 *
 * @author hellovie <br>
 * @version 1.0.0 2023/5/19 <br>
 * @Email hellovie@foxmail.com <br>
 * @since JDK 1.8
 */
@Data
@Entity(name = "Article")
public class Article extends BaseEntity {
    /** 标题 */
    private String title;

    /** 封面 */
    @OneToOne
    @JoinColumn(name = "cover", referencedColumnName = "id")
    private File cover;

    /** 摘要 */
    @Column(name = "abstract")
    private String articleAbstract;

    /** 文章主体 */
    @Column(columnDefinition = "TEXT")
    private String body;

    /** 状态 */
    @Enumerated(EnumType.STRING)
    private ArticleStatus status;

    /** 作者 */
    @ManyToOne
    @JoinColumn(name = "author", referencedColumnName = "id")
    private User author;

    /** 创作类型 */
    @Enumerated(EnumType.STRING)
    @Column(name = "creation_type")
    private CreationType creationType;

    /** 版权 */
    private String copyright;
}
