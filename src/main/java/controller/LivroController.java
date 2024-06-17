package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.ConnectionFactory;
import model.Livro;

/**
 * Classe de controle para gerenciar operações CRUD de livros no banco de dados.
 * Esta classe fornece métodos para inserção, atualização, remoção e listagem de
 * livros.
 */
public class LivroController {

    /**
     * Insere um novo livro no banco de dados.
     *
     * @param livro Objeto Livro a ser inserido.
     * @throws SQLException Se ocorrer um erro ao acessar o banco de dados.
     */
    
    public void inserirLivro(Livro livro) {
        String sql = "INSERT INTO livro (titulo, autor, genero, quantidade, id) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, livro.getTitulo());
            stmt.setString(2, livro.getAutor());
            stmt.setString(3, livro.getGenero());
            stmt.setInt(4, livro.getQuantidade());
            stmt.setLong(5, livro.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Atualiza as informações de um livro no banco de dados.
     *
     * @param livro Objeto Livro com os novos dados.
     * @throws SQLException Se ocorrer um erro ao acessar o banco de dados.
     */
    
    public void alterarLivro(Livro livro) {
        String sql = "UPDATE livro SET titulo = ?, autor = ?, genero = ?, quantidade = ? WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, livro.getTitulo());
            stmt.setString(2, livro.getAutor());
            stmt.setString(3, livro.getGenero());
            stmt.setInt(4, livro.getQuantidade());
            stmt.setLong(5, livro.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Remove um livro do banco de dados com base no ID fornecido.
     *
     * @param id ID do livro a ser removido.
     * @throws SQLException Se ocorrer um erro ao acessar o banco de dados.
     */
    
    public void removerLivro(long id) {
        String sql = "DELETE FROM livro WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Lista todos os livros presentes no banco de dados.
     *
     * @return Lista de objetos Livro.
     * @throws SQLException Se ocorrer um erro ao acessar o banco de dados.
     */
    
    public List<Livro> listarLivros() {
        String sql = "SELECT * FROM livro";
        List<Livro> livros = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Livro livro = new Livro("Título", "Autor", "Gênero", 10, "Disponível", 1, 1);
                livros.add(livro);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return livros;
    }
}
