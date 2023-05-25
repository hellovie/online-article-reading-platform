package io.github.hellovie.file.service.impl;

import io.github.hellovie.exception.business.DatabaseFieldNotFoundException;
import io.github.hellovie.exception.business.FileUploadException;
import io.github.hellovie.file.domain.dto.FileDTO;
import io.github.hellovie.file.domain.dto.FileUploadDTO;
import io.github.hellovie.file.domain.entity.File;
import io.github.hellovie.file.domain.enums.FileStatus;
import io.github.hellovie.file.domain.enums.FileType;
import io.github.hellovie.file.domain.enums.StorageType;
import io.github.hellovie.file.mapper.FileMapper;
import io.github.hellovie.file.repository.FileRepository;
import io.github.hellovie.file.service.FileService;
import io.github.hellovie.file.service.StorageService;
import io.github.hellovie.file.util.FileTypeTransformerUtil;
import io.github.hellovie.user.domain.dto.UserDTO;
import io.github.hellovie.user.domain.entity.User;
import io.github.hellovie.user.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static io.github.hellovie.file.config.LocalUploadProperties.AVATAR_PATH;
import static io.github.hellovie.file.domain.enums.FileExceptionType.FILE_IS_EMPTY;
import static io.github.hellovie.file.domain.enums.FileExceptionType.FILE_NOT_FOUND;

/**
 * 文件服务实现. <br>
 *
 * @author hellovie <br>
 * @version 1.0.0 2023/5/14 <br>
 * @Email hellovie@foxmail.com <br>
 * @since JDK 1.8
 */
@Service("fileServiceImpl")
public class FileServiceImpl implements FileService {
    @Resource
    private Map<String, StorageService> storageServiceMap;
    @Resource(name = "fileRepository")
    private FileRepository fileRepository;
    @Resource
    private FileMapper fileMapper;
    @Resource(name = "userServiceImpl")
    private UserService userService;

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

    /**
     * 上传文件.
     *
     * @param file 上传文件的详细信息.
     * @param path 上传文件的相对路径.
     * @return FileDTO.
     */
    @Override
    public FileDTO uploadFile(MultipartFile file, String path) {
        UserDTO currentUser = userService.getCurrentUser();
        User user = new User();
        user.setId(currentUser.getId());

        // 文件校验
        verifyFile(file);

        // 文件处理
        String originalFilename = file.getOriginalFilename();
        String ext = originalFilename.split("\\.")[1];
        String fileKey = (LocalDate.now() + UUID.randomUUID().toString()).replace("-", "");
        FileType fileType = FileTypeTransformerUtil.extToFileType(ext);

        // 上传对象
        FileUploadDTO fileUploadDTO = new FileUploadDTO();
        fileUploadDTO.setName(originalFilename)
                .setFileKey(fileKey)
                .setPath(path)
                .setExt(ext)
                .setSize(file.getSize())
                .setType(fileType)
                .setStorage(getDefaultStorageType())
                .setStatus(FileStatus.UPLOADING)
                .setFile(file);
        
        // 上传成功返回对象.
        File uploadFile = getStorageService(fileUploadDTO.getStorage()).upload(fileUploadDTO);
        uploadFile.setCreatedBy(user).setUpdatedBy(user);
        return fileMapper.toDTO(fileRepository.save(uploadFile));
    }

    /**
     * 上传用户头像.
     *
     * @param file 上传文件的详细信息.
     * @return FileDTO.
     */
    @Override
    public FileDTO uploadUserAvatar(MultipartFile file) {
        FileDTO fileDTO = uploadFile(file, AVATAR_PATH);
        userService.setAvatar(userService.getCurrentUser().getId(), fileDTO.getId());
        return fileDTO;
    }

    /**
     * 获取默认的存储方式.
     *
     * @return StorageType.
     */
    @Override
    public StorageType getDefaultStorageType() {
        return StorageType.LOCAL_STORAGE;
    }

    /**
     * 根据文件 ID 获取文件信息.
     *
     * @param id 文件 ID.
     * @return 文件信息.
     */
    @Override
    public FileDTO getById(String id) {
        return fileMapper.toDTO(checkFileById(id));
    }

    /**
     * 判断文件是否存在, 不存在则抛出异常.
     *
     * @param id ID
     * @return 用户信息.
     * @throw DatabaseFieldNotFoundException 文件不存在.
     */
    private File checkFileById(String id) {
        Optional<File> file = fileRepository.findById(id);
        if (!file.isPresent()) {
            throw new DatabaseFieldNotFoundException(FILE_NOT_FOUND);
        }
        return file.get();
    }

    /**
     * 文件校验.
     *
     * @param file 用户上传的文件.
     */
    private void verifyFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new FileUploadException(FILE_IS_EMPTY);
        }
    }
}
