package com.bootcamp.security.service;

import com.bootcamp.security.model.Usuario;
import java.util.Optional;

public interface UsuarioDetailsService {
    Optional<Usuario> findByEmail(String email);
}
