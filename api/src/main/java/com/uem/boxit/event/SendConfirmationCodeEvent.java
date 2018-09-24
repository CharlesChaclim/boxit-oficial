package com.uem.boxit.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class SendConfirmationCodeEvent extends ApplicationEvent {
    private static final long serialVersionUID = 1L;
    private String confirmationCode;
    private String email;

    public SendConfirmationCodeEvent(Object source, String email, String confirmationCode) {
        super(source);
        this.confirmationCode = confirmationCode;
        this.email = email;
    }
}
