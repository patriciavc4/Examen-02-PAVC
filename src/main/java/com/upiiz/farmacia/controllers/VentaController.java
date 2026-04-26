package com.upiiz.farmacia.controllers;

import com.upiiz.farmacia.entities.DetalleVentaEntity;
import com.upiiz.farmacia.entities.VentaEntity;
import com.upiiz.farmacia.services.DetalleVentaServiceImpl;
import com.upiiz.farmacia.services.MedicamentoServiceImpl;
import com.upiiz.farmacia.services.VentaServiceImpl;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/ventas")
public class VentaController {

    @Autowired
    private VentaServiceImpl ventaServiceImpl;

    @Autowired
    private DetalleVentaServiceImpl detalleVentaServiceImpl;

    @Autowired
    private MedicamentoServiceImpl medicamentoServiceImpl;

    @GetMapping
    public String ventas(HttpSession session, Model model) {
        if (session.getAttribute("usuario") == null) return "redirect:/auth/login";
        model.addAttribute("ventas", ventaServiceImpl.listarVentas());
        return "listado-ventas";
    }

    @GetMapping("/nueva")
    public String mostrarFormularioAgregar(HttpSession session, Model model) {
        if (session.getAttribute("usuario") == null) return "redirect:/auth/login";
        model.addAttribute("venta", new VentaEntity());
        return "agregar-venta";
    }

    @PostMapping
    public String agregarVenta(HttpSession session, @ModelAttribute VentaEntity ventaEntity) {
        if (session.getAttribute("usuario") == null) return "redirect:/auth/login";
        ventaServiceImpl.guardarVenta(ventaEntity);
        return "redirect:/ventas";
    }

    @GetMapping("/actualizar/{id}")
    public String mostrarFormularioActualizar(@PathVariable long id, HttpSession session, Model model) {
        if (session.getAttribute("usuario") == null) return "redirect:/auth/login";
        Optional<VentaEntity> venta = ventaServiceImpl.getVentaPorId(id);
        if (venta.isPresent()) {
            model.addAttribute("venta", venta.get());
            return "actualizar-venta";
        }
        return "redirect:/ventas";
    }

    @PostMapping("/actualizar")
    public String actualizarVenta(HttpSession session, @ModelAttribute VentaEntity ventaEntity) {
        if (session.getAttribute("usuario") == null) return "redirect:/auth/login";
        ventaServiceImpl.actualizarVenta(ventaEntity.getIdVenta(), ventaEntity);
        return "redirect:/ventas";
    }

    @GetMapping("/eliminar/{id}")
    public String mostrarConfirmacionEliminacion(@PathVariable long id, HttpSession session, Model model) {
        if (session.getAttribute("usuario") == null) return "redirect:/auth/login";
        Optional<VentaEntity> venta = ventaServiceImpl.getVentaPorId(id);
        if (venta.isPresent()) {
            model.addAttribute("venta", venta.get());
            return "eliminar-venta";
        }
        return "redirect:/ventas";
    }

    @PostMapping("/eliminar")
    public String eliminarVenta(HttpSession session, @ModelAttribute VentaEntity ventaEntity) {
        if (session.getAttribute("usuario") == null) return "redirect:/auth/login";
        ventaServiceImpl.eliminarVenta(ventaEntity.getIdVenta());
        return "redirect:/ventas";
    }


    @GetMapping("/detalles/{idVenta}")
    public String verDetalles(@PathVariable long idVenta, HttpSession session, Model model) {
        if (session.getAttribute("usuario") == null) return "redirect:/auth/login";
        Optional<VentaEntity> venta = ventaServiceImpl.getVentaPorId(idVenta);
        if (venta.isPresent()) {
            model.addAttribute("venta", venta.get());
            model.addAttribute("detalles", venta.get().getDetalles());
            return "listado-detalles";
        }
        return "redirect:/ventas";
    }

    @GetMapping("/detalles/nuevo/{idVenta}")
    public String mostrarFormularioAgregarDetalle(@PathVariable long idVenta, HttpSession session, Model model) {
        if (session.getAttribute("usuario") == null) return "redirect:/auth/login";
        Optional<VentaEntity> venta = ventaServiceImpl.getVentaPorId(idVenta);
        if (venta.isPresent()) {
            DetalleVentaEntity detalle = new DetalleVentaEntity();
            detalle.setVenta(venta.get());
            model.addAttribute("detalle", detalle);
            model.addAttribute("medicamentos", medicamentoServiceImpl.listarMedicamentos());
            return "agregar-detalle";
        }
        return "redirect:/ventas";
    }

    @PostMapping("/detalles")
    public String agregarDetalle(HttpSession session,
                                 @ModelAttribute DetalleVentaEntity detalle,
                                 @RequestParam("medicamento.idMedicamento") Long idMedicamento) {
        if (session.getAttribute("usuario") == null) return "redirect:/auth/login";

        medicamentoServiceImpl.getMedicamentoPorId(idMedicamento)
                .ifPresent(detalle::setMedicamento);

        detalleVentaServiceImpl.guardarDetalle(detalle);
        return "redirect:/ventas/detalles/" + detalle.getVenta().getIdVenta();
    }
    @GetMapping("/detalles/actualizar/{id}")
    public String mostrarFormularioActualizarDetalle(@PathVariable long id, HttpSession session, Model model) {
        if (session.getAttribute("usuario") == null) return "redirect:/auth/login";
        Optional<DetalleVentaEntity> detalle = detalleVentaServiceImpl.getDetallePorId(id);
        if (detalle.isPresent()) {
            model.addAttribute("detalle", detalle.get());
            model.addAttribute("medicamentos", medicamentoServiceImpl.listarMedicamentos());
            return "actualizar-detalle";
        }
        return "redirect:/ventas";
    }

    @PostMapping("/detalles/actualizar")
    public String actualizarDetalle(HttpSession session,
                                    @ModelAttribute DetalleVentaEntity detalle,
                                    @RequestParam("medicamento.idMedicamento") Long idMedicamento) {
        if (session.getAttribute("usuario") == null) return "redirect:/auth/login";

        medicamentoServiceImpl.getMedicamentoPorId(idMedicamento)
                .ifPresent(detalle::setMedicamento);

        detalleVentaServiceImpl.actualizarDetalle(detalle.getIdDetalle(), detalle);
        return "redirect:/ventas/detalles/" + detalle.getVenta().getIdVenta();
    }

    @GetMapping("/detalles/eliminar/{id}")
    public String mostrarConfirmacionEliminarDetalle(@PathVariable long id, HttpSession session, Model model) {
        if (session.getAttribute("usuario") == null) return "redirect:/auth/login";
        Optional<DetalleVentaEntity> detalle = detalleVentaServiceImpl.getDetallePorId(id);
        if (detalle.isPresent()) {
            model.addAttribute("detalle", detalle.get());
            return "eliminar-detalle";
        }
        return "redirect:/ventas";
    }

    @PostMapping("/detalles/eliminar")
    public String eliminarDetalle(HttpSession session, @ModelAttribute DetalleVentaEntity detalle) {
        if (session.getAttribute("usuario") == null) return "redirect:/auth/login";
        Long idVenta = detalle.getVenta().getIdVenta();
        detalleVentaServiceImpl.eliminarDetalle(detalle.getIdDetalle());
        return "redirect:/ventas/detalles/" + idVenta;
    }
}