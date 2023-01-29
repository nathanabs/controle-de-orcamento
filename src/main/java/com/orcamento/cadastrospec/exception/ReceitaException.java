package com.orcamento.cadastrospec.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


public class ReceitaException extends BaseException{

    public ReceitaException(String mensagem) {
        super(HttpStatus.PRECONDITION_FAILED, mensagem);
    }

    public ReceitaException(String mensagem, HttpStatus status) {
        super(status, mensagem);
    }
}
