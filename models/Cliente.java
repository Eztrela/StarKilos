package models;

import java.util.ArrayList;
import java.util.List;

public class Cliente {
	private int id;
	private List<Pesagem> listaDePesagem;
	
	public Cliente(int id) {
		this.id = id;
		this.listaDePesagem = new ArrayList<>();
	}

	@Override
	public String toString() {
		return "Cliente [id=" + id + "]";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<Pesagem> getListaDePesagem() {
		return listaDePesagem;
	}

	public void setListaDePesagem(List<Pesagem> listaDePesagem) {
		this.listaDePesagem = listaDePesagem;
	}

}
