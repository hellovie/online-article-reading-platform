package io.github.hellovie.file.mapper;

import io.github.hellovie.file.domain.dto.FileDTO;
import io.github.hellovie.file.domain.entity.File;
import org.mapstruct.Mapper;

/**
 * 文件模块 Java Bean 映射器, mapstruct实现. <br>
 *
 * @author hellovie <br>
 * @version 1.0.0 2023/5/14 <br>
 * @Email hellovie@foxmail.com <br>
 * @since JDK 1.8
 */
@Mapper(componentModel = "spring")
public interface FileMapper {
    FileDTO toDto(File entity);
}
