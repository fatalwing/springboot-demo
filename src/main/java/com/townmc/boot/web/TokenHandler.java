package com.townmc.boot.web;

public interface TokenHandler<T> {

    public T getObjectByToken(String accessToken, AccessRequest request);
}
