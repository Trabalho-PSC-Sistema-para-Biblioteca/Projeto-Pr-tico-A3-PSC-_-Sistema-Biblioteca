/**
 * Esta classe representa um empréstimo de livro.
 * Os dados desta classe são obtidos de uma tabela do banco de dados.
 * O código-fonte foi descompilado de um arquivo .class usando o decompilador FernFlower.
 * 
 * @author [Seu Nome]
 * @version 1.0
 * @since 2024-06-16
 */// Source code is decompiled from a .class file using FernFlower decompiler.
package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Emprestimo {
    private int livroId;
    private int alunoId;
    private String dataEmprestimo;
    private String prazo;
    private boolean reservado;
    private int id;
    private int idFuncionarioResponsavel;

    /**
     *@author tayna
     * Construtor da classe Emprestimo.
     * @param livroId O identificador do livro emprestado.
     * @param alunoId O identificador do aluno que realizou o empréstimo.
     * @param dataEmprestimo A data em que o empréstimo foi realizado.
     * @param prazo O prazo de devolução do livro.
     * @param reservado Indica se o livro está reservado ou não.
     * @param id O identificador do empréstimo.
     * @param idFuncionarioResponsavel O identificador do funcionário responsável pelo empréstimo.
     */
    public Emprestimo(int livroId, int alunoId, String dataEmprestimo, String prazo, boolean reservado, int id, int idFuncionarioResponsavel) {
        this.livroId = livroId;
        this.alunoId = alunoId;
        this.dataEmprestimo = dataEmprestimo;
        this.prazo = prazo;
        this.reservado = reservado;
        this.id = id;
        this.idFuncionarioResponsavel = idFuncionarioResponsavel;
    }

    public int getLivroId() {
        return livroId;
    }

    public void setLivroId(int livroId) {
        this.livroId = livroId;
    }

    public int getAlunoId() {
        return alunoId;
    }

    public void setAlunoId(int alunoId) {
        this.alunoId = alunoId;
    }

    public String getDataEmprestimo() {
        return dataEmprestimo;
    }

    public void setDataEmprestimo(String dataEmprestimo) {
        this.dataEmprestimo = dataEmprestimo;
    }

    public String getPrazo() {
        return prazo;
    }

    public void setPrazo(String prazo) {
        this.prazo = prazo;
    }

    public boolean isReservado() {
        return reservado;
    }

    public void setReservado(boolean reservado) {
        this.reservado = reservado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdFuncionarioResponsavel() {
        return idFuncionarioResponsavel;
    }

    public void setIdFuncionarioResponsavel(int idFuncionarioResponsavel) {
        this.idFuncionarioResponsavel = idFuncionarioResponsavel;
    }

    /**
     * Retorna todos os empréstimos cadastrados no banco de dados.
     * @return Uma lista de empréstimos.
     */
    public static List<Emprestimo> listarEmprestimos() {
        List<Emprestimo> emprestimos = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = ConnectionFactory.getConnection();
            String sql = "SELECT * FROM emprestimo";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
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
        } finally {
            ConnectionFactory.close(conn, stmt, rs);
        }

        return emprestimos;
    }
}
