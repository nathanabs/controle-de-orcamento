package com.orcamento.cadastrospec.service.impl;

import com.orcamento.cadastrospec.exception.ReceitaException;
import com.orcamento.cadastrospec.mapper.ReceitaMapper;
import com.orcamento.cadastrospec.model.ReceitaDTO;
import com.orcamento.cadastrospec.model.Receita;
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
    public ReceitaDTO criarReceita(ReceitaDTO receitaDto) {
        Receita receita = ReceitaMapper.receitaToModel(receitaDto);

        verificaReceitaDuplicada(receitaDto);

        var receitaMongo = repository.insert(receita);
        return ReceitaMapper.receitaModelToResponse(receitaMongo);
    }

    @Override
    public ReceitasResponse buscarReceitas(String descricao) {
        Query query = new Query();

        if (Objects.nonNull(descricao)){
            query.addCriteria(Criteria.where("descricao").regex(".*" + descricao + ".*","i"));
        }

        var receitas = mongoTemplate.find(query, Receita.class);

        var receitasDto = receitas.stream().map(ReceitaMapper::receitaModelToResponse).toList();

        return ReceitasResponse.builder()
                .receitas(receitasDto)
                .build();
    }

    @Override
    public ReceitaDTO buscarReceita(String id) {
        Optional<Receita> receita = repository.findById(id);

        return receita.map(ReceitaMapper::receitaModelToResponse)
                .orElseThrow(() -> new ReceitaException(messageSource.getMessage(RECEITA_NAO_ENCONTRADA, null, Locale.getDefault()), HttpStatus.NOT_FOUND));
    }

    @Override
    public ReceitaDTO atualizarReceita(String id, ReceitaDTO receitaDto) {
        var receita = repository.findById(id).orElseThrow(() -> new ReceitaException(messageSource.getMessage(RECEITA_NAO_ENCONTRADA, null, Locale.getDefault()), HttpStatus.NOT_FOUND));

        verificaReceitaDuplicada(receitaDto);

        receita.setDescricao(receitaDto.getDescricao());
        receita.setValor(receitaDto.getValor());
        receita.setData(receitaDto.getData());

        var receitaMongo = repository.save(receita);
        return ReceitaMapper.receitaModelToResponse(receitaMongo);
    }

    @Override
    public void deletarReceita(String id) {
        var receita = repository.findById(id).orElseThrow(() -> new ReceitaException(messageSource.getMessage(RECEITA_NAO_ENCONTRADA, null, Locale.getDefault()), HttpStatus.NOT_FOUND));

        repository.delete(receita);
    }

    @Override
    public ReceitasResponse buscarReceitasMensais(Integer mes, Integer ano) {
        var dataInicial = DateUtils.criarData(mes, ano);
        var dataFinal = dataInicial.plusMonths(1);

        var receitas = repository.buscarReceitasMensais(dataInicial, dataFinal);

        var receitasDto = receitas.stream().map(ReceitaMapper::receitaModelToResponse).toList();

        return ReceitasResponse.builder()
                .receitas(receitasDto)
                .build();
    }

    private void verificaReceitaDuplicada(ReceitaDTO receita) {
        Query query = new Query();
        var inicioDoMes = receita.getData().withDayOfMonth(1);
        var mesSeguinte = inicioDoMes.plusMonths(1);

        query.addCriteria(Criteria.where("data")
                        .gte(inicioDoMes)
                        .lt(mesSeguinte))
                .addCriteria(Criteria.where("descricao").is(receita.getDescricao()));

        var exists = mongoTemplate.exists(query, Receita.class);

        if (exists){
            throw new ReceitaException(messageSource.getMessage(RECEITA_DUPLICADA, null, Locale.getDefault()));
        }
    }
}
