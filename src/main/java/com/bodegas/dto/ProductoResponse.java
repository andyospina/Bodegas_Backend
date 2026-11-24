package com.bodegas.dto;

import java.math.BigDecimal;
import com.bodegas.entity.Producto;

public record ProductoResponse(Long id, String nombre, String descripcion, BigDecimal precio, Integer cantidad) {

    public static ProductoResponse desde(Producto producto) {
        return new ProductoResponse(
                producto.getId(),
                producto.getNombre(),
                producto.getDescripcion(),
                producto.getPrecio(),
                producto.getCantidad());
    }
}
