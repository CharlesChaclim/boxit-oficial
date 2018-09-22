package com.uem.boxit.event.listener;

import com.uem.boxit.event.FuncionarioCreateEvent;
import com.uem.boxit.model.Funcionario;
import org.springframework.context.ApplicationListener;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import java.net.URI;

public class FuncionarioCreateListener implements ApplicationListener<FuncionarioCreateEvent> {
    @Override
    public void onApplicationEvent(FuncionarioCreateEvent e) {
        Integer id = e.getFuncionarioId();
        HttpServletResponse response = e.getResponse();
        adicionarHeaderLocation(response, id);
    }

    private void adicionarHeaderLocation(HttpServletResponse response, Integer codigo) {
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{codigo}")
                .buildAndExpand(codigo).toUri();
        response.setHeader("Location", uri.toASCIIString());
    }

}
