package com.uem.boxit.service;

import com.uem.boxit.model.Cliente;
import com.uem.boxit.repository.ClienteRepository;
import com.uem.boxit.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AuthService {

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
}
