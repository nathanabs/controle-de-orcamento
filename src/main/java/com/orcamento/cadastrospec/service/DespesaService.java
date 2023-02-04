package com.orcamento.cadastrospec.service;


import com.orcamento.cadastrospec.model.DespesaDTO;
import com.orcamento.cadastrospec.model.DespesasResponse;

public interface DespesaService {

    DespesaDTO criarDespesa(DespesaDTO despesa);

    DespesasResponse buscarDespesas(String descricao);

    DespesaDTO buscarDespesa(String id);

    DespesaDTO atualizarDespesa(String id, DespesaDTO despesa);

    void deletarDespesa(String id);

    DespesasResponse buscarReceitasMensais(Integer mes, Integer ano);
}
