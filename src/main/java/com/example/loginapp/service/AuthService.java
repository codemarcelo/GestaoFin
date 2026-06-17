package com.example.loginapp.service;

import com.example.loginapp.exception.BadRequestException;
import com.example.loginapp.exception.ForbiddenException;
import com.example.loginapp.exception.ResourceNotFoundException;
import com.example.loginapp.model.User;
import com.example.loginapp.model.UserRole;
import com.example.loginapp.model.dto.*;
import com.example.loginapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class AuthService {

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    private static final Pattern YEAR_SEQUENCE_PATTERN = Pattern.compile("\\d{4}");

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

        return toLoginResponse(user, "Login realizado com sucesso!");
    }

    public LoginResponse register(RegisterUserRequest request) {
        validateRequiredFields(request);
        validateUsername(request.getUsername());
        validateEmail(request.getEmail());
        validateCpf(request.getCpf());
        validatePhone(request.getPhone());
        validatePassword(request.getPassword(), request.getFirstName(), request.getLastName());

        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new BadRequestException("Login indisponível. Escolha outro usuário.");
        }
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new BadRequestException("E-mail já cadastrado.");
        }
        if (userRepository.findByCpf(request.getCpf()).isPresent()) {
            throw new BadRequestException("CPF já cadastrado.");
        }
        if (userRepository.findByPhone(request.getPhone()).isPresent()) {
            throw new BadRequestException("Telefone já cadastrado.");
        }

        String fullName = buildFullName(request.getFirstName(), request.getLastName());
        User user = new User(
                null,
                request.getUsername(),
                request.getPassword(),
                request.getEmail(),
                fullName,
                request.getFirstName(),
                request.getLastName(),
                request.getCpf(),
                request.getPhone(),
                UserRole.BASIC
        );

        userRepository.save(user);
        return toLoginResponse(user, "Conta criada com sucesso!");
    }

    public UserProfileResponse getProfile(String username) {
        User user = getUserByUsername(username);
        return toProfileResponse(user);
    }

    public UserProfileResponse updateProfile(String username, UpdateUserProfileRequest request) {
        User user = getUserByUsername(username);

        validateRequiredProfileFields(request);
        validateEmail(request.getEmail());
        validateCpf(request.getCpf());
        validatePhone(request.getPhone());

        userRepository.findByEmail(request.getEmail())
                .filter(existing -> !existing.getId().equals(user.getId()))
                .ifPresent(existing -> { throw new BadRequestException("E-mail já cadastrado."); });
        userRepository.findByCpf(request.getCpf())
                .filter(existing -> !existing.getId().equals(user.getId()))
                .ifPresent(existing -> { throw new BadRequestException("CPF já cadastrado."); });
        userRepository.findByPhone(request.getPhone())
                .filter(existing -> !existing.getId().equals(user.getId()))
                .ifPresent(existing -> { throw new BadRequestException("Telefone já cadastrado."); });

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setFullName(buildFullName(request.getFirstName(), request.getLastName()));
        user.setPhone(request.getPhone());
        user.setEmail(request.getEmail());
        userRepository.save(user);

        return toProfileResponse(user);
    }

    public UserProfileResponse updateUserById(String adminUsername, Long userId, UpdateUserProfileRequest request) {
        requireAdmin(adminUsername);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        validateRequiredProfileFields(request);
        validateEmail(request.getEmail());
        validateCpf(request.getCpf());
        validatePhone(request.getPhone());

        userRepository.findByEmail(request.getEmail())
                .filter(existing -> !existing.getId().equals(user.getId()))
                .ifPresent(existing -> { throw new BadRequestException("E-mail já cadastrado."); });
        userRepository.findByCpf(request.getCpf())
                .filter(existing -> !existing.getId().equals(user.getId()))
                .ifPresent(existing -> { throw new BadRequestException("CPF já cadastrado."); });
        userRepository.findByPhone(request.getPhone())
                .filter(existing -> !existing.getId().equals(user.getId()))
                .ifPresent(existing -> { throw new BadRequestException("Telefone já cadastrado."); });

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setFullName(buildFullName(request.getFirstName(), request.getLastName()));
        user.setCpf(request.getCpf());
        user.setPhone(request.getPhone());
        user.setEmail(request.getEmail());
        userRepository.save(user);

        return toProfileResponse(user);
    }

    public List<UserProfileResponse> listUsers(String username) {
        requireAdmin(username);
        return userRepository.findAll().stream()
                .map(this::toProfileResponse)
                .collect(Collectors.toList());
    }

    public UserProfileResponse updateRole(String adminUsername, Long userId, UpdateUserRoleRequest request) {
        requireAdmin(adminUsername);
        if (request.getRole() == null) {
            throw new BadRequestException("Perfil de acesso é obrigatório");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));
        user.setRole(request.getRole());
        userRepository.save(user);
        return toProfileResponse(user);
    }

    public UserProfileResponse resetPassword(String adminUsername, Long userId, ResetPasswordRequest request) {
        requireAdmin(adminUsername);
        if (request.getNewPassword() == null || request.getNewPassword().trim().isEmpty()) {
            throw new BadRequestException("Nova senha é obrigatória");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));
        user.setPassword(request.getNewPassword());
        userRepository.save(user);
        return toProfileResponse(user);
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));
    }

    private LoginResponse toLoginResponse(User user, String message) {
        return new LoginResponse(
                jwtService.generateToken(user.getUsername()),
                user.getUsername(),
                user.getFullName(),
                user.getRole().name(),
                message
        );
    }

    private UserProfileResponse toProfileResponse(User user) {
        return new UserProfileResponse(
                user.getId(),
                user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                user.getFullName(),
                user.getEmail(),
                user.getCpf(),
                user.getPhone(),
                user.getRole()
        );
    }

    private void requireAdmin(String username) {
        User user = getUserByUsername(username);
        if (user.getRole() != UserRole.ADMIN) {
            throw new ForbiddenException("Acesso exclusivo de administrador.");
        }
    }

    private void validateRequiredFields(RegisterUserRequest request) {
        if (isBlank(request.getFirstName()) || isBlank(request.getLastName()) || isBlank(request.getCpf())
                || isBlank(request.getPhone()) || isBlank(request.getEmail()) || isBlank(request.getUsername())
                || isBlank(request.getPassword())) {
            throw new BadRequestException("Todos os campos são obrigatórios.");
        }
    }

    private void validateRequiredProfileFields(UpdateUserProfileRequest request) {
        if (isBlank(request.getFirstName()) || isBlank(request.getLastName()) || isBlank(request.getCpf())
                || isBlank(request.getPhone()) || isBlank(request.getEmail())) {
            throw new BadRequestException("Todos os campos cadastrais são obrigatórios.");
        }
    }

    private void validateUsername(String username) {
        if (username.length() < 3 || username.length() > 50 || !username.matches("^[a-zA-Z0-9_.-]+$")) {
            throw new BadRequestException("Login deve conter de 3 a 50 caracteres, usando apenas letras, números, ponto, underline ou hífen.");
        }
    }

    private void validateEmail(String email) {
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            throw new BadRequestException("Informe um e-mail válido.");
        }
    }

    private void validateCpf(String cpf) {
        String digits = cpf.replaceAll("\\D", "");
        if (digits.length() != 11 || digits.matches("(\\d)\\1{10}")) {
            throw new BadRequestException("Informe um CPF válido.");
        }

        int firstDigit = calculateCpfDigit(digits, 10);
        int secondDigit = calculateCpfDigit(digits, 11);

        if (Character.getNumericValue(digits.charAt(9)) != firstDigit
                || Character.getNumericValue(digits.charAt(10)) != secondDigit) {
            throw new BadRequestException("Informe um CPF válido.");
        }
    }

    private int calculateCpfDigit(String digits, int weightStart) {
        int sum = 0;
        for (int i = 0; i < weightStart - 1; i++) {
            sum += Character.getNumericValue(digits.charAt(i)) * (weightStart - i);
        }
        int remainder = sum % 11;
        return remainder < 2 ? 0 : 11 - remainder;
    }

    private void validatePhone(String phone) {
        String digits = phone.replaceAll("\\D", "");
        if (digits.length() < 10 || digits.length() > 11) {
            throw new BadRequestException("Informe um telefone válido.");
        }
    }

    private void validatePassword(String password, String firstName, String lastName) {
        if (password.length() < 8) {
            throw new BadRequestException("A senha deve conter no mínimo 8 caracteres.");
        }
        if (!password.matches(".*[a-z].*")) {
            throw new BadRequestException("A senha deve conter pelo menos uma letra minúscula.");
        }
        if (!password.matches(".*[A-Z].*")) {
            throw new BadRequestException("A senha deve conter pelo menos uma letra maiúscula.");
        }
        if (!password.matches(".*\\d.*")) {
            throw new BadRequestException("A senha deve conter pelo menos um número.");
        }
        if (!password.matches(".*[^A-Za-z0-9].*")) {
            throw new BadRequestException("A senha deve conter pelo menos um caractere especial.");
        }
        if (containsName(password, firstName) || containsName(password, lastName)) {
            throw new BadRequestException("A senha não pode conter o nome ou sobrenome informados.");
        }
        if (containsYearSequence(password)) {
            throw new BadRequestException("A senha não pode conter uma sequência de 4 dígitos que represente um ano.");
        }
    }

    private boolean containsName(String password, String name) {
        String normalizedPassword = password.toLowerCase(Locale.ROOT);
        String normalizedName = normalizeText(name);
        return normalizedName.length() >= 3 && normalizedPassword.contains(normalizedName);
    }

    private String normalizeText(String value) {
        if (value == null) {
            return "";
        }
        return value.toLowerCase(Locale.ROOT).replaceAll("[^a-z0-9]", "");
    }

    private boolean containsYearSequence(String password) {
        var matcher = YEAR_SEQUENCE_PATTERN.matcher(password);
        while (matcher.find()) {
            int year = Integer.parseInt(matcher.group());
            if (year >= 1900 && year <= 2999) {
                return true;
            }
        }
        return false;
    }

    private String buildFullName(String firstName, String lastName) {
        return (firstName.trim() + " " + lastName.trim()).trim();
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}
