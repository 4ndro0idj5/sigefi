package com.fiap.sigefi.service;

import com.fiap.sigefi.dto.PacienteRequestDTO;
import com.fiap.sigefi.dto.PacienteSeedDTO;
import com.fiap.sigefi.entities.FilaStatus;
import com.fiap.sigefi.entities.Paciente;
import com.fiap.sigefi.repository.PacienteRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class FilaService {

    private final PacienteRepository repository;

    public FilaService(PacienteRepository repository) {
        this.repository = repository;
    }

    public Paciente inserirPaciente(PacienteRequestDTO dto) {
        Paciente paciente = new Paciente();
        paciente.setNome(dto.nome());
        paciente.setProcedimento(dto.procedimento());
        paciente.setAsa(dto.asa());

        paciente.setDataEntradaFila(LocalDate.now());
        paciente.setDataEmissaoLA(LocalDate.now());
        paciente.setDataVencimentoLA(
                paciente.getDataEmissaoLA()
                        .plusDays(dto.asa().getValidadeDias())
        );
        paciente.setStatus(FilaStatus.EM_ESPERA);

        return repository.save(paciente);
    }

    public void verificarVencimentos() {
        List<Paciente> pacientes = repository
                .findByStatus(FilaStatus.EM_ESPERA);

        LocalDate hoje = LocalDate.now();

        pacientes.stream()
                .filter(p -> p.getDataVencimentoLA().isBefore(hoje))
                .forEach(p -> {
                    p.setStatus(FilaStatus.PERDA_LA);
                    repository.save(p);
                });
    }

    public void concluirCirurgia(UUID pacienteId) {
        Paciente paciente = repository.findById(pacienteId)
                .orElseThrow();
        paciente.setStatus(FilaStatus.CONCLUIDO);
        paciente.setDataConclusao(LocalDate.now());
        repository.save(paciente);
    }

    public Paciente inserirPacienteSeed(PacienteSeedDTO dto, FilaStatus status) {

        Paciente paciente = new Paciente();
        paciente.setNome(dto.nome());
        paciente.setProcedimento(dto.procedimento());
        paciente.setAsa(dto.asa());

        paciente.setDataEntradaFila(dto.dataEntradaFila());
        paciente.setDataEmissaoLA(dto.dataEmissaoLA());
        paciente.setDataVencimentoLA(dto.dataVencimentoLA());


        paciente.setStatus(status);

        if (status == FilaStatus.CONCLUIDO) {
            paciente.setDataConclusao(dto.dataConclusao());
        }

        return repository.save(paciente);
    }

    public List<Paciente> obterFilaOrdenada() {
        return repository.filaOrdenada();
    }
}

