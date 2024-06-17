/**
 * Esta classe representa a visualização de listagem de livros.
 * O código-fonte foi descompilado de um arquivo .class usando o decompilador FernFlower.
 *
 * @author [Seu Nome]
 * @version 1.0
 * @since 2024-06-16
 */// Source code is decompiled from a .class file using FernFlower decompiler.
package view;

import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import model.ConnectionFactory;

/**
 *
 * @author raiss
 */
public class ListarLivrosView extends JPanel {

    private JTable tabelaLivros;
    private DefaultTableModel modeloTabela;

    /**
     * Cria uma instância da visualização de listagem de livros.
     */
    public ListarLivrosView() {
        setLayout(new BorderLayout());

        String[] colunas = {"ID", "Título", "Autor", "Gênero", "Quantidade", "Status"};
        Object[][] dados = {}; // Inicialmente vazio, será carregado do banco de dados

        modeloTabela = new DefaultTableModel(dados, colunas) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Permitir edição apenas na coluna de status
                return column == 5;
            }
        };

        tabelaLivros = new JTable(modeloTabela);
        tabelaLivros.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add(new JScrollPane(tabelaLivros), BorderLayout.CENTER);

        // Adicionar listener para detectar alterações na tabela
        modeloTabela.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                int row = e.getFirstRow();
                int column = e.getColumn();

                if (column == 5 && e.getType() == TableModelEvent.UPDATE) { // Verifica se a coluna de status foi editada e o evento é de atualização
                    String statusEmprestimo = (String) modeloTabela.getValueAt(row, column);
                    int livroId = (int) modeloTabela.getValueAt(row, 0);

                    // Abrir janela de empréstimo
                    EmprestimoView dialog = new EmprestimoView(livroId, statusEmprestimo, ListarLivrosView.this);
                    dialog.setVisible(true);
                }
            }
        });
    }

    /**
     * Carrega os livros do banco de dados e atualiza a tabela.
     */
    public void carregarLivros() {
        try (Connection conn = ConnectionFactory.getConnection()) {
            String sql = "SELECT * FROM livro";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            modeloTabela.setRowCount(0); // Limpar tabela

            while (rs.next()) {
                Object[] row = {
                    rs.getInt("id"),
                    rs.getString("titulo"),
                    rs.getString("autor"),
                    rs.getString("genero"),
                    rs.getInt("quantidade"),
                    rs.getString("status")
                };
                modeloTabela.addRow(row);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao carregar livros do banco de dados.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Filtra os livros do banco de dados de acordo com o texto de pesquisa
     * fornecido e atualiza a tabela.
     *
     * @param searchText O texto de pesquisa.
     */
    public void filtrarLivros(String searchText) {
        try (Connection conn = ConnectionFactory.getConnection()) {
            String sql = "SELECT * FROM livro WHERE titulo LIKE ? OR autor LIKE ? OR genero LIKE ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            String queryText = "%" + searchText + "%";
            stmt.setString(1, queryText);
            stmt.setString(2, queryText);
            stmt.setString(3, queryText);
            ResultSet rs = stmt.executeQuery();

            modeloTabela.setRowCount(0); // Limpar tabela

            while (rs.next()) {
                Object[] row = {
                    rs.getInt("id"),
                    rs.getString("titulo"),
                    rs.getString("autor"),
                    rs.getString("genero"),
                    rs.getInt("quantidade"),
                    rs.getString("status")
                };
                modeloTabela.addRow(row);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao filtrar livros do banco de dados.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Obtém o ID do livro selecionado na tabela.
     *
     * @return O ID do livro selecionado, ou -1 se nenhum livro estiver
     * selecionado.
     */
    public int getLivroIdSelecionado() {
        int linhaSelecionada = tabelaLivros.getSelectedRow();
        if (linhaSelecionada != -1) {
            return (int) modeloTabela.getValueAt(linhaSelecionada, 0);
        }
        return -1;
    }

    /**
     * Obtém o status do livro selecionado na tabela.
     *
     * @return O status do livro selecionado, ou null se nenhum livro estiver
     * selecionado.
     */
    public String getStatusLivroSelecionado() {
        int linhaSelecionada = tabelaLivros.getSelectedRow();
        if (linhaSelecionada != -1) {
            return (String) modeloTabela.getValueAt(linhaSelecionada, 5);
        }
        return null;
    }

    /**
     * Obtém o título do livro selecionado na tabela.
     *
     * @return O título do livro selecionado, ou null se nenhum livro estiver
     * selecionado.
     */
    public String getTituloLivroSelecionado() {
        int linhaSelecionada = tabelaLivros.getSelectedRow();
        if (linhaSelecionada != -1) {
            return (String) modeloTabela.getValueAt(linhaSelecionada, 1);
        }
        return null;
    }
}
