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

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import com.db4o.ObjectContainer;

import models.Pesagem;
import models.Cliente;
import regras_negocio.Fachada;

public class TelaConsulta {
	private JDialog frame;
	private JTable tableDeResultados;
	private JScrollPane scrollPane;
	private JButton btnConsultas;
	private JLabel lblMensagens;
	private JLabel lblResultados;

	private ObjectContainer manager;
	private JComboBox comboboxConsultas;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaConsulta tela = new TelaConsulta();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public TelaConsulta() {
		initialize();
		frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JDialog();
		frame.setModal(true);

		frame.setResizable(false);
		frame.setTitle("Consulta");
		frame.setBounds(100, 100, 729, 300);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				Fachada.inicializar();
			}

			@Override
			public void windowClosing(WindowEvent e) {
				Fachada.finalizar();
			}
		});

		scrollPane = new JScrollPane();
		scrollPane.setBounds(21, 43, 674, 148);
		frame.getContentPane().add(scrollPane);

		tableDeResultados = new JTable();
		tableDeResultados.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				lblResultados.setText(
						"selecionado=" + (String) tableDeResultados.getValueAt(tableDeResultados.getSelectedRow(), 0));
			}
		});
		tableDeResultados.setGridColor(Color.BLACK);
		tableDeResultados.setRequestFocusEnabled(false);
		tableDeResultados.setFocusable(false);
		tableDeResultados.setBackground(Color.LIGHT_GRAY);
		tableDeResultados.setFillsViewportHeight(true);
		tableDeResultados.setRowSelectionAllowed(true);
		tableDeResultados.setFont(new Font("Tahoma", Font.PLAIN, 14));
		scrollPane.setViewportView(tableDeResultados);
		tableDeResultados.setBorder(new LineBorder(new Color(0, 0, 0)));
		tableDeResultados.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableDeResultados.setShowGrid(true);
		tableDeResultados.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

		lblMensagens = new JLabel(""); // label de mensagem
		lblMensagens.setForeground(Color.BLUE);
		lblMensagens.setBounds(31, 214, 688, 14);
		frame.getContentPane().add(lblMensagens);

		lblResultados = new JLabel("resultados:");
		lblResultados.setBounds(21, 190, 431, 14);
		frame.getContentPane().add(lblResultados);

		btnConsultas = new JButton("Consultar");
		btnConsultas.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnConsultas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int index = comboboxConsultas.getSelectedIndex();
				if (index < 0)
					lblResultados.setText("consulta nao selecionada");
				else
					switch (index) {
						case 0:
							String data = JOptionPane.showInputDialog("Digite a data");
							List<Pesagem> pesagensNaData = Fachada.pesagensPorData(data);
							listagemPesagem(pesagensNaData);
							break;
						case 1:
							String idCliente = JOptionPane.showInputDialog("Digite o ID do cliente");
							if (idCliente != null) {								
								int idClienteint = Integer.parseInt(idCliente);
								List<Pesagem> pesagens = Fachada.pesagensPorCliente(idClienteint);
								System.out.println(pesagens);
								listagemPesagem(pesagens);
							}
							break;
						case 2:
							String quantidadeN = JOptionPane.showInputDialog("Digite a quantidade N");
							if (quantidadeN != null) {								
								int numero = Integer.parseInt(quantidadeN);
								List<Cliente> clientes = Fachada.clientesComNPesagens(numero);
								listagemCliente(clientes);
							}
							break;

					}

			}
		});
		btnConsultas.setBounds(606, 10, 89, 23);
		frame.getContentPane().add(btnConsultas);

		comboboxConsultas = new JComboBox();
		comboboxConsultas.setToolTipText("selecione a consulta");
		comboboxConsultas.setModel(new DefaultComboBoxModel(new String[] {
				"Pesagens por data",
				"Pesagens por cliente",
				"Clientes com mais de N pesagens" }));
		comboboxConsultas.setBounds(21, 10, 513, 22);
		frame.getContentPane().add(comboboxConsultas);
	}

	public void listagemPesagem(List<Pesagem> lista) {
		try {
			// o model armazena todas as linhas e colunas do table
			DefaultTableModel model = new DefaultTableModel();

			// adicionar colunas no model
			model.addColumn("ID da Pesagem");
			model.addColumn("Tipo da Comida");
			model.addColumn("Peso (KG)");
			model.addColumn("ID do cliente");

			// adicionar linhas no model
			for (Pesagem pesagem : lista) {
				model.addRow(new Object[] {
						pesagem.getId(),
						pesagem.getTipoDaComida(),
						pesagem.getPeso(),
						pesagem.getCliente().getId()
				});
			}
			// atualizar model no table (visualizacao)
			tableDeResultados.setModel(model);

			lblResultados.setText("resultados: " + lista.size() + " objetos");
		} catch (Exception erro) {
			lblMensagens.setText(erro.getMessage());
		}
	}

	public void listagemCliente(List<Cliente> lista) {
		try {
			// model armazena todas as linhas e colunas do table
			DefaultTableModel model = new DefaultTableModel();

			// adicionar colunas no model
			model.addColumn("ID");
			model.addColumn("Lista de pesagens");

			// adicionar linhas no model
			for (Cliente cliente : lista) {
				List<Pesagem> listaDePesagens = cliente.getListaDePesagem();

				if (listaDePesagens.size() > 0)
					for (Pesagem pesagem : listaDePesagens)
						model.addRow(new Object[] { cliente.getId(), pesagem });
				else
					model.addRow(new Object[] { cliente.getId(), "Sem pesagens" });
			}
			// atualizar model no table (visualizacao)
			tableDeResultados.setModel(model);

			lblResultados.setText("resultados: " + lista.size() + " objetos");
		} catch (Exception erro) {
			lblMensagens.setText(erro.getMessage());
		}
	}

}
