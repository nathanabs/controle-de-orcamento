package com.orcamento.cadastrospec.service.impl;

import com.orcamento.cadastrospec.model.Despesa;
import com.orcamento.cadastrospec.model.Receita;
import com.orcamento.cadastrospec.model.ResumoResponse;
import com.orcamento.cadastrospec.repositories.DespesaRepository;
import com.orcamento.cadastrospec.repositories.ReceitaRepository;
import com.orcamento.cadastrospec.service.ResumoService;
import com.orcamento.cadastrospec.utils.DateUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class ResumoServiceImpl implements ResumoService {

    private final ReceitaRepository receitaRepository;
    private final DespesaRepository despesaRepository;

    @Override
    public ResumoResponse resumoMensal(Integer mes, Integer ano) {
        var dataInicial = DateUtils.criarData(mes, ano);
        var dataFinal = dataInicial.plusMonths(1);

        var receitas = receitaRepository.buscarReceitasMensais(dataInicial, dataFinal);
        var despesas = despesaRepository.buscarReceitasMensais(dataInicial, dataFinal);

        var totalReceitas = receitas.stream().map(Receita::getValor).reduce(BigDecimal.ZERO, BigDecimal::add);
        var totalDespesas = despesas.stream().map(Despesa::getValor).reduce(BigDecimal.ZERO, BigDecimal::add);

        var saldo = totalReceitas.subtract(totalDespesas);


        return ResumoResponse.builder()
                .totalReceita(totalReceitas)
                .totalDespesa(totalDespesas)
                .saldoFinal(saldo)
                .build();
    }
}
