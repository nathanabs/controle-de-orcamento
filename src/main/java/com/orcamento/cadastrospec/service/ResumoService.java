package com.orcamento.cadastrospec.service;

import com.orcamento.cadastrospec.model.ResumoResponse;

public interface ResumoService {

    ResumoResponse resumoMensal(Integer mes, Integer ano);
}
