/**********************************
 * IFPB - SI
 * POB - Persistencia de Objetos
 * Prof. Fausto Ayres
 **********************************/

package daojpa;

import java.util.List;

import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import models.Cliente;

public class DAOCliente extends DAO<Cliente> {
	public Cliente read(Object chave) {
		try {
			int id = (int) chave;
			TypedQuery<Cliente> q = manager.createQuery("SELECT c FROM Cliente c WHERE c.id=:n", Cliente.class);
			q.setParameter("n", id);
			Cliente p = q.getSingleResult();
			return p;
		} catch (NoResultException e) {
			return null;
		}
	}

	public List<Cliente> readAll() {
		TypedQuery<Cliente> query = manager
				.createQuery("SELECT c FROM Cliente c LEFT JOIN FETCH c.listaDePesagem order by c.id", Cliente.class);
		return query.getResultList();
	}

	// --------------------------------------------
	// consultas
	// --------------------------------------------

	/**
	 * Obter todos os clientes com mais de N pesagens.
	 * 
	 * @param numeroDePesagens número de pesagens utilizado como filtro.
	 * @return nenhum, um ou vários clientes com mais de N pesagens.
	 */
	public List<Cliente> readClientesComMaisDeNPesagens(int numeroDePesagens) {
		TypedQuery<Cliente> query = manager
				.createQuery("SELECT c FROM Cliente c WHERE SIZE(c.listaDePesagem) > :numeroDePesagens", Cliente.class);
		query.setParameter("numeroDePesagens", numeroDePesagens);
		return query.getResultList();
	}
}
