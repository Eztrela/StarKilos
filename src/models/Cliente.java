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
		String textoExibido = "Cliente [id=" + id  +",Pesagens" + "[";
		for (Pesagem p: listaDePesagem) {
			textoExibido += "id=" + Integer.toString(p.getId()) +
							",peso=" + Double.toString(p.getPeso()) + 
							",tipoDaComida=" + p.getTipoDaComida() +
							",cliente=" + p.getCliente().getId() +
							",data=" + p.getData();
		}
		textoExibido += "]";
		
		return textoExibido;
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
	
	public void adicionarPesagem(Pesagem pesagem) {
		this.listaDePesagem.add(pesagem);
	}
	
	public Pesagem localizar(int idPesagem) {
		for (Pesagem p: this.getListaDePesagem())
			if (p.getId() == idPesagem)
				return p;
		return null;
	}
	
	public boolean removerPesagem(Pesagem p) {
		return this.listaDePesagem.remove(p);
	}

}
