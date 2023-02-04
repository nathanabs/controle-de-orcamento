package com.orcamento.cadastrospec.service;

import com.orcamento.cadastrospec.model.Receita;
import com.orcamento.cadastrospec.model.ReceitasResponse;

public interface ReceitaService {

    Receita criarReceita(Receita receita);

    ReceitasResponse buscarReceitas(String descricao);

    Receita buscarReceita(String id);

    Receita atualizarReceita(String id, Receita receita);

    void deletarReceita(String id);

    ReceitasResponse buscarReceitasMensais(Integer mes, Integer ano);

}
