package model;

import java.sql.SQLException;

public interface BaseModel {
	/*
	 * Esta interface define métodos básicos para operações CRUD (Create, Read, Update, Delete). Contém os métodos salvar(), deletar(int id) e editar(int id), todos lançando SQLException. Esta interface pode ser implementada por várias classes de modelo para garantir que elas suportem essas operações​
	 * */
	public int salvar() throws SQLException;
	public int deletar(int id) throws SQLException;
	public int editar(int id) throws SQLException;
}
