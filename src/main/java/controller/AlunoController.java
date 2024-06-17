package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Aluno;
import model.ConnectionFactory;

/**
 * Classe de controle para gerenciar operações CRUD de alunos no banco de dados.
 */

public class AlunoController {

    /**
     * Insere um novo aluno no banco de dados.
     *
     * @param aluno Objeto Aluno a ser inserido.
     */
    public void inserirAluno(Aluno aluno) {
        String sql = "INSERT INTO aluno (nome, ra) VALUES (?, ?)";

        try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, aluno.getNome());
            stmt.setString(2, aluno.getRa());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Atualiza as informações de um aluno no banco de dados.
     *
     * @param aluno Objeto Aluno com os novos dados.
     */
    public void alterarAluno(Aluno aluno) {
        String sql = "UPDATE aluno SET nome = ? WHERE ra = ?";

        try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, aluno.getNome());
            stmt.setString(2, aluno.getRa());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Remove um aluno do banco de dados com base no RA (Registro Acadêmico).
     *
     * @param ra RA do aluno a ser removido.
     */
    public void removerAluno(String ra) {
        String sql = "DELETE FROM aluno WHERE ra = ?";

        try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, ra);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Lista todos os alunos presentes no banco de dados.
     *
     * @return Lista de objetos Aluno.
     */
    public List<Aluno> listarAlunos() {
        String sql = "SELECT * FROM aluno";
        List<Aluno> alunos = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Aluno aluno = new Aluno(rs.getString("nome"), rs.getString("ra"));
                alunos.add(aluno);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return alunos;
    }
}
