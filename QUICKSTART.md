# 🚀 QUICK START - Como Executar a Aplicação

## ⚡ 30 Segundos para começar

### Opção 1: Executar no Terminal

**PowerShell (Windows):**
```powershell
cd C:\Users\mclco\IdeaProjects\untitled
mvn spring-boot:run
```

Abra o navegador: `http://localhost:8080`

---

## 📋 Pré-requisitos

✅ Java 17+ instalado (verifique com `java -version`)  
✅ Maven 3.6+ instalado (verifique com `mvn -version`)  
✅ IntelliJ IDEA (recomendado, mas não obrigatório)

---

## 🔧 Como Preparar o Projeto (Primeira Vez)

### 1. Abrir em IntelliJ

1. Abra IntelliJ IDEA
2. File → Open
3. Selecione a pasta `C:\Users\mclco\IdeaProjects\untitled`
4. Aguarde a indexação (aparece "Scanning files for IntelliJ IDEA...")
5. Espere até que diga "Frames per second" ou algo similar (significa que terminou)

### 2. Carregar Dependências Maven

Quando abrir o projeto, o IntelliJ automaticamente:
- Detecta `pom.xml`
- Downloads todas as dependências (primeiro download pode levar alguns minutos)
- Configura o projeto como Maven project

Se não carregar automaticamente:
```
View → Tool Windows → Maven
→ Clique em "reload" (ícone em forma de seta circular)
```

### 3. Validar Java SDK

1. Vá para **File** → **Project Structure** (Ctrl+Alt+Shift+S)
2. Selecione **Project**
3. Confirme que **SDK** está configurado para Java 17+
4. Se não estiver, clique em "Add SDK" e selecione Java 17

---

## ▶️ Passo 1: Compilar o Código

### Via Terminal:
```powershell
cd C:\Users\mclco\IdeaProjects\untitled
mvn clean compile
```

### Via IntelliJ:
1. Build → Build Project (Ctrl+F9)
2. Aguarde até ver "Build completed successfully"

---

## ▶️ Passo 2: Executar a Aplicação

### Opção A: Terminal (PowerShell)
```powershell
mvn spring-boot:run
```

Você verá uma saída como:
```
Started Application in 3.456 seconds
```

### Opção B: IntelliJ (Recomendado)

**Primeira vez:**
1. Abra `src/main/java/com/example/loginapp/Application.java`
2. Clique no ícone ▶️ verde ao lado de `public class Application`
3. Selecione "Run 'Application'"

**Próximas vezes:**
- Pressione **Ctrl+Shift+F10** para executar
- Pressione **Shift+F9** para debug

---

## ▶️ Passo 3: Acessar a Aplicação

### 🌐 Abra o navegador:
```
http://localhost:8080
```

Uma tela de login moderna deve aparecer!

---

## 🔑 Testar o Login

Use qualquer uma dessas credenciais:

| Usuário | Senha |
|---------|-------|
| admin | senha123 |
| usuario | senha456 |
| demo | demo123 |

**Processo:**
1. Digite o usuário (ex: `admin`)
2. Digite a senha (ex: `senha123`)
3. Clique em "Entrar"
4. Se sucesso, você verá: "🎉 Login realizado com sucesso!"

---

## 🧪 Testar a API (Opcional)

Abra PowerShell e execute:

```powershell
# Fazer login
curl -X POST http://localhost:8080/api/auth/login `
  -H "Content-Type: application/json" `
  -d '{"username":"admin","password":"senha123"}'
```

Você deve receber um token JWT.

Veja `TESTING.md` para mais exemplos.

---

## 🛑 Parar a Aplicação

- **Terminal**: Pressione `Ctrl+C`
- **IntelliJ**: Clique no botão ⏹️ vermelho na janela Run

---

## ✅ Checklist

- [ ] Java 17+ instalado
- [ ] Maven 3.6+ instalado
- [ ] IntelliJ abriu o projeto sem erros
- [ ] Maven carregou as dependências
- [ ] Compilação sem erros (`mvn clean compile`)
- [ ] Aplicação rodando (`mvn spring-boot:run`)
- [ ] Navegador acessando `http://localhost:8080`
- [ ] Login funcionando com credenciais de teste

---

## 🐛 Se Tiver Problema...

### "java: command not found"
Java não está instalado. Baixe em: https://www.oracle.com/java/technologies/downloads/

### "mvn: command not found"
Maven não está instalado. Baixe em: https://maven.apache.org/download.cgi

### "'Application' has not been run yet"
Na janela de Run do IntelliJ, clique em ▶️ novamente

### "Port 8080 already in use"
Outra aplicação está na porta 8080. Mude em `application.yml`:
```yaml
server.port: 9090
```

### "Build failed: Downloaded invalid file"
Limpe cache Maven:
```powershell
mvn clean
```

### "CORS error in browser console"
Verifique se as requisições estão usando `http://localhost:8080` e não `localhost:8080` ou `127.0.0.1:8080`

---

## 📚 Próximos Passos

Após verificar que funciona:

1. **Leia o código** em `src/main/java/com/example/loginapp/`
2. **Adicione novos usuários** em `UserRepository.java`
3. **Customize a tela** editando `index.html`
4. **Integre com banco de dados** substituindo `UserRepository`
5. **Veja TESTING.md** para testar a API completamente

---

**Conseguiu rodar? 🎉 Parabéns! Agora explore o código e customize conforme precisar!**

