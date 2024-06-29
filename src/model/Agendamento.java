package model;

import java.util.ArrayList;
import java.util.Date;

public class Agendamento implements BaseModel{
	private int id;
	private Date data;
	private int id_cliente;
	private int id_funcionario;
	private int id_servico;
	ArrayList<Agendamento> agendamentos = new ArrayList<Agendamento>(); 
	Cliente clienteModel = new Cliente();
	Funcionario funcionarioModel = new Funcionario();
	Servico servicoModel = new Servico();
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
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

	public String getNomeCliente(int id_cliente) {
		return clienteModel.encontrarClientePorId(id_cliente).getNome();
	}
	
	public String getNomeFuncionario(int id_funcionario) {
		return funcionarioModel.encontrarFuncionarioPorId(id_funcionario).getNome();
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
	public void salvar() {
		agendamentos.add(this);
	}

	@Override
	public void deletar(int id) {
        agendamentos.removeIf(servico -> servico.getId() == id);
	}

	@Override
	public void editar(int id) {
		for(int i = 0; i < agendamentos.size(); i++) {
			Agendamento agendamento = agendamentos.get(i);
			if(agendamento.getId() == id) {
				agendamento.setData(this.getData());
				agendamento.setId_cliente(this.getId_cliente());
				agendamento.setId_funcionario(this.getId_funcionario());
				agendamento.setId_servico(this.getId_servico());
				agendamentos.set(i, agendamento);
				break;
			}
		}
	}
	
	public ArrayList<Agendamento> agendamentosCliente(int id_cliente) {
		ArrayList<Agendamento> meusAgendamentos = new ArrayList<Agendamento>();
		for(int i = 0; i < agendamentos.size(); i++) {
			Agendamento agendamento = agendamentos.get(i);
			if(agendamento.getId_cliente() == id_cliente) {
				meusAgendamentos.add(agendamento);
			}
		}
		return meusAgendamentos;
	}
	
	public ArrayList<Agendamento> atendimentosFuncionario(int id_funcionario) {
		ArrayList<Agendamento> meusAtendimentos = new ArrayList<Agendamento>();
		for (int i=0; i<agendamentos.size(); i++) {
			Agendamento agendamento = agendamentos.get(i);
			if(agendamento.getId_funcionario() == id_funcionario) {
				meusAtendimentos.add(agendamento);
			}
		} 
		return meusAtendimentos;
	}
}
