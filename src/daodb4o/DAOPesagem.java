package daodb4o;

import java.util.List;

import com.db4o.query.Query;

import models.Cliente;
import models.Pesagem;

public class DAOPesagem extends DAO<Pesagem> {
	
	public Pesagem read(Object chave) {
		int id = (int) chave;
		Query queryPesagem = manager.query();
		queryPesagem.constrain(Pesagem.class);
		queryPesagem.descend("id").constrain(id);
		List<Pesagem> pesagens = queryPesagem.execute();
		
		if (pesagens.size() > 0)
			return pesagens.get(0);
		else
			return null;
	}
	
	public void create(Pesagem obj){
		int novoid = super.gerarId();
		obj.setId(novoid);
		manager.store(obj);
	}
	
	public List<Pesagem> pesagensPorData(String data) {
		Query queryPesagens;
		queryPesagens = manager.query();
		queryPesagens.constrain(Pesagem.class);
		queryPesagens.descend("data").constrain(data);
		return queryPesagens.execute();
	}
	
	public List<Pesagem> pesagensPorCliente(int idDoCliente) {
		Query queryCliente = manager.query();
        queryCliente.constrain(Cliente.class);
        queryCliente.descend("id").constrain(idDoCliente);
        List<Cliente> clienteRetornado = queryCliente.execute();
        return clienteRetornado.get(0).getListaDePesagem();
	}
}
