package com.uem.boxit.event;

import com.uem.boxit.model.Funcionario;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import javax.servlet.http.HttpServletResponse;

@Getter
public class UsuarioCreateEvent extends ApplicationEvent {
    private static final long serialVersionUID = 1L;

    private HttpServletResponse response;
    private Integer id;
    private String confirmationCode;

    public UsuarioCreateEvent(Object source, Integer id, String confirmationCode, HttpServletResponse response) {
        super(source);
        this.id = id;
        this.confirmationCode = confirmationCode;
        this.response = response;
    }
}
