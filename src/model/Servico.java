package model;

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
	public Servico[] listarServicos() {
		return null;
	}
	public Servico encontrarServicoPorId(int id) {
	    for (Servico servico : servicos) {
	        if (servico.getId() == id) {
	            return servico;
	        }
	    }
	    return null;
	}
	@Override
	public void salvar() {
		servicos.add(this);
	}
	@Override
	public void deletar(int id) {
        servicos.removeIf(servico -> servico.getId() == id);
	}
	@Override
	public void editar(int id) {
		for(int i = 0; i < servicos.size(); i++) {
			Servico servico = servicos.get(i);
			if(servico.getId() == id) {
				servico.setId_cargo(this.getId_cargo());
				servico.setNome(this.getNome());
				servico.setTempo(this.getTempo());
				servico.setValor(this.getValor());
				servicos.set(i, servico);
				break;
			}
		}
	}
}
