package com.townmc.boot.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Locale;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * 系统逻辑异常
 */
@Slf4j
public class LogicException extends RuntimeException implements Supplier<LogicException> {

    private String errorCode;

    private String errorInfo;

    private boolean i18 = false;

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

    public LogicException(String errorCode, String errorInfo, boolean i18) {
        super("errorCode: " + errorCode + ", errorInfo: " + errorInfo);
        this.errorCode = errorCode;
        this.i18 = i18;

        this.errorInfo = this.errorInfo(errorInfo);
    }

    public LogicException(String errorCode, String errorInfo) {
        super("errorCode: " + errorCode + ", errorInfo: " + errorInfo);
        this.errorCode = errorCode;

        this.errorInfo = this.errorInfo(errorInfo);
    }

    public LogicException(String errorCode, String errorInfo, Throwable cause) {
        super("errorCode: " + errorCode + ", errorInfo: " + errorInfo, cause);
        this.errorCode = errorCode;
        this.errorInfo = this.errorInfo(errorInfo);
    }

    private String errorInfo(String info) {
        if (this.i18) {
            MessageSource messageSource = (MessageSource) SpringUtil.getBean("messageSource");
            Locale locale = LocaleContextHolder.getLocale();
            try {
                return messageSource.getMessage(info, null, locale);
            } catch (Exception ex) {
                log.warn("error info {} no i18n messages config in {}!!!", info, locale.toString());
                return info;
            }
        } else {
            return info;
        }
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
