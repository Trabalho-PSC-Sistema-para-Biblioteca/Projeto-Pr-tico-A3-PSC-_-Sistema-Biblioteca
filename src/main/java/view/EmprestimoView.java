/**
 * Esta classe representa a interface gráfica para realizar empréstimos de livros.
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
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.*;
import model.ConnectionFactory;
import model.Funcionario;
import model.UsuarioLogado;

/**
 *
 * @author raiss
 */
public class EmprestimoView extends JDialog {

    private final int livroId;
    private ListarLivrosView listarLivrosView;
    private final JTextField txtLivroId;
    private final JTextField txtTitulo;
    private final JTextField txtAutor;
    private final JTextField txtGenero;
    private final JTextField txtQuantidade;
    private final JTextField txtStatusEmprestimo;
    private final JTextField txtRaAluno;
    private final JTextField txtDataEmprestimo;
    private final JTextField txtPrazo;
    private final JButton btnSalvar;
    private final JButton btnCancelar;

    /**
     * Constrói uma nova instância da classe EmprestimoView.
     *
     * @param livroId O ID do livro a ser emprestado.
     * @param status O status do livro.
     * @param listarLivrosView A instância de ListarLivrosView associada.
     */
    public EmprestimoView(int livroId, String statusEmprestimo, ListarLivrosView listarLivrosView) {
        this.livroId = livroId;
        this.listarLivrosView = listarLivrosView;

        setTitle("Empréstimo de Livro");
        setSize(600, 500);
        setLocationRelativeTo(null);
        setLayout(null);

        // Campos de texto
        JLabel lblLivroId = new JLabel("ID do Livro:");
        lblLivroId.setBounds(20, 20, 150, 25);
        add(lblLivroId);

        txtLivroId = new JTextField(String.valueOf(livroId));
        txtLivroId.setBounds(200, 20, 200, 25);
        txtLivroId.setEditable(false);
        add(txtLivroId);

        JLabel lblTitulo = new JLabel("Título:");
        lblTitulo.setBounds(20, 60, 150, 25);
        add(lblTitulo);

        txtTitulo = new JTextField();
        txtTitulo.setBounds(200, 60, 200, 25);
        txtTitulo.setEditable(false);
        add(txtTitulo);

        JLabel lblAutor = new JLabel("Autor:");
        lblAutor.setBounds(20, 100, 150, 25);
        add(lblAutor);

        txtAutor = new JTextField();
        txtAutor.setBounds(200, 100, 200, 25);
        txtAutor.setEditable(false);
        add(txtAutor);

        JLabel lblGenero = new JLabel("Gênero:");
        lblGenero.setBounds(20, 140, 150, 25);
        add(lblGenero);

        txtGenero = new JTextField();
        txtGenero.setBounds(200, 140, 200, 25);
        txtGenero.setEditable(false);
        add(txtGenero);

        JLabel lblQuantidade = new JLabel("Quantidade Disponível:");
        lblQuantidade.setBounds(20, 180, 150, 25);
        add(lblQuantidade);

        txtQuantidade = new JTextField();
        txtQuantidade.setBounds(200, 180, 200, 25);
        txtQuantidade.setEditable(false);
        add(txtQuantidade);

        JLabel lblStatusEmprestimo = new JLabel("Status do Empréstimo:");
        lblStatusEmprestimo.setBounds(20, 220, 150, 25);
        add(lblStatusEmprestimo);

        txtStatusEmprestimo = new JTextField(statusEmprestimo);
        txtStatusEmprestimo.setBounds(200, 220, 200, 25);
        add(txtStatusEmprestimo);

        JLabel lblRaAluno = new JLabel("RA do Aluno:");
        lblRaAluno.setBounds(20, 260, 150, 25);
        add(lblRaAluno);

        txtRaAluno = new JTextField();
        txtRaAluno.setBounds(200, 260, 200, 25);
        add(txtRaAluno);

        JLabel lblDataEmprestimo = new JLabel("Data do Empréstimo:");
        lblDataEmprestimo.setBounds(20, 300, 150, 25);
        add(lblDataEmprestimo);

        txtDataEmprestimo = new JTextField(new SimpleDateFormat("yyyy-MM-dd").format(new Date())); // Altere o formato para 'yyyy-MM-dd'
        txtDataEmprestimo.setBounds(200, 300, 200, 25);
        add(txtDataEmprestimo);

        JLabel lblPrazo = new JLabel("Prazo de Devolução:");
        lblPrazo.setBounds(20, 340, 150, 25);
        add(lblPrazo);

        txtPrazo = new JTextField(new SimpleDateFormat("yyyy-MM-dd").format(new Date())); // Inicialmente, a data é a mesma que a data do empréstimo
        txtPrazo.setBounds(200, 340, 200, 25);
        add(txtPrazo);

        // Botões
        btnSalvar = new JButton("Salvar");
        btnSalvar.setBounds(70, 420, 100, 25);
        add(btnSalvar);

        btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(200, 420, 100, 25);
        add(btnCancelar);

        btnSalvar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validarDados()) {
                    if (verificarAluno(txtRaAluno.getText())) {
                        emprestarLivro();
                        JOptionPane.showMessageDialog(EmprestimoView.this, "Empréstimo salvo com sucesso.");
                        listarLivrosView.carregarLivros();
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(EmprestimoView.this, "Aluno não encontrado no banco de dados.", "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        carregarInformacoesLivro(livroId);
    }

    /**
     * Carrega a informações do livro.
     */
    private void carregarInformacoesLivro(int livroId) {
        String sql = "SELECT * FROM livro WHERE id = ?";
        try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, livroId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                txtTitulo.setText(rs.getString("titulo"));
                txtAutor.setText(rs.getString("autor"));
                txtGenero.setText(rs.getString("genero"));
                txtQuantidade.setText(String.valueOf(rs.getInt("quantidade")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao carregar informações do livro.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean validarDados() {
        if (txtRaAluno.getText().isEmpty() || txtPrazo.getText().isEmpty() || txtDataEmprestimo.getText().isEmpty() || txtStatusEmprestimo.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos os campos devem ser preenchidos.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        try {
            new SimpleDateFormat("yyyy-MM-dd").parse(txtDataEmprestimo.getText());
            new SimpleDateFormat("yyyy-MM-dd").parse(txtPrazo.getText()); // Verificar se o prazo é uma data válida
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this, "Data deve estar no formato yyyy-MM-dd.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private boolean verificarAluno(String raAluno) {
        String sql = "SELECT * FROM aluno WHERE ra = ?";
        try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, raAluno);
            ResultSet rs = stmt.executeQuery();

            return rs.next(); // Retorna true se o aluno for encontrado, false caso contrário
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao verificar aluno.", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    private void emprestarLivro() {
        String raAluno = txtRaAluno.getText();
        String dataEmprestimo = txtDataEmprestimo.getText(); // Já está no formato 'yyyy-MM-dd'
        String prazo = txtPrazo.getText(); // Já está no formato 'yyyy-MM-dd'
        String status = "ativo"; // Status do empréstimo
        Funcionario funcionario = UsuarioLogado.getFuncionario(); // Obter o funcionário logado
        String nomeFuncionario = funcionario.getNomeCompleto(); // Nome do funcionário logado

        String sqlCheckDisponibilidade = "SELECT quantidade FROM livro WHERE id = ?";
        String sqlUpdateLivro = "UPDATE livro SET quantidade = quantidade - 1 WHERE id = ?";
        String sqlInsertEmprestimo = "INSERT INTO emprestimo (livro_id, aluno_ra, prazo, data_emprestimo, status, funcionario_id, funcionario_nome) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement stmtCheckDisponibilidade = conn.prepareStatement(sqlCheckDisponibilidade); PreparedStatement stmtUpdateLivro = conn.prepareStatement(sqlUpdateLivro); PreparedStatement stmtInsertEmprestimo = conn.prepareStatement(sqlInsertEmprestimo)) {

            // Verifica a quantidade disponível do livro
            stmtCheckDisponibilidade.setInt(1, livroId);
            ResultSet rs = stmtCheckDisponibilidade.executeQuery();
            if (rs.next()) {
                int quantidadeDisponivel = rs.getInt("quantidade");
                if (quantidadeDisponivel <= 0) {
                    JOptionPane.showMessageDialog(this, "Nenhuma unidade disponível para empréstimo.", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } else {
                JOptionPane.showMessageDialog(this, "Livro não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Atualiza a quantidade disponível do livro
            stmtUpdateLivro.setInt(1, livroId);
            int rowsAffected = stmtUpdateLivro.executeUpdate();

            // Verifica se houve atualização na quantidade disponível
            if (rowsAffected == 0) {
                JOptionPane.showMessageDialog(this, "Erro ao atualizar a quantidade disponível do livro.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Insere o empréstimo na tabela emprestimo
            stmtInsertEmprestimo.setInt(1, livroId); // livro_id
            stmtInsertEmprestimo.setString(2, raAluno); // aluno_ra
            stmtInsertEmprestimo.setString(3, prazo); // prazo (data no formato 'yyyy-MM-dd')
            stmtInsertEmprestimo.setString(4, dataEmprestimo); // data_emprestimo (data no formato 'yyyy-MM-dd')
            stmtInsertEmprestimo.setString(5, status); // status
            stmtInsertEmprestimo.setInt(6, funcionario.getId()); // funcionario_id
            stmtInsertEmprestimo.setString(7, nomeFuncionario); // funcionario_nome
            stmtInsertEmprestimo.executeUpdate();

            JOptionPane.showMessageDialog(this, "Empréstimo salvo com sucesso.");
            listarLivrosView.carregarLivros();
            dispose();

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao salvar o empréstimo.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

}
