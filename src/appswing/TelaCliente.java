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

import models.Cliente;
import regras_negocio.Fachada;

public class TelaCliente {
	private JDialog frameCliente;
	private JTable tableListagem;
	private JScrollPane scrollPane;
	private JButton btnListar;
	private JButton btnCriarCliente;
	private JButton btnApagarCliente;
	private JLabel label;
	private JLabel label_4;

	private JButton button_3;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaCliente tela = new TelaCliente();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public TelaCliente() {
		initialize();
		frameCliente.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frameCliente = new JDialog();
		frameCliente.setModal(true);
		
		frameCliente.setResizable(false);
		frameCliente.setTitle("Cliente");
		frameCliente.setBounds(100, 100, 729, 340);
		frameCliente.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frameCliente.getContentPane().setLayout(null);
		frameCliente.addWindowListener(new WindowAdapter() {
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
		frameCliente.getContentPane().add(scrollPane);

		tableListagem = new JTable();
		tableListagem.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				label_4.setText("selecionado="+ (String) tableListagem.getValueAt( tableListagem.getSelectedRow(), 0));
			}
		});
		tableListagem.setGridColor(Color.BLACK);
		tableListagem.setRequestFocusEnabled(false);
		tableListagem.setFocusable(false);
		tableListagem.setBackground(Color.ORANGE);
		tableListagem.setFillsViewportHeight(true);
		tableListagem.setRowSelectionAllowed(true);
		tableListagem.setFont(new Font("Tahoma", Font.PLAIN, 14));
		scrollPane.setViewportView(tableListagem);
		tableListagem.setBorder(new LineBorder(new Color(0, 0, 0)));
		tableListagem.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableListagem.setShowGrid(true);
		tableListagem.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

		label = new JLabel("");		//label de mensagem
		label.setForeground(Color.BLUE);
		label.setBounds(21, 254, 688, 14);
		frameCliente.getContentPane().add(label);

		label_4 = new JLabel("resultados:");
		label_4.setBounds(21, 190, 431, 14);
		frameCliente.getContentPane().add(label_4);

		btnCriarCliente = new JButton("Criar novo cliente");
		btnCriarCliente.setBackground(new Color(54, 219, 36));
		btnCriarCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					models.Cliente clienteCadastrado = Fachada.cadastrarCliente();
					label.setText("Cliente criado: " + clienteCadastrado.getId());
					listagem();
				}
				catch(Exception ex) {
					label.setText(ex.getMessage());
				}
			}
		});
		btnCriarCliente.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnCriarCliente.setBounds(286, 214, 134, 23);
		frameCliente.getContentPane().add(btnCriarCliente);

		btnListar = new JButton("Listar");
		btnListar.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnListar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				listagem();
			}
		});
		btnListar.setBounds(308, 11, 89, 23);
		frameCliente.getContentPane().add(btnListar);

		btnApagarCliente = new JButton("Apagar selecionado");
		btnApagarCliente.setBackground(new Color(255, 0, 0));
		btnApagarCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					if (tableListagem.getSelectedRow() >= 0){	
						label.setText("nao implementado " );
						String idDoCliente = (String) tableListagem.getValueAt( tableListagem.getSelectedRow(), 0);
						Fachada.excluirCliente(idDoCliente);
						label.setText("cliente apagado" );
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
		btnApagarCliente.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnApagarCliente.setBounds(430, 214, 134, 23);
		frameCliente.getContentPane().add(btnApagarCliente);

		button_3 = new JButton("exibir alugueis");
		button_3.setFont(new Font("Tahoma", Font.PLAIN, 12));
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					if (tableListagem.getSelectedRow() >= 0){	
						String cpf = (String) tableListagem.getValueAt( tableListagem.getSelectedRow(), 0);
						Cliente cliente = Fachada.localizarCliente(cpf);

						if(cliente !=  null) {
							String texto="";
							if(cliente.getAlugueis().isEmpty())
								texto = "nao possui alugueis";
							else
								for (Aluguel a : cliente.getAlugueis()) 
									texto = texto + a.getDatainicio()+ "-" + a.getDatafim() + "-" +a.getCarro().getPlaca()+ "\n";

							JOptionPane.showMessageDialog(frameCliente, texto, "alugueis", 1);
						}
					}
				}
				catch(Exception erro) {
					label.setText(erro.getMessage());
				}
			}
		});
		button_3.setBounds(142, 214, 134, 23);
		frameCliente.getContentPane().add(button_3);
	}

	public void listagem() {
		try{
			List<Cliente> lista = Fachada.listarClientes();

			// model armazena todas as linhas e colunas do table
			DefaultTableModel model = new DefaultTableModel();

			//adicionar colunas no model
			model.addColumn("cpf");
			model.addColumn("nome");

			//adicionar linhas no model
			for(Cliente cli : lista) {
				model.addRow(new Object[]{cli.getCpf(), cli.getNome()} );
			}

			//atualizar model no table (visualizacao)
			tableListagem.setModel(model);

			label_4.setText("resultados: "+lista.size()+ " objetos");
		}
		catch(Exception erro){
			label.setText(erro.getMessage());
		}
	}
}
