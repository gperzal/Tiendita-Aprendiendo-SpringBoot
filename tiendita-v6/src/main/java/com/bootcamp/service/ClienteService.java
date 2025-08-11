package com.bootcamp.service;

import com.bootcamp.dto.ClienteRequest;
import com.bootcamp.dto.ClienteResponse;

import java.util.List;

public interface ClienteService {
    List<ClienteResponse> listarTodos();
    ClienteResponse obtenerPorId(Long id);
    ClienteResponse guardar(ClienteRequest cliente);
    ClienteResponse actualizar(Long id, ClienteRequest cliente);
    void eliminar(Long id);
}
