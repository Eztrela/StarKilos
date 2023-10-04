package appconsole;

import java.util.List;

import com.db4o.ObjectContainer;
import com.db4o.query.Query;

import models.Cliente;
import models.Pesagem;
import models.TipoComida;
import regras_negocio.Fachada;

public class Alterar {
	protected ObjectContainer manager;

	public Alterar() {
		try {
			Fachada.inicializar();
			System.out.println("Cliente Realizando Pesagens");
			
			// Adicionando duas pesagens ao cliente de ID 1 
			Cliente cliente1 = Fachada.localizarCliente(1);
			List<TipoComida> comidas = Fachada.listarTiposComida();

			if (cliente1 != null && comidas.size() > 0) {
				TipoComida cafe = comidas.get(0);
				TipoComida almoco = comidas.get(1);

				Pesagem pesagem1 = Fachada.cadastrarPesagem(0.300, almoco.getNome(), cliente1.getId());
				Pesagem pesagem2 = Fachada.cadastrarPesagem(0.900, cafe.getNome(), cliente1.getId());
			}

			// Alterando nome do TipoComida Almoco para "Almoco Final de Semana"

//			Query queryAlmoco = manager.query();
//			queryAlmoco.constrain(TipoComida.class);
//			queryAlmoco.descend("nome").constrain("Almoco");
//			List<TipoComida> almocos = queryAlmoco.execute();
//			if (almocos.size() > 0) {
//				TipoComida almoco = almocos.get(0);
//				almoco.setNome("Almoco Final de Semana");
//				manager.store(almoco);
//				manager.commit();
//			}

			// Alterando preço do TipoComida Janta para 25.0

//			Query queryJanta = manager.query();
//			queryJanta.constrain(TipoComida.class);
//			queryJanta.descend("nome").constrain("Janta");
//			List<TipoComida> jantas = queryJanta.execute();
//			if (jantas.size() > 0) {
//				TipoComida janta = jantas.get(0);
//				janta.setPreco(25.0);
//				manager.store(janta);
//				manager.commit();
//			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		Fachada.finalizar();
		System.out.println("\nfim do programa !");
	}

	public static void main(String[] args) {
		new Alterar();
	}
}
