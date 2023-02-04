package com.orcamento.cadastrospec.repositories;

import com.orcamento.cadastrospec.model.DespesaModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DespesaRepository extends MongoRepository<DespesaModel, String> {

    @Query("{'data': {'$gte': ?0, '$lt': ?1}}")
    List<DespesaModel> buscarReceitasMensais(LocalDateTime dataInicial, LocalDateTime dataFinal);
}
