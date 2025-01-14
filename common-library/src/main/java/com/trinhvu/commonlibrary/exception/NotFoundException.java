package com.trinhvu.commonlibrary.exception;

import com.trinhvu.commonlibrary.utils.MessagesUtils;

public class NotFoundException extends RuntimeException {
    private final String message;

    public NotFoundException(String errorCode, Object... var2) {
        this.message = MessagesUtils.getMessage(errorCode, var2);
    }

    @Override
    public String getMessage() {
        return message;
    }
}