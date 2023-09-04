package appconsole;

import java.util.List;

import javax.management.Query;

import models.Cliente;

public class Consultas {
    protected ObjectContainer manager;

    public Consultas{
        try {
            manager = Util.conectarBanco();
            Query queryClientes = manager.query();
            queryClientes.constrain(Cliente.class);
            queryClientes.constrain( new Filtro1() );
            List<Pessoa> clientes = queryClientes.execute();
            for(Cliente c: clientes){
                System.out.println(c);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Util.desconectar();
		System.out.println("\nfim do programa !");
    }
    public static void main(String[] args) {
		new Alterar();
	}
    
}

class Filtro1 implements Evaluation {
    public void evaluate(Candidate candidate) {
        Cliente cliente = (Cliente) candidate.getObject();
        if(cliente.getListaDePesagem().size() > 2){
            candidate.include(true);
        }
        else{
            candidate.include(false);
        }
        
    }
}