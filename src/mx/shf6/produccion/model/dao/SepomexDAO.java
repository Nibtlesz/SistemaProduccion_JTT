package mx.shf6.produccion.model.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import mx.shf6.produccion.utilities.Notificacion;

public class SepomexDAO {

	public ObservableList<String> leerEstados(Connection connection) {
		String query ="";
		ObservableList<String> listaEstados = FXCollections.observableArrayList();
		query="SELECT estado FROM sepomex group by estado order by estado asc";
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			while (resultSet.next()) {
				listaEstados.add(resultSet.getString(1));				
			}//FIN WHILE
		}catch (SQLException e) {
			Notificacion.dialogoException(e);
		}//FIN TRY-CATCH
		return listaEstados;
	}//FIN METODO
	
	public ObservableList<String> leerMunicipios(Connection connection, String estado) {
		String query ="";
		ObservableList<String> listaEstados = FXCollections.observableArrayList();
		query="SELECT municipio "
				+ "FROM sepomex "
				+ "WHERE Estado='" + estado + "' "
				+ "GROUP BY municipio "
				+ "ORDER BY municipio ASC;";
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			while (resultSet.next()) {
				listaEstados.add(resultSet.getString(1));				
			}//FIN WHILE
		}catch (SQLException e) {
			Notificacion.dialogoException(e);
		}//FIN TRY-CATCH
		return listaEstados;
	}//FIN METODO
	
	public ObservableList<String> leerPaises(Connection connection){
		String query = "SELECT Nombre FROM paises GROUP BY Nombre ORDER BY Nombre ASC";
		ObservableList<String> listaPaises = FXCollections	.observableArrayList();
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(query);
			while(resultados.next()) {
				listaPaises.add(resultados.getString(1));
			}//FIN WHILE
		}catch(SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY-CATCH
		return listaPaises;
	}//FIN METODO
}//FIN CLASE