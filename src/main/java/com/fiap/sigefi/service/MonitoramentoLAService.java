package com.fiap.sigefi.service;

import com.fiap.sigefi.entities.FilaStatus;
import com.fiap.sigefi.entities.Paciente;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class MonitoramentoLAService {

    private final FilaService filaService;
    private final NotificacaoService notificacaoService;

    public MonitoramentoLAService(FilaService filaService,
                                  NotificacaoService notificacaoService) {
        this.filaService = filaService;
        this.notificacaoService = notificacaoService;
    }

    // Executa 1x por dia (09:00)
    @Scheduled(cron = "0 41 17 * * *")
    public void verificarLA() {

        LocalDate hoje = LocalDate.now();
        LocalDate limite = hoje.plusDays(10);

        List<Paciente> pacientes = filaService.obterFilaOrdenada();

        pacientes.stream()
                .filter(p -> p.getStatus() == FilaStatus.EM_ESPERA)
                .filter(p -> p.getDataVencimentoLA() != null)
                .filter(p -> !p.getDataVencimentoLA().isBefore(hoje))
                .filter(p -> !p.getDataVencimentoLA().isAfter(limite))
                .forEach(notificacaoService::enviarAlertaLA);
    }
}
