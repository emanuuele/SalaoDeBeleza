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

//classe controladora dos valores que a aplicação recebe das views e "transporta" de forma que as models consiga receber e fazer suas devidas ações no banco de dados
public class FuncionarioController {
	//metodo para adicionar o cliente
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
						//verifica se há apenas numeros no campo de celular
						int celular = Integer.parseInt(celularInput);
						cli.setCelular(String.valueOf(celular));
						celularValido = true;
					} catch (NumberFormatException e) {
						System.out.println("Digite apenas números.");
						System.out.println("Deseja tentar novamente? Digite Sim/Não");
						String opt = scan.next();
						if (!opt.equalsIgnoreCase("sim")) {
							continuar = false;
							LoggedUser.home();
							break;
						}
					}
				}
				//pede uma senha e criptografa ela
				System.out.println("Digite a senha para o cliente");
				String senha = scan.next();
				MessageDigest cript = MessageDigest.getInstance("MD5");
				byte messageDigest[] = algorithm.digest(senha.getBytes("UTF-8"));
				cli.setSenha(Arrays.toString(messageDigest));
				cli.setTipo('C');
				System.out.println("Digite o usuário do cliente");
				String usuario = scan.next();
				Pessoa pessoa = new Pessoa().loginUsuario(usuario);
				//verifica se o usuario digitado está disponível e se sim, adicipona-o
				if (pessoa == null) {
					cli.setUsuario(usuario);
					cli.salvar();
					System.out.println("Cliente adicionado com sucesso!");
					LoggedUser.home();
				} //se não, pergunta se quer tentar novamente
				else {
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

	//metodo que adiciona o cargo, (está aqui porque quem pode adicionar cargo são os funcionários, então não há tanta necessidade de criar um controller apenas para isso
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
			//calculo para identificar quantos horarios seriam possíveis em um dia para realizar aquele procedimento
			float horarios = Math.ceilDiv(minTot, tempo);
			Calendar cal = Calendar.getInstance();
			//cria as datas para serem, usadas np select
			DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			LocalDateTime start_time = LocalDateTime.of(cal.get(Calendar.YEAR), mes, dia, 8, 0, 0);
			LocalDateTime end_time;
			int resultados = 0;
			//aqui começa a rodar os horarios disponíveis para aquele serviço. verifica se tem algum funcionario disponível e que seja compativel com o serviço que ele presta
			for (int i = 0; i < horarios; i++) {
				end_time = start_time.plus(tempo, ChronoUnit.MINUTES);
				System.out.println("Horário: " + start_time.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"))
						+ "  -  " + end_time.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")));

				String sql = "SELECT nome, id FROM Funcionario WHERE id_cargo = (SELECT id_cargo FROM Servico WHERE id = ?) AND id NOT IN ("
						+ "SELECT id_funcionario FROM Agendamento WHERE data BETWEEN ? AND ?"
						+ " AND data_final BETWEEN ? AND ?)";

				//converte o response do select de funcionarios
				PreparedStatement stmt = DAO.getConnection().prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);
				//atribui os valores para o select substituindo o ? pelos valores respectivamente
				stmt.setInt(1, id_servico);
				stmt.setString(2, start_time.format(format));
				stmt.setString(3, end_time.format(format));
				stmt.setString(4, start_time.format(format));
				stmt.setString(5, end_time.format(format));

				ResultSet rs = stmt.executeQuery();
				//adiciona os minutos do serviço para caso queira faz mais uma rodada de horarios
				start_time = start_time.plus(tempo, ChronoUnit.MINUTES);

				boolean encontrouDisponivel = false;
				ArrayList<String> idDisponiveis = new ArrayList<String>();
				while (rs.next()) {
					idDisponiveis.add(String.valueOf(rs.getInt("id")));
					//mostra os funcionários disoníveus
					System.out.println(rs.getInt("id") + ". " + rs.getString("nome"));
					encontrouDisponivel = true;
				}

				if (!encontrouDisponivel) {
					System.out.println("Nenhum funcionário disponível neste horário.");
				} else {
					Scanner scan = new Scanner(System.in);
					System.out.println("Deseja agendar este horário? Sim/Não");
					String esseHorario = scan.next().toLowerCase();
					//VERIFICA SE O USUARIO QUER ESSE HORARIO COM OS FUNCIONARIOS DA LISTA MOSTRADA
					if (esseHorario.equals("sim")) {
						System.out.println("Digite o ID do funcionário que deseja que realize seu serviço:");
						String idFunOpt = scan.next();
						if(idDisponiveis.indexOf(idFunOpt) == -1) {
							throw new Exception("Funcionário não existe ou não está disponível.");
						}
						try {
							idFun = Integer.parseInt(idFunOpt);
							Agendamento evento = new Agendamento();
							evento.setData(start_time.format(format));
							if (LoggedUser.getTipo() == 'F') {
								evento.setId_cliente(40);
							} else {
								evento.setId_cliente(LoggedUser.getID());
							}
							System.out.println(evento.getId_cliente());
							evento.setId_funcionario(idFun);
							evento.setId_servico(id_servico);
							evento.setDataFinal(end_time.format(format));
							//TERMINA O AGENDAMENTO
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
			LoggedUser.home();
		} catch (Exception e) {
			System.out.println("Ocorreu um erro: " + e.getMessage());
			AgendamentoView.agendarHorario();
			return 0;
		}
		return idFun;
	}
	//cadastra um funcionário e verifica se ele é gerente (essa informação é importante para poder mostrar relatórios específicos)
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
							LoggedUser.home();
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
					} else {
						LoggedUser.home();
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
			LoggedUser.home();
		} catch (SQLException e) {
			System.out.println("Ocorreu um erro: " + e.getMessage());
		}
	}
}
