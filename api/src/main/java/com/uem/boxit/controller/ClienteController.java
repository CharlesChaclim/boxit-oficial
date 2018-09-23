package com.uem.boxit.controller;

import com.uem.boxit.model.Cliente;
import com.uem.boxit.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(
        value = "/cliente",
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE
)
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public Page<Cliente> listAll(Pageable pageable) {
        return clienteService.getAll(pageable);
    }
}
