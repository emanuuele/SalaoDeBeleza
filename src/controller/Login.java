package controller;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Scanner;

import model.Cliente;
import model.Funcionario;
import model.LoggedUser;
import model.Pessoa;
import views.ClienteView;
import views.FuncionarioView;

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
					if(pessoa.getTipo() == 'C') {
						Pessoa cli = new Cliente().getClientePorUsuario(usuario);
						LoggedUser login = new LoggedUser(cli.getId(), cli.getTipo());
						ClienteView vw = new ClienteView();
						vw.home(0);
					} else {
						Pessoa fun = new Cliente().getClientePorUsuario(usuario);
						LoggedUser login = new LoggedUser(fun.getId(), fun.getTipo());
						FuncionarioView vw = new FuncionarioView();
						vw.home(fun.getId());
					}
				} else {
					System.out.println("Senha incorreta");
					Login.login();
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
    				Login.login();
    			} else {
    				System.out.println("Senhas não coincidem");
    			}
            }
		} catch (Exception e) {
			System.out.println("Ocorreu um erro: " + e);
		}
	}
}
