package com.orcamento.cadastrospec.controllers;

import com.orcamento.cadastrospec.ReceitasApi;
import com.orcamento.cadastrospec.model.ReceitaDTO;
import com.orcamento.cadastrospec.model.ReceitasResponse;
import com.orcamento.cadastrospec.service.ReceitaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ReceitasApiImpl implements ReceitasApi {

    private final ReceitaService service;

    @Override
    public ResponseEntity<ReceitaDTO> addReceita(ReceitaDTO receita) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criarReceita(receita));
    }

    @Override
    public ResponseEntity<ReceitaDTO> atualizarReceita(String id, ReceitaDTO receita) {
        return ResponseEntity.ok(service.atualizarReceita(id, receita));
    }

    @Override
    public ResponseEntity<ReceitasResponse> getReceitas(String descricao) {
        return ResponseEntity.ok(service.buscarReceitas(descricao));
    }

    @Override
    public ResponseEntity<ReceitaDTO> getReceita(String id) {
        return ResponseEntity.ok(service.buscarReceita(id));
    }

    @Override
    public ResponseEntity<Void> deleteReceita(String id) {
        service.deletarReceita(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<ReceitasResponse> getReceitasMensal(Integer mes, Integer ano) {
        return ResponseEntity.ok(service.buscarReceitasMensais(mes, ano));
    }
}
