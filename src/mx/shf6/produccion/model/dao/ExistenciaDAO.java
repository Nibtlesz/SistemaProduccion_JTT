package mx.shf6.produccion.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import mx.shf6.produccion.model.Existencia;
import mx.shf6.produccion.utilities.Notificacion;

public class ExistenciaDAO {

	// METODO PARA CREAR UN REGISTRO
	public static final boolean create(Connection connection, Existencia existencia) {
		String consulta = "INSERT INTO existencias (Existencia, ComponenteFK, AlmacenFK) VALUES (?, ?, ?)";
		try {
			PreparedStatement sentenciaPreparada = connection.prepareStatement(consulta);
			sentenciaPreparada.setDouble(1, existencia.getExistencia());
			sentenciaPreparada.setInt(2, existencia.getComponenteFK());
			sentenciaPreparada.setInt(3, existencia.getAlmacenFK() );
			sentenciaPreparada.execute();
			return true;
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
			return false;
		} // FIN TRY-CATCH
	}// FIN METODO

	// METODO PARA LEER UN REGISTRO POR SYSPK
	public static final Existencia readPorSysPK(Connection connection, int sysPK) {
		String consulta = "SELECT Sys_PK, Existencia, ComponenteFK, AlmacenFK  FROM existencias WHERE Sys_PK = " + sysPK;
		Existencia existencia = new Existencia();
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(consulta);
			while (resultados.next()) {
				existencia.setSysPK(resultados.getInt(1));
				existencia.setExistencia(resultados.getDouble(2));
				existencia.setComponenteFK(resultados.getInt(3));
				existencia.setAlmacenFK(resultados.getInt(4));
			} // FIN WHILE
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		} // FIN TRY-CATCH
		return existencia;
	}// FIN METODO

	public static final Existencia readExistencia(Connection connection, int componenteFK, int almacenFK) {
		String consulta = "SELECT Sys_PK, Existencia, ComponenteFK, AlmacenFK  FROM existencias WHERE ComponenteFK = " + componenteFK + " AND AlmacenFK = " + almacenFK;
		Existencia existencia = new Existencia();
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(consulta);
			while (resultados.next()) {
				existencia.setSysPK(resultados.getInt(1));
				existencia.setExistencia(resultados.getDouble(2));
				existencia.setComponenteFK(resultados.getInt(3));
				existencia.setAlmacenFK(resultados.getInt(4));
			} // FIN WHILE
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		} // FIN TRY-CATCH
		return existencia;
	}// FIN METODO

	// METODO PARA LEER TODOS LOS REGISTROS
	public static final ArrayList<Existencia> readTodosPorAlmacen(Connection connection, String almacenDescripcion) {
		ArrayList<Existencia> arrayListExistencia = new ArrayList<Existencia>();
		String consulta = "SELECT existencias.Sys_PK, existencias.Existencia, existencias.ComponenteFK, existencias.AlmacenFK, componentes.NumeroParte, componentes.Descripcion, almacenes.Codigo, almacenes.Descripcion FROM existencias INNER JOIN componentes ON componentes.Sys_PK = existencias.ComponenteFK INNER JOIN almacenes ON almacenes.Sys_PK = existencias.AlmacenFK WHERE almacenes.Descripcion LIKE '%" + almacenDescripcion + "%'";
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(consulta);
			while (resultados.next()) {
				Existencia existencia = new Existencia();
				existencia.setSysPK(resultados.getInt(1));
				existencia.setExistencia(resultados.getDouble(2));
				existencia.setComponenteFK(resultados.getInt(3));
				existencia.setAlmacenFK(resultados.getInt(4));
				existencia.setNumeroComponente(resultados.getString(5));
				existencia.setDescripcionComponente(resultados.getString(6));
				existencia.setCodigoAlmacen(resultados.getString(7));
				existencia.setDescripcionAlmacen(resultados.getString(8));
				arrayListExistencia.add(existencia);
			} // FIN WHILE
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		} // FIN TRY-CATCH
		return arrayListExistencia;
	}// FIN METODO

	// METODO PARA LEER TODOS LOS REGISTROS PARECIDOS
	public static final ArrayList<Existencia> readTodosPorComponenteAlmacenParecido(Connection connection, String like, String almacenDescripcion) {
		ArrayList<Existencia> arrayListExistencia = new ArrayList<Existencia>();
		String consulta = "SELECT existencias.Sys_PK, existencias.Existencia, existencias.ComponenteFK, existencias.AlmacenFK, componentes.NumeroParte, componentes.Descripcion, almacenes.Codigo, almacenes.Descripcion FROM existencias INNER JOIN componentes ON componentes.Sys_PK = existencias.ComponenteFK INNER JOIN almacenes ON almacenes.Sys_PK = existencias.AlmacenFK WHERE almacenes.Descripcion LIKE '%" + almacenDescripcion + "%' AND (componentes.Descripcion  LIKE '%" + like + "%'  OR componentes.NumeroParte LIKE '%" + like + "%')";

		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(consulta);
			while (resultados.next()) {
				Existencia existencia = new Existencia();
				existencia.setSysPK(resultados.getInt(1));
				existencia.setExistencia(resultados.getDouble(2));
				existencia.setComponenteFK(resultados.getInt(3));
				existencia.setAlmacenFK(resultados.getInt(4));
				existencia.setNumeroComponente(resultados.getString(5));
				existencia.setDescripcionComponente(resultados.getString(6));
				existencia.setCodigoAlmacen(resultados.getString(7));
				existencia.setDescripcionAlmacen(resultados.getString(8));
				arrayListExistencia.add(existencia);
			} // FIN WHILE
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		} // FIN TRY-CATCH
		return arrayListExistencia;
	}// FIN METODO

	public static final Double readExistenciaComponente(Connection connection, int componenteFK, int almacenFK) {
		Double existenciaComponente = 0.0;
		String consulta = "SELECT Existencia FROM existencias WHERE ComponenteFK = "+ componenteFK +" AND AlmacenFK = "+ almacenFK;
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(consulta);
			while (resultados.next()) {
				existenciaComponente = resultados.getDouble(1);
			} // FIN WHILE
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		} // FIN TRY-CATCH
		return existenciaComponente;
	}// FIN METODO

	// METODO PARA ACTUALIZAR UN REGISTRO
	public static final boolean update(Connection connection, Existencia existencia) {
		String query = "UPDATE existencias SET Existencia = ?, ComponenteFK = ?, AlmacenFK = ? WHERE Sys_PK = ?";
		try {
			PreparedStatement sentenciaPreparada = connection.prepareStatement(query);
			sentenciaPreparada.setDouble(1, existencia.getExistencia());
			sentenciaPreparada.setDouble(2, existencia.getComponenteFK());
			sentenciaPreparada.setDouble(3, existencia.getAlmacenFK());
			sentenciaPreparada.setInt(4,  existencia.getSysPk());
			sentenciaPreparada.execute();
			return true;
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
			return false;
		} // FIN TRY-CATCH
	}// FIN METODO

	// METODO PARA ELIMINAR UN REGISTRO
	public static final boolean delete(Connection connection, Existencia existencia) {
		String query = "DELETE FROM existencias WHERE Sys_PK = ?";
		try {
			PreparedStatement sentenciaPreparada = connection.prepareStatement(query);
			sentenciaPreparada.setInt(1, existencia.getSysPk());
			sentenciaPreparada.execute();
			return true;
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
			return false;
		} // FIN TRY-CATCH
	}// FIN METODO

	// METODO PARA CONVERTIR UN ARRAYLIST EN UN OBSERVABLELIST
	public static final ObservableList<Existencia> toObservableList(ArrayList<Existencia> arrayListExistencia) {
		ObservableList<Existencia> listaObservableExistencia = FXCollections.observableArrayList();
		for (Existencia existencia : arrayListExistencia)
			listaObservableExistencia.add(existencia);
		return listaObservableExistencia;
	}// FIN METODO


	public static ObservableList<String> readNumeroParteComponente(Connection connection, String almacenOrigen) {
		ObservableList<String> observableLisNumeroParteComponente = FXCollections.observableArrayList();
		String consulta = "SELECT existencias.Sys_PK, existencias.Existencia, existencias.ComponenteFK, existencias.AlmacenFK, componentes.NumeroParte, componentes.Descripcion FROM existencias  INNER JOIN componentes ON componentes.Sys_PK = existencias.ComponenteFK INNER JOIN almacenes ON almacenes.Sys_PK = existencias.AlmacenFK  WHERE almacenes.Descripcion LIKE '%"+ almacenOrigen +"%'";
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(consulta);
			while (resultados.next()) {
				observableLisNumeroParteComponente.add(resultados.getString(5));
			}//FIN WHILE
		}  catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY/CATCH
		return observableLisNumeroParteComponente;
	}//FIN METODO

}
