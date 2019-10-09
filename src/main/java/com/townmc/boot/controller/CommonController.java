package com.townmc.boot.controller;

import com.townmc.boot.service.UploadService;
import com.townmc.boot.utils.ApiResponse;
import com.townmc.boot.utils.LogicException;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController("hxfCommon")
@RequestMapping(value = "/common")
@Slf4j
public class CommonController {

    @Autowired
    private UploadService uploadService;

    @PostMapping("/uploadImage.json")
    @ApiOperation(value = "上传图片", response = ApiResponse.class, tags = {"common"})
    public ApiResponse uploadImage(@RequestBody MultipartFile multipartFile) {
        if (null == multipartFile || multipartFile.isEmpty()) {
            throw new LogicException("file_not_exists", "error.file_not_exists");
        }
        String picId = "";
        try {
            String fileStr = multipartFile.getOriginalFilename();
            String type = fileStr.substring(fileStr.lastIndexOf(".") + 1);
            log.info("tye:" + type);
            picId = uploadService.uploadImg(type, multipartFile.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
            log.error("上传图片失败");
        }
        return new ApiResponse(picId);
    }

}