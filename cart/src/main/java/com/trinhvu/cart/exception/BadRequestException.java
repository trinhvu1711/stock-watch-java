package com.trinhvu.cart.exception;

import com.trinhvu.cart.utils.MessagesUtils;

public class BadRequestException extends RuntimeException {

    private String message;

    public BadRequestException(String errorCode, Object... var2) {
        this.message = MessagesUtils.getMessage(errorCode, var2);
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
