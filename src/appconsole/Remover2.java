package appconsole;

import com.db4o.ObjectContainer;

import models.Cliente;
import regras_negocio.Fachada;

public class Remover2 {
	protected ObjectContainer manager;
	
	public Remover2() {
		try {
			Fachada.inicializar();
			System.out.println("Excluindo");
			
			// Remocao 2 -> remover o cliente de id 1
			
			Cliente cliente = Fachada.localizarCliente(1);
			if (cliente != null) {
				Fachada.removerCliente(1);
			}
			
		} catch (Exception exp) {
			System.out.println(exp.getMessage());
		}
		
		Fachada.finalizar();
		System.out.println("\nFim do programa!");
	}
	
	public static void main(String[] args) {
		new Remover2();
	}
}
