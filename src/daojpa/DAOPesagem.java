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
		TypedQuery<Pesagem> q = manager.createQuery(
				"SELECT p FROM Pesagem p LEFT JOIN FETCH p.tipoDaComida  JOIN FETCH p.cliente ORDER BY p.id", Pesagem.class);
		return q.getResultList();
	}

	// --------------------------------------------
	// consultas
	// --------------------------------------------

//	public List<Aluguel> alugueisModelo(String modelo) {
//		// alugueis contendo carro de modelo 'palio'
//		TypedQuery<Aluguel> q = manager.createQuery("select a from Aluguel a where a.carro.modelo = :x", Aluguel.class);
//		q.setParameter("x", modelo);
//
//		return q.getResultList();
//	}
//
//	public List<Aluguel> alugueisFinalizados() {
//		TypedQuery<Aluguel> q = manager.createQuery("select a from Aluguel a where a.finalizado = true", Aluguel.class);
//
//		return q.getResultList();
//	}
}
