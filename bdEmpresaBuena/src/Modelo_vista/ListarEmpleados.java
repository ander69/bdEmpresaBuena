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

import modeloDB_DAO.EmpleadoDAO;
import modeloDB_DTO.EmpleadoDTO;

public class ListarEmpleados extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7610249335574089732L;
	private final JPanel contentPanel = new JPanel();
	private JTable tableEmpe;
	private ArrayList<EmpleadoDTO> empleados;
	private EmpleadoDAO emp = new EmpleadoDAO();


	/**
	 * Create the dialog.
	 */
	public ListarEmpleados() {
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(27, 57, 375, 194);
		contentPanel.add(scrollPane);
		{
			tableEmpe = new JTable();
			tableEmpe.setModel(new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
					"Id emple", "Nombre", "Apellidos", "Fecha alta", "Sueldo", "Dpto"
				}
			));
			scrollPane.setViewportView(tableEmpe);
		}
		
		JLabel lblListarEmpleados = new JLabel("LISTAR EMPLEADOS");
		lblListarEmpleados.setHorizontalAlignment(SwingConstants.CENTER);
		lblListarEmpleados.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblListarEmpleados.setBounds(10, 11, 414, 14);
		contentPanel.add(lblListarEmpleados);
		
		try{
			cargarEmpleados();
		}catch(Exception e){
			JOptionPane.showMessageDialog(contentPanel, "Error al leer los empleados!", "Error", JOptionPane.ERROR_MESSAGE);
		}
		
		this.setVisible(true);		
	}
	
	private void cargarEmpleados(){
		empleados = emp.listarTodos();
		DefaultTableModel modelo = (DefaultTableModel)tableEmpe.getModel();
		int numcols = modelo.getColumnCount();
		for(EmpleadoDTO emp : empleados){
			Object [] fila=new Object[numcols];
			fila[0] = emp.getIdEmple();
			fila[1] = emp.getNomEmple();
			fila[2] = emp.getApelEmple();
			fila[3] = emp.getFaltaEmple();
			fila[4] = emp.getSueldoEmple();
			fila[5] = emp.getIdDptoEmple();
			
			modelo.addRow(fila);
		}
	}
}

