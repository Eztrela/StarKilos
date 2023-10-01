package appswing;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import models.Usuario;
import regras_negocio.Fachada;

public class TelaLogin {

	private JFrame frame;
	private JLabel lblUsurio;
	private JLabel lblSenha;
	private JTextField inputUsuario;
	private JTextField inputSenha;
	private JLabel lblMensagens;
	private JButton btnEntrar;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaLogin window = new TelaLogin();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public TelaLogin() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("Login");
		frame.setBounds(100, 100, 248, 215);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				Fachada.inicializar();
				if (Fachada.listarUsuarios().isEmpty()) {
					try {
						Fachada.cadastrarUsuario("fausto", "1234");
						lblMensagens.setText("usuario 'fausto' cadastrado");
					}
					catch(Exception ex) {
						lblMensagens.setText("nao conseguiu criar usuario");
					}
				}
			}
			@Override
			public void windowClosed(WindowEvent e) {
				Fachada.finalizar();
			}
		});

		lblUsurio = new JLabel("Usuário");
		lblUsurio.setBounds(72, 10, 46, 14);
		frame.getContentPane().add(lblUsurio);

		lblSenha = new JLabel("Senha");
		lblSenha.setBounds(72, 64, 46, 14);
		frame.getContentPane().add(lblSenha);

		inputUsuario = new JTextField();
		inputUsuario.setBounds(72, 34, 86, 20);
		frame.getContentPane().add(inputUsuario);
		inputUsuario.setColumns(10);

		inputSenha = new JTextField();
		inputSenha.setBounds(72, 88, 86, 20);
		frame.getContentPane().add(inputSenha);
		inputSenha.setColumns(10);

		lblMensagens = new JLabel("");
		lblMensagens.setBounds(10, 151, 214, 14);
		frame.getContentPane().add(lblMensagens);

		btnEntrar = new JButton("Entrar");
		btnEntrar.setBackground(new Color(54, 219, 36));
		btnEntrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String nome = inputUsuario.getText();
				String senha = inputSenha.getText();

				Usuario usu = Fachada.localizarUsuario(nome,senha);

				if(usu != null) {
					Fachada.logado = usu;
					TelaPrincipal tela = new TelaPrincipal();
					frame.dispose();
				}
				else
					lblMensagens.setText("usuario ou senha incorreto");
			}
		});
		btnEntrar.setBounds(69, 118, 89, 23);
		frame.getContentPane().add(btnEntrar);
	}
}
