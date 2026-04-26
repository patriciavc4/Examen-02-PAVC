package com.upiiz.farmacia.controllers;

import com.upiiz.farmacia.entities.VentaEntity;
import com.upiiz.farmacia.services.VentaServiceImpl;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/facturacion")
public class FacturacionController {

    @Autowired
    private VentaServiceImpl ventaServiceImpl;

    @GetMapping
    public String facturacion(HttpSession session, Model model) {
        if (session.getAttribute("usuario") == null) return "redirect:/auth/login";
        model.addAttribute("ventas", ventaServiceImpl.listarVentas());
        return "listado-facturacion";
    }

    @GetMapping("/generar/{id}")
    public String generarFactura(@PathVariable Long id, HttpSession session, Model model) {
        if (session.getAttribute("usuario") == null) return "redirect:/auth/login";
        Optional<VentaEntity> venta = ventaServiceImpl.getVentaPorId(id);
        if (venta.isPresent()) {
            model.addAttribute("venta", venta.get());
            return "factura";
        }
        return "redirect:/facturacion";
    }
}