package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Cliente extends Pessoa implements BaseModel{
	private int id;
	private ArrayList<Agendamento> meusAgendamentos;	
	public Cliente(String nome, String usuario, String celular, String senha, char tipo, int id) {
		super(nome, usuario, celular, senha, tipo);
		this.id=id;
	}
	
	public Cliente() {

	}

	public static Cliente getClientePorId(int id) throws Exception {
	    try {
	        String sql = "SELECT * FROM Cliente WHERE id = ?";
	        PreparedStatement stmt = DAO.getConnection().prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
	        stmt.setInt(1, id);
	        ResultSet rs = stmt.executeQuery();
	        Cliente cliente = new Cliente();
	        if (rs.first()) {
	            cliente.setNome(rs.getString("nome"));
	            cliente.setId(rs.getInt("id"));
	        }
	        return cliente;
	    } catch (Exception e) {
	        System.out.println("Ocorreu um erro: " + e.getMessage());
	        return null;
	    }
	}

	public static Cliente getClientePorUsuario(String usuario) throws Exception {
	    try {
	        String sql = "SELECT * FROM Cliente WHERE usuario = ?";
	        PreparedStatement stmt = DAO.getConnection().prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
	        stmt.setString(1, usuario);
	        ResultSet rs = stmt.executeQuery();
	        Cliente cliente = new Cliente();
	        if (rs.first()) {
	            cliente.setNome(rs.getString("nome"));
	            cliente.setId(rs.getInt("id"));
	            cliente.setSenha(rs.getString("senha"));
	        }
	        return cliente;
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
	
	@Override
	public int salvar() throws SQLException {
		try {
    		String sql = "INSERT INTO Cliente (nome, usuario, celular, senha, tipo) VALUES (?, ?, ?, ?, ?)";
        	PreparedStatement stmt = DAO.getConnection().prepareStatement(sql);
        	stmt.setString(1, this.getNome());
            stmt.setString(2, this.getUsuario());
            stmt.setString(3, this.getCelular());
            stmt.setString(4, this.getSenha());
            stmt.setString(5, String.valueOf(this.getTipo()));
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
    		String sql = "DELETE FROM Agendamento WHERE id = ?";
        	PreparedStatement stmt = DAO.getConnection().prepareStatement(sql);
        	stmt.setInt(1, this.getId());
        	return stmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Ocorreu um erro: " + e.getMessage());
			DAO.getConnection().close();
			return 0;
		}
	}
	public int editar(int id) throws SQLException {
		try {
			String sql = "UPDATE Cliente SET nome = ?, usuario = ?, celular = ? WHERE id = ?";
        	PreparedStatement stmt = DAO.getConnection().prepareStatement(sql);
        	stmt.setString(1, this.getNome());
            stmt.setString(2, this.getUsuario());
            stmt.setString(3, this.getCelular());
            stmt.setInt(4, id);

        	return stmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Ocorreu um erro: " + e.getMessage());
			DAO.getConnection().close();
			return 0;
		}
	}
	public ArrayList<Agendamento> getMeusAgendamentos() throws SQLException {
		return new Agendamento().agendamentosCliente(LoggedUser.getID());
	}
	
	public ArrayList<Cliente> listarClientes() throws SQLException{
    	ArrayList<Cliente> clientes = new ArrayList<Cliente>(); 
		try {
			String sql = "SELECT * FROM Cliente";
	        PreparedStatement stmt = DAO.getConnection().prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
	        ResultSet rs = stmt.executeQuery();
	        while(rs.next()) {
	        	Cliente cliente = new Cliente(rs.getString("nome"), rs.getString("usuario"), rs.getString("celular"), null, rs.getString("tipo").toCharArray()[0], rs.getInt("id"));
	        	clientes.add(cliente);
	        }
			return clientes;
		} catch (SQLException e) {
			System.out.println("Ocorreu um erro: " + e.getMessage());
			return clientes;
		}
	}
}
