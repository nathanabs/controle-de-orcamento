package com.orcamento.cadastrospec.enums;

public enum Categoria {

    ALIMENTACAO("Alimentação"),
    SAUDE("Saúde"),
    MORADIA("Moradia"),
    TRANSPORTE("Transporte"),
    EDUCACAO("Educação"),
    LAZER(" Lazer"),
    IMPREVISTOS("Imprevistos"),
    OUTRAS("Outras");

    private String value;

    public String getValue() {
        return value;
    }

    Categoria(String value) {
        this.value = value;
    }

    public static Categoria fromValue(String value) {
        for (Categoria b : Categoria.values()) {
            if (b.value.equals(value)) {
                return b;
            }
        }
        throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
}
