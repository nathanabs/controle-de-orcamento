package com.orcamento.cadastrospec.exception;

import org.springframework.http.HttpStatus;

public class BaseException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final HttpStatus status;

    public BaseException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
