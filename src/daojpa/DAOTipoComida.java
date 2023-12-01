/**********************************
 * IFPB - SI
 * POB - Persistencia de Objetos
 * Prof. Fausto Ayres
 **********************************/

package daojpa;

import java.util.List;

import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import models.TipoComida;

public class DAOTipoComida extends DAO<TipoComida>{

	public TipoComida read (Object chave){
		try{
			String nome = (String) chave;
			TypedQuery<TipoComida> q = manager.createQuery("select t from TipoComida t where t.nome=:nome",TipoComida.class);
			q.setParameter("nome", nome);
			TipoComida t =  q.getSingleResult();
			return t;
		}catch(NoResultException e){
			return null;
		}
	}

	public List<TipoComida> readAll(){
		TypedQuery<TipoComida> query = manager.createQuery("select t from TipoComida t order by t.nome",TipoComida.class);
		return  query.getResultList();
	}
	
//	public List<Carro> carrosNAlugueis(int n) {
//		// cliestes com 3 alugueis
//		TypedQuery<Carro> q = manager.createQuery("select c from Carro c LEFT JOIN FETCH c.alugueis where size(c.alugueis) = :x", Carro.class);
//		q.setParameter("x", n);
//		return q.getResultList();
//	}
}

