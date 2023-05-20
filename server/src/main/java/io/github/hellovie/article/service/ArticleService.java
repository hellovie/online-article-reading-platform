package io.github.hellovie.article.service;

import io.github.hellovie.article.domain.dto.ArticleDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * 文章服务接口. <br>
 *
 * @author hellovie <br>
 * @version 1.0.0 2023/5/19 <br>
 * @Email hellovie@foxmail.com <br>
 * @since JDK 1.8
 */
public interface ArticleService {
    /**
     * 模糊分页查询文章.
     *
     * @param search 关键字 (文章标题).
     * @param pageable 分页规则.
     * @return 文章分页数据 (不包含文章主体).
     */
    Page<ArticleDTO> search(String search, Pageable pageable);

    /**
     * 根据 ID 查询文章信息.
     *
     * @param id 文章 ID.
     * @return 文章信息.
     */
    ArticleDTO getOneById(String id);
}
