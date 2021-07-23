package com.townmc.boot.utils;

import com.townmc.boot.domain.enums.Err;

import java.util.function.Supplier;

/**
 * 系统逻辑异常
 * @author meng
 */
public class BrokenException extends RuntimeException implements Supplier<BrokenException> {

    private String error;

    private String message;

    private boolean i18 = false;

    private Err err;

    private BrokenException() {

    }

    public BrokenException(Throwable cause) {
        super(cause);
    }

    public BrokenException(Err err) {
        super("errorCode: " + err.getError() + ", errorInfo: " + err.getMessage());
        this.error = err.getError();
        this.message = err.getMessage();
        this.err = err;
    }

    public BrokenException(Err err, Throwable cause) {
        super("errorCode: " + err.getError() + ", errorInfo: " + err.getMessage(), cause);
        this.error = err.getError();
        this.message = err.getMessage();
    }

    public String getMessage() {
        return message;
    }

    public String getError() {
        return error;
    }

    public Err getErr() {
        return err;
    }

    @Override
    public BrokenException get() {
        return new BrokenException(this.err);
    }
}
