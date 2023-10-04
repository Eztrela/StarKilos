package appconsole;

import java.util.List;

import com.db4o.ObjectContainer;
import com.db4o.query.Query;

import models.Cliente;
import models.Pesagem;
import regras_negocio.Fachada;

public class Remover {
	protected ObjectContainer manager;
	
	/*
	 * Remocao 1 -> remover duas pesagens de um cliente
	 * Remocao 2 -> remover um cliente (suas pesagens consequentemente)
	 */
	
	public Remover() {
		Fachada.inicializar();
		System.out.println("Excluindo...");
		
		// Localizar o cliente de id 2
		Cliente cliente = Fachada.localizarCliente(1);
		
		try {
			if (cliente != null) {
//				List<Pesagem> listaDePesagens = cliente.getListaDePesagem();
//				int tamanhoDaLista = listaDePesagens.size(); 
				
				// Remover as duas últimas pesagens do cliente
//				Pesagem ultimaPesagem = cliente.getListaDePesagem().get(tamanhoDaLista-1);
//				Pesagem penultimaPesagem =cliente.getListaDePesagem().get(tamanhoDaLista-2);
				Fachada.excluirPesagem(cliente.getListaDePesagem()
						.get(cliente.getListaDePesagem().size()-1).getId());
				Fachada.excluirPesagem(cliente.getListaDePesagem()
						.get(cliente.getListaDePesagem().size()-1).getId());
				
				
//				cliente.removerPesagem(ultimaPesagem);
//				cliente.removerPesagem(penultimaPesagem);
//				manager.store(cliente);
//				
//				manager.delete(ultimaPesagem);
//				manager.delete(penultimaPesagem);
//				manager.commit();
			}
			
			// Remover o cliente de id 1
			// TODO - fazer
			
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		
		Fachada.finalizar();
		
	}
	
	public static void main(String[] args) {
		new Remover();
	}
}
