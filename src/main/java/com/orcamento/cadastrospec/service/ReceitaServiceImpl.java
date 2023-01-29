package com.orcamento.cadastrospec.service;

import com.orcamento.cadastrospec.exception.ReceitaException;
import com.orcamento.cadastrospec.mapper.ReceitaMapper;
import com.orcamento.cadastrospec.model.Receita;
import com.orcamento.cadastrospec.model.ReceitaModel;
import com.orcamento.cadastrospec.repositories.ReceitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

@Service
public class ReceitaServiceImpl implements ReceitaService{

    @Autowired
    private ReceitaRepository repository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public Receita criarReceita(Receita receita) {
        ReceitaModel model = ReceitaMapper.receitaToModel(receita);

        Query query = new Query();
        var inicioDoMes = receita.getData().withDayOfMonth(1);
        var mesSeguinte = inicioDoMes.plusMonths(1);

        query.addCriteria(Criteria.where("data")
                        .gte(inicioDoMes)
                        .lt(mesSeguinte))
                .addCriteria(Criteria.where("descricao").is(receita.getDescricao()));

        var receitas = mongoTemplate.find(query, ReceitaModel.class);

        if (receitas.size() > 0){
            throw new ReceitaException("Existe uma receita com essa descrição no mês");
        }

        var receitaMongo = repository.insert(model);
        return ReceitaMapper.receitaModelToResponse(receitaMongo);
    }
}
