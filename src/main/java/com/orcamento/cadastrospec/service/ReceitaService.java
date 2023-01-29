package com.orcamento.cadastrospec.service;

import com.orcamento.cadastrospec.model.Receita;
import com.orcamento.cadastrospec.model.ReceitasResponse;

public interface ReceitaService {

    Receita criarReceita(Receita receita);

    ReceitasResponse buscarReceitas();
}
