package com.townmc.boot.utils;

import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.Map;

/******************************************
 * Created by dongwujing on 2018/8/20.
 * map builder
 ******************************************/
public class MapBuilder<T> {

    public Builder<T> b;

    public MapBuilder(Builder<T> b) {
        this.b = b;
    }

    public Map<String, T> map() {
        return b.map;
    }

    public T get(String key) {
        return b.map.get(key);
    }


    public static class Builder<T> {

        public Map<String, T> map;

        public Builder() {
            map = new HashMap<String, T>();
        }

        public Builder<T> map(String key, T value) {
            map.put(key, value);
            return this;
        }


        public MapBuilder<T> build() {
            return new MapBuilder<T>(this);
        }
    }

    public static void main(String[] args) {
        MapBuilder<String> build = new MapBuilder.Builder<String>().map("a", "b").map("c", "d").build();
        System.out.println(build.get("a"));
        System.out.println(JSON.toJSON(build.map()));
    }

}
