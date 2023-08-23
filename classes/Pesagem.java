package model;

public class Pesagem {
	private int id;
	private double peso;
	private TipoComida tipoDaComida;
	private Cliente cliente;
	
	public Pesagem() {}
	
	public Pesagem(int id, double peso, TipoComida tipoDaComida, Cliente cliente) {
		super();
		this.id = id;
		this.peso = peso;
		this.tipoDaComida = tipoDaComida;
		this.cliente = cliente;
	}

	public int getId() {
		return id;
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

	@Override
	public String toString() {
		return "Pesagem [id=" + id + ", peso=" + peso + ", tipoDaComida=" + tipoDaComida + ", cliente=" + cliente + "]";
	}
}
