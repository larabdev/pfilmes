package util;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionFactory {
    private static final String URL  = "jdbc:mysql://localhost:3306/filmes_db?useSSL=false&serverTimezone=America/Sao_Paulo";
    private static final String USER = "larab";
    private static final String PASS = "lara9904B.#";

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao conectar ao banco de dados: " + e.getMessage(), e);
        }
    }
}
