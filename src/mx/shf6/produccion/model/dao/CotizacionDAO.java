package mx.shf6.produccion.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert.AlertType;
import mx.shf6.produccion.model.Cotizacion;
import mx.shf6.produccion.utilities.Notificacion;

public class CotizacionDAO{

	//METODO PARA CREAR UN REGISTRO
	public static boolean createCotizacion(Connection connection, Cotizacion cotizacion) {
		String consulta = "INSERT INTO cotizaciones "
				+ "(Referencia, Fecha, Status, Solicitante, AreaDepartamento, TelefonoFax, "
				+ "Email, TipoServicio, FechaEntrega, CondicionEmbarque, CondicionPago, Moneda, TipoCambio, "
				+ "Observaciones, Vigencia, FolioFK, ClienteFK) "
				+ "VALUES (?, CURDATE(), 0, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
		try {
			PreparedStatement sentenciaPreparada = connection.prepareStatement(consulta);
			sentenciaPreparada.setString(1, cotizacion.getReferencia());
			sentenciaPreparada.setString(2, cotizacion.getSolicitante());
			sentenciaPreparada.setString(3, cotizacion.getAreaDepartamento());
			sentenciaPreparada.setString(4, cotizacion.getTelefonoFax());
			sentenciaPreparada.setString(5, cotizacion.getEmail());
			sentenciaPreparada.setString(6, cotizacion.getTipoServicio());
			sentenciaPreparada.setDate(7, cotizacion.getFechaEntrega());
			sentenciaPreparada.setString(8, cotizacion.getCondicionEmbarque());
			sentenciaPreparada.setString(9, cotizacion.getCondicionPago());
			sentenciaPreparada.setInt(10, cotizacion.getMoneda());
			sentenciaPreparada.setDouble(11, cotizacion.getTipoCambio());
			sentenciaPreparada.setString(12, cotizacion.getObservaciones());
			sentenciaPreparada.setString(13, cotizacion.getVigencia());
			sentenciaPreparada.setInt(14, cotizacion.getFolioFK());
			sentenciaPreparada.setInt(15, cotizacion.getClienteFK());
			sentenciaPreparada.execute();
			return true;
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
			return false;
		}//FIN TRY/CATCH
	}//FIN METODO
	
	//METODO PARA OBTENER UN REGISTRO
	public static ArrayList<Cotizacion> readCotizacion(Connection connection) {
		ArrayList<Cotizacion> arrayListCotizacion = new ArrayList<Cotizacion>();
		String consulta = "SELECT Sys_PK, Referencia, Fecha, Status, Solicitante, AreaDepartamento, TelefonoFax, "
				+ "Email, TipoServicio, FechaEntrega, CondicionEmbarque, CondicionPago, Moneda, TipoCambio, Observaciones, "
				+ "Vigencia, FolioFK, ClienteFK "
				+ "FROM cotizaciones";
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(consulta);
			while (resultados.next()) {
				Cotizacion cotizacion = new Cotizacion();
				cotizacion.setSysPK(resultados.getInt(1));
				cotizacion.setReferencia(resultados.getString(2));
				cotizacion.setFecha(resultados.getDate(3));
				cotizacion.setStatus(resultados.getInt(4));
				cotizacion.setSolicitante(resultados.getString(5));
				cotizacion.setAreaDepartamento(resultados.getString(6));
				cotizacion.setTelefonoFax(resultados.getString(7));
				cotizacion.setEmail(resultados.getString(8));
				cotizacion.setTipoServicio(resultados.getString(9));
				cotizacion.setFechaEntrega(resultados.getDate(10));
				cotizacion.setCondicionEmbarque(resultados.getString(11));
				cotizacion.setCondicionPago(resultados.getString(12));
				cotizacion.setMoneda(resultados.getInt(13));
				cotizacion.setTipoCambio(resultados.getDouble(14));
				cotizacion.setObservaciones(resultados.getString(15));
				cotizacion.setVigencia(resultados.getString(16));
				cotizacion.setFolioFK(resultados.getInt(17));
				cotizacion.setClienteFK(resultados.getInt(18));
				arrayListCotizacion.add(cotizacion);
			}//FIN WHILE
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY/CATCH
		return arrayListCotizacion;
	}//FIN METODO
	
