package com.upiiz.farmacia.repositories;

import com.upiiz.farmacia.entities.DetalleVentaEntity;
import com.upiiz.farmacia.entities.VentaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DetalleVentaRepository extends JpaRepository<DetalleVentaEntity, Long> {
    List<DetalleVentaEntity> findByVenta(VentaEntity venta);
}
