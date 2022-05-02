package com.buttercms.exception;

public class ButterCMSResponseException extends RuntimeException {

    public ButterCMSResponseException(String message, Throwable cause) {
        super(message, cause);
    }

    public ButterCMSResponseException(String message) {
        super(message);
    }
}
