package controller;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Scanner;

import model.Cargo;
import model.Cliente;
import model.DAO;
import model.Funcionario;
import model.Pessoa;

public class FuncionarioController {
	public void addCliente() throws Exception{
		try (Connection connection = DAO.getConnection()) {
        	boolean continuar = true;
        	while(continuar) {
        		continuar = false;
        		MessageDigest algorithm = MessageDigest.getInstance("MD5");
                System.out.println("Funfou");
                Scanner scan = new Scanner(System.in);
                Cliente cli = new Cliente();
                System.out.println("Digite o nome do cliente");
                cli.setNome(scan.next());
                boolean celularValido = false;
                while (!celularValido) {
                    System.out.println("Digite o celular do cliente");
                    String celularInput = scan.next();
                    try {
                        int celular = Integer.parseInt(celularInput);
                        cli.setCelular(String.valueOf(celular));
                        celularValido = true;
                    } catch (NumberFormatException e) {
                        System.out.println("Digite apenas números.");
                        System.out.println("Deseja tentar novamente? Digite Sim/Não");
                        String opt = scan.next();
                        if (!opt.equalsIgnoreCase("sim")) {
                            continuar = false;
                            break;
                        }
                    }
                }
                System.out.println("Digite a senha para o cliente");
                String senha = scan.next();
                MessageDigest cript = MessageDigest.getInstance("MD5");
                byte messageDigest[] = algorithm.digest(senha.getBytes("UTF-8"));
                cli.setSenha(Arrays.toString(messageDigest));
                cli.setTipo('C');
                System.out.println("Digite o usuário do cliente");
                String usuario = scan.next();
                Pessoa pessoa = new Pessoa().loginUsuario(usuario);
                if(pessoa == null) {
                	cli.setUsuario(usuario);
                	cli.salvar();
                	System.out.println("Cliente adicionado com sucesso!");
                } else {
                	System.out.println("Já existe alguém com este usuário");
                	System.out.println("Deseja tentar novamente? Digite Sim/Não");
                	String opt = scan.next();
                	if(opt.toLowerCase().equals("sim")) {
                		continuar = true;
                	}
                }
        	}
        } catch (SQLException e) {
            e.printStackTrace();
        }
	}
	
	public void addCargo() throws Exception{
		try (Connection connection = DAO.getConnection()) {
        	boolean continuar = true;
        	while(continuar) {
        		continuar = false;
        		MessageDigest algorithm = MessageDigest.getInstance("MD5");
                System.out.println("Funfou");
                Scanner scan = new Scanner(System.in);
                Cargo cargo = new Cargo();
                System.out.println("Digite o nome do cliente");
                cargo.setNome(scan.next());
                cargo.salvar();
        	}
        } catch (SQLException e) {
            e.printStackTrace();
        }
	}
	
	public void funcionariosDisponiveis(int id_cargo,int tempo, int dia, int mes) {
		try {
			final int minTot = 600;
			float horarios = Math.ceilDiv(minTot, tempo);
	        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	        LocalDateTime start_time = LocalDateTime.parse(Calendar.YEAR + "-" + mes + "-" + dia + "08:00:00", format);
	        LocalDateTime end_time;
			for (int i = 0; i < horarios; i++) {
				end_time = start_time.plus(tempo, ChronoUnit.MINUTES);
				String sql = "select nome, id from Funcionario where id_cargo = ? and not in ("
						+ "select id_funcionario from Agendamento where data between " + start_time + " AND " + end_time
						+ "and data_final between " + start_time + " AND " + end_time
						+ ")";
				start_time = end_time;
				PreparedStatement stmt = DAO.getConnection().prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		        stmt.setInt(1, id_cargo);
		        ResultSet rs = stmt.executeQuery();
		        while (rs.next()) {
		            System.out.println(rs.getInt("id") + ". " + rs.getString("nome"));
		        }
			}
		} catch (Exception e) {
			System.out.println("Ocorreu um erro: "+ e.getMessage());
		}
	}
}
