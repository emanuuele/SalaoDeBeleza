package controller;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Scanner;

import model.Cargo;
import model.Cliente;
import model.DAO;
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
}
