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
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import modeloDB_DAO.DepartamentoDAO;
import modeloDB_DAO.EmpleadoDAO;
import modeloDB_DTO.DepartamentoDTO;
import modeloDB_DTO.EmpleadoDTO;

public class ListarEmpleadosDpto extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7610249335574089732L;
	private final JPanel contentPanel = new JPanel();
	private ArrayList<EmpleadoDTO> empleados;
	private static EmpleadoDAO emp = new EmpleadoDAO();
	private static DepartamentoDAO dpto = new DepartamentoDAO();
	private JTextField tfCodDpto;
	private JTextField tfDescripcion;
	private JTextField tfPoblacion;
	private JTable table;


	/**
	 * Create the dialog.
	 */
	public ListarEmpleadosDpto(int codDept) {
		setBounds(100, 100, 609, 347);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblListarEmpleados = new JLabel("LISTAR EMPLEADOS POR DEPARTAMENTO");
		lblListarEmpleados.setBounds(10, 11, 573, 14);
		lblListarEmpleados.setHorizontalAlignment(SwingConstants.CENTER);
		lblListarEmpleados.setFont(new Font("Tahoma", Font.BOLD, 14));
		contentPanel.add(lblListarEmpleados);
		
		JLabel lblCodDpto = new JLabel("Cod Dpto");
		lblCodDpto.setBounds(10, 36, 75, 14);
		contentPanel.add(lblCodDpto);
		
		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setBounds(229, 36, 46, 14);
		contentPanel.add(lblNombre);
		
		JLabel lblPoblacion = new JLabel("Poblacion");
		lblPoblacion.setBounds(415, 36, 72, 14);
		contentPanel.add(lblPoblacion);
		
		tfCodDpto = new JTextField();
		tfCodDpto.setEditable(false);
		tfCodDpto.setBounds(95, 36, 86, 20);
		contentPanel.add(tfCodDpto);
		tfCodDpto.setColumns(10);
		tfCodDpto.setText(codDept + "");
		
		tfDescripcion = new JTextField();
		tfDescripcion.setEditable(false);
		tfDescripcion.setBounds(285, 36, 86, 20);
		tfDescripcion.setColumns(10);
		contentPanel.add(tfDescripcion);
		
		tfPoblacion = new JTextField();
		tfPoblacion.setEditable(false);
		tfPoblacion.setBounds(497, 36, 86, 20);
		tfPoblacion.setColumns(10);
		contentPanel.add(tfPoblacion);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 85, 573, 213);
		contentPanel.add(scrollPane);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Id Emple", "Nombre", "Apellido", "Fecha Alta", "Sueldo", "Cod Dpto"
			}
		));
		scrollPane.setViewportView(table);
		
		try{
			cargarEmpleados();
			cargarDatosDpto();
		}catch(Exception e){
			JOptionPane.showMessageDialog(contentPanel, "Error al cargar los empleados!", "Error", JOptionPane.ERROR_MESSAGE);
		}
		
		this.setVisible(true);		
	}
	
	private void cargarEmpleados() {
		int dpto = 0;
		try {
			dpto = Integer.parseInt(tfCodDpto.getText());
		} catch(Exception e) {
			System.out.println("Error al leer el dpto!");
		}
		empleados = emp.listarTodosDpto(dpto);
		DefaultTableModel modelo = (DefaultTableModel) table.getModel();
		int numcols = modelo.getColumnCount();
		for(EmpleadoDTO emp:empleados){
			Object [] fila=new Object[numcols];
			fila[0]=emp.getIdEmple();
			fila[1]=emp.getNomEmple();
			fila[2]=emp.getApelEmple();
			fila[3]=emp.getFaltaEmple();
			fila[4]=emp.getSueldoEmple();
			fila[5]=emp.getIdDptoEmple();
			
			modelo.addRow(fila);
		}
	}
	
	private void cargarDatosDpto() {
		int coddpto = 0;
		try {
			coddpto = Integer.parseInt(tfCodDpto.getText());
		} catch(Exception e) {
			JOptionPane.showMessageDialog(contentPanel, "Error al leer el departamento!", "Error", JOptionPane.ERROR_MESSAGE);
		}
		DepartamentoDTO departamento = dpto.buscar(coddpto);
		if(departamento != null) {
			tfDescripcion.setText(departamento.getNomDpto());
			tfPoblacion.setText(departamento.getPoblDpto());
		}
	}
}

