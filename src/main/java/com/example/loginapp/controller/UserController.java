package com.example.loginapp.controller;

import com.example.loginapp.model.dto.ResetPasswordRequest;
import com.example.loginapp.model.dto.UpdateUserRoleRequest;
import com.example.loginapp.model.dto.UpdateUserProfileRequest;
import com.example.loginapp.model.dto.UserProfileResponse;
import com.example.loginapp.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private AuthService authService;

    @GetMapping
    public ResponseEntity<List<UserProfileResponse>> listUsers(Authentication authentication) {
        return ResponseEntity.ok(authService.listUsers(authentication.getName()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserProfileResponse> updateUser(
            Authentication authentication,
            @PathVariable Long id,
            @RequestBody UpdateUserProfileRequest request) {
        return ResponseEntity.ok(authService.updateUserById(authentication.getName(), id, request));
    }

    @PutMapping("/{id}/role")
    public ResponseEntity<UserProfileResponse> updateRole(
            Authentication authentication,
            @PathVariable Long id,
            @RequestBody UpdateUserRoleRequest request) {
        return ResponseEntity.ok(authService.updateRole(authentication.getName(), id, request));
    }

    @PostMapping("/{id}/reset-password")
    public ResponseEntity<UserProfileResponse> resetPassword(
            Authentication authentication,
            @PathVariable Long id,
            @RequestBody ResetPasswordRequest request) {
        return ResponseEntity.ok(authService.resetPassword(authentication.getName(), id, request));
    }
}
