package com.fiap.sigefi.service;

import com.fiap.sigefi.entities.Paciente;
import org.springframework.stereotype.Service;

@Service
public class NotificacaoService {

    public void enviarAlertaLA(Paciente paciente) {

        // 🔔 Simulação de envio de e-mail
        System.out.println("📧 NOTIFICAÇÃO DE LA PRESTES A VENCER");
        System.out.println("Paciente: " + paciente.getNome());
        System.out.println("Procedimento: " + paciente.getProcedimento());
        System.out.println("Vencimento LA: " + paciente.getDataVencimentoLA());
        System.out.println("Mensagem: LA vencerá em menos de 10 dias.");
        System.out.println("-----------------------------------");
    }
}
