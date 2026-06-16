package service;

import dao.FilmeDAO;
import model.Filme;

import java.util.List;

public class FilmeService {

    private FilmeDAO dao = new FilmeDAO();

    public void salvar(Filme filme) {
        // CREATE: valida os dados antes de mandar o DAO inserir no banco.
        if (filme.getTitulo() == null || filme.getTitulo().trim().isEmpty()) {
            throw new RuntimeException("Título é obrigatório.");
        }
        if (filme.getAnoLancamento() < 1888 || filme.getAnoLancamento() > 2100) {
            throw new RuntimeException("Ano de lançamento inválido.");
        }
        dao.salvar(filme);
    }

    public List<Filme> listar() {
        // READ: busca todos os filmes cadastrados.
        return dao.listar();
    }

    public Filme buscarPorId(int id) {
        // READ: busca um filme especifico pelo id.
        return dao.buscarPorId(id);
    }

    public void atualizar(Filme filme) {
        // UPDATE: valida e manda o DAO atualizar o filme existente.
        if (filme.getTitulo() == null || filme.getTitulo().trim().isEmpty()) {
            throw new RuntimeException("Título é obrigatório.");
        }
        dao.atualizar(filme);
    }

    public void deletar(int id) {
        // DELETE: manda o DAO remover o filme pelo id.
        dao.deletar(id);
    }
}
