package io.github.hellovie.article.service.impl;

import io.github.hellovie.article.domain.dto.ArticleDTO;
import io.github.hellovie.article.domain.entity.Article;
import io.github.hellovie.article.mapper.ArticleMapper;
import io.github.hellovie.article.repository.ArticleRepository;
import io.github.hellovie.article.repository.ArticleSpecification;
import io.github.hellovie.article.service.ArticleService;
import io.github.hellovie.core.specs.SearchCriteria;
import io.github.hellovie.core.specs.SearchOperation;
import io.github.hellovie.exception.business.DatabaseFieldNotFoundException;
import io.github.hellovie.user.domain.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.Optional;

import static io.github.hellovie.article.domain.enums.ArticleExceptionType.ARTICLE_NOT_FOUND;
import static io.github.hellovie.article.domain.enums.ArticleStatus.*;
import static io.github.hellovie.user.domain.enums.UserExceptionType.USER_NOT_FOUND;

/**
 * 文章服务实现类. <br>
 *
 * @author hellovie <br>
 * @version 1.0.0 2023/5/19 <br>
 * @Email hellovie@foxmail.com <br>
 * @since JDK 1.8
 */
@Service("articleServiceImpl")
@Slf4j
public class ArticleServiceImpl implements ArticleService {
    @Resource(name = "articleRepository")
    private ArticleRepository articleRepository;
    @Resource
    private ArticleMapper articleMapper;

    /**
     * 模糊分页查询文章.
     *
     * @param search   关键字 (文章标题).
     * @param pageable 分页规则.
     * @return 文章分页数据 (不包含文章主体).
     */
    @Override
    public Page<ArticleDTO> search(String search, Pageable pageable) {
        ArticleSpecification specs = new ArticleSpecification();
        specs.add(new SearchCriteria("title", search, SearchOperation.MATCH));
        specs.add(new SearchCriteria("status", DRAFT, SearchOperation.NOT_EQUAL));
        specs.add(new SearchCriteria("status", PRIVACY, SearchOperation.NOT_EQUAL));
        Page<Article> pages = articleRepository.findAll(specs, pageable);
        return pages.map(articleMapper::toDTO);
    }

    /**
     * 根据 ID 查询文章信息.
     *
     * @param id 文章 ID.
     * @return 文章信息.
     */
    @Override
    public ArticleDTO getOneById(String id) {
        Article article = checkArticleById(id);
        // 不是公开可见的文章, 显示文章不存在.
        if (!ifPublic(article)) {
            throw new DatabaseFieldNotFoundException(ARTICLE_NOT_FOUND);
        }
        return articleMapper.toDTO(article);
    }

    /**
     * 文章是否公开可见.
     *
     * @param article 文章
     * @return true: 可见.
     */
    private boolean ifPublic(Article article) {
        if (OPENNESS.equals(article.getStatus())) {
            return true;
        }
        return false;
    }

    /**
     * 判断文章是否存在, 不存在则抛出异常.
     *
     * @param id 文章 ID
     * @return 文章信息.
     * @throw DatabaseFieldNotFoundException 文章不存在.
     */
    private Article checkArticleById(String id) {
        Optional<Article> article = articleRepository.findById(id);
        if (!article.isPresent()) {
            throw new DatabaseFieldNotFoundException(ARTICLE_NOT_FOUND);
        }
        return article.get();
    }
}
