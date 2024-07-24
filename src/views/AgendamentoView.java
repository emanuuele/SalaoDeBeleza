package views;

import java.util.ArrayList;
import java.util.Scanner;

import controller.FuncionarioController;
import model.Agendamento;
import model.Servico;

//Classe para gerenciar a interface do usuário relacionada aos agendamentos
public class AgendamentoView {
    // Método para exibir uma lista de agendamentos atribuidos ao funcionario
	public ArrayList<String> meusAtendimentos(int id) throws Exception {
		ArrayList<String> lista = new ArrayList<String>();
		lista.add(String.format("%-5s %-20s %-12s %-15s %-10s%n", "ID", "Data", "Cliente", "Funcionario", "Servico"));
		Agendamento atendimento = new Agendamento();
		ArrayList<Agendamento> atendimentos = atendimento.atendimentosFuncionario(id);
		for (Agendamento evento : atendimentos) {
			lista.add(String.format("%-5d %-20s %-12s %-15s %-10s%n", evento.getId(), evento.getData(),
					evento.getNomeCliente(),
					evento.getNomeFuncionario(),
					evento.getNomeServico()));
		}
		return lista;
	}
	// lista os atendimentos atribuidos ao cliente 
	public ArrayList<String> meusAgendamentos(int id) throws Exception {
		ArrayList<String> lista = new ArrayList<String>();
		lista.add(String.format("%-5s %-20s %-12s %-15s %-10s%n", "ID", "Data", "Cliente", "Funcionario", "Servico"));
		Agendamento atendimento = new Agendamento();
		ArrayList<Agendamento> atendimentos = atendimento.agendamentosCliente(id);
		for (Agendamento evento : atendimentos) {
			lista.add(String.format("\n %-5d %-20s %-12s %-15s %-10s%n", evento.getId(), evento.getData(),
					evento.getNomeCliente(),
					evento.getNomeFuncionario(),
					evento.getNomeServico()));
		}
		return lista;
	}

	public static void agendarHorario() {
		try {
			Scanner scan = new Scanner(System.in);
			ArrayList<String> servicos = ServicoView.listarServicos();
			if (servicos.size() == 0) {
				System.out.println("Nenhum serviço");
			} else {
				for (String string : servicos) {
					System.out.println(string);
				}
				System.out.println("Digite o ID do procedimento que deseja fazer");
				String optServico = scan.next();
				Servico servico = Servico.encontrarServicoPorId(Integer.parseInt(optServico));
				if (servico == null) {
					throw new Exception("Digite um serviço válido");
				}
				String meses = String.format("\n %-5s %-20s ", "ID", "Mês");
				meses += String.format("\n %-5s %-20s ", "1", "Jan");
				meses += String.format("\n %-5s %-20s ", "2", "Fev");
				meses += String.format("\n %-5s %-20s ", "3", "Mar");
				meses += String.format("\n %-5s %-20s ", "4", "Abr");
				meses += String.format("\n %-5s %-20s ", "5", "Mai");
				meses += String.format("\n %-5s %-20s ", "6", "Jun");
				meses += String.format("\n %-5s %-20s ", "7", "Jul");
				meses += String.format("\n %-5s %-20s ", "8", "Ago");
				meses += String.format("\n %-5s %-20s ", "9", "Set");
				meses += String.format("\n %-5s %-20s ", "10", "Out");
				meses += String.format("\n %-5s %-20s ", "11", "Nov");
				meses += String.format("\n %-5s %-20s ", "12", "Dez");
				System.out.println(meses);
				System.out.println("Digite o ID do mes que deseja fazer o procedimento");
				String optMes = scan.next();
				if (Integer.parseInt(optMes) <= 0 || Integer.parseInt(optMes) > 12) {
					throw new Exception("Digite um mês válido!");
				}
				System.out.println("Digite o dia do mes que deseja fazer o procedimento");
				String optDia = scan.next();
				try {
					int response = FuncionarioController.agendar(Integer.parseInt(optServico), servico.getTempo(),
							Integer.parseInt(optDia), Integer.parseInt(optMes));
				} catch (NumberFormatException e) {
					System.out.println("Digite apenas numeros!");
					agendarHorario();
				}
			}
		} catch (Exception e) {
			System.out.println("Ocorreu um erro: " + e.getMessage());
			agendarHorario();
		}
	}

}
