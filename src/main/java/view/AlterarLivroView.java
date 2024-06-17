/**
 * Esta classe representa a interface gráfica para alterar informações de um livro.
 * Os dados são obtidos de uma tabela do banco de dados.
 * O código-fonte foi descompilado de um arquivo .class usando o decompilador FernFlower.
 *
 * @author [Seu Nome]
 * @version 1.0
 * @since 2024-06-16
 */// Source code is decompiled from a .class file using FernFlower decompiler.
package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.*;
import model.ConnectionFactory;
/**
 *
 * @author raiss
 */
public class AlterarLivroView extends JFrame {

    private final JTextField idField;
    private final JTextField tituloField;
    private final JTextField autorField;
    private final JTextField generoField;
    private final JTextField quantidadeField;
    private final JButton buscarButton;
    private final JButton atualizarButton;
    private final ListarLivrosView listarLivrosView;

    /**
     * Construtor da classe AlterarLivroView.
     *
     * @param listarLivrosView A instância da ListarLivrosView associada.
     */
    public AlterarLivroView(ListarLivrosView listarLivrosView) {
        this.listarLivrosView = listarLivrosView;
        setTitle("Alterar Livro");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel idLabel = new JLabel("ID:");
        idLabel.setBounds(20, 20, 100, 25);
        add(idLabel);

        idField = new JTextField();
        idField.setBounds(120, 20, 200, 25);
        add(idField);

        buscarButton = new JButton("Buscar");
        buscarButton.setBounds(120, 60, 100, 25);
        add(buscarButton);

        JLabel tituloLabel = new JLabel("Título:");
        tituloLabel.setBounds(20, 100, 100, 25);
        add(tituloLabel);

        tituloField = new JTextField();
        tituloField.setBounds(120, 100, 200, 25);
        add(tituloField);

        JLabel autorLabel = new JLabel("Autor:");
        autorLabel.setBounds(20, 140, 100, 25);
        add(autorLabel);

        autorField = new JTextField();
        autorField.setBounds(120, 140, 200, 25);
        add(autorField);

        JLabel generoLabel = new JLabel("Gênero:");
        generoLabel.setBounds(20, 180, 100, 25);
        add(generoLabel);

        generoField = new JTextField();
        generoField.setBounds(120, 180, 200, 25);
        add(generoField);

        JLabel quantidadeLabel = new JLabel("Quantidade:");
        quantidadeLabel.setBounds(20, 220, 100, 25);
        add(quantidadeLabel);

        quantidadeField = new JTextField();
        quantidadeField.setBounds(120, 220, 200, 25);
        add(quantidadeField);

        atualizarButton = new JButton("Atualizar");
        atualizarButton.setBounds(120, 260, 100, 25);
        add(atualizarButton);

        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarLivro();
            }
        });

        atualizarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                atualizarLivro();
            }
        });
    }

    /**
     * Busca as informações do livro a ser alterado.
     */
    private void buscarLivro() {
        int id = Integer.parseInt(idField.getText());

        try (Connection conn = ConnectionFactory.getConnection()) {
            String sql = "SELECT * FROM livro WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                tituloField.setText(rs.getString("titulo"));
                autorField.setText(rs.getString("autor"));
                generoField.setText(rs.getString("genero"));
                quantidadeField.setText(rs.getString("quantidade"));
            } else {
                JOptionPane.showMessageDialog(this, "Livro não encontrado.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao conectar ao banco de dados.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Atualiza as informações do livro no banco de dados.
     */
    private void atualizarLivro() {
        int id = Integer.parseInt(idField.getText());
        String titulo = tituloField.getText();
        String autor = autorField.getText();
        String genero = generoField.getText();
        int quantidade = Integer.parseInt(quantidadeField.getText());

        try (Connection conn = ConnectionFactory.getConnection()) {
            String sql = "UPDATE livro SET titulo = ?, autor = ?, genero = ?, quantidade = ? WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, titulo);
            stmt.setString(2, autor);
            stmt.setString(3, genero);
            stmt.setInt(4, quantidade);
            stmt.setInt(5, id);

            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Livro atualizado com sucesso.");
            listarLivrosView.carregarLivros(); // Atualiza a tabela de livros após a alteração
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao conectar ao banco de dados.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
