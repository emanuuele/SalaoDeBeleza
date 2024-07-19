package views;

import java.util.ArrayList;
import java.util.Scanner;

import model.Agendamento;
import model.Servico;


public class AgendamentoView {
	public ArrayList<String> meusAtendimentos(int id) throws Exception {
		ArrayList<String> lista = new ArrayList<String>();
		lista.add(String.format("%-5s %-20s %-12s %-15s %-10s%n", "ID", "Data", "Cliente", "Funcionario", "Servico"));
		Agendamento atendimento = new Agendamento();
		ArrayList<Agendamento> atendimentos = atendimento.atendimentosFuncionario(id);
		for(Agendamento evento : atendimentos) {
			lista.add(String.format("%-5d %-20s %-12s %-15s %-10s%n", evento.getId(), evento.getData(), evento.getNomeCliente(evento.getId_cliente()), evento.getNomeFuncionario(evento.getId_funcionario()), evento.getNomeServico(evento.getId_servico())));
		}
		return lista;
	}
	
	public ArrayList<String> meusAgendamentos(int id) throws Exception {
		ArrayList<String> lista = new ArrayList<String>();
		lista.add(String.format("%-5s %-20s %-12s %-15s %-10s%n", "ID", "Data", "Cliente", "Funcionario", "Servico"));
		Agendamento atendimento = new Agendamento();
		ArrayList<Agendamento> atendimentos = atendimento.agendamentosCliente(id);
		for(Agendamento evento : atendimentos) {
			lista.add(String.format("\n %-5d %-20s %-12s %-15s %-10s%n", evento.getId(), evento.getData(), evento.getNomeCliente(evento.getId_cliente()), evento.getNomeFuncionario(evento.getId_funcionario()), evento.getNomeServico(evento.getId_servico())));
		}
		return lista;
	}
	
	public void agendarHorario() {
		try {
			Scanner scan = new Scanner(System.in);
			ServicoView.listarServicos();
			System.out.println("Digite o ID do procedimento que deseja fazer");
			String optServico = scan.next();
			System.out.println(String.format("%-5s %-20s ", "ID", "Mês"));
			
			
			System.out.println("Digite o ID do mes que deseja fazer o procedimento");
			String optMes = scan.next();
		} catch (Exception e) {

		}
	}
	
	
}
