package mx.shf6.produccion.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import mx.shf6.produccion.model.DetalleHojaViajera;
import mx.shf6.produccion.utilities.Notificacion;

public class DetalleHojaViajeraDAO {
	
	//METODO PARA CREAR UN REGISTRO
	public static boolean createDetalleHojaViajera(Connection connection, DetalleHojaViajera detalleHojaViajera) {
		String consulta = "INSERT INTO ut_detallecontroloperaciones (uf_FechaHoraInicio, uf_FechaHoraFinal, uf_CantidadProceso, uf_CantidadTerminada, uf_DetalleProcesoFK, uf_ControlOperacionesFK) VALUES (?, ?, ?, ?, ?, ?)";
		try {
			PreparedStatement sentenciaPreparada = connection.prepareStatement(consulta);
			sentenciaPreparada.setTimestamp(1, detalleHojaViajera.getFechaHoraInicio());
			sentenciaPreparada.setTimestamp(2, detalleHojaViajera.getFechaHoraFinal());
			sentenciaPreparada.setDouble(3, detalleHojaViajera.getCantidadEnProceso());
			sentenciaPreparada.setDouble(4, detalleHojaViajera.getCantidadTermiando());
			sentenciaPreparada.setInt(5, detalleHojaViajera.getDetalleProcesoFK());
			sentenciaPreparada.setInt(6, detalleHojaViajera.getHojaViajeraFK());
			sentenciaPreparada.execute();
			return true;
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
			return false;
		}//FIN TRY CATCH
	}//FIN METODO	

	public static ArrayList<DetalleHojaViajera> readHojaViajeraPorOrdenProduccionComponente(Connection connection, Integer hojaViajeraFK) {
		ArrayList<DetalleHojaViajera> llistaDetallesHojaViajera = new ArrayList<DetalleHojaViajera>();
		String consulta = "SELECT ut_detallecontroloperaciones.Sys_PK, ut_detallecontroloperaciones.uf_ControlOperacionesFK, ut_detallecontroloperaciones.uf_DetalleProcesoFK, ut_detalleprocesos.uf_Operacion, ut_detalleprocesos.uf_Descripcion, ut_detallecontroloperaciones.uf_CantidadProceso, ut_detallecontroloperaciones.uf_CantidadTerminada, ut_detallecontroloperaciones.uf_FechaHoraInicio, ut_detallecontroloperaciones.uf_FechaHoraFinal FROM ut_detallecontroloperaciones INNER JOIN ut_detalleprocesos ON ut_detallecontroloperaciones.uf_DetalleProcesoFK = ut_detalleprocesos.Sys_PK WHERE ut_detallecontroloperaciones.uf_ControlOperacionesFK = " + hojaViajeraFK;
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(consulta);
			while (resultados.next()) {
				DetalleHojaViajera detalleHojaViajera = new DetalleHojaViajera();
				detalleHojaViajera.setSysPK(resultados.getInt(1));
				detalleHojaViajera.setHojaViajeraFK(resultados.getInt(2));
				detalleHojaViajera.setDetalleProcesoFK(resultados.getInt(3));
				detalleHojaViajera.setDetalleProcesoOperacion(resultados.getInt(4));
				detalleHojaViajera.setDetalleProcesoDescripcion(resultados.getString(5));
				detalleHojaViajera.setCantidadEnProceso(resultados.getInt(6));
				detalleHojaViajera.setCantidadTerminado(resultados.getInt(7));
				detalleHojaViajera.setFechaHoraInicio(resultados.getTimestamp(8));
				detalleHojaViajera.setFechaHoraFinal(resultados.getTimestamp(9));
				llistaDetallesHojaViajera.add(detalleHojaViajera);
			}//FIN WHILE
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY CATCH
		return llistaDetallesHojaViajera;
	}//FIN METODO
	
	public static ArrayList<DetalleHojaViajera> readOrdenProduccion(Connection connection, Integer ordenProduccion) {
		ArrayList<DetalleHojaViajera> llistaDetallesHojaViajera = new ArrayList<DetalleHojaViajera>();
		String consulta = "SELECT uf_CantidadProceso FROM ut_detallecontroloperaciones INNER JOIN ut_controloperaciones ON ut_detallecontroloperaciones.uf_ControlOperacionesFK = ut_controloperaciones.Sys_PK INNER JOIN ut_ordenesproduccion ON ut_controloperaciones.uf_OrdenProduccionFK = ut_ordenesproduccion.Sys_PK WHERE ut_ordenesproduccion.Sys_PK =  " + ordenProduccion;
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(consulta);
			while (resultados.next()) {
				DetalleHojaViajera detalleHojaViajera = new DetalleHojaViajera();
				detalleHojaViajera.setCantidadEnProceso(resultados.getInt(1));
				llistaDetallesHojaViajera.add(detalleHojaViajera);
			}//FIN WHILE
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY CATCH
		return llistaDetallesHojaViajera;
	}//FIN METODO
	
	//METODO PARA CREAR UN REGISTRO
	public static boolean updateDetalleHojaViajera(Connection connection, DetalleHojaViajera detalleHojaViajera) {
		String consulta = "UPDATE ut_detallecontroloperaciones SET uf_CantidadProceso = ?, uf_CantidadTerminada = ?, uf_FechaHoraInicio = ?, uf_FechaHoraFinal = ? WHERE uf_DetalleProcesoFK = ? AND uf_ControlOperacionesFK = ?";
		try {
			PreparedStatement sentenciaPreparada = connection.prepareStatement(consulta);
			sentenciaPreparada.setDouble(1, detalleHojaViajera.getCantidadEnProceso());
			sentenciaPreparada.setDouble(2, detalleHojaViajera.getCantidadTermiando());
			sentenciaPreparada.setTimestamp(3, detalleHojaViajera.getFechaHoraInicio());
			sentenciaPreparada.setTimestamp(4, detalleHojaViajera.getFechaHoraFinal());
			sentenciaPreparada.setInt(5, detalleHojaViajera.getDetalleProcesoFK());
			sentenciaPreparada.setInt(6, detalleHojaViajera.getHojaViajeraFK());
			sentenciaPreparada.execute();
			return true;
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
			return false;
		}//FIN TRY CATCH
	}//FIN METODO
	
}//FIN CLASE
