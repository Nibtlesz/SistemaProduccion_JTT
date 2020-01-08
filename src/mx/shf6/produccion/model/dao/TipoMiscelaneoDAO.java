package mx.shf6.produccion.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import mx.shf6.produccion.model.TipoMiscelaneo;
import mx.shf6.produccion.utilities.Notificacion;

public class TipoMiscelaneoDAO {
	
	//METODO PARA CREAR UN REGISTRO
	public static boolean createTipoMiscelaneo(Connection connection, TipoMiscelaneo tipoMiscelaneo) {
		String consulta = "INSERT INTO tipomiscelaneos (Codigo, Descripcion, Status) VALUES (?, ?, ?)";
		try {
			PreparedStatement sentenciaPreparada = connection.prepareStatement(consulta);
			sentenciaPreparada.setString(1, tipoMiscelaneo.getCodigo());
			sentenciaPreparada.setString(2, tipoMiscelaneo.getDescripcion());
			sentenciaPreparada.setInt(3,  tipoMiscelaneo.getStatusFK());
			sentenciaPreparada.execute();
			return true;
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
			return false;
		}//FIN TRY/CATCH
	}//FIN METODO
	
	//METODO PARA OBTENER UN REGISTRO
	public static ArrayList<TipoMiscelaneo> readTipoMiscelaneo(Connection connection) {
		ArrayList<TipoMiscelaneo> arrayListTipoMiscelaneo = new ArrayList<TipoMiscelaneo>();
		String consulta = "SELECT Sys_PK, Codigo, Descripcion, Status FROM tipomiscelaneos";
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(consulta);
			while (resultados.next()) {
				TipoMiscelaneo tipoMiscelaneo = new TipoMiscelaneo();
				tipoMiscelaneo.setSysPK(resultados.getInt(1));
				tipoMiscelaneo.setCodigo(resultados.getString(2));
				tipoMiscelaneo.setDescripcion(resultados.getString(3));
				tipoMiscelaneo.setStatus(resultados.getInt(4));
				arrayListTipoMiscelaneo.add(tipoMiscelaneo);
			}//FIN WHILE
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY/CATCH
		return arrayListTipoMiscelaneo;
	}//FIN METODO
	
	//METODO PARA OBTENER UN REGISTRO
	public static TipoMiscelaneo readTipoMiscelaneo(Connection connection, int sysPK) {
		TipoMiscelaneo tipoMiscelaneo = new TipoMiscelaneo();
		String consulta = "SELECT Sys_PK, Codigo, Descripcion, Status from tipomiscelaneos WHERE Sys_PK = " + sysPK;
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(consulta);
			while (resultados.next()) {
				tipoMiscelaneo.setSysPK(resultados.getInt(1));
				tipoMiscelaneo.setCodigo(resultados.getString(2));
				tipoMiscelaneo.setDescripcion(resultados.getString(3));
				tipoMiscelaneo.setStatus(resultados.getInt(4));
			}//FIN WHILE
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY/CATCH
		return tipoMiscelaneo;
	}//FIN METODO
	
	//METODO PARA OBTENER UN REGISTRO
	public static ArrayList<TipoMiscelaneo> readTipoMiscelaneo(Connection connection, String like) {
		ArrayList<TipoMiscelaneo> arrayListTipoMiscelaneo = new ArrayList<TipoMiscelaneo>();
		String consulta = "SELECT Sys_PK, Codigo, Descripcion, Status FROM tipomiscelaneos WHERE Codigo LIKE '%" + like + "%' OR Descripcion LIKE '%" + like + "%';";
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(consulta);
			while (resultados.next()) {
				TipoMiscelaneo tipoMiscelaneo = new TipoMiscelaneo();
				tipoMiscelaneo.setSysPK(resultados.getInt(1));
				tipoMiscelaneo.setCodigo(resultados.getString(2));
				tipoMiscelaneo.setDescripcion(resultados.getString(3));
				tipoMiscelaneo.setStatus(resultados.getInt(4));
				arrayListTipoMiscelaneo.add(tipoMiscelaneo);
			}//FIN WHILE
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY/CATCH
		return arrayListTipoMiscelaneo;
	}//FIN METODO
	
	//METODO PARA CREAR UN REGISTRO
	public static boolean updateTipoMiscelaneo(Connection connection, TipoMiscelaneo tipoMiscelaneo) {
		String consulta = "UPDATE tipomiscelaneos SET Codigo = ?, Descripcion = ?, Status = ? WHERE Sys_PK = ?";
		try {
			PreparedStatement sentenciaPreparada = connection.prepareStatement(consulta);
			sentenciaPreparada.setString(1, tipoMiscelaneo.getCodigo());
			sentenciaPreparada.setString(2, tipoMiscelaneo.getDescripcion());
			sentenciaPreparada.setInt(3,  tipoMiscelaneo.getStatusFK());
			sentenciaPreparada.setInt(4, tipoMiscelaneo.getSysPK());
			sentenciaPreparada.execute();
			return true;
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
			return false;
		}//FIN TRY/CATCH 	
	}//FIN METODO
	
	//METODO PARA CREAR UN REGISTRO
	public static boolean deleteTipoMiscelaneo(Connection connection, TipoMiscelaneo tipoMiscelaneo) {
		String consulta = "DELETE FROM tipomiscelaneos WHERE Sys_PK = ?";
		try {
			PreparedStatement sentenciaPreparada = connection.prepareStatement(consulta);
			sentenciaPreparada.setInt(1, tipoMiscelaneo.getSysPK());
			sentenciaPreparada.execute();
			return true;
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
			return false;
		}//FIN TRY/CATCH
	}//FIN METODO
	
	//METODO PARA CONVERTIR ARRAYLIST EN OBSERVABLELIST
	public static ObservableList<TipoMiscelaneo> toObservableList(ArrayList<TipoMiscelaneo> arrayList) {
		ObservableList<TipoMiscelaneo> listaObservableTipoMiscelaneo = FXCollections.observableArrayList();
		for (TipoMiscelaneo tipoMiscelaneo : arrayList) 
			listaObservableTipoMiscelaneo.add(tipoMiscelaneo);
		return listaObservableTipoMiscelaneo;
	}//FIN METODO

	public static ObservableList<String> listaTiposMiscelaneo(Connection connection) {
		ObservableList<String> listaTiposMiscelaneo = FXCollections.observableArrayList();
		String consulta = "SELECT Descripcion FROM tipomiscelaneos ORDER BY Descripcion ASC";
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(consulta);
			while (resultados.next()) {
				listaTiposMiscelaneo.add(resultados.getString(1));
			}//FIN WHILE
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY/CATCH
		return listaTiposMiscelaneo;
	}//FIN METODO
	
}//FIN CLASE