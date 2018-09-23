package com.uem.boxit.exception;

public class CnpjJaExisteException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public CnpjJaExisteException(String msg) {
        super(msg);
    }
}
