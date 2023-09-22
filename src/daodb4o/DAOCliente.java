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

public class DAOCliente extends DAO<Cliente>{

	public Cliente read (Object chave){
		String id = (String) chave;	//casting para o tipo da chave
		Query q = manager.query();
		q.constrain(Cliente.class);
		q.descend("id").constrain(id);
		List<Cliente> resultados = q.execute();
		if (resultados.size()>0)
			return resultados.get(0);
		else
			return null;
	}

	//metodo da classe DAO sobrescrito DAOCliente para
	//criar "id" sequencial 
	public void create(Cliente obj){
		int novoid = super.gerarId();  	//gerar novo id da classe
		obj.setId(novoid);				//atualizar id do objeto antes de grava-lo no banco
		manager.store( obj );
	}

	public List<Cliente> clienteNPesagens(int n){
		System.out.println("Clientes com mais de 2 pesagens");
		Query queryClientes = manager.query();
		queryClientes.constrain(Cliente.class);
		queryClientes.constrain( new Filtro1(n) );
		List<Cliente> clientes = queryClientes.execute();
		return clientes;
	}

	
	//--------------------------------------------
	//  consultas
	//--------------------------------------------
	
	class Filtro1 implements Evaluation {
		private int n;
		public Filtro1(int n){
			this.n = n;
		}
		public void evaluate(Candidate candidate) {
			Cliente cliente = (Cliente) candidate.getObject();
			if(cliente.getListaDePesagem().size() > n){
				candidate.include(true);
			}
			else{
				candidate.include(false);
			}
			
		}
	}
}
