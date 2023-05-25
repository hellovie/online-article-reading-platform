package io.github.hellovie.file.service;

import io.github.hellovie.file.domain.dto.FileDTO;
import io.github.hellovie.file.domain.enums.StorageType;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件服务接口. <br>
 *
 * @author hellovie <br>
 * @version 1.0.0 2023/5/13 <br>
 * @Email hellovie@foxmail.com <br>
 * @since JDK 1.8
 */
public interface FileService {
    /**
     * 根据存储类型获取存储服务
     *
     * @param type 存储类型.
     * @return 存储服务.
     */
    StorageService getStorageService(StorageType type);

    /**
     * 上传文件.
     *
     * @param file 上传文件的详细信息.
     * @param path 上传文件的相对路径.
     * @return FileDTO.
     */
    FileDTO uploadFile(MultipartFile file, String path);

    /**
     * 获取默认的存储方式.
     *
     * @return StorageType.
     */
    StorageType getDefaultStorageType();

    /**
     * 上传用户头像.
     *
     * @param file 上传文件的详细信息.
     * @return FileDTO.
     */
    FileDTO uploadUserAvatar(MultipartFile file);

    /**
     * 根据文件 ID 获取文件信息.
     *
     * @param id 文件 ID.
     * @return 文件信息.
     */
    FileDTO getById(String id);
}
