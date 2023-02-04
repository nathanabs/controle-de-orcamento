package com.orcamento.cadastrospec.mapper;

import com.orcamento.cadastrospec.model.ReceitaDTO;
import com.orcamento.cadastrospec.model.Receita;

public class ReceitaMapper {

    public static Receita receitaToModel(ReceitaDTO receita){
        return Receita.builder()
                .id(receita.getId())
                .descricao(receita.getDescricao())
                .valor(receita.getValor())
                .data(receita.getData())
                .build();
    }

    public static ReceitaDTO receitaModelToResponse(Receita receita){
        return ReceitaDTO.builder()
                .id(receita.getId())
                .descricao(receita.getDescricao())
                .valor(receita.getValor())
                .data(receita.getData())
                .build();
    }
}
