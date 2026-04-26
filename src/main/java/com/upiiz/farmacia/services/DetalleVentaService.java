package com.upiiz.farmacia.services;

import com.upiiz.farmacia.entities.DetalleVentaEntity;
import java.util.List;
import java.util.Optional;

public interface DetalleVentaService {
    DetalleVentaEntity guardarDetalle(DetalleVentaEntity detalle);
    List<DetalleVentaEntity> listarDetalles();
    public Optional<DetalleVentaEntity> actualizarDetalle(Long id, DetalleVentaEntity detalleVentaEntity);
    void eliminarDetalle(Long id);
    public Optional<DetalleVentaEntity> getDetallePorId(Long id);
}