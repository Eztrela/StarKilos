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
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import com.db4o.ObjectContainer;

import models.TipoComida;
import regras_negocio.Fachada;

public class TelaTipoComida {
	private JDialog frame;
	private JTable table;
	private JScrollPane scrollPane;
	private JTextField inputNomeTipoComida;
	private JTextField inputPrecoTipoComida;
	private JButton btnListar;
	private JButton btnCriarTipoComida;
	private JButton btnApagar;
	private JLabel label;
	private JLabel lblNomeTipoComida;
	private JLabel lblPreo;
	private JLabel label_4;

	private JButton button_3;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaTipoComida tela = new TelaTipoComida();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public TelaTipoComida() {
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
		frame.setTitle("Carro");
		frame.setBounds(100, 100, 729, 385);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.addWindowListener(new WindowAdapter() {
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
		frame.getContentPane().add(scrollPane);

		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				label_4.setText("selecionado="+ (String) table.getValueAt( table.getSelectedRow(), 0));
			}
		});
		table.setGridColor(Color.BLACK);
		table.setRequestFocusEnabled(false);
		table.setFocusable(false);
		table.setBackground(Color.YELLOW);
		table.setFillsViewportHeight(true);
		table.setRowSelectionAllowed(true);
		table.setFont(new Font("Tahoma", Font.PLAIN, 14));
		scrollPane.setViewportView(table);
		table.setBorder(new LineBorder(new Color(0, 0, 0)));
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setShowGrid(true);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

		label = new JLabel("");		//label de mensagem
		label.setForeground(Color.BLUE);
		label.setBounds(21, 321, 688, 14);
		frame.getContentPane().add(label);

		label_4 = new JLabel("resultados:");
		label_4.setBounds(21, 190, 431, 14);
		frame.getContentPane().add(label_4);

		lblNomeTipoComida = new JLabel("Nome:");
		lblNomeTipoComida.setHorizontalAlignment(SwingConstants.LEFT);
		lblNomeTipoComida.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNomeTipoComida.setBounds(230, 217, 44, 14);
		frame.getContentPane().add(lblNomeTipoComida);

		inputNomeTipoComida = new JTextField();
		inputNomeTipoComida.setFont(new Font("Dialog", Font.PLAIN, 12));
		inputNomeTipoComida.setColumns(10);
		inputNomeTipoComida.setBounds(284, 214, 168, 20);
		frame.getContentPane().add(inputNomeTipoComida);

		btnCriarTipoComida = new JButton("Criar tipo de comida");
		btnCriarTipoComida.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					// TODO - fazer o tratamento do campo 'preco'
					if(inputNomeTipoComida.getText().isBlank() || inputPrecoTipoComida.getText().isBlank()) {
						label.setText("Campo vazio");
						return;
					}
					String nome = inputNomeTipoComida.getText();
					double preco = Double.parseDouble(inputPrecoTipoComida.getText());
					Fachada.cadastrarTipoComida(nome, preco);
					label.setText("Tipo de comida criado: "+ nome);
					listagem();
				}
				catch(Exception ex) {
					label.setText(ex.getMessage());
				}
			}
		});
		btnCriarTipoComida.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnCriarTipoComida.setBounds(294, 274, 142, 23);
		frame.getContentPane().add(btnCriarTipoComida);

		btnListar = new JButton("Listar");
		btnListar.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnListar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				listagem();
			}
		});
		btnListar.setBounds(308, 11, 89, 23);
		frame.getContentPane().add(btnListar);

		lblPreo = new JLabel("Preço:");
		lblPreo.setHorizontalAlignment(SwingConstants.LEFT);
		lblPreo.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblPreo.setBounds(230, 241, 44, 14);
		frame.getContentPane().add(lblPreo);

		inputPrecoTipoComida = new JTextField();
		inputPrecoTipoComida.setFont(new Font("Dialog", Font.PLAIN, 12));
		inputPrecoTipoComida.setColumns(10);
		inputPrecoTipoComida.setBounds(284, 244, 168, 20);
		frame.getContentPane().add(inputPrecoTipoComida);

		btnApagar = new JButton("Apagar selecionado");
		btnApagar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					if (table.getSelectedRow() >= 0){	
						label.setText("nao implementado ");
						String nome = (String) table.getValueAt( table.getSelectedRow(), 0);

						Fachada.excluirTipoComida(nome);
						label.setText("Tipo de comida apagado" );
						listagem();
					}
					else
						label.setText("nao selecionado");
				}
				catch(Exception ex) {
					label.setText(ex.getMessage());
				}
			}
		});
		btnApagar.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnApagar.setBounds(50, 237, 142, 23);
		frame.getContentPane().add(btnApagar);

		button_3 = new JButton("exibir alugueis");
		button_3.setFont(new Font("Tahoma", Font.PLAIN, 12));
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					if (table.getSelectedRow() >= 0){	
						String placa = (String) table.getValueAt( table.getSelectedRow(), 0);
						Carro car = Fachada.localizarCarro(placa);

						if(car != null) {
							String texto="";
							if(car.getAlugueis().isEmpty())
								texto = "nao possui alugueis";
							else
								for (Aluguel a : car.getAlugueis()) {
									texto = texto + a.getDatainicio()+ "-" + a.getDatafim() + "-" +a.getCliente().getNome()+ "\n";
								}

							JOptionPane.showMessageDialog(frame, texto, "alugueis", 1);
						}
					}
				}
				catch(Exception erro) {
					label.setText(erro.getMessage());
				}
			}
		});
		button_3.setBounds(516, 237, 134, 23);
		frame.getContentPane().add(button_3);
	}

	public void listagem() {
		try{
			List<Carro> lista = Fachada.listarCarros();

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
			table.setModel(model);

			label_4.setText("resultados: "+lista.size()+ " objetos");
		}
		catch(Exception erro){
			label.setText(erro.getMessage());
		}
	}


}
