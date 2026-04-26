package com.upiiz.farmacia.services;

import com.upiiz.farmacia.entities.VentaEntity;
import com.upiiz.farmacia.repositories.VentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class VentaServiceImpl implements VentaService {

    @Autowired
    private VentaRepository ventaRepository;

    @Override
    public VentaEntity guardarVenta(VentaEntity venta) {
        venta.setFechaVenta(new Date());
        return ventaRepository.save(venta);
    }

    @Override
    public List<VentaEntity> listarVentas() {
        return ventaRepository.findAll();
    }

    @Override
    public Optional<VentaEntity> actualizarVenta(Long id, VentaEntity venta) {
        return ventaRepository.findById(id).map(ventaExistente -> {
            ventaExistente.setNombreCliente(venta.getNombreCliente());
            return ventaRepository.save(ventaExistente);
        });
    }

    @Override
    public void eliminarVenta(Long idVenta) {
        if (ventaRepository.existsById(idVenta)) {
            ventaRepository.deleteById(idVenta);
        }
    }

    @Override
    public Optional<VentaEntity> getVentaPorId(Long idVenta) {

        return ventaRepository.findById(idVenta);
    }
}