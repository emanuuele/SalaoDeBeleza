package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoggedUser {
	public LoggedUser(int id_user, char tipo_user) {
        this.ID = id_user;
        this.TIPO = tipo_user;
    }
	private static int ID;
	private static char TIPO;
	
	public static int getID() {
		return ID;
	}
	public static char getTipo() {
		return TIPO;
	}
	
}
