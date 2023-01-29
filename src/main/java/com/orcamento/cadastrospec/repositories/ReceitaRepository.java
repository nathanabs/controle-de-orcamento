package com.orcamento.cadastrospec.repositories;

import com.orcamento.cadastrospec.model.ReceitaModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReceitaRepository extends MongoRepository<ReceitaModel, String> {
}
