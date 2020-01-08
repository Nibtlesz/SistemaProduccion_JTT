package mx.shf6.produccion.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import mx.shf6.produccion.model.ArchivoProyecto;
import mx.shf6.produccion.utilities.Notificacion;

public class ArchivoProyectoDAO {
	//METODO PARA CREAR UN REGISTRO
	public static boolean createArchivoProyecto(Connection connection, ArchivoProyecto archivoProyecto) {
		String consulta = "INSERT INTO archivoproyectos (Codigo, Descripcion,ProyectoFK) "
				+ "VALUES (?, ?, ?)";
		try {
			PreparedStatement sentenciaPreparada = connection.prepareStatement(consulta);
			sentenciaPreparada.setString(1, archivoProyecto.getCodigo());
			sentenciaPreparada.setString(2, archivoProyecto.getDescripcion());
			sentenciaPreparada.setInt(3,archivoProyecto.getProyectoFK());
			sentenciaPreparada.execute();
			return true;
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
			return false;
		}//FIN TRY/CATCH
	}//FIN METODO
	
	//METODO PARA OBTENER UN REGISTRO
	public static ArrayList<ArchivoProyecto> readArchivoProyecto(Connection connection) {
		ArrayList<ArchivoProyecto> arrayListArchivoProyecto = new ArrayList<ArchivoProyecto>();
		String consulta = "SELECT Sys_PK, Codigo, Descripcion,ClienteFK "
				+ "FROM archivoproyectos";
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(consulta);
			while (resultados.next()) {
				ArchivoProyecto archivoProyecto = new ArchivoProyecto();
				archivoProyecto.setSysPK(resultados.getInt(1));
				archivoProyecto.setCodigo(resultados.getString(2));
				archivoProyecto.setProyectoFK(resultados.getInt(3));
				arrayListArchivoProyecto.add(archivoProyecto);
			}//FIN WHILE
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY/CATCH
		return arrayListArchivoProyecto;
	}//FIN METODO
	
	//METODO PARA OBTENER UN REGISTRO
	public static ArchivoProyecto readArchivoProyecto(Connection connection, int sysPK) {
		ArchivoProyecto archivoProyecto = new ArchivoProyecto();
		String consulta = "SELECT Sys_PK, Codigo, Descripcion, Carpeta,ProyectoFK "
				+ "FROM archivoproyectos "
				+ "WHERE Sys_PK = " + sysPK;
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(consulta);
			while (resultados.next()) {
				archivoProyecto.setSysPK(resultados.getInt(1));
				archivoProyecto.setCodigo(resultados.getString(2));
				archivoProyecto.setDescripcion(resultados.getString(3));
				archivoProyecto.setProyectoFK(resultados.getInt(4));
			}//FIN WHILE
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY/CATCH
		return archivoProyecto;
	}//FIN METODO
	
	//METODO PARA OBTENER UN REGISTRO
	public static ArrayList<ArchivoProyecto> readArchivoProyecto(Connection connection, String like, int proyectoFK) {
		ArrayList<ArchivoProyecto> arrayListArchivoProyecto = new ArrayList<ArchivoProyecto>();
		String consulta = "SELECT Sys_PK, Codigo, Descripcion,ProyectoFK "
				+ "FROM archivoproyectos "
				+ "WHERE (Codigo LIKE '%" + like + "%' OR Descripcion LIKE '%" + like + "%') AND ProyectoFK = " + proyectoFK;
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(consulta);
			while (resultados.next()) {
				ArchivoProyecto archivoProyecto = new ArchivoProyecto();
				archivoProyecto.setSysPK(resultados.getInt(1));
				archivoProyecto.setCodigo(resultados.getString(2));
				archivoProyecto.setDescripcion(resultados.getString(3));
				archivoProyecto.setProyectoFK(resultados.getInt(4));
				arrayListArchivoProyecto.add(archivoProyecto);
			}//FIN WHILE
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY/CATCH
		return arrayListArchivoProyecto;
	}//FIN METODO
	
	//METODO PARA OBTENER UN REGISTRO
	public static ArrayList<ArchivoProyecto> readArchivoProyectoCliente(Connection connection, int proyectoFK){
		ArrayList<ArchivoProyecto> arrayListArchivoProyecto = new ArrayList<ArchivoProyecto>();
		String consulta = "SELECT Sys_PK, Codigo, Descripcion, ProyectoFK "
				+ " FROM archivoproyectos"
				+ " WHERE ProyectoFK= " + proyectoFK;
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(consulta);
			while (resultados.next()) {
				ArchivoProyecto archivoProyecto = new ArchivoProyecto();
				archivoProyecto.setSysPK(resultados.getInt(1));
				archivoProyecto.setCodigo(resultados.getString(2));
				archivoProyecto.setDescripcion(resultados.getString(3));
				archivoProyecto.setProyectoFK(resultados.getInt(4));
				arrayListArchivoProyecto.add(archivoProyecto);
			}//FIN WHILE
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY/CATCH
		return arrayListArchivoProyecto;
	}//FIN METODO
	
	//METODO PARA CREAR UN REGISTRO
	public static boolean updateArchivoProyecto(Connection connection, ArchivoProyecto archivoProyecto) {
		String consulta = "UPDATE archivoproyectos SET Codigo = ?, Descripcion = ?, ProyectoFK=? "
				+ "WHERE Sys_PK = ?";
		try {
			PreparedStatement sentenciaPreparada = connection.prepareStatement(consulta);
			sentenciaPreparada.setString(1, archivoProyecto.getCodigo());
			sentenciaPreparada.setString(2, archivoProyecto.getDescripcion());
			sentenciaPreparada.setInt(3,archivoProyecto.getProyectoFK());
			sentenciaPreparada.setInt(4, archivoProyecto.getSysPK());
			sentenciaPreparada.execute();
			return true;
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
			return false;
			}//FIN TRY/CATCH
	}//FIN METODO
	
	//METODO PARA CREAR UN REGISTRO
	public static boolean deleteArchivoProyecto(Connection connection, ArchivoProyecto archivoProyecto) {
		String consulta = "DELETE FROM archivoproyectos WHERE Sys_PK = ?";
		try {
			PreparedStatement sentenciaPreparada = connection.prepareStatement(consulta);
			sentenciaPreparada.setInt(1,archivoProyecto.getSysPK());
			sentenciaPreparada.execute();
			return true;
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
			return false;
		}//FIN TRY/CATCH
	}//FIN METODO
	
	//METODO PARA CONVERTIR ARRAYLIST EN OBSERVABLELIST
	public static ObservableList<ArchivoProyecto> toObservableList(ArrayList<ArchivoProyecto> arrayList) {
		ObservableList<ArchivoProyecto> listaObservableArchivoProyecto = FXCollections.observableArrayList();
		for (ArchivoProyecto proyecto : arrayList) 
			listaObservableArchivoProyecto.add(proyecto);
		return listaObservableArchivoProyecto;
	}//FIN METODO
}