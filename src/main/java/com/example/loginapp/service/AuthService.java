package com.example.loginapp.service;

import com.example.loginapp.exception.BadRequestException;
import com.example.loginapp.exception.ResourceNotFoundException;
import com.example.loginapp.model.User;
import com.example.loginapp.model.dto.LoginRequest;
import com.example.loginapp.model.dto.LoginResponse;
import com.example.loginapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    public LoginResponse login(LoginRequest loginRequest) {
        User user = userRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        if (!user.getPassword().equals(loginRequest.getPassword())) {
            throw new BadRequestException("Senha incorreta");
        }

        String token = jwtService.generateToken(user.getUsername());

        return new LoginResponse(
                token,
                user.getUsername(),
                user.getFullName(),
                "Login realizado com sucesso!"
        );
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));
    }
}

