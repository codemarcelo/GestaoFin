package com.example.loginapp.model.dto;

import com.example.loginapp.model.UserRole;

public class UpdateUserRoleRequest {
    private UserRole role;

    public UpdateUserRoleRequest() {}

    public UpdateUserRoleRequest(UserRole role) {
        this.role = role;
    }

    public UserRole getRole() { return role; }
    public void setRole(UserRole role) { this.role = role; }
}
