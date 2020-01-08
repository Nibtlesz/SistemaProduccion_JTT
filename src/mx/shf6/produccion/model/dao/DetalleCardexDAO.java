package mx.shf6.produccion.model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import mx.shf6.produccion.model.DetalleCardex;
import mx.shf6.produccion.utilities.Notificacion;

public class DetalleCardexDAO {
	
	//METODO PARA cREAR UN REGISTRO
	public static final boolean create(Connection connection, DetalleCardex detalleCardex) {
		String query = "INSERT INTO detallecardex (Entrada, Salida, CardexFK, AlmacenFK, ComponenteFK) VALUES (?, ?, ?, ?, ?)";
		try {
			PreparedStatement sentenciaPreparada = connection.prepareStatement(query);
			sentenciaPreparada.setDouble(1, detalleCardex.getEntrada());
			sentenciaPreparada.setDouble(2, detalleCardex.getSalida());
			sentenciaPreparada.setInt(3, detalleCardex.getCardexFK());
			sentenciaPreparada.setInt(4, detalleCardex.getAlmacenFK());
			sentenciaPreparada.setInt(5, detalleCardex.getComponenteFK());
			sentenciaPreparada.execute();
			return true;
		}catch (SQLException ex) {
			Notificacion.dialogoException(ex);
			return false;
		}//FIN TRY-CATCH
	}//FIN METODO
	
	//METODO PARA LEER REGISTROS
	public static final ArrayList<DetalleCardex> readTodos (Connection connection){
		ArrayList<DetalleCardex> arrayListDetalleCardex = new ArrayList<DetalleCardex>();
		String query = "SELECT Sys_PK, Entrada, Salida, CardexFK, AlmacenFK, ComponenteFK FROM detallecardex";
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(query);
			while(resultados.next()) {
				DetalleCardex detalleCardex = new DetalleCardex();
				detalleCardex.setSysPK(resultados.getInt(1));
				detalleCardex.setEntrada(resultados.getDouble(2));
				detalleCardex.setSalida(resultados.getDouble(3));
				detalleCardex.setCardexFK(resultados.getInt(4));
				detalleCardex.setAlmacenFK(resultados.getInt(5));
				detalleCardex.setComponenteFK(resultados.getInt(6));
				arrayListDetalleCardex.add(detalleCardex);
			}//FIN WHILE
		}catch(SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY-CATCH
		return arrayListDetalleCardex;
	}//FIN METODO
	
	//METODO PARA LEER UN REGISTRO POR SUS SYSPK
	public static final DetalleCardex readPorSysPK(Connection connection, int sysPK) {
		DetalleCardex detalleCardex =new DetalleCardex();
		String query = "SELECT Sys_PK, Entrada, Salida, CardexFK, AlmacenFK, ComponenteFK FROM detallecardex WHERE Sys_PK = " + sysPK;
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(query);
			while(resultados.next()) {
				detalleCardex.setSysPK(resultados.getInt(1));
				detalleCardex.setEntrada(resultados.getDouble(2));
				detalleCardex.setSalida(resultados.getDouble(3));
				detalleCardex.setCardexFK(resultados.getInt(4));
				detalleCardex.setAlmacenFK(resultados.getInt(5));
				detalleCardex.setComponenteFK(resultados.getInt(6));
			}//FIN WHILE
		}catch(SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY-CATCH
		return detalleCardex;
	}//FIN METODO
	
	//METODO PARA LEER REGISTROS POR ALMACEN
	public static final ArrayList<DetalleCardex> readPorAlmacen (Connection connection, int almacenFK){
		ArrayList<DetalleCardex> arrayListDetalleCardex = new ArrayList<DetalleCardex>();
		String query = "SELECT Sys_PK, Entrada, Salida, CardexFK, AlmacenFK, ComponenteFK FROM detallecardex WHERE AlmacenFK = " + almacenFK;
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(query);
			while(resultados.next()) {
				DetalleCardex detalleCardex = new DetalleCardex();
				detalleCardex.setSysPK(resultados.getInt(1));
				detalleCardex.setEntrada(resultados.getDouble(2));
				detalleCardex.setSalida(resultados.getDouble(3));
				detalleCardex.setCardexFK(resultados.getInt(4));
				detalleCardex.setAlmacenFK(resultados.getInt(5));
				detalleCardex.setComponenteFK(resultados.getInt(6));
				arrayListDetalleCardex.add(detalleCardex);
			}//FIN WHILE
		}catch(SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY-CATCH
		return arrayListDetalleCardex;
	}//FIN METODO
	
