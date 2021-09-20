package com.townmc.boot.utils;

import com.townmc.boot.model.dto.Base64Resp;

public class PicUtil {


    /**
     * 获取图片url
     *
     * @param picId
     * @param w
     * @param h
     * @return
     */
    public static String getPicUrl(String picId, Integer w, Integer h) {
        return "https://upic.juniuo.com/file/picture/" + picId + "/resize_" + w + "_" + h + "/mode_fill";
    }

    /**
     * 获取base64数据
     *
     * @param base64
     * @return
     */
    public static Base64Resp getBase64(String base64) {
        String suffix = "jpg";
        base64 = base64.replaceAll("[\\s*\t\n\r]", "");
        if (base64.startsWith("data:")) {
            String[] arr = base64.split(",");
            base64 = arr[1];
            suffix = arr[0].split(";")[0].split("/")[1];
        }
        return Base64Resp.builder().base64(base64).suffix(suffix).build();
    }


}
