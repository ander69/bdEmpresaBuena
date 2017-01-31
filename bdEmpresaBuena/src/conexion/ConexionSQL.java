package conexion;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConexionSQL {
	private static ConexionSQL instancia = null;
	private Connection con;
	
	private ConexionSQL() {
		String host = "127.0.0.1";
		String user = "root";
		String pass = "";
		String dtbs = "bd_empresa";
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String newConnectionURL = "jdbc:mysql://" + host + "/" + dtbs + "?user=" + user + "&password=" + pass;
			con = DriverManager.getConnection(newConnectionURL);
		} catch(Exception e) {
			System.out.println("Error al abrir la conexión: " + e.getMessage());
		}
	}
	
	public static ConexionSQL getInstance() {
		if(instancia == null) {
			instancia = new ConexionSQL();
		}
		return instancia;
	}
	
	public Connection getCon() {
		return con;
	}
	
	public void cerrarConexion() {
		try {
			con.close();
		} catch(Exception e) {
			System.out.println("Error al cerrar la conexión: " + e.getMessage());
		}
	}
	
	
}
