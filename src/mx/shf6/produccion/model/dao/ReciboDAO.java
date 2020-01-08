package mx.shf6.produccion.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import mx.shf6.produccion.model.Recibo;
import mx.shf6.produccion.utilities.Notificacion;


public class ReciboDAO {

	public static boolean create(Connection connection, Recibo recibo) {
		String consulta = "INSERT INTO recibos (Fecha, Hora, Importe, Referencia, Notas, FolioFK, ClienteFK) VALUES (CURDATE(), NOW(), ?, ?, ?, 4, ?)";
		try {
			PreparedStatement sentenciaPreparada = connection.prepareStatement(consulta);
			sentenciaPreparada.setDouble(1, recibo.getImporte());
			sentenciaPreparada.setString(2, recibo.getReferencia());
			sentenciaPreparada.setString(3, recibo.getNotas());
			sentenciaPreparada.setInt(4, recibo.getClienteFK());
			sentenciaPreparada.execute();
			return true;
		} catch (SQLException ex) {
//			Notificacion.dialogoException(ex);
			System.out.println(ex);
			return false;
		}//FIN TRY/CATCH
	}//FIN METODO

	public static ArrayList<Recibo> readTodos(Connection connection) {
		ArrayList<Recibo> arrayListRecibos = new ArrayList<Recibo>();
		String consulta = "SELECT Sys_PK, Fecha, Hora, Importe, Referencia, Notas, FolioFK, ClienteFK FROM recibos";
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(consulta);
			while (resultados.next()) {
				Recibo recibo = new Recibo();
				recibo.setSysPK(resultados.getInt(1));
				recibo.setFecha(resultados.getDate(2));
				recibo.setHora(resultados.getTime(3));
				recibo.setImporte(resultados.getDouble(4));
				recibo.setReferencia(resultados.getString(5));
				recibo.setNotas(resultados.getString(6));
				recibo.setFolioFK(resultados.getInt(7));
				recibo.setClienteFK(resultados.getInt(8));
				arrayListRecibos.add(recibo);
			}//FIN WHILE
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY/CATCH
		return arrayListRecibos;
	}//FIN METODO

	public static Recibo readPorSysPk(Connection connection, int sysPK) {
		Recibo recibo = new Recibo();
		String consulta = "SELECT Sys_PK, Fecha, Hora, Importe, Referencia, Notas, FolioFK, ClienteFK FROM recibos WHERE Sys_PK =" + sysPK;
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(consulta);
			while (resultados.next()) {
				recibo.setSysPK(resultados.getInt(1));
				recibo.setFecha(resultados.getDate(2));
				recibo.setHora(resultados.getTime(3));
				recibo.setImporte(resultados.getDouble(4));
				recibo.setReferencia(resultados.getString(5));
				recibo.setNotas(resultados.getString(6));
				recibo.setFolioFK(resultados.getInt(7));
				recibo.setClienteFK(resultados.getInt(8));
			}//FIN WHILE
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY/CATCH
		return recibo;
	}//FIN METODO

	public static int ultimoSysPk(Connection connection) {
		String query = "SELECT Sys_PK FROM recibos ORDER BY Sys_PK ASC";
		int ultimoSysPk=0;
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			while (resultSet.next())
				ultimoSysPk=resultSet.getInt(1);
			return ultimoSysPk;
		}catch (SQLException e) {
			System.out.println("Error: En método leer");
			e.printStackTrace();
		}//FIN TRY/CATCH
		return ultimoSysPk;
	}//FIN METODO

	public static boolean update(Connection connection, Recibo recibo) {
		String consulta = "UPDATE recibos SET Importe = ?, Notas = ?, ClienteFK = ? WHERE Sys_PK = ?";
		try {
			PreparedStatement sentenciaPreparada = connection.prepareStatement(consulta);
			sentenciaPreparada.setDouble(1, recibo.getImporte());
			sentenciaPreparada.setString(2, recibo.getNotas());
			sentenciaPreparada.setInt(3, recibo.getClienteFK());
			sentenciaPreparada.setInt(4, recibo.getSysPK());
			sentenciaPreparada.execute();
			return true;
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
			return false;
		}//FIN TRY/CATCH
	}//FIN METODO

	public static boolean delete(Connection connection, Recibo recibo) {
		String consulta = "DELETE FROM recibos WHERE Sys_PK = ?";
		try {
			PreparedStatement sentenciaPreparada = connection.prepareStatement(consulta);
			sentenciaPreparada.setInt(1, recibo.getSysPK());
			sentenciaPreparada.execute();
			return true;
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
			return false;
		}//FIN TRY/CATCH
	}//FIN METODO
}//FIN DE CLASE
