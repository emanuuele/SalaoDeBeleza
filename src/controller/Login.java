package controller;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Scanner;

import model.Cliente;
import model.Funcionario;
import model.Pessoa;

public class Login {
	public static void login() throws Exception {
		Scanner scan = new Scanner(System.in);
		System.out.println("Usuário");
		String usuario = scan.next();
		try {
			System.out.println("Senha");
			String senha = scan.next();
			Pessoa pessoa = new Pessoa().loginUsuario(usuario);
			MessageDigest cript = MessageDigest.getInstance("MD5");
			byte[] hash1 = cript.digest(senha.getBytes(StandardCharsets.UTF_8));
			if(pessoa != null) {
                if (Arrays.toString(hash1).equals(pessoa.getSenha())) {
					System.out.println("logou");
				} else {
					System.out.println(pessoa);
					System.out.println("Senha incorreta");
				}
            } else {
            	System.out.println("Confirmação da senha");
    			String csenha = scan.next();
    			if(senha.equals(csenha)) {
    				Cliente cli = new Cliente();
    				cli.setSenha(csenha);
    				cli.setUsuario(usuario);
    				cli.salvar();
    				System.out.println("Cliente salvo com sucesso");
    			} else {
    				System.out.println("Senhas não coincidem");
    			}
            }
		} catch (SQLException e) {
			System.out.println("Ocorreu um erro: " + e);
		}
	}
}
