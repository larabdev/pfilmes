package controller;

import model.Filme;
import service.FilmeService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;

public class FilmeController extends HttpServlet {

    private FilmeService service = new FilmeService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");
        if (action == null) action = "listar";

        switch (action) {

            case "listar":
                req.setAttribute("filmes", service.listar());
                req.getRequestDispatcher("/view/filmes.jsp").forward(req, resp);
                break;

            case "novo":
                req.getRequestDispatcher("/view/formFilme.jsp").forward(req, resp);
                break;

            case "editar":
                int idEditar = Integer.parseInt(req.getParameter("id"));
                Filme filmeEditar = service.buscarPorId(idEditar);
                req.setAttribute("filme", filmeEditar);
                req.getRequestDispatcher("/view/editarFilme.jsp").forward(req, resp);
                break;

            case "deletar":
                int idDeletar = Integer.parseInt(req.getParameter("id"));
                service.deletar(idDeletar);
                resp.sendRedirect(req.getContextPath() + "/filmes");
                break;

            default:
                resp.sendRedirect(req.getContextPath() + "/filmes");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");
        if (action == null) action = "salvar";

        switch (action) {

            case "salvar":
                Filme novoFilme = new Filme();
                novoFilme.setTitulo(req.getParameter("titulo"));
                novoFilme.setAnoLancamento(Integer.parseInt(req.getParameter("ano")));
                novoFilme.setDiretor(req.getParameter("diretor"));
                novoFilme.setGenero(req.getParameter("genero"));
                novoFilme.setSinopse(req.getParameter("sinopse"));
                service.salvar(novoFilme);
                resp.sendRedirect(req.getContextPath() + "/filmes");
                break;

            case "atualizar":
                Filme filmeAtualizar = new Filme();
                filmeAtualizar.setId(Integer.parseInt(req.getParameter("id")));
                filmeAtualizar.setTitulo(req.getParameter("titulo"));
                filmeAtualizar.setAnoLancamento(Integer.parseInt(req.getParameter("ano")));
                filmeAtualizar.setDiretor(req.getParameter("diretor"));
                filmeAtualizar.setGenero(req.getParameter("genero"));
                filmeAtualizar.setSinopse(req.getParameter("sinopse"));
                service.atualizar(filmeAtualizar);
                resp.sendRedirect(req.getContextPath() + "/filmes");
                break;

            default:
                resp.sendRedirect(req.getContextPath() + "/filmes");
        }
    }
}
