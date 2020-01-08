package mx.shf6.produccion.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import mx.shf6.produccion.utilities.Notificacion;
import mx.shf6.produccion.model.Almacen;


public class AlmacenDAO {

	// METODO PARA CREAR UN REGISTRO
	public static final boolean create(Connection connection, Almacen almacen) {
		String consulta = "INSERT INTO almacenes (Codigo, Descripcion) VALUES (?, ?)";
		try {
			PreparedStatement sentenciaPreparada = connection.prepareStatement(consulta);
			sentenciaPreparada.setString(1, almacen.getCodigo());
			sentenciaPreparada.setString(2, almacen.getDescripcion());
			sentenciaPreparada.execute();
			return true;
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
			return false;
		} // FIN TRY-CATCH
	}// FIN METODO

	// METODO PARA LEER UN REGISTRO POR SYSPK
	public static final Almacen readPorSysPK(Connection connection, int sysPK) {
		String consulta = "SELECT Sys_PK, Codigo, Descripcion FROM almacenes WHERE Sys_PK = " + sysPK;
		Almacen almacen = new Almacen();
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(consulta);
			while (resultados.next()) {
				almacen.setSysPK(resultados.getInt(1));
				almacen.setCodigo(resultados.getString(2));
				almacen.setDescripcion(resultados.getString(3));
			} // FIN WHILE
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		} // FIN TRY-CATCH
		return almacen;
	}// FIN METODO

	public static final Almacen readPorDescripcion(Connection connection, String  descripcion) {
		String consulta = "SELECT Sys_PK, Codigo, Descripcion FROM almacenes WHERE Descripcion = '"+ descripcion +"'";
		Almacen almacen = new Almacen();
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(consulta);
			while (resultados.next()) {
				almacen.setSysPK(resultados.getInt(1));
				almacen.setCodigo(resultados.getString(2));
				almacen.setDescripcion(resultados.getString(3));
			} // FIN WHILE
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		} // FIN TRY-CATCH
		return almacen;
	}// FIN METODO

	// METODO PARA LEER TODOS LOS REGISTROS
	public static final ArrayList<Almacen> readTodos(Connection connection) {
		ArrayList<Almacen> arrayListAlmacen = new ArrayList<Almacen>();
		String consulta = "SELECT Sys_PK, Codigo, Descripcion FROM almacenes";
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(consulta);
			while (resultados.next()) {
				Almacen almacen = new Almacen();
				almacen.setSysPK(resultados.getInt(1));
				almacen.setCodigo(resultados.getString(2));
				almacen.setDescripcion(resultados.getString(3));
				arrayListAlmacen.add(almacen);
			} // FIN WHILE
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		} // FIN TRY-CATCH
		return arrayListAlmacen;
	}// FIN METODO

	// METODO PARA LEER TODOS LOS REGISTROS PARECIDOS
	public static final ArrayList<Almacen> readTodosParecidos(Connection connection, String like) {
		ArrayList<Almacen> arrayListAlmacen = new ArrayList<Almacen>();
		String consulta = "SELECT Sys_PK, Codigo, Descripcion FROM almacenes WHERE Codigo LIKE '%" + like + "%' OR Descripcion LIKE '%" + like + "%'";
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(consulta);
			while (resultados.next()) {
				Almacen almacen = new Almacen();
				almacen.setSysPK(resultados.getInt(1));
				almacen.setCodigo(resultados.getString(2));
				almacen.setDescripcion(resultados.getString(3));
				arrayListAlmacen.add(almacen);
			} // FIN WHILE
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		} // FIN TRY-CATCH
		return arrayListAlmacen;
	}// FIN METODO

	// METODO PARA ACTUALIZAR UN REGISTRO
	public static final boolean update(Connection connection, Almacen almacen) {
		String query = "UPDATE almacenes SET Codigo = ?, Descripcion = ?  WHERE Sys_PK = ?";
		try {
			PreparedStatement sentenciaPreparada = connection.prepareStatement(query);
			sentenciaPreparada.setString(1, almacen.getCodigo());
			sentenciaPreparada.setString(2, almacen.getDescripcion());
			sentenciaPreparada.setInt(3, almacen.getSysPK());
			sentenciaPreparada.execute();
			return true;
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
			return false;
		} // FIN TRY-CATCH
	}// FIN METODO

	// METODO PARA ELIMINAR UN REGISTRO
	public static final boolean delete(Connection connection, Almacen almacen) {
		String query = "DELETE FROM almacenes WHERE Sys_PK = ?";
		try {
			PreparedStatement sentenciaPreparada = connection.prepareStatement(query);
			sentenciaPreparada.setInt(1, almacen.getSysPK());
			sentenciaPreparada.execute();
			return true;
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
			return false;
		} // FIN TRY-CATCH
	}// FIN METODO

	// METODO PARA CONVERTIR UN ARRAYLIST EN UN OBSERVABLELIST
	public static final ObservableList<Almacen> toObservableList(ArrayList<Almacen> arrayListAlmacen) {
		ObservableList<Almacen> listaObservableAlmacen = FXCollections.observableArrayList();
		for (Almacen almacen : arrayListAlmacen)
			listaObservableAlmacen.add(almacen);
		return listaObservableAlmacen;
	}// FIN METODO

	public static ObservableList<String> readDescripcion(Connection connection) {
		ObservableList<String> observableListAlmacen = FXCollections.observableArrayList();
		String consulta = "SELECT Descripcion FROM almacenes ORDER BY Descripcion ASC";
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(consulta);
			while (resultados.next()) {
				observableListAlmacen.add(resultados.getString(1));
			}//FIN WHILE
		}  catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY/CATCH
		return observableListAlmacen;
	}//FIN METODO

}//FIN CLASE
