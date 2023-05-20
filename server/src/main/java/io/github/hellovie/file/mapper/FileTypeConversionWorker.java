package io.github.hellovie.file.mapper;

import io.github.hellovie.file.domain.dto.FileDTO;
import io.github.hellovie.file.service.FileUploadService;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.net.UnknownHostException;

/**
 * Mapping 通用转换 (mapstruct). <br>
 *
 * @author hellovie <br>
 * @version 1.0.0 2023/5/20 <br>
 * @Email hellovie@foxmail.com <br>
 * @since JDK 1.8
 */
@Component
public class FileTypeConversionWorker {
    @Resource(name = "fileUploadServiceImpl")
    private FileUploadService fileUploadService;

    /**
     * FileDTO 转换为完整的资源路径地址.
     *
     * @param fileDTO FileDTO
     * @return 完整的资源路径地址
     * @throws UnknownHostException
     */
    @Named("toFullPath")
    public String toJsonString(FileDTO fileDTO) throws UnknownHostException {
        if (fileDTO != null) {
            String rootPath = fileUploadService.getStorageService(fileDTO.getStorage()).getRootPath();
            return fileDTO.getFullPath(rootPath);
        }
        return "";
    }
}
