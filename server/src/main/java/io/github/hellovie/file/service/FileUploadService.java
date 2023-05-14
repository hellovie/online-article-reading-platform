package io.github.hellovie.file.service;

import io.github.hellovie.file.enums.StorageType;

/**
 * Todo 文件上传服务接口. <br>
 *
 * @author hellovie <br>
 * @version 1.0.0 2023/5/13 <br>
 * @Email hellovie@foxmail.com <br>
 * @since JDK 1.8
 */
public interface FileUploadService {
    /**
     * 根据存储类型获取存储服务
     *
     * @param type 存储类型.
     * @return 存储服务.
     */
    StorageService getStorageService(StorageType type);
}
