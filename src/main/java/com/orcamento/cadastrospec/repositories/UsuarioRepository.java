package com.orcamento.cadastrospec.repositories;

import com.orcamento.cadastrospec.model.Usuario;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends MongoRepository<Usuario, String> {

    @Query("{'login': ?0}")
    UserDetails findByLogin(String login);
}
