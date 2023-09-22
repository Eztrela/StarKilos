package regras_negocio;
/**********************************
 * IFPB - Curso Superior de Tec. em Sist. para Internet
 * POB - Persistencia de Objetos
 * Prof. Fausto Ayres
 *
 */

import java.util.List;

import daodb4o.DAO;
import daodb4o.DAOCliente;
import daodb4o.DAOPesagem;
import daodb4o.DAOTipoComida;
import daodb4o.DAOUsuario;
import models.Cliente;
import models.Pesagem;
import models.TipoComida;
import models.Usuario;

public class Fachada {
	private Fachada() {}

	private static DAOPesagem daoPesagem = new DAOPesagem();  
	private static DAOCliente daoCliente = new DAOCliente(); 
	private static DAOTipoComida daoTipoComida = new DAOTipoComida(); 
	private static DAOUsuario daoUsuario = new DAOUsuario(); 
	public static Usuario logado;	//contem o objeto Usuario logado em TelaLogin.java

	public static void inicializar(){
		DAO.open();
	}
	public static void finalizar(){
		DAO.close();
	}


	public static Pesagem cadastrarPesagem(String placa, String modelo) throws Exception{
		DAO.begin();
		Pesagem Pesagem = daoPesagem.read(placa);
		if (Pesagem!=null)
			throw new Exception("Pesagem ja cadastrado:" + placa);
		Pesagem = new Pesagem(placa, modelo);

		daoPesagem.create(Pesagem);
		DAO.commit();
		return Pesagem;
	}

	public static Cliente realizarPesagem(String cpf, String placa, double diaria, String data1, String data2) throws Exception{
		DAO.begin();
		Pesagem car =  daoPesagem.read(placa);
		if(car==null) 
			throw new Exception ("Pesagem incorreto para Cliente "+placa);
		if(car.isAlugado()) 
			throw new Exception ("Pesagem ja esta alugado:"+placa);

		TipoComida cli = daoTipoComida.read(cpf);
		if(cli==null) 
			throw new Exception ("TipoComida incorreto para Cliente " + cpf);

		Cliente Cliente = new Cliente(data1,data2, diaria);
		Cliente.setPesagem(car);
		Cliente.setTipoComida(cli);
		car.adicionar(Cliente);
		car.setAlugado(true);
		cli.adicionar(Cliente);

		daoCliente.create(Cliente);
		daoPesagem.update(car);
		daoTipoComida.update(cli);
		DAO.commit();
		return Cliente;
	}

	public static void devolverPesagem(String placa) throws Exception{
		DAO.begin();
		Pesagem car =  daoPesagem.read(placa);
		if(car==null) 
			throw new Exception ("Pesagem incorreto para devolucao");

		if(car.getAlugueis().isEmpty()) 
			throw new Exception ("Pesagem nao pode ser devolvido - nao esta alugado");

		car.setAlugado(false);
		// obter o ultimo Cliente do Pesagem
		Cliente alug = car.getAlugueis().get(car.getAlugueis().size()-1);
		alug.setFinalizado(true);

		daoPesagem.update(car);
		DAO.commit();
	}

	public static void excluirPesagem(String placa) throws Exception{
		DAO.begin();
		Pesagem car =  daoPesagem.read(placa);
		if(car==null) 
			throw new Exception ("Pesagem incorreto para exclusao " + placa);

		if(! car.isAlugado()) 
			throw new Exception ("Pesagem alugado nao pode ser excluido " + placa);


		//alterar os TipoComidas dos alugueis do Pesagem
		for (Cliente a : car.getAlugueis()) {
			TipoComida cli = a.getTipoComida();
			cli.remover(a);
			//atualizar o TipoComida no banco
			daoTipoComida.update(cli);
			//apagar o Cliente
			daoCliente.delete(a);
		}

		//apagar Pesagem e seus alugueis em cascata
		daoPesagem.delete(car);
		DAO.commit();
	}

