package com.fiap.sigefi.repository;

import com.fiap.sigefi.entities.FilaStatus;
import com.fiap.sigefi.entities.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface PacienteRepository
        extends JpaRepository<Paciente, UUID> {

    List<Paciente> findByStatus(FilaStatus status);

    @Query("""
        SELECT p FROM Paciente p
        ORDER BY p.dataVencimentoLA ASC, p.dataEntradaFila ASC
    """)
    List<Paciente> filaOrdenada();

    @Modifying
    @Query("""
       UPDATE Paciente p 
       SET p.status = 'PERDA_LA'
       WHERE p.status = 'EM_ESPERA'
       AND p.dataVencimentoLA < :hoje
       """)
    void atualizarLaVencido(@Param("hoje") LocalDate hoje);
}
