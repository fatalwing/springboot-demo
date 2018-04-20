package com.townmc.demo.utils;

import java.util.Optional;
import java.util.function.Supplier;

/**
 * 系统逻辑异常
 */
public class LogicException extends RuntimeException implements Supplier<LogicException> {

    private String errorCode;

    private String errorInfo;

    private LogicException() {

    }

    public LogicException(String message) {
        super(message);
    }

    public LogicException(Throwable cause) {
        super(cause);
    }

    public LogicException(String message, Throwable cause) {
        super(message, cause);
    }

    public LogicException(String errorCode, String errorInfo) {
        super("errorCode: " + errorCode + ", errorInfo: " + errorInfo);
        this.errorCode = errorCode;
        this.errorInfo = errorInfo;
    }

    public LogicException(String errorCode, String errorInfo, Throwable cause) {
        super("errorCode: " + errorCode + ", errorInfo: " + errorInfo, cause);
        this.errorCode = errorCode;
        this.errorInfo = errorInfo;
    }

    public String getErrorInfo() {
        return errorInfo;
    }

    public String getErrorCode() {
        return errorCode;
    }

    @Override
    public LogicException get() {
        return new LogicException(Optional.of(this.getErrorCode()).orElse("system_error"),
                Optional.of(this.getErrorInfo()).orElse("系统异常。by_exception"));
    }
}
