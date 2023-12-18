package models;

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

@Entity
public class Pesagem {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private double peso;

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
	private TipoComida tipoDaComida;

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
	private Cliente cliente;
	private String data;

	public Pesagem() {
	}

	public Pesagem(double peso, TipoComida tipoDaComida, Cliente cliente, String data) {
		super();
		this.peso = peso;
		this.tipoDaComida = tipoDaComida;
		this.cliente = cliente;
		this.data = data;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getPeso() {
		return peso;
	}

	public TipoComida getTipoDaComida() {
		return tipoDaComida;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public String getData() {
		return data;
	}

	@Override
	public String toString() {
		return "[id=" + id + ", peso=" + peso + ", TipoComida=" + tipoDaComida.getNome() + ", cliente="
				+ cliente.getId() + ", data=" + data + "]";
	}
}
