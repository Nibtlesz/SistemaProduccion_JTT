package mx.shf6.produccion.model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import mx.shf6.produccion.model.Cardex;
import mx.shf6.produccion.utilities.Notificacion;

public class CardexDAO {

	//METODO PARA CREAR UN REGISTRO
	public static final boolean create (Connection connection, Cardex cardex) {
		String query =  "INSERT INTO cardex (Fecha, Referencia, Nota, Tipo, CategoriaFK) VALUES (CURDATE(), ?, ?, ?, ?)";
		try {
			PreparedStatement sentenciaPreparada = connection.prepareStatement(query);
			sentenciaPreparada.setString(1, cardex.getReferencia());
			sentenciaPreparada.setString(2, cardex.getNota());
			sentenciaPreparada.setInt(3, cardex.getTipo());
			sentenciaPreparada.setInt(4, cardex.getCategoriaFK());
			sentenciaPreparada.execute();
			return true;
		}catch(SQLException ex) {
			Notificacion.dialogoException(ex);
			return false;
		}//FIN TRY-CATCH
	}//FIN METODO
	
	//METODO PARA LEER REGISTROS
	public static final ArrayList<Cardex> readTodos(Connection connection) {
		ArrayList<Cardex> arrayListCardex = new ArrayList<Cardex>();
		String query = "SELECT Sys_PK, Fecha, Referenia, Nota, Tipo, CategoriaFK FROM cardex";
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(query);
			while (resultados.next()) {
				Cardex cardex = new Cardex();
				cardex.setSysPK(resultados.getInt(1));
				cardex.setFecha(resultados.getDate(2));
				cardex.setReferencia(resultados.getString(3));
				cardex.setNota(resultados.getString(4));
				cardex.setTipo(resultados.getInt(5));
				cardex.setCategoriaFK(resultados.getInt(6));
				arrayListCardex.add(cardex);
			}//FIN WHILE
		}catch(SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY-CATCH
		return arrayListCardex;
	}//FIN METODO
	
	//METODO PARA LEER UN REGISTRO POR SU SYSPK
	public static final Cardex readPorSysPK(Connection connection, int sysPK) {
		Cardex cardex = new Cardex();
		String query = "SELECT Sys_PK, Fecha, Referencia, Nota, Tipo, CategoriaFK FROM cardex WHERE Sys_PK = " + sysPK;
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(query);
			while(resultados.next()) {
				cardex.setSysPK(resultados.getInt(1));
				cardex.setFecha(resultados.getDate(2));
				cardex.setReferencia(resultados.getString(3));
				cardex.setNota(resultados.getString(4));
				cardex.setTipo(resultados.getInt(5));
				cardex.setCategoriaFK(resultados.getInt(6));
			}//FIN WHILE
		}catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY-CATCH
		return cardex;
	}//FIN METODO
	
	//METODO PARA LEER REGISTROS POR FECHA
	public static final ArrayList<Cardex> readPorFecha (Connection connection, Date fecha){
		ArrayList<Cardex> arrayListcardex = new ArrayList<Cardex>();
		String query = "SELECT Sys_PK, Fecha, Referencia, Nota, Tipo, CategoriaFK FROM cardex WHERE Fecha = '" + fecha + "'";
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(query);
			while(resultados.next()) {
				Cardex cardex = new Cardex();
				cardex.setSysPK(resultados.getInt(1));
				cardex.setFecha(resultados.getDate(2));
				cardex.setReferencia(resultados.getString(3));
				cardex.setNota(resultados.getString(4));
				cardex.setTipo(resultados.getInt(5));
				cardex.setCategoriaFK(resultados.getInt(6));
				arrayListcardex.add(cardex);
			}//FIN WHILE
		}catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY-CATCH
		return arrayListcardex;
	}//FIN CLASE
	
	//METODO PARA LEER UN REGISTRO SEGUN SU REFERENCIA
	public static final ArrayList<Cardex> readLikeReferencia(Connection connection, String referencia){
		ArrayList<Cardex> arrayListCardex = new ArrayList<Cardex>();
		String query = "SELECT Sys_PK, Fecha, Referencia, Nota, Tipo, CategoriaFK FROM cardex WHERE Referencia LIKE '%" + referencia + "'%";
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(query);
			while(resultados.next()) {
				Cardex cardex = new Cardex();
				cardex.setSysPK(resultados.getInt(1));
				cardex.setFecha(resultados.getDate(2));
				cardex.setReferencia(resultados.getString(3));
				cardex.setNota(resultados.getString(4));
				cardex.setTipo(resultados.getInt(5));
				cardex.setCategoriaFK(resultados.getInt(6));
				arrayListCardex.add(cardex);
			}//FIN WHILE
		}catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY-CATCH
		return arrayListCardex;
 	}//FIN METODO
	
	//METODO PARA ACTUALIZAR UN REGISTRO
	public static final boolean update(Connection connection, Cardex cardex) {
		String query = "UPDATE cardex SET Referencia = ?, Nota = ?, Tipo = ?, CategoriaFK = ? WHERE Sys_PK = ?";
		try{
			PreparedStatement sentenciaPreparada = connection.prepareStatement(query);
			sentenciaPreparada.setString(1, cardex.getReferencia());
			sentenciaPreparada.setString(2, cardex.getNota());
			sentenciaPreparada.setInt(3, cardex.getTipo());
			sentenciaPreparada.setInt(4, cardex.getCategoriaFK());
			sentenciaPreparada.setInt(5, cardex.getSysPK());
			return true;
		}catch (SQLException ex) {
			return false;
		}//FIN TRY-CATCH
	}//FIN METODO
	
	//METODO PARA ELIMINAR UN REGISTRO
	public static final boolean delete(Connection connection, Cardex cardex) {
		String query = "DELETE FROM cardex WHERE SysPK = ?";
		try {
			PreparedStatement sentenciaPreparada = connection.prepareStatement(query);
			sentenciaPreparada.setInt(1, cardex.getSysPK());
			return true;
		}catch(SQLException ex) {
			Notificacion.dialogoException(ex);
			return false;
		}//FIN TRY-CATCH
	}//FIN METODO
	
	//METODO PARA OBTENER EL ULTIMO SYSPK
	public static final int ultimoSysPK(Connection connection ) {
		String query = "SELECT Sys_PK FROM cardex ORDER BY Sys_PK ASC";
		int ultimoSysPK = 0;
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(query);
			while(resultados.next()) {
				ultimoSysPK = resultados.getInt(1);
			}//FIN WHILE
			return ultimoSysPK;
		}catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY-CATCH
		return ultimoSysPK;
	}//FIN METODO
}//FIN CLASE
