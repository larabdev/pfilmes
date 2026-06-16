<%@ page import="java.util.List" %>
<%@ page import="model.Categoria" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    List<String> ranking = (List<String>) request.getAttribute("ranking");
    List<Categoria> categorias = (List<Categoria>) request.getAttribute("categorias");
    int categoriaSelecionada = request.getAttribute("categoriaSelecionada") != null
                               ? (int) request.getAttribute("categoriaSelecionada") : 1;
%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>CineList - Ranking</title>
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
        .container { max-width: 700px; margin: 50px auto; padding: 0 20px; }
        h2 { font-size: 26px; margin-bottom: 24px; }
        .filter { display: flex; align-items: center; gap: 12px; margin-bottom: 30px; }
        select {
            padding: 10px 14px; background: #1e1e1e; border: 1px solid #333;
            border-radius: 6px; color: #fff; font-size: 14px;
        }
        .btn-filter {
            background: #e50914; color: #fff; padding: 10px 20px;
            border: none; border-radius: 6px; font-size: 14px; cursor: pointer; font-weight: bold;
            text-decoration: none;
        }
        .btn-filter:hover { background: #b0060f; }
        .ranking-list { list-style: none; }
        .ranking-item {
            background: #1e1e1e; border-radius: 8px; padding: 18px 20px;
            margin-bottom: 12px; display: flex; align-items: center; gap: 18px;
            border-left: 4px solid #e50914;
        }
        .pos { font-size: 22px; font-weight: bold; color: #e50914; min-width: 36px; }
        .pos.gold   { color: #f0d060; }
        .pos.silver { color: #bbb; }
        .pos.bronze { color: #cd7f32; }
        .info { flex: 1; }
        .info .titulo { font-size: 16px; font-weight: bold; }
        .info .meta   { font-size: 13px; color: #aaa; margin-top: 4px; }
        .stars-display { color: #f0a500; font-size: 18px; }
        .empty { text-align: center; padding: 40px; color: #555; }
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
        <h2>🏆 Ranking de Filmes</h2>

        <div class="filter">
            <form method="get" action="${pageContext.request.contextPath}/avaliacoes" style="display:flex;gap:12px;align-items:center;">
                <input type="hidden" name="action" value="ranking">
                <select name="categoriaId">
                    <% if (categorias != null) {
                           for (Categoria c : categorias) {
                               String sel = c.getId() == categoriaSelecionada ? "selected" : "";
                    %>
                        <option value="<%= c.getId() %>" <%= sel %>><%= c.getNome() %></option>
                    <%     }
                       } %>
                </select>
                <button type="submit" class="btn-filter">Filtrar</button>
            </form>
        </div>

        <% if (ranking != null && !ranking.isEmpty()) { %>
        <ul class="ranking-list">
            <%
                int posicao = 1;
                for (String item : ranking) {
                    // formato: "Titulo | Média: X.X | Avaliações: N"
                    String[] partes = item.split("\\|");
                    String titulo = partes[0].trim();
                    String media  = partes.length > 1 ? partes[1].replace("Média:", "").trim() : "-";
                    String total  = partes.length > 2 ? partes[2].replace("Avaliações:", "").trim() : "-";

                    String posClass = posicao == 1 ? "gold" : posicao == 2 ? "silver" : posicao == 3 ? "bronze" : "";
                    double mediaNum = 0;
                    try { mediaNum = Double.parseDouble(media); } catch (Exception ex) {}
                    StringBuilder estrelas = new StringBuilder();
                    for (int i = 1; i <= 5; i++) estrelas.append(i <= Math.round(mediaNum) ? "★" : "☆");
            %>
            <li class="ranking-item">
                <span class="pos <%= posClass %>"><%= posicao %>º</span>
                <div class="info">
                    <div class="titulo"><%= titulo %></div>
                    <div class="meta"><%= total %> avaliações</div>
                </div>
                <div>
                    <div class="stars-display"><%= estrelas %></div>
                    <div style="text-align:center;font-size:13px;color:#aaa;"><%= media %>/5</div>
                </div>
            </li>
            <% posicao++; } %>
        </ul>
        <% } else { %>
            <p class="empty">Nenhuma avaliação encontrada para esta categoria ainda.</p>
        <% } %>
    </div>
</body>
</html>
