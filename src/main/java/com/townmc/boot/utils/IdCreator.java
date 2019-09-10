package com.townmc.boot.utils;

import com.townmc.utils.StringUtil;

public class IdCreator {
    /**
     * 通用主键id
     *
     * @return
     */
    public static String createId() {
        return "90" + System.currentTimeMillis() + StringUtil.randomNum(5);
    }


    public static String createRedisKey() {
        return "bd" + System.currentTimeMillis() + StringUtil.randomNum(5);
    }

}
