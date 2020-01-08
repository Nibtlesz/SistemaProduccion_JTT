package mx.shf6.produccion.model.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import mx.shf6.produccion.utilities.Notificacion;

public class Seguridad {
	
	public static boolean verificarAcceso(Connection connection, int grupoUsuario, String codigoItem) {
		
		//METODO PARA VERIFICAR EL ACCESO A LOS COMPONENTES SEGUN EL USUARIO
		String query="SELECT * "
				+ "FROM rolgruposusuario "
				+ "INNER JOIN roles ON rolgruposusuario.RolFK = roles.Sys_PK "
				+ "WHERE rolgruposusuario.GrupoUsuarioFK = "+grupoUsuario+" "
				+ "AND roles.CodigoItem = '"+codigoItem+"';";			
		try {	
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			while (resultSet.next()) {					
				return true;
			}//FIN WHILE
			return false;
		} catch (SQLException e) {
			Notificacion.dialogoException(e);
			return false;
		}//FIN TRY-CATCH	
	}//FIN METODO
	
}//FIN CLASE
