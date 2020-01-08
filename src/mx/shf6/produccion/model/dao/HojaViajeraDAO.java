package mx.shf6.produccion.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import mx.shf6.produccion.model.HojaViajera;
import mx.shf6.produccion.utilities.Notificacion;

public class HojaViajeraDAO {
	
	//METODO PARA CREAR UN REGISTRO
	public static boolean createControlOperaciones(Connection connection, HojaViajera hojaViajera) {
		String consulta = "INSERT INTO controloperaciones (Cantidad, CodigoParoFK, ComponenteFK, OrdenProduccionFK, Referencia, Status) VALUES (?, ?, ?, ?, ?, ?)";
		try {
			PreparedStatement sentenciaPreparada = connection.prepareStatement(consulta);
			sentenciaPreparada.setDouble(1,hojaViajera.getCantidad());
			sentenciaPreparada.setInt(2, hojaViajera.getCodigoParoFK());
			sentenciaPreparada.setInt(3, hojaViajera.getComponenteFK());
			sentenciaPreparada.setInt(4, hojaViajera.getOrdenProduccionFK());
			sentenciaPreparada.setString(5, "NA");
			sentenciaPreparada.setInt(6, hojaViajera.getStatus());
			sentenciaPreparada.execute();
			return true;
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
			return false;
		}//FIN TRY CATCH
	}//FIN METODO
	
	public static HojaViajera readHojaViajeraPorOrdenProduccionComponente(Connection connection, Integer ordenProduccionFK, Integer componenteFK) {
		HojaViajera hojaViajera = new HojaViajera();
		String consulta = "SELECT controloperaciones.Sys_PK, controloperaciones.Cantidad, controloperaciones.CodigoParoFK, codigosparo.Descripcion, controloperaciones.ComponenteFK, componentes.NumeroParte, controloperaciones.OrdenProduccionFK, controloperaciones.Referencia, controloperaciones.Status FROM controloperaciones INNER JOIN codigosparo ON controloperaciones.CodigoParoFK = codigosparo.Sys_PK INNER JOIN componentes ON controloperaciones.ComponenteFK = componentes.Sys_PK WHERE controloperaciones.OrdenProduccionFK = " + ordenProduccionFK + " AND controloperaciones.ComponenteFK = " + componenteFK;
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(consulta);
			while (resultados.next()) {
				hojaViajera.setSysPK(resultados.getInt(1));
				hojaViajera.setCantidad(resultados.getDouble(2));
				hojaViajera.setCodigoParoFK(resultados.getInt(3));
				hojaViajera.setDescripcionParo(resultados.getString(4));
				hojaViajera.setComponenteFK(resultados.getInt(5));
				hojaViajera.setNumeroParte(resultados.getString(6));
				hojaViajera.setOrdenProduccionFK(resultados.getInt(7));
				hojaViajera.setStatus(resultados.getInt(9));
			}//FIN WHILE
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY CATCH
		return hojaViajera;
	}//FIN METODO

}//FIN CLASE
