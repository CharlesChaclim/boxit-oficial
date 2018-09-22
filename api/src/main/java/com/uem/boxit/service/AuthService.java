package com.uem.boxit.service;

import com.uem.boxit.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Boolean usernameExist(String username) {
        return usuarioRepository.findByUsername(username).isPresent();
    }

    public Boolean emailExist(String email) {
        return usuarioRepository.findByEmail(email).isPresent();
    }
}
