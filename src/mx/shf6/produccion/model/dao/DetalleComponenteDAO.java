package mx.shf6.produccion.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import mx.shf6.produccion.model.DetalleComponente;
import mx.shf6.produccion.utilities.Notificacion;

public class DetalleComponenteDAO {

	//METODO PARA CREAR UN REGISTRO
	public static boolean createDetalleComponente(Connection connection, DetalleComponente detalleComponente) {
		String consulta = "INSERT INTO detallecomponentes (ComponenteSuperiorFK, ComponenteInferiorFK, Cantidad, Notas) VALUES (?, ?, ?, ?)";
		try {
			PreparedStatement sentenciaPreparada = connection.prepareStatement(consulta);
			sentenciaPreparada.setInt(1, detalleComponente.getComponenteSuperiorFK());
			sentenciaPreparada.setInt(2, detalleComponente.getComponenteInferiorFK());
			sentenciaPreparada.setDouble(3, detalleComponente.getCantidad());
			sentenciaPreparada.setString(4, detalleComponente.getNotas());
			sentenciaPreparada.execute();
			return true;
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
			return false;
		}//FIN TRY/CATCH
	}//FIN METODO

	//METODO PARA OBTENER UN REGISTRO
	public static ArrayList<DetalleComponente> readDetalleComponente(Connection connection) {
		ArrayList<DetalleComponente> arrayListDetalleComponente = new ArrayList<DetalleComponente>();
		String consulta = "SELECT Sys_PK, ComponenteSuperiorFK, ComponenteInferiorFK, Cantidad, Notas FROM detallecomponentes";
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(consulta);
			while (resultados.next()) {
				DetalleComponente detalleComponente = new DetalleComponente();
				detalleComponente.setSysPK(resultados.getInt(1));
				detalleComponente.setComponenteSuperiorFK(resultados.getInt(2));
				detalleComponente.setComponenteInferiorFK(resultados.getInt(3));
				detalleComponente.setCantidad(resultados.getDouble(4));
				detalleComponente.setNotas(resultados.getString(5));
				arrayListDetalleComponente.add(detalleComponente);
			}//FIN WHILE
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY/CATCH
		return arrayListDetalleComponente;
	}//FIN METODO

	//METODO PARA OBTENER UN REGISTRO
	public static DetalleComponente readDetalleComponente(Connection connection, int sysPK) {
		DetalleComponente detalleComponente = new DetalleComponente();
		String consulta = "SELECT Sys_PK, ComponenteSuperiorFK, ComponenteInferiorFK, Cantidad, Notas FROM detallecomponentes WHERE Sys_PK = " + sysPK;
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(consulta);
			while (resultados.next()) {
				detalleComponente.setSysPK(resultados.getInt(1));
				detalleComponente.setComponenteSuperiorFK(resultados.getInt(2));
				detalleComponente.setComponenteInferiorFK(resultados.getInt(3));
				detalleComponente.setCantidad(resultados.getDouble(4));
				detalleComponente.setNotas(resultados.getString(5));
			}//FIN WHILE
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY/CATCH
		return detalleComponente;
	}//FIN METODO

