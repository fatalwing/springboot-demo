package com.townmc.boot.service;

public interface UploadService {

    public String uploadImg(String suffix, byte[] bytes);

    public String uploadImg(String suffix, String base64);
}
