package model;

public class TipoPesagem {
	private String nome;
	private double preco;
	
	public TipoPesagem(String nome, double preco) {
		this.nome = nome;
		this.preco = preco;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public double getPreco() {
		return preco;
	}

	public void setPreco(double preco) {
		this.preco = preco;
	}

	@Override
	public String toString() {
		return "TipoPesagem [nome=" + nome + ", preco=" + preco + "]";
	}
	
	
}
