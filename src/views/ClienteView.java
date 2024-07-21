package views;


import java.util.Scanner;

import controller.Login;
import model.Cliente;
import model.Funcionario;
import model.LoggedUser;
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
				this.clienteModel.getMeusAgendamentos();
			}
			case "3": {
				AgendamentoView.agendarHorario();
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
	
}
