/**********************************
 * IFPB - Curso Superior de Tec. em Sist. para Internet
 * POB - Persistencia de Objetos
 * Prof. Fausto Ayres
 *
 */
package appswing;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import models.Pesagem;
import regras_negocio.Fachada;

public class TelaPesagem {
	private JDialog framePesagem;
	private JTable table;
	private JScrollPane scrollPane;
	private JTextField inputPeso;
	private JTextField inputTipoDeComida;
	private JTextField inputIdDoClient;
	private JButton btnListar;
	private JButton btnCriarPesagem;
	private JButton btnApagarSelecionada;
	private JLabel lblMensagens;
	private JLabel lblInputPeso;
	private JLabel lblTipoDeComida;
	private JLabel lblIdDoCliente;
	private JLabel lblResultados;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaPesagem tela = new TelaPesagem();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public TelaPesagem() {
		initialize();
		framePesagem.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		framePesagem = new JDialog();
		framePesagem.setModal(true);
		framePesagem.setResizable(false);
		framePesagem.setTitle("Pesagem");
		framePesagem.setBounds(100, 100, 729, 400);
		framePesagem.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		framePesagem.getContentPane().setLayout(null);
		framePesagem.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				Fachada.inicializar();
				listagem();
			}
			@Override
			public void windowClosing(WindowEvent e) {
				Fachada.finalizar();
			}
		});

		scrollPane = new JScrollPane();
		scrollPane.setBounds(21, 43, 674, 148);
		framePesagem.getContentPane().add(scrollPane);

		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				lblResultados.setText("selecionado="+ (int) table.getValueAt( table.getSelectedRow(), 0));
			}
		});
		table.setGridColor(Color.BLACK);
		table.setRequestFocusEnabled(false);
		table.setFocusable(false);
		table.setBackground(new Color(144, 238, 144));
		table.setFillsViewportHeight(true);
		table.setRowSelectionAllowed(true);
		table.setFont(new Font("Tahoma", Font.PLAIN, 14));
		scrollPane.setViewportView(table);
		table.setBorder(new LineBorder(new Color(0, 0, 0)));
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setShowGrid(true);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

		lblMensagens = new JLabel("");		//label de mensagem
		lblMensagens.setForeground(Color.BLUE);
		lblMensagens.setBounds(21, 334, 688, 14);
		framePesagem.getContentPane().add(lblMensagens);

		lblResultados = new JLabel("resultados:");
		lblResultados.setBounds(21, 190, 431, 14);
		framePesagem.getContentPane().add(lblResultados);

		lblInputPeso = new JLabel("Peso:");
		lblInputPeso.setHorizontalAlignment(SwingConstants.LEFT);
		lblInputPeso.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblInputPeso.setBounds(256, 214, 35, 14);
		framePesagem.getContentPane().add(lblInputPeso);

		inputPeso = new JTextField();
		inputPeso.setFont(new Font("Dialog", Font.PLAIN, 12));
		inputPeso.setColumns(10);
		inputPeso.setBounds(295, 211, 130, 20);
		framePesagem.getContentPane().add(inputPeso);

		btnCriarPesagem = new JButton("Criar pesagem");
		btnCriarPesagem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if(inputPeso.getText().isEmpty() || inputTipoDeComida.getText().isEmpty() || inputIdDoClient.getText().isEmpty()) {
						lblMensagens.setText("Campo vazio");
						return;
					}
					double peso = Double.parseDouble(inputPeso.getText());
					String tipoDeComida = inputTipoDeComida.getText();
					int idDoCliente = Integer.parseInt(inputIdDoClient.getText());

					Fachada.cadastrarPesagem(peso, tipoDeComida, idDoCliente);
					lblMensagens.setText("Pesagem criada");
					listagem();
				}
				catch (NumberFormatException nfe) {
					lblMensagens.setText(nfe.getMessage());
				}
				catch(Exception ex) {
					lblMensagens.setText(ex.getMessage());
				}
			}
		});
		btnCriarPesagem.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnCriarPesagem.setBounds(223, 301, 130, 23);
		framePesagem.getContentPane().add(btnCriarPesagem);

		btnListar = new JButton("Listar");
		btnListar.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnListar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				listagem();
			}
		});
		btnListar.setBounds(308, 11, 89, 23);
		framePesagem.getContentPane().add(btnListar);

		lblTipoDeComida = new JLabel("Tipo de comida:");
		lblTipoDeComida.setHorizontalAlignment(SwingConstants.LEFT);
		lblTipoDeComida.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblTipoDeComida.setBounds(199, 274, 94, 14);
		framePesagem.getContentPane().add(lblTipoDeComida);

		inputTipoDeComida = new JTextField();
		inputTipoDeComida.setFont(new Font("Dialog", Font.PLAIN, 12));
		inputTipoDeComida.setColumns(10);
		inputTipoDeComida.setBounds(295, 271, 130, 20);
		framePesagem.getContentPane().add(inputTipoDeComida);

		btnApagarSelecionada = new JButton("Apagar selecionada");
		btnApagarSelecionada.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					if (table.getSelectedRow() >= 0) {	
						int idPesagem = (int) table.getValueAt( table.getSelectedRow(), 0);

						Fachada.excluirPesagem(idPesagem);
						lblMensagens.setText("aluguel apagado" );
						listagem();
					}
					else
						lblMensagens.setText("nao selecionado");
				}
				catch(Exception ex) {
					lblMensagens.setText(ex.getMessage());
				}
			}
		});
		btnApagarSelecionada.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnApagarSelecionada.setBounds(363, 301, 142, 23);
		framePesagem.getContentPane().add(btnApagarSelecionada);


		inputIdDoClient = new JTextField();
		inputIdDoClient.setBounds(295, 241, 130, 20);
		framePesagem.getContentPane().add(inputIdDoClient);
		inputIdDoClient.setColumns(10);

		JTextPane textPane = new JTextPane();
		textPane.setBounds(47, 308, 1, 16);
		framePesagem.getContentPane().add(textPane);

		lblIdDoCliente = new JLabel("ID do cliente:");
		lblIdDoCliente.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblIdDoCliente.setBounds(212, 241, 81, 16);
		framePesagem.getContentPane().add(lblIdDoCliente);
	}

	public void listagem() {
		try{
			//ler as pesagens do banco
			List<Pesagem> listaDePesagens = Fachada.listarPesagens();

			// o model armazena todas as linhas e colunas do table
			DefaultTableModel model = new DefaultTableModel();

			//adicionar colunas no model
			model.addColumn("ID da pesagem");
			model.addColumn("Tipo de comida");
			model.addColumn("Peso (KG)");
			model.addColumn("ID do cliente");

			//adicionar linhas no model
			for(Pesagem pesagem : listaDePesagens)
				model.addRow(new Object[]{
						pesagem.getId(),
						pesagem.getTipoDaComida(),
						pesagem.getPeso(),
						pesagem.getCliente().getId()
				});

			//atualizar model no table (visualizacao)
			table.setModel(model);

			lblResultados.setText("resultados: "+listaDePesagens.size()+ " objetos");
		}
		catch(Exception erro){
			lblMensagens.setText(erro.getMessage());
		}
	}

	


}
