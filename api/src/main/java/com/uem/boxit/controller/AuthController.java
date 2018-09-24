package com.uem.boxit.controller;

import com.uem.boxit.config.property.BoxItApiProperty;
import com.uem.boxit.dto.EmailDTO;
import com.uem.boxit.dto.UsernameDTO;
import com.uem.boxit.exception.ObjectNotFoundException;
import com.uem.boxit.mail.SendGridEmailService;
import com.uem.boxit.model.Cliente;
import com.uem.boxit.service.AuthService;
import com.uem.boxit.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;
import java.util.*;

@RestController
@RequestMapping(
        value = "/auth",
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE
)
public class AuthController {
    private static final int EXPIRATION = 60 * 24;

    @Autowired
    private AuthService authService;

    @Autowired
    private SendGridEmailService sendGridEmailService;

    @Autowired
    private BoxItApiProperty property;

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
    public ResponseEntity<?> confirmNewUser(@RequestParam String code) {
        Optional<Cliente> cliente = authService.findByConfirmationCode(code);
        if (cliente.isPresent()) {
            authService.confirmCliente(cliente.get());
            return ResponseEntity.ok().build();
        } else {
            throw new ObjectNotFoundException("Código de confirmação não encontrado");
        }
    }

    @PostMapping("/forgot_password")
    public ResponseEntity<?> requestResetPasswordCode(@RequestParam String email) {
        Optional<Cliente> cliente = authService.fincByEmail(email);
        if (cliente.isPresent()) {
            String token = UUID.randomUUID().toString();
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(new Date().getTime());
            cal.add(Calendar.MINUTE, EXPIRATION);
            Date expiration = new Date(cal.getTime().getTime());
            Cliente cli = cliente.get();
            cli.setPasswordResetCode(token);
            cli.setPasswordRestCodeExpiryDate(expiration);
            authService.updateResetToken(cli);
            String to = cli.getEmail();
            String subject = "Redefina sua senha";
            String url = property.getOriginPermitida() +
                    "reset_password?token=" + token;
            String message = "Alguém solicitou uma redefinição de senha para sua conta.\n"
                    + "Se não foi você, apenas desconsidere este email.\n"
                    + "Para redefinir sua senha, clique no link a seguir:\n\n"
                    + url
                    + "\n\n";
            sendGridEmailService.sendMail(subject, message, to);
            return ResponseEntity.ok().build();
        } else {
            throw new ObjectNotFoundException("Email não registrado!");
        }
    }

    @PostMapping("/reset_password")
    public ResponseEntity<?> resetPassword(@RequestParam String token, @RequestParam String password) {
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
}
