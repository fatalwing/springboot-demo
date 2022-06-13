package com.townmc.boot.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @author meng
 */
@Schema(title = "图片验证码")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ValidImageResp {

    /**
     * 用于验证的key
     */
    @Schema(title = "用于验证的key")
    private String imageValidKey;

    /**
     * 验证图片的base64形式，类似 data:image/jpg;base64,/9j/4AAQ==，
     * 可直接用于img标签的src属性
     */
    @Schema(title = "验证图片的base64形式，类似 data:image/jpg;base64,/9j/4AAQ==")
    private String imageContent;
}
