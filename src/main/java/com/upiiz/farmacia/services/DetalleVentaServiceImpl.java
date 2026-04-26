package com.upiiz.farmacia.services;

import com.upiiz.farmacia.entities.DetalleVentaEntity;
import com.upiiz.farmacia.entities.VentaEntity;
import com.upiiz.farmacia.repositories.DetalleVentaRepository;
import com.upiiz.farmacia.repositories.VentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DetalleVentaServiceImpl implements DetalleVentaService {

    @Autowired
    private DetalleVentaRepository detalleVentaRepository;

    @Autowired
    private VentaRepository ventaRepository;

    private void recalcularTotalVenta(VentaEntity venta) {
        VentaEntity ventaCompleta = ventaRepository.findById(venta.getIdVenta()).orElse(null);
        if (ventaCompleta != null) {
            double total = detalleVentaRepository.findByVenta(ventaCompleta)
                    .stream()
                    .mapToDouble(DetalleVentaEntity::getSubtotal)
                    .sum();
            ventaCompleta.setTotal(total);
            ventaRepository.save(ventaCompleta);
        }
    }
    @Override
    public DetalleVentaEntity guardarDetalle(DetalleVentaEntity detalle) {

        double subtotal = detalle.getMedicamento().getPrecio() * detalle.getCantidad();
        detalle.setSubtotal(subtotal);

        DetalleVentaEntity guardado = detalleVentaRepository.save(detalle);


        recalcularTotalVenta(guardado.getVenta());

        return guardado;
    }

    @Override
    public List<DetalleVentaEntity> listarDetalles() {
        return detalleVentaRepository.findAll();
    }

    @Override
    public Optional<DetalleVentaEntity> actualizarDetalle(Long id, DetalleVentaEntity detalleVentaEntity) {
        Optional<DetalleVentaEntity> detalleActualizar = getDetallePorId(id);

        if (detalleActualizar.isPresent()) {
            DetalleVentaEntity detalleVentaActual = detalleActualizar.get();


            detalleVentaActual.setMedicamento(detalleVentaEntity.getMedicamento());
            detalleVentaActual.setCantidad(detalleVentaEntity.getCantidad());


            double subtotal = detalleVentaActual.getMedicamento().getPrecio() * detalleVentaActual.getCantidad();
            detalleVentaActual.setSubtotal(subtotal);

            detalleVentaRepository.save(detalleVentaActual);
            recalcularTotalVenta(detalleVentaActual.getVenta());

            return Optional.of(detalleVentaActual);
        }
        return Optional.empty();
    }

    @Override
    public void eliminarDetalle(Long id) {
        Optional<DetalleVentaEntity> detalle = detalleVentaRepository.findById(id);

        if (detalle.isPresent()) {
            VentaEntity venta = detalle.get().getVenta();
            detalleVentaRepository.deleteById(id);
            recalcularTotalVenta(venta);
        }
    }

    @Override
    public Optional<DetalleVentaEntity> getDetallePorId(Long id) {

        return detalleVentaRepository.findById(id);
    }
}