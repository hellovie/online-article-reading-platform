package io.github.hellovie.article.service.impl;

import io.github.hellovie.article.domain.dto.ArticleDTO;
import io.github.hellovie.article.domain.entity.Article;
import io.github.hellovie.article.domain.enums.ArticleStatus;
import io.github.hellovie.article.domain.request.CreateArticleRequest;
import io.github.hellovie.article.mapper.ArticleMapper;
import io.github.hellovie.article.repository.ArticleRepository;
import io.github.hellovie.article.repository.ArticleSpecification;
import io.github.hellovie.article.service.ArticleService;
import io.github.hellovie.core.specs.SearchCriteria;
import io.github.hellovie.core.specs.SearchOperation;
import io.github.hellovie.exception.business.DatabaseFieldNotFoundException;
import io.github.hellovie.exception.business.ForbiddenException;
import io.github.hellovie.exception.business.InputException;
import io.github.hellovie.file.domain.entity.File;
import io.github.hellovie.file.service.FileService;
import io.github.hellovie.user.domain.dto.UserDTO;
import io.github.hellovie.user.domain.entity.User;
import io.github.hellovie.user.domain.enums.UserExceptionType;
import io.github.hellovie.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;

import static io.github.hellovie.article.domain.enums.ArticleExceptionType.ARTICLE_NOT_FOUND;
import static io.github.hellovie.article.domain.enums.ArticleExceptionType.MISS_QUOTE_URL;
import static io.github.hellovie.article.domain.enums.ArticleStatus.*;
import static io.github.hellovie.article.domain.enums.CreationType.*;

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
    @Resource(name = "userServiceImpl")
    private UserService userService;
    @Resource(name = "fileServiceImpl")
    FileService fileService;

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
     * 设置文章状态.
     *
     * @param id     文章 ID.
     * @param status 文章状态.
     */
    @Override
    public void setArticleStatus(String id, ArticleStatus status) {
        UserDTO currentUser = userService.getCurrentUser();
        Article article = checkArticleById(id);
        // 修改文章的用户和文章作者不是同一人, 抛出权限不足异常.
        if (currentUser.getId() != article.getAuthor().getId()) {
            throw new ForbiddenException(UserExceptionType.NO_PERMISSION);
        }
        article.setStatus(status);
        articleRepository.save(article);
    }

    /**
     * 创建文章.
     *
     * @param request 文章信息.
     * @return ArticleDTO.
     */
    @Override
    public ArticleDTO create(CreateArticleRequest request) {
        UserDTO currentUser = userService.getCurrentUser();
        User user = new User();
        user.setId(currentUser.getId());
        Article article = new Article();
        article.setTitle(request.getTitle())
        	.setArticleAbstract(request.getArticleAbstract())
        	.setBody(request.getBody())
        	.setStatus(DRAFT)
        	.setAuthor(user);

        if (ORIGINAL.name().equals(request.getCreationType())) {
            article.setCreationType(ORIGINAL);
        } else if (REPRODUCE.name().equals(request.getCreationType())) {
            article.setCreationType(REPRODUCE);
        } else if (TRANSLATE.name().equals(request.getCreationType())) {
            article.setCreationType(TRANSLATE);
        }

        if (request.getCoverId() != null && !"".equals(request.getCoverId())) {
            File cover = new File();
            cover.setId(fileService.getById(request.getCoverId()).getId());
            article.setCover(cover);
        }

        if (article.getCreationType() == ORIGINAL) {
            article.setCopyright("本文为博主原创文章，遵循[ CC 4.0 BY-SA ](http://creativecommons.org/licenses/by-sa/4.0/)版权协议，转载请附上原文出处链接和本声明。");
        } else if ("".equals(request.getQuote())) {
            throw new InputException(MISS_QUOTE_URL);
        } else {
            article.setCopyright(request.getQuote());
        }

        Article save = articleRepository.save(article);
        return articleMapper.toDTO(save);
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
