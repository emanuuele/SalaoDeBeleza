package model;

import java.sql.SQLException;

public interface BaseModel {
	public int salvar() throws SQLException;
	public int deletar(int id) throws SQLException;
	public int editar(int id) throws SQLException;
}
