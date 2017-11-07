package com.townmc.demo.utils;

import com.townmc.utils.JsonUtil;

public class ApiResponse<T> {

    private String errorCode;
    private String errorInfo;
    private T data;

    public ApiResponse(String errorCode, String errorInfo, T t) {
        this.errorCode = errorCode;
        this.errorInfo = errorInfo;
        this.data = t;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorInfo() {
        return errorInfo;
    }

    public void setErrorInfo(String errorInfo) {
        this.errorInfo = errorInfo;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static ApiResponse<Object> success(Object t) {
        return new ApiResponse("0", "", t);
    }

    public static ApiResponse<String> fail(String errorCode, String errorInfo) {
        return new ApiResponse(errorCode, errorInfo, "");
    }

}
