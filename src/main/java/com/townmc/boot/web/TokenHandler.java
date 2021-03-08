package com.townmc.boot.web;

import org.springframework.web.context.request.NativeWebRequest;

public interface TokenHandler<T> {

    public T getObjectByToken(String accessToken, NativeWebRequest request);
}
