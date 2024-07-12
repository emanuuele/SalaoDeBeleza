package model;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class Agendamento implements BaseModel{
	private int id;
	private String data;
	private int id_cliente;
	private int id_funcionario;
	private int id_servico;
	Cliente clienteModel = new Cliente();
	Funcionario funcionarioModel = new Funcionario();
	Servico servicoModel = new Servico();
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public int getId_cliente() {
		return id_cliente;
	}

	public void setId_cliente(int id_cliente) {
		this.id_cliente = id_cliente;
	}

	public int getId_funcionario() {
		return id_funcionario;
	}

	public void setId_funcionario(int id_funcionario) {
		this.id_funcionario = id_funcionario;
	}

	public String getNomeCliente(int id_cliente) throws Exception {
		return clienteModel.getClientePorId(id_cliente).getNome();
	}
	
	public String getNomeFuncionario(int id_funcionario) throws Exception {
		return funcionarioModel.getFuncionarioPorId(id_funcionario).getNome();
	}
	
	public String getNomeServico(int id_servico) {
		return servicoModel.encontrarServicoPorId(id_servico).getNome();
	}
	
	public int getId_servico() {
		return id_servico;
	}

	public void setId_servico(int id_servico) {
		this.id_servico = id_servico;
	}

	@Override
	public int salvar() throws SQLException {
		try {
    		String sql = "INSERT INTO Agendamento (data, id_cliente, id_funcionario, id_sevico) VALUES (?, ?, ?, ?)";
        	PreparedStatement stmt = DAO.getConnection().prepareStatement(sql);
        	stmt.setString(1, this.getData());
        	stmt.setInt(2, this.getId_cliente());
        	stmt.setInt(3, this.getId_funcionario());
        	stmt.setInt(4, this.getId_servico());
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

	@Override
	public int editar(int id) {
		return id;
		
	}
	
	public ArrayList<Agendamento> agendamentosCliente(int id_cliente) {
		ArrayList<Agendamento> meusAgendamentos = new ArrayList<Agendamento>();
		
		return meusAgendamentos;
	}
	
	public ArrayList<Agendamento> atendimentosFuncionario(int id_funcionario) {
		ArrayList<Agendamento> meusAtendimentos = new ArrayList<Agendamento>();
		
		return meusAtendimentos;
	}
}
