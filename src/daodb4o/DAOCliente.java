/**********************************
 * IFPB - SI
 * POB - Persistencia de Objetos
 * Prof. Fausto Ayres
 **********************************/

package daodb4o;

import java.util.List;

import com.db4o.query.Candidate;
import com.db4o.query.Evaluation;
import com.db4o.query.Query;

import models.Cliente;

public class DAOCliente extends DAO<Cliente> {

	public Cliente read(Object chave) {
		int id = (int) chave; // casting para o tipo da chave
		Query q = manager.query();
		q.constrain(Cliente.class);
		q.descend("id").constrain(id);
		List<Cliente> resultados = q.execute();
		if (resultados.size() > 0)
			return resultados.get(0);
		else
			return null;
	}

	// metodo da classe DAO sobrescrito DAOCliente para
	// criar "id" sequencial
	public void create(Cliente obj) {
		int novoid = super.gerarId(); // gerar novo id da classe
		obj.setId(novoid); // atualizar id do objeto antes de grava-lo no banco
		manager.store(obj);
	}

	public List<Cliente> clienteNPesagens(int quantidade) {
		Query queryClientes = manager.query();
		queryClientes.constrain(Cliente.class);
		queryClientes.constrain(new Filtro1(quantidade));
		List<Cliente> clientes = queryClientes.execute();
		System.out.println(clientes);
		return clientes;
	}

	// --------------------------------------------
	// consultas
	// --------------------------------------------

	class Filtro1 implements Evaluation {
		private int quantidade;

		public Filtro1(int quantidade) {
			this.quantidade = quantidade;
		}

		public void evaluate(Candidate candidate) {
			Cliente cliente = (Cliente) candidate.getObject();
			if (cliente.getListaDePesagem().size() > quantidade) {
				candidate.include(true);
			} else {
				candidate.include(false);
			}

		}
	}
}
