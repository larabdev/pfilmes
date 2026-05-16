<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>CineList - Cadastrar Filme</title>
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
        .container { max-width: 600px; margin: 50px auto; padding: 0 20px; }
        h2 { font-size: 24px; margin-bottom: 28px; }
        .form-group { margin-bottom: 18px; }
        label { display: block; margin-bottom: 6px; color: #aaa; font-size: 14px; }
        input, select, textarea {
            width: 100%; padding: 12px 14px;
            background: #1e1e1e; border: 1px solid #333; border-radius: 6px;
            color: #fff; font-size: 14px;
        }
        input:focus, select:focus, textarea:focus { outline: none; border-color: #e50914; }
        textarea { resize: vertical; height: 90px; }
        .btn {
            background: #e50914; color: #fff; padding: 12px 28px;
            border: none; border-radius: 6px; font-size: 15px; font-weight: bold;
            cursor: pointer; width: 100%; margin-top: 8px;
        }
        .btn:hover { background: #b0060f; }
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
            <a href="${pageContext.request.contextPath}/avaliar?action=ranking&categoriaId=1">Ranking</a>
        </nav>
    </header>

    <div class="container">
        <a class="back" href="${pageContext.request.contextPath}/filmes">← Voltar para lista</a>
        <h2>Cadastrar Novo Filme</h2>

        <form method="post" action="${pageContext.request.contextPath}/filmes">
            <input type="hidden" name="action" value="salvar">

            <div class="form-group">
                <label for="titulo">Título *</label>
                <input type="text" id="titulo" name="titulo" placeholder="Ex: O Poderoso Chefão" required>
            </div>

            <div class="form-group">
                <label for="ano">Ano de Lançamento *</label>
                <input type="number" id="ano" name="ano" placeholder="Ex: 1972" min="1888" max="2100" required>
            </div>

            <div class="form-group">
                <label for="diretor">Diretor</label>
                <input type="text" id="diretor" name="diretor" placeholder="Ex: Francis Ford Coppola">
            </div>

            <div class="form-group">
                <label for="genero">Gênero</label>
                <select id="genero" name="genero">
                    <option value="">-- Selecione --</option>
                    <option value="Ação">Ação</option>
                    <option value="Aventura">Aventura</option>
                    <option value="Comédia">Comédia</option>
                    <option value="Drama">Drama</option>
                    <option value="Ficção Científica">Ficção Científica</option>
                    <option value="Horror">Horror</option>
                    <option value="Romance">Romance</option>
                    <option value="Suspense">Suspense</option>
                    <option value="Animação">Animação</option>
                    <option value="Documentário">Documentário</option>
                </select>
            </div>

            <div class="form-group">
                <label for="sinopse">Sinopse</label>
                <textarea id="sinopse" name="sinopse" placeholder="Breve descrição do filme..."></textarea>
            </div>

            <button type="submit" class="btn">Salvar Filme</button>
        </form>
    </div>
</body>
</html>
