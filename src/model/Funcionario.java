package model;

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
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public boolean getEhGerente() {
		return ehGerente;
	}
	public void setEhGerente(boolean ehGerente) {
		this.ehGerente = ehGerente;
	}
	public int getId_cargo() {
		return id_cargo;
	}
	public void setId_cargo(int id_cargo) {
		this.id_cargo = id_cargo;
	}
	@Override
	public void salvar() {
		funcionarios.add(this);
	}
	@Override
	public void deletar(int id) {
        funcionarios.removeIf(servico -> servico.id == id);
	}
	@Override
	public void editar(int id) {
		for(int i = 0; i < funcionarios.size(); i++) {
			Funcionario funcionario = funcionarios.get(i);
			if(funcionario.id == id) {
				funcionario.setCelular(this.getCelular());
				funcionario.setNome(this.getNome());
				funcionario.setSenha(this.getSenha());
				funcionario.setUsuario(this.getUsuario());
				funcionarios.set(i, funcionario);
				break;
			}
		}
	}
	
	public ArrayList<Agendamento> getAtendimentosFuncionario() {
		return atendimentosFuncionario;
	}
	public void setAtendimentosFuncionario(int id) {
		this.atendimentosFuncionario = new Agendamento().atendimentosFuncionario(id);
	}
	public Funcionario[] listarFuncionarios() {
		return null;
	}
}
