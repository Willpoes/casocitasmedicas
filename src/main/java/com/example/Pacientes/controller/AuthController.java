package com.example.Pacientes.controller;

import com.example.Pacientes.security.JwtUtil;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password) {
        // 👇 Aquí deberías validar usuario/contraseña contra la BD.
        if ("admin".equals(username) && "1234".equals(password)) {
            return JwtUtil.generarToken(username);
        }
        throw new RuntimeException("Credenciales inválidas");
    }

}