	//METODO PARA LEER REGISTROS POR COMPONENTE
	public static final ArrayList<DetalleCardex> readPorComponente (Connection connection, int componenteFK){
		ArrayList<DetalleCardex> arrayListDetalleCardex = new ArrayList<DetalleCardex>();
		String query = "SELECT Sys_PK, Entrada, Salida, CardexFK, AlmacenFK, ComponenteFK FROM detallecardex WHERE ComponenteFK = " + componenteFK;
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(query);
			while(resultados.next()) {
				DetalleCardex detalleCardex = new DetalleCardex();
				detalleCardex.setSysPK(resultados.getInt(1));
				detalleCardex.setEntrada(resultados.getDouble(2));
				detalleCardex.setSalida(resultados.getDouble(3));
				detalleCardex.setCardexFK(resultados.getInt(4));
				detalleCardex.setAlmacenFK(resultados.getInt(5));
				detalleCardex.setComponenteFK(resultados.getInt(6));
				arrayListDetalleCardex.add(detalleCardex);
			}//FIN WHILE
		}catch(SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY-CATCH
		return arrayListDetalleCardex;
	}//FIN METODO
	
	//METODO PARA LEER REGISTROS POR RANGO DE FECHAS
	public static final ArrayList<DetalleCardex> readPorRangoFechas (Connection connection, Date fechaInicio, Date fechaFin){
		ArrayList<DetalleCardex> arrayListDetalleCardex = new ArrayList<DetalleCardex>();
		String query = "SELECT detallecardex.Sys_PK, detallecardex.Entrada, detallecardex.Salida, detallecardex.CardexFK, detallecardex.AlmacenFK, detallecardex.ComponenteFK, cardex.Fecha FROM detallecardex INNER JOIN cardex ON detallecardex.CardexFK = cardex.Sys_PK WHERE cardex.Fecha BETWEEN '" + fechaInicio + "' AND '" + fechaFin + "'";
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(query);
			while(resultados.next()) {
				DetalleCardex detalleCardex = new DetalleCardex();
				detalleCardex.setSysPK(resultados.getInt(1));
				detalleCardex.setEntrada(resultados.getDouble(2));
				detalleCardex.setSalida(resultados.getDouble(3));
				detalleCardex.setCardexFK(resultados.getInt(4));
				detalleCardex.setAlmacenFK(resultados.getInt(5));
				detalleCardex.setComponenteFK(resultados.getInt(6));
				detalleCardex.setFecha(resultados.getDate(7));
				arrayListDetalleCardex.add(detalleCardex);
			}//FIN WHILE
		}catch(SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY-CATCH
		return arrayListDetalleCardex;
	}//FIN METODO
		
	//METODO PARA ACTUALIZAR UN REGISTRO
	public static final boolean update(Connection connection, DetalleCardex detalleCardex ) {
		String query = "UPDATE detallecardex SET Entrada = ?, Salida = ?, CardexFK = ?, AlmacenFK = ?, ComponenteFK = ? WHERE Sys_PK = ?";
		try{
			PreparedStatement sentenciaPreparada = connection.prepareStatement(query);
			sentenciaPreparada.setDouble(1, detalleCardex.getEntrada());
			sentenciaPreparada.setDouble(2, detalleCardex.getSalida());
			sentenciaPreparada.setInt(3, detalleCardex.getCardexFK());
			sentenciaPreparada.setInt(4, detalleCardex.getAlmacenFK());
			sentenciaPreparada.setInt(5, detalleCardex.getComponenteFK());
			sentenciaPreparada.execute();
			return true;
		}catch(SQLException ex) {
			Notificacion.dialogoException(ex);
			return false;
		}//FIN TRY-CATCH
	}//FIN METODO
	
	//METODO PARA ELIMINAR UN REGISTRO
	public static final boolean delete(Connection connection, DetalleCardex detalleCardex) {
		String query = "DELETE FROM detallecardex WHERE Sys_PK = ?";
		try {
			PreparedStatement sentenciaPreparada = connection.prepareStatement(query);
			sentenciaPreparada.setInt(1, detalleCardex.getSysPK());
			sentenciaPreparada.execute();
			return true;
		}catch (SQLException ex) {
			Notificacion.dialogoException(ex);
			return false;
		}//FIN TRY-CATCH
	}//FIN METODOñ
}//FIN CLASE
