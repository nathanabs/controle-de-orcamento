package com.orcamento.cadastrospec.repositories;

import com.orcamento.cadastrospec.model.DespesaModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DespesaRepository extends MongoRepository<String, DespesaModel> {
}
