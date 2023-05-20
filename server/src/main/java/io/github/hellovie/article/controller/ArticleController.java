package io.github.hellovie.article.controller;

import io.github.hellovie.article.domain.vo.ArticleVO;
import io.github.hellovie.article.mapper.ArticleMapper;
import io.github.hellovie.article.service.ArticleService;
import io.github.hellovie.core.util.ResultResponse;
import io.github.hellovie.exception.business.InputException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import static io.github.hellovie.core.domain.SortType.ASC;
import static io.github.hellovie.core.domain.SortType.DESC;
import static io.github.hellovie.exception.CommonExceptionType.INVALID_COLLATION;

/**
 * 文章 Api. <br>
 *
 * @author hellovie <br>
 * @version 1.0.0 2023/5/19 <br>
 * @Email hellovie@foxmail.com <br>
 * @since JDK 1.8
 */
@Api(tags = "文章接口")
@RestController
@Validated
@RequestMapping("/articles")
public class ArticleController {
    @Resource(name = "articleServiceImpl")
    private ArticleService articleService;
    @Resource
    private ArticleMapper articleMapper;

    /**
     * 分页模糊查询文章.
     *
     * @param request 查询规则.
     * @return 分页文章.
     */
    @ApiOperation("分页模糊查询文章")
    @GetMapping("/views/{pageIndex}/{pageSize}/**")
    public ResultResponse<Page<ArticleVO>> searchPages(@PathVariable @ApiParam(value = "当前页码", required = true, example = "1") @Min(value = 1, message = "当前页码不得小于1!") @Max(value = Integer.MAX_VALUE, message = "当前页码超出有效值!") Integer pageIndex, @PathVariable @ApiParam(value = "分页大小", required = true, example = "10") @Min(value = 1, message = "分页大小不得小于1!") @Max(value = Integer.MAX_VALUE, message = "分页大小超出有效值!") Integer pageSize, HttpServletRequest request) {

        String search = request.getParameter("search");
        String sortType = request.getParameter("sort");

        search = search != null ? search : "";
        // 排序规则, 根据修改时间和 ID 排序.
        Sort sort = null;
        if (sortType == null || "".equals(sortType) || DESC.name().equals(sortType)) {
            sort = Sort.by(Sort.Direction.DESC, "gmtModified", "id");
        } else if (ASC.name().equals(sortType)) {
            sort = Sort.by(Sort.Direction.ASC, "gmtModified", "id");
        } else {
            throw new InputException(INVALID_COLLATION);
        }

        Pageable pageable = PageRequest.of(pageIndex - 1, pageSize, sort);
        Page<ArticleVO> voPage = articleService.search(search, pageable).map(articleMapper::toVO);
        return ResultResponse.success(voPage);
    }

    /**
     * 根据ID查询文章.
     *
     * @param id 文章 ID.
     * @return 文章信息.
     */
    @ApiOperation("根据ID查询文章")
    @GetMapping("/views/{id}")
    public ResultResponse<ArticleVO> getOneById(
            @PathVariable
            @ApiParam(value = "文章ID", required = true)
            @NotBlank(message = "文章ID不能为空！")
                    String id) {
        ArticleVO articleVO = articleMapper.toVO(articleService.getOneById(id));
        return ResultResponse.success(articleVO);
    }
}
