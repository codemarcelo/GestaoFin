# 🧪 Guia de Testes - API de Login

Exemplos de como testar a API usando cURL, que está disponível no PowerShell (Windows 10+).

## Pré-requisitos

- Aplicação rodando em `http://localhost:8080`
- cURL instalado (vem com Windows 10+)

## 1️⃣ Testar se o servidor está online

```powershell
curl http://localhost:8080/api/auth/ping
```

**Esperado:**
```json
{"message":"pong"}
```

---

## 2️⃣ Fazer Login

```powershell
curl -X POST http://localhost:8080/api/auth/login `
  -H "Content-Type: application/json" `
  -d '{"username":"admin","password":"senha123"}'
```

**Esperado:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTcyNDc2MzcxNSwiZXhwIjoxNzI0NzY3MzE1fQ.XYZ...",
  "username": "admin",
  "fullName": "Administrador",
  "message": "Login realizado com sucesso!"
}
```

**Copie o token para usar nos próximos passos!**

---

## 3️⃣ Acessar Dashboard (Com Token)

```powershell
# Substitua seu_token_aqui pelo token copiado acima
curl -X GET http://localhost:8080/api/dashboard/welcome `
  -H "Authorization: Bearer seu_token_aqui"
```

**Esperado:**
```json
{
  "message": "🎉 Login realizado com sucesso!",
  "username": "admin",
  "fullName": "Administrador",
  "email": "admin@example.com"
}
```

---

## 4️⃣ Obter Perfil do Usuário (Com Token)

```powershell
curl -X GET http://localhost:8080/api/dashboard/profile `
  -H "Authorization: Bearer seu_token_aqui"
```

**Esperado:**
```json
{
  "id": 1,
  "username": "admin",
  "password": "senha123",
  "email": "admin@example.com",
  "fullName": "Administrador"
}
```

---

## 5️⃣ Testar com Token Inválido

```powershell
curl -X GET http://localhost:8080/api/dashboard/welcome `
  -H "Authorization: Bearer token_invalido_123"
```

**Esperado:** Status 401 (Unauthorized)

---

## 6️⃣ Testar com Credenciais Erradas

```powershell
curl -X POST http://localhost:8080/api/auth/login `
  -H "Content-Type: application/json" `
  -d '{"username":"admin","password":"senha_errada"}'
```

**Esperado:** Status 400 com mensagem de erro
```json
{
  "token": null,
  "username": null,
  "fullName": null,
  "message": "Erro: Senha incorreta"
}
```

---

## 🔄 Script Completo (Automatizado)

Crie um arquivo `test-api.ps1` e execute com:

```powershell
.\test-api.ps1
```

**Conteúdo do arquivo:**

```powershell
# Script de teste - test-api.ps1
$baseUrl = "http://localhost:8080"

# 1. Verifica se servidor está online
Write-Host "1️⃣  Testando conexão..."
curl "$baseUrl/api/auth/ping"
Write-Host ""

# 2. Fazer login
Write-Host "2️⃣  Fazendo login com admin..."
$loginResponse = curl -X POST "$baseUrl/api/auth/login" `
  -H "Content-Type: application/json" `
  -d '{"username":"admin","password":"senha123"}' | ConvertFrom-Json

$token = $loginResponse.token
Write-Host "Token obtido: $($token.Substring(0, 20))..."
Write-Host ""

# 3. Acessar dashboard
Write-Host "3️⃣  Acessando dashboard..."
curl -X GET "$baseUrl/api/dashboard/welcome" `
  -H "Authorization: Bearer $token"
Write-Host ""

# 4. Obter perfil
Write-Host "4️⃣  Obtendo perfil..."
curl -X GET "$baseUrl/api/dashboard/profile" `
  -H "Authorization: Bearer $token"
Write-Host ""

Write-Host "✅ Testes concluídos!"
```

---

## Credenciais Disponíveis

Use qualquer uma das credenciais abaixo para testar:

```
admin      | senha123
usuario    | senha456
demo       | demo123
```

---

## 📊 Fluxo do Teste Manual

1. Abra **PowerShell**
2. Execute o login e **copie o token**
3. Execute os endpoints com o token copiado
4. Veja as respostas no terminal

---

## ✅ Checklist de Testes

- [ ] Servidor respondendo em `http://localhost:8080`
- [ ] Login com admin funcionando
- [ ] Token JWT sendo gerado
- [ ] Dashboard acessível com token válido
- [ ] Perfil do usuário sendo retornado
- [ ] Token inválido retorna 401
- [ ] Credenciais erradas retornam erro
- [ ] CORS funcionando (teste do navegador)

---

## 🐛 Solução de Problemas

**"Connection refused"**
- Certifique-se da que a aplicação está rodando (`mvn spring-boot:run`)

**"Invalid token"**
- Token expirou (validade é 1 hora) - faça login novamente
- Verifique se copiou o token corretamente

**"User not found"**
- Verifique se o usuário existe na lista de credenciais
- Verifique capitalização correta

---

**Dúvidas?** Verifique o `README.md` para mais informações!

