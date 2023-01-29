package com.orcamento.cadastrospec.mapper;

import com.orcamento.cadastrospec.model.Receita;
import com.orcamento.cadastrospec.model.ReceitaModel;

public class ReceitaMapper {

    public static ReceitaModel receitaToModel(Receita receita){
        return ReceitaModel.builder()
                .descricao(receita.getDescricao())
                .valor(receita.getValor())
                .data(receita.getData())
                .build();
    }

    public static Receita receitaModelToResponse(ReceitaModel receita){
        return Receita.builder()
                .id(receita.getId())
                .descricao(receita.getDescricao())
                .valor(receita.getValor())
                .data(receita.getData())
                .build();
    }
}
