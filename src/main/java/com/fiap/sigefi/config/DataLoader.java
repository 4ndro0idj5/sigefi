package com.fiap.sigefi.config;

import com.fiap.sigefi.dto.PacienteSeedDTO;
import com.fiap.sigefi.entities.*;
import com.fiap.sigefi.repository.PacienteRepository;
import com.fiap.sigefi.service.FilaService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;


@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner seedPacientes(FilaService filaService,
                                    PacienteRepository repository) {
        return args -> {

            if (repository.count() > 0) return;

            LocalDate hoje = LocalDate.now();

            // =====================
            // EM ESPERA (válidos)
            // =====================
            filaService.inserirPacienteSeed(
                    new PacienteSeedDTO(
                            "Maria Silva",
                            "Colecistectomia",
                            AsaClassificacao.ASA_I,
                            hoje.minusDays(30),
                            hoje.minusDays(30),
                            hoje.plusDays(335),
                            null
                    ),
                    FilaStatus.EM_ESPERA
            );

            filaService.inserirPacienteSeed(
                    new PacienteSeedDTO(
                            "Ronaldo Silva",
                            "Cirugia Córnea",
                            AsaClassificacao.ASA_II,
                            hoje.minusDays(30),
                            hoje.minusDays(30),
                            hoje.plusDays(300),
                            null
                    ),
                    FilaStatus.EM_ESPERA
            );


            filaService.inserirPacienteSeed(
                    new PacienteSeedDTO(
                            "João Pereira",
                            "Hérnia Inguinal",
                            AsaClassificacao.ASA_II,
                            hoje.minusDays(90),
                            hoje.minusDays(90),
                            hoje.plusDays(7),
                            null
                    ),
                    FilaStatus.EM_ESPERA
            );

            // =====================
            // PERDA DE LA (vencidos)
            // =====================
            filaService.inserirPacienteSeed(
                    new PacienteSeedDTO(
                            "Carlos Mendes",
                            "Prostatectomia",
                            AsaClassificacao.ASA_II,
                            hoje.minusDays(200),
                            hoje.minusDays(200),
                            hoje.minusDays(20),
                            null
                    ),
                    FilaStatus.PERDA_LA
            );

            filaService.inserirPacienteSeed(
                    new PacienteSeedDTO(
                            "Fernanda Lima",
                            "Cirurgia Cardíaca",
                            AsaClassificacao.ASA_III,
                            hoje.minusDays(120),
                            hoje.minusDays(120),
                            hoje.minusDays(10),
                            null
                    ),
                    FilaStatus.PERDA_LA
            );

            // =====================
            // CONCLUÍDO
            // =====================
            filaService.inserirPacienteSeed(
                    new PacienteSeedDTO(
                            "Paulo Rocha",
                            "Cirurgia Bariátrica",
                            AsaClassificacao.ASA_I,
                            hoje.minusDays(300),
                            hoje.minusDays(300),
                            hoje.minusDays(50),
                            hoje.minusDays(45)
                    ),
                    FilaStatus.CONCLUIDO
            );
        };
    }
}