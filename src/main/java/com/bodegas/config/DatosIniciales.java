package com.bodegas.config;

import java.math.BigDecimal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.bodegas.entity.Bodega;
import com.bodegas.entity.Producto;
import com.bodegas.repository.BodegaRepository;
import com.bodegas.repository.ProductoRepository;

@Component
public class DatosIniciales implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DatosIniciales.class);

    private final BodegaRepository bodegaRepository;
    private final ProductoRepository productoRepository;

    public DatosIniciales(BodegaRepository bodegaRepository, ProductoRepository productoRepository) {
        this.bodegaRepository = bodegaRepository;
        this.productoRepository = productoRepository;
    }

    @Override
    @Transactional
    public void run(String... args) {
        if (bodegaRepository.count() > 0) {
            return;
        }

        Bodega norte = bodegaRepository.save(new Bodega("Bodega Norte", "Calle 170 # 45-20, Bogota"));
        Bodega sur = bodegaRepository.save(new Bodega("Bodega Sur", "Autopista Sur # 60-15, Bogota"));

        guardarProducto(norte, new Producto("Computador portatil", "Portatil de 15 pulgadas, 16 GB RAM",
                new BigDecimal("2500000"), 15));
        guardarProducto(norte, new Producto("Monitor", "Monitor LED de 27 pulgadas",
                new BigDecimal("750000"), 20));
        guardarProducto(sur, new Producto("Impresora", "Impresora multifuncional laser",
                new BigDecimal("1200000"), 8));

        log.info("Datos de prueba cargados: {} bodegas y {} productos",
                bodegaRepository.count(), productoRepository.count());
    }

    private void guardarProducto(Bodega bodega, Producto producto) {
        bodega.agregarProducto(producto);
        productoRepository.save(producto);
    }
}

