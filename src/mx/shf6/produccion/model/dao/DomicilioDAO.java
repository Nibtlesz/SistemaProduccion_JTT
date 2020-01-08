package mx.shf6.produccion.model.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;

import mx.shf6.produccion.model.Domicilio;
import mx.shf6.produccion.utilities.Notificacion;


public class DomicilioDAO {
	
	//METODO PARA HACER CREATE EN LA TABLA DOMICILIOS
	public static boolean createDomicilio(Connection connection, Domicilio domicilio) {	
		String query = "INSERT INTO domicilios (Calle, NumeroInterior, NumeroExterior, Colonia, Localidad, Municipio, Estado, CodigoPostal, Pais) "
				+ "values ( ?, ?, ?, ?, ?, ?, ?, ?,?)";
		try {	
			PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(query);
			preparedStatement.setString(1, domicilio.getCalle());
			preparedStatement.setString(2, domicilio.getNumeroInterior());
			preparedStatement.setString(3, domicilio.getNumeroExterior());
			preparedStatement.setString(4, domicilio.getColonia());
			preparedStatement.setString(5, domicilio.getLocalidad());
			preparedStatement.setString(6, domicilio.getMunicipio());
			preparedStatement.setString(7, domicilio.getEstado());
			preparedStatement.setString(8, domicilio.getCodigoPostal());
			preparedStatement.setString(9, domicilio.getPais());
			preparedStatement.execute();
			return true;   
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
			return false;			
		}//FIN TRY/CATCH
	}//FIN METODO	
	
	//METODO PARA HACER SELECT EN LA TABLA DOMICILIOS
	
	//METODO PARA OBTENER UN REGISTRO
	public static ArrayList<Domicilio> readDomicilio(Connection connection) {
		ArrayList<Domicilio> arrayListDomicilio = new ArrayList<Domicilio>();
		String consulta = "SELECT Sys_PK, Calle, NumeroExterior, NumeroInterior, Colonia, Localidad, Municipio, Estado, CodigoPostal, Pais FROM domicilios";
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(consulta);
			while (resultados.next()) {
				Domicilio domicilio = new Domicilio();
				domicilio.setSysPK(resultados.getInt(1));
				domicilio.setCalle(resultados.getString(2));
				domicilio.setNumeroExterior(resultados.getString(3));
				domicilio.setNumeroInterior(resultados.getString(4));
				domicilio.setColonia(resultados.getString(5));
				domicilio.setLocalidad(resultados.getString(6));
				domicilio.setMunicipio(resultados.getString(7));
				domicilio.setEstado(resultados.getString(8));
				domicilio.setCodigoPostal(resultados.getString(9));
				domicilio.setPais(resultados.getString(10));
				arrayListDomicilio.add(domicilio);
			}//FIN WHILE
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY/CATCH
		return arrayListDomicilio;
	}//FIN METODO
	
	//METODO PARA OBTENER UN REGISTRO
	public static Domicilio readDomicilio(Connection connection, int sysPK) {
		Domicilio domicilio = new Domicilio();
		String consulta = "SELECT Sys_PK, Calle, NumeroExterior, NumeroInterior, Colonia, Localidad, Municipio, Estado, CodigoPostal, Pais FROM domicilios WHERE Sys_PK=" + sysPK;
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(consulta);
			while (resultados.next()) {
				domicilio.setSysPK(resultados.getInt(1));
				domicilio.setCalle(resultados.getString(2));
				domicilio.setNumeroExterior(resultados.getString(3));
				domicilio.setNumeroInterior(resultados.getString(4));
				domicilio.setColonia(resultados.getString(5));
				domicilio.setLocalidad(resultados.getString(6));
				domicilio.setMunicipio(resultados.getString(7));
				domicilio.setEstado(resultados.getString(8));
				domicilio.setCodigoPostal(resultados.getString(9));
				domicilio.setPais(resultados.getString(10));
			}//FIN WHILE
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY/CATCH
		return domicilio;
	}//FIN METODO
	
	public static boolean updateDomicilio(Connection connection, Domicilio domicilio) {
		String consulta = "UPDATE domicilios SET Calle = ?, NumeroExterior = ?, NumeroInterior = ?, Colonia = ?, Localidad = ?, Municipio = ?, Estado = ?, CodigoPostal = ?, Pais = ? WHERE Sys_PK = ?";
		try {
			PreparedStatement sentenciaPreparada = connection.prepareStatement(consulta);
			sentenciaPreparada.setString(1, domicilio.getCalle());
			sentenciaPreparada.setString(2, domicilio.getNumeroExterior());
			sentenciaPreparada.setString(3, domicilio.getNumeroInterior());
			sentenciaPreparada.setString(4,  domicilio.getColonia());
			sentenciaPreparada.setString(5, domicilio.getLocalidad());
			sentenciaPreparada.setString(6, domicilio.getMunicipio());
			sentenciaPreparada.setString(7, domicilio.getEstado());
			sentenciaPreparada.setString(8, domicilio.getCodigoPostal());
			sentenciaPreparada.setString(9, domicilio.getPais());
			sentenciaPreparada.setInt(10, domicilio.getSysPK());
			sentenciaPreparada.execute();
			return true;
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
			return false;
		}//FIN TRY/CATCH
	}//FIN METODO	
	
	
	//METODO PARA HACER DELETE EN LA TABLA DOMICILIOS
	public static boolean deleteDomicilio(Connection connection, Object domicilio) {
		String query = "DELETE FROM domicilios WHERE Sys_PK = ?";
		try {	
			Domicilio claseDomicilio=(Domicilio)domicilio;
			PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(query);
			preparedStatement.setInt(1, claseDomicilio.getSysPK());
			preparedStatement.execute();
			return true;
		} catch (SQLException e) {
			Notificacion.dialogoException(e);
			return false;
		}//FIN TRY/CATCH	
	}//FIN METODO		
	
	//METODO PARA OBTENER EL ULTIMO SYSPK AGREGADO A LA TABLA 
	public static int ultimoSysPk(Connection connection) {
		String query = "SELECT Sys_PK FROM domicilios order by Sys_PK asc";
		int ultimoSysPk = 0;
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			while (resultSet.next())
				ultimoSysPk=resultSet.getInt(1);
			return ultimoSysPk;
		}catch (SQLException e) {
			System.out.println("Error: En método leer");
			e.printStackTrace();
		}//FIN TRY/CATCH
		return ultimoSysPk;
	}//FIN METODO

	

}//FIN CLASE
