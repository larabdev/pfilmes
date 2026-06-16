<%@ page import="java.util.List" %>
<%@ page import="model.Filme" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>CineList - Lista de Filmes</title>
    <style>
        * { box-sizing: border-box; margin: 0; padding: 0; }
        body { font-family: 'Segoe UI', sans-serif; background: #141414; color: #fff; }
        header {
            background: #1a1a2e;
            padding: 18px 40px;
            display: flex;
            align-items: center;
            justify-content: space-between;
            border-bottom: 2px solid #e50914;
        }
        header h1 { color: #e50914; font-size: 24px; }
        nav a { color: #ccc; text-decoration: none; margin-left: 20px; font-size: 14px; }
        nav a:hover { color: #e50914; }
        .container { max-width: 1100px; margin: 40px auto; padding: 0 20px; }
        .top-bar { display: flex; justify-content: space-between; align-items: center; margin-bottom: 24px; }
        .top-bar h2 { font-size: 26px; }
        .btn {
            background: #e50914; color: #fff; padding: 10px 22px;
            border-radius: 5px; text-decoration: none; font-weight: bold; font-size: 14px;
        }
        .btn:hover { background: #b0060f; }
        table { width: 100%; border-collapse: collapse; background: #1e1e1e; border-radius: 8px; overflow: hidden; }
        th { background: #2a2a2a; padding: 14px 16px; text-align: left; font-size: 13px; color: #aaa; text-transform: uppercase; letter-spacing: 1px; }
        td { padding: 14px 16px; border-bottom: 1px solid #2a2a2a; font-size: 14px; }
        tr:last-child td { border-bottom: none; }
        tr:hover td { background: #252525; }
        .actions a { margin-right: 12px; text-decoration: none; font-size: 13px; font-weight: bold; }
        .actions a.edit { color: #f0a500; }
        .actions a.del  { color: #e50914; }
        .actions a.rate { color: #27ae60; }
        .actions a:hover { text-decoration: underline; }
        .empty { text-align: center; padding: 40px; color: #555; }
        .genero { background: #333; padding: 3px 10px; border-radius: 12px; font-size: 12px; }
    </style>
</head>
<body>
    <header>
        <h1>🎬 CineList</h1>
        <nav>
            <a href="${pageContext.request.contextPath}/filmes">Filmes</a>
            <a href="${pageContext.request.contextPath}/filmes?action=novo">Cadastrar</a>
            <a href="${pageContext.request.contextPath}/avaliacoes?action=ranking&categoriaId=1">Ranking</a>
            <a href="${pageContext.request.contextPath}/login?action=logout">Sair</a>
        </nav>
    </header>

    <div class="container">
        <div class="top-bar">
            <h2>Lista de Filmes</h2>
            <a href="${pageContext.request.contextPath}/filmes?action=novo" class="btn">+ Novo Filme</a>
        </div>

        <table>
            <thead>
                <tr>
                    <th>#</th>
                    <th>Título</th>
                    <th>Ano</th>
                    <th>Diretor</th>
                    <th>Gênero</th>
                    <th>Ações</th>
                </tr>
            </thead>
            <tbody>
            <%
                List<Filme> filmes = (List<Filme>) request.getAttribute("filmes");
                if (filmes != null && !filmes.isEmpty()) {
                    for (Filme f : filmes) {
            %>
                <tr>
                    <td><%= f.getId() %></td>
                    <td><%= f.getTitulo() %></td>
                    <td><%= f.getAnoLancamento() %></td>
                    <td><%= f.getDiretor() != null ? f.getDiretor() : "-" %></td>
                    <td><span class="genero"><%= f.getGenero() != null ? f.getGenero() : "-" %></span></td>
                    <td class="actions">
                        <a class="edit" href="${pageContext.request.contextPath}/filmes?action=editar&id=<%= f.getId() %>">Editar</a>
                        <a class="del"  href="${pageContext.request.contextPath}/filmes?action=deletar&id=<%= f.getId() %>"
                           onclick="return confirm('Excluir o filme \'<%= f.getTitulo() %>\'?')">Excluir</a>
                        <a class="rate" href="${pageContext.request.contextPath}/avaliacoes?action=form&filmeId=<%= f.getId() %>">Avaliar</a>
                    </td>
                </tr>
            <%
                    }
                } else {
            %>
                <tr><td colspan="6" class="empty">Nenhum filme cadastrado ainda.</td></tr>
            <%  } %>
            </tbody>
        </table>
    </div>
</body>
</html>
