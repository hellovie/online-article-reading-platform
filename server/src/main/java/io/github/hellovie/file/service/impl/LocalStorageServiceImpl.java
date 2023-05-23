package io.github.hellovie.file.service.impl;

import io.github.hellovie.exception.system.SystemException;
import io.github.hellovie.file.config.FilePathMappingConfig;
import io.github.hellovie.file.domain.dto.FileUploadDTO;
import io.github.hellovie.file.domain.entity.File;
import io.github.hellovie.file.domain.enums.FileStatus;
import io.github.hellovie.file.service.StorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;

import static io.github.hellovie.exception.CommonExceptionType.UNKNOWN_HOST;
import static io.github.hellovie.file.config.LocalUploadProperties.ROOT_PATH;

/**
 * 本地存储服务. <br>
 *
 * @author hellovie <br>
 * @version 1.0.0 2023/5/14 <br>
 * @Email hellovie@foxmail.com <br>
 * @since JDK 1.8
 */
@Service("LOCAL_STORAGE")
public class LocalStorageServiceImpl implements StorageService {
    @Value("${server.port}")
    private int port;
    @Value("${hellovie.protocol}")
    private String protocol;
    private InetAddress localHost = null;
    private String rootPath = null;

    /**
     * 根据 key 获取完整资源路径.
     *
     * @param key key.
     * @return 文件完整路径.
     */
    @Override
    public String getFullPath(String key) {
        if (rootPath == null) {
            try {
                localHost = InetAddress.getLocalHost();
            } catch (UnknownHostException e) {
                throw new SystemException(UNKNOWN_HOST);
            }
            String hostAddress = localHost.getHostAddress();
            rootPath = protocol + "://" + hostAddress + ":" + port + FilePathMappingConfig.FILE_ROOT;
        }
        return rootPath + key;
    }

    /**
     * 获取存储服务的信息.
     *
     * @return 存储服务的信息.
     */
    @Override
    public HashMap<String, String> getStorageInfo() {
        HashMap<String, String> storageInfo = new HashMap<>(4);
        storageInfo.put("protocol", protocol);
        storageInfo.put("port", Integer.toString(port));
        storageInfo.put("rootPath", getFullPath(""));
        storageInfo.put("storage", "LOCAL_STORAGE");
        return storageInfo;
    }

    /**
     * 上传文件.
     *
     * @param dto 上传文件所需信息.
     * @return File DTO.
     */
    @Override
    public File upload(FileUploadDTO dto) {
        // 上传文件
        String path = dto.getPath() + "/" + dto.getFileKey() + "." + dto.getExt();
        MultipartFile file = dto.getFile();
        try {
            file.transferTo(new java.io.File(path));
            File uploadFile = new File();
            uploadFile.setName(dto.getName())
            	.setFileKey(dto.getFileKey())
            	.setPath(dto.getPath().replace(ROOT_PATH, ""))
            	.setExt(dto.getExt())
            	.setSize(dto.getSize())
            	.setType(dto.getType())
            	.setStorage(dto.getStorage())
            	.setStatus(FileStatus.UPLOADED);
            return uploadFile;
        } catch (IOException e) {
            // throw new FileUploadException(FILE_UPLOAD_ERROR);
            e.printStackTrace();
        }
        return null;
    }
}
