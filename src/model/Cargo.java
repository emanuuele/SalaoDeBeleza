package model;

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
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getId_serviço() {
		return id_serviço;
	}
	public void setId_serviço(int id_serviço) {
		this.id_serviço = id_serviço;
	}
	@Override
	public void salvar() {
		cargos.add(this);
	}
	@Override
	public void deletar(int id) {
        cargos.removeIf(servico -> servico.getId() == id);
	}
	@Override
	public void editar(int id) {
		
	}


}
