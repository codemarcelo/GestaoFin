package com.example.loginapp.repository;

import com.example.loginapp.model.User;
import com.example.loginapp.model.UserRole;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
public class UserRepository {

    private static final Map<String, User> users = new ConcurrentHashMap<>();
    private static final AtomicLong nextId = new AtomicLong(4L);

    static {
        users.put("admin", new User(1L, "admin", "senha123", "admin@example.com", "Administrador", "Administrador", "Sistema", "123.456.789-09", "(00) 00000-0000", UserRole.ADMIN));
        users.put("usuario", new User(2L, "usuario", "senha456", "usuario@example.com", "Usuário Teste", "Usuário", "Teste", "111.444.777-35", "(11) 90000-0000", UserRole.BASIC));
        users.put("demo", new User(3L, "demo", "demo123", "demo@example.com", "Usuário Demo", "Usuário", "Demo", "529.982.247-25", "(22) 90000-0000", UserRole.BASIC));
    }

    public Optional<User> findByUsername(String username) {
        return Optional.ofNullable(users.get(username));
    }

    public Optional<User> findById(Long id) {
        return users.values().stream()
                .filter(user -> user.getId().equals(id))
                .findFirst();
    }

    public Optional<User> findByEmail(String email) {
        return users.values().stream()
                .filter(user -> user.getEmail().equalsIgnoreCase(email))
                .findFirst();
    }

    public Optional<User> findByCpf(String cpf) {
        return users.values().stream()
                .filter(user -> user.getCpf().equals(cpf))
                .findFirst();
    }

    public Optional<User> findByPhone(String phone) {
        return users.values().stream()
                .filter(user -> user.getPhone().equals(phone))
                .findFirst();
    }

    public List<User> findAll() {
        return users.values().stream()
                .sorted(Comparator.comparing(User::getUsername))
                .collect(Collectors.toList());
    }

    public void save(User user) {
        if (user.getId() == null) {
            user.setId(nextId.getAndIncrement());
        }
        users.put(user.getUsername(), user);
    }
}
