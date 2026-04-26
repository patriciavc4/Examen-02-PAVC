package com.upiiz.farmacia.controllers;

import com.upiiz.farmacia.entities.UserEntity;
import com.upiiz.farmacia.repositories.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JavaMailSender mailSender;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @GetMapping("/forgot-password")
    public String forgotpassword() {
        return "forgot-password";
    }

    @PostMapping("/login")
    public String loginPost(@RequestParam String email,
                            @RequestParam String password,
                            HttpSession session,
                            Model model) {
        UserEntity user = userRepository.findByEmail(email);

        if (user == null) {
            model.addAttribute("error", "Usuario no encontrado");
            return "login";
        }

        if (!user.getPassword().equals(password)) {
            model.addAttribute("error", "Contraseña incorrecta");
            return "login";
        }

        session.setAttribute("usuario", user);
        return "redirect:/inicio";
    }

    @PostMapping("/register")
    public String registerPost(@RequestParam String email,
                               @RequestParam String password,
                               @RequestParam String nombre,
                               Model model) {
        if (userRepository.existsByEmail(email)) {
            model.addAttribute("error", "El usuario ya existe");
            return "register";
        }

        UserEntity user = new UserEntity();
        user.setEmail(email);
        user.setPassword(password);
        user.setNombre(nombre);

        userRepository.save(user);
        return "login";
    }

    @PostMapping("/forgot-password")
    public String processForgotPassword(@RequestParam("email") String email, Model model) {
        UserEntity user = userRepository.findByEmail(email);

        if (user != null) {
            try {
                SimpleMailMessage message = new SimpleMailMessage();
                message.setTo(user.getEmail());
                message.setSubject("Recuperación de Contraseña - Farmacia");
                message.setText("Hola " + user.getNombre() + ",\n\n" +
                        "Tu contraseña es: " + user.getPassword());

                mailSender.send(message);
                model.addAttribute("mensaje", "Se ha enviado un correo con tu contraseña.");
            } catch (Exception e) {
                model.addAttribute("error", "Error al enviar el correo: " + e.getMessage());
            }
        } else {
            model.addAttribute("error", "El correo no está registrado.");
        }
        return "forgot-password";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/auth/login";
    }
}