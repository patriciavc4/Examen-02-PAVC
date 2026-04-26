package com.upiiz.farmacia.controllers;

import com.upiiz.farmacia.entities.MedicamentoEntity;
import com.upiiz.farmacia.services.MedicamentoServiceImpl;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/medicamentos")
public class MedicamentoController {

    @Autowired
    private MedicamentoServiceImpl medicamentoServiceImpl;

    @GetMapping
    public String medicamentos(HttpSession session, Model model) {
        if (session.getAttribute("usuario") == null) {
            return "redirect:/auth/login";
        }
        List<MedicamentoEntity> listadoMedicamentos = medicamentoServiceImpl.listarMedicamentos();
        model.addAttribute("medicamentos", listadoMedicamentos);
        return "listado-medicamentos";
    }


    @GetMapping("/nuevo")
    public String mostrarFormularioAgregar(HttpSession session, Model model) {
        if (session.getAttribute("usuario") == null) {
            return "redirect:/auth/login";
        }
        model.addAttribute("medicamento", new MedicamentoEntity());
        return "agregar-medicamento";
    }

    @PostMapping
    public String agregarMedicamento(HttpSession session, @ModelAttribute MedicamentoEntity medicamentoEntity) {
        if (session.getAttribute("usuario") == null) {
            return "redirect:/auth/login";
        }
        medicamentoServiceImpl.guardarMedicamento(medicamentoEntity);
        return "redirect:/medicamentos";
    }


    @GetMapping("/actualizar/{id}")
    public String mostrarFormularioActualizar(@PathVariable long id, HttpSession session, Model model) {
        if (session.getAttribute("usuario") == null) {
            return "redirect:/auth/login";
        }
        Optional<MedicamentoEntity> medicamento = medicamentoServiceImpl.getMedicamentoPorId(id);
        if (medicamento.isPresent()) {
            model.addAttribute("medicamento", medicamento.get());
            return "actualizar-medicamento";
        }
        return "redirect:/medicamentos";
    }

    @PostMapping("/actualizar")
    public String actualizarMedicamento(HttpSession session, @ModelAttribute MedicamentoEntity medicamentoEntity) {
        if (session.getAttribute("usuario") == null) {
            return "redirect:/auth/login";
        }
        medicamentoServiceImpl.actualizarMedicamento(medicamentoEntity.getIdMedicamento(), medicamentoEntity);
        return "redirect:/medicamentos";
    }

    @GetMapping("/eliminar/{id}")
    public String mostrarConfirmacionEliminacion(@PathVariable long id, HttpSession session, Model model) {
        if (session.getAttribute("usuario") == null) {
            return "redirect:/auth/login";
        }
        Optional<MedicamentoEntity> medicamento = medicamentoServiceImpl.getMedicamentoPorId(id);
        if (medicamento.isPresent()) {
            model.addAttribute("medicamento", medicamento.get());
            return "eliminar-medicamento";
        }
        return "redirect:/medicamentos";
    }

    @PostMapping("/eliminar")
    public String eliminarMedicamento(HttpSession session, @ModelAttribute MedicamentoEntity medicamentoEntity) {
        // 1. Verificamos sesión
        if (session.getAttribute("usuario") == null) {
            return "redirect:/auth/login";
        }

        try {
            // 2. Intentamos eliminar
            medicamentoServiceImpl.eliminarMedicamento(medicamentoEntity.getIdMedicamento());
            return "redirect:/medicamentos";

        } catch (Exception e) {
            System.err.println("Error: El medicamento tiene registros asociados y no puede eliminarse.");
            return "redirect:/medicamentos?error=true";
        }
    }
}