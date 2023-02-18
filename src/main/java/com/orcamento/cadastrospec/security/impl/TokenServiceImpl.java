package com.orcamento.cadastrospec.security.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.orcamento.cadastrospec.model.Usuario;
import com.orcamento.cadastrospec.security.TokenService;
import com.orcamento.cadastrospec.utils.DateUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TokenServiceImpl implements TokenService {

    @Value("${api.security.token.secret}")
    private String secret;
    @Override
    public String gerarToken(Usuario usuario) {
        try {
            var algoritimo = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("API controle de orcamento")
                    .withSubject(usuario.getLogin())
                    .withExpiresAt(DateUtils.adicionarHoras(2))
                    .sign(algoritimo);
        } catch (JWTCreationException exception){
            throw new RuntimeException(exception);
        }
    }
}
