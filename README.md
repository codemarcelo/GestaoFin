# 🔐 Sistema de Autenticação com JWT - Spring Boot

Uma aplicação web completa de login com segurança JWT, desenvolvida com Spring Boot MVC, front-end moderno e dados mockados.

## 🎯 Features

✅ Autenticação com JWT (JSON Web Token)  
✅ Base de dados mockada em memória  
✅ Interface de login responsiva e moderna  
✅ Página de dashboard com sucesso de login  
✅ Segurança com Spring Security  
✅ API REST documentada  
✅ Suporte a CORS  

## 📋 Arquitetura

```
src/main/java/com/example/loginapp/
├── Application.java                 # Entrypoint da aplicação
├── config/
│   └── SecurityConfig.java          # Configuração de segurança
├── controller/
│   ├── AuthController.java          # Endpoints de autenticação
│   ├── DashboardController.java     # Endpoints do dashboard
│   └── HomeController.java          # Serve index.html
├── model/
│   ├── User.java                    # Entidade de usuário
│   └── dto/
│       ├── LoginRequest.java        # DTO de requisição
│       └── LoginResponse.java       # DTO de resposta
├── repository/
│   └── UserRepository.java          # Mock de base de dados
├── security/
│   └── JwtAuthenticationFilter.java # Filtro JWT
└── service/
    ├── AuthService.java            # Serviço de autenticação
    └── JwtService.java             # Serviço de JWT
```

## 🚀 Como Executar

### Pré-requisitos
- Java 17 ou superior
- Maven 3.6+

### Passo 1: Compilar o projeto

```bash
mvn clean compile
```

### Passo 2: Executar a aplicação

```bash
mvn spring-boot:run
```

Ou, execute diretamente pela IDE:
- **Ctrl+Shift+F10** para Run
- **Shift+F9** para Debug

### Passo 3: Acessar a aplicação

Abra seu navegador e acesse:
```
http://localhost:8080
```

## 🔑 Credenciais de Teste

A aplicação possui 3 usuários pré-configurados:

| Usuário | Senha | Nome Completo |
|---------|-------|--------------|
| `admin` | `senha123` | Administrador |
| `usuario` | `senha456` | Usuário Teste |
| `demo` | `demo123` | Usuário Demo |

## 📡 Endpoints da API

### Autenticação

```http
POST /api/auth/login
Content-Type: application/json

{
  "username": "admin",
  "password": "senha123"
}
```

**Resposta (200 OK):**
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "username": "admin",
  "fullName": "Administrador",
  "message": "Login realizado com sucesso!"
}
```

### Dashboard (Protegido)

```http
GET /api/dashboard/welcome
Authorization: Bearer <seu_token_jwt>
```

**Resposta (200 OK):**
```json
{
  "message": "🎉 Login realizado com sucesso!",
  "username": "admin",
  "fullName": "Administrador",
  "email": "admin@example.com"
}
```

```http
GET /api/dashboard/profile
Authorization: Bearer <seu_token_jwt>
```

**Resposta:**
```json
{
  "id": 1,
  "username": "admin",
  "email": "admin@example.com",
  "fullName": "Administrador"
}
```

## 🔒 Segurança com JWT

### Como funciona:

1. **Login**: Usuário envia credenciais para `/api/auth/login`
2. **Token Gerado**: Servidor gera um JWT válido por 1 hora
3. **Armazenamento**: Token é armazenado no localStorage do navegador
4. **Autenticação**: Token é enviado em cada requisição no header `Authorization: Bearer <token>`
5. **Validação**: O `JwtAuthenticationFilter` valida o token e autentica o usuário

### Segredos JWT:
- Localizado em: `application.yml`
- Algoritmo: HS256
- Expiração: 1 hora (3600000 ms)

## 🎨 Interface do Usuário

### Tela de Login
- Campo de usuário e senha
- Validação em tempo real
- Credenciais de teste visíveis abaixo do formulário
- Loading indicator durante autenticação

### Tela de Dashboard
- Mensagem de sucesso com ícone celebrativo
- Informações do usuário logado
- Botão para fazer logout

## 📦 Estrutura de Pastas do Projeto

```
untitled/
├── src/
│   ├── main/
│   │   ├── java/com/example/loginapp/
│   │   │   ├── Application.java
│   │   │   ├── config/
│   │   │   ├── controller/
│   │   │   ├── model/
│   │   │   ├── repository/
│   │   │   ├── security/
│   │   │   └── service/
│   │   └── resources/
│   │       ├── application.yml
│   │       └── static/
│   │           └── index.html
│   └── test/
├── pom.xml
├── untitled.iml
├── AGENTS.md
└── README.md
```

## 🔧 Configurações Importantes

### application.yml
```yaml
server.port: 8080
jwt.secret: mySecretKeyForJWTTokenGenerationAndValidationThatIsLongEnough2024!
jwt.expiration: 3600000 (1 hora em ms)
```

**⚠️ Importante**: Em produção, altere a `jwt.secret` para uma chave segura e use variáveis de ambiente.

## 🧪 Testando a API

### Com cURL:

```bash
# 1. Fazer login
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"senha123"}'

# 2. Copiar o token da resposta

# 3. Acessar o dashboard
curl -X GET http://localhost:8080/api/dashboard/welcome \
  -H "Authorization: Bearer seu_token_aqui"
```

### Com Postman:

1. **POST** → `http://localhost:8080/api/auth/login`
2. Body (raw JSON):
```json
{"username":"admin","password":"senha123"}
```
3. Copie o `token` da resposta
4. **GET** → `http://localhost:8080/api/dashboard/welcome`
5. Headers → Authorization: `Bearer seu_token_aqui`

## 🛠️ Desenvolvendo

### Adicionar novo usuário
Edite `UserRepository.java` no método `static`:

```java
users.put("novo_usuario", new User(4L, "novo_usuario", "senha", "email@example.com", "Nome Completo"));
```

### Modificar tempo de expiração do JWT
Edite `application.yml`:

```yaml
jwt.expiration: 7200000  # 2 horas
```

### Mudar a porta do servidor
Edite `application.yml`:

```yaml
server.port: 9090
```

## 📚 Dependências Principais

- **Spring Boot 3.1.5** - Framework web
- **Spring Security** - Segurança
- **jjwt 0.12.3** - Geração e validação de JWT
- **Lombok** - Redução de boilerplate
- **Maven** - Gerenciador de dependências

## 📝 Licença

Este projeto é fornecido como exemplo educacional.

## 🤝 Contribuindo

Sinta-se livre para fazer fork e melhorar este projeto!

---

**Criado com ❤️ para demonstrar boas práticas de autenticação web com Spring Boot e JWT**

