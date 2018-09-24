package com.uem.boxit.event.listener;

import com.uem.boxit.config.property.BoxItApiProperty;
import com.uem.boxit.event.SendConfirmationCodeEvent;
import com.uem.boxit.mail.SendGridEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class SendConfirmationCodeListener implements ApplicationListener<SendConfirmationCodeEvent> {

    @Autowired
    private BoxItApiProperty property;

    @Autowired
    private SendGridEmailService sendGridEmailService;

    @Override
    public void onApplicationEvent(SendConfirmationCodeEvent e) {
        String to = e.getEmail();
        String subject = "Confirme sua conta";
        String url = property.getOriginPermitida() + "/confirmation?code=" + e.getConfirmationCode();
        String message = "Seu cadastro está quase completo!\n"
                + "Clique no link abaixo para confirmar sua conta e finalizar o cadastro:\n"
                + url
                + "\n\n";
        sendGridEmailService.sendMail(subject, message, to);
    }
}
