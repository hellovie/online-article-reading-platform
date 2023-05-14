package io.github.hellovie.file.service.impl;

import io.github.hellovie.file.enums.StorageType;
import io.github.hellovie.file.service.FileUploadService;
import io.github.hellovie.file.service.StorageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Todo 文件上传服务实现. <br>
 *
 * @author hellovie <br>
 * @version 1.0.0 2023/5/14 <br>
 * @Email hellovie@foxmail.com <br>
 * @since JDK 1.8
 */
@Service("fileUploadServiceImpl")
public class FileUploadServiceImpl implements FileUploadService {
    @Resource
    private Map<String, StorageService> storageServiceMap;

    /**
     * 根据存储类型获取存储服务
     *
     * @param type 存储类型.
     * @return 存储服务.
     */
    @Override
    public StorageService getStorageService(StorageType type) {
        return storageServiceMap.get(type.name());
    }
}
