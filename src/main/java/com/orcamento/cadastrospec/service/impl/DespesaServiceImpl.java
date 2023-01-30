package com.orcamento.cadastrospec.service.impl;

import com.orcamento.cadastrospec.exception.ReceitaException;
import com.orcamento.cadastrospec.mapper.DespesaMapper;
import com.orcamento.cadastrospec.model.Despesa;
import com.orcamento.cadastrospec.model.DespesaModel;
import com.orcamento.cadastrospec.model.DespesasResponse;
import com.orcamento.cadastrospec.repositories.DespesaRepository;
import com.orcamento.cadastrospec.service.DespesaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Optional;

import static com.orcamento.cadastrospec.constants.AppConstants.Erros.DESPESA_DUPLICADA;
import static com.orcamento.cadastrospec.constants.AppConstants.Erros.DESPESA_NAO_ENCONTRADA;

@Service
public class DespesaServiceImpl implements DespesaService {
    @Autowired
    private DespesaRepository repository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private MessageSource messageSource;

    @Override
    public Despesa criarDespesa(Despesa despesa) {
        var model = DespesaMapper.despesaToModel(despesa);

        verificaReceitaDuplicada(despesa);

        var receitaMongo = repository.insert(model);
        return DespesaMapper.despesaModelToResponse(receitaMongo);
    }

    @Override
    public DespesasResponse buscarDespesas() {
        var despesaModels = repository.findAll();

        var despesas = despesaModels.stream().map(DespesaMapper::despesaModelToResponse).toList();

        return DespesasResponse.builder()
                .despesas(despesas)
                .build();
    }

    @Override
    public Despesa buscarDespesa(String id) {
        Optional<DespesaModel> receitaOpt = repository.findById(id);

        return receitaOpt.map(DespesaMapper::despesaModelToResponse)
                .orElseThrow(() -> new ReceitaException(messageSource.getMessage(DESPESA_NAO_ENCONTRADA, null, Locale.getDefault()), HttpStatus.NOT_FOUND));
    }

    @Override
    public Despesa atualizarDespesa(String id, Despesa despesa) {
        var despesaModel = repository.findById(id).orElseThrow(() -> new ReceitaException(messageSource.getMessage(DESPESA_NAO_ENCONTRADA, null, Locale.getDefault()), HttpStatus.NOT_FOUND));

        verificaReceitaDuplicada(despesa);

        despesaModel.setDescricao(despesa.getDescricao());
        despesaModel.setValor(despesa.getValor());
        despesaModel.setData(despesa.getData());

        var receitaMongo = repository.save(despesaModel);
        return DespesaMapper.despesaModelToResponse(receitaMongo);
    }

    @Override
    public void deletarDespesa(String id) {
        var receitaModel = repository.findById(id).orElseThrow(() -> new ReceitaException(messageSource.getMessage(DESPESA_NAO_ENCONTRADA, null, Locale.getDefault()), HttpStatus.NOT_FOUND));

        repository.delete(receitaModel);
    }

    private void verificaReceitaDuplicada(Despesa despesa) {
        Query query = new Query();
        var inicioDoMes = despesa.getData().withDayOfMonth(1);
        var mesSeguinte = inicioDoMes.plusMonths(1);

        query.addCriteria(Criteria.where("data")
                        .gte(inicioDoMes)
                        .lt(mesSeguinte))
                .addCriteria(Criteria.where("descricao").is(despesa.getDescricao()));

        var exists = mongoTemplate.exists(query, DespesaModel.class);

        if (exists){
            throw new ReceitaException(messageSource.getMessage(DESPESA_DUPLICADA, null, Locale.getDefault()));
        }
    }
}
