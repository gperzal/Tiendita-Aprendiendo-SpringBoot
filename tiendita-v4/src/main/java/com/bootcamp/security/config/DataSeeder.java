package com.bootcamp.security.config;

import com.bootcamp.security.model.NombreRol;
import com.bootcamp.security.model.Rol;
import com.bootcamp.security.repository.RolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final RolRepository rolRepo;

    @Override
    public void run(String... args) {
        inicializarRoles();
    }

    private void inicializarRoles() {
        crearRolSiNoExiste(NombreRol.ROLE_USER);
        crearRolSiNoExiste(NombreRol.ROLE_ADMIN);

        System.out.println("✅ Roles iniciales verificados/creados.");
    }

    private void crearRolSiNoExiste(NombreRol nombreRol) {
        rolRepo.findByNombre(nombreRol).orElseGet(() -> {
            System.out.println("🔹 Creando rol: " + nombreRol);
            return rolRepo.save(new Rol(null, nombreRol));
        });
    }
}
