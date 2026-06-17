package com.example.loginapp.controller;

import com.example.loginapp.model.dto.UserProfileResponse;
import com.example.loginapp.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin(origins = "*")
public class DashboardController {

    @Autowired
    private AuthService authService;

    @GetMapping("/welcome")
    public ResponseEntity<?> welcome(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            UserProfileResponse user = authService.getProfile(authentication.getName());

            return ResponseEntity.ok(new Object() {
                public String message = "🎉 Login realizado com sucesso!";
                public String username = user.getUsername();
                public String fullName = user.getFullName();
                public String email = user.getEmail();
                public String role = user.getRole().name();
            });
        }

        return ResponseEntity.status(401).body(
                new Object() {
                    public String message = "Não autenticado";
                }
        );
    }

    @GetMapping("/profile")
    public ResponseEntity<UserProfileResponse> getProfile(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            return ResponseEntity.ok(authService.getProfile(authentication.getName()));
        }

        return ResponseEntity.status(401).build();
    }

    @PutMapping("/profile")
    public ResponseEntity<UserProfileResponse> updateProfile(
            Authentication authentication,
            @RequestBody com.example.loginapp.model.dto.UpdateUserProfileRequest request) {
        return ResponseEntity.ok(authService.updateProfile(authentication.getName(), request));
    }
}
