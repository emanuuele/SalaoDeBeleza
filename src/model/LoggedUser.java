package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import views.ClienteView;
import views.FuncionarioView;

public class LoggedUser {
	public LoggedUser(int id_user, char tipo_user, int ehGerente) {
        this.ID = id_user;
        this.TIPO = tipo_user;
        this.ehGerente = ehGerente;
    }
	private static int ID;
	private static char TIPO;
	private static int ehGerente;

	
	public static int getEhGerente() {
		return ehGerente;
	}
	public static void setEhGerente(char ehGerente) {
		LoggedUser.ehGerente = ehGerente;
	}
	public static int getID() {
		return ID;
	}
	public static char getTipo() {
		return TIPO;
	}
	
	public static void home() throws Exception {
		if (getTipo() == 'F') {
			new FuncionarioView().home(ID);
		} else {
			new ClienteView().home(ID);
		}
	}
	
}
