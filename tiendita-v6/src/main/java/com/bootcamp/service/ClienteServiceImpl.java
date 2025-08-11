package com.bootcamp.service;

import com.bootcamp.dto.ClienteRequest;
import com.bootcamp.dto.ClienteResponse;
import com.bootcamp.mapper.ClienteMapper;
import com.bootcamp.model.Cliente;
import com.bootcamp.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;

    @Override
    public List<ClienteResponse> listarTodos() {
        return clienteRepository.findAll().stream()
                .map(ClienteMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ClienteResponse obtenerPorId(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        return ClienteMapper.toResponse(cliente);
    }

    @Override
    public ClienteResponse guardar(ClienteRequest request) {
        Cliente cliente = ClienteMapper.toEntity(request);
        return ClienteMapper.toResponse(clienteRepository.save(cliente));
    }

    @Override
    public ClienteResponse actualizar(Long id, ClienteRequest request) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        cliente.setNombre(request.nombre());
        cliente.setEmail(request.email());
        return ClienteMapper.toResponse(clienteRepository.save(cliente));
    }

    @Override
    public void eliminar(Long id) {
        clienteRepository.deleteById(id);
    }
}
