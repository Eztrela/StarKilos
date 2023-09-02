package appconsole;

import java.util.List;

import com.db4o.ObjectContainer;
import com.db4o.query.Query;

import models.Cliente;
import models.Pesagem;

public class Remover {
	protected ObjectContainer manager;
	
	/*
	 * Remocao 1 -> remover duas pesagens de um cliente
	 * Remocao 2 -> remover um cliente (suas pesagens consequentemente)
	 */
	
	public Remover() {
		manager = Util.conectarBanco();
		System.out.println("Excluindo...");
		
		// Localizar o cliente de id 2
		Query q = manager.query();
		q.constrain(Cliente.class);
		q.descend("id").constrain(2);
		
		List<Cliente> resultado = q.execute();
		
		if (resultado.size() > 0) {
			Cliente clienteRetornado = resultado.get(0);
			List<Pesagem> listaDePesagens = clienteRetornado.getListaDePesagem();
			int tamanhoDaLista = listaDePesagens.size(); 
			
			// Remover as duas últimas pesagens do cliente
			Pesagem ultimaPesagem = clienteRetornado.getListaDePesagem().get(tamanhoDaLista-1);
			Pesagem penultimaPesagem =clienteRetornado.getListaDePesagem().get(tamanhoDaLista-2);
			
			clienteRetornado.removerPesagem(ultimaPesagem);
			clienteRetornado.removerPesagem(penultimaPesagem);
			manager.store(clienteRetornado);
			
			manager.delete(ultimaPesagem);
			manager.delete(penultimaPesagem);
			manager.commit();
		}
		
		// Remover o cliente de id 1
		// TODO - fazer
		
	}
}
