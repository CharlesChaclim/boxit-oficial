package com.uem.boxit.controller;

import com.uem.boxit.dto.NewFuncionarioDTO;
import com.uem.boxit.dto.NewPasswordDTO;
import com.uem.boxit.event.FuncionarioCreateEvent;
import com.uem.boxit.model.Funcionario;
import com.uem.boxit.service.FuncionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@RestController
@RequestMapping(
        value = "/funcionario",
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE
)
public class FuncionarioController {

    @Autowired
    private ApplicationEventPublisher publisher;

    @Autowired
    private FuncionarioService funcionarioService;

    @GetMapping
    public Page<Funcionario> listAll(Pageable pageable) {
        return funcionarioService.getAll(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Funcionario> getOne(@PathVariable Integer id) {
        Optional<Funcionario> f = funcionarioService.getOne(id);
        return f.isPresent() ? ResponseEntity.ok(f.get()) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Funcionario> create(@RequestBody NewFuncionarioDTO dto, HttpServletResponse resp) {
        Funcionario f = funcionarioService.create(dto);
        publisher.publishEvent(new FuncionarioCreateEvent(f, resp));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Funcionario> update(@PathVariable Integer id, @RequestBody Funcionario f) {
        Funcionario func = funcionarioService.update(id, f);
        return ResponseEntity.ok(func);
    }

    @PutMapping("/{id}/password")
    public ResponseEntity<?> updatePassword(@PathVariable Integer id, @RequestBody NewPasswordDTO dto) {
        funcionarioService.updatePassword(id, dto.getPassword());
    }

    @DeleteMapping("/{id}")
    public void deactivate(@PathVariable Integer id) {
        funcionarioService.delete(id);
    }
}
