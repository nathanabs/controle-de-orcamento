package com.orcamento.cadastrospec.controllers;

import com.orcamento.cadastrospec.LoginApi;
import com.orcamento.cadastrospec.model.DadosAutenticacao;
import com.orcamento.cadastrospec.model.TokenResponse;
import com.orcamento.cadastrospec.model.Usuario;
import com.orcamento.cadastrospec.security.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LoginApiImpl implements LoginApi {

    private final AuthenticationManager manager;
    private final TokenService tokenService;

    @Override
    public ResponseEntity<TokenResponse> efetuarLogin(DadosAutenticacao dadosAutenticacao) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(dadosAutenticacao.getLogin(), dadosAutenticacao.getSenha());

        var authentication = manager.authenticate(authenticationToken);

        var tokenJwt = tokenService.gerarToken((Usuario) authentication.getPrincipal());

        var response = TokenResponse.builder().token(tokenJwt).build();

        return ResponseEntity.ok(response);
    }
}
