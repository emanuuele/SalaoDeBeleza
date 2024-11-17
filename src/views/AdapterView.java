package views;

import model.LoggedUser;

public class AdapterView implements IUserView {

	@Override
	public Menus selectedView() {
		if (LoggedUser.getUsuarioFactory().getTipo() == 'F') {
			return new FuncionarioView();
		}
		return new ClienteView();
	}
}
