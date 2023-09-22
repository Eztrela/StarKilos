package models;

public class Pesagem {
	private int id;
	private double peso;
	private TipoComida tipoDaComida;
	private Cliente cliente;
	private String data;
	
	public Pesagem() {}
	
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
		return "[id=" + id +
				", peso=" + peso +
				", TipoComida=" + tipoDaComida +
				", cliente=" + cliente.getId() +
				", data=" + data + "]";
	}
}
