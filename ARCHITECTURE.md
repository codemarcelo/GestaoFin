# 🏗️ Arquitetura do Projeto

## Visão Geral

```
┌─────────────────────────────────────────────────────────────────┐
│                        NAVEGADOR (Frontend)                    │
│   ┌─────────────────────────────────────────────────────────┐  │
│   │  index.html (HTML + CSS + Vanilla JavaScript)          │  │
│   │  ├─ Tela de Login                                       │  │
│   │  └─ Tela de Dashboard (após login)                     │  │
│   └─────────────────────────────────────────────────────────┘  │
└──────────────────────────────────────────────────────────────────┘
                              ↕
                         HTTP/REST
                              ↕
┌──────────────────────────────────────────────────────────────────┐
│                    SPRING BOOT SERVER (Backend)                  │
│                                                                  │
│  ┌─────────────────────────────────────────────────────────┐   │
│  │  Controllers (com.example.loginapp.controller)         │   │
│  │  ├─ AuthController (/api/auth/login)                   │   │
│  │  ├─ DashboardController (/api/dashboard/*)             │   │
│  │  └─ HomeController (/)                                 │   │
│  └─────────────────────────────────────────────────────────┘   │
│                              ↕                                   │
│  ┌─────────────────────────────────────────────────────────┐   │
│  │  Services (com.example.loginapp.service)               │   │
│  │  ├─ AuthService (autenticação)                         │   │
│  │  └─ JwtService (geração/validação de tokens)           │   │
│  └─────────────────────────────────────────────────────────┘   │
│                              ↕                                   │
│  ┌─────────────────────────────────────────────────────────┐   │
│  │  Repository (com.example.loginapp.repository)          │   │
│  │  └─ UserRepository (mock in-memory, 3 usuários)        │   │
│  └─────────────────────────────────────────────────────────┘   │
│                              ↕                                   │
│  ┌─────────────────────────────────────────────────────────┐   │
│  │  Security (com.example.loginapp.security)              │   │
│  │  └─ JwtAuthenticationFilter (valida JWT em requests)   │   │
│  │  └─ SecurityConfig (setup Spring Security)             │   │
│  └─────────────────────────────────────────────────────────┘   │
│                                                                  │
│  Data: 3 usuários mockados                                      │
│  - admin (senha123) - Administrador                             │
│  - usuario (senha456) - Usuário Teste                           │
│  - demo (demo123) - Usuário Demo                                │
└──────────────────────────────────────────────────────────────────┘
```

---

## Fluxo de Login

```
1. USUÁRIO INTERAGE
   │
   ├─ Acessa http://localhost:8080
   │  └─ Servidor serve index.html
   │
   ├─ Digita username e password
   │  └─ Clica em "Entrar"
   │
   └─ JavaScript envia:
      POST /api/auth/login
      {
        "username": "admin",
        "password": "senha123"
      }

2. SERVIDOR PROCESSA
   │
   ├─ AuthController.login() recebe request
   │
   ├─ AuthService.login() executa:
   │  ├─ UserRepository.findByUsername() procura usuário
   │  ├─ Valida senha
   │  └─ JwtService.generateToken() gera JWT
   │
   └─ Retorna response:
      200 OK
      {
        "token": "eyJhbGc...",
        "username": "admin",
        "fullName": "Administrador",
        "message": "Login realizado com sucesso!"
      }

3. FRONTEND ARMAZENA
   │
   ├─ JavaScript salva token em localStorage
   │  └─ localStorage.setItem('authToken', token)
   │
   └─ Renderiza dashboard com dados do usuário

4. USUÁRIO LOGADO
   │
   ├─ Vê mensagem "🎉 Login realizado com sucesso!"
   ├─ Vê informações: nome, email, etc
   └─ Pode fazer logout (limpa localStorage)
```

---

## Fluxo de Requisição Autenticada

```
FRONTEND (com token)
│
├─ Requisição HTTP
│  │
│  ├─ GET /api/dashboard/welcome
│  │
│  └─ Headers:
│     Authorization: Bearer eyJhbGc...
│
└─ Envia

→ NETWORK

BACKEND
│
├─ JwtAuthenticationFilter intercepta
│  │
│  ├─ Extrai token do header Authorization
│  │
│  ├─ JwtService.isTokenValid(token)
│  │  ├─ Verifica assinatura
│  │  ├─ Verifica expiração
│  │  └─ Se inválido → retorna 401
│  │
│  ├─ Se válido, extrai username
│  │
│  └─ SecurityContextHolder.getContext().setAuthentication(...)
│     └─ Marca como autenticado
│
├─ DashboardController.welcome()
│  │
│  ├─ Extrai authentication do SecurityContext
│  │
│  ├─ AuthService.getUserByUsername()
│  │  └─ Busca dados do usuário no UserRepository
│  │
│  └─ Retorna dados
│
└─ Resposta:
   200 OK
   {
     "message": "🎉 Login realizado com sucesso!",
     "username": "admin",
     "fullName": "Administrador",
     "email": "admin@example.com"
   }

← NETWORK

FRONTEND
│
└─ Recebe resposta e atualiza UI
```

---

## Fluxo de Logout

```
FRONTEND
│
├─ Usuário clica em "Logout"
│
├─ JavaScript remove token:
│  └─ localStorage.removeItem('authToken')
│
├─ Limpa dados armazenados
│
└─ Renderiza tela de login novamente
   └─ Padrão não requer call ao backend
```

