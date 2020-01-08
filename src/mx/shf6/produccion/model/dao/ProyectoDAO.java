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
import mx.shf6.produccion.model.Proyecto;
import mx.shf6.produccion.utilities.Notificacion;

public class ProyectoDAO{

	//METODO PARA CREAR UN REGISTRO
	public static boolean createProyecto(Connection connection, Proyecto proyecto) {
		String consulta = "INSERT INTO proyectos (Codigo, Descripcion, Carpeta, EspecificacionTecnica,CostoDirecto, CostoIndirecto, Precio, ClienteFK, ComponenteFK) VALUES (?, ?, ?, ?,?,?,?,?,?)";
		try {
			PreparedStatement sentenciaPreparada = connection.prepareStatement(consulta);
			sentenciaPreparada.setString(1, proyecto.getCodigo());
			sentenciaPreparada.setString(2, proyecto.getDescripcion());
			sentenciaPreparada.setString(3, proyecto.getCarpeta());
			sentenciaPreparada.setString(4, proyecto.getEspecificacionTecnica());
			sentenciaPreparada.setDouble(5,proyecto.getCostoDirecto());
			sentenciaPreparada.setDouble(6, proyecto.getCostoIndirecto());
			sentenciaPreparada.setDouble(7, proyecto.getPrecio());
			sentenciaPreparada.setInt(8,proyecto.getClienteFK());
			sentenciaPreparada.setInt(9, proyecto.getComponenteFK());
			sentenciaPreparada.execute();
			return true;
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
			return false;
		}//FIN TRY/CATCH
	}//FIN METODO
	
	//METODO PARA OBTENER UN REGISTRO
	public static ArrayList<Proyecto> readProyecto(Connection connection) {
		ArrayList<Proyecto> arrayListProyecto = new ArrayList<Proyecto>();
		String consulta = "SELECT Sys_PK, Codigo, Descripcion, Carpeta, EspecificacionTecnica, CostoDirecto, CostoIndirecto,Precio,ClienteFK,ComponenteFK "
				+ "FROM proyectos";
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(consulta);
			while (resultados.next()) {
				Proyecto proyecto = new Proyecto();
				proyecto.setSysPK(resultados.getInt(1));
				proyecto.setCodigo(resultados.getString(2));
				proyecto.setDescripcion(resultados.getString(3));
				proyecto.setCarpeta(resultados.getString(4));
				proyecto.setEspecificacionTenica(resultados.getString(5));
				proyecto.setCostoDirecto(resultados.getDouble(6));
				proyecto.setCostoIndirecto(resultados.getDouble(7));
				proyecto.setPrecio(resultados.getDouble(8));
				proyecto.setClienteFK(resultados.getInt(9));
				proyecto.setComponenteFK(resultados.getInt(10));
				arrayListProyecto.add(proyecto);
			}//FIN WHILE
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY/CATCH
		return arrayListProyecto;
	}//FIN METODO
	
	//METODO PARA OBTENER UN REGISTRO
	public static Proyecto readProyecto(Connection connection, int sysPK) {
		Proyecto proyecto = new Proyecto();
		String consulta = "SELECT Sys_PK, Codigo, Descripcion, Carpeta, EspecificacionTecnica, CostoDirecto, CostoIndirecto, Precio, ClienteFK,ComponenteFK "
				+ "FROM proyectos "
				+ "WHERE Sys_PK = " + sysPK;
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(consulta);
			while (resultados.next()) {
				proyecto.setSysPK(resultados.getInt(1));
				proyecto.setCodigo(resultados.getString(2));
				proyecto.setDescripcion(resultados.getString(3));
				proyecto.setCarpeta(resultados.getString(4));
				proyecto.setEspecificacionTenica(resultados.getString(5));
				proyecto.setCostoDirecto(resultados.getDouble(6));
				proyecto.setCostoIndirecto(resultados.getDouble(7));
				proyecto.setPrecio(resultados.getDouble(8));
				proyecto.setClienteFK(resultados.getInt(9));
				proyecto.setComponenteFK(resultados.getInt(10));
			}//FIN WHILE
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY/CATCH
		return proyecto;
	}//FIN METODO
	
	//METODO PARA OBTENER UN REGISTRO
	public static Proyecto readProyectoPorCodigo(Connection connection, String codigo) {
		Proyecto proyecto = new Proyecto();
		String consulta = "SELECT Sys_PK, Codigo, Descripcion, Carpeta, EspecificacionTecnica, CostoDirecto, CostoIndirecto, Precio, ClienteFK,ComponenteFK "
				+ "FROM proyectos "
				+ "WHERE Codigo = '" + codigo + "'";
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(consulta);
			while (resultados.next()) {
				proyecto.setSysPK(resultados.getInt(1));
				proyecto.setCodigo(resultados.getString(2));
				proyecto.setDescripcion(resultados.getString(3));
				proyecto.setCarpeta(resultados.getString(4));
				proyecto.setEspecificacionTenica(resultados.getString(5));
				proyecto.setCostoDirecto(resultados.getDouble(6));
				proyecto.setCostoIndirecto(resultados.getDouble(7));
				proyecto.setPrecio(resultados.getDouble(8));
				proyecto.setClienteFK(resultados.getInt(9));
				proyecto.setComponenteFK(resultados.getInt(10));
			}//FIN WHILE
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY/CATCH
		return proyecto;
	}//FIN METODO
	
