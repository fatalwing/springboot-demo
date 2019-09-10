package com.townmc.boot.constants;

/**
 * redis 相关key定义
 */
public class RedisKeyConstants {

    public static final Long TOKEN_EXPIRE_TIME = 7*24*60*60L;  //token失效时间

    public static final String SPLIT_CHAR = ":";

    public static final String ORDERS_TAKE_NO_PREFIX_KEY = "com:townmc:boot_no:"; // 号前缀

}
