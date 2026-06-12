# AGENTS.md - AI Agent Guidelines

## Project Overview
Spring Boot MVC application with JWT authentication, mock database, and modern web UI. Uses Maven for dependency management.

## Project Structure
```
untitled/
├── pom.xml                          # Maven configuration
├── src/
│   ├── main/
│   │   ├── java/com/example/loginapp/
│   │   │   ├── Application.java                 # Spring Boot entry point
│   │   │   ├── config/
│   │   │   │   └── SecurityConfig.java          # Spring Security & JWT config
│   │   │   ├── controller/
│   │   │   │   ├── AuthController.java          # Login endpoint
│   │   │   │   ├── DashboardController.java     # Protected endpoints
│   │   │   │   └── HomeController.java          # Serves index.html
│   │   │   ├── model/
│   │   │   │   ├── User.java                    # User entity
│   │   │   │   └── dto/
│   │   │   │       ├── LoginRequest.java
│   │   │   │       └── LoginResponse.java
│   │   │   ├── repository/
│   │   │   │   └── UserRepository.java          # In-memory mock database
│   │   │   ├── security/
│   │   │   │   └── JwtAuthenticationFilter.java # JWT validation filter
│   │   │   └── service/
│   │   │       ├── AuthService.java             # Authentication logic
│   │   │       └── JwtService.java              # JWT token generation
│   │   └── resources/
│   │       ├── application.yml                  # Spring Boot config
│   │       └── static/
│   │           └── index.html                   # Web UI
├── untitled.iml                    # IntelliJ module config (Maven)
├── README.md                       # User documentation
├── TESTING.md                      # Testing guide with cURL examples
└── .gitignore                      # Excludes IDE and Maven artifacts
```

## Architecture

### Security Flow
1. **LoginController**: Receives `POST /api/auth/login` with credentials
2. **AuthService**: Validates against `UserRepository` (mocked in-memory)
3. **JwtService**: Generates JWT token valid for 1 hour
4. **Frontend**: Stores token in `localStorage`
5. **Subsequent Requests**: Include token in `Authorization: Bearer <token>` header
6. **JwtAuthenticationFilter**: Validates token on every request, sets `SecurityContext`
7. **DashboardController**: Protected endpoints check authentication

### Key Components

#### UserRepository (Mock Database)
- Located: `com/example/loginapp/repository/UserRepository.java`
- Contains 3 test users: `admin` (senha123), `usuario` (senha456), `demo` (demo123)
- Add new users by editing the static block
- No persistence - resets on application restart

#### JwtService
- Algorithm: HS256
- Secret: Configured in `application.yml` as `jwt.secret`
- Expiration: 1 hour (3600000 ms) - configured in `jwt.expiration`
- Token format: Standard JWT with claims (subject, issued-at, expiration)

#### SecurityConfig
- Stateless sessions (`SessionCreationPolicy.STATELESS`)
- CORS enabled for all endpoints
- Public endpoints: `/`, `/index.html`, `/static/**`, `/api/auth/login`
- Protected: `/api/**` - requires valid JWT

## Development Workflows

### Running the Application
```bash
mvn spring-boot:run
```

**Or in IntelliJ:**
- Right-click `Application.java` → Run
- Or use **Ctrl+Shift+F10**
- Debug with **Shift+F9**

### Building for Production
```bash
mvn clean package
# JAR created at: target/login-app-1.0.0.jar

java -jar target/login-app-1.0.0.jar
```

### Adding New Users
Edit `UserRepository.java` static block:
```java
users.put("newuser", new User(4L, "newuser", "password", "email@example.com", "Full Name"));
```

### Changing JWT Expiration
Edit `application.yml`:
```yaml
jwt.expiration: 7200000  # 2 hours instead of 1
```

### Changing Server Port
Edit `application.yml`:
```yaml
server.port: 9090
```

## Key Patterns & Conventions

### API Response Structure
All success responses include message: `"Login realizado com sucesso!"` or similar
Error responses have `message` field with error details

### Frontend Integration
- HTML at: `src/main/resources/static/index.html`
- Handles both login form and dashboard views
- Stores JWT in `localStorage` under key `authToken`
- No external framework - vanilla JS

### Request/Response Flow
```
Frontend
  ↓ POST /api/auth/login {username, password}
  ↓
AuthController → AuthService → UserRepository
                           ↓
                       JwtService → JWT Token
  ↑ 200 OK {token, username, fullName, message}
  ↓ GET /api/dashboard/welcome (Authorization: Bearer token)
  ↓
DashboardController (protected by JwtAuthenticationFilter)
  ↑ 200 OK {message, username, fullName, email}
```

## Important Configuration

### application.yml
```yaml
server.port: 8080
jwt.secret: mySecretKeyForJWTTokenGenerationAndValidationThatIsLongEnough2024!
jwt.expiration: 3600000
```

⚠️ **Production**: Change `jwt.secret` to secure random key, use environment variables

### Maven Properties
- Java: 17 (set in pom.xml)
- Spring Boot: 3.1.5
- Key dependencies: spring-boot-starter-web, spring-boot-starter-security, jjwt

## Testing

### Quick Test
Navigate browser to `http://localhost:8080` after running application

### API Testing
See `TESTING.md` for cURL examples on Windows PowerShell

### Logs
Default Spring Boot logging to console. Check IDE console output after running.

## Common Modifications

### Add Custom Validation
Extend `AuthService.login()` method after password check

### Change Login Page Styling
Edit CSS in `src/main/resources/static/index.html` (lines 6-250)

### Add Role-Based Access Control
1. Add `role` field to `User` model
2. Update `JWT` token to include role claim
3. Modify `SecurityConfig` to use `@PreAuthorize` on controllers

### Implement Database Persistence
Replace `UserRepository.java` with dependency injection of `JpaRepository`

## Known Limitations
- Mock database (in-memory, not persistent)
- No email validation
- Single application instance (no distributed JWT handling)
- No HTTP-only cookies (JWT in localStorage)
- No HTTPS enforcement in config (add to application.yml for production)

## AI Agent Guidance

### When Adding Features
1. Respect JWT security pattern - don't bypass `JwtAuthenticationFilter`
2. Protected endpoints should use `DashboardController` pattern
3. DTOs for request/response in `model/dto/` package
4. Services in `service/` package handle business logic
5. Controllers minimal logic - use services

### When Debugging
- **Check token**: JWT token in browser localStorage? Correct format?
- **Check logs**: Spring Security will log authentication failures
- **Check CORS**: Browser console for "No 'Access-Control-Allow-Origin'" errors
- **Check filters**: If endpoint hangs, `JwtAuthenticationFilter` might not complete

### When Expanding
- **Add endpoints**: Create controller in `controller/` package, add routes to `SecurityConfig`
- **Add users**: Edit `UserRepository.java` or replace with JPA
- **Change auth method**: Replace `JwtService` with OAuth2 if needed
- **Add tables**: Create `@Entity` classes, add JPA dependency, update `repository/`

## Next Steps for Agents
- Always verify `Application.java` is the entry point
- JWT security is critical - preserve `JwtAuthenticationFilter` behavior
- Test API with cURL examples from `TESTING.md`
- Mock database is intentional - acceptable for demo/development
- Frontend is vanilla JS - avoid adding frameworks without request

