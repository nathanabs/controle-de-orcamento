package com.orcamento.cadastrospec.controllers;

import com.orcamento.cadastrospec.LoginApi;
import com.orcamento.cadastrospec.model.DadosAutenticacao;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LoginApiImpl implements LoginApi {

    private final AuthenticationManager manager;

    @Override
    public ResponseEntity<Void> efetuarLogin(DadosAutenticacao dadosAutenticacao) {
        var token = new UsernamePasswordAuthenticationToken(dadosAutenticacao.getLogin(), dadosAutenticacao.getSenha());

        var authentication = manager.authenticate(token);

        return ResponseEntity.ok().build();
    }
}
