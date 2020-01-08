package mx.shf6.produccion.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import mx.shf6.produccion.model.AplicarCuentasXCobrar;
import mx.shf6.produccion.utilities.Notificacion;

public class AplicarCuentasXCobrarDAO {

	public static boolean create (Connection connection, AplicarCuentasXCobrar aplicarCuentasXCobrar) {
		String consulta = "INSERT INTO aplcxc (Fecha, Importe, Cotizacion_DCXC_FK, Abono_DCXC_FK) VALUES (CURDATE(), ?, ?, ?)";
		try {
			PreparedStatement sentenciaPreparada = connection.prepareStatement(consulta);
			sentenciaPreparada.setDouble(1, aplicarCuentasXCobrar.getImporte());
			sentenciaPreparada.setInt(2, aplicarCuentasXCobrar.getAplicadoA());
			sentenciaPreparada.setInt(3, aplicarCuentasXCobrar.getDcxcFK());
			sentenciaPreparada.execute();
			return true;
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
			return false;
		}//FIN TRY/CATCH
	}//FIN METODO

	public static ArrayList<AplicarCuentasXCobrar> readTodos (Connection connection){
		ArrayList<AplicarCuentasXCobrar> arrayListAplicarCuentasXCobrar = new ArrayList<AplicarCuentasXCobrar>();
		String consulta = "SELECT Sys_PK, Fecha, Importe, Cotizacion_DCXC_FK, Abono_DCXC_FK FROM aplcxc;";
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(consulta);
			while (resultados.next()) {
				AplicarCuentasXCobrar aplicarCuentasXCobrar = new AplicarCuentasXCobrar();
				aplicarCuentasXCobrar.setSysPK(resultados.getInt(1));
				aplicarCuentasXCobrar.setFecha(resultados.getDate(2));
				aplicarCuentasXCobrar.setImporte(resultados.getDouble(3));
				aplicarCuentasXCobrar.setAplicadoA(resultados.getInt(4));
				aplicarCuentasXCobrar.setDcxcFK(resultados.getInt(5));
				arrayListAplicarCuentasXCobrar.add(aplicarCuentasXCobrar);
			}//FIN WHILE
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY/CATCH
		return arrayListAplicarCuentasXCobrar;
	}//FIN METODO

	public static AplicarCuentasXCobrar readPorSysPK (Connection connection, int sysPK) {
		AplicarCuentasXCobrar aplicarCuentasXCobrar = new AplicarCuentasXCobrar();
		String consulta = "SELECT Sys_PK, Fecha, Importe, Cotizacion_DCXC_FK, Abono_DCXC_FK FROM aplcxc WHERE Sys_PK = " + sysPK;
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(consulta);
			while (resultados.next()) {
				aplicarCuentasXCobrar.setSysPK(resultados.getInt(1));
				aplicarCuentasXCobrar.setFecha(resultados.getDate(2));
				aplicarCuentasXCobrar.setImporte(resultados.getDouble(3));
				aplicarCuentasXCobrar.setAplicadoA(resultados.getInt(4));
				aplicarCuentasXCobrar.setDcxcFK(resultados.getInt(5));
			}//FIN WHILE
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY/CATCH
		return aplicarCuentasXCobrar;
	}//FIN METODO

	public static boolean delete(Connection connection, AplicarCuentasXCobrar aplicarCuentasXCobrar) {
		String consulta = "DELETE FROM aplcxc WHERE Sys_PK = ?";
		try {
			PreparedStatement sentenciaPreparada = connection.prepareStatement(consulta);
			sentenciaPreparada.setInt(1, aplicarCuentasXCobrar.getSysPK());
			sentenciaPreparada.execute();
			return true;
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
			return false;
		}//FIN TRY/CATCH
	}//FIN METODO

}//FIN CLASE
