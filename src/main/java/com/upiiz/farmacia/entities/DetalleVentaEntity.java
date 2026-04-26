package com.upiiz.farmacia.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "detalles_ventas")
public class DetalleVentaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle")
    private Long idDetalle;

    @ManyToOne
    @JoinColumn(name = "id_venta", referencedColumnName = "id_venta")
    private VentaEntity venta;

    @ManyToOne
    @JoinColumn(name = "id_medicamento", referencedColumnName = "id_medicamento")
    private MedicamentoEntity medicamento;

    @Column(name = "cantidad")
    private int cantidad;

    @Column(name = "subtotal")
    private double subtotal;

    public DetalleVentaEntity() {
    }

    public DetalleVentaEntity(int cantidad, Long idDetalle, MedicamentoEntity medicamento, double subtotal, VentaEntity venta) {
        this.cantidad = cantidad;
        this.idDetalle = idDetalle;
        this.medicamento = medicamento;
        this.subtotal = subtotal;
        this.venta = venta;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Long getIdDetalle() {
        return idDetalle;
    }

    public void setIdDetalle(Long idDetalle) {
        this.idDetalle = idDetalle;
    }

    public MedicamentoEntity getMedicamento() {
        return medicamento;
    }

    public void setMedicamento(MedicamentoEntity medicamento) {
        this.medicamento = medicamento;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public VentaEntity getVenta() {
        return venta;
    }

    public void setVenta(VentaEntity venta) {
        this.venta = venta;
    }
}