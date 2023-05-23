package io.github.hellovie.file.service;

import io.github.hellovie.file.domain.dto.FileUploadDTO;
import io.github.hellovie.file.domain.entity.File;

import java.net.UnknownHostException;
import java.util.HashMap;

/**
 * 存储服务. <br>
 *
 * @author hellovie <br>
 * @version 1.0.0 2023/5/13 <br>
 * @Email hellovie@foxmail.com <br>
 * @since JDK 1.8
 */
public interface StorageService {
    /**
     * 根据 key 获取完整资源路径.
     *
     * @return 文件完整路径.
     * @param key key.
     * @throws UnknownHostException
     */
    String getFullPath(String key);

    /**
     * 获取存储服务的信息.
     *
     * @return 存储服务的信息.
     */
    HashMap<String, String> getStorageInfo();

    /**
     * 上传文件.
     *
     * @param fileUploadDTO 上传文件所需信息.
     * @return File DTO.
     */
    File upload(FileUploadDTO fileUploadDTO);
}
