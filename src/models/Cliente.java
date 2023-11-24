package models;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Cliente {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@OneToMany(mappedBy = "cliente", cascade = {CascadeType.PERSIST,CascadeType.MERGE}, fetch = FetchType.LAZY)
	private List<Pesagem> listaDePesagem;
	
	public Cliente() {
		this.listaDePesagem = new ArrayList<>();
	}

	@Override
	public String toString() {
		return "[id=" + id  +", Pesagens=" + this.listaDePesagem + "]"; 
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
