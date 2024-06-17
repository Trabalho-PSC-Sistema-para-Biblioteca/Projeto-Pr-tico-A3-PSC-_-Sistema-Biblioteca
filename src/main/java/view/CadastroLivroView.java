/**
 * Esta classe representa a interface gráfica para cadastrar um novo livro no sistema de biblioteca.
 * Os dados são inseridos em uma tabela do banco de dados.
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
import javax.swing.*;
import model.ConnectionFactory;
import model.Funcionario;
/**
 *
 * @author raiss
 */
public class CadastroLivroView extends JFrame {

    private final Funcionario funcionario;
    private final ListarLivrosView listarLivrosView;
    private final JTextField tituloField;
    private final JTextField autorField;
    private final JTextField generoField;
    private final JTextField quantidadeField;
    private final JButton cadastrarButton;

    /**
     * Construtor da classe CadastroLivroView.
     *
     * @param funcionario O funcionário responsável pelo cadastro.
     * @param listarLivrosView A instância da ListarLivrosView associada.
     */

    public CadastroLivroView(Funcionario funcionario, ListarLivrosView listarLivrosView) {
        this.funcionario = funcionario;
        this.listarLivrosView = listarLivrosView;
        setTitle("Sistema de Biblioteca - Cadastro de Livro");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel tituloLabel = new JLabel("Título:");
        tituloLabel.setBounds(20, 20, 100, 25);
        add(tituloLabel);

        tituloField = new JTextField();
        tituloField.setBounds(120, 20, 200, 25);
        add(tituloField);

        JLabel autorLabel = new JLabel("Autor:");
        autorLabel.setBounds(20, 60, 100, 25);
        add(autorLabel);

        autorField = new JTextField();
        autorField.setBounds(120, 60, 200, 25);
        add(autorField);

        JLabel generoLabel = new JLabel("Gênero:");
        generoLabel.setBounds(20, 100, 100, 25);
        add(generoLabel);

        generoField = new JTextField();
        generoField.setBounds(120, 100, 200, 25);
        add(generoField);

        JLabel quantidadeLabel = new JLabel("Quantidade:");
        quantidadeLabel.setBounds(20, 140, 100, 25);
        add(quantidadeLabel);

        quantidadeField = new JTextField();
        quantidadeField.setBounds(120, 140, 200, 25);
        add(quantidadeField);

        cadastrarButton = new JButton("Cadastrar");
        cadastrarButton.setBounds(120, 180, 100, 25);
        add(cadastrarButton);

        cadastrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cadastrarLivro();
            }
        });
    }

    /**
     * Realiza o cadastro do livro no banco de dados.
     */
    private void cadastrarLivro() {
        String titulo = tituloField.getText();
        String autor = autorField.getText();
        String genero = generoField.getText();
        int quantidade = Integer.parseInt(quantidadeField.getText());

        try (Connection conn = ConnectionFactory.getConnection()) {
            String sql = "INSERT INTO livro (titulo, autor, genero, quantidade) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, titulo);
            stmt.setString(2, autor);
            stmt.setString(3, genero);
            stmt.setInt(4, quantidade);

            stmt.executeUpdate();

            JOptionPane.showMessageDialog(this, "Livro cadastrado com sucesso.");

            // Atualizar a tabela de livros após o cadastro
            listarLivrosView.carregarLivros();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao conectar ao banco de dados.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