---

## Camadas da Aplicação

### 🎨 Presentation Layer (Frontend)
**Arquivo:** `src/main/resources/static/index.html`
- Renderiza UI
- Captura entrada do usuário
- Gerencia tokens
- Faz requisições à API

**Tecnologias:**
- HTML5
- CSS3 (responsivo)
- JavaScript (vanilla, sem frameworks)
- localStorage (persistência de token)

---

### 🔌 API Layer (Controllers)
**Pacote:** `com.example.loginapp.controller`

**AuthController**
```java
POST /api/auth/login
  → AuthService.login(LoginRequest)
  ← LoginResponse {token, username, fullName, message}

GET /api/auth/ping
  ← {message: "pong"}
```

**DashboardController** (requer autenticação)
```java
GET /api/dashboard/welcome
  → Headers: Authorization: Bearer {token}
  ← {message, username, fullName, email}

GET /api/dashboard/profile
  → Headers: Authorization: Bearer {token}
  ← {id, username, email, fullName}
```

**HomeController**
```java
GET /
  ← Forwards to /index.html
```

---

### 💼 Business Layer (Services)
**Pacote:** `com.example.loginapp.service`

**AuthService**
- `login(LoginRequest)`: Valida credenciais, dele authentication
- `getUserByUsername(String)`: Busca dados do usuário

**JwtService**
- `generateToken(String username)`: Cria JWT com 1 hora de validade
- `extractUsername(String token)`: Extrai username do token
- `isTokenValid(String token)`: Valida integridade e expiração

---

### 📊 Data Layer (Repository)
**Pacote:** `com.example.loginapp.repository`

**UserRepository** (Mock - Em Memória)
```
Users:
├─ {id: 1, username: "admin", password: "senha123", ...}
├─ {id: 2, username: "usuario", password: "senha456", ...}
└─ {id: 3, username: "demo", password: "demo123", ...}

Métodos:
├─ findByUsername(String): Optional<User>
├─ findById(Long): Optional<User>
└─ save(User): void
```

---

### 🔒 Security Layer
**Pacote:** `com.example.loginapp.security`

**JwtAuthenticationFilter**
- Intercepta cada requisição HTTP
- Extrai JWT do header Authorization
- Valida token
- Define SecurityContext se válido
- Retorna 401 se inválido

**SecurityConfig**
- Desabilita CSRF
- Configura stateless sessions
- Define públicas: `/`, `/index.html`, `/static/**`, `/api/auth/**`
- Protege: `/api/**`
- Registra JwtAuthenticationFilter

---

## Modelos de Dados

### User
```java
{
  id: Long,
  username: String,
  password: String,
  email: String,
  fullName: String
}
```

### LoginRequest
```java
{
  username: String,
  password: String
}
```

### LoginResponse
```java
{
  token: String,
  username: String,
  fullName: String,
  message: String
}
```

---

## Fluxo de Segurança

```
┌──────────────────────────────────────────────────────────┐
│  1. SENHA TRANSMITIDA (primero login apenas)            │
│     - Sempre via HTTPS em produção                      │
│     - POST /api/auth/login com body JSON                │
└──────────────────────────────────────────────────────────┘
                         ↓
┌──────────────────────────────────────────────────────────┐
│  2. PASSWORD VALIDADO (no servidor)                      │
│     - Comparação simples (plain text)                   │
│     - ⚠️ Em produção: usar bcrypt/argon2               │
└──────────────────────────────────────────────────────────┘
                         ↓
┌──────────────────────────────────────────────────────────┐
│  3. JWT GERADO                                           │
│     - Assinado com HS256                               │
│     - Secret: configurado em application.yml           │
│     - Expira em: 1 hora                                │
│     - Contém: username (subject)                       │
└──────────────────────────────────────────────────────────┘
                         ↓
┌──────────────────────────────────────────────────────────┐
│  4. TOKEN ARMAZENADO (frontend)                         │
│     - localStorage (acessível via JS)                  │
│     - ⚠️ Em produção: usar HttpOnly cookies            │
└──────────────────────────────────────────────────────────┘
                         ↓
┌──────────────────────────────────────────────────────────┐
│  5. TOKEN ENVIADO (cada requisição)                     │
│     - Header: Authorization: Bearer {token}            │
│     - Válido por 1 hora                               │
│     - JwtAuthenticationFilter valida                   │
└──────────────────────────────────────────────────────────┘
```

---

## Deployable Artifacts

### Desenvolvimento
```
mvn spring-boot:run
→ Inicia aplicação em http://localhost:8080
```

### Produção
```
mvn clean package
→ Cria: target/login-app-1.0.0.jar

java -jar target/login-app-1.0.0.jar
→ Inicia aplicação (porta configurável)
```

---

## Tecnologias Utilizadas

| Layer | Tecnologia | Versão | Propósito |
|-------|-----------|--------|----------|
| Frontend | HTML5/CSS3/JS | - | Interface de usuário |
| Backend | Spring Boot | 3.1.5 | Framework web |
| Segurança | Spring Security | 3.1.5 | Autenticação/Autorização |
| JWT | jjwt | 0.12.3 | Token generation/validation |
| Construção | Maven | 3.6+ | Gerenciador de dependências |
| Java | OpenJDK | 17+ | Runtime |

---

**Para mais detalhes, consulte AGENTS.md e README.md**

