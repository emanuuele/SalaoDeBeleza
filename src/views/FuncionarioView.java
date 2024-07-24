package views;

import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

import controller.FuncionarioController;
import controller.Login;
import controller.RelatorioController;
import model.Cargo;
import model.Cliente;
import model.Funcionario;
import model.LoggedUser;
import model.Pessoa;
import model.Servico;

public class FuncionarioView extends Menus {
	private Funcionario funcionarioModel;
	private Cliente clienteModel;
	private Servico servicoModel;
	private Cargo cargoModel;

	public FuncionarioView() {
		this.funcionarioModel = new Funcionario();
		this.clienteModel = new Cliente();
		this.servicoModel = new Servico();
		this.cargoModel = new Cargo();
	}

	@Override
	public void home(int id_funcionario) throws Exception {
		try {
			String menu = "";
			menu += "Home Funcionário";
			menu += "\n 1-Meus atendimentos";
			menu += "\n 2-Agendar atendimentos";
			menu += "\n 3-Cadastrar Cliente";
			menu += "\n 4-Cadastrar Funcionário";
			menu += "\n 5-Cadastrar Serviço";
			menu += "\n 6-Listar Clientes";
			menu += "\n 7-Listar Funcionários";
			menu += "\n 8-Listar Serviços";
			menu += "\n 9-Sair";
			if (LoggedUser.getEhGerente() == 1) {
				menu += "\n 10-Relatórios";
			}
			System.out.println(menu);
			Scanner scan = new Scanner(System.in);
			int opt = scan.nextInt();
			switch (opt) {
			case 1: {
				AgendamentoView vw = new AgendamentoView();
				for (String event : vw.meusAtendimentos(LoggedUser.getID())) {
					System.out.println(event);
				}
				this.home(0);
				break;
			}
			case 2: {
				AgendamentoView.agendarHorario();
				break;
			}
			case 3: {
				FuncionarioController.addCliente();
				break;
			}
			case 4: {
				FuncionarioController.cadastro();
				break;
			}
			case 5: {
				FuncionarioController.cadastroServico();
				break;
			}
			case 6: {
				this.menuEditarExcluir("C");
				break;
			}
			case 7: {
				this.menuEditarExcluir("F");
				break;
			}
			case 8: {
				this.menuEditarExcluir("S");
				break;
			}
			case 9: {
				Login.login();
				break;
			}
			case 10: {
				System.out.println("1 - FINACEIRO");
				System.out.println("2 - CLIENTES");
				System.out.println("Digite um opção:");
				try {
					int optRelatorio = scan.nextInt();
					switch (optRelatorio) {
					case 1: {
						RelatorioController.financeiro();
						break;
					}
					case 2: {
						RelatorioController.clientes();
						break;
					}
					default:
						throw new IllegalArgumentException("Digite um valor válido");
					}
				} catch (IllegalArgumentException e) {
					System.out.println("Digite um número");
				}
				this.home(0);
				break;
			}
			default:
				throw new InputMismatchException();
			}
		} catch (InputMismatchException e) {
			System.out.println("Digite uma opção válida");
			this.home(0);
		}
	}

	public int funEscolhido(String acao) {
		String menu = "Digite o ID que deseja " + acao;
		menu += String.format("\n %-5s %-20s %-12s %-15s %-10s%n", "ID", "Nome", "Cargo", "Celular", "Usuário");

		for (Funcionario fun : this.funcionarioModel.listarFuncionarios()) {
			menu += String.format(" %-5d %-20s %-12s %-15s %-10s%n", fun.getId(), fun.getNome(),
					fun.getEhGerente() == 1 ? "Gerente" : "", fun.getCelular(), fun.getUsuario());
		}

		System.out.println(menu);
		Scanner scan = new Scanner(System.in);
		return scan.nextInt();
	}

	public int clienteEscolhido(String acao) throws SQLException {
		String menu = "Digite o ID que deseja " + acao;
		menu += String.format("\n %-5s %-20s %-12s", "ID", "Nome", "Usuário");

		for (Cliente cli : this.clienteModel.listarClientes()) {
			menu += String.format("\n %-5d %-20s %-12s", cli.getId(), cli.getNome(), cli.getUsuario());
		}

		System.out.println(menu);
		Scanner scan = new Scanner(System.in);
		return scan.nextInt();
	}

	public int servicoEscolhido(String acao) {
		String menu = "Digite o ID que deseja " + acao;
		menu += String.format("\n %-5s %-20s", "ID", "Nome");

		for (Servico servico : this.servicoModel.listarServicos()) {
			menu += String.format("\n %-5d %-20s", servico.getId(), servico.getNome());
		}

		System.out.println(menu);
		Scanner scan = new Scanner(System.in);
		return scan.nextInt();
	}

	public int cargoEscolhido(String acao) {
		String menu = "Digite o ID que deseja " + acao;
		menu += String.format("\n %-5s %-20s", "ID", "Nome");

		for (Cargo cargo : this.cargoModel.listarCargos()) {
			menu += String.format("\n %-5d %-20s", cargo.getId(), cargo.getNome());
		}

		System.out.println(menu);
		Scanner scan = new Scanner(System.in);
		return scan.nextInt();
	}

