package com.bodegas.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.bodegas.entity.Bodega;

@Repository
public interface BodegaRepository extends JpaRepository<Bodega, Long> {

    @Query("SELECT DISTINCT b FROM Bodega b LEFT JOIN FETCH b.productos ORDER BY b.id")
    List<Bodega> findAllConProductos();
}
