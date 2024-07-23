package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.DAO;

public class RelatorioController {
	public static int financeiro() {
		try {
			String sql = "select count(*) as qtd, s.nome, sum(s.valor) as total, monthname(a.data) as mes from servico as s join agendamento as a on (s.id = a.id_servico) group by mes";
	        PreparedStatement stmt = DAO.getConnection().prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
	        ResultSet rs = stmt.executeQuery();
	        File file = new File("financeiro.txt");
	        try(PrintWriter write = new PrintWriter(file.getAbsoluteFile())) {
				write.println(String.format("\n %-5s %-20s %-20s %-10s", "QTD", "NOME SERVIÇO", "TOTAL MêS", "MÊS"));
	        	while(rs.next()) {
					write.println(String.format("\n %-5d %-20s %-20s %-10s", rs.getInt("qtd"), rs.getString("nome"), String.valueOf(rs.getFloat("total")).replace(".", ","), rs.getString("mes")));
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
			String sql = "select count(*) as qtd, c.nome, monthname(a.data) as mes from agendamento as a join cliente as c on (c.id = a.id_cliente) group by c.id, mes";
	        PreparedStatement stmt = DAO.getConnection().prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
	        ResultSet rs = stmt.executeQuery();
	        File file = new File("clientes.txt");
	        try(PrintWriter write = new PrintWriter(file.getAbsoluteFile())) {
				write.println(String.format("\n %-5s %-20s %-10s", "QTD", "NOME", "MÊS"));
	        	while(rs.next()) {
					write.println(String.format("\n %-5d %-20s %-20s", rs.getInt("qtd"), rs.getString("nome"), rs.getString("mes")));
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
}
