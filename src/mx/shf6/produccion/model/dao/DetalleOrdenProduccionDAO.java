package mx.shf6.produccion.model.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import mx.shf6.produccion.model.DetalleOrdenProduccion;
import mx.shf6.produccion.utilities.Notificacion;

public class DetalleOrdenProduccionDAO {
	
	//METODO PARA CREAR UN REGISTRO
	public static boolean createDetalleOrdenProduccion(Connection connection, DetalleOrdenProduccion detalleOrdenProduccion) {
		String consulta = "INSERT INTO ut_detalleordenesproduccion (uf_NumeroSerie, uf_Status, uf_OrdenProduccionFK) VALUES (?, 0, ?)";
		try {
			PreparedStatement sentenciaPreparada = connection.prepareStatement(consulta);
			sentenciaPreparada.setString(1, detalleOrdenProduccion.getNumeroSerie());
			sentenciaPreparada.setInt(2, detalleOrdenProduccion.getOrdenProduccionFK());
			sentenciaPreparada.execute();
			return true;
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
			return false;
		}//FIN TRY CATCH
	}//FIN METODO
	
	public static ArrayList<DetalleOrdenProduccion> readDetalleLoteProduccion(Connection connection) {
		ArrayList<DetalleOrdenProduccion> listaDetalleLoteProduccion = new ArrayList<DetalleOrdenProduccion>();
		String consulta = "SELECT Sys_PK, uf_NumeroSerie, uf_Status, uf_OrdenProduccionFK FROM ut_detalleordenesproduccion";
		try {
			Statement sentencia =connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(consulta);
			while(resultados.next()) {
				DetalleOrdenProduccion detalleLote = new DetalleOrdenProduccion();
				detalleLote.setSysPK(resultados.getInt(1));
				detalleLote.setNumeroSerie(resultados.getString(2));
				detalleLote.setStatus(resultados.getInt(3));
				detalleLote.setOrdenProduccionFK(resultados.getInt(4));
				listaDetalleLoteProduccion.add(detalleLote);
			}//FIN WHILE
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY CATCH
		return listaDetalleLoteProduccion;
	}//FIN METODO
	
	public static ArrayList<DetalleOrdenProduccion> readDetalleLoteProduccion(Connection connection, int orden) {
		ArrayList<DetalleOrdenProduccion> listaDetalleLoteProduccion = new ArrayList<DetalleOrdenProduccion>();
		String consulta = "";
		if (orden < 0)
			consulta = "SELECT Sys_PK, uf_NumeroSerie, uf_Status, uf_OrdenProduccionFK FROM ut_detalleordenesproduccion WHERE uf_Status != 4";
		else
			consulta = "SELECT Sys_PK, uf_NumeroSerie, uf_Status, uf_OrdenProduccionFK FROM ut_detalleordenesproduccion  WHERE uf_OrdenProduccionFK = " + orden + " AND uf_Status != 4";
		try {
			Statement sentencia =connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(consulta);
			while(resultados.next()) {
				DetalleOrdenProduccion detalleLote = new DetalleOrdenProduccion();
				detalleLote.setSysPK(resultados.getInt(1));
				detalleLote.setNumeroSerie(resultados.getString(2));
				detalleLote.setStatus(resultados.getInt(3));
				detalleLote.setOrdenProduccionFK(resultados.getInt(4));
				listaDetalleLoteProduccion.add(detalleLote);
			}//FIN WHILE
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY CATCH
		return listaDetalleLoteProduccion;
	}//FIN METODO
	
	public static DetalleOrdenProduccion searchOrdenProduccion(Connection connection, int sysPK) {
		DetalleOrdenProduccion detalleOrden = new DetalleOrdenProduccion();
		String consulta = "SELECT Sys_PK, uf_NumeroSerie, uf_OrdenProduccionFK, uf_Status FROM ut_detalleordenesproduccion WHERE uf_OrdenProduccionFK =" + sysPK;
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(consulta);
			while (resultados.next()) {
				detalleOrden.setSysPK(resultados.getInt(1));
				detalleOrden.setNumeroSerie(resultados.getString(2));
				detalleOrden.setOrdenProduccionFK(resultados.getInt(3));
				detalleOrden.setStatus(resultados.getInt(4));
			}//FIN WHILE
		} catch (SQLException e) {
			Notificacion.dialogoException(e);
		}//FIN TRY/CATCH
		return detalleOrden;
	}//FIN METODO	
}//FIN CLASE
