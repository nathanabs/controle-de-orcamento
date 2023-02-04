package com.orcamento.cadastrospec.service;

import com.orcamento.cadastrospec.model.Despesa;
import com.orcamento.cadastrospec.model.DespesasResponse;

public interface DespesaService {

    Despesa criarDespesa(Despesa despesa);

    DespesasResponse buscarDespesas(String descricao);

    Despesa buscarDespesa(String id);

    Despesa atualizarDespesa(String id, Despesa despesa);

    void deletarDespesa(String id);

    DespesasResponse buscarReceitasMensais(Integer mes, Integer ano);
}
