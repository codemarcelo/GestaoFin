# 🚀 COMECE AQUI - START HERE

## ⚡ 5 Minutos para Sua Aplicação de Login Rodando

Siga **EXATAMENTE** nesta ordem:

---

## 1️⃣ Verificar Pré-requisitos (1 min)

Abra PowerShell e execute:

```powershell
java -version
mvn -version
```

Se der erro, instale:
- Java: https://www.oracle.com/java/technologies/downloads/
- Maven: https://maven.apache.org/download.cgi

---

## 2️⃣ Navegar para Pasta (30 segundos)

```powershell
cd C:\Users\mclco\IdeaProjects\untitled
```

---

## 3️⃣ Executar Aplicação (2-3 minutos)

```powershell
mvn spring-boot:run
```

Aguarde até ver:
```
Started Application in X.XXX seconds
```

---

## 4️⃣ Abrir Navegador (30 segundos)

Navegue para:
```
http://localhost:8080
```

Você deverá ver uma tela de login moderna!

---

## 5️⃣ Fazer Login (1 minuto)

Use qualquer credencial:

```
Usuário: admin
Senha:   senha123
```

Ou:
```
Usuário: usuario
Senha:   senha456
```

Ou:
```
Usuário: demo
Senha:   demo123
```

Clique em **"Entrar"** e você vai ver:
```
🎉 Login realizado com sucesso!
```

---

## ✅ Pronto! Sua aplicação está rodando!

---

## 📚 Próximos Passos

### Se Quiser Entender O Que Aconteceu:
→ Leia: **[ARCHITECTURE.md](./ARCHITECTURE.md)** (20 min)

### Se Quiser Testar a API:
→ Leia: **[TESTING.md](./TESTING.md)** (15 min)

### Se Quiser Adicionar Novo Usuário:
→ Leia: **[ADVANCED.md](./ADVANCED.md)** - Seção "Adicionar Novo Usuário"

### Se Tiver Erro:
→ Leia: **[TROUBLESHOOTING.md](./TROUBLESHOOTING.md)**

### Se Quiser VER TUDO:
→ Leia: **[INDEX.md](./INDEX.md)** (Índice Completo)

---

## 🎯 Mapa de Documentos

```
📄 START_HERE.md          ← Você está aqui (5 min)
│
├─→ 📄 QUICKSTART.md      (30 min até funcionar)
│
├─→ 📄 README.md          (Features e overview)
│
├─→ 📄 ARCHITECTURE.md    (Como funciona | Diagramas)
│
├─→ 📄 TESTING.md         (Como testar a API)
│
├─→ 📄 ADVANCED.md        (Como estender)
│
├─→ 📄 TROUBLESHOOTING.md (Se der erro)
│
├─→ 📄 INDEX.md           (Índice completo)
│
└─→ 📄 ROADMAP.md         (Roadmap do projeto)
```

---

## ❓ Tenho uma Dúvida

### "A aplicação não inicia"
1. Confirme que Java 17+ está instalado: `java -version`
2. Confirme que Maven está instalado: `mvn -version`
3. Leia: [TROUBLESHOOTING.md](./TROUBLESHOOTING.md)

### "Não consegui fazer login"
1. Verifique as credenciais acima
2. Abra console do navegador (F12) e veja se há erros
3. Leia: [TESTING.md](./TESTING.md)

### "Quer entender a arquitetura"
→ Leia: [ARCHITECTURE.md](./ARCHITECTURE.md)

### "Quer adicionar um novo usuário"
→ Leia: [ADVANCED.md](./ADVANCED.md)

### "Quer testar pelo cURL"
→ Leia: [TESTING.md](./TESTING.md)

---

## 🎓 Como Funciona (Resumo Rápido)

```
1. Você faz login no navegador
   ↓
2. Credenciais são enviadas para /api/auth/login
   ↓
3. Servidor valida no banco mockado
   ↓
4. Token JWT é gerado e retornado
   ↓
5. Token é armazenado no localStorage
   ↓
6. Dashboard é exibido
   ↓
7. Token é incluído em requisições futuras
   ↓
8. Servidor valida token e retorna dados
```

**Para mais detalhes, leia [ARCHITECTURE.md](./ARCHITECTURE.md)**

---

## 📋 Credenciais Para Testar

| Usuário | Senha | Perfil |
|---------|-------|--------|
| **admin** | **senha123** | Administrador |
| **usuario** | **senha456** | Usuário Teste |
| **demo** | **demo123** | Usuário Demo |

---

## 📞 Ajuda Rápida

| Situação | Arquivo |
|----------|---------|
| Quer executar rápido | **QUICKSTART.md** |
| Está tudo pronto? | **README.md** |
| Quer entender tudo | **ARCHITECTURE.md** |
| Quer testar API | **TESTING.md** |
| Quer estender | **ADVANCED.md** |
| Tem erro? | **TROUBLESHOOTING.md** |
| Perdido? | **INDEX.md** |

---

## ✨ Você irá Descobrir

✅ Como autenticação JWT funciona  
✅ Como Spring Boot funciona  
✅ Como Spring Security funciona  
✅ Como frontend e backend se comunicam  
✅ Como armazenar tokens com segurança  
✅ Como proteger endpoints  
✅ Como testar uma API  
✅ Como estender uma aplicação  

---

## 📞 Estou Pronto!

Você tem agora uma **aplicação completa de login** com:
- ✅ Autenticação com JWT
- ✅ Interface moderna
- ✅ Base de dados mockada
- ✅ API REST funcional
- ✅ Segurança implementada
- ✅ Documentação completa

---

## 🚀 Vá Para O Próximo Passo

Escolha uma opção:

### Opção A: Executar Imediatamente ⏱️ (5 min)
1. Abra PowerShell
2. Execute `mvn spring-boot:run`
3. Acesse `http://localhost:8080`
4. Teste com uma credencial
5. **PRONTO!**

### Opção B: Entender Depois ⏱️ (30 min)
1. Leia [QUICKSTART.md](./QUICKSTART.md)
2. Execute a aplicação
3. Leia [ARCHITECTURE.md](./ARCHITECTURE.md)
4. Explore o código

### Opção C: Explorar Completo ⏱️ (2-3 horas)
1. Leia [INDEX.md](./INDEX.md) inteiro
2. Execute a aplicação
3. Teste todos endpoints
4. Customize e estenda

---

## 🎯 Objetivo Desta Documentação

Você conseguirá:
1. ✅ Executar a aplicação em **5 minutos**
2. ✅ Entender a arquitetura em **20 minutos**
3. ✅ Testar a API em **15 minutos**
4. ✅ Estender em **1-2 horas**
5. ✅ Deployar em produção em **2-4 horas**

---

## 📊 O Que Foi Criado

- 🎨 **1 Interface Web** (HTML/CSS/JS)
- ☕ **13 Arquivos Java** (Controllers, Services, Models, Security)
- 📚 **10 Documentos** (Este, QUICKSTART, README, etc)
- 📮 **1 Postman Collection** (Para testar)
- ⚙️ **3 Arquivos de Configuração** (pom.xml, application.yml, .gitignore)

---

## 🏁 Está Pronto Para Começar?

```
Sim? → Abra PowerShell e execute: mvn spring-boot:run
Não? → Leia QUICKSTART.md (30 min) e depois execute
Perdido? → Leia INDEX.md (5 min)
```

---

**Você tem tudo que precisa. Boa sorte! 🚀**

Próximo arquivo a ler: **[QUICKSTART.md](./QUICKSTART.md)**

