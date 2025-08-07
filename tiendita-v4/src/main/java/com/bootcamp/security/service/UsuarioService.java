package com.bootcamp.security.service;

import com.bootcamp.security.model.Usuario;

import java.util.Optional;

public interface UsuarioService {
    Optional<Usuario> findByEmail(String email);
}
