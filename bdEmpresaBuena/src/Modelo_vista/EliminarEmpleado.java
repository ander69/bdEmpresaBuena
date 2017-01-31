package Modelo_vista;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.JButton;
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

public class EliminarEmpleado extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6745035280936297753L;
	private final JPanel contentPanel = new JPanel();
	private JTextField tfNombre;
	private JTextField tfApellido;
	private JTextField tfFecha;
	private JTextField tfSueldo;
	private int idEmpe;
	private JTextField tfDepartamento;
	private EmpleadoDAO empeDAO = new EmpleadoDAO();

	/**
	 * Create the dialog.
	 */
	public EliminarEmpleado(int idEmpe) {
		this.idEmpe = idEmpe;
		setBounds(100, 100, 295, 264);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblModificarEmpleado = new JLabel("ELIMINAR EMPLEADO");
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
			tfNombre.setEditable(false);
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
			tfApellido.setEditable(false);
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
			tfFecha.setEditable(false);
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
			tfSueldo.setEditable(false);
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
				eliminar();
			}
		});
		btnAceptar.setBounds(10, 190, 255, 23);
		contentPanel.add(btnAceptar);
		
		tfDepartamento = new JTextField();
		tfDepartamento.setText("0.0");
		tfDepartamento.setEditable(false);
		tfDepartamento.setColumns(10);
		tfDepartamento.setBounds(157, 151, 108, 20);
		contentPanel.add(tfDepartamento);
		
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
	}
	
	private void cargarDatos() {
		EmpleadoDTO empleado = empeDAO.buscar(idEmpe);
		tfNombre.setText(empleado.getNomEmple());
		tfApellido.setText(empleado.getApelEmple());
		tfFecha.setText((new SimpleDateFormat("dd/MM/yyyy")).format(empleado.getFaltaEmple()));
		tfSueldo.setText(empleado.getSueldoEmple()+"");
		
		DepartamentoDAO dptDAO = new DepartamentoDAO();
		DepartamentoDTO dptDTO = dptDAO.buscar(empleado.getIdDptoEmple());		
		tfDepartamento.setText(dptDTO.getIdDpto() + "- " + dptDTO.getNomDpto());
	}
	
	private void eliminar() {
		try {
			int confirmar = JOptionPane.showConfirmDialog(contentPanel, "Estás seguro de que deseas eliminar el empleado?");
			if(confirmar != 0) {
				return;
			}
			
			if(empeDAO.borrar(idEmpe)) {
				JOptionPane.showMessageDialog(contentPanel, "Empleado eliminado!");
			} else {
				JOptionPane.showMessageDialog(contentPanel, "Error al eliminar empleado!", "Error", JOptionPane.ERROR_MESSAGE);
			}
		} catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(contentPanel, "Error al eliminar empleado!", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
}
