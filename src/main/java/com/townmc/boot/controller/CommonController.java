package com.townmc.boot.controller;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import com.townmc.boot.service.UploadService;
import com.townmc.boot.utils.ApiResponse;
import com.townmc.boot.utils.LogicException;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;

@RestController("commonController")
@RequestMapping(value = "/common")
@Slf4j
public class CommonController {

    @Autowired private UploadService uploadService;
    @Autowired private Producer kaptchaProducer;

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

    @RequestMapping("/image/code")
    public void kaptcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        response.setContentType("image/jpeg");

        String capText = kaptchaProducer.createText();
        session.setAttribute(Constants.KAPTCHA_SESSION_KEY, capText);

        String code = (String) session.getAttribute(Constants.KAPTCHA_SESSION_KEY);
        log.info("输出验证码：[{}]", code);

        BufferedImage bi = kaptchaProducer.createImage(capText);
        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(bi, "jpg", out);
        out.flush();
        out.close();
    }
}
