package com.orcamento.cadastrospec.security;

import com.orcamento.cadastrospec.model.Usuario;

public interface TokenService {

    String gerarToken(Usuario usuario);
}
