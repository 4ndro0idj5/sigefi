package com.fiap.sigefi.controller;

import com.fiap.sigefi.dto.PacienteRequestDTO;
import com.fiap.sigefi.entities.AsaClassificacao;
import com.fiap.sigefi.entities.FilaStatus;
import com.fiap.sigefi.entities.Paciente;
import com.fiap.sigefi.service.FilaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/pacientes")
public class PacienteViewController {

    private final FilaService filaService;

    public PacienteViewController(FilaService filaService) {
        this.filaService = filaService;
    }

    @GetMapping
    public String listar(Model model) {

        List<Paciente> fila = filaService.obterFilaOrdenada();

        List<Paciente> emEspera = fila.stream()
                .filter(p -> p.getStatus() == FilaStatus.EM_ESPERA)
                .toList();

        List<Paciente> laVencida = fila.stream()
                .filter(p -> p.getStatus() == FilaStatus.PERDA_LA)
                .toList();

        List<Paciente> concluidos = fila.stream()
                .filter(p -> p.getStatus() == FilaStatus.CONCLUIDO)
                .toList();

        model.addAttribute("emEspera", emEspera);
        model.addAttribute("laVencida", laVencida);
        model.addAttribute("concluidos", concluidos);

        model.addAttribute("asas", AsaClassificacao.values());


        return "pacientes";
    }

    @PostMapping("/cadastro")
    public String cadastrar(PacienteRequestDTO dto) {
        filaService.inserirPaciente(dto);
        return "redirect:/pacientes";
    }

    @PostMapping("/{id}/concluir")
    public String concluir(@PathVariable UUID id) {
        filaService.concluirCirurgia(id);
        return "redirect:/pacientes";
    }
}
