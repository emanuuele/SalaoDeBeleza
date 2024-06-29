package model;

public class Pessoa {
	private String nome;
	private String usuario;
	private String celular;
	private String senha;
	private char tipo;
	
	public Pessoa(String nome, String usuario, String celular, String senha, char tipo) {
		this.nome=nome;
		this.usuario=usuario;
		this.celular=celular;
		this.senha=senha;
		this.tipo=tipo;
	}
	public Pessoa() {
		
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getCelular() {
		return celular;
	}
	public void setCelular(String celular) {
		this.celular = celular;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public char getTipo() {
		return tipo;
	}
	public void setTipo(char tipo) {
		this.tipo = tipo;
	}
}
