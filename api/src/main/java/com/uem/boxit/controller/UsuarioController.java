package com.uem.boxit.controller;

import com.uem.boxit.model.Usuario;
import com.uem.boxit.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(
        value = "/usuario",
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE
)
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<?> listAll(Pageable pageable) {
        Page<Usuario> page = usuarioService.getAll(pageable);
        if (page.getTotalElements() > 0)
            return ResponseEntity.ok(page);
        else
            return ResponseEntity.notFound().build();
    }
}