	//METODO PARA OBTENER UN REGISTRO
	public static ArrayList<DetalleComponente> readDetalleComponenteSuperiorFK(Connection connection, int componenteSuperiorFK) {
		ArrayList<DetalleComponente> arrayListDetalleComponente = new ArrayList<DetalleComponente>();
		String consulta =  "SELECT detallecomponentes.Sys_PK, detallecomponentes.ComponenteSuperiorFK, detallecomponentes.ComponenteInferiorFK, detallecomponentes.Cantidad, detallecomponentes.Notas, cI.Descripcion AS DescripcionComponenteInferior, cI.NumeroParte AS NumeroParteInferior, cI.TipoComponente AS TipoComponenteInferior, cS.Descripcion AS DescripcionComponenteSuperior, cS.NumeroParte AS NumeroParteSuperior, cS.TipoComponente AS TipoComponenteSuperior FROM detallecomponentes INNER JOIN componentes AS cI ON cI.Sys_PK = detallecomponentes.ComponenteInferiorFK  INNER JOIN componentes AS cS ON cS.Sys_PK = detallecomponentes.ComponenteSuperiorFK WHERE ComponenteSuperiorFK =" + componenteSuperiorFK;
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(consulta);
			while (resultados.next()) {
				DetalleComponente detalleComponente = new DetalleComponente();
				detalleComponente.setSysPK(resultados.getInt(1));
				detalleComponente.setComponenteSuperiorFK(resultados.getInt(2));
				detalleComponente.setComponenteInferiorFK(resultados.getInt(3));
				detalleComponente.setCantidad(resultados.getDouble(4));
				detalleComponente.setNotas(resultados.getString(5));
				detalleComponente.setDescripcionComponenteInferior(resultados.getString(6));
				detalleComponente.setNumeroParteComponenteInferior(resultados.getString(7));
				detalleComponente.setNumeroDescripcionComponenteIferior();
				detalleComponente.setTipoComponenteInferior(resultados.getString(8));
				detalleComponente.setDescripcionComponenteSuperior(resultados.getString(9));
				detalleComponente.setNumeroParteComponenteSuperior(resultados.getString(10));
				detalleComponente.setTipoComponenteSuperior(resultados.getString(11));
				arrayListDetalleComponente.add(detalleComponente);
			}//FIN WHILE
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY/CATCH
		return arrayListDetalleComponente;
	}//FIN METODO

	public static DetalleComponente readDetalleComponenteSuperiorFKObject(Connection connection, int componenteSuperiorFK) {
		DetalleComponente detalleComponente = new DetalleComponente();
		String consulta =  "SELECT detallecomponentes.Sys_PK, detallecomponentes.ComponenteSuperiorFK, detallecomponentes.ComponenteInferiorFK, detallecomponentes.Cantidad, detallecomponentes.Notas, cI.Descripcion AS DescripcionComponenteInferior, cI.NumeroParte AS NumeroParteInferior, cI.TipoComponente AS TipoComponenteInferior, cS.Descripcion AS DescripcionComponenteSuperior, cS.NumeroParte AS NumeroParteSuperior, cS.TipoComponente AS TipoComponenteSuperior FROM detallecomponentes INNER JOIN componentes AS cI ON cI.Sys_PK = detallecomponentes.ComponenteInferiorFK  INNER JOIN componentes AS cS ON cS.Sys_PK = detallecomponentes.ComponenteSuperiorFK WHERE ComponenteSuperiorFK =" + componenteSuperiorFK;
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(consulta);
			while (resultados.next()) {
				detalleComponente.setSysPK(resultados.getInt(1));
				detalleComponente.setComponenteSuperiorFK(resultados.getInt(2));
				detalleComponente.setComponenteInferiorFK(resultados.getInt(3));
				detalleComponente.setCantidad(resultados.getDouble(4));
				detalleComponente.setNotas(resultados.getString(5));
				detalleComponente.setDescripcionComponenteInferior(resultados.getString(6));
				detalleComponente.setNumeroParteComponenteInferior(resultados.getString(7));
				detalleComponente.setNumeroDescripcionComponenteIferior();
				detalleComponente.setTipoComponenteInferior(resultados.getString(8));
				detalleComponente.setDescripcionComponenteSuperior(resultados.getString(9));
				detalleComponente.setNumeroParteComponenteSuperior(resultados.getString(10));
				detalleComponente.setTipoComponenteSuperior(resultados.getString(11));
			}//FIN WHILE
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY/CATCH
		return detalleComponente;
	}//FIN METODO
	
