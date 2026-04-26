package com.upiiz.farmacia.controllers;

import com.upiiz.farmacia.entities.VentaEntity;
import com.upiiz.farmacia.services.MedicamentoServiceImpl;
import com.upiiz.farmacia.services.VentaServiceImpl;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Calendar;
import java.util.List;

@Controller
public class InicioController {

    @Autowired
    private VentaServiceImpl ventaServiceImpl;

    @Autowired
    private MedicamentoServiceImpl medicamentoServiceImpl;

    @GetMapping("/")
    public String redireccion(HttpSession session) {
        if (session.getAttribute("usuario") == null) return "redirect:/auth/login";
        return "redirect:/inicio";
    }

    @GetMapping("/inicio")
    public String inicio(HttpSession session, Model model) {
        if (session.getAttribute("usuario") == null) return "redirect:/auth/login";

        List<VentaEntity> ventas = ventaServiceImpl.listarVentas();


        model.addAttribute("totalVentas", ventas.size());
        model.addAttribute("totalMedicamentos", medicamentoServiceImpl.listarMedicamentos().size());
        model.addAttribute("totalUsuarios", 1);
        model.addAttribute("totalIngresos",
                ventas.stream().mapToDouble(v -> v.getTotal() != null ? v.getTotal() : 0).sum());


        int[] ventasPorMes = new int[12];
        for (VentaEntity v : ventas) {
            if (v.getFechaVenta() != null) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(v.getFechaVenta());
                int mes = cal.get(Calendar.MONTH);
                ventasPorMes[mes]++;
            }
        }

        model.addAttribute("meses", List.of("Ene","Feb","Mar","Abr","May","Jun","Jul","Ago","Sep","Oct","Nov","Dic"));
        model.addAttribute("ventasPorMes", ventasPorMes);

        return "inicio";
    }
}