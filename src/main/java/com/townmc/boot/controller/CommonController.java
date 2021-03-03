package com.townmc.boot.controller;

import com.townmc.boot.service.UploadService;
import com.townmc.boot.utils.LogicException;
import com.townmc.utils.Result;
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

    @PostMapping("/uploadImage.json")
    @ApiOperation("上传图片")
    public Result uploadImage(@RequestBody MultipartFile multipartFile) {
        if (null == multipartFile || multipartFile.isEmpty()) {
            throw new LogicException("file_not_exists", "error.file_not_exists");
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
        return new Result(picId);
    }

}
