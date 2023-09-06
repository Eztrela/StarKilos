package appconsole;

import java.util.List;

import com.db4o.ObjectContainer;
import com.db4o.query.Query;

import models.Cliente;
import models.Pesagem;
import models.TipoComida;

public class Remover3 {
	protected ObjectContainer manager;
	
	public Remover3() {
		try {
			manager = Util.conectarBanco();
			System.out.println("Excluindo");
			
			// Remocao 3 -> deletar o tipoComida "Almoco Final de Semana"
			
			Query queryAlmoco = manager.query();
			queryAlmoco.constrain(TipoComida.class);
			queryAlmoco.descend("nome").constrain("Almoco Final de Semana");
			
			List<TipoComida> almocosRetornados = queryAlmoco.execute();
			
			TipoComida almoco = almocosRetornados.get(0);
			
			if (almocosRetornados.size() > 0) {
				Query queryPesagens = manager.query();
				queryPesagens.constrain(Pesagem.class);
				queryPesagens.descend("tipoDaComida").descend("nome").constrain(almoco.getNome());
				
				List<Pesagem> pesagensAlmoco = queryPesagens.execute();
				
				if (pesagensAlmoco.size() > 0) {
					for (Pesagem pesagem : pesagensAlmoco) {
						Cliente cliente = pesagem.getCliente();
						cliente.removerPesagem(pesagem);
						manager.delete(pesagem);
						manager.store(cliente);
					}
					manager.commit();
				}
				
				manager.delete(almoco);
				manager.commit();
			}
			
			
		} catch (Exception exp) {
			System.out.println(exp.getMessage());
		}
		
		Util.desconectar();
		System.out.println("\nFim do programa!");
	}
	
	public static void main(String[] args) {
		new Remover3();
	}
}
