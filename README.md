<<<<<<< HEAD
# 🎬 CineList — Plataforma de Sugestões de Filmes

Aplicação web Java com arquitetura **MVC**, padrões **DAO** e **Service**, banco de dados **MySQL** e interface dinâmica em **JSP + HTML/CSS**.

---

## 📁 Estrutura do Projeto

```
pfilmes/
├── banco.sql                          ← Script para criar o banco de dados
└── src/main/
    ├── java/
    │   ├── controller/
    │   │   ├── FilmeController.java   ← CRUD completo de filmes
    │   │   └── AvaliacaoController.java ← Avaliação e ranking
    │   ├── dao/
    │   │   ├── FilmeDAO.java
    │   │   ├── AvaliacaoDAO.java
    │   │   └── CategoriaDAO.java
    │   ├── model/
    │   │   ├── Filme.java
    │   │   ├── Avaliacao.java
    │   │   └── Categoria.java
    │   ├── service/
    │   │   ├── FilmeService.java
    │   │   └── AvaliacaoService.java
    │   └── util/
    │       └── ConnectionFactory.java
    └── webapp/
        ├── index.jsp                  ← Página inicial
        ├── WEB-INF/
        │   └── web.xml
        └── view/
            ├── filmes.jsp             ← Listagem de filmes
            ├── formFilme.jsp          ← Cadastro de filme
            ├── editarFilme.jsp        ← Edição de filme
            ├── avaliar.jsp            ← Formulário de avaliação
            └── ranking.jsp            ← Ranking por categoria
```

---

## ⚙️ Como executar

### Pré-requisitos
- JDK 11 ou superior
- Apache Tomcat 9.x
- MySQL 8.x
- Conector JDBC MySQL (`mysql-connector-j-9.7.0.jar`) no `WEB-INF/lib`

### Passo a passo

1. **Criar o banco de dados**
   ```sql
   -- Execute o arquivo banco.sql no MySQL Workbench
   mysql -u root -p < banco.sql
   ```

2. **Configurar a conexão** (se necessário)  
   Edite `src/main/java/util/ConnectionFactory.java`:
   ```java
   private static final String USER = "root";
   private static final String PASS = "sua_senha";
   ```

3. **Implantar no Tomcat**  
   - Copie a pasta do projeto para `webapps/` do Tomcat
   - Inicie o servidor Tomcat.

4. **Acessar no navegador**
   ```
   http://localhost:8080/pfilmes/
   ```

---

## 🧱 Arquitetura MVC

| Camada | Responsabilidade |
|--------|-----------------|
| **Model** | Classes de domínio: `Filme`, `Categoria`, `Avaliacao` |
| **View** | Páginas JSP — exibem os dados ao usuário |
| **Controller** | Servlets — recebem requisições, chamam o Service e redirecionam para a View |
| **Service** | Regras de negócio (validações) |
| **DAO** | Comunicação direta com o banco de dados |

---

## ✅ Funcionalidades

- [x] Listar filmes
- [x] Cadastrar filme (Create)
- [x] Editar filme (Update)
- [x] Excluir filme (Delete)
- [x] Avaliar filme com nota de 1 a 5 estrelas por categoria
- [x] Ranking de filmes por categoria com média e total de avaliações
=======
# plataforma_de_sugestoes_de_filmes
>>>>>>> d186aae33a27846482c83a714c40868ebc395c62
