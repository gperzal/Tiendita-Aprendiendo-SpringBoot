package com.bootcamp.security.dto;

public record LoginResponse(
        String nombre,
        String email,
        String token,
        String roles
) {}
