package mx.shf6.produccion.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import mx.shf6.produccion.model.Empleado;
import mx.shf6.produccion.utilities.Notificacion;



public class EmpleadoDAO {

	// METODO PARA CREAR UN REGISTRO
	public static boolean createEmpleado(Connection connection, Empleado empleado) {
		String consulta = "INSERT INTO ut_empleados (uf_Codigo, uf_Nombre, uf_PuestoFK) VALUES (?,?,?)";
		try {
			PreparedStatement senteciaPreparada = connection.prepareStatement(consulta);
			senteciaPreparada.setString(1, empleado.getCodigo());
			senteciaPreparada.setString(2, empleado.getNombre());
			senteciaPreparada.setInt(3, empleado.getPuestoFK());
			senteciaPreparada.execute();
			return true;
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
			return false;
		} // FIN TRY/CATCH
	}// FIN METODO

	// METODO PARA OBTENER UN REGISTRO
	public static ArrayList<Empleado> readEmpleado(Connection connection) {
		ArrayList<Empleado> arrayListEmpleado = new ArrayList<Empleado>();
		String consulta = "SELECT ut_empleados.Sys_PK, ut_empleados.uf_Codigo, ut_empleados.uf_Nombre, ut_empleados.uf_PuestoFK, ut_puestos.uf_Codigo FROM ut_empleados INNER JOIN ut_puestos ON ut_empleados.uf_PuestoFK = ut_puestos.Sys_PK ORDER BY ut_puestos.uf_Codigo";
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(consulta);
			while (resultados.next()) {
				Empleado empleado = new Empleado();
				empleado.setSysPK(resultados.getInt(1));
				empleado.setCodigo(resultados.getString(2));
				empleado.setNombre(resultados.getString(3));
				empleado.setPuestoFK(resultados.getInt(4));
				empleado.setCodigoPuesto(resultados.getString(5));
				arrayListEmpleado.add(empleado);
			} // FIN WHILE
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		} // FIN TRY/CATH
		return arrayListEmpleado;
	}// FIN METODO

	// METODO PARA OBTENER UN REGISTRO
	public static Empleado readEmpleado(Connection connection, int sysPK) {
		Empleado empleado = new Empleado();
		String consulta = "SELECT Sys_PK, uf_Codigo, uf_Nombre, uf_PuestoFK from ut_empleados WHERE Sys_PK = " + sysPK;
		try {
			Statement sentencia = connection.createStatement();
			java.sql.ResultSet resultados = sentencia.executeQuery(consulta);
			while (resultados.next()) {
				empleado.setSysPK(resultados.getInt(1));
				empleado.setCodigo(resultados.getString(2));
				empleado.setNombre(resultados.getString(3));
				empleado.setPuestoFK(resultados.getInt(4));
			} // FIN WHILE
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		} // FIN TRY/CATCH
		return empleado;
	}// FIN METODO

	// METODO PARA OBTENER UN REGISTRO
	public static ArrayList<Empleado> readEmpleadoLike(Connection connection, String like) {
		ArrayList<Empleado> arrayListaEmpleado = new ArrayList<Empleado>();
		String consulta = "SELECT ut_empleados.Sys_PK, ut_empleados.uf_Codigo, ut_empleados.uf_Nombre, ut_puestos.uf_Codigo FROM ut_empleados INNER JOIN ut_puestos ON ut_empleados.uf_PuestoFK = ut_puestos.Sys_PK  WHERE ut_empleados.uf_Nombre LIKE '%" + like + "%' OR ut_empleados.uf_Codigo LIKE '%" + like + "%'";
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(consulta);
			while (resultados.next()) {
				Empleado empleado = new Empleado();
				empleado.setSysPK(resultados.getInt(1));
				empleado.setCodigo(resultados.getString(2));
				empleado.setNombre(resultados.getString(3));
				empleado.setCodigoPuesto(resultados.getString(4));
				arrayListaEmpleado.add(empleado);
			} // FIN WHILE
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		} // FIN TRY/CATCH
		return arrayListaEmpleado;
	}// FIN METODO

	// METODO PARA OBTENER EMPLEADOS SIN USUARIO
	public static ArrayList<String> readEmpleadosSinUsuario(Connection connection) {
		ArrayList<String> arrayListaEmpleado = new ArrayList<String>();
		String consulta = "SELECT ut_empleados.uf_Nombre FROM ut_empleados  LEFT JOIN ut_usuarios ON ut_usuarios.uf_EmpleadoFK = ut_empleados.Sys_PK WHERE ut_usuarios.uf_EmpleadoFK IS NULL";
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(consulta);
			while (resultados.next()) {
				arrayListaEmpleado.add(resultados.getString(1));
			} // FIN WHILE
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		} // FIN TRY/CATCH
		return arrayListaEmpleado;
	}// FIN METODO

	// METODO PARA OBTENER UN EMPLEADO POR NOMBRE
	public static Empleado readEmpleadoPorNombre(Connection connection, String nombre) {
		Empleado empleado = new Empleado();
		String consulta = "SELECT Sys_PK, uf_Codigo, uf_Nombre, uf_PuestoFK FROM ut_empleados WHERE uf_Nombre = '"+ nombre +"'";
		try {
			Statement sentencia = connection.createStatement();
			java.sql.ResultSet resultados = sentencia.executeQuery(consulta);
			while (resultados.next()) {
				empleado.setSysPK(resultados.getInt(1));
				empleado.setCodigo(resultados.getString(2));
				empleado.setNombre(resultados.getString(3));
				empleado.setPuestoFK(resultados.getInt(4));
			} // FIN WHILE
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		} // FIN TRY/CATCH
		return empleado;
	}// FIN METODO

	// METODO PARA EDITAR UN REGISTRO
	public static boolean updateEmpleado(Connection connection, Empleado empleado) {
		String consulta = "UPDATE ut_empleados SET uf_Codigo = ?, uf_Nombre = ?, uf_PuestoFK = ? WHERE Sys_PK = ?";
		try {
			PreparedStatement sentenciaPreparada = connection.prepareStatement(consulta);
			sentenciaPreparada.setString(1, empleado.getCodigo());
			sentenciaPreparada.setString(2, empleado.getNombre());
			sentenciaPreparada.setInt(3, empleado.getPuestoFK());
			sentenciaPreparada.setInt(4, empleado.getSysPK());
			sentenciaPreparada.execute();
			return true;
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
			return false;
		} // FIN TRY/CATCH
	}// FIN METODO

	// METODO PARA ELIMINAR UN REGISTRO
	public static boolean deleteEmpleado(Connection connection, Empleado empleado) {
		String consulta = "DELETE FROM ut_empleados WHERE Sys_PK = ?";
		try {
			PreparedStatement sentenciaPreparada = connection.prepareStatement(consulta);
			sentenciaPreparada.setInt(1, empleado.getSysPK());
			sentenciaPreparada.execute();
			return true;
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
			return false;
		} // FIN TRY/CATCH
	}// FIN METODO

}
