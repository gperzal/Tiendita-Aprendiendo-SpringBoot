package com.bootcamp.controller;

import com.bootcamp.dto.ProductoRequest;
import com.bootcamp.dto.ProductoResponse;
import com.bootcamp.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private ProductoService service;

    @PostMapping
    public ResponseEntity<ProductoResponse> crear(@RequestBody ProductoRequest request) {
        return ResponseEntity.ok(service.crearProducto(request));
    }

    @GetMapping
    public ResponseEntity<List<ProductoResponse>> listar() {
        return ResponseEntity.ok(service.listarProductos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoResponse> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerProducto(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductoResponse> actualizar(@PathVariable Long id, @RequestBody ProductoRequest request) {
        return ResponseEntity.ok(service.actualizarProducto(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminarProducto(id);
        return ResponseEntity.noContent().build();
    }
}
