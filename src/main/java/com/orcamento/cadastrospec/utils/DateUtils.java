package com.orcamento.cadastrospec.utils;

import com.orcamento.cadastrospec.exception.DataException;

import java.time.*;

public class DateUtils {

    public static LocalDateTime criarData(Integer mes, Integer ano){
        if (mes < 1 || mes > 12) {
            throw new DataException("Data inválida para mês: " + mes);
        }
        if (ano < Year.MIN_VALUE || ano > Year.MAX_VALUE) {
            throw new DataException("Data inválida para ano: " + ano);
        }

        return LocalDateTime.of(ano, Month.of(mes), 1, 0, 0);
    }

    public static Instant adicionarHoras(Integer horas) {
        return LocalDateTime.now().plusHours(horas).toInstant(ZoneOffset.of("-03:00"));
    }
}