	public static DetalleComponente readDetalleComponenteInferiorFKObject(Connection connection, int componenteInferiorFK) {
		DetalleComponente detalleComponente = new DetalleComponente();
		String consulta =  "SELECT detallecomponentes.Sys_PK, detallecomponentes.ComponenteSuperiorFK, detallecomponentes.ComponenteInferiorFK, detallecomponentes.Cantidad, detallecomponentes.Notas, cI.Descripcion AS DescripcionComponenteInferior, cI.NumeroParte AS NumeroParteInferior, cI.TipoComponente AS TipoComponenteInferior, cS.Descripcion AS DescripcionComponenteSuperior, cS.NumeroParte AS NumeroParteSuperior, cS.TipoComponente AS TipoComponenteSuperior FROM detallecomponentes INNER JOIN componentes AS cI ON cI.Sys_PK = detallecomponentes.ComponenteInferiorFK  INNER JOIN componentes AS cS ON cS.Sys_PK = detallecomponentes.ComponenteSuperiorFK WHERE ComponenteInferiorFK =" + componenteInferiorFK;
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(consulta);
			while (resultados.next()) {
				detalleComponente.setSysPK(resultados.getInt(1));
				detalleComponente.setComponenteSuperiorFK(resultados.getInt(2));
				detalleComponente.setComponenteInferiorFK(resultados.getInt(3));
				detalleComponente.setCantidad(resultados.getDouble(4));
				detalleComponente.setNotas(resultados.getString(5));
				detalleComponente.setDescripcionComponenteInferior(resultados.getString(6));
				detalleComponente.setNumeroParteComponenteInferior(resultados.getString(7));
				detalleComponente.setNumeroDescripcionComponenteIferior();
				detalleComponente.setTipoComponenteInferior(resultados.getString(8));
				detalleComponente.setDescripcionComponenteSuperior(resultados.getString(9));
				detalleComponente.setNumeroParteComponenteSuperior(resultados.getString(10));
				detalleComponente.setTipoComponenteSuperior(resultados.getString(11));
			}//FIN WHILE
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY/CATCH
		return detalleComponente;
	}//FIN METODO
	
	//METODO PARA CREAR UN REGISTRO
	public static boolean updateDetalleComponente(Connection connection, DetalleComponente detalleComponente) {
		String consulta = "UPDATE detallecomponentes SET ComponenteSuperiorFK = ?, ComponenteInferiorFK = ?, Cantidad = ?, Notas = ? WHERE Sys_PK = ?";
		try {
			PreparedStatement sentenciaPreparada = connection.prepareStatement(consulta);
			sentenciaPreparada.setInt(1, detalleComponente.getComponenteSuperiorFK());
			sentenciaPreparada.setInt(2, detalleComponente.getComponenteInferiorFK());
			sentenciaPreparada.setDouble(3, detalleComponente.getCantidad());
			sentenciaPreparada.setString(4, detalleComponente.getNotas());
			sentenciaPreparada.execute();
			return true;
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
			return false;
		}//FIN TRY/CATCH
	}//FIN METODO

	//METODO PARA CREAR UN REGISTRO
	public static boolean deleteDetalleComponente(Connection connection, DetalleComponente detalleComponente) {
		String consulta = "DELETE FROM detallecomponentes WHERE Sys_PK = ?";
		try {
			PreparedStatement sentenciaPreparada = connection.prepareStatement(consulta);
			sentenciaPreparada.setInt(1, detalleComponente.getSysPK());
			sentenciaPreparada.execute();
			return true;
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
			return false;
		}//FIN TRY/CATCH
	}//FIN METODO

	//METODO PARA CONVERTIR ARRAYLIST EN OBSERVABLELIST
	public static ObservableList<DetalleComponente> toObservableList(ArrayList<DetalleComponente> arrayList) {
		ObservableList<DetalleComponente> listaObservableDetalleComponente = FXCollections.observableArrayList();
		for (DetalleComponente detalleComponente : arrayList)
			listaObservableDetalleComponente.add(detalleComponente);
		return listaObservableDetalleComponente;
	}//FIN METODO
	
}//FIN CLASE
