package mx.shf6.produccion.model.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import mx.shf6.produccion.model.Sesion;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class SesionDAO implements ObjectDAO {
	
	//METODO PARA HACER CREATE EN LA TABLA SESIONES
	@Override
	public boolean crear(Connection connection, Object sesion) {	
		Sesion claseSesion=(Sesion)sesion;
		String query=" INSERT INTO sesiones (fechaApertura, horaApertura, usuario) values ( ?, ?, ?)";
		try {	
			PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(query);
			preparedStatement.setDate(1, claseSesion.getFechaApertura());
			preparedStatement.setTime(2, claseSesion.getHoraApertura());
			preparedStatement.setInt(3, claseSesion.getUsuario(connection).getSysPk());
			preparedStatement.execute();
			return true;   
		} catch (SQLException e) {
			System.out.println("Error: En método crear");
			e.printStackTrace();
			return false;			
		}		
	}//FIN METODO
	
	
	//METODO PARA HACER SELECT EN LA TABLA SESIONES
	@Override
	public ArrayList<Object> leer(Connection connection, String campoBusqueda, String valorBusqueda) {
		String query ="";
		ArrayList<Object> listaSesion = new ArrayList<Object>();
		if(campoBusqueda.isEmpty() || valorBusqueda.isEmpty()) {
			query="SELECT * FROM sesiones ORDER BY sysPK;";
			try {
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery(query);  
				Sesion sesion = null;
				while (resultSet.next()) {
					sesion=new Sesion();
					sesion.setSysPk(Integer.parseInt(resultSet.getString(1)));
					sesion.setFechaApertura(resultSet.getDate(2));
					sesion.setHoraApertura(resultSet.getTime(3));
					sesion.setFechaCierre(resultSet.getDate(4));
					sesion.setHoraCierre(resultSet.getTime(5));
					sesion.setUsuarioFk(resultSet.getInt(6));
					listaSesion.add(sesion);
				}
			}catch (SQLException e) {
				System.out.println("Error: En método leer");
				e.printStackTrace();
			}
		}else {
			query="SELECT * FROM sesiones WHERE "+campoBusqueda+" = ? ORDER BY sysPK;";
			try {
				PreparedStatement preparedStatement = connection.prepareStatement(query);
				preparedStatement.setString(1, valorBusqueda);
				ResultSet resultSet=preparedStatement.executeQuery();
				Sesion sesion = null;
				while (resultSet.next()) {
					sesion=new Sesion();
					sesion.setSysPk(Integer.parseInt(resultSet.getString(1)));
					sesion.setFechaApertura(resultSet.getDate(2));
					sesion.setHoraApertura(resultSet.getTime(3));
					sesion.setFechaCierre(resultSet.getDate(4));
					sesion.setHoraCierre(resultSet.getTime(5));
					sesion.setUsuarioFk(resultSet.getInt(6));
					listaSesion.add(sesion);
				}
			}catch (SQLException e) {
				System.out.println("Error: En método leer");
				e.printStackTrace();
			}
		}
		return listaSesion;
	}//FIN METODO
	
	
	//METODO PARA HACER UPDATE EN LA TABLA SESIONES
	@Override
	public boolean modificar(Connection connection, Object sesion) {
		String query="UPDATE sesiones SET  fechaCierre= ?, horaCierre= ?  WHERE sysPK= ?";
		try {
			Sesion claseSesion=(Sesion)sesion;
			PreparedStatement preparedStmt = (PreparedStatement) connection.prepareStatement(query);
			preparedStmt.setDate(1, claseSesion.getFechaCierre());
			preparedStmt.setTime(2, claseSesion.getHoraCierre());
			preparedStmt.setInt(3, claseSesion.getSysPk());
			preparedStmt.execute();
			return true;
		} catch (SQLException e) {
			System.out.println("Error: En método modificar");
			e.printStackTrace();
			return false;
		}
	}//FIN METODO
	
	
	//METODO PARA HACER DELETE EN LA TABLA SESIONES
	@Override
	public boolean eliminar(Connection connection, Object sesion) {
		String query=" DELETE FROM sesiones WHERE sysPK= ?";
		try {	
			Sesion claseSesion=(Sesion)sesion;
			PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(query);
			preparedStatement.setInt(1, claseSesion.getSysPk());
			preparedStatement.execute();
			return true;
		} catch (SQLException e) {
			System.out.println("Error: En método eliminar");
			e.printStackTrace();
			return false;
		}		
	}//FIN METODO	

}//FIN CLASE