	//METODO PARA OBTENER UN REGISTRO
	public static Cotizacion readCotizacion(Connection connection, int sysPK) {
		Cotizacion cotizacion = new Cotizacion();
		String consulta = "SELECT Sys_PK, Referencia, Fecha, Status, Solicitante, AreaDepartamento, TelefonoFax, "
				+ "Email, TipoServicio, FechaEntrega, CondicionEmbarque, CondicionPago, Moneda, TipoCambio, Observaciones, "
				+ "Vigencia, FolioFK, ClienteFK "
				+ "FROM cotizaciones "
				+ "WHERE Sys_PK = " + sysPK;
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(consulta);
			while (resultados.next()) {
				cotizacion.setSysPK(resultados.getInt(1));
				cotizacion.setReferencia(resultados.getString(2));
				cotizacion.setFecha(resultados.getDate(3));
				cotizacion.setStatus(resultados.getInt(4));
				cotizacion.setSolicitante(resultados.getString(5));
				cotizacion.setAreaDepartamento(resultados.getString(6));
				cotizacion.setTelefonoFax(resultados.getString(7));
				cotizacion.setEmail(resultados.getString(8));
				cotizacion.setTipoServicio(resultados.getString(9));
				cotizacion.setFechaEntrega(resultados.getDate(10));
				cotizacion.setCondicionEmbarque(resultados.getString(11));
				cotizacion.setCondicionPago(resultados.getString(12));
				cotizacion.setMoneda(resultados.getInt(13));
				cotizacion.setTipoCambio(resultados.getDouble(14));
				cotizacion.setObservaciones(resultados.getString(15));
				cotizacion.setVigencia(resultados.getString(16));
				cotizacion.setFolioFK(resultados.getInt(17));
				cotizacion.setClienteFK(resultados.getInt(18));
			}//FIN WHILE
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY/CATCH
		return cotizacion;
	}//FIN METODO
	
	//METODO PARA OBTENER UN REGISTRO
	public static ArrayList<Cotizacion> readCotizacionCliente(Connection connection, int clienteFK) {
		ArrayList<Cotizacion> arrayListCotizacion = new ArrayList<Cotizacion>();
		String consulta = "SELECT Sys_PK, Referencia, Fecha, Status, Solicitante, AreaDepartamento, TelefonoFax, "
				+ "Email, TipoServicio, FechaEntrega, CondicionEmbarque, CondicionPago, Moneda, TipoCambio, Observaciones, "
				+ "Vigencia, FolioFK, ClienteFK "
				+ "FROM cotizaciones WHERE ClienteFK=" + clienteFK;
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(consulta);
			while (resultados.next()) {
				Cotizacion cotizacion = new Cotizacion();
				cotizacion.setSysPK(resultados.getInt(1));
				cotizacion.setReferencia(resultados.getString(2));
				cotizacion.setFecha(resultados.getDate(3));
				cotizacion.setStatus(resultados.getInt(4));
				cotizacion.setSolicitante(resultados.getString(5));
				cotizacion.setAreaDepartamento(resultados.getString(6));
				cotizacion.setTelefonoFax(resultados.getString(7));
				cotizacion.setEmail(resultados.getString(8));
				cotizacion.setTipoServicio(resultados.getString(9));
				cotizacion.setFechaEntrega(resultados.getDate(10));
				cotizacion.setCondicionEmbarque(resultados.getString(11));
				cotizacion.setCondicionPago(resultados.getString(12));
				cotizacion.setMoneda(resultados.getInt(13));
				cotizacion.setTipoCambio(resultados.getDouble(14));
				cotizacion.setObservaciones(resultados.getString(15));
				cotizacion.setVigencia(resultados.getString(16));
				cotizacion.setFolioFK(resultados.getInt(17));
				cotizacion.setClienteFK(resultados.getInt(18));
				arrayListCotizacion.add(cotizacion);
			}//FIN WHILE
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY/CATCH
		return arrayListCotizacion;
	}//FIN METODO
	
