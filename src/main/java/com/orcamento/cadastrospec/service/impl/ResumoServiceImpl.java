package com.orcamento.cadastrospec.service.impl;

import com.orcamento.cadastrospec.enums.Categoria;
import com.orcamento.cadastrospec.model.Despesa;
import com.orcamento.cadastrospec.model.Receita;
import com.orcamento.cadastrospec.model.ResumoCategoria;
import com.orcamento.cadastrospec.model.ResumoResponse;
import com.orcamento.cadastrospec.repositories.DespesaRepository;
import com.orcamento.cadastrospec.repositories.ReceitaRepository;
import com.orcamento.cadastrospec.service.ResumoService;
import com.orcamento.cadastrospec.utils.DateUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

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
        var totalDespesas = getTotalDespesas(despesas);

        var saldo = totalReceitas.subtract(totalDespesas);

        return ResumoResponse.builder()
                .totalReceita(totalReceitas)
                .totalDespesa(totalDespesas)
                .saldoFinal(saldo)
                .valorCategoria(resumoDespesaPorCategoria(despesas))
                .build();
    }

    private static BigDecimal getTotalDespesas(List<Despesa> despesas) {
        return despesas.stream().map(Despesa::getValor).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private List<ResumoCategoria> resumoDespesaPorCategoria(List<Despesa> despesas){
        var despesaCategoria = despesas.stream().collect(Collectors.groupingBy(Despesa::getCategoria));

        return despesaCategoria.entrySet().stream()
                .map(entry -> getResumoDespesa(entry.getKey(), entry.getValue()))
                .toList();
    }

    private ResumoCategoria getResumoDespesa(Categoria categoria, List<Despesa> despesas){
        return ResumoCategoria.builder()
                .categoria(com.orcamento.cadastrospec.model.Categoria.fromValue(categoria.getValue()))
                .valor(getTotalDespesas(despesas))
                .build();
    }
}
