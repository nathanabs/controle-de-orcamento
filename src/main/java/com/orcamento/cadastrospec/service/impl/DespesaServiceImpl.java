package com.orcamento.cadastrospec.service.impl;

import com.orcamento.cadastrospec.exception.DespesaException;
import com.orcamento.cadastrospec.mapper.DespesaMapper;
import com.orcamento.cadastrospec.model.Categoria;
import com.orcamento.cadastrospec.model.Despesa;
import com.orcamento.cadastrospec.model.DespesaDTO;
import com.orcamento.cadastrospec.model.DespesasResponse;
import com.orcamento.cadastrospec.repositories.DespesaRepository;
import com.orcamento.cadastrospec.service.DespesaService;
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

import static com.orcamento.cadastrospec.constants.AppConstants.Erros.DESPESA_DUPLICADA;
import static com.orcamento.cadastrospec.constants.AppConstants.Erros.DESPESA_NAO_ENCONTRADA;

@Service
@RequiredArgsConstructor
public class DespesaServiceImpl implements DespesaService {
    private final DespesaRepository repository;

    private final MongoTemplate mongoTemplate;

    private final MessageSource messageSource;

    @Override
    public DespesaDTO criarDespesa(DespesaDTO despesaDto) {

        if (despesaDto.getCategoria() == null){
            despesaDto.setCategoria(Categoria.OUTRAS);
        }
        var despesa = DespesaMapper.despesaToModel(despesaDto);

        verificaReceitaDuplicada(despesaDto);

        var response = repository.insert(despesa);
        return DespesaMapper.despesaModelToResponse(response);
    }

    @Override
    public DespesasResponse buscarDespesas(String descricao) {
        Query query = new Query();

        if (Objects.nonNull(descricao)){
            query.addCriteria(Criteria.where("descricao").regex(".*" + descricao + ".*","i"));
        }

        var despesas = mongoTemplate.find(query, Despesa.class);

        var despesasDto = despesas.stream().map(DespesaMapper::despesaModelToResponse).toList();

        return DespesasResponse.builder()
                .despesas(despesasDto)
                .build();
    }

    @Override
    public DespesaDTO buscarDespesa(String id) {
        Optional<Despesa> despesa = repository.findById(id);

        return despesa.map(DespesaMapper::despesaModelToResponse)
                .orElseThrow(() -> new DespesaException(messageSource.getMessage(DESPESA_NAO_ENCONTRADA, null, Locale.getDefault()), HttpStatus.NOT_FOUND));
    }

    @Override
    public DespesaDTO atualizarDespesa(String id, DespesaDTO despesaDto) {
        var despesa = repository.findById(id).orElseThrow(() -> new DespesaException(messageSource.getMessage(DESPESA_NAO_ENCONTRADA, null, Locale.getDefault()), HttpStatus.NOT_FOUND));

        verificaReceitaDuplicada(despesaDto);

        despesa.setDescricao(despesaDto.getDescricao());
        despesa.setValor(despesaDto.getValor());
        despesa.setData(despesaDto.getData());

        var receitaMongo = repository.save(despesa);
        return DespesaMapper.despesaModelToResponse(receitaMongo);
    }

    @Override
    public void deletarDespesa(String id) {
        var despesa = repository.findById(id).orElseThrow(() -> new DespesaException(messageSource.getMessage(DESPESA_NAO_ENCONTRADA, null, Locale.getDefault()), HttpStatus.NOT_FOUND));

        repository.delete(despesa);
    }

    @Override
    public DespesasResponse buscarReceitasMensais(Integer mes, Integer ano) {
        var dataInicial = DateUtils.criarData(mes, ano);
        var dataFinal = dataInicial.plusMonths(1);

        var despesas = repository.buscarReceitasMensais(dataInicial, dataFinal);

        var despesaDto = despesas.stream().map(DespesaMapper::despesaModelToResponse).toList();

        return DespesasResponse.builder()
                .despesas(despesaDto)
                .build();
    }

    private void verificaReceitaDuplicada(DespesaDTO despesa) {
        Query query = new Query();
        var inicioDoMes = despesa.getData().withDayOfMonth(1);
        var mesSeguinte = inicioDoMes.plusMonths(1);

        query.addCriteria(Criteria.where("data")
                        .gte(inicioDoMes)
                        .lt(mesSeguinte))
                .addCriteria(Criteria.where("descricao").is(despesa.getDescricao()));

        var exists = mongoTemplate.exists(query, Despesa.class);

        if (exists){
            throw new DespesaException(messageSource.getMessage(DESPESA_DUPLICADA, null, Locale.getDefault()));
        }
    }
}
