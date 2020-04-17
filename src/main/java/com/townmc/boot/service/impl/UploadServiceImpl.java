package com.townmc.boot.service.impl;

import com.townmc.boot.service.UploadService;
import com.townmc.utils.Http;
import com.townmc.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class UploadServiceImpl implements UploadService {

    @Override
    public String uploadImg(String suffix, byte[] bytes) {
        String base64Str = "";
        String label = "platform";
        // 获得base64串
        base64Str = Base64.getEncoder().encodeToString(bytes);

        Map<String, Object> params = new HashMap<>();
        params.put("base64", base64Str);
        params.put("suffix", suffix);
        params.put("label", label);
        params.put("timestamp", System.currentTimeMillis());
        params.put("sign", "xpp");

        Http http = new Http();
        http.setTimeout(10000, 10000, 10000);
        String fileId = http.post("http://pic-src.xxx.com/file/oss_upload.do", JsonUtil.object2Json(params)).toString();
        return fileId;

    }

    @Override
    public String uploadImg(String suffix, String base64) {

        String label = "platform";

        Map<String, Object> params = new HashMap<>();
        params.put("base64", base64);
        params.put("suffix", suffix);
        params.put("label", label);
        params.put("timestamp", System.currentTimeMillis());
        params.put("sign", "xpp");

        Http http = new Http();
        http.setTimeout(10000, 10000, 10000);
        String fileId = http.post("http://pic-src.xxx.com/file/oss_upload.do", JsonUtil.object2Json(params)).toString();
        return fileId;
    }
}
