package views;


import java.security.MessageDigest;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Scanner;

import controller.Login;
import model.Cliente;
import model.Funcionario;
import model.LoggedUser;
import model.Pessoa;
import model.Servico;

public class ClienteView extends Menus{
	private Cliente clienteModel;
	public ClienteView() {
		this.clienteModel = new Cliente();
	}
	@Override
	public void home(int id) throws Exception {
		String menu = "";
		menu = menu + "Home Cliente";
		menu+="\n 1-Agendar horário";
		menu+="\n 2-Ver meus agendamentos";
		menu+="\n 3-Configurações";
		menu+="\n 4-Sair";
		menu+="\n Digite a opção";
		
		System.out.println(menu);
		Scanner scan = new Scanner(System.in);
		String opt = scan.next();
		try {
			switch (opt) {
			case "1": {
				AgendamentoView.agendarHorario();
				break;
			}
			case "2": {
				AgendamentoView vw = new AgendamentoView();
				for (String event : vw.meusAgendamentos(LoggedUser.getID())) {
					System.out.println(event);
				}
				this.home(0);
				break;
			}
			case "3": {
				configuracoes();
				break;
			}
			case "4": {
				Login.login();
			}
			default:
				throw new IllegalArgumentException("Unexpected value: " + opt);
			}
		} catch (IllegalArgumentException e) {
			System.out.println("Digite uma opção válida");
			this.home(0);
		}
	}
	
	public static void configuracoes() throws Exception {
		try {
			Scanner scan = new Scanner(System.in);
			System.out.println("Digite o seu nome:");
			String nome = scan.next();
			Cliente cli = new Cliente();
			cli.setNome(nome);
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
						new ClienteView().home(0);
						break;
					}
				}
			}
			System.out.println("Digite sua senha: ");
			String senha = scan.next();
			MessageDigest algorithm = MessageDigest.getInstance("MD5");
			byte messageDigest[] = algorithm.digest(senha.getBytes("UTF-8"));
			cli.setSenha(Arrays.toString(messageDigest));
			System.out.println("Digite o usuário do cliente");
			String usuario = scan.next();
			Pessoa pessoa = new Pessoa().loginUsuario(usuario);
			if (pessoa == null) {
				cli.setUsuario(usuario);
				cli.salvar();
				System.out.println("Cliente adicionado com sucesso!");
			} else {
				System.out.println("Já existe alguém com este usuário");
				System.out.println("Deseja tentar novamente? Digite Sim/Não");
				String opt = scan.next();
				if (opt.toLowerCase().equals("sim")) {
					configuracoes();
				}
			}
			cli.editar(LoggedUser.getID());
			System.out.println("Perfil editado com sucesso");
			new ClienteView().home(0);
		} catch (SQLException e) {
			System.out.println("Ocorreu um erro: " + e.getMessage());
		}
	}
	
}
