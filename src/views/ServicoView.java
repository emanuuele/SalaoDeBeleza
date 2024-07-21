package views;

import java.util.ArrayList;

import model.Servico;


public class ServicoView {
	public static ArrayList<String> listarServicos() throws Exception {
		ArrayList<String> lista = new ArrayList<String>();
		Servico atendimento = new Servico();
		ArrayList<Servico> servicos = atendimento.listarServicos();
		if(servicos.size() > 0) {
			lista.add(String.format("%-5s %-20s ", "ID", "Servico"));
			for(Servico evento : servicos) {
				lista.add(String.format("\n %-5d %-20s", evento.getId(), evento.getNome()));
			}
		}
		return lista;
	}
}
