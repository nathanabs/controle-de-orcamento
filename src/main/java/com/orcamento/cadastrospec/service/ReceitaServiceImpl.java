package com.orcamento.cadastrospec.service;

import com.orcamento.cadastrospec.exception.ReceitaException;
import com.orcamento.cadastrospec.mapper.ReceitaMapper;
import com.orcamento.cadastrospec.model.Receita;
import com.orcamento.cadastrospec.model.ReceitaModel;
import com.orcamento.cadastrospec.model.ReceitasResponse;
import com.orcamento.cadastrospec.repositories.ReceitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class ReceitaServiceImpl implements ReceitaService {

    @Autowired
    private ReceitaRepository repository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public Receita criarReceita(Receita receita) {
        ReceitaModel model = ReceitaMapper.receitaToModel(receita);

        verificaReceitaDuplicada(receita);

        var receitaMongo = repository.insert(model);
        return ReceitaMapper.receitaModelToResponse(receitaMongo);
    }

    @Override
    public ReceitasResponse buscarReceitas() {
        var receitasModel = repository.findAll();

        var receitas = receitasModel.stream().map(ReceitaMapper::receitaModelToResponse).toList();

        return ReceitasResponse.builder()
                .receitas(receitas)
                .build();
    }

    @Override
    public Receita buscarReceita(String id) {
        Optional<ReceitaModel> receitaOpt = repository.findById(id);

        return receitaOpt.map(ReceitaMapper::receitaModelToResponse)
                .orElseThrow(() -> new ReceitaException("Receita não encontrada", HttpStatus.NOT_FOUND));
    }

    @Override
    public Receita atualizarReceita(String id, Receita receita) {
        var receitaModel = repository.findById(id).orElseThrow(() -> new ReceitaException("Receita não encontrada", HttpStatus.NOT_FOUND));

        verificaReceitaDuplicada(receita);

        receitaModel.setDescricao(receita.getDescricao());
        receitaModel.setValor(receita.getValor());
        receitaModel.setData(receita.getData());

        var receitaMongo = repository.save(receitaModel);
        return ReceitaMapper.receitaModelToResponse(receitaMongo);
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
            throw new ReceitaException("Existe uma receita com essa descrição no mês");
        }
    }
}
