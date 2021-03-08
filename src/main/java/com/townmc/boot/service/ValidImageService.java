package com.townmc.boot.service;

import com.townmc.boot.domain.dto.ValidImageResp;

/**
 * @author meng
 */
public interface ValidImageService {

    /**
     * 获取图片验证码
     * @return
     */
    ValidImageResp takeValidImage();

    /**
     * 根据key验证输入的验证码是否正确
     * @param imageValidKey
     * @param imageValidCode
     * @return
     */
    boolean valid(String imageValidKey, String imageValidCode);

    /**
     * 获得图片验证的token
     * @param imageValidKey
     * @param imageValidCode
     * @return
     */
    String takeValidToken(String imageValidKey, String imageValidCode);

    /**
     * 验证token是否有效
     * @param token
     * @return
     */
    boolean validToken(String token);
}
