package com.bootcamp.dto;

import lombok.Builder;

@Builder
public record ClienteResponse(
        Long id,
        String nombre,
        String email
) {}
