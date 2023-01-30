package com.orcamento.cadastrospec.service;

import com.orcamento.cadastrospec.model.Despesa;
import com.orcamento.cadastrospec.model.DespesasResponse;
import com.orcamento.cadastrospec.model.Receita;
import com.orcamento.cadastrospec.model.ReceitasResponse;

public interface DespesaService {

    Despesa criarDespesa(Despesa despesa);

    DespesasResponse buscarDespesas();

    Despesa buscarDespesa(String id);

    Despesa atualizarDespesa(String id, Despesa despesa);

    void deletarDespesa(String id);
}
