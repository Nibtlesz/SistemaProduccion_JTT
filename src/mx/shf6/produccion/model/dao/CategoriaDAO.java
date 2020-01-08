package mx.shf6.produccion.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import mx.shf6.produccion.model.Categoria;
import mx.shf6.produccion.utilities.Notificacion;

public class CategoriaDAO {

	//METODO PARA CREAR UN REGISTRO
	public static final boolean create (Connection connection, Categoria categoria) {
		String query = "INSERT INTO categorias (Codigo, Descripcion) VALUES (?, ?)";
		try {
			PreparedStatement sentenciaPreparada = connection.prepareStatement(query);
			sentenciaPreparada.setString(1, categoria.getCodigo());
			sentenciaPreparada.setString(2, categoria.getDescripcion());
			sentenciaPreparada.execute();
			return true;
		}catch (SQLException ex) {
			Notificacion.dialogoException(ex);
			return false;
		}//FIN TRY-CATCH
	}//FIN METODO

	public static final ArrayList<Categoria> readTodos (Connection connection){
		ArrayList<Categoria> arrayListCategoria = new ArrayList<Categoria>();
		String query = "SELECT Sys_PK, Codigo, Descripcion FROM categorias";
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(query);
			while (resultados.next()) {
				Categoria categoria = new Categoria();
				categoria.setSysPK(resultados.getInt(1));
				categoria.setCodigo(resultados.getString(2));
				categoria.setDescripcion(resultados.getString(3));
				arrayListCategoria.add(categoria);
			}//FIN WHILE
		}catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY-CATCH
		return arrayListCategoria;
	}//FIN METODO

	public static final Categoria readPorSysPK (Connection connection, int sysPK) {
		Categoria categoria = new Categoria();
		String query = "SELECT Sys_PK, Codigo, Descripcion FROM categorias WHERE Sys_PK = " + sysPK;
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(query);
			while(resultados.next()) {
				categoria.setSysPK(resultados.getInt(1));
				categoria.setCodigo(resultados.getString(2));
				categoria.setDescripcion(resultados.getString(3));
			}//FIN WHILE
		}catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY-CATCH
		return categoria;
	}//FIN METODO

	public static final Categoria readPorDescripcion (Connection connection, String descripcion) {
		Categoria categoria = new Categoria();
		String query = "SELECT Sys_PK, Codigo, Descripcion FROM categorias WHERE Descripcion = '"+ descripcion +"'";
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(query);
			while(resultados.next()) {
				categoria.setSysPK(resultados.getInt(1));
				categoria.setCodigo(resultados.getString(2));
				categoria.setDescripcion(resultados.getString(3));
			}//FIN WHILE
		}catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY-CATCH
		return categoria;
	}//FIN METODO

	public static final ArrayList<Categoria> readPorCodigo (Connection connection, String codigo){
		ArrayList<Categoria> arrayListCategoria = new ArrayList<Categoria>();
		String query = "SELECT Sys_PK, Codigo, Descripcion FROM categorias WHERE Codigo LIKE '%" + codigo + "%'";
		try {
			Statement sentencia =  connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(query);
			while (resultados.next()) {
				Categoria categoria = new Categoria();
				categoria.setSysPK(resultados.getInt(1));
				categoria.setCodigo(resultados.getString(2));
				categoria.setDescripcion(resultados.getString(3));
			}//FIN WHILE
		}catch(SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY-CATCH
		return arrayListCategoria;
	}//FIN METODO

	//METODO PARA LEER DESCRIPCIONES EN UN OBSERVABLElIST
	public static final ObservableList<String> readDescripciones (Connection connection){
		ObservableList<String> observableListDescripcion = FXCollections.observableArrayList();
		String query ="SELECT Sys_PK, Codigo, Descripcion FROM categorias ORDER BY Descripcion ASC";
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(query);
			while(resultados.next()) {
				observableListDescripcion.add(resultados.getString(3));
			}//FIN WHILE
		}catch(SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY-CATCH
		return observableListDescripcion;
	}//FIN METODO

	//METODO PARA ACTUALIZAR UN REGISTRO
	public static final boolean update(Connection connection, Categoria categoria) {
		String query = "UPDATE categorias SET Codigo = ?, Descripcion = ? WHERE Sys_PK = ?";
		try {
			PreparedStatement sentenciaPreparada = connection.prepareStatement(query);
			sentenciaPreparada.setString(1, categoria.getCodigo());
			sentenciaPreparada.setString(2, categoria.getDescripcion());
			sentenciaPreparada.setInt(3, categoria.getSysPK());
			sentenciaPreparada.execute();
			return true;
		}catch (SQLException ex) {
			Notificacion.dialogoException(ex);
			return false;
		}//FIN TRY-CATCH
	}//FIN METODO

	//METODO PARA ELIMINAR UN REGISTRO
	public static final boolean delete(Connection connection, Categoria categoria) {
		String query = "DELETE FROM categorias WHERE Sys_PK = ?";
		try {
			PreparedStatement sentenciaPreparada = connection.prepareStatement(query);
			sentenciaPreparada.setInt(1, categoria.getSysPK());
			sentenciaPreparada.execute();
			return true;
		}catch (SQLException ex) {
			Notificacion.dialogoException(ex);
			return false;
		}//FIN TRY-CATCH
	}//FIN METODO
}//FIN CLASE
