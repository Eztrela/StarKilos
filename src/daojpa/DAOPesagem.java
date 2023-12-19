package daojpa;

import java.util.List;

import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import models.Pesagem;

public class DAOPesagem extends DAO<Pesagem> {
	public Pesagem read(Object chave) {
		try {
			int id = (int) chave;
			TypedQuery<Pesagem> q = manager.createQuery("SELECT p FROM Pesagem p WHERE p.id = :n ", Pesagem.class);
			q.setParameter("n", id);

			return q.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public List<Pesagem> readAll() {
//		TypedQuery<Pesagem> q = manager.createQuery(
//				"SELECT p FROM Pesagem p LEFT JOIN FETCH p.tipoDaComida JOIN FETCH p.cliente ORDER BY p.id",
//				Pesagem.class);
		TypedQuery<Pesagem> q = manager.createQuery("SELECT p FROM Pesagem p JOIN FETCH p.tipoDaComida JOIN FETCH p.cliente ORDER BY p.id", Pesagem.class);
		return q.getResultList();
	}

	// --------------------------------------------
	// consultas
	// --------------------------------------------

	/**
	 * Obter todas as realizadas na data passada como argumento.
	 * 
	 * @param data uma data que servira de filtro para as pesagens.
	 * @return nenhuma, uma ou várias pesagens feitas naquela data.
	 */
	public List<Pesagem> readPesagensPorData(String data) {
		TypedQuery<Pesagem> query = manager.createQuery("SELECT p FROM Pesagem p JOIN FETCH p.cliente JOIN FETCH p.tipoDaComida WHERE p.data LIKE :data",
				Pesagem.class);
		query.setParameter("data", data);
		return query.getResultList();
	}

	/**
	 * Obter todas as pesagens cujo ID do cliente seja igual a um determinado ID.
	 * 
	 * @param id o ID do cliente passado como argumento.
	 * @return nenhuma, uma ou várias pesagens do cliente.
	 */
	public List<Pesagem> readPesagensPorCliente(Integer id) {
		TypedQuery<Pesagem> query = manager
				.createQuery("SELECT p FROM Pesagem p LEFT JOIN FETCH p.cliente JOIN FETCH p.tipoDaComida WHERE p.cliente.id=:id", Pesagem.class);
		query.setParameter("id", id);
		return query.getResultList();
	}
}