	public void menuEditarExcluir(String tipo) throws Exception {
		try {
			String opt = super.menuEditarExcluir();
			Scanner scan = new Scanner(System.in);
			switch (opt) {
			case "1": {
				if (tipo.equals("F")) {
					int id_fun = this.funEscolhido("editar");
					if (this.funcionarioModel.getFuncionarioPorId(id_fun) == null) {
						throw new IllegalArgumentException("Funcionário não existe");
					}
					System.out.println("Digite o nome:");
					String nome = scan.next();
					System.out.println("Digite o usuario:");
					boolean continuar = true;
					while (continuar) {
						String usuario = scan.next();
						Pessoa pessoa = new Pessoa().loginUsuario(usuario);
						if (pessoa == null) {
							this.funcionarioModel.setNome(nome);
							this.funcionarioModel.setUsuario(usuario);
							this.funcionarioModel.editar(id_fun);
							System.out.println("Funcionario editado com sucesso!");
							continuar = false;
						} else {
							System.out.println("Já existe alguém com este usuário");
							System.out.println("Deseja tentar novamente? Digite Sim/Não");
							if (scan.next().toLowerCase().equals("sim")) {
								continuar = true;
							} else {
								new FuncionarioView().home(id_fun);
							}
						}
					}
					new FuncionarioView().home(id_fun);
				} else if (tipo.equals("C")) {
					int id_cliente = this.clienteEscolhido("editar");
					if (this.clienteModel.getClientePorId(id_cliente) == null) {
						throw new IllegalArgumentException("Cliente não existe");
					}
					System.out.println("Digite o nome:");
					String nome = scan.next();
					System.out.println("Digite o usuario:");
					boolean continuar = true;
					while (continuar) {
						String usuario = scan.next();
						Pessoa pessoa = new Pessoa().loginUsuario(usuario);
						if (pessoa == null) {
							this.clienteModel.setNome(nome);
							this.clienteModel.setUsuario(usuario);
							this.clienteModel.editar(id_cliente);
							System.out.println("Cliente editado com sucesso!");
							continuar = false;
						} else {
							System.out.println("Já existe alguém com este usuário");
							System.out.println("Deseja tentar novamente? Digite Sim/Não");
							if (scan.next().toLowerCase().equals("sim")) {
								continuar = true;
							} else {
								new FuncionarioView().home(0);
							}
						}
					}
					this.clienteModel.editar(id_cliente);
				} else if (tipo.equals("CA")) {
					int id_cargo = this.cargoEscolhido("editar");
					if (this.cargoModel.encontrarCargoPorId(id_cargo) == null) {
						throw new IllegalArgumentException("Cargo não existe");
					}
					System.out.println("Digite o nome:");
					String nome = scan.next();
					this.cargoModel.setNome(nome);
					this.cargoModel.editar(id_cargo);
					this.cargoModel.editar(id_cargo);
					System.out.println("Cargo editado com sucesso!");	
				} else if (tipo.equals("S")) {
					int id_servico = this.servicoEscolhido("editar");
					if (this.servicoModel.encontrarServicoPorId(id_servico) == null) {
						throw new IllegalArgumentException("Serviço não existe");
					}
					System.out.println("Digite o nome:");
					String nome = scan.next();
					this.servicoModel.setNome(nome);
					try {
						System.out.println("Digite o valor:");
						String valor = scan.next();
						valor = valor.replace(',', '.');
						System.out.println("Digite o tempo em minutos!:");
						String tempo = scan.next();
						this.servicoModel.setTempo(Integer.parseInt(tempo));
					} catch (NumberFormatException e) {
						System.out.println("Digite uma opção válida");
						this.menuEditarExcluir(tipo);
					}
					this.servicoModel.editar(id_servico);
				}
				break;
			}
			case "2": {
				if (tipo.equals("F")) {
					int id_fun = this.funEscolhido("excluir");
					if (this.funcionarioModel.getFuncionarioPorId(id_fun) == null) {
						throw new IllegalArgumentException("Funcionário não existe");
					}
					this.funcionarioModel.deletar(id_fun);
				} else if (tipo.equals("C")) {
					int id_cliente = this.clienteEscolhido("excluir");
					if (this.clienteModel.getClientePorId(id_cliente) == null) {
						throw new IllegalArgumentException("Funcionário não existe");
					}
					this.clienteModel.deletar(id_cliente);
				} else if (tipo.equals("CA")) {
					int id_cargo = this.cargoEscolhido("excluir");
					if (this.cargoModel.encontrarCargoPorId(id_cargo) == null) {
						throw new IllegalArgumentException("Cargo não existe");
					}
					this.cargoModel.deletar(id_cargo);
				} else if (tipo.equals("S")) {
					int id_servico = this.servicoEscolhido("excluir");
					if (this.servicoModel.encontrarServicoPorId(id_servico) == null) {
						throw new IllegalArgumentException("Serviço não existe");
					}
					this.servicoModel.deletar(id_servico);
				}
				break;
			}
			default:
				throw new IllegalArgumentException("Digite uma opção válida");
			}
			this.home(0);
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
			this.menuEditarExcluir(tipo);
		}
	}

}
