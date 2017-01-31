package Modelo_vista;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import java.awt.Font;
import javax.swing.SwingConstants;

public class JEmpleados extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7390160279888302127L;
	private JPanel contentPane;
	private JTextField etCodDpto;
	private JTextField etIdEmple;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JEmpleados frame = new JEmpleados();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public JEmpleados() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblGestionDeDepartamentos = new JLabel("GESTION DE DEPARTAMENTOS");
		lblGestionDeDepartamentos.setHorizontalAlignment(SwingConstants.CENTER);
		lblGestionDeDepartamentos.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblGestionDeDepartamentos.setBounds(10, 11, 414, 14);
		contentPane.add(lblGestionDeDepartamentos);
		
		JButton btnListarEmpleados = new JButton("Listar Empleados");
		btnListarEmpleados.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new ListarEmpleados();
			}
		});
		btnListarEmpleados.setBounds(10, 58, 201, 23);
		contentPane.add(btnListarEmpleados);
		
		JButton btnListarDepartamentos = new JButton("Listar departamentos");
		btnListarDepartamentos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new ListarDepartamentos();
			}
		});
		btnListarDepartamentos.setBounds(10, 92, 201, 23);
		contentPane.add(btnListarDepartamentos);
		
		JButton btnListarEmpleadosPor = new JButton("Listar empleados por dpto");
		btnListarEmpleadosPor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					int dpto = Integer.parseInt(etCodDpto.getText());
					new ListarEmpleadosDpto(dpto);
				} catch(Exception e) {
					JOptionPane.showMessageDialog(contentPane, "Error al leer la id de departamento!", "Error", JOptionPane.ERROR_MESSAGE);
				}				
			}
		});
		btnListarEmpleadosPor.setBounds(10, 127, 201, 23);
		contentPane.add(btnListarEmpleadosPor);
		
		JButton btnAltaEmpleado = new JButton("Alta empleado");
		btnAltaEmpleado.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new AltaEmpleado();
			}
		});
		btnAltaEmpleado.setBounds(10, 160, 201, 23);
		contentPane.add(btnAltaEmpleado);
		
		JButton btnModificarEmpleado = new JButton("Modificar empleado");
		btnModificarEmpleado.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int idEmpe = Integer.parseInt(etIdEmple.getText());
					new ModificarEmpleado(idEmpe);
				} catch(Exception ex) {
					JOptionPane.showMessageDialog(contentPane, "Error al leer la id de empleado!", "Error", JOptionPane.ERROR_MESSAGE);
				}				
			}
		});
		btnModificarEmpleado.setBounds(10, 194, 201, 23);
		contentPane.add(btnModificarEmpleado);
		
		JButton btnEliminarEmpleado = new JButton("Eliminar empleado");
		btnEliminarEmpleado.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int idEmpe = Integer.parseInt(etIdEmple.getText());
					new EliminarEmpleado(idEmpe);
				} catch(Exception ex) {
					JOptionPane.showMessageDialog(contentPane, "Error al leer la id de empleado!", "Error", JOptionPane.ERROR_MESSAGE);
				}		
			}
		});
		btnEliminarEmpleado.setBounds(10, 228, 201, 23);
		contentPane.add(btnEliminarEmpleado);
		
		JLabel lblIdDpto = new JLabel("Id Dpto");
		lblIdDpto.setBounds(253, 95, 58, 14);
		contentPane.add(lblIdDpto);
		
		JLabel lblIdEmple = new JLabel("Id Emple");
		lblIdEmple.setBounds(253, 130, 58, 14);
		contentPane.add(lblIdEmple);
		
		etCodDpto = new JTextField();
		etCodDpto.setBounds(321, 92, 86, 20);
		contentPane.add(etCodDpto);
		etCodDpto.setColumns(10);
		
		etIdEmple = new JTextField();
		etIdEmple.setText("");
		etIdEmple.setBounds(321, 127, 86, 20);
		contentPane.add(etIdEmple);
		etIdEmple.setColumns(10);
	}
}
