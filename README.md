# 🎬 CineList — Plataforma de Sugestões de Filmes

Site MVC em Java para sugestoes e avaliacoes de filmes, com telas JSP, banco MySQL, autenticacao JWT e API RESTful em JSON.

## Tecnologias utilizadas

- Java
- JSP
- Servlets
- Apache Tomcat
- MySQL
- JDBC
- HTML/CSS
- API RESTful
- JSON
- JWT

## Funcionalidades

- Login de usuario
- Cadastro de filmes
- Listagem de filmes
- Edicao de filmes
- Exclusao de filmes
- Cadastro de avaliacoes
- Listagem de avaliacoes
- Edicao de avaliacoes
- Exclusao de avaliacoes
- Ranking de filmes por categoria
- API RESTful com `GET`, `POST`, `PUT` e `DELETE`

## Estrutura de pastas

```text
pfilme-corrigido-rest/
│
├── banco.sql
├── README.md
├── pom.xml
│
├── src/
│   └── main/
│       │
│       ├── java/
│       │   │
│       │   ├── controller/
│       │   │   ├── AuthController.java
│       │   │   ├── FilmeController.java
│       │   │   └── AvaliacaoController.java
│       │   │
│       │   ├── dao/
│       │   │   ├── UsuarioDAO.java
│       │   │   ├── FilmeDAO.java
│       │   │   ├── AvaliacaoDAO.java
│       │   │   └── CategoriaDAO.java
│       │   │
│       │   ├── filter/
│       │   │   └── JwtFilter.java
│       │   │
│       │   ├── model/
│       │   │   ├── Usuario.java
│       │   │   ├── Filme.java
│       │   │   ├── Avaliacao.java
│       │   │   └── Categoria.java
│       │   │
│       │   ├── service/
│       │   │   ├── AuthService.java
│       │   │   ├── FilmeService.java
│       │   │   └── AvaliacaoService.java
│       │   │
│       │   └── util/
│       │       ├── ConnectionFactory.java
│       │       ├── JwtUtil.java
│       │       └── JsonUtil.java
│       │
│       └── webapp/
│           │
│           ├── index.jsp
│           ├── login.jsp
│           │
│           ├── view/
│           │   ├── filmes.jsp
│           │   ├── formFilme.jsp
│           │   ├── editarFilme.jsp
│           │   ├── avaliar.jsp
│           │   └── ranking.jsp
│           │
│           └── WEB-INF/
│               └── web.xml
│
└── target/
    ├── classes/
    ├── pfilme-corrigido-rest-war/
    └── pfilme-corrigido-rest.war

Usuario padrao:

- E-mail: `admin@filmes.com`
- Senha: `123456`

## O que foi implementado

As rotas REST foram implementadas nos controllers existentes, sem criar controller separado:

- `FilmeController` atende as telas JSP em `/filmes` e a API em `/api/filmes`.
- `AvaliacaoController` atende as telas JSP em `/avaliacoes` e a API em `/api/avaliacoes`.

A API usa:

- JSON no corpo da requisicao.
- Verbos HTTP RESTful: `GET`, `POST`, `PUT` e `DELETE`.
- CRUD completo para filmes.
- CRUD completo para avaliacoes.
- JWT no header `Authorization`.

## Login

`POST /login`

```bash
curl -X POST http://localhost:8080/pfilmes/login ^
  -H "Accept: application/json" ^
  -d "email=admin@filmes.com" ^
  -d "senha=123456"
```

Resposta:

```json
{"token":"SEU_TOKEN_JWT"}
```

Use o token assim:

```text
Authorization: Bearer SEU_TOKEN_JWT
```

## API RESTful de filmes

### Criar filme

`POST /api/filmes`

```json
{
  "titulo": "Interestelar",
  "anoLancamento": 2014,
  "diretor": "Christopher Nolan",
  "genero": "Ficcao Cientifica",
  "sinopse": "Viagem espacial."
}
```

### Listar filmes

`GET /api/filmes`

### Buscar filme por id

`GET /api/filmes/1`

### Atualizar filme

`PUT /api/filmes/1`

```json
{
  "titulo": "Interestelar",
  "anoLancamento": 2014,
  "diretor": "Christopher Nolan",
  "genero": "Sci-Fi",
  "sinopse": "Filme atualizado."
}
```

### Excluir filme

`DELETE /api/filmes/1`

## API RESTful de avaliacoes

### Criar avaliacao

`POST /api/avaliacoes`

```json
{
  "filmeId": 1,
  "categoriaId": 1,
  "nota": 5,
  "comentario": "Excelente."
}
```

### Listar avaliacoes

`GET /api/avaliacoes`

### Buscar avaliacao por id

`GET /api/avaliacoes/1`

### Atualizar avaliacao

`PUT /api/avaliacoes/1`

```json
{
  "filmeId": 1,
  "categoriaId": 1,
  "nota": 4,
  "comentario": "Comentario atualizado."
}
```

### Excluir avaliacao

`DELETE /api/avaliacoes/1`

## Categorias e ranking

Listar categorias:

`GET /api/categorias`

Ranking por categoria:

`GET /api/avaliacoes/ranking?categoriaId=1`

## Telas JSP

- `/login.jsp`: login.
- `/filmes`: lista de filmes.
- `/filmes?action=novo`: cadastro.
- `/filmes?action=editar&id=1`: edicao.
- `/avaliacoes?action=form&filmeId=1`: avaliar filme.
- `/avaliacoes?action=ranking&categoriaId=1`: ranking.

## Como compilar

No terminal PowerShell do VS Code:

$env:JAVA_HOME="C:\Program Files\Java\jdk1.8.0_202"
$env:Path="$env:JAVA_HOME\bin;$env:Path"

New-Item -ItemType Directory -Force -Path target\classes

javac -encoding UTF-8 `
  -cp "C:\xampp\tomcat\lib\servlet-api.jar" `
  -d target\classes `
  (Get-ChildItem src\main\java -Recurse -Filter *.java | ForEach-Object { $_.FullName })
  
## Como gerar o WAR (copie a pasta para tomcat/webapps):

New-Item -ItemType Directory -Force -Path target\*nome pasta*-war
Copy-Item src\main\webapp\* target\*nome pasta*-war -Recurse -Force

New-Item -ItemType Directory -Force -Path target\*nome pasta-war\WEB-INF\classes
Copy-Item target\classes\* target\*nome pasta-war\WEB-INF\classes -Recurse -Force

New-Item -ItemType Directory -Force -Path target\*nome pasta-war\WEB-INF\lib
Copy-Item "..\WEB-INF\lib\mysql-connector-j-9.7.0.jar" target\*nome pasta-war\WEB-INF\lib -Force

jar -cf target\*nome pasta.war -C target\*nome pasta-war .

## Como executar no Tomcat:

Copy-Item target\*nome pasta.war C:\xampp\tomcat\webapps\*nome pasta.war -Force
