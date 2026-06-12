package com.example.loginapp.repository;

import com.example.loginapp.model.User;
import org.springframework.stereotype.Repository;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Repositório mockado com dados em memória
 * Substitua por um banco de dados real em produção
 */
@Repository
public class UserRepository {

    private static final Map<String, User> users = new HashMap<>();

    static {
        // Usuários de teste
        users.put("admin", new User(1L, "admin", "senha123", "admin@example.com", "Administrador"));
        users.put("usuario", new User(2L, "usuario", "senha456", "usuario@example.com", "Usuário Teste"));
        users.put("demo", new User(3L, "demo", "demo123", "demo@example.com", "Usuário Demo"));
    }

    public Optional<User> findByUsername(String username) {
        return Optional.ofNullable(users.get(username));
    }

    public Optional<User> findById(Long id) {
        return users.values().stream()
                .filter(user -> user.getId().equals(id))
                .findFirst();
    }

    public void save(User user) {
        users.put(user.getUsername(), user);
    }
}

