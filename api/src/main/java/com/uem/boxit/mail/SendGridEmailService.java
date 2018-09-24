package com.uem.boxit.mail;

import com.sendgrid.*;
import com.uem.boxit.exception.MailException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SendGridEmailService {
    private static final Logger logger = LoggerFactory.getLogger(SendGridEmailService.class);

    @Autowired
    private SendGrid sg;

    public void sendMail(String subject, String message, String dest) {
        Email from = new Email("boxit@gmail.com");
        Email to = new Email(dest);
        Content content = new Content("text/plain", message);
        Mail mail = new Mail(from, subject, to, content);
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            sg.api(request);
        } catch (Exception e) {
            logger.info(e.getMessage());
            throw new MailException("Falha ao enviar email de confirmação");
        }
    }
}
