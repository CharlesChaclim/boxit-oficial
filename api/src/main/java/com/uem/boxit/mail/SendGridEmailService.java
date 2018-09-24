package com.uem.boxit.mail;

import com.sendgrid.*;
import com.uem.boxit.exception.MailException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SendGridEmailService {

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
            Response response = sg.api(request);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new MailException("Falha ao enviar email de confirmação");
        }
    }
}
