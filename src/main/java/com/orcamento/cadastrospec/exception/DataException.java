package com.orcamento.cadastrospec.exception;

import org.springframework.http.HttpStatus;


public class DataException extends BaseException{

    public DataException(String mensagem) {
        super(HttpStatus.PRECONDITION_FAILED, mensagem);
    }

    public DataException(String mensagem, HttpStatus status) {
        super(status, mensagem);
    }
}
