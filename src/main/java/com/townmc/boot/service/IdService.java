package com.townmc.boot.service;

/**
 * @author meng
 */
public interface IdService {

    /**
     * 生成一个纯数字唯一id
     * @return
     */
    String createNumId();

    /**
     * 生成一个尽可能短的唯一id
     * @return
     */
    String createId();

}
