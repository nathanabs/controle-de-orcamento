package com.orcamento.cadastrospec.mapper;

import com.orcamento.cadastrospec.enums.Categoria;
import com.orcamento.cadastrospec.model.DespesaDTO;
import com.orcamento.cadastrospec.model.Despesa;

public class DespesaMapper {

    public static Despesa despesaToModel(DespesaDTO despesa){
        return Despesa.builder()
                .id(despesa.getId())
                .descricao(despesa.getDescricao())
                .valor(despesa.getValor())
                .data(despesa.getData())
                .categoria(Categoria.fromValue(despesa.getCategoria().getValue()))
                .build();
    }

    public static DespesaDTO despesaModelToResponse(Despesa despesa){
        return DespesaDTO.builder()
                .id(despesa.getId())
                .descricao(despesa.getDescricao())
                .valor(despesa.getValor())
                .data(despesa.getData())
                .categoria(com.orcamento.cadastrospec.model.Categoria.fromValue(despesa.getCategoria().getValue()))
                .build();
    }
}
