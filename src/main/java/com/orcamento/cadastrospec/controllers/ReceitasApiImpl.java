package com.orcamento.cadastrospec.controllers;

import com.orcamento.cadastrospec.ReceitasApi;
import com.orcamento.cadastrospec.model.Receita;
import com.orcamento.cadastrospec.model.ReceitasResponse;
import com.orcamento.cadastrospec.service.ReceitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ReceitasApiImpl implements ReceitasApi {

    @Autowired
    private ReceitaService service;

    @Override
    public ResponseEntity<Receita> addReceita(Receita receita) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criarReceita(receita));
    }

    @Override
    public ResponseEntity<ReceitasResponse> getReceitas() {
        return ResponseEntity.ok(service.buscarReceitas());
    }
}
