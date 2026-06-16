package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Filme;
import util.ConnectionFactory;

public class FilmeDAO {

    private final ConnectionFactory cf = ConnectionFactory.getInstance();

    public void salvar(Filme filme) {
        // CREATE no banco: INSERT cria um novo registro na tabela filmes.
        String sql = "INSERT INTO filmes (titulo, ano_lancamento, diretor, genero, sinopse) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = cf.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, filme.getTitulo());
            stmt.setInt(2, filme.getAnoLancamento());
            stmt.setString(3, filme.getDiretor());
            stmt.setString(4, filme.getGenero());
            stmt.setString(5, filme.getSinopse());
            stmt.execute();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) filme.setId(rs.getInt(1));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar filme.", e);
        }
    }

    public List<Filme> listar() {
        // READ no banco: SELECT lista todos os filmes.
        List<Filme> lista = new ArrayList<>();
        String sql = "SELECT * FROM filmes ORDER BY titulo";

        try (Connection conn = cf.getConnection();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Filme f = new Filme();
                f.setId(rs.getInt("id"));
                f.setTitulo(rs.getString("titulo"));
                f.setAnoLancamento(rs.getInt("ano_lancamento"));
                f.setDiretor(rs.getString("diretor"));
                f.setGenero(rs.getString("genero"));
                f.setSinopse(rs.getString("sinopse"));
                lista.add(f);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar filmes.", e);
        }

        return lista;
    }

    public Filme buscarPorId(int id) {
        // READ no banco: SELECT busca um filme especifico pelo id.
        String sql = "SELECT * FROM filmes WHERE id = ?";

        try (Connection conn = cf.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Filme f = new Filme();
                f.setId(rs.getInt("id"));
                f.setTitulo(rs.getString("titulo"));
                f.setAnoLancamento(rs.getInt("ano_lancamento"));
                f.setDiretor(rs.getString("diretor"));
                f.setGenero(rs.getString("genero"));
                f.setSinopse(rs.getString("sinopse"));
                return f;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar filme por id.", e);
        }

        return null;
    }

    public void atualizar(Filme filme) {
        // UPDATE no banco: altera os dados de um filme existente.
        String sql = "UPDATE filmes SET titulo = ?, ano_lancamento = ?, diretor = ?, genero = ?, sinopse = ? WHERE id = ?";

        try (Connection conn = cf.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, filme.getTitulo());
            stmt.setInt(2, filme.getAnoLancamento());
            stmt.setString(3, filme.getDiretor());
            stmt.setString(4, filme.getGenero());
            stmt.setString(5, filme.getSinopse());
            stmt.setInt(6, filme.getId());
            stmt.execute();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar filme.", e);
        }
    }

    public void deletar(int id) {
        // DELETE no banco: remove o filme pelo id.
        String sql = "DELETE FROM filmes WHERE id = ?";

        try (Connection conn = cf.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.execute();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar filme.", e);
        }
    }
}
