package com.orcamento.cadastrospec.controllers;

import com.orcamento.cadastrospec.ReceitasApi;
import com.orcamento.cadastrospec.model.Receita;
import com.orcamento.cadastrospec.model.ReceitasResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ReceitasApiImpl implements ReceitasApi {
    @Override
    public ResponseEntity<Receita> addReceita(Receita receita) {
        return ReceitasApi.super.addReceita(receita);
    }

    @Override
    public ResponseEntity<List<ReceitasResponse>> getReceitas() {
        return ReceitasApi.super.getReceitas();
    }
}
