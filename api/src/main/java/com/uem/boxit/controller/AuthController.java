package com.uem.boxit.controller;

import com.uem.boxit.dto.EmailDTO;
import com.uem.boxit.dto.EnableDTO;
import com.uem.boxit.dto.UsernameDTO;
import com.uem.boxit.exception.ObjectNotFoundException;
import com.uem.boxit.model.Cliente;
import com.uem.boxit.service.AuthService;
import com.uem.boxit.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(
        value = "/auth",
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE
)
public class AuthController {
    @Autowired
    private AuthService authService;

    @Autowired
    private ClienteService clienteService;

    @PostMapping("/email")
    public ResponseEntity<Boolean> emailExist(@RequestBody EmailDTO dto) {
        Boolean exist = authService.emailExist(dto.getEmail());
        return ResponseEntity.ok(exist);
    }

    @PostMapping("/username")
    public ResponseEntity<Boolean> usernameExist(@RequestBody UsernameDTO dto) {
        Boolean exist = authService.usernameExist(dto.getUsername());
        return ResponseEntity.ok(exist);
    }

    @PostMapping("/confirmation")
    public ResponseEntity confirmNewUser(@RequestParam String code) {
        Optional<Cliente> cliente = authService.findByConfirmationCode(code);
        if (cliente.isPresent()) {
            authService.confirmCliente(cliente.get());
            return ResponseEntity.ok().build();
        } else {
            throw new ObjectNotFoundException("Código de confirmação não encontrado");
        }
    }

    @PostMapping("/forgot_password")
    public ResponseEntity requestResetPasswordCode(@RequestParam String email) {
        Optional<Cliente> cliente = authService.fincByEmail(email);
        if (cliente.isPresent()) {
            authService.sendPasswordResetToken(cliente.get());
            return ResponseEntity.ok().build();
        } else {
            throw new ObjectNotFoundException("Email não registrado!");
        }
    }

    @PostMapping("/reset_password")
    public ResponseEntity resetPassword(@RequestParam String token, @RequestParam String password) {
        Map<String, String> msg = new HashMap<>();
        Optional<Cliente> cli = authService.findByPasswordResetToken(token);
        if (cli.isPresent()) {
            Cliente c = cli.get();
            clienteService.updatePassword(c.getId(), password);
            c.setPasswordResetCode(null);
            c.setPasswordRestCodeExpiryDate(null);
            clienteService.update(c.getId(), c);
            msg.put("success", "Senha alterada com sucesso!");
            return ResponseEntity.ok(msg);
        } else {
            throw new ObjectNotFoundException("O código que você está usando não foi encontrado.");
        }
    }

    @PutMapping("/{id}/enabled")
    public ResponseEntity updateEnabled(@PathVariable Integer id, @RequestBody EnableDTO dto) {
        authService.updateEnable(id, dto.getEnabled());
        return ResponseEntity.ok().build();
    }
}
