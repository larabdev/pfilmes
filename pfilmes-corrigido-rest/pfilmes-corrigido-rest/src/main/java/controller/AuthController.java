package controller;

import service.AuthService;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

public class AuthController extends HttpServlet {

    private final AuthService authService = new AuthService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        String email = req.getParameter("email");
        String senha = req.getParameter("senha");

        try {
            String token = authService.login(email, senha);
            req.getSession(true).setAttribute("token", token);
            req.getSession(true).setAttribute("usuarioLogado", email);

            if (querJson(req)) {
                resp.setStatus(HttpServletResponse.SC_OK);
                escreverJson(resp, "{\"token\":\"" + escaparJson(token) + "\"}");
            } else {
                resp.sendRedirect(req.getContextPath() + "/filmes");
            }
        } catch (RuntimeException e) {
            if (querJson(req)) {
                resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                escreverJson(resp, "{\"erro\":\"" + escaparJson(e.getMessage()) + "\"}");
            } else {
                resp.sendRedirect(req.getContextPath() + "/login.jsp?erro=1");
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        if ("logout".equals(req.getParameter("action")) && req.getSession(false) != null) {
            req.getSession(false).invalidate();
        }
        resp.sendRedirect(req.getContextPath() + "/login.jsp");
    }

    private boolean querJson(HttpServletRequest req) {
        String accept = req.getHeader("Accept");
        return accept != null && accept.contains("application/json");
    }

    private void escreverJson(HttpServletResponse resp, String json) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();
        out.print(json);
        out.flush();
    }

    private String escaparJson(String valor) {
        if (valor == null) return "";
        return valor.replace("\\", "\\\\").replace("\"", "\\\"");
    }
}
