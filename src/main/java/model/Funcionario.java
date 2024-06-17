/**
 * Esta classe representa um funcionário do sistema.
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
/**
 * 
 *@author tayna
 */
public class Funcionario {
    private String login;
    private String senha;
    private String nomeCompleto;
    private int id;

    public Funcionario(int id, String senha, String nomeCompleto, String login) {
    	this.login = login;
    	this.senha = senha;
    	this.nomeCompleto = nomeCompleto;
    	this.id = id;
    }

	public String getLogin() {
    	return login;
    }
    
    public void setLogin(String login) {
    	this.login = login;
    }
    
    public String getSenha() {
    	return senha;
    }
    
    public void setSenha(String senha) {
    	this.senha = senha;
    }
    
    public String getNomeCompleto() {
    	return nomeCompleto;
    }
    
    public void setNomeCompleto(String nomeCompleto) {
    	this.nomeCompleto = nomeCompleto;
    }
    
    public int getId() {
    	return id;
    }
    
    public void setId(int id) {
    	this.id = id;
    }

    /**
     * Realiza a autenticação do funcionário.
     * @param login O login informado pelo usuário.
     * @param senha A senha informada pelo usuário.
     * @return Verdadeiro se a autenticação for bem-sucedida, falso caso contrário.
     */
    public static boolean autenticar(String login, String senha) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = ConnectionFactory.getConnection();
            String sql = "SELECT * FROM funcionario WHERE login = ? AND senha = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, login);
            stmt.setString(2, senha);
            rs = stmt.executeQuery();
            return rs.next(); // Retorna verdadeiro se houver um resultado (funcionário encontrado)
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            ConnectionFactory.close(conn, stmt, rs);
        }
    }
}

