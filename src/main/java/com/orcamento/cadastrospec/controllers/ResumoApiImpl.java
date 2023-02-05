package com.orcamento.cadastrospec.controllers;

import com.orcamento.cadastrospec.ResumosApi;
import com.orcamento.cadastrospec.model.ResumoResponse;
import com.orcamento.cadastrospec.service.ResumoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ResumoApiImpl implements ResumosApi {

    private final ResumoService service;

    @Override
    public ResponseEntity<ResumoResponse> resumoMensal(Integer mes, Integer ano) {
        return ResponseEntity.ok(service.resumoMensal(mes, ano));
    }
}
