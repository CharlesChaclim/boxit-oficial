package com.uem.boxit.event;

import com.uem.boxit.model.Funcionario;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import javax.servlet.http.HttpServletResponse;

@Getter
public class FuncionarioCreateEvent extends ApplicationEvent {
    private static final long serialVersionUID = 1L;

    private HttpServletResponse response;
    private Funcionario funcionario;

    public FuncionarioCreateEvent(Funcionario f, HttpServletResponse response) {
        super(f);
        this.funcionario = f;
        this.response = response;
    }

    public Integer getFuncionarioId() {
        return this.funcionario.getId();
    }
    public String getFuncionarioConfirmationCode() {
        return this.funcionario.getConfirmCode();
    }
}
