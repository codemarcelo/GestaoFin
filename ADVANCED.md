# 📖 Exemplos de Uso Avançado

## 🧪 Testes com Diferentes Cenários

### 1. Teste de Sucesso (Login Válido)

**Request:**
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "admin",
    "password": "senha123"
  }'
```

**Response (200 OK):**
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTcyNDc2NzMyNywiZXhwIjoxNzI0NzcwOTI3fQ.AiPPu-XLx5L8yZ7k9Q2wR3sT4uV6w7xY8z9a0b1c2dE",
  "username": "admin",
  "fullName": "Administrador",
  "message": "Login realizado com sucesso!"
}
```

---

### 2. Teste de Falha (Senha Incorreta)

**Request:**
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "admin",
    "password": "senha_errada"
  }'
```

**Response (400 Bad Request):**
```json
{
  "token": null,
  "username": null,
  "fullName": null,
  "message": "Erro: Senha incorreta"
}
```

---

### 3. Teste de Falha (Usuário Não Existe)

**Request:**
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "usuario_inexistente",
    "password": "qualquer_senha"
  }'
```

**Response (400 Bad Request):**
```json
{
  "token": null,
  "username": null,
  "fullName": null,
  "message": "Erro: Usuário não encontrado"
}
```

---

### 4. Acesso ao Dashboard (Com Token Válido)

**Request:**
```bash
# Primeiro, obter o token
TOKEN=$(curl -s -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"senha123"}' | \
  grep -o '"token":"[^"]*' | \
  grep -o '[^"]*$')

# Depois, acessar o dashboard
curl -X GET http://localhost:8080/api/dashboard/welcome \
  -H "Authorization: Bearer $TOKEN"
```

**Response (200 OK):**
```json
{
  "message": "🎉 Login realizado com sucesso!",
  "username": "admin",
  "fullName": "Administrador",
  "email": "admin@example.com"
}
```

---

### 5. Acesso ao Dashboard (Sem Token)

**Request:**
```bash
curl -X GET http://localhost:8080/api/dashboard/welcome
```

**Response (401 Unauthorized):**
```json
{
  "timestamp": "2024-08-27T12:34:56.789+00:00",
  "status": 401,
  "error": "Unauthorized",
  "path": "/api/dashboard/welcome"
}
```

---

### 6. Acesso ao Dashboard (Com Token Inválido)

**Request:**
```bash
curl -X GET http://localhost:8080/api/dashboard/welcome \
  -H "Authorization: Bearer token_invalido_123"
```

**Response (401 Unauthorized):** Same as above

---

## 🛠️ Casos de Extensão

### Adicionar Novo Usuário

**Arquivo:** `src/main/java/com/example/loginapp/repository/UserRepository.java`

```java
static {
    // Usuários existentes
    users.put("admin", new User(1L, "admin", "senha123", "admin@example.com", "Administrador"));
    users.put("usuario", new User(2L, "usuario", "senha456", "usuario@example.com", "Usuário Teste"));
    users.put("demo", new User(3L, "demo", "demo123", "demo@example.com", "Usuário Demo"));
    
    // NOVO USUÁRIO
    users.put("joao", new User(4L, "joao", "senha789", "joao@example.com", "João Silva"));
}
```

Agora você pode fazer login com:
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"joao","password":"senha789"}'
```

---

### Adicionar Campo ao Usuário

1. **Edite User.java:**
```java
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Long id;
    private String username;
    private String password;
    private String email;
    private String fullName;
    private String department;  // NOVO CAMPO
    private String phoneNumber; // NOVO CAMPO
}
```

2. **Atualize UserRepository.java:**
```java
users.put("admin", new User(
    1L, "admin", "senha123", "admin@example.com", "Administrador",
    "IT", "11-99999-1111"  // Novos campos
));
```

3. **O novo campo será automaticamente retornado em:**
```
GET /api/dashboard/profile
GET /api/dashboard/welcome
```

---

### Mudar Tempo de Expiração do JWT

**Arquivo:** `src/main/resources/application.yml`

```yaml
jwt:
  secret: mySecretKeyForJWTTokenGenerationAndValidationThatIsLongEnough2024!
  expiration: 7200000  # 2 horas em vez de 1 hora (3600000)
```

A aplicação precisa ser reiniciada para aplicar a mudança.

---

### Adicionar Endpoint Protegido Customizado

1. **Crie novo método em DashboardController:**

```java
@GetMapping("/stats")
public ResponseEntity<?> getStats() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    String username = auth.getName();
    
    return ResponseEntity.ok(new Object() {
        public String message = "Estatísticas do usuário " + username;
        public int loginCount = 42;
        public String lastLogin = "2024-08-27T12:30:00";
    });
}
```

2. **Teste:**
```bash
curl -X GET http://localhost:8080/api/dashboard/stats \
  -H "Authorization: Bearer seu_token"
