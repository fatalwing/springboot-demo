package com.townmc.boot.controller;

import com.townmc.boot.model.dto.ValidImageResp;
import com.townmc.boot.model.enums.Err;
import com.townmc.boot.service.ValidImageService;
import com.townmc.boot.service.UploadService;
import com.townmc.boot.utils.BrokenException;
import com.townmc.boot.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * @author meng
 */
@Tag(name = "公共接口")
@RestController("commonController")
@RequestMapping(value = "/common")
@Slf4j
public class CommonController {

    @Resource
    private UploadService uploadService;
    @Resource 
    private ValidImageService validImageService;

    @Operation(summary = "上传")
    @PostMapping("/uploadImage.json")
    public Result uploadImage(@RequestBody MultipartFile multipartFile) {
        if (null == multipartFile || multipartFile.isEmpty()) {
            throw new BrokenException(Err.REQUIRED_PARAMETER);
        }
        String picId = "";
        try {
            String fileStr = multipartFile.getOriginalFilename();
            String type = fileStr.substring(fileStr.lastIndexOf(".") + 1);
            log.info("type:" + type);
            picId = uploadService.uploadImg(type, multipartFile.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
            log.error("上传图片失败");
        }
        return Result.success(picId);
    }

    @Operation(summary = "验证图片")
    @GetMapping("/get-valid-image")
    public Result<ValidImageResp> validImage(@Parameter(hidden = true) String _r) {

        ValidImageResp data = validImageService.takeValidImage();

        return Result.success(data);
    }

    @GetMapping("/get-image-valid-token")
    public Result<String> imageValidToken(String imageValidKey, String imageValidCode) {
        return Result.success(validImageService.takeValidToken(imageValidKey, imageValidCode));
    }
}
