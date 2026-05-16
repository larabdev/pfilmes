package dao;

import model.Avaliacao;
import util.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AvaliacaoDAO {

    public void avaliar(Avaliacao a) {
        String sql = "INSERT INTO avaliacoes (filme_id, categoria_id, nota, comentario) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, a.getFilmeId());
            stmt.setInt(2, a.getCategoriaId());
            stmt.setInt(3, a.getNota());
            stmt.setString(4, a.getComentario());
            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<String> ranking(int categoriaId) {
        List<String> lista = new ArrayList<>();

        String sql = "SELECT f.titulo, AVG(a.nota) AS media, COUNT(a.id) AS total " +
                     "FROM avaliacoes a " +
                     "JOIN filmes f ON f.id = a.filme_id " +
                     "WHERE a.categoria_id = ? " +
                     "GROUP BY f.id, f.titulo " +
                     "ORDER BY media DESC";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, categoriaId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String titulo = rs.getString("titulo");
                    double media  = rs.getDouble("media");
                    int total     = rs.getInt("total");
                    lista.add(titulo + " | Média: " + String.format("%.1f", media) + " | Avaliações: " + total);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }
}
