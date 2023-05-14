package io.github.hellovie.file.service.impl;

import io.github.hellovie.file.config.FilePathMappingConfig;
import io.github.hellovie.file.config.LocalUploadProperties;
import io.github.hellovie.file.service.StorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Todo 本地存储服务. <br>
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

    /**
     * 获取根路径.
     *
     * @return 根路径.
     */
    @Override
    public String getRootPath() throws UnknownHostException {
        InetAddress localHost = InetAddress.getLocalHost();
        String hostAddress = localHost.getHostAddress();
        return protocol + "://" + hostAddress + ":" + port + FilePathMappingConfig.FILE_ROOT;
    }
}
