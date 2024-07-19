package views;

import java.util.Scanner;

public abstract class Menus {
	public abstract void home(int id) throws Exception;
	public String menuEditarExcluir() {
		String tipo = "";
		Scanner scan = new Scanner(System.in);
		System.out.println("Digite \n 1 - Editar \n 2 - Excuir");
		tipo = scan.toString();
		if(tipo != "1" || tipo != "2") {
			throw new IllegalArgumentException("Digite 1 ou 2: ");
		}
		return tipo;
	};
}
