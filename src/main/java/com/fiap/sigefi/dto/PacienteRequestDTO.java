package com.fiap.sigefi.dto;


import com.fiap.sigefi.entities.AsaClassificacao;

public record PacienteRequestDTO(
        String nome,
        String procedimento,
        AsaClassificacao asa

) {
}

