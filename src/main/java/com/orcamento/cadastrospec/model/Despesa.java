package com.orcamento.cadastrospec.model;

import com.orcamento.cadastrospec.enums.Categoria;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@Document(value = "Despesa")
public class Despesa {

    @Id
    private String id;

    private String descricao;

    private BigDecimal valor;

    private LocalDateTime data;

    private Categoria categoria;
}
