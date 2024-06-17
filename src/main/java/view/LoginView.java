/**
 * Esta classe representa a interface de usuário para autenticação de login.
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
import javax.swing.*;
import model.ConnectionFactory;
import model.Funcionario;
import model.UsuarioLogado;

/**
 *
 * @author raiss
 */
public class LoginView extends JFrame {

    private JTextField loginField;
    private JPasswordField passwordField;
    private JButton loginButton;

    /**
     * Cria uma nova instância da interface de login.
     */
    public LoginView() {
        setTitle("Login");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;  // Allow horizontal stretching
        gbc.insets = new Insets(5, 5, 5, 5);  // Add padding around components

        // Title Label
        JLabel textoLabel = new JLabel("Sistema da Biblioteca Leitura+");
        textoLabel.setFont(new Font("Verdana", Font.BOLD, 18));
        textoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 10, 20, 10);  // More padding for the title
        add(textoLabel, gbc);

        // Login Label
        JLabel loginLabel = new JLabel("Login:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        add(loginLabel, gbc);

        loginField = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        add(loginField, gbc);

        // Password Label
        JLabel passwordLabel = new JLabel("Senha:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        add(passwordLabel, gbc);

        passwordField = new JPasswordField(15);
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        add(passwordField, gbc);

        // Login Button
        loginButton = new JButton("Login");
        loginButton.setPreferredSize(new Dimension(25, 25));
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        add(loginButton, gbc);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                authenticateUser();
            }
        });
    }

    /**
     * Autentica o usuário com as credenciais fornecidas.
     */
    private void authenticateUser() {
        String login = loginField.getText();
        String password = new String(passwordField.getPassword());

        try (Connection conn = ConnectionFactory.getConnection()) {
            String sql = "SELECT * FROM funcionario WHERE login = ? AND senha = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, login);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String nomeCompleto = rs.getString("nome_completo");
                Funcionario funcionario = new Funcionario(rs.getInt("id"), login, password, nomeCompleto);
                UsuarioLogado.setFuncionario(funcionario); // Armazena o funcionário logado
                PrincipalView principalView = new PrincipalView(funcionario);
                principalView.setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Login ou senha incorretos.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao conectar ao banco de dados.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * O método principal para iniciar a aplicação.
     *
     * @param args Argumentos de linha de comando (não utilizados neste caso)
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LoginView().setVisible(true);
            }
        });
    }
}
