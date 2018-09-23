package com.uem.boxit.event.listener;

import com.uem.boxit.event.UsuarioCreateEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import java.net.URI;

public class UsuarioCreateListener implements ApplicationListener<UsuarioCreateEvent> {
    @Override
    public void onApplicationEvent(UsuarioCreateEvent e) {
        Integer id = e.getId();
        String confirmationCode = e.getConfirmationCode();
        HttpServletResponse response = e.getResponse();
        adicionarHeaderLocation(response, id);
    }

    private void adicionarHeaderLocation(HttpServletResponse response, Integer codigo) {
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{codigo}")
                .buildAndExpand(codigo).toUri();
        response.setHeader("Location", uri.toASCIIString());
    }

}
