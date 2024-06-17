// Source code is decompiled from a .class file using FernFlower decompiler.
package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.ConnectionFactory;
import model.Emprestimo;

/**
 * Classe de controle para gerenciar operações de empréstimos no banco de dados.
 */
public class EmprestimoController {

   /**
     * Registra um novo empréstimo no banco de dados.
     *
     * @param emprestimo Objeto Emprestimo a ser registrado.
     */
    public void registrarEmprestimo(Emprestimo emprestimo) {
        String sql = "INSERT INTO emprestimo (livro_id, aluno_id, data_emprestimo, prazo, reservado, id, id_funcionario_responsavel) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, emprestimo.getLivroId());
            stmt.setInt(2, emprestimo.getAlunoId());
            stmt.setString(3, emprestimo.getDataEmprestimo());
            stmt.setString(4, emprestimo.getPrazo());
            stmt.setBoolean(5, emprestimo.isReservado());
            stmt.setInt(6, emprestimo.getId());
            stmt.setInt(7, emprestimo.getIdFuncionarioResponsavel());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

   /**
     * Registra a devolução de um empréstimo no banco de dados.
     *
     * @param id ID do empréstimo a ser devolvido.
     */
    public void registrarDevolucao(long id) {
        String sql = "DELETE FROM emprestimo WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Lista todos os empréstimos registrados no banco de dados.
     *
     * @return Lista de objetos Emprestimo.
     */
    public List<Emprestimo> listarEmprestimos() {
        String sql = "SELECT * FROM emprestimo";
        List<Emprestimo> emprestimos = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Emprestimo emprestimo = new Emprestimo(
                        rs.getInt("livro_id"),
                        rs.getInt("aluno_id"),
                        rs.getString("data_emprestimo"),
                        rs.getString("prazo"),
                        rs.getBoolean("reservado"),
                        rs.getInt("id"),
                        rs.getInt("id_funcionario_responsavel")
                );
                emprestimos.add(emprestimo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return emprestimos;
    }
}

