package appconsole;

import java.util.List;

import com.db4o.ObjectContainer;
import com.db4o.query.Query;

import models.Cliente;
import models.Pesagem;
import models.TipoComida;

public class Listar {
	protected ObjectContainer manager;

	public Listar() {
		try {
			manager = Util.conectarBanco();
			
			System.out.println("\n##### Listagem de clientes #####");
			Query q = manager.query();
			q.constrain(Cliente.class);
			List<Cliente> clientesRetornados = q.execute();
			for (Cliente c: clientesRetornados)
				System.out.println(c);

			System.out.println("\n##### Listagem de tipos de comida #####");
			q = manager.query();
			q.constrain(TipoComida.class);  				
			List<TipoComida> tiposComidaRetornados = q.execute();
			for (TipoComida tipo: tiposComidaRetornados)
				System.out.println(tipo);
			
			System.out.println("\n##### Listagem de pesagens #####");
			q = manager.query();
			q.constrain(Pesagem.class);  				
			List<Pesagem> pesagensRetornadas = q.execute();
			for (Pesagem p: pesagensRetornadas)
				System.out.println(p);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		Util.desconectar();
		System.out.println("\nFim do programa!");
	}

	public static void main(String[] args) {
		new Listar();
	}
}
