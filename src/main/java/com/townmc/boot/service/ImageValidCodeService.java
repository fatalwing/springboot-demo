package com.townmc.boot.service;

import com.townmc.boot.domain.dto.ImageValidCodeResp;

/**
 * @author meng
 */
public interface ImageValidCodeService {

    /**
     * 获取图片验证码
     * @return
     */
    ImageValidCodeResp imageValidCode();

    /**
     * 根据key验证输入的验证码是否正确
     * @param imageValidKey
     * @param imageValidCode
     * @return
     */
    boolean valid(String imageValidKey, String imageValidCode);
}
