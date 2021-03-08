package com.townmc.boot.constants;

/**
 * redis 相关key定义
 * @author meng
 */
public class RedisKeyConstants {
    /**
     * 分隔符
     */
    public static final String SPLIT_CHAR = ":";

    /**
     * 通用前缀
     */
    private static final String PUBLIC_PREFIX = "com:townmc:boot:";

    /**
     * token失效时间
     */
    public static final Long TOKEN_EXPIRE_TIME = 7 * 24 * 60 * 60L;

    /**
     * 图片验证码暂存key前缀
     */
    public static final String IMAGE_VALID_CODE_KEY = PUBLIC_PREFIX + "imageValidCode:";

    public static final String IMAGE_VALID_TOKEN_KEY = PUBLIC_PREFIX + "imageValidToken:";

    public static final String ID_CREATOR_KEY = PUBLIC_PREFIX + "idCreator:";
}
