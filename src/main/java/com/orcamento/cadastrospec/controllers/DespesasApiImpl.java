package com.orcamento.cadastrospec.controllers;

import com.orcamento.cadastrospec.DespesasApi;
import com.orcamento.cadastrospec.model.Despesa;
import com.orcamento.cadastrospec.model.DespesasResponse;
import com.orcamento.cadastrospec.service.DespesaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DespesasApiImpl implements DespesasApi {

    private final DespesaService service;

    @Override
    public ResponseEntity<DespesasResponse> getDespesasMensal(Integer mes, Integer ano) {
        return ResponseEntity.ok(service.buscarReceitasMensais(mes, ano));
    }

    public final ResponseEntity<Despesa> addDespesa(Despesa despesa) {
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
    public ResponseEntity<DespesasResponse> getDespesas(String descricao) {
        return ResponseEntity.ok(service.buscarDespesas(descricao));
    }

    @Override
    public ResponseEntity<Void> deleteDespesa(String id) {
        service.deletarDespesa(id);
        return ResponseEntity.ok(null);
    }
}
