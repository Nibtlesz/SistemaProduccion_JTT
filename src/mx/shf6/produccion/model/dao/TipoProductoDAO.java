package mx.shf6.produccion.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import mx.shf6.produccion.model.TipoProducto;
import mx.shf6.produccion.utilities.Notificacion;

public class TipoProductoDAO {

	//METODO PARA CREAR UN REGISTRO
	public static boolean createTipoProducto(Connection connection, TipoProducto tipoProducto) {
		String consulta = "INSERT INTO tipoproducto (Codigo, Descripcion, Status) VALUES (?, ?, ?)";
		try {
			PreparedStatement sentenciaPreparada = connection.prepareStatement(consulta);
			sentenciaPreparada.setString(1, tipoProducto.getCodigo());
			sentenciaPreparada.setString(2, tipoProducto.getDescripcion());
			sentenciaPreparada.setInt(3,  tipoProducto.getStatusFK());
			sentenciaPreparada.execute();
			return true;
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
			return false;
		}//FIN TRY/CATCH
	}//FIN METODO
	
	//METODO PARA OBTENER UN REGISTRO
	public static ArrayList<TipoProducto> readTipoProducto(Connection connection) {
		ArrayList<TipoProducto> arrayListTipoProducto = new ArrayList<TipoProducto>();
		String consulta = "SELECT Sys_PK, Codigo, Descripcion, Status FROM tipoproducto";
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(consulta);
			while (resultados.next()) {
				TipoProducto tipoProducto = new TipoProducto();
				tipoProducto.setSysPK(resultados.getInt(1));
				tipoProducto.setCodigo(resultados.getString(2));
				tipoProducto.setDescripcion(resultados.getString(3));
				tipoProducto.setStatus(resultados.getInt(4));
				arrayListTipoProducto.add(tipoProducto);
			}//FIN WHILE
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY/CATCH
		return arrayListTipoProducto;
	}//FIN METODO
	
	//METODO PARA OBTENER UN REGISTRO
	public static TipoProducto readTipoProducto(Connection connection, int sysPK) {
		TipoProducto tipoProducto = new TipoProducto();
		String consulta = "SELECT Sys_PK, Codigo, Descripcion, Status FROM tipoproducto WHERE Sys_PK = " + sysPK;
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(consulta);
			while (resultados.next()) {
				tipoProducto.setSysPK(resultados.getInt(1));
				tipoProducto.setCodigo(resultados.getString(2));
				tipoProducto.setDescripcion(resultados.getString(3));
				tipoProducto.setStatus(resultados.getInt(4));
			}//FIN WHILE
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY/CATCH
		return tipoProducto;
	}//FIN METODO
	
	//METODO PARA OBTENER UN REGISTRO
	public static ArrayList<TipoProducto> readTipoProducto(Connection connection, String like) {
		ArrayList<TipoProducto> arrayListTipoProducto = new ArrayList<TipoProducto>();
		String consulta = "SELECT Sys_PK, Codigo, Descripcion, Status FROM tipoproducto WHERE Codigo LIKE '%" + like + "%' OR Descripcion LIKE '% " + like + " %'";
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(consulta);
			while (resultados.next()) {
				TipoProducto tipoProducto = new TipoProducto();
				tipoProducto.setSysPK(resultados.getInt(1));
				tipoProducto.setCodigo(resultados.getString(2));
				tipoProducto.setDescripcion(resultados.getString(3));
				tipoProducto.setStatus(resultados.getInt(4));
				arrayListTipoProducto.add(tipoProducto);
			}//FIN WHILE
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY/CATCH
		return arrayListTipoProducto;
	}//FIN METODO
	
	//METODO PARA CREAR UN REGISTRO
	public static boolean updateTipoProducto(Connection connection, TipoProducto tipoProducto) {
		String consulta = "UPDATE tipoproducto SET Codigo = ?, Descripcion = ?, Status = ? WHERE Sys_PK = ?";
		try {
			PreparedStatement sentenciaPreparada = connection.prepareStatement(consulta);
			sentenciaPreparada.setString(1, tipoProducto.getCodigo());
			sentenciaPreparada.setString(2, tipoProducto.getDescripcion());
			sentenciaPreparada.setInt(3,  tipoProducto.getStatusFK());
			sentenciaPreparada.setInt(4, tipoProducto.getSysPK());
			sentenciaPreparada.execute();
			return true;
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
			return false;
		}//FIN TRY/CATCH
	}//FIN METODO
	
	//METODO PARA CREAR UN REGISTRO
	public static boolean deleteTipoProducto(Connection connection, TipoProducto tipoProducto) {
		String consulta = "DELETE FROM tipoproducto WHERE Sys_PK = ?";
		try {
			PreparedStatement sentenciaPreparada = connection.prepareStatement(consulta);
			sentenciaPreparada.setInt(1, tipoProducto.getSysPK());
			sentenciaPreparada.execute();
			return true;
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
			return false;
		}//FIN TRY/CATCH
	}//FIN METODO
	
	//METODO PARA CONVERTIR ARRAYLIST EN OBSERVABLELIST
	public static ObservableList<TipoProducto> toObservableList(ArrayList<TipoProducto> arrayList) {
		ObservableList<TipoProducto> listaObservableTipoProducto = FXCollections.observableArrayList();
		for (TipoProducto tipoProducto : arrayList) 
			listaObservableTipoProducto.add(tipoProducto);
		return listaObservableTipoProducto;
	}//FIN METODO
	
}//FIN CLASE