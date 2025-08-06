package com.bootcamp.service;

import com.bootcamp.dto.ProductoRequest;
import com.bootcamp.dto.ProductoResponse;

import java.util.List;

public interface ProductoService {
    ProductoResponse crearProducto(ProductoRequest request);
    ProductoResponse obtenerProducto(Long id);
    List<ProductoResponse> listarProductos();
    ProductoResponse actualizarProducto(Long id, ProductoRequest request);
    void eliminarProducto(Long id);
}