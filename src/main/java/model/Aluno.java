// Source code is decompiled from a .class file using FernFlower decompiler.
package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Aluno {
    private String nome;
    private String ra;

    /**
     * Construtor da classe Aluno.
     * @param nome O nome do aluno.
     * @param ra O RA (Registro Acadêmico) do aluno.
     *@author tayna
     */
    public Aluno(String nome, String ra) {
        this.nome = nome;
        this.ra = ra;
    }

    /**
     * Obtém o nome do aluno.
     * @return O nome do aluno.
     */
    public String getNome() {
        return nome;
    }

    /**
     * Define o nome do aluno.
     * @param nome O novo nome do aluno.
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Obtém o RA (Registro Acadêmico) do aluno.
     * @return O RA do aluno.
     */
    public String getRa() {
        return ra;
    }

    /**
     * Define o RA (Registro Acadêmico) do aluno.
     * @param ra O novo RA do aluno.
     */
    public void setRa(String ra) {
        this.ra = ra;
    }

    /**
     * Retorna todos os alunos cadastrados no banco de dados.
     * @return Uma lista de alunos.
     */
    public static List<Aluno> listarAlunos() {
        List<Aluno> alunos = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = ConnectionFactory.getConnection();
            String sql = "SELECT * FROM aluno";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Aluno aluno = new Aluno(
                    rs.getString("nome"),
                    rs.getString("ra")
                );
                alunos.add(aluno);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionFactory.close(conn, stmt, rs);
        }
        return alunos;
    }
}
