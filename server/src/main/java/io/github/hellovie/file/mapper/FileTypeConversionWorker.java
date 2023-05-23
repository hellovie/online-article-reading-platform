package io.github.hellovie.file.mapper;

import io.github.hellovie.file.domain.dto.FileDTO;
import io.github.hellovie.file.service.StorageService;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

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
    @Resource
    private Map<String, StorageService> storageServiceMap;

    /**
     * FileDTO 转换为完整的资源路径地址.
     *
     * @param fileDTO FileDTO
     * @return 完整的资源路径地址, 找不到则为"".
     */
    @Named("toFullPath")
    public String toFullPath(FileDTO fileDTO) {
        String relativePath = getRelativePath(fileDTO);

        if (fileDTO != null && relativePath != null) {
            StorageService storageService = storageServiceMap.get(fileDTO.getStorage().name());
            String path = storageService.getFullPath(relativePath);
            return path;
        }
        return "";
    }

    /**
     * 获取资源相对路径.
     *
     * @param fileDTO FileDTO
     * @return 拼接路径的值缺失则返回 null.
     */
    private String getRelativePath(FileDTO fileDTO) {
        if (fileDTO != null) {
            String fileKey = fileDTO.getFileKey();
            String path = fileDTO.getPath();
            String ext = fileDTO.getExt();
            if (fileKey != null && path != null && ext != null && !fileKey.isEmpty() && !path.isEmpty() && !ext.isEmpty()) {
                return path + "/" + fileKey + '.' + ext;
            }
        }
        return null;
    }
}
