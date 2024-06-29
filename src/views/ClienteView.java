package views;

public class ClienteView {
	public String home() {
		String menu = "";
		menu = menu + "Home Cliente";
		menu+="\n 1-Agendar horário";
		menu+="\n 2-Ver meus agendamentos";
		menu+="\n 3-Configurações";
		menu+="\n 4-Sair";
		menu+="\n Digite a opção";
		return menu;
		
	}
}
