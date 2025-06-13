package com.grupo3.pawHome.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @GetMapping()
    @PreAuthorize("hasRole('ADMIN')") // Solo accesible para usuarios con rol ROLE_ADMIN
    public String verDashboardAdmin() {
        return "Bienvenido al dashboard de administración, Admin 👑";
    }
}
