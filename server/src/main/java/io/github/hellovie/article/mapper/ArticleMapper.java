package io.github.hellovie.article.mapper;

import io.github.hellovie.article.domain.dto.ArticleDTO;
import io.github.hellovie.article.domain.entity.Article;
import io.github.hellovie.article.domain.vo.ArticleVO;
import io.github.hellovie.file.mapper.FileTypeConversionWorker;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * 文章模块 Java Bean 映射器, mapstruct 实现. <br>
 *
 * @author hellovie <br>
 * @version 1.0.0 2023/5/19 <br>
 * @Email hellovie@foxmail.com <br>
 * @since JDK 1.8
 */
@Mapper(componentModel = "spring", uses = FileTypeConversionWorker.class)
public interface ArticleMapper {
    ArticleDTO toDTO(Article entity);

    @Mapping(target = "cover", source = "cover", qualifiedByName = "toFullPath")
    @Mapping(target = "author.avatar", source = "author.avatar", qualifiedByName = "toFullPath")
    ArticleVO toVO(ArticleDTO dto);
}
