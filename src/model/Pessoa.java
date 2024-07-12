package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Pessoa {
	private String nome;
	private String usuario;
	private String celular;
	private String senha;
	private char tipo;
	
	public Pessoa(String nome, String usuario, String celular, String senha, char tipo) {
		this.nome=nome;
		this.usuario=usuario;
		this.celular=celular;
		this.senha=senha;
		this.tipo=tipo;
	}
	public Pessoa() {
		
	}
	public String getNome() {
		return this.nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getUsuario() {
		return this.usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getCelular() {
		return this.celular;
	}
	public void setCelular(String celular) {
		this.celular = celular;
	}
	public String getSenha() {
		return this.senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public char getTipo() {
		return this.tipo;
	}
	public void setTipo(char tipo) {
		this.tipo = tipo;
	}
	
	public static Pessoa loginUsuario(String usuario) throws SQLException {
	    try {
	        String sql = "SELECT Cliente.id AS id_cliente, NULL AS id_funcionario, senha "
	                   + "FROM Cliente "
	                   + "WHERE Cliente.usuario = ? "
	                   + "UNION "
	                   + "SELECT NULL AS id_cliente, Funcionario.id AS id_funcionario, senha "
	                   + "FROM Funcionario "
	                   + "WHERE Funcionario.usuario = ?;";
	        
	        PreparedStatement stmt = DAO.getConnection().prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
	        stmt.setString(1, usuario);
	        stmt.setString(2, usuario);
	        
	        ResultSet rs = stmt.executeQuery();
	        Pessoa pessoa = new Pessoa();
	        
	        if (rs.first()) {
	            pessoa.setSenha(rs.getString("senha"));
	            return pessoa;
	        }
	        return null;
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return null;
	    }
	}

	
	public static boolean existeEsseUsuario(String usuario) throws SQLException {
			Connection connection = null;
		    PreparedStatement stmtCliente = null;
		    PreparedStatement stmtFuncionario = null;
		    ResultSet resultSetCliente = null;
		    ResultSet resultSetFuncionario = null;
		    boolean existe = false;

		    try {
		        connection = DAO.getConnection();

		        String sqlCliente = "SELECT COUNT(*) as count FROM Cliente WHERE usuario = ?";
		        stmtCliente = connection.prepareStatement(sqlCliente);
		        stmtCliente.setString(1, usuario);
		        resultSetCliente = stmtCliente.executeQuery();

		        String sqlFuncionario = "SELECT COUNT(*) as count FROM Funcionario WHERE usuario = ?";
		        stmtFuncionario = connection.prepareStatement(sqlFuncionario);
		        stmtFuncionario.setString(1, usuario);
		        resultSetFuncionario = stmtFuncionario.executeQuery();

		        resultSetCliente.next();
		        resultSetFuncionario.next();
		        
		        if(resultSetCliente.getInt("count") > 0 || resultSetFuncionario.getInt("count") > 0) {
		        	throw new SQLException("Já existe alguém com esse usuário.");
		        }
		        return false;
		    } catch (SQLException e) {
		    	DAO.getConnection().close();
		    	return true;
		    }
	}
}
