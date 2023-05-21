package io.github.hellovie.file.controller;

import com.github.ksuid.KsuidGenerator;
import io.github.hellovie.core.util.ResultResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.UUID;

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

    // TODO 文件上传
    // @ApiOperation("头像上传")
    // @PostMapping("/avatar")
    // public ResultResponse<String> uploadImage(MultipartFile file) {
    //     // 文件校验
    //     if (file.isEmpty()) {
    //         return ResultResponse.success("上传文件失败!");
    //     }
    //
    //     // 文件重命名
    //     String originalFilename = file.getOriginalFilename();
    //     String ext = "." + originalFilename.split("\\.")[1];
    //     String uuid = UUID.randomUUID().toString().replace("-", "");
    //     String filename = uuid + ext;
    //
    //     // 上传文件
    //     ApplicationHome appHome = new ApplicationHome(this.getClass());
    //     String pre = appHome.getDir().getParentFile().getParentFile().getParentFile().getAbsolutePath() + "\\src\\main\\resources\\images\\";
    //     String path = pre + filename;
    //     try {
    //         file.transferTo(new File((path)));
    //         return ResultResponse.success(path);
    //     } catch (IOException e) {
    //         e.printStackTrace();
    //     }
    //     return ResultResponse.success("上传文件失败!");
    // }

    @ApiOperation("测试接口")
    @GetMapping
    public ResultResponse<HashMap<String, String>> test() {
        String id = KsuidGenerator.generate();
        String fileKey = (LocalDate.now() + UUID.randomUUID().toString()).replace("-", "");
        HashMap<String, String> map = new HashMap<>(2);
        map.put("id", id);
        map.put("fileKey", fileKey);
        return ResultResponse.success(map);
    }
}
