package com.bodegas.dto;

import java.util.List;
import com.bodegas.entity.Bodega;

public record BodegaResponse(Long id, String nombre, String ubicacion, List<ProductoResponse> productos) {

    public static BodegaResponse desde(Bodega bodega) {
        List<ProductoResponse> productos = bodega.getProductos().stream()
                .map(ProductoResponse::desde)
                .toList();
        return new BodegaResponse(bodega.getId(), bodega.getNombre(), bodega.getUbicacion(), productos);
    }
}
