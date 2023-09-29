/**********************************
 * IFPB - SI
 * POB - Persistencia de Objetos
 * Prof. Fausto Ayres
 **********************************/

package daodb4o;

import java.util.List;

import com.db4o.query.Query;

import models.TipoComida;

public class DAOTipoComida extends DAO<TipoComida> {
	
	public TipoComida read (Object chave){
		String nome = (String) chave;	
		Query queryTiposComida = manager.query();
		queryTiposComida.constrain(TipoComida.class);
		queryTiposComida.descend("nome").constrain(nome);
		List<TipoComida> tiposComida = queryTiposComida.execute();
		if (tiposComida.size() > 0)
			return tiposComida.get(0);
		else
			return null;
	}
}
