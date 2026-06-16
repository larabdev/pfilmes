package filter;

import util.JwtUtil;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

public class JwtFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        String token = extrairToken(req);

        if (token == null || token.trim().isEmpty()) {
            enviarNaoAutorizado(req, resp);
            return;
        }

        try {
            String email = JwtUtil.validarToken(token);
            req.setAttribute("usuarioLogado", email);
            chain.doFilter(request, response);
        } catch (RuntimeException e) {
            enviarNaoAutorizado(req, resp);
        }
    }

    private String extrairToken(HttpServletRequest req) {
        String authHeader = req.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }

        HttpSession session = req.getSession(false);
        if (session != null && session.getAttribute("token") instanceof String) {
            return (String) session.getAttribute("token");
        }

        return null;
    }

    private void enviarNaoAutorizado(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (aceitaHtml(req)) {
            resp.sendRedirect(req.getContextPath() + "/login.jsp?erro=1");
            return;
        }

        resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();
        out.print("{\"erro\":\"Acesso nao autorizado\"}");
        out.flush();
    }

    private boolean aceitaHtml(HttpServletRequest req) {
        String accept = req.getHeader("Accept");
        String requestedWith = req.getHeader("X-Requested-With");
        return requestedWith == null && accept != null && accept.contains("text/html");
    }

    @Override public void init(FilterConfig fc) {}
    @Override public void destroy() {}
}
