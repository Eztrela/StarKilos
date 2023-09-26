package regras_negocio;

import java.time.LocalDateTime;

import daodb4o.DAO;
import daodb4o.DAOPesagem;
import daodb4o.DAOTipoComida;
import models.Cliente;
import models.Pesagem;
import models.TipoComida;

public class Fachada {
	private Fachada() {}
	
	private static DAOPesagem daoPesagem = new DAOPesagem();
	private static DAOTipoComida daoTipoComida = new DAOTipoComida();
	private static DAOCliente daoCliente = new DAOCliente();
	
	public static void inicializar() {
		DAO.open();
	}
	
	public static void finalizar(){
		DAO.close();
	}
	
	public Pesagem cadastrarPesagem(double peso, int idCliente, String nomeTipoComida) {
		DAO.begin();
		Cliente cliente = daoCliente.read(idCliente);
		if (cliente == null)
			throw new Exception("Nao existe um cliente de ID " + idCliente);
		TipoComida tipoDaComida = daoTipoComida.read(nomeTipoComida);
		if (tipoDaComida == null)
			throw new Exception("Nao existe um tipo de comida de nome " + nomeTipoComida);  
		String dataAtual = LocalDateTime.now().toString()
				.substring(0, 10);
		
		Pesagem pesagem = new Pesagem(peso, tipoDaComida, cliente, dataAtual);
		cliente.adicionarPesagem(pesagem);
		
		daoPesagem.create(pesagem);
		daoCliente.update(cliente);
		DAO.commit();
		return pesagem;
	}
	
	public TipoComida cadastrarTipoComida(String nome, double preco) {
		DAO.begin();
		if (daoTipoComida.read(nome) != null) 
			throw new Exception("O tipo de comida de nome '" + nome + "' ja existe");
		
		TipoComida tipoComida = new TipoComida(nome, preco);
		daoTipoComida.create(tipoComida);
		
		DAO.commit();
		return tipoComida;
		
	}
}
