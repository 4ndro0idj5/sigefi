package com.fiap.sigefi.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String nome;

    private String procedimento;

    @Enumerated(EnumType.STRING)
    private AsaClassificacao asa;

    private LocalDate dataEntradaFila;

    private LocalDate dataEmissaoLA;

    private LocalDate dataVencimentoLA;

    @Enumerated(EnumType.STRING)
    private FilaStatus status;

}
