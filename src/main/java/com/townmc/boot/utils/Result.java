package com.townmc.boot.utils;

import com.alibaba.fastjson.JSON;
import com.townmc.boot.domain.enums.Err;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> {

    // 非0时代表发生错误
    private String error;

    //错误信息
    private String message;

    //返回数据
    private T data = null;

    //时间戳（秒）1558318656"
    private long timestamp;

    /**
     * 获取对象
     *
     * @param tClass
     * @param <T>
     * @return
     */
    public <T> T getObject(Class<T> tClass) {
        if (data == null) {
            return null;
        }
        return JSON.parseObject(JSON.toJSONString(data), tClass);
    }

    /**
     * 获取列表
     *
     * @param tClass
     * @param <T>
     * @return
     */
    public <T> List<T> getList(Class<T> tClass) {
        if (data == null) {
            return new ArrayList<>();
        }
        return JSON.parseArray(JSON.toJSONString(data), tClass);
    }

    public static Result success() {
        return new Result("0", "", null, getCurrentTimestamp());
    }

    public static Result success(Object data) {
        return new Result("0", "", data, getCurrentTimestamp());
    }

    public static Result fail(Err err) {
        return new Result(err.getError(), err.getMessage(), null, getCurrentTimestamp());
    }

    public static Long getCurrentTimestamp() {
        return System.currentTimeMillis() / 1000;
    }

}
