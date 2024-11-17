package factory;

import model.LoggedUser;

public interface IUsuario {
	public LoggedUser acessar(int id);
	public char getTipo();
}
