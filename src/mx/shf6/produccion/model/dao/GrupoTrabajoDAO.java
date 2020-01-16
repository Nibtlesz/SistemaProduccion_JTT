package mx.shf6.produccion.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import mx.shf6.produccion.model.GrupoTrabajo;
import mx.shf6.produccion.utilities.Notificacion;



public class GrupoTrabajoDAO {
	// METODO PARA CREAR UN REGISTRO
	public static boolean createGrupoTrabajo(Connection connection, GrupoTrabajo grupoTrabajo) {
		String consulta = "INSERT INTO ut_grupotrabajo (uf_Codigo, uf_Descripcion) VALUES (?, ?)";
		try {
			PreparedStatement sentenciaPreparada = connection.prepareStatement(consulta);
			sentenciaPreparada.setString(1, grupoTrabajo.getCodigo());
			sentenciaPreparada.setString(2, grupoTrabajo.getDescripcion());
			sentenciaPreparada.execute();
			return true;
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
			return false;
		} // FIN TRY/CATCH
	}// FIN METODO 

	// METODO PARA OBTENER UN REGISTRO
	public static ArrayList<GrupoTrabajo> readGrupoTrabajo(Connection connection) {
		ArrayList<GrupoTrabajo> arrayListGrupoTrabajo = new ArrayList<GrupoTrabajo>();
		String consulta = "SELECT Sys_PK, uf_Codigo, uf_Descripcion FROM ut_grupotrabajo ORDER BY uf_Codigo ASC";
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(consulta);
			while (resultados.next()) {
				GrupoTrabajo grupotrabajo = new GrupoTrabajo();
				grupotrabajo.setSysPK(resultados.getInt(1));
				grupotrabajo.setCodigo(resultados.getString(2));
				grupotrabajo.setDescripcion(resultados.getString(3));
				arrayListGrupoTrabajo.add(grupotrabajo);
			} // FIN WHILE
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		} // FIN TRY/CATCH
		return arrayListGrupoTrabajo;
	}// FIN METODO

	// METODO PARA OBTENER UN REGISTRO
	public static GrupoTrabajo readGrupoTrabajo(Connection connection, int sysPK) {
		GrupoTrabajo grupotrabajo = new GrupoTrabajo();
		String consulta = "SELECT Sys_PK, uf_Codigo, uf_Descripcion FROM ut_grupostrabajo WHERE Sys_PK = " + sysPK ;
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(consulta);
			while (resultados.next()) {
				grupotrabajo.setSysPK(resultados.getInt(1));
				grupotrabajo.setCodigo(resultados.getString(2));
				grupotrabajo.setDescripcion(resultados.getString(3));

			} // FIN WHILE
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		} // FIN TRY/CATCH
		return grupotrabajo;
	}// FIN METODO
	
	// METODO PARA OBTENER UN REGISTRO
	public static GrupoTrabajo readGrupoTrabajoNombre(Connection connection, String nombre) {
		GrupoTrabajo grupotrabajo = new GrupoTrabajo();
		String consulta = "SELECT Sys_PK, uf_Codigo, uf_Descripcion FROM ut_grupostrabajo WHERE uf_Codigo = '" + nombre + "'" ;
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(consulta);
			while (resultados.next()) {
				grupotrabajo.setSysPK(resultados.getInt(1));
				grupotrabajo.setCodigo(resultados.getString(2));
				grupotrabajo.setDescripcion(resultados.getString(3));

			} // FIN WHILE
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		} // FIN TRY/CATCH
		return grupotrabajo;
	}// FIN METODO

	// METODO PARA OBTENER UN REGISTRO
	public static ArrayList<GrupoTrabajo> readGrupoTrabajoLike(Connection connection, String like) {
		ArrayList<GrupoTrabajo> arrayListaGrupoTrabajo = new ArrayList<GrupoTrabajo>();
		String consulta = "SELECT Sys_PK, uf_Codigo, uf_Descripcion FROM ut_grupostrabajo WHERE uf_Codigo LIKE '%" + like + "%' OR uf_Descripcion LIKE '%" + like + "%'";
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(consulta);
			while (resultados.next()) {
				GrupoTrabajo grupos = new GrupoTrabajo();
				grupos.setSysPK(resultados.getInt(1));
				grupos.setCodigo(resultados.getString(2));
				grupos.setDescripcion(resultados.getString(3));
				arrayListaGrupoTrabajo.add(grupos);
			} // FIN WHILE
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		} // FIN TRY/CATCH
		return arrayListaGrupoTrabajo;
	}// FIN METODO

	// METODO PARA EDITAR UN REGISTRO
	public static boolean updateGrupoTrabajo(Connection connection, GrupoTrabajo grupotrabajo) {
		String consulta = "UPDATE ut_grupostrabajo SET uf_Codigo = ?, uf_Descripcion = ? WHERE Sys_PK = ?";
		try {
			PreparedStatement sentenciaPreparada = connection.prepareStatement(consulta);
			sentenciaPreparada.setString(1, grupotrabajo.getCodigo());
			sentenciaPreparada.setString(2, grupotrabajo.getDescripcion());
			sentenciaPreparada.setInt(3, grupotrabajo.getSysPK());
			sentenciaPreparada.execute();
			return true;
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
			return false;
		} // FIN TRY/CATCH
	}// FIN METODO

	// METODO PARA ELIMINAR UN REGISTRO
	public static boolean deleteGrupoTrabajo(Connection connection, GrupoTrabajo grupotrabajo) {
		String consulta = "DELETE FROM ut_grupostrabajo WHERE Sys_PK = ?";
		try {
			PreparedStatement sentenciaPreparada = connection.prepareStatement(consulta);
			sentenciaPreparada.setInt(1, grupotrabajo.getSysPK());
			sentenciaPreparada.execute();
			return true;
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
			return false;
		} // FIN TRY/CATCH
	}// FIN METODO

}// FIN CLASE
