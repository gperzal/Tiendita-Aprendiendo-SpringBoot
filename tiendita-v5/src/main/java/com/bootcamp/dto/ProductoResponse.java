// DTO para respuestas (consultar productos)
package com.bootcamp.dto;

import java.math.BigDecimal;

public record ProductoResponse(
        Long id,
        String nombre,
        BigDecimal precio,
        int stock
) {}