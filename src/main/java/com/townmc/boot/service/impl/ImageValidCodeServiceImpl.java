package com.townmc.boot.service.impl;

import com.google.code.kaptcha.Producer;
import com.townmc.boot.dao.RedisDao;
import com.townmc.boot.domain.dto.ImageValidCodeResp;
import com.townmc.boot.service.ImageValidCodeService;
import com.townmc.utils.Base64Plus;
import com.townmc.utils.LogicException;
import com.townmc.utils.StringUtil;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static com.townmc.boot.constants.RedisKeyConstants.IMAGE_VALID_CODE_KEY;

/**
 * @author meng
 */
@Service
public class ImageValidCodeServiceImpl implements ImageValidCodeService {

    private final Producer kaptchaProducer;
    private final RedisDao redisDao;

    public ImageValidCodeServiceImpl(Producer kaptchaProducer,
                                     RedisDao redisDao) {
        this.kaptchaProducer = kaptchaProducer;
        this.redisDao = redisDao;
    }

    @Override
    public ImageValidCodeResp imageValidCode() {
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
        redisDao.set(IMAGE_VALID_CODE_KEY + key, capText, 60 * 10L);

        ImageValidCodeResp data = new ImageValidCodeResp();
        data.setImgValidKey(key);
        data.setValidImg(base64);
        return data;
    }

    @Override
    public boolean valid(String imageValidKey, String imageValidCode) {
        if (StringUtil.isBlank(imageValidKey) || StringUtil.isBlank(imageValidCode)) {
            throw new LogicException("required_parameter", "请提供验证的key和code");
        }

        String key = IMAGE_VALID_CODE_KEY + imageValidKey;
        String codeCache = (String) redisDao.get(key);

        return imageValidCode.equals(codeCache);
    }
}
