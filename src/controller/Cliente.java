package controller;

public class Cliente extends Pessoa implements BaseController{

	private int id;
	private Agendamento[] meusAgendamentos;
	
	public Cliente(String nome, String usuario, String celular, String senha, char tipo, int id) {
		super(nome, usuario, celular, senha, tipo);
		this.id=id;
		this.setMeusAgendamentos(new Agendamento().agendamentosCliente(id));
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public Agendamento[] getMeusAgendamentos() {
		return meusAgendamentos;
	}
	public void setMeusAgendamentos(Agendamento[] meusAgendamentos) {
		this.meusAgendamentos = meusAgendamentos;
	}
}
