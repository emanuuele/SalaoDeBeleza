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
		switch (opt) {
		case "1": {
			this.agendarServico();
			break;
		}
		case "2": {
			this.clienteModel.getMeusAgendamentos();
		}
		case "3": {
			break;
		}
		case "4": {
			Login.login();
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + opt);
		}
	}
	
	public void agendarServico() {
		try {
			boolean valido = false;
            while (!valido) {
            	for (String servico : ServicoView.listarServicos()) {
            		System.out.println(servico);
            	}
            	System.out.println("Digite o id do serviço que deseja fazer");
            	Scanner scan = new Scanner(System.in);

                try {
                	String servicoIDopt = scan.next();
                    int servicoId = Integer.parseInt(servicoIDopt);
                    System.out.println(
                    		"1. JAN"
                    		+ "2. FEV"
                    		+ "3. MAR"
                    		+ "4. ABR"
                    		+ "5. MAI"
                    		+ "6. JUN"
                    		+ "7. JUL"
                    		+ "8. AGO"
                    		+ "9. SET"
                    		+ "10. OUT"
                    		+ "11. NOV"
                    		+ "12, DEZ"
                    		);
                    
                	System.out.println("Digite o mês que deseja:");
                	String mesOpt = scan.next();
                	int mesID = Integer.parseInt(mesOpt);
                	System.out.println("Digite o dia do mês");
                	String diaIDopt = scan.next();
                	int diaID = Integer.parseInt(diaIDopt);
                	Funcionario.funcionariosPorCargo(servicoId, diaID, mesID);
                	
                	
                	String funcIDopt = scan.next();
                	int funcID = Integer.parseInt(funcIDopt);
                	

                } catch (NumberFormatException e) {
                    System.out.println("Digite apenas números.");
                    System.out.println("Deseja tentar novamente? Digite Sim/Não");
                    String opt = scan.next();
                    if (!opt.equalsIgnoreCase("sim")) {
                        valido = false;
                        break;
                    }
                }
            }
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
}
