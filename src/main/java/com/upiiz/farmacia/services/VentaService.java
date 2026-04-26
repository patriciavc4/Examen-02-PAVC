package com.upiiz.farmacia.services;

import com.upiiz.farmacia.entities.VentaEntity;
import java.util.List;
import java.util.Optional;

public interface VentaService {
    VentaEntity guardarVenta(VentaEntity venta);
    List<VentaEntity> listarVentas();
    public Optional<VentaEntity> actualizarVenta(Long id, VentaEntity venta);
    void eliminarVenta(Long idVenta);
    public Optional<VentaEntity> getVentaPorId(Long idVenta);
}