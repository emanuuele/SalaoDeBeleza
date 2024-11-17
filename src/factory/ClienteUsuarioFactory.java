package factory;

import model.LoggedUser;

public class ClienteUsuarioFactory extends Usuario {

	@Override
	public LoggedUser acessar(int id) {
		LoggedUser.setID(id);
		LoggedUser.setUsuarioFactory(new ClienteUsuarioFactory());
		return LoggedUser.getInstance();
	}

	@Override
	public char getTipo() {
		return 'C';
	}

}
