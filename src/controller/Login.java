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
			//SE NÃO HÁ NENHUMA PESSOA COM AQUELE USUÁRIO, CRIA UM
			if(pessoa != null) {
				//VERIFICA SE A SENHA ESTA CORRETa
                if (Arrays.toString(hash1).equals(pessoa.getSenha())) {
					if(String.valueOf(pessoa.getTipo()).equals("C")) {
						Pessoa cli = Cliente.getClientePorUsuario(usuario);
						//Polimorfismo login de cliente como pessoa
						new LoggedUser(cli.getId(), 'C', 0);
						ClienteView vw = new ClienteView();
						vw.home(0);
						//chama a home de cliente e cria um objeto login que será usada em toda a aplicação
					} else {
						Funcionario fun = Funcionario.getFuncionarioPorUsuario(usuario);
						new LoggedUser(fun.getId(), 'F', fun.getEhGerente());
						FuncionarioView vw = new FuncionarioView();
						vw.home(0);
						//chama a home de funcionario e cria um objeto login que será usada em toda a aplicação
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
    				byte[] hashCSenha = cript.digest(senha.getBytes(StandardCharsets.UTF_8));
    				cli.setTipo('C');
    				cli.setSenha(Arrays.toString(hashCSenha));
    				cli.setUsuario(usuario);
    				//chama o método de salvar o cliente no banco (só salva clientes por esse cqminho)
    				cli.salvar();
    				System.out.println("Cliente salvo com sucesso");
    				Login.login();
    			} else {
    				System.out.println("Senhas não coincidem");
    				Login.login();
    			}
            }
		} catch (Exception e) {
			System.out.println("Ocorreu um erro: " + e);
		}
	}
}
