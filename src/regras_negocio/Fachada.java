package regras_negocio;

/**********************************
 * IFPB - Curso Superior de Tec. em Sist. para Internet
 * POB - Persistencia de Objetos
 * Prof. Fausto Ayres
 *
 */

import java.time.LocalDateTime;
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
	private Fachada() {
	}

	private static DAOPesagem daoPesagem = new DAOPesagem();
	private static DAOCliente daoCliente = new DAOCliente();
	private static DAOTipoComida daoTipoComida = new DAOTipoComida();
	private static DAOUsuario daoUsuario = new DAOUsuario();
	public static Usuario logado; // contem o objeto Usuario logado em TelaLogin.java

	public static void inicializar() {
		DAO.open();
	}

	public static void finalizar() {
		DAO.close();
	}

	public static Pesagem cadastrarPesagem(double peso, String nomeTipoComida, int idCliente) throws Exception {
		DAO.begin();
		TipoComida tipoComida = daoTipoComida.read(nomeTipoComida);
		
		if (tipoComida == null)
			throw new Exception("Não existe tipo de comida cujo nome é '" + nomeTipoComida + "'");
		
		Cliente cliente = daoCliente.read(idCliente);
		if (cliente == null)
			throw new Exception("Não existe cliente cujo ID é " + idCliente + ".");
		
		if (peso <= 0.0)
			throw new Exception("Uma pesagem não pode ter peso menor ou igual a 0.");
		
		Pesagem pesagem = new Pesagem(peso, tipoComida, cliente, LocalDateTime.now().toString());

		daoPesagem.create(pesagem);
		DAO.commit();
		
		return pesagem;
	}
	
	public static List<Pesagem> listarPesagens() {
		DAO.begin();
		List<Pesagem> resultados = daoPesagem.readAll();
		DAO.commit();
		
		return resultados;
	}
	
	public static Pesagem localizarPesagem(int id) {
		return daoPesagem.read(id);
	}
	
	public static void excluirPesagem(int id) throws Exception {
		DAO.begin();
		Pesagem pesagem = daoPesagem.read(id);
		
		if (pesagem == null)
			throw new Exception("A pesagem de ID " + id + " não foi localizada.");
		
		Cliente cliente = pesagem.getCliente();
		cliente.removerPesagem(pesagem);
		
		daoCliente.update(cliente);
		daoPesagem.delete(pesagem);
		DAO.commit();
		
	}

	public static TipoComida cadastrarTipoComida(String nome, double preco) throws Exception {
		DAO.begin();
				
		nome = nome.trim();
		if (nome.isBlank())
			throw new Exception("O nome de um tipo de comida não pode ser vazio.");

		if (daoTipoComida.read(nome) != null) 
			throw new Exception("O tipo de comida de nome '" + nome + "' ja existe.");
		
		if (preco <= 0.0)
			throw new Exception("O preço deve ser maior do que 0.");

		TipoComida tipoComida = new TipoComida(nome, preco);
		daoTipoComida.create(tipoComida);

		DAO.commit();
		return tipoComida;
	}

	public static Cliente cadastrarCliente() {
		DAO.begin();
		Cliente cliente = new Cliente();
		daoCliente.create(cliente);
		DAO.commit();
		return cliente;
	}
	
	public static List<TipoComida> listarTiposComida() {
		DAO.begin();
		List<TipoComida> resultados = daoTipoComida.readAll();
		DAO.commit();
		
		return resultados;
	}
	
	public static TipoComida localizarTipoComida(String nome) {
		return daoTipoComida.read(nome);
	}

	public static List<Cliente> listarClientes() {
		DAO.begin();
		List<Cliente> clientes = daoCliente.readAll();
		DAO.commit();

		return clientes;
	}

	public static void removerCliente(int id) throws Exception {
		DAO.begin();
		Cliente cliente = daoCliente.read(id);
		if (cliente == null) {
			throw new Exception("O cliente de ID " + id + " nao foi localizado");
		}
		List<Pesagem> pesagens = cliente.getListaDePesagem();
		if (pesagens.size() > 0) {
			for (Pesagem pesagem : pesagens) {
				daoPesagem.delete(pesagem);
				pesagens.remove(pesagem);
			}
		}
		daoCliente.delete(cliente);
		DAO.commit();
	}

	public static Cliente localizarCliente(int id) {
		return daoCliente.read(id);
	}

	public static void updateTipoComida(String nomeAtual, String novoNome) throws Exception {
		DAO.begin();
		TipoComida comida = daoTipoComida.read(nomeAtual);
		if (comida == null) {
			throw new Exception("O Tipo de Comida de nome " + nomeAtual + " nao foi localizado");
		}
		comida.setNome(novoNome);
		daoTipoComida.update(comida);
		DAO.commit();
	}

	public static void updateTipoComida(String nome, double novoPreco) throws Exception {
		DAO.begin();
		TipoComida comida = daoTipoComida.read(nome);
		if (comida == null) {
			throw new Exception("O Tipo de Comida de nome " + nome + " nao foi localizado");
		}
		comida.setPreco(novoPreco);
		daoTipoComida.update(comida);
		DAO.commit();
	}

	public static void removerTipoComida(String nome) throws Exception {
		DAO.begin();
		TipoComida comida = daoTipoComida.read(nome);
		if (comida == null) {
			throw new Exception("O Tipo de Comida de nome " + nome + " nao foi localizado");
		}
		List<Pesagem> pesagens = daoPesagem.readAll();
		if (pesagens.size() > 0) {
			for (Pesagem pesagem : pesagens) {
				if (pesagem.getTipoDaComida().equals(comida)) {
					List<Pesagem> listaPesagensCliente = pesagem.getCliente().getListaDePesagem();
					listaPesagensCliente.remove(pesagem);
					daoPesagem.delete(pesagem);
					daoCliente.update(pesagem.getCliente());
				}
			}
		}
		daoTipoComida.delete(comida);
		DAO.commit();
	}
	
	public static List<Usuario>  listarUsuarios(){
		DAO.begin();
		List<Usuario> resultados =  daoUsuario.readAll();
		DAO.commit();
		return resultados;
	}
	
	public static Usuario cadastrarUsuario(String nome, String senha) throws Exception{
		DAO.begin();
		Usuario usuario = daoUsuario.read(nome);
		if (usuario != null)
			throw new Exception("Usuario já cadastrado:" + nome);
		usuario = new Usuario(nome, senha);

		daoUsuario.create(usuario);
		DAO.commit();
		return usuario;
	}
	public static Usuario localizarUsuario(String nome, String senha) {
		Usuario usuario = daoUsuario.read(nome);
		if (usuario == null)
			return null;
		if (!usuario.getSenha().equals(senha))
			return null;
		return usuario;
	}
}