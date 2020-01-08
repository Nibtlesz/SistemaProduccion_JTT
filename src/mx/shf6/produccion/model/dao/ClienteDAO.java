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
import mx.shf6.produccion.model.Cliente;
import mx.shf6.produccion.utilities.Notificacion;

public class ClienteDAO{

	//METODO PARA CREAR UN REGISTRO
	public static boolean createCliente(Connection connection, Cliente cliente) {
		String consulta = "INSERT INTO clientes (Codigo, Nombre, Status, FechaRegistro, RegistroContribuyente, Telefono, Correo, RutaCarpeta, DomicilioFK) VALUES (?, ?, ?, CURDATE(), ?, ?, ?, ?, ?)";
		try {
			PreparedStatement sentenciaPreparada = connection.prepareStatement(consulta);
			sentenciaPreparada.setString(1, cliente.getCodigo());
			sentenciaPreparada.setString(2, cliente.getNombre());
			sentenciaPreparada.setInt(3, cliente.getStatus());
			sentenciaPreparada.setString(4, cliente.getRegistroContribuyente());
			sentenciaPreparada.setString(5, cliente.getTelefono());
			sentenciaPreparada.setString(6, cliente.getCorreo());
			sentenciaPreparada.setString(7, cliente.getRutaCarpeta());
			sentenciaPreparada.setInt(8, cliente.getDomicilioFK());
			sentenciaPreparada.execute();
			return true;
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
			return false;
		}//FIN TRY/CATCH
	}//FIN METODO
	
	//METODO PARA OBTENER UN REGISTRO
	public static ArrayList<Cliente> readCliente(Connection connection) {
		ArrayList<Cliente> arrayListCliente = new ArrayList<Cliente>();
		String consulta = "SELECT Sys_PK, Codigo, Nombre, Status, FechaRegistro, RegistroContribuyente, Telefono, Correo, RutaCarpeta, DomicilioFK FROM clientes";
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(consulta);
			while (resultados.next()) {
				Cliente cliente = new Cliente();
				cliente.setSysPK(resultados.getInt(1));
				cliente.setCodigo(resultados.getString(2));
				cliente.setNombre(resultados.getString(3));
				cliente.setStatus(resultados.getInt(4));
				cliente.setFechaRegistro(resultados.getDate(5));
				cliente.setRegistroContribuyente(resultados.getString(6));
				cliente.setTelefono(resultados.getString(7));
				cliente.setCorreo(resultados.getString(8));
				cliente.setRutaCarpeta(resultados.getString(9));
				cliente.setDomicilioFK(resultados.getInt(10));
				arrayListCliente.add(cliente);
			}//FIN WHILE
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY/CATCH
		return arrayListCliente;
	}//FIN METODO
	
	//METODO PARA OBTENER UN REGISTRO
	public static Cliente readCliente(Connection connection, int sysPK) {
		Cliente cliente = new Cliente();
		String consulta = "SELECT Sys_PK, Codigo, Nombre, Status, FechaRegistro, RegistroContribuyente, Telefono, Correo, RutaCarpeta, DomicilioFK FROM clientes WHERE Sys_PK = " + sysPK;
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(consulta);
			while (resultados.next()) {
				cliente.setSysPK(resultados.getInt(1));
				cliente.setCodigo(resultados.getString(2));
				cliente.setNombre(resultados.getString(3));
				cliente.setStatus(resultados.getInt(4));
				cliente.setFechaRegistro(resultados.getDate(5));
				cliente.setRegistroContribuyente(resultados.getString(6));
				cliente.setTelefono(resultados.getString(7));
				cliente.setCorreo(resultados.getString(8));
				cliente.setRutaCarpeta(resultados.getString(9));
				cliente.setDomicilioFK(resultados.getInt(10));
			}//FIN WHILE
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY/CATCH
		return cliente;
	}//FIN METODO
	
	public static Cliente readClienteNombre(Connection connection, String nombre) {
		Cliente cliente = new Cliente();
		String consulta = "SELECT Sys_PK, Codigo, Nombre, Status, FechaRegistro, RegistroContribuyente, Telefono, Correo, RutaCarpeta, DomicilioFK FROM clientes WHERE Nombre = '" + nombre + "'";
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(consulta);
			while (resultados.next()) {
				cliente.setSysPK(resultados.getInt(1));
				cliente.setCodigo(resultados.getString(2));
				cliente.setNombre(resultados.getString(3));
				cliente.setStatus(resultados.getInt(4));
				cliente.setFechaRegistro(resultados.getDate(5));
				cliente.setRegistroContribuyente(resultados.getString(6));
				cliente.setTelefono(resultados.getString(7));
				cliente.setCorreo(resultados.getString(8));
				cliente.setRutaCarpeta(resultados.getString(9));
				cliente.setDomicilioFK(resultados.getInt(10));
			}//FIN WHILE
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY/CATCH
		return cliente;
	}//FIN METODO
	
