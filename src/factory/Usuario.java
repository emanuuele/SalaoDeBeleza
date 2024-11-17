package factory;

import model.LoggedUser;

public abstract class Usuario implements IUsuario {

	@Override
	public abstract LoggedUser acessar(int id);
	@Override
	public abstract char getTipo();

}
