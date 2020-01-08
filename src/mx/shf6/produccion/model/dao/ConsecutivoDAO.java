package mx.shf6.produccion.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import mx.shf6.produccion.utilities.Notificacion;

public class ConsecutivoDAO {

	//METODO PARA OBTENER UN REGISTRO
	public static int readConsecutivoTipoMateriaPrima(Connection connection) {
		int consecutivo = 0;
		String consulta = "SELECT consecutivo FROM consecutivos2 WHERE Sys_PK = 2 ";
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(consulta);
			while (resultados.next()) {
				consecutivo = resultados.getInt(1);
			}//FIN WHILE
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY/CATCH
		return consecutivo + 1;
	}//FIN METODO
	
	//METODO PARA OBTENER UN REGISTRO
	public static int readConsecutivoTipoMiscelaneo(Connection connection) {
		int consecutivo = 0;
		String consulta = "SELECT consecutivo FROM consecutivos2 WHERE Sys_PK = 1 ";
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(consulta);
			while (resultados.next()) {
				consecutivo = resultados.getInt(1);
			}//FIN WHILE
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY/CATCH
		return consecutivo + 1;
	}//FIN METODO
	
	//METODO PARA OBTENER UN REGISTRO
	public static int readConsecutivoPartePrimaria(Connection connection) {
		int consecutivo = 0;
		String consulta = "SELECT consecutivo FROM consecutivos2 WHERE Sys_PK = 3 ";
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(consulta);
			while (resultados.next()) {
				consecutivo = resultados.getInt(1);
			}//FIN WHILE
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY/CATCH
		return consecutivo + 1;
	}//FIN METODO
	
	//METODO PARA OBTENER UN REGISTRO
	public static int readConsecutivoEnsamble(Connection connection) {
		int consecutivo = 0;
		String consulta = "SELECT consecutivo FROM consecutivos2 WHERE Sys_PK = 4 ";
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(consulta);
			while (resultados.next()) {
				consecutivo = resultados.getInt(1);
			}//FIN WHILE
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY/CATCH
		return consecutivo + 1;
	}//FIN METODO
	
	//METODO PARA OBTENER UN REGISTRO
	public static int readConsecutivoCliente(Connection connection, int clienteFK, String folio) {
		int consecutivo = 0;
		String consulta = "SELECT consecutivo FROM consecutivos WHERE ClienteFK = " + clienteFK + " AND Folio = '" + folio + "'";
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(consulta);
			while (resultados.next()) {
				consecutivo = resultados.getInt(1);
			}//FIN WHILE
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY/CATCH
		return consecutivo + 1;
	}//FIN METODO
	
	//METODO PARA CREAR UN REGISTRO
	public static boolean updateConsecutivoTipoMateriaPrima(Connection connection, int tipoMateriaPrimaFK) {
		String consulta = "UPDATE consecutivos SET Consecutivo = Consecutivo + 1 WHERE TipoMateriaPrimaFK = ?";
		try {
			PreparedStatement sentenciaPreparada = connection.prepareStatement(consulta);
			sentenciaPreparada.setInt(1, tipoMateriaPrimaFK);
			sentenciaPreparada.execute();
			return true;
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
			return false;
		}//FIN TRY/CATCH
	}//FIN METODO
	
	//METODO PARA CREAR UN REGISTRO
	public static boolean updateConsecutivoTipoMiscelaneo(Connection connection, int tipoMiscelaneoFK) {
		String consulta = "UPDATE consecutivos SET Consecutivo = Consecutivo + 1 WHERE TipoMiscelaneoFK = ?";
		try {
			PreparedStatement sentenciaPreparada = connection.prepareStatement(consulta);
			sentenciaPreparada.setInt(1, tipoMiscelaneoFK);
			sentenciaPreparada.execute();
			return true;
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
			return false;
		}//FIN TRY/CATCH
	}//FIN METODO
	
	//METODO PARA CREAR UN REGISTRO
	public static boolean updateConsecutivoCliente(Connection connection, int clienteFK, String folio) {
		String consulta = "UPDATE consecutivos SET Consecutivo = Consecutivo + 1 WHERE TipoMateriaPrimaFK = ? AND Folio = ?;";
		try {
			PreparedStatement sentenciaPreparada = connection.prepareStatement(consulta);
			sentenciaPreparada.setInt(1, clienteFK);
			sentenciaPreparada.setString(2, folio);
			sentenciaPreparada.execute();
			return true;
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
			return false;
		}//FIN TRY/CATCH
	}//FIN METODO
	
}//FIN CLASE
