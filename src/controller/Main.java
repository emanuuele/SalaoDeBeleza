package controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import model.Cargo;

import java.util.Arrays;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;

public class Main {
	public static void readDelimitedFile() {
        List<String> items = null;

        try {
            String content = new String(Files.readAllBytes(Paths.get("C:\\Projeto P2\\SalaoDeBeleza\\cargos.txt")));
            items = Arrays.asList(content.split("\\|"));
            for (String string : items) {
    			Cargo cargo = new Cargo();
    			cargo.setNome(string);
    			try {
					cargo.salvar();
				} catch (SQLException e) {
					System.out.println("Ocorreu um erro: "+e.getMessage());
				}
    		}
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	public static void main(String[] args) throws Exception {
		Login.login();
	}

}