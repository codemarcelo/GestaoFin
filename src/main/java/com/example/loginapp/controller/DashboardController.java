package com.example.loginapp.controller;

import com.example.loginapp.model.User;
import com.example.loginapp.service.AuthService;
import com.example.loginapp.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin(origins = "*")
public class DashboardController {

    @Autowired
    private AuthService authService;

    @GetMapping("/welcome")
    public ResponseEntity<?> welcome() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            User user = authService.getUserByUsername(username);

            return ResponseEntity.ok(new Object() {
                public String message = "🎉 Login realizado com sucesso!";
                public String username = user.getUsername();
                public String fullName = user.getFullName();
                public String email = user.getEmail();
            });
        }

        return ResponseEntity.status(401).body(
                new Object() {
                    public String message = "Não autenticado";
                }
        );
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            User user = authService.getUserByUsername(username);
            return ResponseEntity.ok(user);
        }

        return ResponseEntity.status(401).body(
                new Object() {
                    public String message = "Não autenticado";
                }
        );
    }
}

