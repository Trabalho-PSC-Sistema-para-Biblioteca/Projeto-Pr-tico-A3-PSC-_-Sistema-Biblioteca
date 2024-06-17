/**
 * Esta classe representa a interface principal da aplicação.
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
import javax.swing.*;
import model.Funcionario;

/**
 *
 * @author raiss
 */
public class PrincipalView extends JFrame {

    private Funcionario funcionario;
    private JMenuBar menuBar;
    private JButton btnListarEmprestados;
    private JTextField searchField;
    private ListarLivrosView listarLivrosView;

    /**
     * Construtor da classe PrincipalView.
     *
     * @param funcionario O funcionário logado na aplicação
     */
    public PrincipalView(Funcionario funcionario) {
        this.funcionario = funcionario;
        setTitle("Sistema de Biblioteca - Principal");
        setSize(900, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        menuBar = new JMenuBar();
        menuBar.setLayout(new FlowLayout(FlowLayout.LEFT)); // Alinhar os botões à esquerda

        JButton btnInserir = createStyledButton("Inserir Livro");
        JButton btnAlterar = createStyledButton("Alterar Livro");
        JButton btnRemover = createStyledButton("Remover Livro");
        JButton btnListar = createStyledButton("Listar Livros");
        JButton btnEmprestimo = createStyledButton("Empréstimo");
        JButton btnDevolucao = createStyledButton("Devolução");
        JButton btnSair = createStyledButton("Sair");

        menuBar.add(btnInserir);
        menuBar.add(btnAlterar);
        menuBar.add(btnRemover);
        menuBar.add(btnListar);
        menuBar.add(btnEmprestimo);
        menuBar.add(btnDevolucao);
        menuBar.add(btnSair);

        setJMenuBar(menuBar);

        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        searchField = new JTextField(20);
        JButton btnBusca = createStyledButton("Pesquisar");
        searchPanel.add(searchField);
        searchPanel.add(btnBusca);

        listarLivrosView = new ListarLivrosView();

        add(searchPanel, BorderLayout.NORTH);
        add(listarLivrosView, BorderLayout.CENTER);

        btnEmprestimo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int livroId = listarLivrosView.getLivroIdSelecionado();
                String statusEmprestimo = listarLivrosView.getStatusLivroSelecionado();
                String tituloLivro = listarLivrosView.getTituloLivroSelecionado();

                if (livroId != -1 && statusEmprestimo != null) {
                    new EmprestimoView(livroId, statusEmprestimo, listarLivrosView).setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(PrincipalView.this, "Selecione um livro na tabela primeiro.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnDevolucao.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new DevolucaoView(listarLivrosView).setVisible(true);
            }
        });

        btnInserir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CadastroLivroView(funcionario, listarLivrosView).setVisible(true);
            }
        });

        btnAlterar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AlterarLivroView(listarLivrosView).setVisible(true);
            }
        });

        btnRemover.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new RemoverLivroView(listarLivrosView).setVisible(true);
            }
        });

        btnListar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listarLivrosView.carregarLivros();
            }
        });

        btnSair.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        btnBusca.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchText = searchField.getText().trim();
                listarLivrosView.filtrarLivros(searchText);
            }
        });
    }

    /**
     * Cria um botão estilizado com o texto fornecido.
     *
     * @param text O texto do botão
     * @return O botão criado
     */
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(110, 30));
        button.setFont(new Font("Arial", Font.BOLD, 12));
        return button;
    }
}
