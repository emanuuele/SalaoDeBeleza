package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Funcionario extends Pessoa implements BaseModel {
	
	private int id;
	private boolean ehGerente;
	private int id_cargo;
	private ArrayList<Agendamento> atendimentosFuncionario;
	ArrayList<Funcionario> funcionarios = new ArrayList<Funcionario>(); 
	
	public Funcionario(String nome, String usuario, String celular, String senha, char tipo, int id, boolean ehGerente, int id_cargo) {
		super(nome, usuario, celular, senha, tipo);
		this.id=id;
		this.id_cargo = id_cargo;
		this.ehGerente = ehGerente;
		setAtendimentosFuncionario(id);
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
	        System.out.println("Ocorreu um erro: " + e);
	        return null;
	    }
	}
	
	public static Funcionario getFuncionarioPorUsuario(String usuario) throws Exception {
	    try {
	        String sql = "SELECT * FROM Cliente WHERE usuario = ?";
	        PreparedStatement stmt = DAO.getConnection().prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
	        stmt.setString(1, usuario);
	        ResultSet rs = stmt.executeQuery();
	        Funcionario fun = new Funcionario();
	        if (rs.first()) {
	            fun.setNome(rs.getString("nome"));
	            fun.setId(rs.getInt("id"));
	            fun.setSenha(rs.getString("senha"));
	        }
	        return fun;
	    } catch (Exception e) {
	        System.out.println("Ocorreu um erro: " + e);
	        return null;
	    }
	}
	public void add(String nome, String usuario, String celular, String senha, char tipo, boolean ehGerente, int id_cargo) {
		//Funcionario fun = new Funcionario(nome, usuario, celular, senha, tipo, ehGerente, id_cargo);
	}
	public int getId() {
		return this.id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public boolean getEhGerente() {
		return this.ehGerente;
	}
	public void setEhGerente(boolean ehGerente) {
		this.ehGerente = ehGerente;
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
            stmt.setString(6, String.valueOf(this.getEhGerente() == true ? 'S' : 'N'));
            stmt.setInt(7, this.getId_cargo());

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
        	stmt.setInt(1, this.getId());
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
            stmt.setString(4, String.valueOf(this.getEhGerente() == true ? 'S' : 'N'));
            stmt.setInt(5, id);

        	return stmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Ocorreu um erro: " + e.getMessage());
			DAO.getConnection().close();
			return 0;
		}
	}
	
	public ArrayList<Agendamento> getAtendimentosFuncionario() {
		return this.atendimentosFuncionario;
	}
	public void setAtendimentosFuncionario(int id) {
		this.atendimentosFuncionario = new Agendamento().atendimentosFuncionario(id);
	}
	public ArrayList<Funcionario> listarFuncionarios() {
		return funcionarios;
	}
}
