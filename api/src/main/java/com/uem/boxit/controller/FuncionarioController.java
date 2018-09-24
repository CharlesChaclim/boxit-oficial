package com.uem.boxit.controller;

import com.uem.boxit.dto.CpfDTO;
import com.uem.boxit.dto.NewFuncionarioDTO;
import com.uem.boxit.dto.NewPasswordDTO;
import com.uem.boxit.exception.ObjectNotFoundException;
import com.uem.boxit.model.Funcionario;
import com.uem.boxit.service.FuncionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
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

    @GetMapping("/filter")
    public Page<Funcionario> filter(Pageable pageable, @RequestParam(required = false) String nome, @RequestParam(required = false) String cpf, @RequestParam(required = false) String email) {
        Page<Funcionario> page = funcionarioService.filter(nome, cpf, email, pageable);
        if (page != null)
            return page;
        else {
            throw new ObjectNotFoundException("Os argumentos não devem ser nulos");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Funcionario> getOne(@PathVariable Integer id) {
        Optional<Funcionario> f = funcionarioService.getOne(id);
        return f.isPresent() ? ResponseEntity.ok(f.get()) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Funcionario> create(@RequestBody NewFuncionarioDTO dto) {
        Funcionario f = funcionarioService.create(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{codigo}")
                .buildAndExpand(f.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PostMapping("/cpf")
    public ResponseEntity<Boolean> cpfExist(@RequestBody CpfDTO dto) {
        Boolean exist = funcionarioService.cpfExist(dto.getCpf());
        return ResponseEntity.ok(exist);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Funcionario> update(@PathVariable Integer id, @RequestBody Funcionario f) {
        Funcionario func = funcionarioService.update(id, f);
        return ResponseEntity.ok(func);
    }

    @PutMapping("/{id}/password")
    public ResponseEntity<Void> updatePassword(@PathVariable Integer id, @RequestBody NewPasswordDTO dto) {
        funcionarioService.updatePassword(id, dto.getPassword());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deactivate(@PathVariable Integer id) {
        funcionarioService.delete(id);
        return ResponseEntity.ok().build();
    }
}
