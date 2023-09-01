package models;

public class Pesagem {
	private int id;
	private double peso;
	private TipoComida tipoDaComida;
	private Cliente cliente;
	private String data;
	
	public Pesagem() {}
	
	public Pesagem(int id, double peso, TipoComida tipoDaComida, Cliente cliente, String data) {
		super();
		this.id = id;
		this.peso = peso;
		this.tipoDaComida = tipoDaComida;
		this.cliente = cliente;
		this.data = data;
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
	
	public String getData() {
		return data;
	}

	@Override
	public String toString() {
		return "Pesagem [id=" + id +
				", peso=" + peso +
				", tipoDaComida=" + tipoDaComida +
				", cliente=" + cliente.getId() +
				", data=" + data + "]";
	}
}
