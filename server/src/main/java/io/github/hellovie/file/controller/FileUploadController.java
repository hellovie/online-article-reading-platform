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
import static io.github.hellovie.file.config.LocalUploadProperties.IMAGE_PATH;

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
     * @param avatar 上传头像文件.
     * @return File VO.
     */
    @ApiOperation("头像上传")
    @PostMapping("/avatar")
    public ResultResponse<FileVO> uploadAvatar(@RequestBody MultipartFile avatar) {
        FileDTO fileDTO = fileService.uploadUserAvatar(avatar);
        return ResultResponse.success(fileMapper.toVO(fileDTO));
    }

    /**
     * 文章封面上传.
     *
     * @param cover 上传文章封面文件.
     * @return File VO.
     */
    @ApiOperation("文章封面上传")
    @PostMapping("/cover")
    public ResultResponse<FileVO> uploadArticleCover(@RequestBody MultipartFile cover) {
        FileDTO fileDTO = fileService.uploadFile(cover, COVER_PATH);
        return ResultResponse.success(fileMapper.toVO(fileDTO));
    }

    /**
     * 图片上传.
     *
     * @param image 图片文件.
     * @return File VO.
     */
    @ApiOperation("图片上传")
    @PostMapping("/image")
    public ResultResponse<FileVO> uploadImage(@RequestBody MultipartFile image) {
        FileDTO fileDTO = fileService.uploadFile(image, IMAGE_PATH);
        return ResultResponse.success(fileMapper.toVO(fileDTO));
    }
}
