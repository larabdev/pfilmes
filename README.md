# 🎬 CineList — Plataforma de Sugestões de Filmes

Aplicação web Java com arquitetura **MVC**, padrões **DAO**, **Service** e **Singleton**, autenticação com **JWT**, banco de dados **MySQL** e interface dinâmica em **JSP + HTML/CSS**.

---

## 📁 Estrutura do Projeto

```
pfilmes-corrigido/

├── banco.sql

├── README.md

└── src/main/

├── java/

│   ├── controller/

│   │   ├── AuthController.java       ← Login e logout

│   │   ├── FilmeController.java      ← CRUD de filmes

│   │   └── AvaliacaoController.java  ← Avaliação e ranking

│   ├── dao/

│   │   ├── FilmeDAO.java

│   │   ├── AvaliacaoDAO.java

│   │   ├── CategoriaDAO.java

│   │   └── UsuarioDAO.java

│   ├── filter/

│   │   └── JwtFilter.java            ← Filtro de autenticação JWT

│   ├── model/

│   │   ├── Filme.java

│   │   ├── Avaliacao.java

│   │   ├── Categoria.java

│   │   └── Usuario.java

│   ├── service/

│   │   ├── AuthService.java

│   │   ├── FilmeService.java

│   │   └── AvaliacaoService.java

│   └── util/

│       ├── ConnectionFactory.java    ← Singleton

│       └── JwtUtil.java              ← Geração e validação de tokens

└── webapp/

├── index.jsp                     ← Página inicial (requer login)

├── login.jsp                     ← Tela de login

├── WEB-INF/

│   └── web.xml

└── view/

├── filmes.jsp

├── formFilme.jsp

├── editarFilme.jsp

├── avaliar.jsp

└── ranking.jsp
```

---

## 🧱 Arquitetura MVC

| Camada | Responsabilidade |
|--------|-----------------|
| **Model** | Classes de domínio: `Filme`, `Categoria`, `Avaliacao`, `Usuario` |
| **View** | Páginas JSP — exibem os dados ao usuário |
| **Controller** | Servlets — recebem requisições, chamam o Service e redirecionam para a View |
| **Service** | Regras de negócio e validações |
| **DAO** | Comunicação direta com o banco de dados |

---

## 🔐 Autenticação JWT

- O login valida e-mail e senha no banco e gera um token JWT via `JwtUtil`
- O token é salvo na sessão HTTP
- O `JwtFilter` intercepta todas as rotas protegidas (`/filmes`, `/avaliacoes`, `/avaliar`) e valida o token
- Se o token for inválido ou ausente, o usuário é redirecionado para `login.jsp?erro=1`
- Resposta para APIs: `{"erro":"Acesso não autorizado"}` com status 401

**Credenciais padrão:**
- E-mail: `admin@filmes.com`
- Senha: `123456`

---

## 🔄 Padrão Singleton — ConnectionFactory

A classe `ConnectionFactory` implementa o padrão Singleton com double-checked locking:

```java
public static ConnectionFactory getInstance() {
    if (instance == null) {
        synchronized (ConnectionFactory.class) {
            if (instance == null) {
                instance = new ConnectionFactory();
            }
        }
    }
    return instance;
}
```

Garante uma única instância da fábrica de conexões durante todo o ciclo de vida da aplicação.

---

## 📡 Endpoints da API

### Autenticação
| Método | URL | Descrição |
|--------|-----|-----------|
| POST | `/login` | Realiza login e retorna token JWT |
| GET | `/login?action=logout` | Encerra a sessão |

### Filmes (requer autenticação)
| Método | URL | Descrição |
|--------|-----|-----------|
| GET | `/filmes` | Lista todos os filmes |
| GET | `/filmes?action=novo` | Exibe formulário de cadastro |
| POST | `/filmes` (action=salvar) | Cadastra novo filme |
| GET | `/filmes?action=editar&id=N` | Exibe formulário de edição |
| POST | `/filmes` (action=atualizar) | Atualiza filme |
| GET | `/filmes?action=deletar&id=N` | Exclui filme |
| GET | `/filmes?action=buscar&id=N` | Busca filme por ID (retorna JSON) |
| GET | `/filmes?action=listar` + Header `Accept: application/json` | Lista filmes em JSON |

### Avaliações (requer autenticação)
| Método | URL | Descrição |
|--------|-----|-----------|
| GET | `/avaliacoes?action=form&filmeId=N` | Exibe formulário de avaliação |
| POST | `/avaliar` | Registra avaliação |
| GET | `/avaliacoes?action=ranking&categoriaId=N` | Exibe ranking por categoria |
| GET | `/avaliacoes?action=categorias` | Lista categorias (JSON) |

---

## ⚙️ Como executar

### Pré-requisitos
- JDK 8
- Apache Tomcat 8.5 (via XAMPP)
- MySQL 8.x
- Conector JDBC MySQL (`mysql-connector-j-9.7.0.jar`) em `WEB-INF/lib`

### Passo a passo

**1. Criar o banco de dados** no MySQL Workbench:
```sql
-- Execute o arquivo banco.sql
```

**2. Compilar:**
```bash
javac -source 8 -target 8 -cp "C:\xampp\tomcat\lib\servlet-api.jar;C:\xampp\tomcat\webapps\pfilmes-corrigido\WEB-INF\lib\mysql-connector-j-9.7.0.jar" -d "C:\xampp\tomcat\webapps\pfilmes-corrigido\WEB-INF\classes" src\main\java\util\ConnectionFactory.java src\main\java\util\JwtUtil.java src\main\java\model\*.java src\main\java\dao\*.java src\main\java\service\*.java src\main\java\filter\JwtFilter.java src\main\java\controller\*.java
```

**3. Iniciar o Tomcat** pelo XAMPP Control Panel.

**4. Acessar:**

---

## ✅ Funcionalidades

- [x] Login com autenticação JWT
- [x] Logout
- [x] Rotas protegidas por filtro JWT
- [x] Listar filmes
- [x] Cadastrar filme (Create)
- [x] Editar filme (Update)
- [x] Excluir filme (Delete)
- [x] Avaliar filme com nota de 1 a 5 estrelas por categoria
- [x] Ranking de filmes por categoria
- [x] API RESTful com respostas JSON
- [x] Padrão Singleton no ConnectionFactory
- [x] Padrão MVC com camadas Controller, Service e DAO
