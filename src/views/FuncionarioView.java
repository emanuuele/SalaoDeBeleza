package views;

import java.util.ArrayList;

import model.Funcionario;

public class FuncionarioView {
	public String home(int id_funcionario) {
		Funcionario funcionarios = new Funcionario();
		ArrayList<Funcionario> lista = funcionarios.listarFuncionarios();
		boolean gerente = false;
		for(Funcionario fun : lista) {
			if(fun.getId() == id_funcionario && fun.getEhGerente() == true) {
				gerente = true;
				break;
			}
		}
		String menu="";
		menu+="Home Funcionário";
		menu+="\n 1-Meus atendimentos";
		menu+="\n 2-Agendar atendimentos";
		menu+="\n 3-Cadastrar Cliente";
		menu+="\n 4-Cadastrar Funcionário";
		menu+="\n 5-Cadastrar Serviço";
		menu+="\n 6-Listar Clientes";
		menu+="\n 7-Listar Funcionários";
		menu+="\n 8-Listar Serviços";
		menu+="\n 9-Sair";
		if(gerente) {
			menu+="\n 10-Relatórios";
		}
		return menu;
		
	}
	
}
