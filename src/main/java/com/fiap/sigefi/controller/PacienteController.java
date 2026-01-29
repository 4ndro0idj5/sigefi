package com.fiap.sigefi.controller;

import com.fiap.sigefi.dto.PacienteRequestDTO;
import com.fiap.sigefi.entities.Paciente;
import com.fiap.sigefi.repository.PacienteRepository;
import com.fiap.sigefi.service.FilaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/pacientes")
public class PacienteController {

    private final FilaService filaService;


    public PacienteController(FilaService filaService,
                              PacienteRepository repository) {
        this.filaService = filaService;
    }

    @PostMapping
    public Paciente criar(@RequestBody PacienteRequestDTO paciente) {
        return filaService.inserirPaciente(paciente);
    }

    @GetMapping("/fila")
    public List<Paciente> fila() {
        return filaService.obterFilaOrdenada();
    }

    @PostMapping("/{id}/concluir")
    public void concluir(@PathVariable UUID id) {
        filaService.concluirCirurgia(id);
    }
}
