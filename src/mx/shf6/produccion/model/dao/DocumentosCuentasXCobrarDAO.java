package mx.shf6.produccion.model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;

import mx.shf6.produccion.model.DocumentosCuentasXCobrar;
import mx.shf6.produccion.utilities.Notificacion;

public class DocumentosCuentasXCobrarDAO {

	public static boolean create(Connection connection, DocumentosCuentasXCobrar documentosCuentasXCobrar) {
		String consulta = "INSERT INTO dcxc (Bonificacion, Debe, Documento, Fecha, Haber, Notas, Pagos, Referencia, XAplicar, ClienteFK, ReciboFK, CotizacionFK) VALUES (?, ?, ?, CURDATE(), ?, ?, ?, ?, ?, ?, ?, ?)";
		try {
			PreparedStatement sentenciaPreparada = connection.prepareStatement(consulta);
			sentenciaPreparada.setDouble(1, documentosCuentasXCobrar.getBonificaciones());
			sentenciaPreparada.setDouble(2, documentosCuentasXCobrar.getDebe());
			sentenciaPreparada.setInt(3, documentosCuentasXCobrar.getDocumento());
			sentenciaPreparada.setDouble(4, documentosCuentasXCobrar.getHaber());
			sentenciaPreparada.setString(5, documentosCuentasXCobrar.getNotas());
			sentenciaPreparada.setDouble(6, documentosCuentasXCobrar.getPagos());
			sentenciaPreparada.setString(7, documentosCuentasXCobrar.getReferencia());
			sentenciaPreparada.setDouble(8, documentosCuentasXCobrar.getXAplicar());
			sentenciaPreparada.setInt(9, documentosCuentasXCobrar.getClienteFK());
			if(documentosCuentasXCobrar.getReciboFK() != 0)
				sentenciaPreparada.setInt(10, documentosCuentasXCobrar.getReciboFK());
			else
				sentenciaPreparada.setNull(10, Types.INTEGER);

			if(documentosCuentasXCobrar.getCotizacionFK() != 0)
				sentenciaPreparada.setInt(11, documentosCuentasXCobrar.getCotizacionFK());
			else
				sentenciaPreparada.setNull(11, Types.INTEGER);

			sentenciaPreparada.execute();
			return true;
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
			return false;
		}//FIN TRY/CATCH
	}//FIN METODO

	public static ArrayList<DocumentosCuentasXCobrar> readTodos(Connection connection) {
		ArrayList<DocumentosCuentasXCobrar> arrayListDocumentosCuentasXCobrar = new ArrayList<DocumentosCuentasXCobrar>();
		String consulta = "SELECT Sys_PK, Bonificacion, Debe, Documento, Fecha, Haber, Notas, Pagos, Referencia, XAplicar, ClienteFK, ReciboFK, CotizacionFK FROM dcxc";
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(consulta);
			while (resultados.next()) {
				DocumentosCuentasXCobrar documentosCuentasXCobrar = new DocumentosCuentasXCobrar();
				documentosCuentasXCobrar.setSysPK(resultados.getInt(1));
				documentosCuentasXCobrar.setBonificaciones(resultados.getDouble(2));
				documentosCuentasXCobrar.setDebe(resultados.getDouble(3));
				documentosCuentasXCobrar.setDocumento(resultados.getInt(4));
				documentosCuentasXCobrar.setFecha(resultados.getDate(5));
				documentosCuentasXCobrar.setHaber(resultados.getDouble(6));
				documentosCuentasXCobrar.setNotas(resultados.getString(7));
				documentosCuentasXCobrar.setPagos(resultados.getDouble(8));
				documentosCuentasXCobrar.setReferencia(resultados.getString(9));
				documentosCuentasXCobrar.setXAplicar(resultados.getDouble(10));
				documentosCuentasXCobrar.setClienteFK(resultados.getInt(11));
				documentosCuentasXCobrar.setReciboFK(resultados.getInt(12));
				documentosCuentasXCobrar.setCotizacionFK(resultados.getInt(13));
				arrayListDocumentosCuentasXCobrar.add(documentosCuentasXCobrar);
			}//FIN WHILE
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY/CATCH
		return arrayListDocumentosCuentasXCobrar;
	}//FIN METODO

