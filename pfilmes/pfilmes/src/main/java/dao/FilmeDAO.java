package dao;

import model.Filme;
import util.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FilmeDAO {

    public void salvar(Filme filme) {
        String sql = "INSERT INTO filmes (titulo, ano_lancamento, diretor, genero, sinopse) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, filme.getTitulo());
            stmt.setInt(2, filme.getAnoLancamento());
            stmt.setString(3, filme.getDiretor());
            stmt.setString(4, filme.getGenero());
            stmt.setString(5, filme.getSinopse());
            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Filme> listar() {
        List<Filme> lista = new ArrayList<>();
        String sql = "SELECT * FROM filmes ORDER BY titulo";

        try (Connection conn = ConnectionFactory.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

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

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

    public Filme buscarPorId(int id) {
        String sql = "SELECT * FROM filmes WHERE id = ?";
        Filme f = null;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    f = new Filme();
                    f.setId(rs.getInt("id"));
                    f.setTitulo(rs.getString("titulo"));
                    f.setAnoLancamento(rs.getInt("ano_lancamento"));
                    f.setDiretor(rs.getString("diretor"));
                    f.setGenero(rs.getString("genero"));
                    f.setSinopse(rs.getString("sinopse"));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return f;
    }

    public void atualizar(Filme filme) {
        String sql = "UPDATE filmes SET titulo=?, ano_lancamento=?, diretor=?, genero=?, sinopse=? WHERE id=?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, filme.getTitulo());
            stmt.setInt(2, filme.getAnoLancamento());
            stmt.setString(3, filme.getDiretor());
            stmt.setString(4, filme.getGenero());
            stmt.setString(5, filme.getSinopse());
            stmt.setInt(6, filme.getId());
            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deletar(int id) {
        String sql = "DELETE FROM filmes WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
