package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * ConnectionFactory - Padrão Singleton
 * Garante uma única instância da fábrica de conexões durante o ciclo de vida da aplicação.
 */
public class ConnectionFactory {

    private static final String URL = System.getenv().getOrDefault(
            "DB_URL",
            "jdbc:mysql://localhost:3306/filmes_db?useSSL=false&serverTimezone=America/Sao_Paulo&allowPublicKeyRetrieval=true"
    );
    private static final String USER = System.getenv().getOrDefault("DB_USER", "larab");
    private static final String PASS = System.getenv().getOrDefault("DB_PASS", "lara9904B.#");
    private static ConnectionFactory instance;
    
    private ConnectionFactory() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Driver MySQL não encontrado.", e);
        }
    }

    /**
     * Retorna a única instância de ConnectionFactory 
     */
    public static ConnectionFactory getInstance() {
        if (instance == null) {
            synchronized (ConnectionFactory.class) {
                if (instance == null) {
                    instance = new ConnectionFactory();
                }
            }
        }
        return instance;
    }

    /**
     * Abre e retorna uma nova conexão com o banco de dados.
     */
    public Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao obter conexão com o banco de dados.", e);
        }
    }
}
