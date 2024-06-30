package model;

import java.util.ArrayList;

public class Cargo implements BaseModel{
	private String nome;
	private int id;
	private int id_serviço;
	ArrayList<Cargo> cargos = new ArrayList<Cargo>(); 
	public Cargo(String nome, int id, int id_serviço) {
		this.nome=nome;
		this.id=id;
		this.id_serviço=id_serviço;
	}
	public Cargo() {
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getId_serviço() {
		return id_serviço;
	}
	public void setId_serviço(int id_serviço) {
		this.id_serviço = id_serviço;
	}
	@Override
	public void salvar() {
		cargos.add(this);
	}
	@Override
	public void deletar(int id) {
        cargos.removeIf(servico -> servico.getId() == id);
	}
	@Override
	public void editar(int id) {
        for (int i = 0; i < cargos.size(); i++) {
            Cargo cargo = cargos.get(i);
            if (cargo.getId() == id) {
                cargo.setNome(this.getNome());
                cargo.setId_serviço(this.getId_serviço());
                cargos.set(i, cargo);
                break;
            }
        }
    }

	public Cargo encontrarCargoPorId(int id) {
        for (Cargo cargo : cargos) {
            if (cargo.getId() == id) {
                return cargo;
            }
        }
        return null;
    }
	
	public ArrayList<Cargo> listarServicos() {
        return cargos;
    }


}
