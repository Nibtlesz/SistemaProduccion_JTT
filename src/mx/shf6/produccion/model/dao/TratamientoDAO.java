package mx.shf6.produccion.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import mx.shf6.produccion.model.Tratamiento;
import mx.shf6.produccion.utilities.Notificacion;

public class TratamientoDAO {

	//METODO PARA CREAR UN REGISTRO
	public static boolean createTratamiento(Connection connection,Tratamiento tratamiento ) {
		String consulta = "INSERT INTO tratamiento (Codigo, Descripcion, Status) VALUES (?, ?, ?)";
		try {
			PreparedStatement sentenciaPreparada = connection.prepareStatement(consulta);
			sentenciaPreparada.setString(1, tratamiento.getCodigo());
			sentenciaPreparada.setString(2, tratamiento.getDescripcion());
			sentenciaPreparada.setInt(3, tratamiento.getStatusFK());
			sentenciaPreparada.execute();
			return true;
		}catch(SQLException ex) {
			Notificacion.dialogoException(ex);
			return false;
		}//FIN TRY/CATCH
	}//FIN METODO
	
	//METODO PARA OBTENER UN REGISTRO
	public static ArrayList<Tratamiento> readTratamiento(Connection connection) {
		ArrayList<Tratamiento> arrayListTratamiento = new ArrayList<Tratamiento>();
		String consulta = "SELECT Sys_PK, Codigo, Descripcion, Status FROM tratamiento";
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(consulta);  
			while (resultados.next()) {
				Tratamiento tratamiento = new Tratamiento();
				tratamiento.setSysPK(resultados.getInt(1));
				tratamiento.setCodigo(resultados.getString(2));
				tratamiento.setDescripcion(resultados.getString(3));
				tratamiento.setStatus(resultados.getInt(4));
				arrayListTratamiento.add(tratamiento);
			}//FIN WHILE
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY/CATCH
		return arrayListTratamiento;
	}//FIN METODO	
	
	//METODO PARA OBTENER UN REGISTRO
	public static Tratamiento readTratamiento(Connection connection, int sysPk) {
		Tratamiento tratamiento = new Tratamiento();
		String consulta = "SELECT Sys_PK, Codigo, Descripcion, Status FROM tratamiento WHERE Sys_PK =" + sysPk;
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(consulta);
			while (resultados.next()) {
				tratamiento.setSysPK(resultados.getInt(1));
				tratamiento.setCodigo(resultados.getString(2));
				tratamiento.setDescripcion(resultados.getString(3));
				tratamiento.setStatus(resultados.getInt(4));
			}//FIN WHILE
		} catch (SQLException e) {
			Notificacion.dialogoException(e);
		}//FIN TRY/CATCH
		return tratamiento;
	}//FIN METODO	
	
	//METODO PARA OBTENER UN REGISTRO
	public static ArrayList<Tratamiento> readTratamiento(Connection connection, String like) {
		ArrayList<Tratamiento> arrayListTratamiento = new ArrayList<Tratamiento>();
		String consulta = "SELECT Sys_PK, Codigo, Descripcion, Status FROM tratamiento WHERE Codigo LIKE '%" + like + "%'OR Descripcion LIKE'%" + like + "%';";
		try {	
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(consulta);
			while(resultados.next()) {
				Tratamiento tratamiento = new Tratamiento();
				tratamiento.setSysPK(resultados.getInt(1));
				tratamiento.setCodigo(resultados.getString(2));
				tratamiento.setDescripcion(resultados.getString(3));
				tratamiento.setStatus(resultados.getInt(4));
				arrayListTratamiento.add(tratamiento);
			}//FIN WHILE
		} catch (SQLException e) {
			Notificacion.dialogoException(e);
		}//FIN TRY/CATCH
		return arrayListTratamiento;
	}//FIN METODO		
	
	//METODO PARA MODIFICAR UN REGISTRO
	public static boolean updateTratamiento(Connection connection, Tratamiento tratamiento) {
		String consulta = "UPDATE tratamiento SET Codigo = ?, Descripcion = ?, Status = ? WHERE Sys_PK = ?";
		try {
			PreparedStatement sentenciaPreparada = connection.prepareStatement(consulta);
			sentenciaPreparada.setString(1,  tratamiento.getCodigo());
			sentenciaPreparada.setString(2,  tratamiento.getDescripcion());
			sentenciaPreparada.setInt(3,  tratamiento.getStatusFK());
			sentenciaPreparada.setInt(4,  tratamiento.getSysPK());
			sentenciaPreparada.execute();
			return true;
		}catch (SQLException e) {
			Notificacion.dialogoException(e);
			return false;
		}//FIN TRY/CATCH
	}//FIN METODO
	
	//METODO PARA ELIMINAR UN REGISTRO
	public static boolean deleteTratamiento(Connection connection, Tratamiento tratamiento) {
		String consulta = "DELETE FROM tratamiento where Sys_PK = ?";
		try {
			PreparedStatement sentenciaPreparada = connection.prepareStatement(consulta);
			sentenciaPreparada.setInt(1, tratamiento.getSysPK());
			sentenciaPreparada.execute();
			return true;
		}catch(SQLException ex) {
			Notificacion.dialogoException(ex);
			return false;
		}//FIN TRY/CATCH
	}//FIN METODO
	
	//METODO PARA CONVERTIR EL AGUA EN VINO
	public static ObservableList<Tratamiento> toObservableList(ArrayList<Tratamiento> arrayList){
		ObservableList<Tratamiento> listaObservableTratamiento = FXCollections.observableArrayList();
		for	(Tratamiento venta : arrayList)
			listaObservableTratamiento.add(venta);
		return listaObservableTratamiento;
	}//FIN METODO
	
	public static ObservableList<String> listaTiposTratamiento(Connection connection) {
		ObservableList<String> listaTiposTratamiento = FXCollections.observableArrayList();
		String consulta = "SELECT Descripcion FROM tratamiento ORDER BY Descripcion ASC";
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(consulta);
			while (resultados.next()) {
				listaTiposTratamiento.add(resultados.getString(1));
			}//FIN WHILE
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY/CATCH
		return listaTiposTratamiento;
	}//FIN METODO

}//FIN CLASE