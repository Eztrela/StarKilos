package appswing;
/**********************************
 * IFPB - Curso Superior de Tec. em Sist. para Internet
 * POO
 * Prof. Fausto Maranh�o Ayres
 **********************************/

import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.SwingConstants;

import regras_negocio.Fachada;

public class TelaPrincipal {
	private JFrame frmStarkilos;
	private JMenu mnTipoComida;
	private JMenu mnCliente;
	private JMenu mnPesagem;
	private JMenu mnConsulta;
	private JLabel lblInicializando;



	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					TelaPrincipal window = new TelaPrincipal();
//					window.frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the application.
	 */
	public TelaPrincipal() {
		initialize();
		frmStarkilos.setTitle("StarKilos - usuário: "+ Fachada.logado.getNome());
		frmStarkilos.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmStarkilos = new JFrame();
		frmStarkilos.setTitle("StarKilos");
		frmStarkilos.setBounds(100, 100, 450, 363);
		frmStarkilos.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmStarkilos.getContentPane().setLayout(null);

		lblInicializando = new JLabel("");
		lblInicializando.setFont(new Font("Tahoma", Font.PLAIN, 26));
		lblInicializando.setHorizontalAlignment(SwingConstants.CENTER);
		lblInicializando.setText("Inicializando...");
		lblInicializando.setBounds(0, 0, 467, 302);
		//label.setBounds(0, 0, frame.getWidth(), frame.getHeight());
//		ImageIcon imagem = new ImageIcon(getClass().getResource("/arquivos/imagem.png"));
//		imagem = new ImageIcon(imagem.getImage().getScaledInstance(lblInicializando.getWidth(),lblInicializando.getHeight(), Image.SCALE_DEFAULT));//		label.setIcon(fotos);
//		lblInicializando.setIcon(imagem);
		frmStarkilos.getContentPane().add(lblInicializando);
		frmStarkilos.setResizable(false);

		JMenuBar menuBar = new JMenuBar();
		frmStarkilos.setJMenuBar(menuBar);
		mnTipoComida = new JMenu("Tipo de comida");
		mnTipoComida.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				TelaTipoComida tela = new TelaTipoComida();
			}
		});
		menuBar.add(mnTipoComida);

		mnCliente = new JMenu("Cliente");
		mnCliente.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				TelaCliente tela = new TelaCliente();
			}
		});
		menuBar.add(mnCliente);
		
		mnPesagem = new JMenu("Pesagem");
		mnPesagem.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				TelaPesagem tela = new TelaPesagem();
			}
		});
		menuBar.add(mnPesagem);
		
		mnConsulta = new JMenu("Consultas");
		mnConsulta.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				TelaConsulta tela = new TelaConsulta();
			}
		});
		menuBar.add(mnConsulta);
	}
}
