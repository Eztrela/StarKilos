package appconsole;

import com.db4o.ObjectContainer;

/**********************************
 * IFPB - SI
 * POB - Persistencia de Objetos
 * Alunos Pablo Estrela e Yago César
 **********************************/

import models.Cliente;
import models.Pesagem;
import models.TipoComida;

public class Cadastrar {
	protected ObjectContainer manager;
	
	public Cadastrar() {
		
		try {
			manager = Util.conectarBanco();
			
			System.out.println("Cadastrando...");
			
			// Objetos 'Cliente'
			Cliente clienteUm = new Cliente(Util.gerarIdCliente());
			manager.store(clienteUm);
			manager.commit();
			
			Cliente clienteDois = new Cliente(Util.gerarIdCliente());
			manager.store(clienteDois);
			manager.commit();
			
			Cliente clienteTres = new Cliente(Util.gerarIdCliente());
			manager.store(clienteDois);
			manager.commit();
			
			// Objetos 'TipoComida'
			TipoComida cafe = new TipoComida("Café", 11.0);
			manager.store(cafe);
			manager.commit();
			
			TipoComida almoco = new TipoComida("Almoço", 13.0);
			manager.store(almoco);
			manager.commit();
			
			TipoComida janta = new TipoComida("Janta", 15.0);
			manager.store(janta);
			manager.commit();
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		Util.desconectar();
		System.out.println("Fim do programa!");
	}
	
	public static void main(String[] args) {
		new Cadastrar();
	}
}
