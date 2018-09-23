package com.uem.boxit.controller;

import com.uem.boxit.dto.CnpjDTO;
import com.uem.boxit.dto.NewClienteDTO;
import com.uem.boxit.event.UsuarioCreateEvent;
import com.uem.boxit.model.Cliente;
import com.uem.boxit.dto.NewPasswordDTO;
import com.uem.boxit.service.ClienteService;
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

    @GetMapping("/enable")
    public Page<Cliente> listAllValido(Pageable pageable) {
        return clienteService.getAllValido(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> getOne(@PathVariable Integer id){
        Optional<Cliente> cliente = clienteService.getOne(id);
        return cliente.isPresent() ? ResponseEntity.ok(cliente.get()) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Cliente> create(@RequestBody NewClienteDTO dto, HttpServletResponse resp){
        Cliente cliente = clienteService.create(dto);
        publisher.publishEvent(new UsuarioCreateEvent(this, cliente.getId(), cliente.getConfirmCode(), resp));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/cnpj")
    public ResponseEntity<Boolean> cnpjExist(@RequestBody CnpjDTO dto){
        Boolean exist = clienteService.cnpjExist(dto.getCnpj());
        return ResponseEntity.ok(exist);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cliente> update(@PathVariable Integer id, @RequestBody Cliente cliente){
        Cliente cli = clienteService.update(id, cliente);
        return ResponseEntity.ok(cli);
    }

    @PutMapping("/{id}/cnpj")
    public ResponseEntity<Cliente> updateCnpj(@PathVariable Integer id, @RequestBody Cliente cliente){
        Cliente cli = clienteService.updateCnpj(id, cliente);
        return ResponseEntity.ok(cli);
    }

    @PutMapping("/{id}/password")
    public ResponseEntity<Void> updatePassword(@PathVariable Integer id, @RequestBody NewPasswordDTO dto) {
        clienteService.updatePassword(id, dto.getPassword());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deactivate(@PathVariable Integer id) {
        clienteService.delete(id);
        return ResponseEntity.ok().build();
    }
}
