package com.orcamento.cadastrospec.controllers;

import com.orcamento.cadastrospec.DespesasApi;
import com.orcamento.cadastrospec.ReceitasApi;
import com.orcamento.cadastrospec.model.Despesa;
import com.orcamento.cadastrospec.model.DespesasResponse;
import com.orcamento.cadastrospec.model.Receita;
import com.orcamento.cadastrospec.model.ReceitasResponse;
import com.orcamento.cadastrospec.service.DespesaService;
import com.orcamento.cadastrospec.service.ReceitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DespesasApiImpl implements DespesasApi {

    @Autowired
    private DespesaService service;

    @Override
    public ResponseEntity<Despesa> addDespesa(Despesa despesa) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criarDespesa(despesa));
    }

    @Override
    public ResponseEntity<Despesa> atualizarDespesa(String id, Despesa despesa) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(service.atualizarDespesa(id, despesa));
    }

    @Override
    public ResponseEntity<Despesa> getDespesa(String id) {
        return ResponseEntity.ok(service.buscarDespesa(id));
    }

    @Override
    public ResponseEntity<DespesasResponse> getDespesas() {
        return ResponseEntity.ok(service.buscarDespesas());
    }

    @Override
    public ResponseEntity<Void> deleteDespesa(String id) {
        service.deletarDespesa(id);
        return ResponseEntity.ok(null);
    }
}