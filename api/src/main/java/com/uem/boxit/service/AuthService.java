package com.uem.boxit.service;

import com.uem.boxit.config.property.BoxItApiProperty;
import com.uem.boxit.mail.SendGridEmailService;
import com.uem.boxit.model.Cliente;
import com.uem.boxit.repository.ClienteRepository;
import com.uem.boxit.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AuthService {
    private static final int EXPIRATION = 60 * 24;

    @Autowired
    private SendGridEmailService sendGridEmailService;

    @Autowired
    private BoxItApiProperty property;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    public Boolean usernameExist(String username) {
        return usuarioRepository.findByUsername(username).isPresent();
    }

    public Boolean emailExist(String email) {
        return usuarioRepository.findByEmail(email).isPresent();
    }

    public Optional<Cliente> fincByEmail(String email) {
        return clienteRepository.findByEmail(email);
    }

    public void updateResetToken(Cliente cli) {
        clienteRepository.save(cli);
    }

    public Optional<Cliente> findByConfirmationCode(String code) {
        return clienteRepository.findByConfirmCode(code);
    }

    public Optional<Cliente> findByPasswordResetToken(String code) {
        return clienteRepository.findByPasswordResetCode(code);
    }

    public void confirmCliente(Cliente cliente) {
        cliente.setConfirmCode(null);
        cliente.setEnable(true);
        clienteRepository.save(cliente);
    }

    @Scheduled(cron = "0 * * * * *")
    public void removeExpiredPasswordResetToken() {
        List<Cliente> clientes = clienteRepository.findByPasswordRestCodeExpiryDateLessThanEqual(new Date());
        clientes.forEach(e -> {
            e.setPasswordRestCodeExpiryDate(null);
            e.setPasswordResetCode(null);
            clienteRepository.save(e);
        });
    }

    public void sendPasswordResetToken(Cliente cliente) {
        String token = UUID.randomUUID().toString();
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(new Date().getTime());
        cal.add(Calendar.MINUTE, EXPIRATION);
        Date expiration = new Date(cal.getTime().getTime());
        cliente.setPasswordResetCode(token);
        cliente.setPasswordRestCodeExpiryDate(expiration);
        updateResetToken(cliente);
        String to = cliente.getEmail();
        String subject = "Redefina sua senha";
        String url = property.getOriginPermitida() +
                "/reset_password?token=" + token;
        String message = "Alguém solicitou uma redefinição de senha para sua conta.\n"
                + "Se não foi você, apenas desconsidere este email.\n"
                + "Para redefinir sua senha, clique no link a seguir:\n\n"
                + url
                + "\n\n";
        sendGridEmailService.sendMail(subject, message, to);
    }
}