	//METODO PARA OBTENER UN REGISTRO
	public static ArrayList<Cotizacion> readCotizacion(Connection connection, String like) {
		ArrayList<Cotizacion> arrayListCotizacion = new ArrayList<Cotizacion>();
		String consulta = "SELECT Sys_PK, Referencia, Fecha, Status, Solicitante, AreaDepartamento, TelefonoFax, "
				+ "Email, TipoServicio, FechaEntrega, CondicionEmbarque, CondicionPago, Moneda, TipoCambio, Observaciones, "
				+ "Vigencia, FolioFK, ClienteFK "
				+ "FROM cotizaciones "
				+ "WHERE Referencia LIKE '%" + like + "%'";
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(consulta);
			while (resultados.next()) {
				Cotizacion cotizacion = new Cotizacion();
				cotizacion.setSysPK(resultados.getInt(1));
				cotizacion.setReferencia(resultados.getString(2));
				cotizacion.setFecha(resultados.getDate(3));
				cotizacion.setStatus(resultados.getInt(4));
				cotizacion.setSolicitante(resultados.getString(5));
				cotizacion.setAreaDepartamento(resultados.getString(6));
				cotizacion.setTelefonoFax(resultados.getString(7));
				cotizacion.setEmail(resultados.getString(8));
				cotizacion.setTipoServicio(resultados.getString(9));
				cotizacion.setFechaEntrega(resultados.getDate(10));
				cotizacion.setCondicionEmbarque(resultados.getString(11));
				cotizacion.setCondicionPago(resultados.getString(12));
				cotizacion.setMoneda(resultados.getInt(13));
				cotizacion.setTipoCambio(resultados.getDouble(14));
				cotizacion.setObservaciones(resultados.getString(15));
				cotizacion.setVigencia(resultados.getString(16));
				cotizacion.setFolioFK(resultados.getInt(17));
				cotizacion.setClienteFK(resultados.getInt(18));
				arrayListCotizacion.add(cotizacion);
			}//FIN WHILE
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY/CATCH
		return arrayListCotizacion;
	}//FIN METODO
	
	//METODO PARA OBTENER UN REGISTRO
	public static ArrayList<Cotizacion> readCotizacionCliente(Connection connection, int clienteFK, String like) {
		ArrayList<Cotizacion> arrayListCotizacion = new ArrayList<Cotizacion>();
		String consulta = "SELECT Sys_PK, Referencia, Fecha, Status, Solicitante, AreaDepartamento, TelefonoFax, "
				+ "Email, TipoServicio, FechaEntrega, CondicionEmbarque, CondicionPago, Moneda, TipoCambio, Observaciones, "
				+ "Vigencia, FolioFK, ClienteFK "
				+ "FROM cotizaciones "
				+ "WHERE ClienteFK = " + clienteFK +" AND Referencia LIKE '%" + like + "%'";
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(consulta);
			while (resultados.next()) {
				Cotizacion cotizacion = new Cotizacion();
				cotizacion.setSysPK(resultados.getInt(1));
				cotizacion.setReferencia(resultados.getString(2));
				cotizacion.setFecha(resultados.getDate(3));
				cotizacion.setStatus(resultados.getInt(4));
				cotizacion.setSolicitante(resultados.getString(5));
				cotizacion.setAreaDepartamento(resultados.getString(6));
				cotizacion.setTelefonoFax(resultados.getString(7));
				cotizacion.setEmail(resultados.getString(8));
				cotizacion.setTipoServicio(resultados.getString(9));
				cotizacion.setFechaEntrega(resultados.getDate(10));
				cotizacion.setCondicionEmbarque(resultados.getString(11));
				cotizacion.setCondicionPago(resultados.getString(12));
				cotizacion.setMoneda(resultados.getInt(13));
				cotizacion.setTipoCambio(resultados.getDouble(14));
				cotizacion.setObservaciones(resultados.getString(15));
				cotizacion.setVigencia(resultados.getString(16));
				cotizacion.setFolioFK(resultados.getInt(17));
				cotizacion.setClienteFK(resultados.getInt(18));
				arrayListCotizacion.add(cotizacion);
			}//FIN WHILE
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY/CATCH
		return arrayListCotizacion;
	}//FIN METODO
	
