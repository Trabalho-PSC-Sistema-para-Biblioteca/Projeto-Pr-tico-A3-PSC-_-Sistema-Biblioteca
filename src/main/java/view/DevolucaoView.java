/**
 * Esta classe representa a interface gráfica para devolução de livros.
 * O código-fonte foi descompilado de um arquivo .class usando o decompilador FernFlower.
 *
 * @author [Seu Nome]
 * @version 1.0
 * @since 2024-06-16
 */// Source code is decompiled from a .class file using FernFlower decompiler.
package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import javax.swing.*;
import model.ConnectionFactory;

/**
 *
 * @author raiss
 */
public class DevolucaoView extends JFrame {

    private final JTextField txtLivroId;
    private final JTextField txtAlunoRa;
    private final JButton btnDevolver;
    private final ListarLivrosView listarLivrosView;

    /**
     * Construtor da classe DevolucaoView.
     *
     * @param listarLivrosView A referência para a ListarLivrosView associada a
     * esta interface.
     */
    public DevolucaoView(ListarLivrosView listarLivrosView) {
        this.listarLivrosView = listarLivrosView;

        setTitle("Devolução de Livro");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Label e campo de texto para ID do Livro
        JLabel lblLivroId = new JLabel("ID do Livro:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(lblLivroId, gbc);

        txtLivroId = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 0;
        add(txtLivroId, gbc);

        // Label e campo de texto para RA do Aluno
        JLabel lblAlunoRa = new JLabel("RA do Aluno:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(lblAlunoRa, gbc);

        txtAlunoRa = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(txtAlunoRa, gbc);

        // Botão de Devolução
        btnDevolver = new JButton("Devolver");
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(btnDevolver, gbc);

        btnDevolver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                devolverLivro();
            }
        });
    }

    /**
     * Método para realizar a devolução do livro.
     */
    private void devolverLivro() {
        int livroId = Integer.parseInt(txtLivroId.getText());
        String alunoRa = txtAlunoRa.getText();
        LocalDate dataDevolucao = LocalDate.now();

        String sqlBuscaEmprestimo = "SELECT id FROM emprestimo WHERE livro_id = ? AND aluno_ra = ? AND data_devolucao IS NULL";
        String sqlDevolucao = "UPDATE emprestimo SET data_devolucao = ? WHERE id = ?";
        String sqlUpdateLivro = "UPDATE livro SET quantidade = quantidade + 1 WHERE id = ?";
        String sqlRemoverEmprestimo = "DELETE FROM emprestimo WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement stmtBuscaEmprestimo = conn.prepareStatement(sqlBuscaEmprestimo); PreparedStatement stmtDevolucao = conn.prepareStatement(sqlDevolucao); PreparedStatement stmtUpdateLivro = conn.prepareStatement(sqlUpdateLivro); PreparedStatement stmtRemoverEmprestimo = conn.prepareStatement(sqlRemoverEmprestimo)) {

            // Busca o ID do empréstimo com base no livro e no aluno
            stmtBuscaEmprestimo.setInt(1, livroId);
            stmtBuscaEmprestimo.setString(2, alunoRa);
            ResultSet rs = stmtBuscaEmprestimo.executeQuery();

            if (rs.next()) {
                int emprestimoId = rs.getInt("id");

                // Atualiza a data de devolução na tabela emprestimo
                stmtDevolucao.setDate(1, java.sql.Date.valueOf(dataDevolucao));
                stmtDevolucao.setInt(2, emprestimoId);
                stmtDevolucao.executeUpdate();

                // Atualiza a quantidade disponível do livro na tabela livro
                stmtUpdateLivro.setInt(1, livroId);
                stmtUpdateLivro.executeUpdate();

                // Remove o nome do aluno do registro de empréstimo
                stmtRemoverEmprestimo.setInt(1, emprestimoId);
                stmtRemoverEmprestimo.executeUpdate();

                JOptionPane.showMessageDialog(this, "Livro devolvido com sucesso.");
                listarLivrosView.carregarLivros();
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Não foi encontrado empréstimo pendente para o livro e aluno informados.",
                        "Empréstimo não encontrado", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao devolver o livro.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
