package com.bootcamp.repository;

import com.bootcamp.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    //JpaRepository nos da CRUD completo automáticamente.
}
