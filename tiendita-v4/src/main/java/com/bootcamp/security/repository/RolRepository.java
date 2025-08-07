package com.bootcamp.security.repository;

import com.bootcamp.security.model.Rol;
import com.bootcamp.security.model.NombreRol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolRepository extends JpaRepository<Rol, Long> {

    Optional<Rol> findByNombre(NombreRol nombre);
}
