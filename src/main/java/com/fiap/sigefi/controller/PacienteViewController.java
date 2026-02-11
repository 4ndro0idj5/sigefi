package com.fiap.sigefi.controller;

import com.fiap.sigefi.dto.PacienteRequestDTO;
import com.fiap.sigefi.entities.AsaClassificacao;
import com.fiap.sigefi.entities.FilaStatus;
import com.fiap.sigefi.entities.Paciente;
import com.fiap.sigefi.entities.PerfilUsuario;
import com.fiap.sigefi.service.FilaService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


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
    public String listar(@RequestParam(defaultValue = "MEDICO") PerfilUsuario perfil, Model model) {

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

        model.addAttribute("perfil", perfil);


        return "pacientes";
    }

    @PostMapping("/cadastro")
    public String cadastrar(
            PacienteRequestDTO dto,
            @RequestParam PerfilUsuario perfil) {

        if (perfil != PerfilUsuario.ADMINISTRATIVO) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        filaService.inserirPaciente(dto);
        return "redirect:/pacientes?perfil=" + perfil;
    }


    @PostMapping("/{id}/concluir")
    public String concluir(
            @PathVariable UUID id,
            @RequestParam PerfilUsuario perfil) {

        if (perfil != PerfilUsuario.ADMINISTRATIVO) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        filaService.concluirCirurgia(id);
        return "redirect:/pacientes?perfil=" + perfil;
    }

    @GetMapping("/relatorio")
    public ResponseEntity<byte[]> gerarRelatorio(
            @RequestParam FilaStatus status) {



        byte[] csv = filaService.gerarRelatorioCsv(status);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=fila_" + status + ".csv")
                .contentType(MediaType.TEXT_PLAIN)
                .body(csv);
    }

}
