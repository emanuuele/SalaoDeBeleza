package controller;

import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Scanner;

import model.Agendamento;
import model.Cargo;
import model.Cliente;
import model.DAO;
import model.Funcionario;
import model.LoggedUser;
import model.Pessoa;
import model.Servico;
import views.AgendamentoView;
import views.CargoView;
import views.ClienteView;
import views.FuncionarioView;

public class FuncionarioController {
	public static void addCliente() throws Exception {
		try (Connection connection = DAO.getConnection()) {
			boolean continuar = true;
			while (continuar) {
				continuar = false;
				MessageDigest algorithm = MessageDigest.getInstance("MD5");
				Scanner scan = new Scanner(System.in);
				Cliente cli = new Cliente();
				System.out.println("Digite o nome do cliente");
				cli.setNome(scan.next());
				boolean celularValido = false;
				while (!celularValido) {
					System.out.println("Digite o celular do cliente");
					String celularInput = scan.next();
					try {
						int celular = Integer.parseInt(celularInput);
						cli.setCelular(String.valueOf(celular));
						celularValido = true;
					} catch (NumberFormatException e) {
						System.out.println("Digite apenas números.");
						System.out.println("Deseja tentar novamente? Digite Sim/Não");
						String opt = scan.next();
						if (!opt.equalsIgnoreCase("sim")) {
							continuar = false;
							break;
						}
					}
				}
				System.out.println("Digite a senha para o cliente");
				String senha = scan.next();
				MessageDigest cript = MessageDigest.getInstance("MD5");
				byte messageDigest[] = algorithm.digest(senha.getBytes("UTF-8"));
				cli.setSenha(Arrays.toString(messageDigest));
				cli.setTipo('C');
				System.out.println("Digite o usuário do cliente");
				String usuario = scan.next();
				Pessoa pessoa = new Pessoa().loginUsuario(usuario);
				if (pessoa == null) {
					cli.setUsuario(usuario);
					cli.salvar();
					System.out.println("Cliente adicionado com sucesso!");
				} else {
					System.out.println("Já existe alguém com este usuário");
					System.out.println("Deseja tentar novamente? Digite Sim/Não");
					String opt = scan.next();
					if (opt.toLowerCase().equals("sim")) {
						continuar = true;
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void addCargo() throws Exception {
		try (Connection connection = DAO.getConnection()) {
			boolean continuar = true;
			while (continuar) {
				continuar = false;
				Scanner scan = new Scanner(System.in);
				Cargo cargo = new Cargo();
				System.out.println("Digite o nome do cargo");
				cargo.setNome(scan.next());
				cargo.salvar();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static int agendar(int id_servico, int tempo, int dia, int mes) {
		int idFun = 0;
		try {
			final int minTot = 600;
			float horarios = Math.ceilDiv(minTot, tempo);
			Calendar cal = Calendar.getInstance();
			DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			LocalDateTime start_time = LocalDateTime.of(cal.get(Calendar.YEAR), mes, dia, 8, 0, 0);
			LocalDateTime end_time;
			int resultados = 0;

			for (int i = 0; i < horarios; i++) {
				end_time = start_time.plus(tempo, ChronoUnit.MINUTES);
				System.out.println("Horário: " + start_time.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"))
						+ "  -  " + end_time.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")));

				String sql = "SELECT nome, id FROM Funcionario WHERE id_cargo = (SELECT id_cargo FROM Servico WHERE id = ?) AND id NOT IN ("
						+ "SELECT id_funcionario FROM Agendamento WHERE data BETWEEN ? AND ?"
						+ " AND data_final BETWEEN ? AND ?)";

				PreparedStatement stmt = DAO.getConnection().prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);

				stmt.setInt(1, id_servico);
				stmt.setString(2, start_time.format(format));
				stmt.setString(3, end_time.format(format));
				stmt.setString(4, start_time.format(format));
				stmt.setString(5, end_time.format(format));

				ResultSet rs = stmt.executeQuery();
				start_time = start_time.plus(tempo, ChronoUnit.MINUTES);

				boolean encontrouDisponivel = false;
				ArrayList<String> idDisponiveis = new ArrayList<String>();
				while (rs.next()) {
					idDisponiveis.add(String.valueOf(rs.getInt("id")));
					System.out.println(rs.getInt("id") + ". " + rs.getString("nome"));
					encontrouDisponivel = true;
				}

				if (!encontrouDisponivel) {
					System.out.println("Nenhum funcionário disponível neste horário.");
				} else {
					Scanner scan = new Scanner(System.in);
					System.out.println("Deseja agendar este horário? Sim/Não");
					String esseHorario = scan.next().toLowerCase();

					if (esseHorario.equals("sim")) {
						System.out.println("Digite o ID do funcionário que deseja que realize seu serviço:");
						String idFunOpt = scan.next();
						if(idDisponiveis.indexOf(idFunOpt) == -1) {
							throw new Exception("Esse funcionário não existe ou não está disponível");
						}
						try {
							idFun = Integer.parseInt(idFunOpt);
							Agendamento evento = new Agendamento();
							evento.setData(start_time.format(format));
							if (LoggedUser.getTipo() == 'F') {
								evento.setId_cliente(1);
							} else {
								evento.setId_cliente(LoggedUser.getID());
							}
							evento.setId_funcionario(idFun);
							evento.setId_servico(id_servico);
							evento.setDataFinal(end_time.format(format));
							evento.salvar();
							System.out.println("Confirmado!");
							return idFun;
						} catch (NumberFormatException e) {
							throw new Exception("Digite apenas números válidos para o ID.");
						} finally {
							if (LoggedUser.getTipo() == 'F') {
								new FuncionarioView().home(0);
							} else {
								new ClienteView().home(0);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			System.out.println("Ocorreu um erro: " + e.getMessage());
			AgendamentoView.agendarHorario();
			return 0;
		}
		return idFun;
	}

	public static void cadastro() throws Exception {
		try (Connection connection = DAO.getConnection()) {
			boolean continuar = true;
			while (continuar) {
				continuar = false;
				MessageDigest algorithm = MessageDigest.getInstance("MD5");
				Scanner scan = new Scanner(System.in);
				Funcionario fun = new Funcionario();
				System.out.println("Digite o nome do funcionario");
				fun.setNome(scan.next());
				boolean celularValido = false;
				while (!celularValido) {
					System.out.println("Digite o celular do funcionario");
					String celularInput = scan.next();
					try {
						int celular = Integer.parseInt(celularInput);
						fun.setCelular(String.valueOf(celular));
						celularValido = true;
					} catch (NumberFormatException e) {
						System.out.println("Digite apenas números.");
						System.out.println("Deseja tentar novamente? Digite Sim/Não");
						String opt = scan.next();
						if (!opt.equalsIgnoreCase("sim")) {
							continuar = false;
							break;
						}
					}
				}
				System.out.println("Digite a senha para o funcionario");
				String senha = scan.next();
				MessageDigest cript = MessageDigest.getInstance("MD5");
				byte messageDigest[] = algorithm.digest(senha.getBytes("UTF-8"));
				fun.setSenha(Arrays.toString(messageDigest));
				fun.setTipo('F');
				ArrayList<String> listaCargos = CargoView.listarCargos();
				int cargoId = 0;
				if (listaCargos.size() > 0) {
					System.out.println("Digite o ID do cargo do funcionario");
					for (String string : listaCargos) {
						System.out.println(string);
					}
					String cargoOpt = scan.next();
					try {
						cargoId = Integer.parseInt(cargoOpt);
						if (Cargo.encontrarCargoPorId(cargoId) == null) {
							throw new NumberFormatException();
						}
					} catch (NumberFormatException e) {
						System.out.println("Digite uma opção válida");
						cadastro();
					}
				}
				fun.setId_cargo(cargoId);
				System.out.println("É gerente? Sim/Não");
				String gerenteOpt = scan.next();
				if (gerenteOpt.toLowerCase().equals("sim")) {
					fun.setEhGerente(1);
				}
				System.out.println("Digite o usuário do funcionario");
				String usuario = scan.next();
				Pessoa pessoa = new Pessoa().loginUsuario(usuario);
				if (pessoa == null) {
					fun.setUsuario(usuario);
					fun.salvar();
					System.out.println("Funcionario adicionado com sucesso!");
					FuncionarioView vw = new FuncionarioView();
					vw.home(0);
				} else {
					System.out.println("Já existe alguém com este usuário");
					System.out.println("Deseja tentar novamente? Digite Sim/Não");
					String opt = scan.next();
					if (opt.toLowerCase().equals("sim")) {
						continuar = true;
					}
				}
			}
		} catch (SQLException e) {
			System.out.println("Ocorreu um erro: " + e.getMessage());
		}
	}

	public static void cadastroCargo() {
		try (Connection connection = DAO.getConnection()) {
			System.out.println("Digite o nome do cargo");
			Scanner scan = new Scanner(System.in);
			String nome = scan.next();
			Cargo cargo = new Cargo();
			cargo.setNome(nome);
			cargo.salvar();
			System.out.println("Cargo salvo com sucesso!");
		} catch (SQLException e) {
			System.out.println("Ocorreu um erro: " + e.getMessage());
		}
	}

	public static void cadastroServico() throws Exception {
		try (Connection connection = DAO.getConnection()) {
			System.out.println("Digite o nome do serviço");
			Scanner scan = new Scanner(System.in);
			String nome = scan.next();
			Servico servico = new Servico();
			servico.setNome(nome);
			ArrayList<String> listaCargos = CargoView.listarCargos();
			int cargoId = 0;
			if (listaCargos.size() > 0) {
				System.out.println("Digite o ID do cargo do serviço");
				for (String string : listaCargos) {
					System.out.println(string);
				}
				String cargoOpt = scan.next();
				try {
					cargoId = Integer.parseInt(cargoOpt);
					if (Cargo.encontrarCargoPorId(cargoId) == null) {
						throw new NumberFormatException();
					}
					System.out.println("Digite o valor:");
					String valor = scan.next();
					valor = valor.replace(',', '.');
					System.out.println("Digite o tempo em minutos!:");
					String tempo = scan.next();
					servico.setTempo(Integer.parseInt(tempo));
				} catch (NumberFormatException e) {
					System.out.println("Digite uma opção válida");
					cadastroServico();
				}
			}

			if (cargoId == 0) {
				FuncionarioController.cadastroCargo();
			} else {
				servico.setId_cargo(cargoId);
				servico.salvar();
				System.out.println("Serviço salvo com sucesso!");
			}
		} catch (SQLException e) {
			System.out.println("Ocorreu um erro: " + e.getMessage());
		}
	}
}
