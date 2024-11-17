package factory;

import model.LoggedUser;

public class FuncionarioUsuarioFactory extends Usuario {

	@Override
	public LoggedUser acessar(int id) {
		LoggedUser.setID(id);
		LoggedUser.setUsuarioFactory(new FuncionarioUsuarioFactory());
		return LoggedUser.getInstance();
	}

	@Override
	public char getTipo() {
		return 'F';
	}
}
