package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DAO {
	private static final String URL = "jdbc:mysql://localhost:3306/salaodebeleza";
    private static final String USER = "root";
    private static final String PASSWORD = "";
   
    // Método para estabelecer uma conexão com o banco de dados
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
