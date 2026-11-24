package com.bodegas.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.bodegas.dto.BodegaRequest;
import com.bodegas.dto.BodegaResponse;
import com.bodegas.entity.Bodega;
import com.bodegas.repository.BodegaRepository;

@Service
public class BodegaService {

    private final BodegaRepository bodegaRepository;

    public BodegaService(BodegaRepository bodegaRepository) {
        this.bodegaRepository = bodegaRepository;
    }

    @Transactional
    public BodegaResponse registrar(BodegaRequest request) {
        Bodega bodega = new Bodega(request.nombre().trim(), request.ubicacion().trim());
        return BodegaResponse.desde(bodegaRepository.save(bodega));
    }

    @Transactional(readOnly = true)
    public List<BodegaResponse> consultarTodas() {
        return bodegaRepository.findAllConProductos().stream()
                .map(BodegaResponse::desde)
                .toList();
    }
}
