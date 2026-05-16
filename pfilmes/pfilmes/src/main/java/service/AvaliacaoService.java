package service;

import dao.AvaliacaoDAO;
import dao.CategoriaDAO;
import model.Avaliacao;
import model.Categoria;

import java.util.List;

public class AvaliacaoService {

    private AvaliacaoDAO avaliacaoDAO = new AvaliacaoDAO();
    private CategoriaDAO categoriaDAO = new CategoriaDAO();

    public void avaliar(Avaliacao a) {
        if (a.getNota() < 1 || a.getNota() > 5) {
            throw new RuntimeException("Nota deve ser entre 1 e 5.");
        }
        avaliacaoDAO.avaliar(a);
    }

    public List<String> ranking(int categoriaId) {
        return avaliacaoDAO.ranking(categoriaId);
    }

    public List<Categoria> listarCategorias() {
        return categoriaDAO.listar();
    }
}
