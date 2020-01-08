package mx.shf6.produccion.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import mx.shf6.produccion.model.DetalleCotizacion;
import mx.shf6.produccion.utilities.Notificacion;

public class DetalleCotizacionDAO {

	//METODO PARA CREAR UN REGISTRO
	public static boolean createDetalleCotizacion(Connection connection, DetalleCotizacion detalleCotizacion) {
		String consulta = "INSERT INTO detallecotizaciones (Cantidad, Precio, Costo, FechaEntrega, Observaciones, ProyectoFK, CotizacionFK) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?)";
		try {
			PreparedStatement sentenciaPreparada = connection.prepareStatement(consulta);
			sentenciaPreparada.setDouble(1, detalleCotizacion.getCantidad());
			sentenciaPreparada.setDouble(2, detalleCotizacion.getPrecio());
			sentenciaPreparada.setDouble(3, detalleCotizacion.getCosto());
			sentenciaPreparada.setDate(4, detalleCotizacion.getFechaEntrega());
			sentenciaPreparada.setString(5, detalleCotizacion.getObservaciones());
			sentenciaPreparada.setInt(6, detalleCotizacion.getProyectoFK());
			sentenciaPreparada.setInt(7, detalleCotizacion.getCotizacionFK());
			sentenciaPreparada.execute();
			return true;
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
			return false;
		}//FIN TRY/CATCH
	}//FIN METODO
	
	//METODO PARA OBTENER UN REGISTRO
	public static ArrayList<DetalleCotizacion> readDetalleCotizacion(Connection connection) {
		ArrayList<DetalleCotizacion> arrayListDetalleCotizacion = new ArrayList<DetalleCotizacion>();
		String consulta = "SELECT Sys_PK, Cantidad, Precio, Costo, FechaEntrega, Observaciones, ProyectoFK, CotizacionFK "
				+ "FROM detallecotizaciones";
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(consulta);
			while (resultados.next()) {
				DetalleCotizacion detalleCotizacion = new DetalleCotizacion();
				detalleCotizacion.setSysPK(resultados.getInt(1));
				detalleCotizacion.setCantidad(resultados.getDouble(2));
				detalleCotizacion.setPrecio(resultados.getDouble(3));
				detalleCotizacion.setCosto(resultados.getDouble(4));
				detalleCotizacion.setFechaEntrega(resultados.getDate(5));
				detalleCotizacion.setObservaciones(resultados.getString(6));
				detalleCotizacion.setProyectoFK(resultados.getInt(7));
				detalleCotizacion.setCotizacionFK(resultados.getInt(8));
				arrayListDetalleCotizacion.add(detalleCotizacion);
			}//FIN WHILE
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY/CATCH
		return arrayListDetalleCotizacion;
	}//FIN METODO
	
	//METODO PARA OBTENER UN REGISTRO
	public static DetalleCotizacion readDetalleCotizacion(Connection connection, int sysPK) {
		DetalleCotizacion detalleCotizacion = new DetalleCotizacion();
		String consulta = "SELECT Sys_PK, Cantidad, Precio, Costo, FechaEntrega, Observaciones, ProyectoFK, CotizacionFK "
				+ "FROM detallecotizaciones "
				+ "WHERE Sys_PK = " + sysPK;
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(consulta);
			while (resultados.next()) {
				detalleCotizacion.setSysPK(resultados.getInt(1));
				detalleCotizacion.setCantidad(resultados.getDouble(2));
				detalleCotizacion.setPrecio(resultados.getDouble(3));
				detalleCotizacion.setCosto(resultados.getDouble(4));
				detalleCotizacion.setFechaEntrega(resultados.getDate(5));
				detalleCotizacion.setObservaciones(resultados.getString(6));
				detalleCotizacion.setProyectoFK(resultados.getInt(7));
				detalleCotizacion.setCotizacionFK(resultados.getInt(8));
			}//FIN WHILE
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY/CATCH
		return detalleCotizacion;
	}//FIN METODO
	
	//METODO PARA OBTENER UN REGISTRO
	public static ArrayList<DetalleCotizacion> readCotizacionDetalle(Connection connection, int CotizacionFK) {
		ArrayList<DetalleCotizacion> arrayListDetalleCotizacion = new ArrayList<DetalleCotizacion>();
		String consulta = "SELECT detallecotizaciones.Sys_PK, detallecotizaciones.Cantidad, detallecotizaciones.Precio, detallecotizaciones.Costo, detallecotizaciones.FechaEntrega, detallecotizaciones.Observaciones, detallecotizaciones.ProyectoFK, detallecotizaciones.CotizacionFK, proyectos.Codigo, proyectos.Descripcion FROM detallecotizaciones INNER JOIN proyectos ON detallecotizaciones.ProyectoFK = proyectos.Sys_PK WHERE CotizacionFK = " + CotizacionFK + " ORDER BY detallecotizaciones.FechaEntrega ASC";
		int consecutivo = 1;
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(consulta);
			while (resultados.next()) {
				DetalleCotizacion detalleCotizacion = new DetalleCotizacion();
				detalleCotizacion.setSysPK(resultados.getInt(1));
				detalleCotizacion.setSysPK(resultados.getInt(1));
				detalleCotizacion.setCantidad(resultados.getDouble(2));
				detalleCotizacion.setPrecio(resultados.getDouble(3));
				detalleCotizacion.setCosto(resultados.getDouble(4));
				detalleCotizacion.setFechaEntrega(resultados.getDate(5));
				detalleCotizacion.setObservaciones(resultados.getString(6));
				detalleCotizacion.setProyectoFK(resultados.getInt(7));
				detalleCotizacion.setCotizacionFK(resultados.getInt(8));
				detalleCotizacion.setNumeroDibujo(resultados.getString(9));
				detalleCotizacion.setNombreProyecto(resultados.getString(10));
				detalleCotizacion.setPartida(consecutivo++);
				arrayListDetalleCotizacion.add(detalleCotizacion);
			}//FIN WHILE
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY/CATCH
		return arrayListDetalleCotizacion;
	}//FIN METODO
	
