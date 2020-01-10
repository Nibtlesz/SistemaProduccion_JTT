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
		String consulta = "INSERT INTO detallecontroloperaciones (DetalleProcesoFK, ControlOperacionesFK, CantidadProceso, CantidadTerminada, FechaHoraInicio, FechaHoraFinal) VALUES (?, ?, ?, ?, ?, ?)";
		try {
			PreparedStatement sentenciaPreparada = connection.prepareStatement(consulta);
			sentenciaPreparada.setInt(1, detalleHojaViajera.getDetalleProcesoFK());
			sentenciaPreparada.setInt(2, detalleHojaViajera.getHojaViajeraFK());
			sentenciaPreparada.setDouble(3, detalleHojaViajera.getCantidadEnProceso());
			sentenciaPreparada.setDouble(4, detalleHojaViajera.getCantidadTermiando());
			sentenciaPreparada.setTimestamp(5, detalleHojaViajera.getFechaHoraInicio());
			sentenciaPreparada.setTimestamp(6, detalleHojaViajera.getFechaHoraFinal());
			sentenciaPreparada.execute();
			return true;
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
			return false;
		}//FIN TRY CATCH
	}//FIN METODO	

	public static ArrayList<DetalleHojaViajera> readHojaViajeraPorOrdenProduccionComponente(Connection connection, Integer hojaViajeraFK) {
		ArrayList<DetalleHojaViajera> llistaDetallesHojaViajera = new ArrayList<DetalleHojaViajera>();
		String consulta = "SELECT detallecontroloperaciones.Sys_PK, detallecontroloperaciones.ControlOperacionesFK, detallecontroloperaciones.DetalleProcesoFK, detalleprocesos.Operacion, detalleprocesos.Descripcion, detallecontroloperaciones.CantidadProceso, detallecontroloperaciones.CantidadTerminada, detallecontroloperaciones.FechaHoraInicio, detallecontroloperaciones.FechaHoraFinal FROM detallecontroloperaciones INNER JOIN detalleprocesos ON detallecontroloperaciones.DetalleProcesoFK = detalleprocesos.Sys_PK WHERE detallecontroloperaciones.ControlOperacionesFK = " + hojaViajeraFK;
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
	
	//METODO PARA CREAR UN REGISTRO
	public static boolean updateDetalleHojaViajera(Connection connection, DetalleHojaViajera detalleHojaViajera) {
		String consulta = "UPDATE detallecontroloperaciones SET CantidadProceso = ?, CantidadTerminada = ?, FechaHoraInicio = ?, FechaHoraFinal = ? WHERE DetalleProcesoFK = ? AND ControlOperacionesFK = ?";
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
