package com.fiap.sigefi.repository;

import com.fiap.sigefi.entities.FilaStatus;
import com.fiap.sigefi.entities.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface PacienteRepository
        extends JpaRepository<Paciente, UUID> {

    List<Paciente> findByStatus(FilaStatus status);

    @Query("""
        SELECT p FROM Paciente p
        WHERE p.status = 'EM_ESPERA'
        ORDER BY p.dataVencimentoLA ASC, p.dataEntradaFila ASC
    """)
    List<Paciente> filaOrdenada();
}
