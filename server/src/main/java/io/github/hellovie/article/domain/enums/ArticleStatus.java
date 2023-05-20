package io.github.hellovie.article.domain.enums;

/**
 * 文章状态, 默认为草稿. <br>
 *
 * @author hellovie <br>
 * @version 1.0.0 2023/5/19 <br>
 * @Email hellovie@foxmail.com <br>
 * @since JDK 1.8
 */
public enum ArticleStatus {
    /** 草稿 - 仅作者可见 */
    DRAFT,
    /** 公开 - 全部可见 */
    OPENNESS,
    /** 私密 - 仅作者可见 */
    PRIVACY
}
