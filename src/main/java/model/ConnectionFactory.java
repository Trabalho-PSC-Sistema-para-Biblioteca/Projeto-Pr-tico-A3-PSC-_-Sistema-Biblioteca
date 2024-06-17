// Source code is decompiled from a .class file using FernFlower decompiler.
/**
 * Esta classe fornece métodos para gerenciar conexões com o banco de dados MySQL.
 * O código-fonte foi descompilado de um arquivo .class usando o decompilador FernFlower.
 * 
 * @author [Seu Nome]
 * @version 1.0
 * @since 2024-06-16
 */
package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConnectionFactory {
    private static final String URL = "jdbc:mysql://localhost:3306/biblioteca";
    private static final String USER = "root";
    private static final String PASSWORD = "Admin123";

    /**
     * Obtém uma conexão com o banco de dados.
     * @return A conexão estabelecida.
     * @throws SQLException Se houver um erro ao conectar ao banco de dados.
     *@author tayna
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    /**
     * Fecha a conexão com o banco de dados.
     * @param conn A conexão a ser fechada.
     */
    public static void close(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Fecha a conexão com o banco de dados.
     * @param conn A conexão a ser fechada.
     * @param stmt O statement associado à conexão.
     */
    public static void close(Connection conn, PreparedStatement stmt) {
        close(conn);
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Fecha a conexão com o banco de dados.
     * @param conn A conexão a ser fechada.
     * @param stmt O statement associado à conexão.
     * @param rs O result set associado à conexão.
     */
    public static void close(Connection conn, PreparedStatement stmt, ResultSet rs) {
        close(conn, stmt);
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

