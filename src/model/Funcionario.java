package model;

public class Funcionario extends Pessoa implements BaseModel {
	
	private int id;
	private boolean ehGerente;
	private int id_cargo;
	private Agendamento[] atendimentosFuncionario;
	
	public Funcionario(String nome, String usuario, String celular, String senha, char tipo, int id, boolean ehGerente, int id_cargo) {
		super(nome, usuario, celular, senha, tipo);
		this.id=id;
		this.id_cargo = id_cargo;
		this.ehGerente = ehGerente;
		this.setAtendimentosFuncionario(new Agendamento().atendimentosFuncionario(id));
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
		
	}
	@Override
	public void deletar() {
		
	}
	@Override
	public void editar() {
		
	}
	public Agendamento[] getAtendimentosFuncionario() {
		return atendimentosFuncionario;
	}
	public void setAtendimentosFuncionario(Agendamento[] atendimentosFuncionario) {
		this.atendimentosFuncionario = atendimentosFuncionario;
	}
	public Funcionario[] listarFincionarios() {
		return null;
	}
}
