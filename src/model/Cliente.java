package model;

import java.util.ArrayList;

public class Cliente extends Pessoa implements BaseModel{
	private int id;
	private ArrayList<Agendamento> meusAgendamentos;
	ArrayList<Cliente> clientes = new ArrayList<Cliente>(); 
	
	public Cliente(String nome, String usuario, String celular, String senha, char tipo, int id) {
		super(nome, usuario, celular, senha, tipo);
		this.id=id;
		setMeusAgendamentos(id);
	}
	
	public Cliente() {

	}

	public Cliente encontrarClientePorId(int id) {
	    for (Cliente cli : clientes) {
	        if (cli.getId() == id) {
	            return cli;
	        }
	    }
	    return null;
	}
	public Cliente encontrarClientePorUsuario(String usuario) {
	    for (Cliente cli : clientes) {
	        if (cli.getUsuario() == usuario) {
	            return cli;
	        }
	    }
	    return null;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@Override
	public void salvar() {
		clientes.add(this);
	}
	@Override
	public void deletar(int id) {
        clientes.removeIf(servico -> servico.getId() == id);
	}
	@Override
	public void editar(int id) {
		for(int i = 0; i < clientes.size(); i++) {
			Cliente cliente = clientes.get(i);
			if(cliente.getId() == id) {
				cliente.setCelular(this.getCelular());
				cliente.setNome(this.getNome());
				cliente.setSenha(this.getSenha());
				cliente.setUsuario(this.getUsuario());
				clientes.set(i, cliente);
				break;
			}
		}
	}
	public ArrayList<Agendamento> getMeusAgendamentos() {
		return meusAgendamentos;
	}
	public void setMeusAgendamentos(int id) {
		this.meusAgendamentos = new Agendamento().agendamentosCliente(id);
	}
	
	public ArrayList<Cliente> listarClientes(){
		return clientes;
	}
}
