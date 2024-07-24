package views;

import java.util.ArrayList;

import model.Cargo;

public class CargoView {
	// converte a lista de arraylist de objetos para string
	public static ArrayList<String> listarCargos() throws Exception {
		ArrayList<String> lista = new ArrayList<String>();
		Cargo cargo = new Cargo();
		ArrayList<Cargo> cargos = cargo.listarCargos();
		if(cargos.size() > 0) {
			lista.add(String.format("%-5s %-20s ", "ID", "Servico"));
			for(Cargo cargoItem : cargos) {
				lista.add(String.format("\n %-5d %-20s", cargoItem.getId(), cargoItem.getNome()));
			}
		}
		return lista;
	}
}
