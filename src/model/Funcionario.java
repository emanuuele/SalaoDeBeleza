package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Funcionario extends Pessoa implements BaseModel {
	
	private int id;
	private int ehGerente;
	private int id_cargo;
	private ArrayList<Agendamento> atendimentosFuncionario;
	
	public Funcionario(String nome, String usuario, String celular, String senha, char tipo, int id, int ehGerente, int id_cargo) throws SQLException {
		super(nome, usuario, celular, senha, tipo);
		this.id=id;
		this.id_cargo = id_cargo;
		this.ehGerente = ehGerente;
	}
	public Funcionario() {
		
	}
	public static Funcionario getFuncionarioPorId(int id) throws Exception {
	    try {
	        String sql = "SELECT * FROM Funcionario WHERE id = ?";
	        PreparedStatement stmt = DAO.getConnection().prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
	        stmt.setInt(1, id);
	        ResultSet rs = stmt.executeQuery();
	        Funcionario fun = new Funcionario();
	        if (rs.first()) {
	            fun.setNome(rs.getString("nome"));
	            fun.setId(rs.getInt("id"));
	        }
	        return fun;
	    } catch (Exception e) {
	        System.out.println("Ocorreu um erro: " + e.getMessage());
	        return null;
	    }
	}
	
	public static Funcionario getFuncionarioPorUsuario(String usuario) throws Exception {
	    try {
	        String sql = "SELECT * FROM Funcionario WHERE usuario = ?";
	        PreparedStatement stmt = DAO.getConnection().prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
	        stmt.setString(1, usuario);
	        ResultSet rs = stmt.executeQuery();
	        Funcionario fun = new Funcionario();
	        if (rs.first()) {
	            fun.setNome(rs.getString("nome"));
	            fun.setId(rs.getInt("id"));
	            fun.setSenha(rs.getString("senha"));
	            fun.setEhGerente(rs.getInt("ehGerente"));
	        }
	        return fun;
	    } catch (Exception e) {
	        System.out.println("Ocorreu um erro: " + e.getMessage());
	        return null;
	    }
	}
	public int getId() {
		return this.id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getEhGerente() {
		return this.ehGerente;
	}
	public void setEhGerente(int i) {
		this.ehGerente = i;
	}
	public int getId_cargo() {
		return this.id_cargo;
	}
	public void setId_cargo(int id_cargo) {
		this.id_cargo = id_cargo;
	}
	@Override
	public int salvar() throws SQLException {
		try {
    		String sql = "INSERT INTO Funcionario (nome, usuario, celular, senha, tipo, ehGerente, id_cargo) VALUES (?, ?, ?, ?, ?, ?, ?)";
        	PreparedStatement stmt = DAO.getConnection().prepareStatement(sql);
        	stmt.setString(1, this.getNome());
            stmt.setString(2, this.getUsuario());
            stmt.setString(3, this.getCelular());
            stmt.setString(4, this.getSenha());
            stmt.setString(5, String.valueOf(this.getTipo()));
            stmt.setInt(6, this.getEhGerente());
            if(this.getId_cargo() == 0) {
            	stmt.setNull(7, id_cargo);;
            } else {
                stmt.setInt(7, this.getId_cargo());
            }
        	return stmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Ocorreu um erro: " + e.getMessage());
			DAO.getConnection().close();
			return 0;
		}
	}
	@Override
	public int deletar(int id) throws SQLException {
		try {
    		String sql = "DELETE FROM Funcionario WHERE id = ?";
        	PreparedStatement stmt = DAO.getConnection().prepareStatement(sql);
        	stmt.setInt(1, id);
        	return stmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Ocorreu um erro: " + e.getMessage());
			DAO.getConnection().close();
			return 0;
		}
	}
	@Override
	public int editar(int id) throws SQLException {
		try {
			String sql = "UPDATE Funcionario SET nome = ?, usuario = ?, celular = ?, ehGerente = ? WHERE id = ?";
        	PreparedStatement stmt = DAO.getConnection().prepareStatement(sql);
        	stmt.setString(1, this.getNome());
            stmt.setString(2, this.getUsuario());
            stmt.setString(3, this.getCelular());
            stmt.setString(4, String.valueOf(this.getEhGerente()));
            stmt.setInt(5, id);

        	return stmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Ocorreu um erro: " + e.getMessage());
			DAO.getConnection().close();
			return 0;
		}
	}
	public static ArrayList<String> funcionariosPorCargo(int id_cargo, int dia, int mes) throws SQLException {
    	ArrayList<String> nomes = new ArrayList<String>();
		try {
	        String sql = "SELECT nome, id FROM funcionario where id_cargo in (select id_cargo from servico where id_cargo = ?)";
        	PreparedStatement stmt = DAO.getConnection().prepareStatement(sql);
        	stmt.setInt(1, id_cargo);
        	ResultSet rs = stmt.executeQuery();
	        while (rs.next()) {
	        	nomes.add(String.valueOf(rs.getInt("id")) + " " + rs.getString("nome"));
	        }
		} catch (SQLException e) {
			System.out.println("Ocorreu um erro: " + e.getMessage());
			DAO.getConnection().close();
		}
		return nomes;
	}
	public ArrayList<Agendamento> getAtendimentosFuncionario() throws SQLException {
		return new Agendamento().atendimentosFuncionario(id);
	}
	public ArrayList<Funcionario> listarFuncionarios() {
		ArrayList<Funcionario> funcionarios = new ArrayList<Funcionario>(); 
		try {
	        String sql = "SELECT f.*, c.nome as nomecargo FROM Funcionario as f LEFT JOIN Cargo as c ON (c.id = f.id_cargo)";
	        PreparedStatement stmt = DAO.getConnection().prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
	        ResultSet rs = stmt.executeQuery();
	        while (rs.next()) {
	        	Funcionario fun = new Funcionario();
	            fun.setNome(rs.getString("nome") +" | " + (rs.getString("nomecargo") != null ? rs.getString("nomecargo") : ""));
	            fun.setId(rs.getInt("id"));
	            fun.setSenha(rs.getString("senha"));
	            fun.setEhGerente(rs.getInt("ehGerente"));
	            fun.setCelular(String.valueOf(rs.getInt("celular")));
	            fun.setUsuario(rs.getString("usuario"));
	            funcionarios.add(fun);
	        }
	    } catch (Exception e) {
	        System.out.println("Ocorreu um erro: " + e.getMessage());
	        return null;
	    }
		return funcionarios;
	}
}
