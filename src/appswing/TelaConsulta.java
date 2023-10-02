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
				lblResultados.setText("selecionado="+ (String) tableDeResultados.getValueAt( tableDeResultados.getSelectedRow(), 0));
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

		lblMensagens = new JLabel("");		//label de mensagem
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
				if(index<0)
					lblResultados.setText("consulta nao selecionada");
				else
					switch(index) {
					case 0: 
						List<Pesagem> resultado1 = Fachada.alugueisFinalizados();
						listagemAluguel(resultado1);
						break;
					case 1: 
						String modelo = JOptionPane.showInputDialog("digite o modelo");
						List<Aluguel> resultado2 = Fachada.alugueisModelo(modelo);
						listagemAluguel(resultado2);
						break;
					case 2: 
						String n = JOptionPane.showInputDialog("digite N");
						int numero = Integer.parseInt(n);
						List<Carro> resultado3 = Fachada.carrosNAlugueis(numero);
						listagemCarro(resultado3);
						break;

					}

			}
		});
		btnConsultas.setBounds(606, 10, 89, 23);
		frame.getContentPane().add(btnConsultas);

		comboboxConsultas = new JComboBox();
		comboboxConsultas.setToolTipText("selecione a consulta");
		comboboxConsultas.setModel(new DefaultComboBoxModel(new String[] {
				"Pesagens por cliente",
				"Pesagens por data",
				"Clientes com N pesagens"}));
		comboboxConsultas.setBounds(21, 10, 513, 22);
		frame.getContentPane().add(comboboxConsultas);
	}

	public void listagemAluguel(List<Aluguel> lista) {
		try{
			// o model armazena todas as linhas e colunas do table
			DefaultTableModel model = new DefaultTableModel();

			//adicionar colunas no model
			model.addColumn("id");
			model.addColumn("nome");
			model.addColumn("placa");
			model.addColumn("data inicial");
			model.addColumn("data final");
			model.addColumn("total a pagar");
			model.addColumn("finalizado");

			//adicionar linhas no model
			for(Aluguel aluguel : lista) {
				model.addRow(new Object[]{aluguel.getId(), aluguel.getCliente().getNome(), aluguel.getCarro().getPlaca(), aluguel.getDatainicio(), aluguel.getDatafim(), aluguel.getValor(), aluguel.isFinalizado()});
			}
			//atualizar model no table (visualizacao)
			tableDeResultados.setModel(model);

			lblResultados.setText("resultados: "+lista.size()+ " objetos");
		}
		catch(Exception erro){
			lblMensagens.setText(erro.getMessage());
		}
	}
	
	public void listagemCarro(List<Carro> lista) {
		try{
			// model armazena todas as linhas e colunas do table
			DefaultTableModel model = new DefaultTableModel();

			//adicionar colunas no model
			model.addColumn("placa");
			model.addColumn("modelo");
			model.addColumn("alugado");

			//adicionar linhas no model
			for(Carro car : lista) {
				model.addRow(new Object[]{car.getPlaca(), car.getModelo(), car.isAlugado()} );
			}
			//atualizar model no table (visualizacao)
			tableDeResultados.setModel(model);

			lblResultados.setText("resultados: "+lista.size()+ " objetos");
		}
		catch(Exception erro){
			lblMensagens.setText(erro.getMessage());
		}
	}

}
