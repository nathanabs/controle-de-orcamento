package com.orcamento.cadastrospec.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AppConstants {

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Erros {
        public static final String RECEITA_NAO_ENCONTRADA = "receita.nao.encontrada";
        public static final String RECEITA_DUPLICADA = "receita.duplicada";

        public static final String DESPESA_NAO_ENCONTRADA = "despesa.nao.encontrada";
        public static final String DESPESA_DUPLICADA = "despesa.duplicada";
    }
}
