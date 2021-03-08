package com.townmc.boot.constants;

/**
 * redis 相关key定义
 * @author meng
 */
public class RedisKeyConstants {
    /**
     * 通用前缀
     */
    private static final String PUBLIC_PREFIX = "com:townmc:boot:";

    public static final String SPLIT_CHAR = ":";

    public static final Long TOKEN_EXPIRE_TIME = 7*24*60*60L;  //token失效时间

    /**
     * 图片验证码暂存key前缀
     */
    public static final String IMAGE_VALID_CODE_KEY = PUBLIC_PREFIX + "imageValidCode:";
}
