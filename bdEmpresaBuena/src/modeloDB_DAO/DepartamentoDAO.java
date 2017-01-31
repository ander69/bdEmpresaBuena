package modeloDB_DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import conexion.ConexionSQL;
import modeloDB_DTO.DepartamentoDTO;

public class DepartamentoDAO implements Patron_DAO <DepartamentoDTO>{
	
	private static final String SQL_INSERT = "INSERT INTO departamentos (descripcion, poblacion) VALUES (?, ?)";
	private static final String SQL_DELETE = "DELETE FROM departamentos WHERE id_Dpto = ?";
	private static final String SQL_UPDATE = "UPDATE departamentos SET descripcion = ?, poblacion = ? WHERE id_Dpto = ?";
	private static final String SQL_FIND = "SELECT * FROM departamentos WHERE id_Dpto = ?";
	private static final String SQL_FINDALL = "SELECT * FROM departamentos";
	
	private ConexionSQL con = ConexionSQL.getInstance();

	@Override
	public boolean insertar(DepartamentoDTO dto) {
		PreparedStatement ps = null;
		try {
			ps = con.getCon().prepareStatement(SQL_INSERT);
			ps.setString(1, dto.getNomDpto());
			ps.setString(2, dto.getPoblDpto());
			
			if(ps.executeUpdate() > 0) return true;
		} catch(SQLException e) {
			System.out.println("Error al insertar: " + e.getMessage());
		}
		
		finally {
			try {
				if(ps != null) {
					ps.close();
				}
			} catch(Exception e) {
				System.out.println("Error al cerrar: " + e.getMessage());
			}
		}
		return false;
	}

	@Override
	public boolean borrar(Object pk) {
		PreparedStatement ps = null;
		try {
			ps = con.getCon().prepareStatement(SQL_DELETE);
			ps.setInt(1, (int) pk);
			if(ps.executeUpdate() > 0) return true;
		} catch(SQLException e) {
			System.out.println("Error al borrar: " + e.getMessage());
		}
		finally {
			try {
				if(ps != null) {
					ps.close();
				}
			} catch(Exception e) {
				System.out.println("Error al cerrar: " + e.getMessage());
			}
		}
		return false;
	}

	@Override
	public boolean actualizar(DepartamentoDTO dto) {
		PreparedStatement ps = null;
		try {
			ps = con.getCon().prepareStatement(SQL_UPDATE);
			ps.setString(1, dto.getNomDpto());
			ps.setString(2, dto.getPoblDpto());
			ps.setInt(3, dto.getIdDpto());
			if(ps.executeUpdate() > 0) return true;
		} catch(SQLException e) {
			System.out.println("Error al actualizar: " + e.getMessage());
		}
		finally {
			try {
				if(ps != null) {
					ps.close();
				}
			} catch(Exception e) {
				System.out.println("Error al cerrar: " + e.getMessage());
			}
		}
		return false;
	}

	@Override
	public DepartamentoDTO buscar(Object pk) {
		DepartamentoDTO departamento = null;
		try {
			PreparedStatement ps = con.getCon().prepareStatement(SQL_FIND);
			ps.setInt(1, (int) pk); 
			ResultSet res = ps.executeQuery();
			
			if(res.next()) {
				int idDpto = res.getInt("id_Dpto");
				String nomDpto = res.getString("descripcion");
				String poblDpto = res.getString("poblacion");
				departamento = new DepartamentoDTO(idDpto, nomDpto, poblDpto);
			}
			
			res.close();
			ps.close();
		} catch(SQLException e) {
			System.out.println("Error al buscar: " + e.getMessage());
		}
		return departamento;
	}

	@Override
	public ArrayList<DepartamentoDTO> listarTodos() {
		ArrayList<DepartamentoDTO> departamentos = new ArrayList<DepartamentoDTO>();
		try {
			PreparedStatement ps = con.getCon().prepareStatement(SQL_FINDALL);
			ResultSet res = ps.executeQuery();
			
			while(res.next()) {
				int idDpto = res.getInt("id_Dpto");
				String nomDpto = res.getString("descripcion");
				String poblDpto = res.getString("poblacion");
				DepartamentoDTO departamento = new DepartamentoDTO(idDpto, nomDpto, poblDpto);
				departamentos.add(departamento);
			}
			
			res.close();
			ps.close();
		} catch(SQLException e) {
			System.out.println("Error al listar todos: " + e.getMessage());
		}
		return departamentos;
	}

}
