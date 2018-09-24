package com.uem.boxit.exception;

public class MailException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public MailException(String msg) {
        super(msg);
    }
}
