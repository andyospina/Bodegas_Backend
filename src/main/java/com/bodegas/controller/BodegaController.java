package com.bodegas.controller;

import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.bodegas.dto.BodegaRequest;
import com.bodegas.dto.BodegaResponse;
import com.bodegas.service.BodegaService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/bodegas")
public class BodegaController {

    private final BodegaService bodegaService;

    public BodegaController(BodegaService bodegaService) {
        this.bodegaService = bodegaService;
    }
    @PostMapping
    public ResponseEntity<BodegaResponse> registrar(@Valid @RequestBody BodegaRequest request) {
        BodegaResponse creada = bodegaService.registrar(request);
        return ResponseEntity.created(URI.create("/bodegas/" + creada.id())).body(creada);
    }
    @GetMapping
    public List<BodegaResponse> consultar() {
        return bodegaService.consultarTodas();
    }
}
