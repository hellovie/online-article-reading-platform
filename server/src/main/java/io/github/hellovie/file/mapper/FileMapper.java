package io.github.hellovie.file.mapper;

import io.github.hellovie.file.domain.dto.FileDTO;
import io.github.hellovie.file.domain.entity.File;
import io.github.hellovie.file.domain.vo.FileVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * 文件模块 Java Bean 映射器, mapstruct 实现. <br>
 *
 * @author hellovie <br>
 * @version 1.0.0 2023/5/14 <br>
 * @Email hellovie@foxmail.com <br>
 * @since JDK 1.8
 */
@Mapper(componentModel = "spring", uses = FileTypeConversionWorker.class)
public interface FileMapper {
    FileDTO toDTO(File entity);

    @Mapping(target = "url", source = "dto", qualifiedByName = "toFullPath")
    FileVO toVO(FileDTO dto);
}
