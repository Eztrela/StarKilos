package appconsole;

import java.util.List;

import com.db4o.ObjectContainer;
import com.db4o.query.Query;

import models.Cliente;
import models.Pesagem;

public class Remover2 {
	protected ObjectContainer manager;
	
	public Remover2() {
		try {
			manager = Util.conectarBanco();
			System.out.println("Excluindo");
			
			// Remocao 2 -> remover o cliente de id 1

			// localizar o cliente de id 1
			Query q = manager.query();
			q.constrain(Cliente.class);
			q.descend("id").constrain(1);
			
			List<Cliente> resultado = q.execute();
			
			if (resultado.size() > 0) {
				Cliente clienteRetornado = resultado.get(0); 
				List<Pesagem> listaDePesagensDoCliente = clienteRetornado.getListaDePesagem();
				
				// removendo do banco as pesagens associadas aquele cliente
				for (Pesagem p : listaDePesagensDoCliente) {
					manager.delete(p);
				}
				manager.commit();
				
				// removendo o relacionamento entre o cliente e cada pesagem
				// associada a ele
				clienteRetornado.getListaDePesagem().clear();
				
				// remover o cliente do banco
				manager.delete(clienteRetornado);
				manager.commit();
			}
			
		} catch (Exception exp) {
			System.out.println(exp.getMessage());
		}
		
		Util.desconectar();
		System.out.println("\nFim do programa!");
	}
	
	public static void main(String[] args) {
		new Remover2();
	}
}
