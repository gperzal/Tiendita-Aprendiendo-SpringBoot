package com.bootcamp.security.service;

import com.bootcamp.security.dto.LoginResponse;
import com.bootcamp.security.model.Rol;
import com.bootcamp.security.model.Usuario;
import com.bootcamp.security.repository.RolRepository;
import com.bootcamp.security.repository.UsuarioRepository;
import com.bootcamp.security.jwt.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.stream.Collectors;

import static com.bootcamp.security.model.NombreRol.ROLE_USER;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepo;
    private final RolRepository rolRepo;
    private final PasswordEncoder encoder;
    private final JwtUtils jwtUtils;

    // ✅ Registro: crea usuario con rol USER
    public void registrar(String nombre, String email, String password) {
        if (usuarioRepo.existsByEmail(email)) {
            throw new RuntimeException("El email ya está registrado"); // Puedes usar EmailAlreadyUsedException
        }

        Rol rolUsuario = rolRepo.findByNombre(ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Rol ROLE_USER no encontrado"));

        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setNombre(nombre);
        nuevoUsuario.setEmail(email);
        nuevoUsuario.setPassword(encoder.encode(password));
        nuevoUsuario.setRoles(Collections.singleton(rolUsuario));

        usuarioRepo.save(nuevoUsuario);
    }

    // ✅ Login: valida credenciales y genera LoginResponse con token
    public LoginResponse login(String email, String password) {
        Usuario usuario = usuarioRepo.findByEmail(email)
                .orElseThrow(() -> new BadCredentialsException("Credenciales inválidas"));

        if (!encoder.matches(password, usuario.getPassword())) {
            throw new BadCredentialsException("Credenciales inválidas");
        }

        String token = jwtUtils.generateToken(email);
        String roles = usuario.getRoles().stream()
                .map(r -> r.getNombre().name()) // Convierte a texto (USER, ADMIN)
                .collect(Collectors.joining(","));

        return new LoginResponse(usuario.getNombre(), usuario.getEmail(), token, roles);
    }
}
