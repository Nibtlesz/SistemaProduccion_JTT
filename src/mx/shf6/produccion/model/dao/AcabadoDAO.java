package mx.shf6.produccion.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import mx.shf6.produccion.model.Acabado;
import mx.shf6.produccion.utilities.Notificacion;

public class AcabadoDAO{

	//METODO PARA CREAR UN REGISTRO
	public static boolean createAcabado(Connection connection, Acabado acabado) {
		String consulta = "INSERT INTO acabado (Codigo, Descripcion, Status) VALUES (?, ?, ?)";
		try {
			PreparedStatement sentenciaPreparada = connection.prepareStatement(consulta);
			sentenciaPreparada.setString(1, acabado.getCodigo());
			sentenciaPreparada.setString(2, acabado.getDescripcion());
			sentenciaPreparada.setInt(3,  acabado.getStatusFK());
			sentenciaPreparada.execute();
			return true;
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
			return false;
		}//FIN TRY/CATCH
	}//FIN METODO	
	
	//METODO PARA OBTENER UN REGISTRO
	public static ArrayList<Acabado> readAcabado(Connection connection) {
		ArrayList<Acabado> arrayListAcabado = new ArrayList<Acabado>();
		String consulta = "SELECT Sys_PK, Codigo, Descripcion, Status FROM acabado";
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(consulta);
			while (resultados.next()) {
				Acabado acabado = new Acabado();
				acabado.setSysPK(resultados.getInt(1));
				acabado.setCodigo(resultados.getString(2));
				acabado.setDescripcion(resultados.getString(3));
				acabado.setStatus(resultados.getInt(4));
				arrayListAcabado.add(acabado);
			}//FIN WHILE
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY/CATCH
		return arrayListAcabado;
	}//FIN METODO	
	
	//METODO PARA OBTENER UN REGISTRO
	public static Acabado readAcabado(Connection connection, int sysPK) {
		Acabado acabado = new Acabado();
		String consulta = "SELECT Sys_PK, Codigo, Descripcion, Status from acabado WHERE Sys_PK = " + sysPK;
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(consulta);
			while (resultados.next()) {
				acabado.setSysPK(resultados.getInt(1));
				acabado.setCodigo(resultados.getString(2));
				acabado.setDescripcion(resultados.getString(3));
				acabado.setStatus(resultados.getInt(4));
			}//FIN WHILE
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY/CATCH
		return acabado;
	}//FIN METODO	
	
	//METODO PARA OBTENER UN REGISTRO
	public static ArrayList<Acabado> readAcabado(Connection connection, String like) {
		ArrayList<Acabado> arrayListaAcabado = new ArrayList<Acabado>();
		String consulta = "SELECT Sys_PK, Codigo, Descripcion, Status FROM acabado WHERE Codigo LIKE '%" + like + "%' OR Descripcion LIKE '%" + like + "%';";
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(consulta);
			while (resultados.next()) {
				Acabado acabado = new Acabado();
				acabado.setSysPK(resultados.getInt(1));
				acabado.setCodigo(resultados.getString(2));
				acabado.setDescripcion(resultados.getString(3));
				acabado.setStatus(resultados.getInt(4));
				arrayListaAcabado.add(acabado);
			}//FIN WHILE
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY/CATCH
		return arrayListaAcabado;
	}//FIN METODO
	
	//METODO PARA CREAR UN REGISTRO
	public static boolean updateAcabado(Connection connection, Acabado acabado) {
		String consulta = "UPDATE acabado SET Codigo = ?, Descripcion = ?, Status = ? WHERE Sys_PK = ?";
		try {
			PreparedStatement sentenciaPreparada = connection.prepareStatement(consulta);
			sentenciaPreparada.setString(1, acabado.getCodigo());
			sentenciaPreparada.setString(2, acabado.getDescripcion());
			sentenciaPreparada.setInt(3,  acabado.getStatusFK());
			sentenciaPreparada.setInt(4, acabado.getSysPK());
			sentenciaPreparada.execute();
			return true;
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
			return false;
		}//FIN TRY/CATCH
	}//FIN METODO
	
	//METODO PARA CREAR UN REGISTRO
	public static boolean deleteAcabado(Connection connection, Acabado acabado) {
		String consulta = "DELETE FROM acabado WHERE Sys_PK = ?";
		try {
			PreparedStatement sentenciaPreparada = connection.prepareStatement(consulta);
			sentenciaPreparada.setInt(1, acabado.getSysPK());
			sentenciaPreparada.execute();
			return true;
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
			return false;
		}//FIN TRY/CATCH
	}//FIN METODO
	
	//METODO PARA CONVERTIR ARRAYLIST EN OBSERVABLELIST
	public static ObservableList<Acabado> toObservableList(ArrayList<Acabado> arrayList) {
		ObservableList<Acabado> listaObservableAcabado = FXCollections.observableArrayList();
		for (Acabado venta : arrayList) 
			listaObservableAcabado.add(venta);
		return listaObservableAcabado;
	}//FIN METODO
	
	public static ObservableList<String> listaTiposAcabado(Connection connection) {
		ObservableList<String> listaTiposAcabado = FXCollections.observableArrayList();
		String consulta = "SELECT Descripcion FROM acabado ORDER BY Descripcion ASC";
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(consulta);
			while (resultados.next()) {
				listaTiposAcabado.add(resultados.getString(1));
			}//FIN WHILE
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY/CATCH
		return listaTiposAcabado;
	}//FIN METODO

}//FIN CLASE