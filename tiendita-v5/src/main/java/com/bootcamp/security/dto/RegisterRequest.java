package com.bootcamp.security.dto;

public record RegisterRequest(
        String nombre,
        String email,
        String password
) {}
