package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Cargo implements BaseModel{
	private String nome;
	private int id;
	private int id_serviço;
	ArrayList<Cargo> cargos = new ArrayList<Cargo>(); 
	public Cargo(String nome, int id, int id_serviço) {
		this.nome=nome;
		this.id=id;
		this.id_serviço=id_serviço;
	}
	public Cargo() {
	}
	public String getNome() {
		return this.nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public int getId() {
		return this.id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getId_serviço() {
		return this.id_serviço;
	}
	public void setId_serviço(int id_serviço) {
		this.id_serviço = id_serviço;
	}
	@Override
	public int salvar() throws SQLException {
		try {
    		String sql = "INSERT INTO Cargo (nome) VALUES (?)";
        	PreparedStatement stmt = DAO.getConnection().prepareStatement(sql);
        	stmt.setString(1, this.getNome());
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
    		String sql = "DELETE FROM Cargo WHERE id = ?";
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
			String sql = "UPDATE Funcionario SET nome = ? WHERE id = ?";
        	PreparedStatement stmt = DAO.getConnection().prepareStatement(sql);
        	stmt.setString(1, this.getNome());
            stmt.setInt(2, id);

        	return stmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Ocorreu um erro: " + e.getMessage());
			DAO.getConnection().close();
			return 0;
		}
	}
	
	public static Cargo encontrarCargoPorId(int id) throws Exception {
	    try {
	        String sql = "SELECT * FROM Cargo WHERE id = ?";
	        PreparedStatement stmt = DAO.getConnection().prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
	        stmt.setInt(1, id);
	        ResultSet rs = stmt.executeQuery();
	        Cargo cargo = new Cargo();
	        if (rs.first()) {
	        	cargo.setNome(rs.getString("nome"));
	        	cargo.setId(rs.getInt("id"));
	        }
	        return cargo;
	    } catch (Exception e) {
	        System.out.println("Ocorreu um erro: " + e.getMessage());
	        return null;
	    }
	}
	
	public ArrayList<Cargo> listarServicos() {
        return cargos;
    }


}
