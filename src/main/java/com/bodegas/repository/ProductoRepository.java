package com.bodegas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.bodegas.entity.Producto;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
}
