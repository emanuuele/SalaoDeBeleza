package controller;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Arrays;

import model.Cliente;
import views.AgendamentoView;
import views.FuncionarioView;
import views.ServicoView;

public class Main {
	public static void main(String[] args) throws Exception {
		FuncionarioController fun = new FuncionarioController();
		//fun.addCliente();
		//AgendamentoView.agendarHorario();
		//FuncionarioController.cadastroServico();
		Login.login();
		//RelatorioController.financeiro();
	}

}