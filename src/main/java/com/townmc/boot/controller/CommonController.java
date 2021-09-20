package com.townmc.boot.controller;

import com.townmc.boot.model.dto.ValidImageResp;
import com.townmc.boot.model.enums.Err;
import com.townmc.boot.service.ValidImageService;
import com.townmc.boot.service.UploadService;
import com.townmc.boot.utils.BrokenException;
import com.townmc.boot.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author meng
 */
@Api(tags = {"公共接口"})
@RestController("commonController")
@RequestMapping(value = "/common")
@Slf4j
public class CommonController {

    @Autowired private UploadService uploadService;
    @Autowired private ValidImageService validImageService;

    @ApiOperation("上传图片")
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

    @ApiOperation("获得防刷验证图片")
    @GetMapping("/get-valid-image")
    public Result<ValidImageResp> validImage() {

        ValidImageResp data = validImageService.takeValidImage();

        return Result.success(data);
    }

    @ApiOperation("防刷图片进行验证并换取验证token，换取到的token 1分钟內使用有效")
    @GetMapping("/get-image-valid-token")
    public Result<String> imageValidToken(String imageValidKey, String imageValidCode) {
        return Result.success(validImageService.takeValidToken(imageValidKey, imageValidCode));
    }
}
