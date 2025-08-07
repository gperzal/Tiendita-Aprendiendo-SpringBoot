// DTO para peticiones (crear/actualizar productos)
package com.bootcamp.dto;

import java.math.BigDecimal;

public record ProductoRequest(
        String nombre,
        BigDecimal precio,
        int stock
) {}