package service;

import dao.FilmeDAO;
import model.Filme;

import java.util.List;

public class FilmeService {

    private FilmeDAO dao = new FilmeDAO();

    public void salvar(Filme filme) {
        if (filme.getTitulo() == null || filme.getTitulo().trim().isEmpty()) {
            throw new RuntimeException("Título é obrigatório.");
        }
        if (filme.getAnoLancamento() < 1888 || filme.getAnoLancamento() > 2100) {
            throw new RuntimeException("Ano de lançamento inválido.");
        }
        dao.salvar(filme);
    }

    public List<Filme> listar() {
        return dao.listar();
    }

    public Filme buscarPorId(int id) {
        return dao.buscarPorId(id);
    }

    public void atualizar(Filme filme) {
        if (filme.getTitulo() == null || filme.getTitulo().trim().isEmpty()) {
            throw new RuntimeException("Título é obrigatório.");
        }
        dao.atualizar(filme);
    }

    public void deletar(int id) {
        dao.deletar(id);
    }
}
