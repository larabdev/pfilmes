<%@ page import="java.util.List" %>
<%@ page import="model.Categoria" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    List<Categoria> categorias = (List<Categoria>) request.getAttribute("categorias");
    String filmeId = (String) request.getAttribute("filmeId");
%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>CineList - Avaliar Filme</title>
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
        .container { max-width: 500px; margin: 50px auto; padding: 0 20px; }
        h2 { font-size: 24px; margin-bottom: 28px; }
        .form-group { margin-bottom: 18px; }
        label { display: block; margin-bottom: 6px; color: #aaa; font-size: 14px; }
        input, select, textarea {
            width: 100%; padding: 12px 14px;
            background: #1e1e1e; border: 1px solid #333; border-radius: 6px;
            color: #fff; font-size: 14px;
        }
        input:focus, select:focus, textarea:focus { outline: none; border-color: #27ae60; }
        textarea { resize: vertical; height: 80px; }
        .stars { display: flex; gap: 10px; margin-top: 4px; }
        .stars input[type="radio"] { display: none; }
        .stars label {
            font-size: 30px; cursor: pointer; color: #555; transition: color .15s;
            width: auto; padding: 0; background: none; border: none;
        }
        .stars input[type="radio"]:checked ~ label,
        .stars label:hover,
        .stars label:hover ~ label { color: #f0a500; }
        .stars { flex-direction: row-reverse; justify-content: flex-end; }
        .btn {
            background: #27ae60; color: #fff; padding: 12px 28px;
            border: none; border-radius: 6px; font-size: 15px; font-weight: bold;
            cursor: pointer; width: 100%; margin-top: 8px;
        }
        .btn:hover { background: #1e8449; }
        .back { display: inline-block; margin-bottom: 20px; color: #aaa; text-decoration: none; font-size: 14px; }
        .back:hover { color: #fff; }
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
        <a class="back" href="${pageContext.request.contextPath}/filmes">← Voltar para lista</a>
        <h2>Avaliar Filme</h2>

        <form method="post" action="${pageContext.request.contextPath}/avaliacoes">
            <input type="hidden" name="filme" value="<%= filmeId %>">

            <div class="form-group">
                <label for="categoria">Categoria *</label>
                <select id="categoria" name="categoria" required>
                    <option value="">-- Selecione a categoria --</option>
                    <% if (categorias != null) {
                           for (Categoria c : categorias) { %>
                        <option value="<%= c.getId() %>"><%= c.getNome() %></option>
                    <%     }
                       } %>
                </select>
            </div>

            <div class="form-group">
                <label>Nota (1 a 5 estrelas) *</label>
                <div class="stars">
                    <input type="radio" id="star5" name="nota" value="5" required>
                    <label for="star5" title="5 estrelas">★</label>
                    <input type="radio" id="star4" name="nota" value="4">
                    <label for="star4" title="4 estrelas">★</label>
                    <input type="radio" id="star3" name="nota" value="3">
                    <label for="star3" title="3 estrelas">★</label>
                    <input type="radio" id="star2" name="nota" value="2">
                    <label for="star2" title="2 estrelas">★</label>
                    <input type="radio" id="star1" name="nota" value="1">
                    <label for="star1" title="1 estrela">★</label>
                </div>
            </div>

            <div class="form-group">
                <label for="comentario">Comentário (opcional)</label>
                <textarea id="comentario" name="comentario" placeholder="O que você achou do filme?"></textarea>
            </div>

            <button type="submit" class="btn">Enviar Avaliação</button>
        </form>
    </div>
</body>
</html>
