package com.bootcamp.mapper;

import com.bootcamp.dto.ClienteRequest;
import com.bootcamp.dto.ClienteResponse;
import com.bootcamp.model.Cliente;

public class ClienteMapper {

    // Convierte de entidad a DTO de respuesta
    public static ClienteResponse toResponse(Cliente cliente) {
        return ClienteResponse.builder()
                .id(cliente.getId())
                .nombre(cliente.getNombre())
                .email(cliente.getEmail())
                .build();
    }

    // Convierte de DTO de petición a entidad
    public static Cliente toEntity(ClienteRequest request) {
        Cliente cliente = new Cliente();
        cliente.setNombre(request.nombre());
        cliente.setEmail(request.email());
        return cliente;
    }
}
