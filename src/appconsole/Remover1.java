package appconsole;

import java.util.List;

import com.db4o.ObjectContainer;
import com.db4o.query.Query;

import models.Cliente;
import models.Pesagem;

public class Remover1 {
	protected ObjectContainer manager;
	
	public Remover1() {
		try {
			manager = Util.conectarBanco();
			System.out.println("Excluindo...");

			// Remocao 1 -> remover uma pesagem de um cliente
			
			// Localizar o cliente de id 2
			Query q = manager.query();
			q.constrain(Cliente.class);
			q.descend("id").constrain(2);
			
			List<Cliente> resultado = q.execute();
			
			if (resultado.size() > 0) {
				Cliente clienteRetornado = resultado.get(0);
				List<Pesagem> listaDePesagensDoCliente = clienteRetornado.getListaDePesagem();
				int tamanhoDaLista = listaDePesagensDoCliente.size();
				
				// Remover a última pesagem do cliente
				if (tamanhoDaLista >= 1) {
					Pesagem ultimaPesagem = listaDePesagensDoCliente.get(tamanhoDaLista-1);
					clienteRetornado.removerPesagem(ultimaPesagem);
					
					manager.store(clienteRetornado);
					manager.delete(ultimaPesagem);
					manager.commit();
				} else
					System.out.println("O cliente " + clienteRetornado.getId() + " não possui pesagens.");
				
			}
		} catch (Exception exp) {
			System.out.println(exp.getMessage());
		}
		
		Util.desconectar();
		System.out.println("\nFim do programa!");
	}
	
	public static void main(String[] args) {
		new Remover1();
	}
}
