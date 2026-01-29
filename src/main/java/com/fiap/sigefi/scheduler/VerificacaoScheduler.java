package com.fiap.sigefi.scheduler;


import com.fiap.sigefi.service.FilaService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class VerificacaoScheduler {

    private final FilaService filaService;

    public VerificacaoScheduler(FilaService filaService) {
        this.filaService = filaService;
    }

    @Scheduled(cron = "0 0 0 * * *") // roda 1x por dia
    public void verificarVencimentoLA() {
        filaService.verificarVencimentos();
    }
}
