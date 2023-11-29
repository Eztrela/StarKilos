package appconsole;

import java.util.List;

//import com.db4o.query.Query;
//
//import com.db4o.ObjectContainer;
//import com.db4o.query.Candidate;
//import com.db4o.query.Evaluation;

import models.Cliente;
import models.Pesagem;
import models.TipoComida;

public class Consultas {
//    protected ObjectContainer manager;

    public Consultas(){
//        try {
//            manager = Util.conectarBanco();
//            
//            System.out.println("Clientes com mais de 2 pesagens");
//            Query queryClientes = manager.query();
//            queryClientes.constrain(Cliente.class);
//            queryClientes.constrain( new Filtro1() );
//            List<Cliente> clientes = queryClientes.execute();
//            for(Cliente c: clientes){
//                System.out.println(c);
//            }
//            
//            System.out.println("\nLista de pesagens na data 03/09/2023");
//            Query queryPesagens = manager.query();
//            queryPesagens.constrain(Pesagem.class);
//            queryPesagens.descend("data").constrain("03/09/2023");
//            List<Pesagem> pesagensNaData = queryPesagens.execute();
//            for(Pesagem pesagem: pesagensNaData) {
//            	System.out.println(pesagem);
//            }
//            
//            System.out.println("\nQuais as pesagens do Cliente de id 2");
//            Query queryClienteId2 = manager.query();
//            queryClienteId2.constrain(Cliente.class);
//            queryClienteId2.descend("id").constrain(2);
//            List<Cliente> clienteId2 = queryClienteId2.execute();
//            List<Pesagem> pesagensClienteId2 = clienteId2.get(0).getListaDePesagem();
//            for(Pesagem pesagem: pesagensClienteId2) {
//            	System.out.println(pesagem);
//            }
//
//            
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
//        Util.desconectar();
//		System.out.println("\nfim do programa !");
    }
    public static void main(String[] args) {
		new Consultas();
	}
    
}

//class Filtro1 implements Evaluation {
//    public void evaluate(Candidate candidate) {
//        Cliente cliente = (Cliente) candidate.getObject();
//        if(cliente.getListaDePesagem().size() > 2){
//            candidate.include(true);
//        }
//        else{
//            candidate.include(false);
//        }
//        
//    }
//}