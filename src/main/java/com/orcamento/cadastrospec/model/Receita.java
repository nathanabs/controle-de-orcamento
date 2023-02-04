package com.orcamento.cadastrospec.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@Document(value = "Receita")
public class Receita {

    @Id
    private String id;

    private String descricao;

    private BigDecimal valor;

    private LocalDateTime data;
}
