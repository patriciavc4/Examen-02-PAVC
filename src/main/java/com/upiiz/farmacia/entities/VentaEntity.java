package com.upiiz.farmacia.entities;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "ventas")
public class VentaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_venta")
    private Long idVenta;

    @Column(name = "fecha_venta")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaVenta;

    @Column(name = "nombre_cliente")
    private String nombreCliente;

    @Column(name = "total")
    private Double total = 0.0;

    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<DetalleVentaEntity> detalles = new ArrayList<>();

    public VentaEntity() {}

    public VentaEntity(List<DetalleVentaEntity> detalles, Date fechaVenta, Long idVenta, String nombreCliente, Double total) {
        this.detalles = detalles;
        this.fechaVenta = fechaVenta;
        this.idVenta = idVenta;
        this.nombreCliente = nombreCliente;
        this.total = total;
    }

    public Long getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(Long idVenta) {
        this.idVenta = idVenta;
    }

    public Date getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(Date fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public List<DetalleVentaEntity> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleVentaEntity> detalles) {
        this.detalles = detalles;
    }
}