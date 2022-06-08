package com.townmc.boot.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author meng
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ValidImageResp {

    /**
     * 用于验证的key
     */
    private String imageValidKey;

    /**
     * 验证图片的base64形式，类似 data:image/jpg;base64,/9j/4AAQ==，
     * 可直接用于img标签的src属性
     */
    private String imageContent;
}
