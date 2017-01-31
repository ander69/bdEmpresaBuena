package modeloDB_DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import conexion.ConexionSQL;
import modeloDB_DTO.EmpleadoDTO;

public class EmpleadoDAO implements Patron_DAO <EmpleadoDTO> {
	
	private static final String SQL_INSERT = "INSERT INTO empleados (nombre_Emple, apell_Emple, fechaAlta_Emple, sueldo_Emple, codDpto_Empe) VALUES (?, ?, ?, ?, ?)";
	private static final String SQL_DELETE = "DELETE FROM empleados WHERE id_Emple = ?";
	private static final String SQL_UPDATE = "UPDATE empleados SET nombre_Emple = ?, apell_Emple = ?, fechaAlta_Emple = ?, sueldo_Emple = ?, codDpto_Empe = ? WHERE id_Emple = ?";
	private static final String SQL_FIND = "SELECT * FROM empleados WHERE id_Emple = ?";
	private static final String SQL_FINDALL = "SELECT * FROM empleados";
	private static final String SQL_FINDDPTO = "SELECT * FROM empleados WHERE codDpto_Empe = ?";
	private ConexionSQL con = ConexionSQL.getInstance();
	
	@Override
	public boolean insertar(EmpleadoDTO dto) {
		PreparedStatement ps = null;
		try {
			ps = con.getCon().prepareStatement(SQL_INSERT);
			ps.setString(1, dto.getNomEmple());
			ps.setString(2, dto.getApelEmple());
			ps.setDate(3, dto.getFaltaEmple());
			ps.setDouble(4, dto.getSueldoEmple());
			ps.setInt(5, dto.getIdDptoEmple());
			
			if (ps.executeUpdate()>0) return true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ps!=null) ps.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return false;
	}
	
	@Override
	public boolean borrar(Object pk) {
		PreparedStatement ps = null;
		try {
			ps = con.getCon().prepareStatement(SQL_DELETE);
			ps.setInt(1, (int)pk);
			
			if (ps.executeUpdate()>0) return true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ps!=null) ps.close();
			} catch (Exception e3) {
				e3.printStackTrace();
			}
		}
		return false;
	}
	
	@Override
	public boolean actualizar(EmpleadoDTO t) {
		PreparedStatement ps = null;
		try {
			ps = con.getCon().prepareStatement(SQL_UPDATE);
			ps.setString(1, t.getNomEmple());
			ps.setString(2, t.getApelEmple());
			ps.setDate(3, t.getFaltaEmple());
			ps.setDouble(4, t.getSueldoEmple());
			ps.setInt(5, t.getIdDptoEmple());
			ps.setInt(6, t.getIdEmple());
			
			if (ps.executeUpdate()>0) return true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ps!=null) ps.close();
			} catch (Exception e4) {
				e4.printStackTrace();
			}
		}
		return false;
	}
	
	@Override
	public EmpleadoDTO buscar(Object pk) {
		EmpleadoDTO Empe = null;
		try {
			PreparedStatement ps = con.getCon().prepareStatement(SQL_FIND);
			ps.setInt(1, (int)pk);
			
			ResultSet rs = ps.executeQuery();
			if (rs.next()==true) {
				Empe = new EmpleadoDTO(rs.getInt("id_Emple"), rs.getString("nombre_Emple"), rs.getString("apell_Emple"), rs.getDate("fechaAlta_Emple"), rs.getDouble("sueldo_Emple"), rs.getInt("codDpto_Empe"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Empe;
	}
	
	@Override
	public ArrayList<EmpleadoDTO> listarTodos() {
		ArrayList<EmpleadoDTO> listaDep = new ArrayList<EmpleadoDTO>();
		try {
			PreparedStatement ps = con.getCon().prepareStatement(SQL_FINDALL);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				EmpleadoDTO emp = new EmpleadoDTO(rs.getInt("id_Emple"), rs.getString("nombre_Emple"), rs.getString("apell_Emple"), rs.getDate("fechaAlta_Emple"), rs.getDouble("sueldo_Emple"), rs.getInt("codDpto_Empe"));
				listaDep.add(emp);
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listaDep;
	}	
	
	public ArrayList<EmpleadoDTO> listarTodosDpto(int dpto) {
		ArrayList<EmpleadoDTO> listaDep = new ArrayList<EmpleadoDTO>();
		try {
			PreparedStatement ps = con.getCon().prepareStatement(SQL_FINDDPTO);
			ps.setInt(1, dpto);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				EmpleadoDTO emp = new EmpleadoDTO(rs.getInt("id_Emple"), rs.getString("nombre_Emple"), rs.getString("apell_Emple"), rs.getDate("fechaAlta_Emple"), rs.getDouble("sueldo_Emple"), rs.getInt("codDpto_Empe"));
				listaDep.add(emp);
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listaDep;
	}

}