```

---

### Adicionar Validação Customizada

**Edite AuthService.java:**

```java
public LoginResponse login(LoginRequest loginRequest) {
    // Validar username vazio
    if (loginRequest.getUsername() == null || loginRequest.getUsername().trim().isEmpty()) {
        throw new RuntimeException("Username não pode estar vazio");
    }
    
    // Validar comprimento mínimo
    if (loginRequest.getUsername().length() < 3) {
        throw new RuntimeException("Username deve ter no mínimo 3 caracteres");
    }
    
    // ... resto do código
}
```

---

### Integração com Banco de Dados (Spring Data JPA)

1. **Adicionar dependência ao pom.xml:**
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
<dependency>
    <groupId>com.h2database</groupId>
    <artifactId>h2</artifactId>
    <scope>runtime</scope>
</dependency>
```

2. **Adicionar @Entity a User:**
```java
@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true)
    private String username;
    
    // ... outros campos
}
```

3. **Substituir UserRepository:**
```java
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
```

4. **Adicionar dados iniciais (data.sql):**
```sql
INSERT INTO users (id, username, password, email, full_name) 
VALUES (1, 'admin', 'senha123', 'admin@example.com', 'Administrador');
```

---

### Adicionar Roles/Permissões

1. **Criar classe Role:**
```java
@Data
@Entity
public class Role {
    @Id
    private Long id;
    private String name; // ADMIN, USER, etc
}
```

2. **Adicionar à User:**
```java
@ManyToMany
private List<Role> roles;
```

3. **Incluir em JWT:**
```java
// Em JwtService.java
public String generateToken(String username) {
    User user = userRepository.findByUsername(username).get();
    List<String> roles = user.getRoles().stream()
        .map(Role::getName)
        .collect(Collectors.toList());
    
    return Jwts.builder()
        .subject(username)
        .claim("roles", roles)  // Adicionar roles
        .issuedAt(new Date())
        .expiration(new Date(System.currentTimeMillis() + expirationTime))
        .signWith(getSigningKey(), SignatureAlgorithm.HS256)
        .compact();
}
```

4. **Usar em Controller:**
```java
@PreAuthorize("hasAuthority('ADMIN')")
@PostMapping("/users")
public ResponseEntity<?> createUser(@RequestBody User user) {
    // Apenas admins podem acessar
}
```

---

### Implementar Refresh Token

1. **Adicionar ao LoginResponse:**
```java
@Data
public class LoginResponse {
    private String token;
    private String refreshToken;  // Novo
    private String username;
    private String fullName;
    private String message;
}
```

2. **Gerar dois tokens:**
```java
String accessToken = jwtService.generateToken(username);
String refreshToken = jwtService.generateRefreshToken(username);

return Response.ok(new LoginResponse(
    accessToken, refreshToken, username, fullName, message
));
```

3. **Endpoint para refresh:**
```java
@PostMapping("/refresh")
public ResponseEntity<?> refresh(@RequestBody RefreshRequest request) {
    String username = jwtService.extractUsername(request.getRefreshToken());
    String newAccessToken = jwtService.generateToken(username);
    return ResponseEntity.ok(new Object() {
        public String token = newAccessToken;
    });
}
```

---

### Adicionar HTTPS/SSL

**application.yml:**
```yaml
server:
  ssl:
    key-store: classpath:keystore.p12
    key-store-password: password
    key-store-type: PKCS12
    key-alias: tomcat
```

Generate keystore:
```bash
keytool -genkeypair -alias tomcat -keyalg RSA -keysize 2048 -storetype PKCS12 \
  -keystore keystore.p12 -validity 365
```

---

### Adicionar Logging

**application.yml:**
```yaml
logging:
  level:
    root: INFO
    com.example.loginapp: DEBUG
  pattern:
    console: "%d{yyyy-MM-dd HH:mm:ss} - %msg%n"
```

**Em Services:**
```java
private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

public LoginResponse login(LoginRequest loginRequest) {
    logger.info("Login attempt for user: {}", loginRequest.getUsername());
    // ...
    logger.debug("Login successful for user: {}", user.getUsername());
}
```

---

## 📚 Recursos Adicionais

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Spring Security](https://spring.io/projects/spring-security)
- [jjwt GitHub](https://github.com/jwtk/jjwt)
- [JWT.io](https://jwt.io) - Decodificador visual de tokens

---

**Pronto para estender? Comece pelo caso mais simples (adicionar usuário) e trabalhe para os mais complexos!**

