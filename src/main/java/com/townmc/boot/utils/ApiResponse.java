package com.townmc.boot.utils;

/**
 * ajax访问时统一的返回格式对象
 * 具体业务数据为泛类T
 * @param <T>
 */
public class ApiResponse<T> {

    public static final String SUCCESS = "0";

    private Integer status = 200;
    private String error; // 定义的错误码
    private String message; // 出现错误时具体错误信息
    private Long timestamp; // 接口返回时间
    private T data; // 返回的数据对象

    public ApiResponse() {
        this.status = 200;
        this.error = "0";
        this.message = "";
        this.timestamp = System.currentTimeMillis();
        this.data = null;
    }

    public ApiResponse(String errorCode, String errorInfo, T t) {
        this.status = 200;
        this.error = errorCode;
        this.message = errorInfo;
        this.timestamp = System.currentTimeMillis();
        this.data = t;
    }

    public ApiResponse(T t) {
        this.status = 200;
        this.error = "0";
        this.message = "";
        this.timestamp = System.currentTimeMillis();
        this.data = t;
    }

    public ApiResponse(String errorCode, String errorInfo) {
        this.status = 200;
        this.error = errorCode;
        this.message = errorInfo;
        this.data = null;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
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
