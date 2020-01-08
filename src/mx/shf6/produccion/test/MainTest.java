package mx.shf6.produccion.test;



import java.sql.Connection;

import mx.shf6.produccion.model.Usuario;
import mx.shf6.produccion.model.dao.UsuarioDAO;
import mx.shf6.produccion.utilities.ConnectionDB;



public class MainTest  {

	private static ConnectionDB conexionBD = new ConnectionDB("produccion_jtt","192.168.1.107", "conn01", "Simons83Mx");
	private static Connection connection = conexionBD.conectarMySQL();
	  
	  public static void main(String[] args) {
		  
		for(Usuario u : UsuarioDAO.readTodos(connection))
			System.out.println(u.getUsuario());
	  }
}
