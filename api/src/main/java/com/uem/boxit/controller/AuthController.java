package com.uem.boxit.controller;

import com.uem.boxit.dto.EmailDTO;
import com.uem.boxit.dto.UsernameDTO;
import com.uem.boxit.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping(
        value = "/auth",
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE
)
public class AuthController {

    @Autowired
    private AuthService authService;

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
}
