package com.bootcamp.security.dto;

public record LoginRequest(
        String email,
        String password
) {}
