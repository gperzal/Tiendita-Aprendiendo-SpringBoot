package com.bootcamp.dto;

public record ClienteRequest(
        String nombre,
        String email,
        String password
) {}

//  No queremos que el cliente nos mande el id, y mucho menos nos devuelva password en la respuesta.