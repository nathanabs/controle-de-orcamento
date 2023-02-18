package com.orcamento.cadastrospec.security.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.orcamento.cadastrospec.model.Usuario;
import com.orcamento.cadastrospec.security.TokenService;
import com.orcamento.cadastrospec.utils.DateUtils;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@Service
public class TokenServiceImpl implements TokenService {
    @Override
    public String gerarToken(Usuario usuario) {
        try {
            var algoritimo = Algorithm.HMAC256("1234456");
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
