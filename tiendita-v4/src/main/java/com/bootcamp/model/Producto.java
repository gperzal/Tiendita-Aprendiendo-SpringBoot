package com.bootcamp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "productos")
@Getter @Setter
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre del producto es obligatorio")
    private String nombre;

    @NotNull(message = "El precio es obligatorio")
    @Column(precision = 10, scale = 2)
    private BigDecimal precio;


    @NotNull(message = "El stock es obligatorio")
    private Integer stock;
}
