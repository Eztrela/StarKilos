package appconsole;

import java.util.List;

import com.db4o.ObjectContainer;
import com.db4o.query.Query;

import models.Cliente;
import models.Pesagem;
import models.TipoComida;

public class Alterar {
	protected ObjectContainer manager;

	public Alterar() {
		try {
			manager = Util.conectarBanco();
			System.out.println("Cliente Realizando Pesagens");

			// consultar cliente 1
			Query querryCliente1 = manager.query();
			querryCliente1.constrain(Cliente.class);
			querryCliente1.descend("id").constrain(1);
			List<Cliente> clientes = querryCliente1.execute();
			// consultar Amoco
			Query queryComidas = manager.query();
			queryComidas.constrain(TipoComida.class);
			List<TipoComida> Comidas = queryComidas.execute();

			if (clientes.size() > 0 && Comidas.size() > 0) {
				Cliente cliente1 = clientes.get(0);
				TipoComida cafe = tiposComida.get(0);
				TipoComida Comidas = tiposComida.get(1);
				Pesagem pesagem1 = new Pesagem(Util.gerarIdPesagem(), 0.300, Comidas, cliente1);
				Pesagem pesagem2 = new Pesagem(Util.gerarIdPesagem(), 0.900, cafe, cliente1);
				cliente1.adicionarPesagem(pesagem1);
				manager.store(cliente1);
				manager.commit();
			}

			// Alterando nome do TipoComida Almoço para "Almoço Fina de Semana"
			Query queryAlmoco = manager.query();
			queryAlmoco.constrain(TipoComida.class);
			queryAlmoco.descend("nome").constrain("Almoco");
			List<TipoComida> Almocos = queryAlmoco.execute();
			if (Almocos.size() > 0) {
				TipoComida almoco = Almocos.get(0);
				almoco.setNome("Almoco Final de Semana");
				manager.store(almoco);
				manager.commit();
			}

			// Alterando preço do TipoComida Janta para 25.0
			Query queryJanta = manager.query();
			queryJanta.constrain(TipoComida.class);
			queryJanta.descend("nome").constrain("Janta");
			List<TipoComida> Jantas = queryJanta.execute();
			if (Jantas.size() > 0) {
				TipoComida janta = Jantas.get(0);
				janta.setPreco(25.0);
				manager.store(janta);
				manager.commit();
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		Util.desconectar();
		System.out.println("\nfim do programa !");
	}

	public static void main(String[] args) {
		new Alterar();
	}
}
