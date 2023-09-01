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
			Query queryAlmoco = manager.query();
			queryAlmoco.constrain(TipoComida.class);
			List<TipoComida> tiposComida = queryAlmoco.execute();

			if (clientes.size() > 0) {
				Cliente cliente1 = clientes.get(0);
				TipoComida cafe = tiposComida.get(0);
				TipoComida almoco = tiposComida.get(1);
				Pesagem pesagem1 = new Pesagem(Util.gerarIdPesagem(), 0.300,almoco,cliente1);
				Pesagem pesagem2 = new Pesagem(Util.gerarIdPesagem(), 0.900,cafe,cliente1);
				cliente1.adicionarPesagem(pesagem1);
			}
			
			
			//TODO alterar nome TipoComida e Preço
			// consultar Amoco
			Query queryAlmoco = manager.query();
			queryAlmoco.constrain(TipoComida.class);
			queryAlmoco.descend("nome").constrain("Almoco");
			List<TipoComida> almocos = queryAlmoco.execute();

			if (resultados1.size() > 0 && resultados2.size() > 0) {
				Carro carro = resultados1.get(0);
				Cliente cliente = resultados2.get(0);

				int id;
				Aluguel aluguel = new Aluguel("01/05/2023", "10/05/2023", 100.0);
				id = Util.gerarIdAluguel();
				aluguel.setId(id);
				aluguel.setCarro(carro);
				aluguel.setCliente(cliente);

				carro.adicionar(aluguel);
				cliente.adicionar(aluguel);
				carro.setAlugado(false);

				manager.store(aluguel);
				manager.commit();
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		Util.desconectar();
		System.out.println("\nfim do programa !");
	}

	public static void main(String[] args) {
		new Alterar1();
	}
}
