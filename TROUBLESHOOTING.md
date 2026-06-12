# 🔧 Resolvendo Erros de Compilação

Se você está vendo erros como "Cannot resolve symbol 'springframework'" ou "Package name does not correspond to file path", não se preocupe! Isso é normal na primeira vez.

## ❌ Erro: "Cannot resolve symbol 'springframework'"

### Causa
O Maven ainda não carregou as dependências do projeto.

### Solução

#### Solução 1: Sincronizar Maven no IntelliJ (Recomendado)

1. Vá para **View** → **Tool Windows** → **Maven** (ou pressione Alt+F12)
2. Expanda o projeto (clique em ▶️)
3. Clique no ícone **"Reload All Maven Projects"** (seta circular azul)
4. Aguarde até ver "BUILD SUCCESS"

#### Solução 2: Terminal

Abra PowerShell na pasta do projeto e execute:

```powershell
mvn clean install -DskipTests
```

Isso pode levar 2-5 minutos na primeira execução.

#### Solução 3: Forçar Renovação via IntelliJ

1. **File** → **Invalidate Caches and Restart**
2. Selecione **Invalidate and Restart**
3. Espere o IntelliJ reiniciar e reindexar

---

## ❌ Erro: "Package name does not correspond to the file path"

### Causa
O IntelliJ não reconheceu a origem correta dos arquivos.

### Solução

1. Vá para **File** → **Project Structure** (Ctrl+Alt+Shift+S)
2. Selecione **Modules** no painel esquerdo
3. Marque `src/main/java` como **Sources**
4. Marque `src/main/resources` como **Resources**
5. Marque `src/test/java` como **Test Sources** (se existir)
6. Clique **Apply** e **OK**

Se não houver essas opções, faça o seguinte:
1. Clique no botão **"+"** para adicionar source folder
2. Navegue para `src/main/java` e marque como **Sources**
3. Repeat para resources e test

---

## ✅ Como Verificar se as Dependências Foram Carregadas

### Via IntelliJ

1. Abra **View** → **Tool Windows** → **Project**
2. Procure pela pasta **"External Libraries"**
3. Expanda e procure por **"spring-boot"** ou **"spring-security"**
4. Se estiver lá, as dependências foram carregadas! ✅

### Via Terminal

```powershell
mvn dependency:tree
```

Se funcionar, as dependências estão prtas.

---

## 🔄 Checklist para Resolver Erros

- [ ] Abrir Maven tool window em IntelliJ
- [ ] Clicar em "Reload All Maven Projects"
- [ ] Aguardar até ver "BUILD SUCCESS"
- [ ] Abrir Project Structure e verificar source folders
- [ ] Se ainda tiver erros, ir para **File** → **Invalidate Caches and Restart**
- [ ] Aguardar IntelliJ reiniciar completamente
- [ ] Tentar compilar novamente (`mvn clean compile`)

---

## 🚀 Após Resolver Erros

1. Compile: `mvn clean compile`
2. Execute: `mvn spring-boot:run`
3. Navegue: `http://localhost:8080`

---

## 💡 Dicas

- **Primeira execução é lenta**: O Maven precisa download ~200MB de bibliotecas
- **Erros de "never used"**: São avisos, não impedem execução
- **Se ainda não funcionar**: Tente limpar cache Maven:
  ```powershell
  rmdir ~/.m2/repository -recurse
  mvn clean install -DskipTests
  ```

---

**Se os erros persistirem após seguir os passos acima, abra um terminal e execute `mvn clean install`. Se compilar com sucesso lá, é um problema de índice do IntelliJ.**

