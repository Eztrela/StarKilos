package appconsole;

//import com.db4o.ObjectContainer;

/**********************************
 * IFPB - SI
 * POB - Persistencia de Objetos
 * Alunos Pablo Estrela e Yago César
 **********************************/

import models.Cliente;
import models.Pesagem;
import models.TipoComida;
import regras_negocio.Fachada;

public class Cadastrar {

	public Cadastrar() {

		try {

			System.out.println("Cadastrando...");
			
			Fachada.inicializar();

			// Criação e inserção de clientes no banco
			Cliente clienteUm = Fachada.cadastrarCliente();
			Cliente clienteDois = Fachada.cadastrarCliente();
			Cliente clienteTres = Fachada.cadastrarCliente();

			// Criação e inserção de tipos de comida no banco

			TipoComida cafe = Fachada.cadastrarTipoComida("Cafe", 11.0);
			TipoComida almoco = Fachada.cadastrarTipoComida("Almoco", 13.0);
			TipoComida janta = Fachada.cadastrarTipoComida("Janta", 15.0);

			// Criação e inserção de pesagens no banco
			Fachada.cadastrarPesagem(0.500, almoco.getNome(), clienteUm.getId());
			Fachada.cadastrarPesagem(0.450, janta.getNome(), clienteDois.getId());
			Fachada.cadastrarPesagem(0.150, cafe.getNome(), clienteTres.getId());
			
			Fachada.finalizar();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		System.out.println("Fim do programa!");
	}

	public static void main(String[] args) {
		new Cadastrar();
	}
}