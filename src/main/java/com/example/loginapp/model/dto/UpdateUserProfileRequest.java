package com.example.loginapp.model.dto;

public class UpdateUserProfileRequest {
    private String firstName;
    private String lastName;
    private String cpf;
    private String phone;
    private String email;

    public UpdateUserProfileRequest() {}

    public UpdateUserProfileRequest(String firstName, String lastName, String cpf, String phone, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.cpf = cpf;
        this.phone = phone;
        this.email = email;
    }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
