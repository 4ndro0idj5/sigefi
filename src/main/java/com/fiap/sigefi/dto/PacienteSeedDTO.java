package com.fiap.sigefi.dto;


import com.fiap.sigefi.entities.AsaClassificacao;

import java.time.LocalDate;

public record PacienteSeedDTO(
        String nome,
        String procedimento,
        AsaClassificacao asa,
        LocalDate dataEntradaFila,
        LocalDate dataEmissaoLA,
        LocalDate dataVencimentoLA,
        LocalDate dataConclusao
) {
}
