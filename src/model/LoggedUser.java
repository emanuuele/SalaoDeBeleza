package model;

import factory.Usuario;

public class LoggedUser {
	private static int ID;
	private static LoggedUser user = null;
	private static Usuario usuarioFactory;
	
	private LoggedUser(int id_user, Usuario usr) {
		ID = id_user;
		usuarioFactory = usr;
	}

	public static int getID() {
		return ID;
	}
	
	public static void setID(int id) {
		ID = id;
	}
	
	public static Usuario getUsuarioFactory() {
		return usuarioFactory;
	}

	public static void setUsuarioFactory(Usuario usuario) {
		usuarioFactory = usuario;
	}

	public static LoggedUser getInstance() {
		user = user == null ? new LoggedUser(ID, usuarioFactory) : user;
		return user;
	}

}
