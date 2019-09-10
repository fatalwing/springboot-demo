package com.townmc.boot.domain.enums;

public enum CacheKeyPrefixEnum {
    MERCHANT_KEY("ME"), STORE_KEY("SH"), USER_KEY("10");
    private String key;

    CacheKeyPrefixEnum(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }}
