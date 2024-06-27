package model;

public class Servico {
	private int id;
	private double valor;
	private String nome;
	private int tempo;
	private int id_cargo;
	public Servico(int id, double valor, String nome, int tempo, int id_cargo) {
		this.id = id;
		this.id_cargo = id_cargo;
		this.valor = valor;
		this.nome = nome;
		this.tempo = tempo;
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
	public Servico[] listarServicos() {
		return null;
	}
}
