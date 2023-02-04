package com.orcamento.cadastrospec.service.impl;

import com.orcamento.cadastrospec.exception.ReceitaException;
import com.orcamento.cadastrospec.mapper.ReceitaMapper;
import com.orcamento.cadastrospec.model.Receita;
import com.orcamento.cadastrospec.model.ReceitaModel;
import com.orcamento.cadastrospec.model.ReceitasResponse;
import com.orcamento.cadastrospec.repositories.ReceitaRepository;
import com.orcamento.cadastrospec.service.ReceitaService;
import com.orcamento.cadastrospec.utils.DateUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

import static com.orcamento.cadastrospec.constants.AppConstants.Erros.RECEITA_DUPLICADA;
import static com.orcamento.cadastrospec.constants.AppConstants.Erros.RECEITA_NAO_ENCONTRADA;


@Service
@RequiredArgsConstructor
public class ReceitaServiceImpl implements ReceitaService {

    private final ReceitaRepository repository;

    private final MongoTemplate mongoTemplate;

    private final MessageSource messageSource;

    @Override
    public Receita criarReceita(Receita receita) {
        ReceitaModel model = ReceitaMapper.receitaToModel(receita);

        verificaReceitaDuplicada(receita);

        var receitaMongo = repository.insert(model);
        return ReceitaMapper.receitaModelToResponse(receitaMongo);
    }

    @Override
    public ReceitasResponse buscarReceitas(String descricao) {
        Query query = new Query();

        if (Objects.nonNull(descricao)){
            query.addCriteria(Criteria.where("descricao").regex(".*" + descricao + ".*","i"));
        }

        var receitasModel = mongoTemplate.find(query, ReceitaModel.class);

        var receitas = receitasModel.stream().map(ReceitaMapper::receitaModelToResponse).toList();

        return ReceitasResponse.builder()
                .receitas(receitas)
                .build();
    }

    @Override
    public Receita buscarReceita(String id) {
        Optional<ReceitaModel> receitaOpt = repository.findById(id);

        return receitaOpt.map(ReceitaMapper::receitaModelToResponse)
                .orElseThrow(() -> new ReceitaException(messageSource.getMessage(RECEITA_NAO_ENCONTRADA, null, Locale.getDefault()), HttpStatus.NOT_FOUND));
    }

    @Override
    public Receita atualizarReceita(String id, Receita receita) {
        var receitaModel = repository.findById(id).orElseThrow(() -> new ReceitaException(messageSource.getMessage(RECEITA_NAO_ENCONTRADA, null, Locale.getDefault()), HttpStatus.NOT_FOUND));

        verificaReceitaDuplicada(receita);

        receitaModel.setDescricao(receita.getDescricao());
        receitaModel.setValor(receita.getValor());
        receitaModel.setData(receita.getData());

        var receitaMongo = repository.save(receitaModel);
        return ReceitaMapper.receitaModelToResponse(receitaMongo);
    }

    @Override
    public void deletarReceita(String id) {
        var receitaModel = repository.findById(id).orElseThrow(() -> new ReceitaException(messageSource.getMessage(RECEITA_NAO_ENCONTRADA, null, Locale.getDefault()), HttpStatus.NOT_FOUND));

        repository.delete(receitaModel);
    }

    @Override
    public ReceitasResponse buscarReceitasMensais(Integer mes, Integer ano) {
        var dataInicial = DateUtils.criarData(mes, ano);
        var dataFinal = dataInicial.plusMonths(1);

        var receitas = repository.buscarReceitasMensais(dataInicial, dataFinal);

        var response = receitas.stream().map(ReceitaMapper::receitaModelToResponse).toList();

        return ReceitasResponse.builder()
                .receitas(response)
                .build();
    }

    private void verificaReceitaDuplicada(Receita receita) {
        Query query = new Query();
        var inicioDoMes = receita.getData().withDayOfMonth(1);
        var mesSeguinte = inicioDoMes.plusMonths(1);

        query.addCriteria(Criteria.where("data")
                        .gte(inicioDoMes)
                        .lt(mesSeguinte))
                .addCriteria(Criteria.where("descricao").is(receita.getDescricao()));

        var exists = mongoTemplate.exists(query, ReceitaModel.class);

        if (exists){
            throw new ReceitaException(messageSource.getMessage(RECEITA_DUPLICADA, null, Locale.getDefault()));
        }
    }
}
