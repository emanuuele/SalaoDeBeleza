package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.DAO;

public class RelatorioController {
	private String mes;
	private String nome;
	private int qtd;
	private float total;
	
	public String getMes() {
		return this.mes;
	}
	public void setMes(String mes) {
		this.mes = mes;
	}
	public String getNome() {
		return this.nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public int getQtd() {
		return this.qtd;
	}
	public void setQtd(int qtd) {
		this.qtd = qtd;
	}
	public float getTotal() {
		return this.total;
	}
	public void setTotal(float total) {
		this.total = total;
	}
	public static int financeiro() {
		try {
			String sql = "select count(*) as qtd, s.nome, sum(s.valor) as total, monthname(a.data) as mes from servico as s join agendamento as a on (s.id = a.id_servico) group by a.id_servico, mes";
	        PreparedStatement stmt = DAO.getConnection().prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
	        ResultSet rs = stmt.executeQuery();
	        File file = new File("financeiro.txt");
	        try(PrintWriter write = new PrintWriter(file.getAbsoluteFile())) {
				write.println(String.format("\n %-5s %-20s %-20s %-10s", "QTD", "NOME SERVIÇO", "TOTAL MêS", "MÊS"));
	        	while(rs.next()) {
	        		RelatorioController result = new RelatorioController();
	        		result.setMes(rs.getString("mes"));
	        		result.setNome(rs.getString("nome"));
	        		result.setQtd(rs.getInt("qtd"));
	        		result.setTotal(rs.getFloat("total"));
					write.println(result.toString());
				}
	        	System.out.println("FINANCEIRO - Veja em: " + file.getAbsoluteFile());
			} catch (FileNotFoundException e) {
				System.out.println(e.getMessage());
			}
			return 1;
		} catch (SQLException e) {
			System.out.println("Ocorreu um erro: " + e.getMessage());
			return 0;
		}
	}
	public static int clientes() {
		try {
			String sql = "select count(*) as qtd, c.nome, monthname(a.data) as mes, '' as total from agendamento as a join cliente as c on (c.id = a.id_cliente) group by c.id, mes";
	        PreparedStatement stmt = DAO.getConnection().prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
	        ResultSet rs = stmt.executeQuery();
	        File file = new File("clientes.txt");
	        try(PrintWriter write = new PrintWriter(file.getAbsoluteFile())) {
				write.println(String.format("\n %-5s %-20s %-10s", "QTD", "NOME", "MÊS"));
	        	while(rs.next()) {
	        		RelatorioController result = new RelatorioController();
	        		result.setMes(rs.getString("mes"));
	        		result.setNome(rs.getString("nome"));
	        		result.setQtd(rs.getInt("qtd"));
					write.println(result.toString());
				}
	        	System.out.println("CLIENTES - Veja em: " + file.getAbsoluteFile());
			} catch (FileNotFoundException e) {
				System.out.println(e.getMessage());
			}
			return 1;
		} catch (SQLException e) {
			System.out.println("Ocorreu um erro: " + e.getMessage());
			return 0;
		}
	}
	
	@Override
	public String toString() {
		return String.format("\n %-5d %-20s %-20s %-10s", this.getQtd(), this.getNome(), this.getMes(), this.getTotal() > 0 ? String.valueOf(this.getTotal()) : "");
	}
	
	
	
	//Converte de objeto relatorio para String 
}