	public static TipoComida cadastrarTipoComida(String nome, String cpf) throws Exception{
		DAO.begin();
		TipoComida cli = daoTipoComida.read(cpf);
		if (cli!=null)
			throw new Exception("Pessoa ja cadastrado:" + cpf);
		cli = new TipoComida(nome, cpf);

		daoTipoComida.create(cli);
		DAO.commit();
		return cli;
	}
	public static void excluirTipoComida(String cpf) throws Exception{
		DAO.begin();
		TipoComida cli =  daoTipoComida.read(cpf);
		if(cli==null) 
			throw new Exception ("TipoComida incorreto para exclusao " + cpf);

		if(!cli.getAlugueis().isEmpty()) {
			List<Cliente> alugueis = cli.getAlugueis();
			Cliente ultimo = alugueis.get(alugueis.size()-1);
			if(ultimo !=null && !ultimo.isFinalizado()) 
				throw new Exception ("Nao pode excluir TipoComida com Pesagem alugado: " + cpf);
		}
		
		//alterar os Pesagems dos alugueis 
		for (Cliente a : cli.getAlugueis()) {
			Pesagem car = a.getPesagem();
			car.remover(a);
			daoPesagem.update(car);
			daoCliente.delete(a);
		}

		//apagar Pesagem e seus alugueis em cascata
		daoTipoComida.delete(cli);
		DAO.commit();
	}

	public static void excluirCliente(int id) throws Exception{
		DAO.begin();
		Cliente Cliente =  daoCliente.read(id);
		if(Cliente==null) 
			throw new Exception ("Cliente incorreto para exclusao " + id);

		if(! Cliente.isFinalizado()) 
			throw new Exception ("Cliente nao finalizado nao pode ser excluido " + id);

		//alterar os TipoComidas dos alugueis do Pesagem
		TipoComida cli = Cliente.getTipoComida();
		Pesagem car = Cliente.getPesagem();
		cli.remover(Cliente);
		car.remover(Cliente);

		daoTipoComida.update(cli);
		daoPesagem.update(car);
		daoCliente.delete(Cliente);
		DAO.commit();
	}

	public static List<TipoComida>  listarTipoComidas(){
		DAO.begin();
		List<TipoComida> resultados =  daoTipoComida.readAll();
		DAO.commit();
		return resultados;
	} 

	public static List<Pesagem>  listarPesagems(){
		DAO.begin();
		List<Pesagem> resultados =  daoPesagem.readAll();
		DAO.commit();
		return resultados;
	}

	public static List<Cliente> listarAlugueis(){
		DAO.begin();
		List<Cliente> resultados =  daoCliente.readAll();
		DAO.commit();
		return resultados;
	}

	public static List<Usuario>  listarUsuarios(){
		DAO.begin();
		List<Usuario> resultados =  daousuario.readAll();
		DAO.commit();
		return resultados;
	} 

	public static List<Cliente> alugueisModelo(String modelo){	
		DAO.begin();
		List<Cliente> resultados =  daoCliente.alugueisModelo(modelo);
		DAO.commit();
		return resultados;
	}

	public static List<Cliente> alugueisFinalizados(){	
		DAO.begin();
		List<Cliente> resultados =  daoCliente.alugueisFinalizados();
		DAO.commit();
		return resultados;
	}

	public static List<Pesagem>  PesagemsNAlugueis(int n){	
		DAO.begin();
		List<Pesagem> resultados =  daoPesagem.PesagemsNAlugueis(n);
		DAO.commit();
		return resultados;
	}

	public static Pesagem localizarPesagem(String placa){
		return daoPesagem.read(placa);
	}
	public static TipoComida localizarTipoComida(String cpf){
		return daoTipoComida.read(cpf);
	}

	
	//------------------Usuario------------------------------------
	public static Usuario cadastrarUsuario(String nome, String senha) throws Exception{
		DAO.begin();
		Usuario usu = daousuario.read(nome);
		if (usu!=null)
			throw new Exception("Usuario ja cadastrado:" + nome);
		usu = new Usuario(nome, senha);

		daousuario.create(usu);
		DAO.commit();
		return usu;
	}
	public static Usuario localizarUsuario(String nome, String senha) {
		Usuario usu = daousuario.read(nome);
		if (usu==null)
			return null;
		if (! usu.getSenha().equals(senha))
			return null;
		return usu;
	}
}
