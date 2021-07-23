package com.townmc.boot.service;

public interface UploadService {

    String uploadImg(String suffix, byte[] bytes);

    String uploadImg(String suffix, String base64);
}
