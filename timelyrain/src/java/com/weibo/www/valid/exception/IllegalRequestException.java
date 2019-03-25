package com.weibo.www.valid.exception;

/**
 * @Author: weibo
 * @Date: 2019/3/11 20:59
 * @Version 1.0
 */
public class IllegalRequestException extends RuntimeException  {

    private static final long serialVersionUID = -3183173966865239818L;

    public IllegalRequestException() {
    }

    public IllegalRequestException(String message) {
        super(message);
    }

    public IllegalRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalRequestException(Throwable cause) {
        super(cause);
    }
}
