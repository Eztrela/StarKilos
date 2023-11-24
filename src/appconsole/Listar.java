package appconsole;

//import com.db4o.ObjectContainer;

import models.Cliente;
import models.Pesagem;
import models.TipoComida;
import models.Usuario;
import regras_negocio.Fachada;

public class Listar {
//	protected ObjectContainer manager;

	public Listar() {
		try {
			Fachada.inicializar();
			System.out.println("\n---listagem de clientes:");
			for(Cliente c: Fachada.listarClientes())
				System.out.println(c);

			System.out.println("\n---listagem de pesagens:");
			for(Pesagem p: Fachada.listarPesagens())
				System.out.println(p);
			
			System.out.println("\n---listagem de tipos de comida:");
			for(TipoComida tipo: Fachada.listarTiposComida())
				System.out.println(tipo);

			System.out.println("\n---listagem de usuarios:");
			for(Usuario u: Fachada.listarUsuarios())
				System.out.println(u);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

//		Util.desconectar();
		System.out.println("\nFim do programa!");
	}

	public static void main(String[] args) {
		new Listar();
	}
}
