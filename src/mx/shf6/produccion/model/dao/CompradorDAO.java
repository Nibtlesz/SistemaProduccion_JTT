package mx.shf6.produccion.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import mx.shf6.produccion.model.Comprador;
import mx.shf6.produccion.utilities.Notificacion;

public class CompradorDAO {
		
	//METODO PARA CREAR UN REGISTRO
	public static boolean createCromprador(Connection connection, Comprador comprador) {
		String consulta = "INSERT INTO compradores (Nombre, Correo, Telefono, TelefonoAuxiliar, AreaDepartamento, ClienteFK) VALUES (?, ?, ?, ?, ?, ?)";
		try {
			PreparedStatement sentenciaPreparada = connection.prepareStatement(consulta);
			sentenciaPreparada.setString(1, comprador.getNombre());
			sentenciaPreparada.setString(2, comprador.getCorreo());
			sentenciaPreparada.setString(3, comprador.getTelefono());
			sentenciaPreparada.setString(4, comprador.getTelefonoAuxiliar());
			sentenciaPreparada.setString(5, comprador.getAreaDepartamento());
			sentenciaPreparada.setInt(6, comprador.getClienteFK());
			sentenciaPreparada.execute();
			return true;
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
			return false;
		}//FIN TRY CATCH
	}//FIN METODO
	
	public static ArrayList<Comprador> readTodos(Connection connection) {
		ArrayList<Comprador> arrayListComprador = new ArrayList<Comprador>();
		String consulta = "SELECT Sys_PK, Nombre, Correo, Telefono, TelefonoAuxiliar, AreaDepartamento, ClienteFK FROM compradores";
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(consulta);
			while(resultados.next()) {
				Comprador comprador = new Comprador();
				comprador.setSysPK(resultados.getInt(1));
				comprador.setNombre(resultados.getString(2));
				comprador.setCorreo(resultados.getString(3));
				comprador.setTelefono(resultados.getString(4));
				comprador.setTelefonoAuxiliar(resultados.getString(5));
				comprador.setAreaDepartamento(resultados.getString(6));
				comprador.setClienteFK(resultados.getInt(7));
				arrayListComprador.add(comprador);
			}//FIN WHILE
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY CATCH
		return arrayListComprador;
	}//FIN METODO
	
	public static ArrayList<Comprador> readCompradorNombre(Connection connection, String like, int clienteFK) {
		ArrayList<Comprador> arrayListComprador = new ArrayList<Comprador>();
		String consulta = "SELECT Sys_PK, Nombre, Correo, Telefono, TelefonoAuxiliar, AreaDepartamento, ClienteFK FROM compradores WHERE Nombre LIKE '%" + like + "%' AND ClienteFK = " + clienteFK;
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(consulta);
			while(resultados.next()) {
				Comprador comprador = new Comprador();
				comprador.setSysPK(resultados.getInt(1));
				comprador.setNombre(resultados.getString(2));
				comprador.setCorreo(resultados.getString(3));
				comprador.setTelefono(resultados.getString(4));
				comprador.setTelefonoAuxiliar(resultados.getString(5));
				comprador.setAreaDepartamento(resultados.getString(6));
				comprador.setClienteFK(resultados.getInt(7));
				arrayListComprador.add(comprador);
			}//FIN WHILE
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY CATCH
		return arrayListComprador;
	}//FIN METODO
	
	public static Comprador readCompradorSys(Connection connection, int sysPK) {
		Comprador comprador = new Comprador();
		String consulta = "SELECT Sys_PK, Nombre, Correo, Telefono, TelefonoAuxiliar, AreaDepartamento, ClienteFK FROM compradores WHERE Sys_PK = " + sysPK ;
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(consulta);
			while(resultados.next()) {
				comprador.setSysPK(resultados.getInt(1));
				comprador.setNombre(resultados.getString(2));
				comprador.setCorreo(resultados.getString(3));
				comprador.setTelefono(resultados.getString(4));
				comprador.setTelefonoAuxiliar(resultados.getString(5));
				comprador.setAreaDepartamento(resultados.getString(6));
				comprador.setClienteFK(resultados.getInt(7));
			}//FIN WHILE
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY CATCH
		return comprador;
	}//FIN METODO
	
