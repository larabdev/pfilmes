package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import model.Avaliacao;
import util.ConnectionFactory;

public class AvaliacaoDAO {

    private final ConnectionFactory cf = ConnectionFactory.getInstance();

    public void avaliar(Avaliacao a) {
        // CREATE no banco: INSERT cria uma nova avaliacao.
        String sql = "INSERT INTO avaliacoes (filme_id, categoria_id, nota, comentario) VALUES (?, ?, ?, ?)";

        try (Connection conn = cf.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, a.getFilmeId());
            stmt.setInt(2, a.getCategoriaId());
            stmt.setInt(3, a.getNota());
            stmt.setString(4, a.getComentario());
            stmt.execute();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) a.setId(rs.getInt(1));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao registrar avaliacao.", e);
        }
    }

    public List<Avaliacao> listar() {
        // READ no banco: SELECT lista todas as avaliacoes.
        List<Avaliacao> lista = new ArrayList<>();
        String sql = "SELECT * FROM avaliacoes ORDER BY id DESC";

        try (Connection conn = cf.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(montarAvaliacao(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar avaliacoes.", e);
        }

        return lista;
    }

    public Avaliacao buscarPorId(int id) {
        // READ no banco: SELECT busca uma avaliacao especifica pelo id.
        String sql = "SELECT * FROM avaliacoes WHERE id = ?";

        try (Connection conn = cf.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return montarAvaliacao(rs);

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar avaliacao por id.", e);
        }

        return null;
    }

    public void atualizar(Avaliacao a) {
        // UPDATE no banco: altera os dados de uma avaliacao existente.
        String sql = "UPDATE avaliacoes SET filme_id = ?, categoria_id = ?, nota = ?, comentario = ? WHERE id = ?";

        try (Connection conn = cf.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, a.getFilmeId());
            stmt.setInt(2, a.getCategoriaId());
            stmt.setInt(3, a.getNota());
            stmt.setString(4, a.getComentario());
            stmt.setInt(5, a.getId());
            stmt.execute();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar avaliacao.", e);
        }
    }

    public void deletar(int id) {
        // DELETE no banco: remove a avaliacao pelo id.
        String sql = "DELETE FROM avaliacoes WHERE id = ?";

        try (Connection conn = cf.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.execute();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar avaliacao.", e);
        }
    }

    public List<String> ranking(int categoriaId) {
        // READ no banco: consulta agregada para mostrar ranking por media das notas.
        List<String> lista = new ArrayList<>();

        String sql = "SELECT f.titulo, AVG(a.nota) AS media, COUNT(*) AS total "
                + "FROM avaliacoes a "
                + "JOIN filmes f ON f.id = a.filme_id "
                + "WHERE a.categoria_id = ? "
                + "GROUP BY f.id, f.titulo "
                + "ORDER BY media DESC";

        try (Connection conn = cf.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, categoriaId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                lista.add(rs.getString("titulo")
                        + " | Media: " + String.format(Locale.US, "%.1f", rs.getDouble("media"))
                        + " | Avaliacoes: " + rs.getInt("total"));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao gerar ranking.", e);
        }

        return lista;
    }

    private Avaliacao montarAvaliacao(ResultSet rs) throws SQLException {
        Avaliacao a = new Avaliacao();
        a.setId(rs.getInt("id"));
        a.setFilmeId(rs.getInt("filme_id"));
        a.setCategoriaId(rs.getInt("categoria_id"));
        a.setNota(rs.getInt("nota"));
        a.setComentario(rs.getString("comentario"));
        return a;
    }
}
