package Modelo_vista;

import java.awt.BorderLayout;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import modeloDB_DAO.DepartamentoDAO;
import modeloDB_DTO.DepartamentoDTO;

public class ListarDepartamentos extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7610249335574089732L;
	private final JPanel contentPanel = new JPanel();
	private JTable tableDept;
	private ArrayList<DepartamentoDTO> departamentos;
	private DepartamentoDAO dpto = new DepartamentoDAO();


	/**
	 * Create the dialog.
	 */
	public ListarDepartamentos() {
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(27, 57, 375, 194);
		contentPanel.add(scrollPane);
		{
			tableDept = new JTable();
			tableDept.setModel(new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
					"Id Dept", "Descripcion", "Poblacion"
				}
			));
			scrollPane.setViewportView(tableDept);
		}
		
		JLabel lblListarDepartamentos = new JLabel("LISTAR DEPATAMENTOS");
		lblListarDepartamentos.setHorizontalAlignment(SwingConstants.CENTER);
		lblListarDepartamentos.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblListarDepartamentos.setBounds(10, 11, 414, 14);
		contentPanel.add(lblListarDepartamentos);
		
		try{
			cargarEmpleados();
		}catch(Exception e){
			JOptionPane.showMessageDialog(contentPanel, "Error al leer los departamentos!", "Error", JOptionPane.ERROR_MESSAGE);
		}
		
		this.setVisible(true);		
	}
	
	private void cargarEmpleados(){
		departamentos = dpto.listarTodos();
		DefaultTableModel modelo=(DefaultTableModel)tableDept.getModel();
		int numcols=modelo.getColumnCount();
		for(DepartamentoDTO dpto : departamentos){
			Object [] fila=new Object[numcols];
			fila[0] = dpto.getIdDpto();
			fila[1] = dpto.getNomDpto();
			fila[2] = dpto.getPoblDpto();
			
			modelo.addRow(fila);
		}
	}
}