	//METODO PARA ATUALIZAR UN REGISTRO
	public static boolean updateCotizacion(Connection connection, Cotizacion cotizacion) {
		String consulta = "UPDATE cotizaciones SET Referencia = ?, Status = ?, Solicitante = ?, "
				+ "AreaDepartamento = ?, TelefonoFax = ?, Email = ?, TipoServicio = ?, "
				+ "FechaEntrega = ?, CondicionEmbarque = ?, CondicionPago = ?, Moneda = ?, "
				+ "TipoCambio = ?, Observaciones = ?, Vigencia = ?, FolioFK = ?, ClienteFK = ? "
				+ "WHERE Sys_PK = ?";
		try {
			PreparedStatement sentenciaPreparada = connection.prepareStatement(consulta);
			sentenciaPreparada.setString(1, cotizacion.getReferencia());
			sentenciaPreparada.setInt(2, cotizacion.getStatus());
			sentenciaPreparada.setString(3, cotizacion.getSolicitante());
			sentenciaPreparada.setString(4, cotizacion.getAreaDepartamento());
			sentenciaPreparada.setString(5, cotizacion.getTelefonoFax());
			sentenciaPreparada.setString(6, cotizacion.getEmail());
			sentenciaPreparada.setString(7, cotizacion.getTipoServicio());
			sentenciaPreparada.setDate(8, cotizacion.getFechaEntrega());
			sentenciaPreparada.setString(9, cotizacion.getCondicionEmbarque());
			sentenciaPreparada.setString(10, cotizacion.getCondicionPago());
			sentenciaPreparada.setInt(11, cotizacion.getMoneda());
			sentenciaPreparada.setDouble(12, cotizacion.getTipoCambio());			
			sentenciaPreparada.setString(13, cotizacion.getObservaciones());
			sentenciaPreparada.setString(14, cotizacion.getVigencia());
			sentenciaPreparada.setInt(15, cotizacion.getFolioFK());
			sentenciaPreparada.setInt(16, cotizacion.getClienteFK());
			sentenciaPreparada.setInt(17, cotizacion.getSysPK());
			sentenciaPreparada.execute();
			return true;
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
			return false;
		}//FIN TRY/CATCH
	}//FIN METODO
	
	//METODO PARA CREAR UN REGISTRO
	public static boolean deleteCotizacion(Connection connection, Cotizacion cotizacion) {
		String consulta = "DELETE FROM cotizaciones WHERE Sys_PK = ?";
		try {
			PreparedStatement sentenciaPreparada = connection.prepareStatement(consulta);
			sentenciaPreparada.setInt(1, cotizacion.getSysPK());
			sentenciaPreparada.execute();
			return true;
		} catch (SQLException ex) {
			Notificacion.dialogoAlerta(AlertType.INFORMATION, "Mensaje del Sistema", "Solo se pueden eliminar cotizaciones recien registradas");
			return false;
		}//FIN TRY/CATCH
	}//FIN METODO
	
	//METODO PARA CONVERTIR ARRAYLIST EN OBSERVABLELIST
	public static ObservableList<Cotizacion> toObservableList(ArrayList<Cotizacion> arrayList) {
		ObservableList<Cotizacion> listaObservableCotizacion = FXCollections.observableArrayList();
		for (Cotizacion cotizacion : arrayList) 
			listaObservableCotizacion.add(cotizacion);
		return listaObservableCotizacion;
	}//FIN METODO
	
	//METODO PARA OBTENER EL ULTIMO SYSPK AGREGADO A LA TABLA
	public static int ultimoSysPk(Connection connection) {
		String query = "SELECT Sys_PK FROM cotizaciones order by Sys_PK asc";
		int ultimoSysPk = 0;
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			while (resultSet.next())
				ultimoSysPk=resultSet.getInt(1);
			return ultimoSysPk;
		}catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY/CATCH
		return ultimoSysPk;
	}//FIN METODO

}//FIN CLASE