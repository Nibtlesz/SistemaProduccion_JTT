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
		String consulta = "INSERT INTO ut_controloperaciones (uf_Cantidad, uf_status, uf_CodigoParoFK, uf_ProductoFK, uf_OrdenProduccionFK) VALUES (?, ?, ?, ?, ?)";
		try {
			PreparedStatement sentenciaPreparada = connection.prepareStatement(consulta);
			sentenciaPreparada.setDouble(1,hojaViajera.getCantidad());
			sentenciaPreparada.setInt(2, hojaViajera.getStatus());
			sentenciaPreparada.setInt(3, hojaViajera.getCodigoParoFK());
			sentenciaPreparada.setInt(4, hojaViajera.getComponenteFK());
			sentenciaPreparada.setInt(5, hojaViajera.getOrdenProduccionFK());
			sentenciaPreparada.execute();
			return true;
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
			return false;
		}//FIN TRY CATCH
	}//FIN METODO
	
	public static HojaViajera readHojaViajeraPorOrdenProduccionComponente(Connection connection, Integer ordenProduccionFK, Integer componenteFK) {
		HojaViajera hojaViajera = new HojaViajera();
		String consulta = "SELECT ut_controloperaciones.Sys_PK, ut_controloperaciones.uf_Cantidad, ut_controloperaciones.uf_CodigoParoFK, ut_codigosparo.uf_Descripcion, ut_controloperaciones.uf_ProductoFK, producto.Codigo, ut_controloperaciones.uf_OrdenProduccionFK FROM ut_controloperaciones INNER JOIN ut_codigosparo ON ut_controloperaciones.uf_CodigoParoFK = ut_codigosparo.Sys_PK INNER JOIN producto ON ut_controloperaciones.uf_ProductoFK = producto.Sys_PK WHERE ut_controloperaciones.uf_OrdenProduccionFK = " + ordenProduccionFK + " AND ut_controloperaciones.uf_ProductoFK = " + componenteFK;
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(consulta);
			while (resultados.next()) {
				hojaViajera.setSysPK(resultados.getInt(1));
				hojaViajera.setCantidad(resultados.getInt(2));
				hojaViajera.setCodigoParoFK(resultados.getInt(3));
				hojaViajera.setDescripcionParo(resultados.getString(4));
				hojaViajera.setComponenteFK(resultados.getInt(5));
				hojaViajera.setNumeroParte(resultados.getString(6));
				hojaViajera.setOrdenProduccionFK(resultados.getInt(7));
			}//FIN WHILE
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY CATCH
		return hojaViajera;
	}//FIN METODO

}//FIN CLASE