	public static DocumentosCuentasXCobrar readPorSysPk(Connection connection, int sysPK) {
		DocumentosCuentasXCobrar documentosCuentasXCobrar = new DocumentosCuentasXCobrar();
		String consulta = "SELECT Sys_PK, Bonificacion, Debe, Documento, Fecha, Haber, Notas, Pagos, Referencia, XAplicar, ClienteFK, ReciboFK, CotizacionFK FROM dcxc WHERE Sys_PK =" + sysPK;
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(consulta);
			while (resultados.next()) {
				documentosCuentasXCobrar.setSysPK(resultados.getInt(1));
				documentosCuentasXCobrar.setBonificaciones(resultados.getDouble(2));
				documentosCuentasXCobrar.setDebe(resultados.getDouble(3));
				documentosCuentasXCobrar.setDocumento(resultados.getInt(4));
				documentosCuentasXCobrar.setFecha(resultados.getDate(5));
				documentosCuentasXCobrar.setHaber(resultados.getDouble(6));
				documentosCuentasXCobrar.setNotas(resultados.getString(7));
				documentosCuentasXCobrar.setPagos(resultados.getDouble(8));
				documentosCuentasXCobrar.setReferencia(resultados.getString(9));
				documentosCuentasXCobrar.setXAplicar(resultados.getDouble(10));
				documentosCuentasXCobrar.setClienteFK(resultados.getInt(11));
				documentosCuentasXCobrar.setReciboFK(resultados.getInt(12));
				documentosCuentasXCobrar.setCotizacionFK(resultados.getInt(13));
			}//FIN WHILE
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY/CATCH
		return documentosCuentasXCobrar;
	}//FIN METODO

	public static ArrayList<DocumentosCuentasXCobrar> readMovimientosPorClienteFK(Connection connection, int clienteFK, Date fechaInicio, Date fechaFin, String like) {
		ArrayList<DocumentosCuentasXCobrar> arrayListDocumentosCuentasXCobrar = new ArrayList<DocumentosCuentasXCobrar>();
		String consulta = "SELECT Sys_PK, Bonificacion, Debe, Documento, Fecha, Haber, Notas, Pagos, Referencia, XAplicar, ClienteFK, ReciboFK, CotizacionFK FROM dcxc WHERE ClienteFK = " + clienteFK +" AND dcxc.Sys_PK NOT IN (SELECT Sys_PK FROM dcxc WHERE Documento = 1 AND Haber > 0) AND (Fecha BETWEEN '"+ fechaInicio +"' AND '"+ fechaFin +"') AND Referencia LIKE '%"+ like +"%'";
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(consulta);
			while (resultados.next()) {
				DocumentosCuentasXCobrar documentosCuentasXCobrar = new DocumentosCuentasXCobrar();
				documentosCuentasXCobrar.setSysPK(resultados.getInt(1));
				documentosCuentasXCobrar.setBonificaciones(resultados.getDouble(2));
				documentosCuentasXCobrar.setDebe(resultados.getDouble(3));
				documentosCuentasXCobrar.setDocumento(resultados.getInt(4));
				documentosCuentasXCobrar.setFecha(resultados.getDate(5));
				documentosCuentasXCobrar.setHaber(resultados.getDouble(6));
				documentosCuentasXCobrar.setNotas(resultados.getString(7));
				documentosCuentasXCobrar.setPagos(resultados.getDouble(8));
				documentosCuentasXCobrar.setReferencia(resultados.getString(9));
				documentosCuentasXCobrar.setXAplicar(resultados.getDouble(10));
				documentosCuentasXCobrar.setClienteFK(resultados.getInt(11));
				documentosCuentasXCobrar.setReciboFK(resultados.getInt(12));
				documentosCuentasXCobrar.setCotizacionFK(resultados.getInt(13));
				documentosCuentasXCobrar.setSaldo(resultados.getDouble(3) - resultados.getDouble(8));
				arrayListDocumentosCuentasXCobrar.add(documentosCuentasXCobrar);
			}//FIN WHILE
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY/CATCH
		return arrayListDocumentosCuentasXCobrar;
	}//FIN METODO

