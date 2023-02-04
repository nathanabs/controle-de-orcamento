package com.orcamento.cadastrospec.service;

import com.orcamento.cadastrospec.model.ReceitaDTO;
import com.orcamento.cadastrospec.model.ReceitasResponse;

public interface ReceitaService {

    ReceitaDTO criarReceita(ReceitaDTO receita);

    ReceitasResponse buscarReceitas(String descricao);

    ReceitaDTO buscarReceita(String id);

    ReceitaDTO atualizarReceita(String id, ReceitaDTO receita);

    void deletarReceita(String id);

    ReceitasResponse buscarReceitasMensais(Integer mes, Integer ano);

}
