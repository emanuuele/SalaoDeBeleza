package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Servico implements BaseModel{
	private int id;
	private double valor;
	private String nome;
	private int tempo;
	private int id_cargo;
	ArrayList<Servico> servicos = new ArrayList<Servico>();
	public Servico(int id, double valor, String nome, int tempo, int id_cargo) {
		this.id = id;
		this.id_cargo = id_cargo;
		this.valor = valor;
		this.nome = nome;
		this.tempo = tempo;
	}
	public Servico() {

	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public double getValor() {
		return valor;
	}
	public void setValor(double valor) {
		this.valor = valor;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public int getTempo() {
		return tempo;
	}
	public void setTempo(int tempo) {
		this.tempo = tempo;
	}
	public int getId_cargo() {
		return id_cargo;
	}
	public void setId_cargo(int id_cargo) {
		this.id_cargo = id_cargo;
	}
	// lista os registros da tb servico
	public ArrayList<Servico> listarServicos() {
        ArrayList<Servico> servicos = new ArrayList<Servico>();
		try {
	        String sql = "SELECT * FROM Servico";
	        //estabelce uma conexão com o banco e atribui ao objeto stmt que seria basicmente um "estado" onde manipula o response do metodo que vc quer utilizar (insert, update, select nesse caso)
	        PreparedStatement stmt = DAO.getConnection().prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
	        ResultSet rs = stmt.executeQuery();
	        //verifica se tem resultados
	        if(rs.next()) {
	        	//guarda o resultado em um arraylist
	        	while (rs.next()) {
		        	Servico servico = new Servico();
		        	servico.setNome(rs.getString("nome"));
		        	servico.setId(rs.getInt("id"));
		        	servicos.add(servico);
		        }
	        }
	    } catch (Exception e) {
	        System.out.println("Ocorreu um erro: " + e.getMessage());
	    }
		return servicos;
	}
	//encontra um servico pelo id dele
	public static Servico encontrarServicoPorId(int id) {
		try {
	        String sql = "SELECT * FROM Servico WHERE id = ?";
	        PreparedStatement stmt = DAO.getConnection().prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
	        stmt.setInt(1, id);
	        ResultSet rs = stmt.executeQuery();
	        Servico servico = new Servico();
	        //retorna o servico encoontrado
	        if (rs.first()) {
	        	servico.setNome(rs.getString("nome"));
	        	servico.setId(rs.getInt("id"));
	        	servico.setTempo(rs.getInt("tempo"));
	        	return servico;
	        }
	        return null;
	    } catch (Exception e) {
	        System.out.println("Ocorreu um erro: " + e.getMessage());
	        return null;
	    }
	}
	//insere o objeto na tabela de servico
	@Override
	public int salvar() throws SQLException {
		try {
    		String sql = "INSERT INTO Servico (nome, id_cargo, valor, tempo) VALUES (?, ?, ?, ?)";
        	PreparedStatement stmt = DAO.getConnection().prepareStatement(sql);
        	stmt.setString(1, this.getNome());
            stmt.setInt(2, this.getId_cargo());
            stmt.setDouble(3, this.getValor());
            stmt.setInt(4, this.getTempo());
        	return stmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Ocorreu um erro: " + e.getMessage());
			DAO.getConnection().close();
			return 0;
		}
	}
	//altera e exclui um servico pelo seu id
	@Override
	public int deletar(int id) throws SQLException {
		try {
    		String sql = "DELETE FROM Servico WHERE id = ?";
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
    		String sql = "UPDATE Servico SET nome = ?, valor = ?, tempo = ? WHERE id = ?";
        	PreparedStatement stmt = DAO.getConnection().prepareStatement(sql);
        	stmt.setString(1, this.getNome());
            stmt.setDouble(2, this.getValor());
            stmt.setInt(3, this.getTempo());
            stmt.setInt(4, id);
        	return stmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Ocorreu um erro: " + e.getMessage());
			DAO.getConnection().close();
			return 0;
		}
	}
}