	public static ArrayList<DocumentosCuentasXCobrar> readPendientesPorClienteFK(Connection connection, int clienteFK, Date fechaInicio, Date fechaFin, String like) {
		ArrayList<DocumentosCuentasXCobrar> arrayListDocumentosCuentasXCobrar = new ArrayList<DocumentosCuentasXCobrar>();
		String consulta = "SELECT  Sys_PK, Bonificacion, Debe, Documento, Fecha, Haber, Notas, Pagos, Referencia, XAplicar, ClienteFK, ReciboFK, CotizacionFK, (debe - pagos) AS Saldo FROM dcxc  WHERE Bonificacion = 0 AND (debe - pagos) > 0 AND ClienteFK = " + clienteFK +" AND (Fecha BETWEEN '"+ fechaInicio +"' AND '"+ fechaFin +"') AND Referencia LIKE '%"+ like +"%'";
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(consulta);
			while (resultados.next()) {
				DocumentosCuentasXCobrar documentosCuentasXCobrar = new DocumentosCuentasXCobrar();
				documentosCuentasXCobrar.setSysPK(resultados.getInt(1));
				documentosCuentasXCobrar.setBonificaciones(resultados.getDouble(2));
				documentosCuentasXCobrar.setDebe(resultados.getDouble(3));
				documentosCuentasXCobrar.setDocumento(resultados.getInt(4));
				documentosCuentasXCobrar.setFecha(resultados.getDate(5));
				documentosCuentasXCobrar.setHaber(resultados.getDouble(6));
				documentosCuentasXCobrar.setNotas(resultados.getString(7));
				documentosCuentasXCobrar.setPagos(resultados.getDouble(8));
				documentosCuentasXCobrar.setReferencia(resultados.getString(9));
				documentosCuentasXCobrar.setXAplicar(resultados.getDouble(10));
				documentosCuentasXCobrar.setClienteFK(resultados.getInt(11));
				documentosCuentasXCobrar.setReciboFK(resultados.getInt(12));
				documentosCuentasXCobrar.setCotizacionFK(resultados.getInt(13));
				documentosCuentasXCobrar.setSaldo(resultados.getDouble(14));
				arrayListDocumentosCuentasXCobrar.add(documentosCuentasXCobrar);
			}//FIN WHILE
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY/CATCH
		return arrayListDocumentosCuentasXCobrar;
	}//FIN METODO

	public static ArrayList<DocumentosCuentasXCobrar> readSaldadosPorClienteFK(Connection connection, int clienteFK, Date fechaInicio, Date fechaFin, String like) {
		ArrayList<DocumentosCuentasXCobrar> arrayListDocumentosCuentasXCobrar = new ArrayList<DocumentosCuentasXCobrar>();
		String consulta = "SELECT  Sys_PK, Bonificacion, Debe, Documento, Fecha, Haber, Notas, Pagos, Referencia, XAplicar, ClienteFK, ReciboFK, CotizacionFK, (debe - pagos) AS Saldo FROM dcxc  WHERE (debe - pagos) = 0 AND ClienteFK = " + clienteFK +" AND dcxc.Sys_PK NOT IN (SELECT Sys_PK FROM dcxc WHERE Haber > 0) AND Documento = 1 AND (Fecha BETWEEN '"+ fechaInicio +"' AND '"+ fechaFin +"') AND Referencia LIKE '%"+ like +"%'";
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(consulta);
			while (resultados.next()) {
				DocumentosCuentasXCobrar documentosCuentasXCobrar = new DocumentosCuentasXCobrar();
				documentosCuentasXCobrar.setSysPK(resultados.getInt(1));
				documentosCuentasXCobrar.setBonificaciones(resultados.getDouble(2));
				documentosCuentasXCobrar.setDebe(resultados.getDouble(3));
				documentosCuentasXCobrar.setDocumento(resultados.getInt(4));
				documentosCuentasXCobrar.setFecha(resultados.getDate(5));
				documentosCuentasXCobrar.setHaber(resultados.getDouble(6));
				documentosCuentasXCobrar.setNotas(resultados.getString(7));
				documentosCuentasXCobrar.setPagos(resultados.getDouble(8));
				documentosCuentasXCobrar.setReferencia(resultados.getString(9));
				documentosCuentasXCobrar.setXAplicar(resultados.getDouble(10));
				documentosCuentasXCobrar.setClienteFK(resultados.getInt(11));
				documentosCuentasXCobrar.setReciboFK(resultados.getInt(12));
				documentosCuentasXCobrar.setCotizacionFK(resultados.getInt(13));
				documentosCuentasXCobrar.setSaldo(resultados.getDouble(14));
				arrayListDocumentosCuentasXCobrar.add(documentosCuentasXCobrar);
			}//FIN WHILE
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY/CATCH
		return arrayListDocumentosCuentasXCobrar;
	}//FIN METODO

