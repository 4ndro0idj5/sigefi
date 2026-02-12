package com.fiap.sigefi.service;

import com.fiap.sigefi.dto.PacienteRequestDTO;
import com.fiap.sigefi.dto.PacienteSeedDTO;
import com.fiap.sigefi.entities.FilaStatus;
import com.fiap.sigefi.entities.Paciente;
import com.fiap.sigefi.repository.PacienteRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
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

    public byte[] gerarRelatorioCsv(FilaStatus status) {

        List<Paciente> pacientes = repository
                .findByStatus(status);

        StringBuilder csv = new StringBuilder();

        if (status == FilaStatus.EM_ESPERA) {

            csv.append("Posição,Nome,Procedimento,ASA,Entrada na Fila,Vencimento LA\n");

            int posicao = 1;

            for (Paciente p : pacientes) {
                csv.append(posicao++).append(",")
                        .append(p.getNome()).append(",")
                        .append(p.getProcedimento()).append(",")
                        .append(p.getAsa()).append(",")
                        .append(p.getDataEntradaFila()).append(",")
                        .append(p.getDataVencimentoLA()).append("\n");
            }

        } else if (status == FilaStatus.PERDA_LA) {

            csv.append("Nome,Procedimento,ASA,Vencimento LA\n");

            for (Paciente p : pacientes) {
                csv.append(p.getNome()).append(",")
                        .append(p.getProcedimento()).append(",")
                        .append(p.getAsa()).append(",")
                        .append(p.getDataVencimentoLA()).append("\n");
            }

        } else if (status == FilaStatus.CONCLUIDO) {

            csv.append("Nome,Procedimento,ASA,Entrada na Fila,Data Conclusão\n");

            for (Paciente p : pacientes) {
                csv.append(p.getNome()).append(",")
                        .append(p.getProcedimento()).append(",")
                        .append(p.getAsa()).append(",")
                        .append(p.getDataEntradaFila()).append(",")
                        .append(p.getDataConclusao()).append("\n");
            }
        }

        return csv.toString().getBytes(StandardCharsets.UTF_8);
    }


    @Transactional
    public void atualizarStatusLaVencido() {
        LocalDate hoje = LocalDate.now();

        List<Paciente> pacientes = repository
                .findByStatus(FilaStatus.EM_ESPERA);

        for (Paciente paciente : pacientes) {
            if (paciente.getDataVencimentoLA().isBefore(hoje)) {
                paciente.setStatus(FilaStatus.PERDA_LA);
            }
        }

        repository.saveAll(pacientes);
    }
}


