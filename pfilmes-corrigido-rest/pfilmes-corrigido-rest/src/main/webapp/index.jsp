<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%
    if (session == null || session.getAttribute("usuarioLogado") == null) {
        response.sendRedirect(request.getContextPath() + "/login.jsp");
        return;
    }
%>

        <!DOCTYPE html>
        <html lang="pt-BR">

        <head>
            <meta charset="UTF-8">
            <title>CineList - Plataforma de Sugestões de Filmes</title>
            <style>
                * {
                    box-sizing: border-box;
                    margin: 0;
                    padding: 0;
                }
                
                body {
                    font-family: 'Segoe UI', sans-serif;
                    background: #141414;
                    color: #fff;
                }
                
                header {
                    background: #1a1a2e;
                    padding: 18px 40px;
                    display: flex;
                    align-items: center;
                    justify-content: space-between;
                    border-bottom: 2px solid #e50914;
                }
                
                header h1 {
                    color: #e50914;
                    font-size: 28px;
                    letter-spacing: 2px;
                }
                
                nav a {
                    color: #ccc;
                    text-decoration: none;
                    margin-left: 24px;
                    font-size: 15px;
                    transition: color .2s;
                }
                
                nav a:hover {
                    color: #e50914;
                }
                
                .hero {
                    text-align: center;
                    padding: 80px 20px 60px;
                    background: linear-gradient(to bottom, #1a1a2e, #141414);
                }
                
                .hero h2 {
                    font-size: 42px;
                    margin-bottom: 16px;
                }
                
                .hero p {
                    color: #aaa;
                    font-size: 18px;
                    margin-bottom: 36px;
                }
                
                .btn {
                    display: inline-block;
                    background: #e50914;
                    color: #fff;
                    padding: 14px 32px;
                    border-radius: 6px;
                    text-decoration: none;
                    font-size: 16px;
                    font-weight: bold;
                    margin: 6px;
                    transition: background .2s;
                }
                
                .btn:hover {
                    background: #b0060f;
                }
                
                .btn.secondary {
                    background: #333;
                }
                
                .btn.secondary:hover {
                    background: #555;
                }
                
                footer {
                    text-align: center;
                    padding: 30px;
                    color: #555;
                    font-size: 13px;
                    border-top: 1px solid #222;
                    margin-top: 60px;
                }
            </style>
        </head>

        <body>
            <header>
                <a href="login.jsp">Login</a>
                <h1>🎬 CineList</h1>
                <nav>
                    <a href="${pageContext.request.contextPath}/filmes">Filmes</a>
                    <a href="${pageContext.request.contextPath}/filmes?action=novo">Cadastrar</a>
                    <a href="${pageContext.request.contextPath}/avaliacoes?action=ranking&categoriaId=1">Ranking</a>
                    <a href="${pageContext.request.contextPath}/login?action=logout">Sair</a>
                </nav>
            </header>

            <div class="hero">
                <h2>Descubra e avalie filmes</h2>
                <p>Cadastre, edite, avalie e veja o ranking dos melhores filmes por categoria.</p>
                <a href="${pageContext.request.contextPath}/filmes" class="btn">Ver Filmes</a>
                <a href="${pageContext.request.contextPath}/filmes?action=novo" class="btn secondary">Cadastrar Filme</a>
            </div>

            <footer>CineList &copy; 2025 — Plataforma de Sugestões de Filmes</footer>
        </body>

        </html>