	//METODO PARA OBTENER UN REGISTRO
	public static ArrayList<Proyecto> readProyecto(Connection connection, String like, int clienteFK) {
		ArrayList<Proyecto> arrayListProyecto = new ArrayList<Proyecto>();
		String consulta = "SELECT Sys_PK, Codigo, Descripcion, Carpeta, EspecificacionTecnica, CostoDirecto, CostoIndirecto, Precio, ClienteFK,ComponenteFK "
				+ "FROM proyectos "
				+ "WHERE Codigo LIKE '%" + like + "%'"
				+ " AND ClienteFK =" + clienteFK ;
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(consulta);
			while (resultados.next()) {
				Proyecto proyecto = new Proyecto();
				proyecto.setSysPK(resultados.getInt(1));
				proyecto.setCodigo(resultados.getString(2));
				proyecto.setDescripcion(resultados.getString(3));
				proyecto.setCarpeta(resultados.getString(4));
				proyecto.setEspecificacionTenica(resultados.getString(5));
				proyecto.setCostoDirecto(resultados.getDouble(6));
				proyecto.setCostoIndirecto(resultados.getDouble(7));
				proyecto.setPrecio(resultados.getDouble(8));
				proyecto.setClienteFK(resultados.getInt(9));
				proyecto.setComponenteFK(resultados.getInt(10));
				arrayListProyecto.add(proyecto);
			}//FIN WHILE
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY/CATCH
		return arrayListProyecto;
	}//FIN METODO
	
	//METODO PARA OBTENER UN REGISTRO
	public static ArrayList<Proyecto> readProyectoCliente(Connection connection, int clienteFK){
		ArrayList<Proyecto> arrayListProyectoCliente = new ArrayList<Proyecto>();
		String consulta = "SELECT Sys_PK, Codigo, Descripcion, Carpeta, EspecificacionTecnica, CostoDirecto, CostoIndirecto, Precio, ClienteFK,ComponenteFK "
				+ " FROM proyectos"
				+" WHERE ClienteFK= " + clienteFK;
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(consulta);
			while (resultados.next()) {
				Proyecto proyecto = new Proyecto();
				proyecto.setSysPK(resultados.getInt(1));
				proyecto.setCodigo(resultados.getString(2));
				proyecto.setDescripcion(resultados.getString(3));
				proyecto.setCarpeta(resultados.getString(4));
				proyecto.setEspecificacionTenica(resultados.getString(5));
				proyecto.setCostoDirecto(resultados.getDouble(6));
				proyecto.setCostoIndirecto(resultados.getDouble(7));
				proyecto.setPrecio(resultados.getDouble(8));
				proyecto.setClienteFK(resultados.getInt(9));
				proyecto.setComponenteFK(resultados.getInt(10));
				arrayListProyectoCliente.add(proyecto);
			}//FIN WHILE
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY/CATCH
		return arrayListProyectoCliente;
	}//FIN METODO
	
	//METODO PARA CREAR UN REGISTRO
	public static boolean updateProyecto(Connection connection, Proyecto proyecto) {
		String consulta = "UPDATE proyectos SET Codigo = ?, Descripcion = ?, Carpeta = ?, EspecificacionTecnica = ?, CostoDirecto = ?, CostoIndirecto = ?, Precio= ?, ClienteFK=?, ComponenteFK = ? "
				+ "WHERE Sys_PK = ?";
		try {
			PreparedStatement sentenciaPreparada = connection.prepareStatement(consulta);
			sentenciaPreparada.setString(1, proyecto.getCodigo());
			sentenciaPreparada.setString(2, proyecto.getDescripcion());
			sentenciaPreparada.setString(3, proyecto.getCarpeta());
			sentenciaPreparada.setString(4, proyecto.getEspecificacionTecnica());
			sentenciaPreparada.setDouble(5,proyecto.getCostoDirecto());
			sentenciaPreparada.setDouble(6, proyecto.getCostoIndirecto());
			sentenciaPreparada.setDouble(7, proyecto.getPrecio());
			sentenciaPreparada.setInt(8,proyecto.getClienteFK());
			sentenciaPreparada.setInt(9, proyecto.getComponenteFK());
			sentenciaPreparada.setInt(10, proyecto.getSysPK());
			sentenciaPreparada.execute();
			return true;
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
			return false;
		}//FIN TRY/CATCH
	}//FIN METODO
	
	//METODO PARA CREAR UN REGISTRO
	public static boolean deleteProyecto(Connection connection, Object proyecto) {
		String consulta = "DELETE FROM proyectos WHERE Sys_PK = ?";
		try {
			Proyecto claseProyecto = (Proyecto)proyecto;
			PreparedStatement sentenciaPreparada = connection.prepareStatement(consulta);
			sentenciaPreparada.setInt(1, claseProyecto.getSysPK());
			sentenciaPreparada.execute();
			return true;
		} catch (SQLException ex) {
			Notificacion.dialogoAlerta(AlertType.WARNING, "No se puede eliminar este proyecto.", "Solo se puede eliminar PROYECTOS recien agregados.");
			return false;
		}//FIN TRY/CATCH
	}//FIN METODO
	
	//METODO PARA CONVERTIR ARRAYLIST EN OBSERVABLELIST
	public static ObservableList<Proyecto> toObservableList(ArrayList<Proyecto> arrayList) {
		ObservableList<Proyecto> listaObservableProyecto = FXCollections.observableArrayList();
		for (Proyecto proyecto : arrayList) 
			listaObservableProyecto.add(proyecto);
		return listaObservableProyecto;
	}//FIN METODO
	
}//FIN CLASE