	public static Comprador readCompradorNombre(Connection connection, String nombre) {
		Comprador comprador = new Comprador();
		String consulta = "SELECT Sys_PK, Nombre, Correo, Telefono, TelefonoAuxiliar, AreaDepartamento, ClienteFK FROM compradores WHERE Nombre = '" + nombre + "'" ;
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(consulta);
			while(resultados.next()) {
				comprador.setSysPK(resultados.getInt(1));
				comprador.setNombre(resultados.getString(2));
				comprador.setCorreo(resultados.getString(3));
				comprador.setTelefono(resultados.getString(4));
				comprador.setTelefonoAuxiliar(resultados.getString(5));
				comprador.setAreaDepartamento(resultados.getString(6));
				comprador.setClienteFK(resultados.getInt(7));
			}//FIN WHILE
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY CATCH
		return comprador;
	}//FIN METODO
	
	public static ArrayList<Comprador> readCompradores(Connection connection, int clienteFK) {
		ArrayList<Comprador> listaCompradores = new ArrayList<Comprador>();
		String consulta = "SELECT Sys_PK, Nombre, Correo, Telefono, TelefonoAuxiliar, AreaDepartamento, ClienteFK FROM compradores WHERE ClienteFK = " + clienteFK;
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(consulta);
			while(resultados.next()) {
				Comprador comprador = new Comprador();
				comprador.setSysPK(resultados.getInt(1));
				comprador.setNombre(resultados.getString(2));
				comprador.setCorreo(resultados.getString(3));
				comprador.setTelefono(resultados.getString(4));
				comprador.setTelefonoAuxiliar(resultados.getString(5));
				comprador.setAreaDepartamento(resultados.getString(6));
				comprador.setClienteFK(resultados.getInt(7));
				listaCompradores.add(comprador);
			}//FIN WHILE
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY CATCH
		return listaCompradores;
	}//FIN METODO
	
	public static boolean updateComprador(Connection connection, Comprador comprador) {
		String consulta = "UPDATE compradores SET Nombre = ?, Correo = ?, Telefono = ?, TelefonoAuxiliar = ?, AreaDepartamento = ?, ClienteFK = ? WHERE Sys_PK = ?";
		try {
			PreparedStatement sentenciaPreparada = connection.prepareStatement(consulta);
			sentenciaPreparada.setString(1, comprador.getNombre());
			sentenciaPreparada.setString(2, comprador.getCorreo());
			sentenciaPreparada.setString(3, comprador.getTelefono());
			sentenciaPreparada.setString(4, comprador.getTelefonoAuxiliar());
			sentenciaPreparada.setString(5, comprador.getAreaDepartamento());
			sentenciaPreparada.setInt(6, comprador.getClienteFK());
			sentenciaPreparada.setInt(7, comprador.getSysPK());
			sentenciaPreparada.execute();
			return true;
		} catch(SQLException ex) {
			Notificacion.dialogoException(ex);
			return false;
		}//FIN TRY CATCH
	}//FIN METODO
	
	public static boolean deleteComprador(Connection connection, Comprador comprador) {
		String consulta = "DELETE FROM compradores WHERE Sys_PK = ?";
		try {
			PreparedStatement sentenciaPreparada = connection.prepareStatement(consulta);
			sentenciaPreparada.setInt(1, comprador.getSysPK());
			sentenciaPreparada.execute();
			return true;
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
			return false;
		}//FINAL TRY CATCH
	}//FIN METODO
	
	public static ObservableList<String> leerCompradores(Connection connection, int sysPK) {
		String query ="";
		ObservableList<String> listaCompradores = FXCollections.observableArrayList();
		query="SELECT Nombre FROM compradores WHERE ClienteFK = " + sysPK + " ORDER BY Nombre ASC";
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			while (resultSet.next()) {
				listaCompradores.add(resultSet.getString(1));				
			}//FIN WHILE
		}catch (SQLException e) {
			Notificacion.dialogoException(e);
		}//FIN TRY-CATCH
		return listaCompradores;
	}//FIN METODO
}//FIN CLASE
