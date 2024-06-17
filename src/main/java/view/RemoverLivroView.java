/**
 * Esta classe representa a interface gráfica de remoção de um livro.
 * O código-fonte foi descompilado de um arquivo .class usando o decompilador FernFlower.
 *
 * @author [Seu Nome]
 * @version 1.0
 * @since 2024-06-16
 *///
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
public class RemoverLivroView extends JFrame {

    private final JTextField idField;
    private final JButton removerButton;
    private final ListarLivrosView listarLivrosView;

    /**
     * Constrói uma nova instância da RemoverLivroView.
     *
     * @param listarLivrosView A instância da ListarLivrosView associada
     */
    public RemoverLivroView(ListarLivrosView listarLivrosView) {
        this.listarLivrosView = listarLivrosView; // Atribui a referência passada para listarLivrosView
        setTitle("Remover Livro");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel idLabel = new JLabel("ID do Livro:");
        idLabel.setBounds(20, 20, 100, 25);
        add(idLabel);

        idField = new JTextField();
        idField.setBounds(120, 20, 150, 25);
        add(idField);

        removerButton = new JButton("Remover");
        removerButton.setBounds(120, 60, 100, 25);
        add(removerButton);

        removerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removerLivro();
            }
        });
    }

    /**
     * Remove o livro do banco de dados.
     */
    private void removerLivro() {
        int id = Integer.parseInt(idField.getText());

        // Verifica se há empréstimos pendentes para o livro antes de excluir
        if (temEmprestimosPendentes(id)) {
            JOptionPane.showMessageDialog(this, "Não é possível excluir o livro pois há empréstimos pendentes associados a ele.");
            return;
        }

        try (Connection conn = ConnectionFactory.getConnection()) {
            String sql = "DELETE FROM livro WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                JOptionPane.showMessageDialog(this, "Livro removido com sucesso.");
                listarLivrosView.carregarLivros(); // Chama o método carregarLivros() da ListarLivrosView
            } else {
                JOptionPane.showMessageDialog(this, "Livro não encontrado.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao conectar ao banco de dados.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Verifica se há empréstimos pendentes associados ao livro.
     *
     * @param livroId O ID do livro
     * @return true se houver empréstimos pendentes, caso contrário false
     */
    private boolean temEmprestimosPendentes(int livroId) {
        try (Connection conn = ConnectionFactory.getConnection()) {
            String sql = "SELECT COUNT(*) AS total FROM emprestimo WHERE livro_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, livroId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int totalEmprestimos = rs.getInt("total");
                return totalEmprestimos > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao verificar empréstimos pendentes.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }
}
