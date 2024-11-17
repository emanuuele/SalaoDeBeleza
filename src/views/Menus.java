package views;

import java.util.Scanner;

public abstract class Menus {
	public abstract void home(int id) throws Exception;

	public static String menuEditarExcluir() {
		String tipo = "";
		Scanner scan = new Scanner(System.in);
		System.out.println("Digite \n 1 - Editar \n 2 - Excluir");
		tipo = scan.next();
		scan.close();
		if (tipo.equals("1") || tipo.equals("2")) {
			return tipo;
		} else {
			throw new IllegalArgumentException("Digite 1 ou 2: ");
		}
	}
}
