package views;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import model.Cargo;
import model.Cliente;
import model.Funcionario;
import model.Servico;

public class FuncionarioView extends Menus{
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
	public void home(int id_funcionario) {
		Funcionario funcionarios = new Funcionario();
		ArrayList<Funcionario> lista = funcionarios.listarFuncionarios();
		boolean gerente = false;
		for(Funcionario fun : lista) {
			if(fun.getId() == id_funcionario && fun.getEhGerente() == true) {
				gerente = true;
				break;
			}
		}
		String menu="";
		menu+="Home Funcionário";
		menu+="\n 1-Meus atendimentos";
		menu+="\n 2-Agendar atendimentos";
		menu+="\n 3-Cadastrar Cliente";
		menu+="\n 4-Cadastrar Funcionário";
		menu+="\n 5-Cadastrar Serviço";
		menu+="\n 6-Listar Clientes";
		menu+="\n 7-Listar Funcionários";
		menu+="\n 8-Listar Serviços";
		menu+="\n 9-Sair";
		if(gerente) {
			menu+="\n 10-Relatórios";
		}
		
	}
	
	public int funEscolhido(String acao) {
		String menu = "Digite o ID que deseja " + acao;
		menu = String.format("\n %-5d %-20s %-12s %-15s %-10s%n", "ID", "Nome", "Cargo", "Celular", "Usuário");

		for(Funcionario fun : this.funcionarioModel.listarFuncionarios()) {
			menu += String.format("\n %-5d %-20s %-12s %-15s %-10s%n", fun.getId(), fun.getNome(), fun.getEhGerente() ? "Gerente" : "", fun.getCelular(), fun.getUsuario());
		}
		
		System.out.println(menu);
		Scanner scan = new Scanner(System.in);
		return scan.nextInt();
	}
	
	public int clienteEscolhido(String acao) throws SQLException {
		String menu = "Digite o ID que deseja " + acao;
		menu = String.format("\n %-5d %-20s %-12s", "ID", "Nome", "Usuário");

		for(Cliente cli : this.clienteModel.listarClientes()) {
			menu += String.format("\n %-5d %-20s %-12s", cli.getId(), cli.getNome(), cli.getUsuario());
		}
		
		System.out.println(menu);
		Scanner scan = new Scanner(System.in);
		return scan.nextInt();
	}
	
	public int servicoEscolhido(String acao) {
		String menu = "Digite o ID que deseja " + acao;
		menu = String.format("\n %-5d %-20s", "ID", "Nome");

		for(Servico servico : this.servicoModel.listarServicos()) {
			menu += String.format("\n %-5d %-20s %-12s", servico.getId(), servico.getNome());
		}
		
		System.out.println(menu);
		Scanner scan = new Scanner(System.in);
		return scan.nextInt();
	}
	
	public int cargoEscolhido(String acao) {
		String menu = "Digite o ID que deseja " + acao;
		menu = String.format("\n %-5d %-20s", "ID", "Nome");

		for(Cargo servico : this.cargoModel.listarServicos()) {
			menu += String.format("\n %-5d %-20s %-12s", servico.getId(), servico.getNome());
		}
		
		System.out.println(menu);
		Scanner scan = new Scanner(System.in);
		return scan.nextInt();
	}
	
 	 public void menuEditarExcluir(int id, String tipo) throws Exception {
	        try {
	        	String opt = super.menuEditarExcluir();
		        switch (opt) {
				case "1": {
					if(tipo == "F") {
						int id_fun = this.funEscolhido("editar");
						if(this.funcionarioModel.getFuncionarioPorId(id_fun) == null) {
							throw new IllegalArgumentException("Funcionário não existe");
						}
						this.funcionarioModel.editar(id_fun);
					}
					else if(tipo == "C") {
						int id_cliente = this.clienteEscolhido("editar");
						if(this.clienteModel.getClientePorId(id_cliente) == null) {
							throw new IllegalArgumentException("Cliente não existe");
						}
						this.clienteModel.editar(id_cliente);
					}
					else if(tipo == "CA") {
						int id_cargo = this.cargoEscolhido("editar");
						if(this.cargoModel.encontrarCargoPorId(id_cargo) == null) {
							throw new IllegalArgumentException("Cargo não existe");
						}
						this.cargoModel.editar(id_cargo);
					}
					else if(tipo == "S") {
						int id_servico = this.servicoEscolhido("editar");
						if(this.servicoModel.encontrarServicoPorId(id_servico) == null) {
							throw new IllegalArgumentException("Serviço não existe");
						}
						this.servicoModel.editar(id_servico);
					}
					break;
				}
				case "2": {
					if(tipo == "F") {
						int id_fun = this.funEscolhido("excluir");
						if(this.funcionarioModel.getFuncionarioPorId(id_fun) == null) {
							throw new IllegalArgumentException("Funcionário não existe");
						}
						this.funcionarioModel.deletar(id_fun);
					}
					else if(tipo == "C") {
						int id_cliente = this.clienteEscolhido("excluir");
						if(this.clienteModel.getClientePorId(id_cliente) == null) {
							throw new IllegalArgumentException("Funcionário não existe");
						}
						this.clienteModel.deletar(id_cliente);
					}
					else if(tipo == "CA") {
						int id_cargo = this.cargoEscolhido("excluir");
						if(this.cargoModel.encontrarCargoPorId(id_cargo) == null) {
							throw new IllegalArgumentException("Cargo não existe");
						}
						this.cargoModel.deletar(id_cargo);
					}
					else if(tipo == "S") {
						int id_servico = this.servicoEscolhido("ecluir");
						if(this.servicoModel.encontrarServicoPorId(id_servico) == null) {
							throw new IllegalArgumentException("Serviço não existe");
						}
						this.servicoModel.deletar(id_servico);
					}
				}
				default:
					throw new IllegalArgumentException("Digite uma opção válida");
				}
			} catch (IllegalArgumentException e) {
				System.out.println(e);
			}
	 }
 	 
 	 public String cadastro() {
 		 String menu = "";
 		 Scanner scan = new Scanner(System.in);
 		 System.out.println("Cadastro de funcionário \n Nome: ");
 		 String nome = scan.next();
 		 System.out.println("Celular: ");
 		 String celular = scan.next();
 		 System.out.println("Gerente (S/N)");
 		 String gerente = scan.next();
 		 System.out.println("Usuário: ");
 		 String user = scan.next();
 		 boolean ehGerente = false;
 		 
 		 if(gerente.equals("S")) {
 			 ehGerente = true;
 		 }
 		 
 		 
 		 return null;
 	 }
 	 
 	 
 	 
 	
}