	public static DetalleCotizacion readCotizacionFK(Connection connection, int cotizacionFK) {
		DetalleCotizacion detalleCotizacion = new DetalleCotizacion();
		String consulta = "SELECT detallecotizaciones.Sys_PK, detallecotizaciones.Cantidad, detallecotizaciones.Precio, detallecotizaciones.Costo, detallecotizaciones.FechaEntrega, detallecotizaciones.Observaciones, detallecotizaciones.ProyectoFK, detallecotizaciones.CotizacionFK, proyectos.Codigo, proyectos.Descripcion FROM detallecotizaciones INNER JOIN proyectos ON detallecotizaciones.ProyectoFK = proyectos.Sys_PK WHERE CotizacionFK = " + cotizacionFK;
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(consulta);
			while (resultados.next()) {
				detalleCotizacion.setSysPK(resultados.getInt(1));
				detalleCotizacion.setSysPK(resultados.getInt(1));
				detalleCotizacion.setCantidad(resultados.getDouble(2));
				detalleCotizacion.setPrecio(resultados.getDouble(3));
				detalleCotizacion.setCosto(resultados.getDouble(4));
				detalleCotizacion.setFechaEntrega(resultados.getDate(5));
				detalleCotizacion.setObservaciones(resultados.getString(6));
				detalleCotizacion.setProyectoFK(resultados.getInt(7));
				detalleCotizacion.setCotizacionFK(resultados.getInt(8));
			}//FIN WHILE
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		}
		return detalleCotizacion;
	}
	
	//METODO PARA CREAR UN REGISTRO
	public static boolean updateDetalleCotizacion(Connection connection, DetalleCotizacion detalleCotizacion) {
		String consulta = "UPDATE detallecotizaciones "
				+ "SET Cantidad = ?, Precio = ?, Costo = ?, FechaEntrega = ?, Observaciones = ?, ProyectoFK = ?, CotizacionFK = ? "
				+ "WHERE Sys_PK = ?";
		try {
			PreparedStatement sentenciaPreparada = connection.prepareStatement(consulta);
			sentenciaPreparada.setDouble(1, detalleCotizacion.getCantidad());
			sentenciaPreparada.setDouble(2, detalleCotizacion.getPrecio());
			sentenciaPreparada.setDouble(3, detalleCotizacion.getCosto());
			sentenciaPreparada.setDate(4, detalleCotizacion.getFechaEntrega());
			sentenciaPreparada.setString(5, detalleCotizacion.getObservaciones());
			sentenciaPreparada.setInt(6, detalleCotizacion.getProyectoFK());
			sentenciaPreparada.setInt(7, detalleCotizacion.getCotizacionFK());
			sentenciaPreparada.setInt(8, detalleCotizacion.getSysPK());
			sentenciaPreparada.execute();
			return true;
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
			return false;
		}//FIN TRY/CATCH
	}//FIN METODO
	
	//METODO PARA CREAR UN REGISTRO
	public static boolean deleteDetalleCotizacion(Connection connection, DetalleCotizacion detalleCotizacion) {
		String consulta = "DELETE FROM detallecotizaciones WHERE Sys_PK = ?";
		try {
			PreparedStatement sentenciaPreparada = connection.prepareStatement(consulta);
			sentenciaPreparada.setInt(1, detalleCotizacion.getSysPK());
			sentenciaPreparada.execute();
			return true;
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
			return false;
		}//FIN TRY/CATCH
	}//FIN METODO
	
	//METODO PARA CONVERTIR ARRAYLIST EN OBSERVABLELIST
	public static ObservableList<DetalleCotizacion> toObservableList(ArrayList<DetalleCotizacion> arrayList) {
		ObservableList<DetalleCotizacion> listaObservableDetalleCotizacion = FXCollections.observableArrayList();
		for (DetalleCotizacion detalleCotizacion : arrayList) 
			listaObservableDetalleCotizacion.add(detalleCotizacion);
		return listaObservableDetalleCotizacion;
	}//FIN METODO
	
	public static List<DetalleCotizacion> toList(ArrayList<DetalleCotizacion> arrayList){
		List<DetalleCotizacion> listaDetalleCotizacion = new ArrayList<DetalleCotizacion>();
		for (DetalleCotizacion detalleCotizacion : arrayList)
			listaDetalleCotizacion.add(detalleCotizacion);
		return listaDetalleCotizacion;
	}//FIN METODO
}//FIN CLASE