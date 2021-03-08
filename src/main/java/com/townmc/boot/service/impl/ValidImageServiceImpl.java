package com.townmc.boot.service.impl;

import com.google.code.kaptcha.Producer;
import com.townmc.boot.domain.dto.ValidImageResp;
import com.townmc.boot.service.CacheService;
import com.townmc.boot.service.ValidImageService;
import com.townmc.utils.Base64Plus;
import com.townmc.utils.LogicException;
import com.townmc.utils.StringUtil;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static com.townmc.boot.constants.RedisKeyConstants.IMAGE_VALID_CODE_KEY;
import static com.townmc.boot.constants.RedisKeyConstants.IMAGE_VALID_TOKEN_KEY;

/**
 * @author meng
 */
@Service
public class ValidImageServiceImpl implements ValidImageService {

    private final Producer kaptchaProducer;
    private final CacheService cacheService;

    public ValidImageServiceImpl(Producer kaptchaProducer,
                                 CacheService cacheService) {
        this.kaptchaProducer = kaptchaProducer;
        this.cacheService = cacheService;
    }

    @Override
    public ValidImageResp takeValidImage() {
        String capText = kaptchaProducer.createText();

        BufferedImage bi = kaptchaProducer.createImage(capText);
        ByteArrayOutputStream out = null;
        String base64 = "";
        try {
            out = new ByteArrayOutputStream();
            ImageIO.write(bi, "jpg", out);

            base64 = "data:image/jpg;base64," + Base64Plus.encode(out.toByteArray());
            out.close();
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new LogicException("create_code_error", "获得验证码失败");
        } finally {
            if (null != out) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        // 暂存到redis，有效时间10分钟
        String key = StringUtil.uuid();
        cacheService.set(IMAGE_VALID_CODE_KEY + key, capText, 60 * 10L);

        ValidImageResp data = new ValidImageResp();
        data.setImageValidKey(key);
        data.setImageContent(base64);
        return data;
    }

    @Override
    public boolean valid(String imageValidKey, String validCode) {
        if (StringUtil.isBlank(imageValidKey) || StringUtil.isBlank(validCode)) {
            throw new LogicException("required_parameter", "请提供验证的key和code");
        }

        String key = IMAGE_VALID_CODE_KEY + imageValidKey;
        String codeCache = (String) cacheService.get(key);

        return validCode.equals(codeCache);
    }

    @Override
    public String takeValidToken(String imageValidKey, String imageValidCode) {

        if (!this.valid(imageValidKey, imageValidCode)) {
            throw new LogicException("valid_image_error", "图片验证码错误");
        }

        // 生成验证token并暂存至缓存
        String token = StringUtil.uuid();
        cacheService.set(IMAGE_VALID_TOKEN_KEY + token, "1", 60 * 1L);

        return token;
    }

    @Override
    public boolean validToken(String token) {
        if (StringUtil.isBlank(token)) {
            throw new LogicException("required_parameter", "请提供验证token参数");
        }

        Object obj = cacheService.get(IMAGE_VALID_TOKEN_KEY + token);

        return null != obj;
    }
}
