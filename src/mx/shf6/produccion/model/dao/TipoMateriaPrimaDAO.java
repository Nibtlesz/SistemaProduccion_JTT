package mx.shf6.produccion.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import mx.shf6.produccion.model.TipoMateriaPrima;
import mx.shf6.produccion.utilities.Notificacion;

public class TipoMateriaPrimaDAO {

	//METODO PARA CREAR UN REGISTRO
	public static boolean createTipoMateriaPrima(Connection connection, TipoMateriaPrima tipoMateriaPrima) {
		String consulta = "INSERT INTO tipomateriaprima (Codigo, Descripcion, Status) VALUES (?, ?, ?)";
		try {
			PreparedStatement sentenciaPreparada = connection.prepareStatement(consulta);
			sentenciaPreparada.setString(1, tipoMateriaPrima.getCodigo());
			sentenciaPreparada.setString(2, tipoMateriaPrima.getDescripcion());
			sentenciaPreparada.setInt(3,  tipoMateriaPrima.getStatusFK());
			sentenciaPreparada.execute();
			return true;
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
			return false;
		}//FIN TRY/CATCH
	}//FIN METODO
	
	//METODO PARA OBTENER UN REGISTRO
	public static ArrayList<TipoMateriaPrima> readTipoMateriaPrima(Connection connection) {
		ArrayList<TipoMateriaPrima> arrayListTipoMateriaPrima = new ArrayList<TipoMateriaPrima>();
		String consulta = "SELECT Sys_PK, Codigo, Descripcion, Status FROM tipomateriaprima";
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(consulta);
			while (resultados.next()) {
				TipoMateriaPrima tipoMateriaPrima = new TipoMateriaPrima();
				tipoMateriaPrima.setSysPK(resultados.getInt(1));
				tipoMateriaPrima.setCodigo(resultados.getString(2));
				tipoMateriaPrima.setDescripcion(resultados.getString(3));
				tipoMateriaPrima.setStatus(resultados.getInt(4));
				arrayListTipoMateriaPrima.add(tipoMateriaPrima);
			}//FIN WHILE
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY/CATCH
		return arrayListTipoMateriaPrima;
	}//FIN METODO
	
	//METODO PARA OBTENER UN REGISTRO
	public static TipoMateriaPrima readTipoMateriaPrima(Connection connection, int sysPK) {
		TipoMateriaPrima tipoMateriaPrima = new TipoMateriaPrima();
		String consulta = "SELECT Sys_PK, Codigo, Descripcion, Status from tipomateriaprima WHERE Sys_PK = " + sysPK;
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(consulta);
			while (resultados.next()) {
				tipoMateriaPrima.setSysPK(resultados.getInt(1));
				tipoMateriaPrima.setCodigo(resultados.getString(2));
				tipoMateriaPrima.setDescripcion(resultados.getString(3));
				tipoMateriaPrima.setStatus(resultados.getInt(4));
			}//FIN WHILE
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY/CATCH
		return tipoMateriaPrima;
	}//FIN METODO
	
	//METODO PARA OBTENER UN REGISTRO
	public static ArrayList<TipoMateriaPrima> readTipoMateriaPrima(Connection connection, String like) {
		ArrayList<TipoMateriaPrima> arrayListTipoMateriaPrima = new ArrayList<TipoMateriaPrima>();
		String consulta = "SELECT Sys_PK, Codigo, Descripcion, Status FROM tipomateriaprima WHERE Codigo LIKE '%" + like + "%' OR Descripcion LIKE '%" + like + "%'";
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(consulta);
			while (resultados.next()) {
				TipoMateriaPrima tipoMateriaPrima = new TipoMateriaPrima();
				tipoMateriaPrima.setSysPK(resultados.getInt(1));
				tipoMateriaPrima.setCodigo(resultados.getString(2));
				tipoMateriaPrima.setDescripcion(resultados.getString(3));
				tipoMateriaPrima.setStatus(resultados.getInt(4));
				arrayListTipoMateriaPrima.add(tipoMateriaPrima);
			}//FIN WHILE
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY/CATCH
		return arrayListTipoMateriaPrima;
	}//FIN METODO
	
	//METODO PARA CREAR UN REGISTRO
	public static boolean updateTipoMateriaPrima(Connection connection, TipoMateriaPrima tipoMateriaPrima) {
		String consulta = "UPDATE tipomateriaprima SET Codigo = ?, Descripcion = ?, Status = ? WHERE Sys_PK = ?";
		try {
			PreparedStatement sentenciaPreparada = connection.prepareStatement(consulta);
			sentenciaPreparada.setString(1, tipoMateriaPrima.getCodigo());
			sentenciaPreparada.setString(2, tipoMateriaPrima.getDescripcion());
			sentenciaPreparada.setInt(3,  tipoMateriaPrima.getStatusFK());
			sentenciaPreparada.setInt(4, tipoMateriaPrima.getSysPK());
			sentenciaPreparada.execute();
			return true;
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
			return false;
		}//FIN TRY/CATCH
	}//FIN METODO
	
	//METODO PARA CREAR UN REGISTRO
	public static boolean deleteTipoMateriaPrima(Connection connection, TipoMateriaPrima tipoMateriaPrima) {
		String consulta = "DELETE FROM tipomateriaprima WHERE Sys_PK = ?";
		try {
			PreparedStatement sentenciaPreparada = connection.prepareStatement(consulta);
			sentenciaPreparada.setInt(1, tipoMateriaPrima.getSysPK());
			sentenciaPreparada.execute();
			return true;
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
			return false;
		}//FIN TRY/CATCH
	}//FIN METODO
	
	//METODO PARA CONVERTIR ARRAYLIST EN OBSERVABLELIST
	public static ObservableList<TipoMateriaPrima> toObservableList(ArrayList<TipoMateriaPrima> arrayList) {
		ObservableList<TipoMateriaPrima> listaObservableTipoMateriaPrima = FXCollections.observableArrayList();
		for (TipoMateriaPrima venta : arrayList) 
			listaObservableTipoMateriaPrima.add(venta);
		return listaObservableTipoMateriaPrima;
	}//FIN METODO
	
	public static ObservableList<String> listaTiposMateriaPrima(Connection connection) {
		ObservableList<String> listaTiposMateriaPrima = FXCollections.observableArrayList();
		String consulta = "SELECT Descripcion FROM tipomateriaprima ORDER BY Descripcion ASC";
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(consulta);
			while (resultados.next()) {
				listaTiposMateriaPrima.add(resultados.getString(1));
			}//FIN WHILE
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY/CATCH
		return listaTiposMateriaPrima;
	}//FIN METODO
	
}//FIN METODO