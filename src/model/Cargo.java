package model;

public class Cargo {
	private String nome;
	private int id;
	private int id_serviço;
	
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


}
