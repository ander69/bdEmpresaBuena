package Modelo_vista;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import modeloDB_DAO.DepartamentoDAO;
import modeloDB_DAO.EmpleadoDAO;
import modeloDB_DTO.DepartamentoDTO;
import modeloDB_DTO.EmpleadoDTO;

public class ModificarEmpleado extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6745035280936297753L;
	private final JPanel contentPanel = new JPanel();
	private JTextField tfNombre;
	private JTextField tfApellido;
	private JTextField tfFecha;
	private JTextField tfSueldo;
	private JComboBox<String> comboBox;
	private int idEmpe;

	/**
	 * Create the dialog.
	 */
	public ModificarEmpleado(int idEmpe) {
		this.idEmpe = idEmpe;
		setBounds(100, 100, 295, 264);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblModificarEmpleado = new JLabel("MODIFICAR EMPLEADO");
			lblModificarEmpleado.setHorizontalAlignment(SwingConstants.CENTER);
			lblModificarEmpleado.setFont(new Font("Tahoma", Font.BOLD, 14));
			lblModificarEmpleado.setBounds(10, 11, 255, 14);
			contentPanel.add(lblModificarEmpleado);
		}
		{
			JLabel lblNombre = new JLabel("Nombre");
			lblNombre.setBounds(10, 36, 137, 14);
			contentPanel.add(lblNombre);
		}
		{
			tfNombre = new JTextField();
			tfNombre.setBounds(157, 36, 108, 20);
			contentPanel.add(tfNombre);
			tfNombre.setColumns(10);
		}
		{
			JLabel lblApellido = new JLabel("Apellido");
			lblApellido.setBounds(10, 64, 137, 14);
			contentPanel.add(lblApellido);
		}
		{
			tfApellido = new JTextField();
			tfApellido.setColumns(10);
			tfApellido.setBounds(157, 64, 108, 20);
			contentPanel.add(tfApellido);
		}
		{
			JLabel lblFechaAlta = new JLabel("Fecha Alta (d/m/a)");
			lblFechaAlta.setBounds(10, 92, 137, 14);
			contentPanel.add(lblFechaAlta);
		}
		{
			tfFecha = new JTextField();
			tfFecha.setColumns(10);
			tfFecha.setBounds(157, 92, 108, 20);
			contentPanel.add(tfFecha);
		}
		{
			JLabel lblSueldo = new JLabel("Sueldo");
			lblSueldo.setBounds(10, 120, 137, 14);
			contentPanel.add(lblSueldo);
		}
		{
			tfSueldo = new JTextField();
			tfSueldo.setColumns(10);
			tfSueldo.setBounds(157, 120, 108, 20);
			contentPanel.add(tfSueldo);
		}
		{
			JLabel lblDepartamento = new JLabel("Departamento");
			lblDepartamento.setBounds(10, 148, 137, 14);
			contentPanel.add(lblDepartamento);
		}
		
		JButton btnAceptar = new JButton("ACEPTAR");
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				modificar();
			}
		});
		btnAceptar.setBounds(10, 190, 255, 23);
		contentPanel.add(btnAceptar);
		
		comboBox = new JComboBox<String>();
		comboBox.setBounds(157, 151, 108, 20);
		contentPanel.add(comboBox);
		
		try {
			cargarDepartamentos();
			cargarDatos();
		} catch(Exception e) {
			JOptionPane.showMessageDialog(contentPanel, "Error al leer el empleado!", "Error!", JOptionPane.ERROR_MESSAGE);
		}		
		this.setVisible(true);	
	}
	
	private void cargarDepartamentos() {
		DepartamentoDAO dptoDAO = new DepartamentoDAO();
		List<DepartamentoDTO> deps = dptoDAO.listarTodos();
		String[] departamentos = new String[deps.size()];
		for(int i=0; i<deps.size(); i++) {
			DepartamentoDTO dpt = deps.get(i);
			departamentos[i] = dpt.getIdDpto() + "- " + dpt.getNomDpto();
		}
		comboBox.setModel(new DefaultComboBoxModel<String>(departamentos));
	}
	
	private void cargarDatos() {
		EmpleadoDAO empeDAO = new EmpleadoDAO();
		EmpleadoDTO empleado = empeDAO.buscar(idEmpe);
		tfNombre.setText(empleado.getNomEmple());
		tfApellido.setText(empleado.getApelEmple());
		tfFecha.setText((new SimpleDateFormat("dd/MM/yyyy")).format(empleado.getFaltaEmple()));
		tfSueldo.setText(empleado.getSueldoEmple()+"");
		int seleccionar = 0;
		int elementos = ((DefaultComboBoxModel<String>) comboBox.getModel()).getSize();
		for(int i=0; i<elementos; i++) {
			String departamento = ((DefaultComboBoxModel<String>) comboBox.getModel()).getElementAt(i);
			int idDpto = Integer.parseInt(departamento.split("-")[0]);
			if(idDpto == empleado.getIdDptoEmple()) {
				seleccionar = i;
			}
		}
		comboBox.setSelectedIndex(seleccionar);
	}
	
	private void modificar() {
		try {
			int confirmar = JOptionPane.showConfirmDialog(contentPanel, "Estás seguro de que deseas modificar el empleado?");
			if(confirmar != 0) {
				return;
			}
			
			EmpleadoDAO empleDAO = new EmpleadoDAO();
			String nombre = tfNombre.getText();
			String apellido = tfApellido.getText();		
			Date fecha = new Date((new SimpleDateFormat("dd/MM/yyyy")).parse(tfFecha.getText()).getTime());		
			double sueldo = Double.parseDouble(tfSueldo.getText());
			String dptoString = (String) comboBox.getSelectedItem();
			int dpto = Integer.parseInt(dptoString.split("-")[0]);
			
			EmpleadoDTO empleado = new EmpleadoDTO(idEmpe, nombre, apellido, fecha, sueldo, dpto);
			if(empleDAO.actualizar(empleado)) {
				JOptionPane.showMessageDialog(contentPanel, "Empleado modificado!");
			} else {
				JOptionPane.showMessageDialog(contentPanel, "Error al modificar empleado!", "Error", JOptionPane.ERROR_MESSAGE);
			}
		} catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(contentPanel, "Error al modificar empleado!", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
}
