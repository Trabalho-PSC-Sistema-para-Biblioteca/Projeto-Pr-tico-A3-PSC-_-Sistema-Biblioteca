package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.ConnectionFactory;
import model.Funcionario;

/**
 * Classe de controle para gerenciar operações CRUD de funcionários no banco de dados.
 * Esta classe fornece métodos para login, inserção, atualização e remoção de funcionários.
 */

public class FuncionarioController {
    /**
     * Autentica um funcionário com base no login e senha fornecidos.
     *
     * @param login Login do funcionário.
     * @param senha Senha do funcionário.
     * @return Um objeto Funcionario se as credenciais forem válidas, caso contrário, retorna null.
     */
    
    public Funcionario login(String login, String senha) {
        String sql = "SELECT * FROM funcionario WHERE login = ? AND senha = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, login);
            stmt.setString(2, senha);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Funcionario(
                        rs.getInt("id"),
                        rs.getString("login"),
                        rs.getString("senha"),
                        rs.getString("nome_completo")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

     /**
     * Insere um novo funcionário no banco de dados.
     *
     * @param funcionario Objeto Funcionario a ser inserido.
     */

    public void inserirFuncionario(Funcionario funcionario) {
        String sql = "INSERT INTO funcionario (login, senha, nome_completo) VALUES (?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, funcionario.getLogin());
            stmt.setString(2, funcionario.getSenha());
            stmt.setString(3, funcionario.getNomeCompleto());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
/**
     * Atualiza as informações de um funcionário no banco de dados.
     *
     * @param funcionario Objeto Funcionario com os novos dados.
     */
    
    public void alterarFuncionario(Funcionario funcionario) {
        String sql = "UPDATE funcionario SET login = ?, senha = ?, nome_completo = ? WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, funcionario.getLogin());
            stmt.setString(2, funcionario.getSenha());
            stmt.setString(3, funcionario.getNomeCompleto());
            stmt.setInt(4, funcionario.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

/**
     * Remove um funcionário do banco de dados com base no ID fornecido.
     *
     * @param id ID do funcionário a ser removido.
     */
    
    public void removerFuncionario(int id) {
        String sql = "DELETE FROM funcionario WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
