package com.orcamento.cadastrospec;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.PRECONDITION_FAILED)
public class ReceitaException extends RuntimeException{

    public ReceitaException(String message) {
        super(message);
    }
}
