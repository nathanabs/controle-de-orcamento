package com.orcamento.cadastrospec.mapper;

import com.orcamento.cadastrospec.model.Despesa;
import com.orcamento.cadastrospec.model.DespesaModel;
import com.orcamento.cadastrospec.model.Receita;
import com.orcamento.cadastrospec.model.ReceitaModel;

public class DespesaMapper {

    public static DespesaModel despesaToModel(Despesa despesa){
        return DespesaModel.builder()
                .id(despesa.getId())
                .descricao(despesa.getDescricao())
                .valor(despesa.getValor())
                .data(despesa.getData())
                .build();
    }

    public static Despesa despesaModelToResponse(DespesaModel despesa){
        return Despesa.builder()
                .id(despesa.getId())
                .descricao(despesa.getDescricao())
                .valor(despesa.getValor())
                .data(despesa.getData())
                .build();
    }
}
