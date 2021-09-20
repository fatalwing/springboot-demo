package com.townmc.boot.model.enums;

/**
 * AccessToken的实现枚举
 */
public enum Handler {

    // 默认的handler，具体业务的根据实际实现的TokenHandler接口的service名称定义
    DEFAULT("tokenHandler")
    /*,USER("userTokenHandler")*/
    ;

    private String serviceName;

    private Handler(String serviceName) {
        this.serviceName = serviceName;
    }

    @Override
    public String toString() {
        return this.serviceName;
    }

}