	public static ArrayList<DocumentosCuentasXCobrar> readRecibosPorAplicarCliente(Connection connection, int clienteFK) {
		ArrayList<DocumentosCuentasXCobrar> arrayListDocumentosCuentasXCobrar = new ArrayList<DocumentosCuentasXCobrar>();
		String consulta = "SELECT Sys_PK, Bonificacion, Debe, Documento, Fecha, Haber, Notas, Pagos, Referencia, XAplicar, ClienteFK, ReciboFK, CotizacionFK FROM dcxc WHERE ClienteFK = "+ clienteFK +" AND Documento = 2 AND XAplicar > 0";
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(consulta);
			while (resultados.next()) {
				DocumentosCuentasXCobrar documentosCuentasXCobrar = new DocumentosCuentasXCobrar();
				documentosCuentasXCobrar.setSysPK(resultados.getInt(1));
				documentosCuentasXCobrar.setBonificaciones(resultados.getDouble(2));
				documentosCuentasXCobrar.setDebe(resultados.getDouble(3));
				documentosCuentasXCobrar.setDocumento(resultados.getInt(4));
				documentosCuentasXCobrar.setFecha(resultados.getDate(5));
				documentosCuentasXCobrar.setHaber(resultados.getDouble(6));
				documentosCuentasXCobrar.setNotas(resultados.getString(7));
				documentosCuentasXCobrar.setPagos(resultados.getDouble(8));
				documentosCuentasXCobrar.setReferencia(resultados.getString(9));
				documentosCuentasXCobrar.setXAplicar(resultados.getDouble(10));
				documentosCuentasXCobrar.setClienteFK(resultados.getInt(11));
				documentosCuentasXCobrar.setReciboFK(resultados.getInt(12));
				documentosCuentasXCobrar.setCotizacionFK(resultados.getInt(13));
				arrayListDocumentosCuentasXCobrar.add(documentosCuentasXCobrar);
			}//FIN WHILE
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY/CATCH
		return arrayListDocumentosCuentasXCobrar;
	}//FIN METODO

	public static DocumentosCuentasXCobrar readPorCotizacionFK(Connection connection, int cotizacionFK) {
		DocumentosCuentasXCobrar documentosCuentasXCobrar = new DocumentosCuentasXCobrar();
		String consulta = "SELECT Sys_PK, Bonificacion, Debe, Documento, Fecha, Haber, Notas, Pagos, Referencia, XAplicar, ClienteFK, ReciboFK, CotizacionFK FROM dcxc WHERE CotizacionFK =" + cotizacionFK;
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(consulta);
			while (resultados.next()) {
				documentosCuentasXCobrar.setSysPK(resultados.getInt(1));
				documentosCuentasXCobrar.setBonificaciones(resultados.getDouble(2));
				documentosCuentasXCobrar.setDebe(resultados.getDouble(3));
				documentosCuentasXCobrar.setDocumento(resultados.getInt(4));
				documentosCuentasXCobrar.setFecha(resultados.getDate(5));
				documentosCuentasXCobrar.setHaber(resultados.getDouble(6));
				documentosCuentasXCobrar.setNotas(resultados.getString(7));
				documentosCuentasXCobrar.setPagos(resultados.getDouble(8));
				documentosCuentasXCobrar.setReferencia(resultados.getString(9));
				documentosCuentasXCobrar.setXAplicar(resultados.getDouble(10));
				documentosCuentasXCobrar.setClienteFK(resultados.getInt(11));
				documentosCuentasXCobrar.setReciboFK(resultados.getInt(12));
				documentosCuentasXCobrar.setCotizacionFK(resultados.getInt(13));
			}//FIN WHILE
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY/CATCH
		return documentosCuentasXCobrar;
	}//FIN METODO

