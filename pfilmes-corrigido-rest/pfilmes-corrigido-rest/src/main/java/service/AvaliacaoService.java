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
        // CREATE: valida a nota antes de registrar a avaliacao no banco.
        if (a.getNota() < 1 || a.getNota() > 5) {
            throw new RuntimeException("Nota deve ser entre 1 e 5.");
        }
        avaliacaoDAO.avaliar(a);
    }

    public List<String> ranking(int categoriaId) {
        // READ: consulta o ranking de filmes por categoria.
        return avaliacaoDAO.ranking(categoriaId);
    }

    public List<Avaliacao> listar() {
        // READ: lista todas as avaliacoes.
        return avaliacaoDAO.listar();
    }

    public Avaliacao buscarPorId(int id) {
        // READ: busca uma avaliacao pelo id.
        return avaliacaoDAO.buscarPorId(id);
    }

    public void atualizar(Avaliacao a) {
        // UPDATE: valida a nota antes de atualizar a avaliacao.
        if (a.getNota() < 1 || a.getNota() > 5) {
            throw new RuntimeException("Nota deve ser entre 1 e 5.");
        }
        avaliacaoDAO.atualizar(a);
    }

    public void deletar(int id) {
        // DELETE: remove uma avaliacao pelo id.
        avaliacaoDAO.deletar(id);
    }

    public List<Categoria> listarCategorias() {
        // READ auxiliar: lista as categorias disponiveis para avaliacao.
        return categoriaDAO.listar();
    }
}
