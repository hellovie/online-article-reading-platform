package io.github.hellovie.file.controller;

import io.github.hellovie.core.util.ResultResponse;
import io.github.hellovie.file.domain.dto.FileDTO;
import io.github.hellovie.file.domain.vo.FileVO;
import io.github.hellovie.file.mapper.FileMapper;
import io.github.hellovie.file.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

import static io.github.hellovie.file.config.LocalUploadProperties.COVER_PATH;

/**
 * 文件上传Api. <br>
 *
 * @author hellovie <br>
 * @version 1.0.0 2023/5/13 <br>
 * @Email hellovie@foxmail.com <br>
 * @since JDK 1.8
 */
@Api(tags = "文件上传接口")
@RestController
@RequestMapping("/uploads")
public class FileUploadController {
    @Resource(name = "fileServiceImpl")
    private FileService fileService;
    @Resource
    private FileMapper fileMapper;

    /**
     * 头像上传.
     *
     * @param id 上传用户 ID.
     * @param avatar 上传头像文件.
     * @return File VO.
     */
    @ApiOperation("头像上传")
    @PostMapping("/avatar/{id}")
    public ResultResponse<FileVO> uploadAvatar(@PathVariable String id, @RequestBody MultipartFile avatar) {
        FileDTO fileDTO = fileService.uploadUserAvatar(id, avatar);
        return ResultResponse.success(fileMapper.toVO(fileDTO));
    }

    /**
     * 文章封面上传.
     *
     * @param id 上传用户 ID.
     * @param cover 上传文章封面文件.
     * @return File VO.
     */
    @ApiOperation("文章封面上传")
    @PostMapping("/cover/{id}")
    public ResultResponse<FileVO> uploadArticleCover(@PathVariable String id, @RequestBody MultipartFile cover) {
        FileDTO fileDTO = fileService.uploadFile(id, cover, COVER_PATH);
        return ResultResponse.success(fileMapper.toVO(fileDTO));
    }
}