	public static boolean update(Connection connection,  DocumentosCuentasXCobrar documentosCuentasXCobrar) {
		String consulta = "UPDATE dcxc SET Bonificacion = ?, Debe = ?, Documento = ?, Haber = ?, Notas = ?, Pagos = ?, XAplicar = ?, ClienteFK = ?, ReciboFK = ?, CotizacionFK = ? WHERE Sys_PK = ?";
		try {
			PreparedStatement sentenciaPreparada = connection.prepareStatement(consulta);
			sentenciaPreparada.setDouble(1, documentosCuentasXCobrar.getBonificaciones());
			sentenciaPreparada.setDouble(2, documentosCuentasXCobrar.getDebe());
			sentenciaPreparada.setInt(3, documentosCuentasXCobrar.getDocumento());
			sentenciaPreparada.setDouble(4, documentosCuentasXCobrar.getHaber());
			sentenciaPreparada.setString(5, documentosCuentasXCobrar.getNotas());
			sentenciaPreparada.setDouble(6, documentosCuentasXCobrar.getPagos());
			sentenciaPreparada.setDouble(7, documentosCuentasXCobrar.getXAplicar());
			sentenciaPreparada.setInt(8, documentosCuentasXCobrar.getClienteFK());
			sentenciaPreparada.setInt(9, documentosCuentasXCobrar.getReciboFK());
			sentenciaPreparada.setInt(10, documentosCuentasXCobrar.getCotizacionFK());
			sentenciaPreparada.setInt(11, documentosCuentasXCobrar.getSysPK());
			sentenciaPreparada.execute();
			return true;
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
			return false;
		}//FIN TRY/CATCH
	}//FIN METODO

	public static boolean updateXAplicar(Connection connection,  DocumentosCuentasXCobrar documentosCuentasXCobrar) {
		String consulta = "UPDATE dcxc SET XAplicar = ? WHERE Sys_PK = ?";
		try {
			PreparedStatement sentenciaPreparada = connection.prepareStatement(consulta);
			sentenciaPreparada.setDouble(1, documentosCuentasXCobrar.getXAplicar());
			sentenciaPreparada.setInt(2, documentosCuentasXCobrar.getSysPK());
			sentenciaPreparada.execute();
			return true;
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
			return false;
		}//FIN TRY/CATCH
	}//FIN METODO

	public static boolean updatePagos(Connection connection,  DocumentosCuentasXCobrar documentosCuentasXCobrar) {
		String consulta = "UPDATE dcxc SET Pagos = ? WHERE Sys_PK = ?";
		try {
			PreparedStatement sentenciaPreparada = connection.prepareStatement(consulta);
			sentenciaPreparada.setDouble(1, documentosCuentasXCobrar.getPagos());
			sentenciaPreparada.setInt(2, documentosCuentasXCobrar.getSysPK());
			sentenciaPreparada.execute();
			return true;
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
			return false;
		}//FIN TRY/CATCH
	}//FIN METODO

	public static boolean updateBonificaciones(Connection connection,  DocumentosCuentasXCobrar documentosCuentasXCobrar) {
		String consulta = "UPDATE dcxc SET Pagos = ?, Bonificacion = ?  WHERE Sys_PK = ?";
		try {
			PreparedStatement sentenciaPreparada = connection.prepareStatement(consulta);
			sentenciaPreparada.setDouble(1, documentosCuentasXCobrar.getPagos());
			sentenciaPreparada.setDouble(2, documentosCuentasXCobrar.getBonificaciones());
			sentenciaPreparada.setInt(3, documentosCuentasXCobrar.getSysPK());
			sentenciaPreparada.execute();
			return true;
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
			return false;
		}//FIN TRY/CATCH
	}//FIN METODO

	public static boolean delete(Connection connection, DocumentosCuentasXCobrar documentosCuentasXCobrar) {
		String consulta = "DELETE FROM dcxc WHERE Sys_PK = ?";
		try {
			PreparedStatement sentenciaPreparada = connection.prepareStatement(consulta);
			sentenciaPreparada.setInt(1, documentosCuentasXCobrar.getSysPK());
			sentenciaPreparada.execute();
			return true;
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
			return false;
		}//FIN TRY/CATCH
	}//FIN METODO
}//FIN CLASE
