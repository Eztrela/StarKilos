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
			Query queryCliente1 = manager.query();
			queryCliente1.constrain(Cliente.class);
			queryCliente1.descend("id").constrain(1);
			List<Cliente> clientes = queryCliente1.execute();
			// consultar Comidas
			Query queryComidas = manager.query();
			queryComidas.constrain(TipoComida.class);
			List<TipoComida> comidas = queryComidas.execute();

			if (clientes.size() > 0 && comidas.size() > 0) {
				Cliente cliente1 = clientes.get(0);
				TipoComida cafe = comidas.get(0);
				TipoComida almoco = comidas.get(1);

				Pesagem pesagem1 = new Pesagem(Util.gerarIdPesagem(), 0.300, almoco, cliente1, "03/09/2023");
				cliente1.adicionarPesagem(pesagem1);
				manager.store(cliente1);
				manager.commit();

				Pesagem pesagem2 = new Pesagem(Util.gerarIdPesagem(), 0.900, cafe, cliente1, "03/09/2023");
				cliente1.adicionarPesagem(pesagem2);
				manager.store(cliente1);
				manager.commit();
			}

			// Alterando nome do TipoComida Almoço para "Almoço Fina de Semana"
			Query queryAlmoco = manager.query();
			queryAlmoco.constrain(TipoComida.class);
			queryAlmoco.descend("nome").constrain("Almoco");
			List<TipoComida> almocos = queryAlmoco.execute();
			if (almocos.size() > 0) {
				TipoComida almoco = almocos.get(0);
				almoco.setNome("Almoco Final de Semana");
				manager.store(almoco);
				manager.commit();
			}

			// Alterando preço do TipoComida Janta para 25.0
			Query queryJanta = manager.query();
			queryJanta.constrain(TipoComida.class);
			queryJanta.descend("nome").constrain("Janta");
			List<TipoComida> jantas = queryJanta.execute();
			if (jantas.size() > 0) {
				TipoComida janta = jantas.get(0);
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