	//METODO PARA OBTENER UN REGISTRO
	public static ArrayList<Cliente> readCliente(Connection connection, String like) {
		ArrayList<Cliente> arrayListCliente = new ArrayList<Cliente>();
		String consulta = "SELECT Sys_Pk, Codigo, Nombre, Status, FechaRegistro, RegistroContribuyente, Telefono, Correo, RutaCarpeta, DomicilioFK FROM clientes WHERE Nombre LIKE '%" + like + "%'";
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(consulta);
			while (resultados.next()) {
				Cliente cliente = new Cliente();
				cliente.setSysPK(resultados.getInt(1));
				cliente.setCodigo(resultados.getString(2));
				cliente.setNombre(resultados.getString(3));
				cliente.setStatus(resultados.getInt(4));
				cliente.setFechaRegistro(resultados.getDate(5));
				cliente.setRegistroContribuyente(resultados.getString(6));
				cliente.setTelefono(resultados.getString(7));
				cliente.setCorreo(resultados.getString(8));
				cliente.setRutaCarpeta(resultados.getString(9));
				cliente.setDomicilioFK(resultados.getInt(10));
				arrayListCliente.add(cliente);
			}//FIN WHILE
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY/CATCH
		return arrayListCliente;
	}//FIN METODO
	
	//METODO PARA CREAR UN REGISTRO
	public static boolean updateCliente(Connection connection, Cliente cliente) {
		String consulta = "UPDATE clientes SET Codigo = ?, Nombre = ?, Status = ?, RegistroContribuyente = ?, Telefono = ?, Correo = ?, RutaCarpeta = ?, DomicilioFK = ? WHERE Sys_PK = ?";
		try {
			PreparedStatement sentenciaPreparada = connection.prepareStatement(consulta);
			sentenciaPreparada.setString(1, cliente.getCodigo());
			sentenciaPreparada.setString(2, cliente.getNombre());
			sentenciaPreparada.setInt(3, cliente.getStatus());
			sentenciaPreparada.setString(4, cliente.getRegistroContribuyente());
			sentenciaPreparada.setString(5, cliente.getTelefono());
			sentenciaPreparada.setString(6, cliente.getCorreo());
			sentenciaPreparada.setString(7, cliente.getRutaCarpeta());
			sentenciaPreparada.setInt(8, cliente.getDomicilioFK());
			sentenciaPreparada.setInt(9, cliente.getSysPK());
			sentenciaPreparada.execute();
			return true;
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
			return false;
		}//FIN TRY/CATCH
	}//FIN METODO
	
	//METODO PARA CREAR UN REGISTRO
	public static boolean deleteCliente(Connection connection, Cliente cliente) {
		String consulta = "DELETE FROM clientes WHERE Sys_PK = ?";
		try {
			PreparedStatement sentenciaPreparada = connection.prepareStatement(consulta);
			sentenciaPreparada.setInt(1, cliente.getSysPK());
			sentenciaPreparada.execute();
			
			return true;
		} catch (SQLException ex) {
			Notificacion.dialogoAlerta(AlertType.WARNING, "ERROR", "Solo se puede eliminar CLIENTES recien registradas.");
			//Notificacion.dialogoException(ex);
			return false;
		}//FIN TRY/CATCH
	}//FIN METODO
	
	//METODO PARA CONVERTIR ARRAYLIST EN OBSERVABLELIST
	public static ObservableList<Cliente> toObservableList(ArrayList<Cliente> arrayList) {
		ObservableList<Cliente> listaObservableCliente = FXCollections.observableArrayList();
		for (Cliente cliente : arrayList) 
			listaObservableCliente.add(cliente);
		return listaObservableCliente;
	}//FIN METODO
	
	public static ObservableList<String> listaNombresClientes(Connection connection) {
		ObservableList<String> listaNombresClientes = FXCollections.observableArrayList();
		String consulta = "SELECT Nombre FROM clientes ORDER BY Nombre ASC";
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(consulta);
			while (resultados.next()) {
				listaNombresClientes.add(resultados.getString(1));
			}//FIN WHILE
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY/CATCH
		return listaNombresClientes;
	}//FIN METODO
	
}//FIN CLASE
