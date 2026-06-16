<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%
    if (session != null && session.getAttribute("usuarioLogado") != null) {
        response.sendRedirect(request.getContextPath() + "/filmes");
        return;
    }
%>

        <!DOCTYPE html>
        <html lang="pt-BR">

        <head>
            <meta charset="UTF-8">
            <title>CineList - Login</title>
            <style>
                * {
                    box-sizing: border-box;
                    margin: 0;
                    padding: 0;
                }
                
                body {
                    min-height: 100vh;
                    display: grid;
                    place-items: center;
                    font-family: 'Segoe UI', sans-serif;
                    background: #141414;
                    color: #fff;
                }
                
                .login {
                    width: min(420px, calc(100vw - 32px));
                    background: #1e1e1e;
                    border: 1px solid #333;
                    border-radius: 8px;
                    padding: 32px;
                }
                
                h1 {
                    color: #e50914;
                    font-size: 26px;
                    margin-bottom: 8px;
                }
                
                p {
                    color: #aaa;
                    margin-bottom: 24px;
                }
                
                label {
                    display: block;
                    color: #bbb;
                    margin: 14px 0 6px;
                    font-size: 14px;
                }
                
                input {
                    width: 100%;
                    padding: 12px 14px;
                    border-radius: 6px;
                    border: 1px solid #333;
                    background: #141414;
                    color: #fff;
                    font-size: 15px;
                }
                
                input:focus {
                    outline: none;
                    border-color: #e50914;
                }
                
                button {
                    width: 100%;
                    margin-top: 22px;
                    padding: 12px 18px;
                    border: 0;
                    border-radius: 6px;
                    background: #e50914;
                    color: #fff;
                    font-weight: bold;
                    cursor: pointer;
                }
                
                button:hover {
                    background: #b0060f;
                }
                
                .erro {
                    background: #3a1518;
                    border: 1px solid #7d2028;
                    color: #ffd7db;
                    padding: 10px 12px;
                    border-radius: 6px;
                    margin-bottom: 18px;
                    font-size: 14px;
                }
                
                .hint {
                    margin-top: 16px;
                    color: #777;
                    font-size: 13px;
                }
            </style>
        </head>

        <body>
            <main class="login">
                <h1>CineList</h1>
                <p>Entre para cadastrar, editar e avaliar filmes.</p>

                <% if (request.getParameter("erro") != null) { %>
                    <div class="erro">E-mail/senha invalidos ou acesso nao autorizado.</div>
                    <% } %>

                        <form method="post" action="${pageContext.request.contextPath}/login">
                            <label for="email">E-mail</label>
                            <input type="email" id="email" name="email" value="admin@filmes.com" required>

                            <label for="senha">Senha</label>
                            <input type="password" id="senha" name="senha" placeholder="123456" required>

                            <button type="submit">Entrar</button>
                        </form>

                        <div class="hint">Usuario padrao: admin@filmes.com / 123456</div>
            </main>
        </body>

        </html>