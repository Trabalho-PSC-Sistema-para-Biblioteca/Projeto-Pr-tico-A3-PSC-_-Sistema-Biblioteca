/**
 * Esta classe representa um livro.
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
/**
 * 
 *@author tayna
 */
public class Livro {
    private String titulo;
    private String autor;
    private String genero;
    private int quantidade;
    private String status;
    private int id;
    private int idFuncionarioResponsavel;

    /**
     * Construtor da classe Livro.
     * @param titulo O título do livro.
     * @param autor O autor do livro.
     * @param genero O gênero do livro.
     * @param quantidade A quantidade disponível do livro.
     * @param status O status do livro (reservado ou não).
     * @param id O identificador do livro.
     * @param idFuncionarioResponsavel O identificador do funcionário responsável pelo livro.
     */
    public Livro(String titulo, String autor, String genero, int quantidade, String status, int id, int idFuncionarioResponsavel) {
        this.titulo = titulo;
        this.autor = autor;
        this.genero = genero;
        this.quantidade = quantidade;
        this.status = status;
        this.id = id;
        this.idFuncionarioResponsavel = idFuncionarioResponsavel;
    }


	public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
     * Retorna todos os livros cadastrados no banco de dados.
     * @return Uma lista de livros.
     */
    public static List<Livro> listarLivros() {
        List<Livro> livros = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = ConnectionFactory.getConnection();
            String sql = "SELECT * FROM livro";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Livro livro = new Livro(
                    rs.getString("titulo"),
                    rs.getString("autor"),
                    rs.getString("genero"),
                    rs.getInt("quantidade"),
                    rs.getString("status"),
                    rs.getInt("id"),
                    rs.getInt("id_funcionario_responsavel")
                );
                livros.add(livro);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionFactory.close(conn, stmt, rs);
        }

        return livros;
    }
}
