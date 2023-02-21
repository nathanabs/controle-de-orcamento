package com.orcamento.cadastrospec.infra.security;

import com.orcamento.cadastrospec.model.Usuario;

public interface TokenService {

    String gerarToken(Usuario usuario);

    String getSubject(String tokenJwt);
}
