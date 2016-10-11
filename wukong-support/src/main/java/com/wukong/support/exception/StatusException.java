package com.wukong.support.exception;

/**
 * Created by dongwentao on 16/10/8.
 */
public class StatusException extends Exception {

    public StatusException() {
        super();
    }

    public StatusException(String message) {
        super(message);
    }

    public StatusException(String message, Throwable cause) {
        super(message, cause);
    }

    public StatusException(Throwable cause) {
        super(cause);
    }

    protected StatusException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
