package com.bootcamp.security.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "roles")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private NombreRol nombre;
}
