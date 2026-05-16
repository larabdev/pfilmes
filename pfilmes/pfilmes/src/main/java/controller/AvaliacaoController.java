package controller;

import model.Avaliacao;
import service.AvaliacaoService;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

public class AvaliacaoController extends HttpServlet {

    private AvaliacaoService service = new AvaliacaoService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");
        if (action == null) action = "form";

        switch (action) {

            case "form":
                // Exibe o formulário de avaliação para um filme específico
                String filmeId = req.getParameter("filmeId");
                req.setAttribute("filmeId", filmeId);
                req.setAttribute("categorias", service.listarCategorias());
                req.getRequestDispatcher("/view/avaliar.jsp").forward(req, resp);
                break;

            case "ranking":
                // Exibe o ranking de um gênero/categoria escolhido
                int categoriaId = Integer.parseInt(req.getParameter("categoriaId"));
                List<String> ranking = service.ranking(categoriaId);
                req.setAttribute("ranking", ranking);
                req.setAttribute("categorias", service.listarCategorias());
                req.setAttribute("categoriaSelecionada", categoriaId);
                req.getRequestDispatcher("/view/ranking.jsp").forward(req, resp);
                break;

            default:
                resp.sendRedirect(req.getContextPath() + "/filmes");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        Avaliacao a = new Avaliacao();
        a.setFilmeId(Integer.parseInt(req.getParameter("filme")));
        a.setCategoriaId(Integer.parseInt(req.getParameter("categoria")));
        a.setNota(Integer.parseInt(req.getParameter("nota")));
        a.setComentario(req.getParameter("comentario"));

        service.avaliar(a);
        resp.sendRedirect(req.getContextPath() + "/filmes");
    }
}
