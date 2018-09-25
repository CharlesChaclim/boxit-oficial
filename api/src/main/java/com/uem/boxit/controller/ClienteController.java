package com.uem.boxit.controller;

import com.uem.boxit.dto.*;
import com.uem.boxit.event.SendConfirmationCodeEvent;
import com.uem.boxit.exception.ObjectNotFoundException;
import com.uem.boxit.model.Cliente;
import com.uem.boxit.model.Usuario;
import com.uem.boxit.service.ClienteService;
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
        value = "/cliente",
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE
)
public class ClienteController {

    @Autowired
    private ApplicationEventPublisher publisher;

    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public Page<Cliente> listAll(Pageable pageable) {
        return clienteService.getAll(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> getOne(@PathVariable Integer id) {
        Optional<Cliente> cliente = clienteService.getOne(id);
        return cliente.isPresent() ? ResponseEntity.ok(cliente.get()) : ResponseEntity.notFound().build();
    }

    @GetMapping("/filtro")
    public Page<Cliente> filtto(Pageable pageable, @RequestParam(required = false) String nome, @RequestParam(required = false) String cnpj, @RequestParam(required = false) String nomeFantasia, @RequestParam(required = true) Integer enable) {
        Page<Cliente> page = clienteService.filtrar(nome, cnpj, nomeFantasia, enable, pageable);
        if (page != null)
            return page;
        else
            throw new ObjectNotFoundException("Os argumentos não devem ser nulos");

    }

    @PostMapping
    public ResponseEntity<Cliente> create(@RequestBody NewClienteDTO dto) {
        Cliente cliente = clienteService.create(dto);
        try {
            publisher.publishEvent(new SendConfirmationCodeEvent(this, cliente.getEmail(), cliente.getConfirmCode()));
        } catch (Exception e) {
            clienteService.rollback(cliente);
            throw new ObjectNotFoundException(e.getLocalizedMessage());
        }
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{codigo}")
                .buildAndExpand(cliente.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PostMapping("/cnpj")
    public ResponseEntity<Boolean> cnpjExist(@RequestBody CnpjDTO dto) {
        Boolean exist = clienteService.cnpjExist(dto.getCnpj());
        return ResponseEntity.ok(exist);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cliente> update(@PathVariable Integer id, @RequestBody UpdateClienteDTO cliente) {
        Cliente cli = clienteService.update(id, cliente);
        return ResponseEntity.ok(cli);
    }

    @PutMapping("/{id}/cnpj")
    public ResponseEntity<Cliente> updateCnpj(@PathVariable Integer id, @RequestBody Cliente cliente) {
        Cliente cli = clienteService.updateCnpj(id, cliente);
        return ResponseEntity.ok(cli);
    }

    @PutMapping("/{id}/password")
    public ResponseEntity<Void> updatePassword(@PathVariable Integer id, @RequestBody NewPasswordDTO dto) {
        clienteService.updatePassword(id, dto.getPassword());
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/enable")
    public ResponseEntity atualizarEnable(@PathVariable Integer id, @RequestBody EnableDTO dto) {
        clienteService.atualizarEnable(id, dto.getEnabled());
        return ResponseEntity.ok().build();
    }
}
