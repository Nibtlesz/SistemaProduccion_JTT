package mx.shf6.produccion.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import mx.shf6.produccion.model.Folio;
import mx.shf6.produccion.utilities.Notificacion;

public class FolioDAO {

	//METODO PARA CREAR UN REGISTRO
	public static boolean createFolio(Connection connection, Folio folio) {
		String consulta = "INSERT INTO folio (Folio, Serie, Descripcion) VALUES (?, 1, ?)";
		try {
			PreparedStatement sentenciaPreparada = connection.prepareStatement(consulta);
			sentenciaPreparada.setString(1, folio.getFolio());
			sentenciaPreparada.setString(2, folio.getDescripcion());
			sentenciaPreparada.execute();
			return true;
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
			return false;
		}//FIN TRY/CATCH
	}//FIN METODO

	//METODO PARA OBTENER UN REGISTRO
	public static ArrayList<Folio> readFolio(Connection connection) {
		ArrayList<Folio> arrayListFolio = new ArrayList<Folio>();
		String consulta = "SELECT Sys_PK, Folio, Serie, Descripcion FROM folios";
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(consulta);
			while (resultados.next()) {
				Folio folio = new Folio();
				folio.setSysPK(resultados.getInt(1));
				folio.setFolio(resultados.getString(2));
				folio.setSerie(resultados.getInt(3));
				folio.setDescripcion(resultados.getString(4));
				arrayListFolio.add(folio);
			}//FIN WHILE
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY/CATCH
		return arrayListFolio;
	}//FIN METODO

	public static int readSeriePorFolio(Connection connection, String folio) {
		int serie = 0;
		String consulta = "SELECT Sys_PK, Folio, Serie, Descripcion FROM folios WHERE Folio = '" + folio + "'";
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(consulta);
			while (resultados.next()) {
				serie = resultados.getInt(3);
			}//FIN WHILE
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY/CATCH
		return serie;
	}//FIN METODO

	//METODO PARA OBTENER UN REGISTRO
	public static Folio readFolio(Connection connection, int sysPK) {
		Folio folio = new Folio();
		String consulta = "SELECT Sys_PK, Folio, Serie, Descripcion FROM folios WHERE Sys_PK = " + sysPK;
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(consulta);
			while (resultados.next()) {
				folio.setSysPK(resultados.getInt(1));
				folio.setFolio(resultados.getString(2));
				folio.setSerie(resultados.getInt(3));
				folio.setDescripcion(resultados.getString(4));
			}//FIN WHILE
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY/CATCH
		return folio;
	}//FIN METODO

	//METODO PARA OBTENER UN REGISTRO
	public static Folio readFolioByFolio(Connection connection, String byFolio) {
		Folio folio = new Folio();
		String consulta = "SELECT Sys_PK, Folio, Serie, Descripcion FROM folios WHERE Folio = '" + byFolio + "'";
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(consulta);
			while (resultados.next()) {
				folio.setSysPK(resultados.getInt(1));
				folio.setFolio(resultados.getString(2));
				folio.setSerie(resultados.getInt(3));
				folio.setDescripcion(resultados.getString(4));
			}//FIN WHILE
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY/CATCH
		return folio;
	}//FIN METODO

	//METODO PARA CREAR UN REGISTRO
	public static boolean updateFolio(Connection connection, Folio folio) {
		String consulta = "UPDATE folios SET Folio = ?, Serie = ?, Descripcion = ? WHERE Sys_PK = ?";
		try {
			PreparedStatement sentenciaPreparada = connection.prepareStatement(consulta);
			sentenciaPreparada.setString(1, folio.getFolio());
			sentenciaPreparada.setInt(2, folio.getSerie());
			sentenciaPreparada.setString(2, folio.getDescripcion());
			sentenciaPreparada.setInt(4, folio.getSysPK());
			sentenciaPreparada.execute();
			return true;
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
			return false;
		}//FIN TRY/CATCH
	}//FIN METODO

	//METODO PARA CREAR UN REGISTRO
	public static boolean deleteFolio(Connection connection, Folio folio) {
		String consulta = "DELETE FROM folios WHERE Sys_PK = ?";
		try {
			PreparedStatement sentenciaPreparada = connection.prepareStatement(consulta);
			sentenciaPreparada.setInt(1, folio.getSysPK());
			sentenciaPreparada.execute();
			return true;
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
			return false;
		}//FIN TRY/CATCH
	}//FIN METODO

	//METODO PARA CONVERTIR ARRAYLIST EN OBSERVABLELIST
	public static ObservableList<Folio> toObservableList(ArrayList<Folio> arrayList) {
		ObservableList<Folio> listaObservableFolio = FXCollections.observableArrayList();
		for (Folio folio : arrayList)
			listaObservableFolio.add(folio);
		return listaObservableFolio;
	}//FIN METODO

}//FIN CLASE
