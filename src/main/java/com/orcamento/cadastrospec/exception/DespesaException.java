package com.orcamento.cadastrospec.exception;

import org.springframework.http.HttpStatus;


public class DespesaException extends BaseException{

    public DespesaException(String mensagem) {
        super(HttpStatus.PRECONDITION_FAILED, mensagem);
    }

    public DespesaException(String mensagem, HttpStatus status) {
        super(status, mensagem);
    }
}
