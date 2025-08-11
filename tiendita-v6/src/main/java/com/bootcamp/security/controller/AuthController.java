package com.bootcamp.security.controller;

import com.bootcamp.security.dto.LoginRequest;
import com.bootcamp.security.dto.LoginResponse;
import com.bootcamp.security.dto.RegisterRequest;
import com.bootcamp.security.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    // ✅ Registro: retorna 201 sin token (solo crea usuario)
    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody RegisterRequest request) {
        authService.registrar(request.nombre(), request.email(), request.password());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // ✅ Login: retorna 200 con LoginResponse (nombre, email, token, roles)
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        LoginResponse response = authService.login(request.email(), request.password());
        return ResponseEntity.ok(response);
    }
}
