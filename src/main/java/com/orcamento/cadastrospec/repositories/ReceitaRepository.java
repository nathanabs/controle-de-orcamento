package com.orcamento.cadastrospec.repositories;

import com.orcamento.cadastrospec.model.Receita;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReceitaRepository extends MongoRepository<Receita, String> {

    @Query("{'data': {'$gte': ?0, '$lt': ?1}}")
    List<Receita> buscarReceitasMensais(LocalDateTime inicio, LocalDateTime fim);
}
