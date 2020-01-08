package mx.shf6.produccion.model.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;


import mx.shf6.produccion.model.Usuario;
import mx.shf6.produccion.utilities.Notificacion;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class UsuarioDAO {

	//CONSTANTES
	public static final int NO_REGISTRADO = 0;
	public static final int CONRASENA_INCORRECTA = 1;
	public static final int USUARIO_BLOQUEADO = 2;
	public static final int ACCESO_CORRECTO = 3;

	//METODO PARA HACER CREATE EN LA TABLA USUARIOS
	public static final boolean crear(Connection connection, Usuario usuario){
		String query=" INSERT INTO usuarios (Usuario, Contrasena, CorreoElectronico, FechaRegistro, FechaBloqueo, Status, GrupoUsuarioFK, EmpleadoFK) VALUES ( ?, aes_encrypt(?, 'ShiftF6'), ?, CURDATE(), ?, ?, ?, ?)";
		try {
			PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(query);
			preparedStatement.setString(1, usuario.getUsuario());
			preparedStatement.setString(2, usuario.getContrasena());
			preparedStatement.setString(3, usuario.getCorreoElectronico());
			if (usuario.getStatus() != Usuario.ACTIVO)
				preparedStatement.setDate(4, usuario.getFechaBloqueo());
			else
				preparedStatement.setNull(4, Types.DATE);
			preparedStatement.setInt(5, usuario.getStatus());
			preparedStatement.setInt(6, usuario.getGrupoUsuarioFk());
			preparedStatement.setInt(7, usuario.getEmpleadoFK());
			preparedStatement.execute();
			return true;
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
			return false;
		}//FIN TRY/CATCH
	}//FIN METODO

	//METODO PARA LEER REGISTROS EN LA TABLA USUARIOS. EMMANUEL OSTRIA
	public static final ArrayList<Usuario> readTodos(Connection connection){
		ArrayList<Usuario> arrayListUsuario = new ArrayList<Usuario>();
		String query = "SELECT usuarios.Sys_PK, Usuario, aes_decrypt(Contrasena, 'ShiftF6'), CorreoElectronico, FechaRegistro, FechaBloqueo, Status, GrupoUsuarioFK, gruposusuario.Nombre, empleados.Nombre FROM usuarios INNER JOIN gruposusuario ON usuarios.GrupoUsuarioFK = gruposusuario.Sys_PK INNER JOIN empleados ON usuarios.EmpleadoFK = empleados.Sys_PK ORDER BY usuarios.Sys_PK";
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			while (resultSet.next()) {
				Usuario usuario = new Usuario();
				usuario.setSysPk(resultSet.getInt(1));
				usuario.setUsuario(resultSet.getString(2));
				usuario.setContrasena(resultSet.getString(3));
				usuario.setCorreoElectronico(resultSet.getString(4));
				usuario.setFechaRegistro(resultSet.getDate(5));
				usuario.setFechaBloqueo(resultSet.getDate(6));
				usuario.setStatus(resultSet.getInt(7));
		    	usuario.setGrupoUsuarioFk(resultSet.getInt(8));
		    	usuario.setNombreGrupoUsuario(resultSet.getString(9));
		    	usuario.setNombreEmpleado(resultSet.getString(10));
				arrayListUsuario.add(usuario);
			}//FIN WHILE
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY/CATCH
		return arrayListUsuario;
	}//FIN METODO

	//METODO PARA OBTENER REGISTROS SEGUN SU USUARIO O CORREO
	public static final ArrayList<Usuario> readPorUsuarioCorreoLike (Connection connection, String like){
		ArrayList<Usuario> arrayListUsuario = new ArrayList<Usuario>();
		String query= "SELECT usuarios.Sys_PK, Usuario, aes_decrypt(Contrasena, 'ShiftF6'), CorreoElectronico, FechaRegistro, FechaBloqueo, Status, GrupoUsuarioFK, gruposusuario.Nombre, empleados.Nombre FROM usuarios INNER JOIN gruposusuario ON usuarios.GrupoUsuarioFK = gruposusuario.Sys_PK INNER JOIN empleados ON empleados.Sys_PK = usuarios.EmpleadoFK WHERE Usuario LIKE '%" + like + "%' OR CorreoElectronico LIKE '%" + like + "%'";
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			Usuario usuario = null;
			while (resultSet.next()) {
				usuario = new Usuario();
				usuario.setSysPk(Integer.parseInt(resultSet.getString(1)));
				usuario.setUsuario(resultSet.getString(2));;
				usuario.setContrasena(resultSet.getString(3));
				usuario.setCorreoElectronico(resultSet.getString(4));
				usuario.setFechaRegistro(resultSet.getDate(5));
				usuario.setFechaBloqueo(resultSet.getDate(6));
				usuario.setStatus(resultSet.getInt(7));
		    	usuario.setGrupoUsuarioFk(resultSet.getInt(8));
		    	usuario.setNombreGrupoUsuario(resultSet.getString(9));
		    	usuario.setNombreEmpleado(resultSet.getString(10));
				arrayListUsuario.add(usuario);
			}//END WHILE
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY/CATCH
		return arrayListUsuario;
	}//FIN METODO

	//METODO PARA HACER SELECT EN LA TABLA USUARIOS
	public static final ArrayList<Usuario> readPorCampo(Connection connection, String campoBusqueda, String valorBusqueda) {
		ArrayList<Usuario> arrayListUsuario = new ArrayList<Usuario>();
		String query = "SELECT Sys_PK, Usuario, aes_decrypt(Contrasena, 'ShiftF6'), CorreoElectronico, FechaRegistro, FechaBloqueo, Status, GrupoUsuarioFK, EmpleadoFK FROM usuarios WHERE "+campoBusqueda+" = ? ORDER BY Sys_PK";
			try {
				PreparedStatement preparedStatement = connection.prepareStatement(query);
				preparedStatement.setString(1, valorBusqueda);
				ResultSet resultSet=preparedStatement.executeQuery();
				Usuario usuario = null;
				while (resultSet.next()) {
					usuario = new Usuario();
					usuario.setSysPk(Integer.parseInt(resultSet.getString(1)));
					usuario.setUsuario(resultSet.getString(2));;
					usuario.setContrasena(resultSet.getString(3));
					usuario.setCorreoElectronico(resultSet.getString(4));
					usuario.setFechaRegistro(resultSet.getDate(5));
					usuario.setFechaBloqueo(resultSet.getDate(6));
					usuario.setStatus(resultSet.getInt(7));
			    	usuario.setGrupoUsuarioFk(resultSet.getInt(8));
			    	usuario.setEmpleadoFK(resultSet.getInt(9));
					arrayListUsuario.add(usuario);
				}//END WHILE
			} catch (SQLException ex) {
				Notificacion.dialogoException(ex);
			}//FIN TRy/CATCH
		return arrayListUsuario;
	}//FIN METODO


	//METODO PARA ACTUALIZAR UN REGITRO
	public static final boolean update(Connection connection, Usuario usuario){
		String query = "UPDATE usuarios SET Usuario = ?, Contrasena = aes_encrypt(?, 'ShiftF6'), CorreoElectronico = ?, FechaBloqueo = ?, Status = ?, GrupoUsuarioFK = ? WHERE Sys_PK = ?";
		try {
			PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(query);
			preparedStatement.setString(1, usuario.getUsuario());
			preparedStatement.setString(2, usuario.getContrasena());
			preparedStatement.setString(3, usuario.getCorreoElectronico());
			if (usuario.getStatus() != Usuario.ACTIVO)
				preparedStatement.setDate(4, usuario.getFechaBloqueo());
			else
				preparedStatement.setNull(4, Types.DATE);
			preparedStatement.setInt(5, usuario.getStatus());
			preparedStatement.setInt(6, usuario.getGrupoUsuarioFk());
			preparedStatement.setInt(7, usuario.getSysPk());
			preparedStatement.execute();
			return true;
		} catch (SQLException ex) {
			System.out.println("Error: En método modificar");
			Notificacion.dialogoException(ex);
			return false;
		}//FIN TRY/CATCH
	}//FIN METODO

	//METODO PARA HACER DELETE EN LA TABLA USUARIOS
	public static final boolean eliminar(Connection connection, Usuario usuario) {
		String query = "DELETE FROM usuarios WHERE Sys_PK= ?";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, usuario.getSysPk());
			preparedStatement.execute();
			return true;
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
			return false;
		}//FIN TRY/CATCH
	}//FIN METODO


	/*//METODO PARA CONVERTIR UNA ARRAYLIST A OBSERVABLELIST
	public ObservableList<Usuario> toObservableList(ArrayList<Object> arrayList) {
		ObservableList<Usuario> usuarioData = FXCollections.observableArrayList();
		for(Object usuario : arrayList) {
			usuarioData.add((Usuario) usuario);
		}//FIN FOR
		return usuarioData;
	}//FIN METODO*/


	//METODO PARA VALIDAR SI UN USUARIO ESTA REGISTRADO Y/O BLOQUEDO Y SI HA ESCRITO CORRECTAMENTE SU CONTRASEÑA
	public static final int validarUsuario(Connection connection, String nombreUsuario, String contrasena) {
		Usuario usuario = new Usuario();
		ArrayList<Usuario> resultadoUsuario = readPorCampo(connection, "usuario", nombreUsuario);
		if (resultadoUsuario.size() != 0) {
			usuario = (Usuario) resultadoUsuario.get(0);
			if(usuario.getUsuario().equals(nombreUsuario)) {
				if(usuario.getContrasena().equals(contrasena)){
					if(usuario.getStatus().equals(0)) {
						return USUARIO_BLOQUEADO;//USUARIO BLOQUEADO
					}else {
						return ACCESO_CORRECTO;//ACCESO CORRECTO
					}//FIN IF-ELSE
				}else {
					return CONRASENA_INCORRECTA;//CONTRASENA INCORRECTA
				}//FIN IF-ELSE
			}//FIN IF
		}else {
			return NO_REGISTRADO;//USUARIO NO REGISTRADO
		}//FIN IF-ELSE
		return 0;
	}//FIN METODO

	//METDODO PARA OBTENER EL ULTIMO SYSPK REGISTRADO EN LA TABAL USUARIOS
	public static final int ultimoSysPk(Connection connection) {
		String query="SELECT Sys_PK FROM usuarios order by Sys_PK asc";
		int ultimoSysPk=0;
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			while (resultSet.next()) {
				ultimoSysPk=resultSet.getInt(1);
			}//FIN WHILE
			return ultimoSysPk;
		}catch (SQLException e) {
			System.out.println("Error: En método leer");
			e.printStackTrace();
		}//FIN TRY-CATCH
		return ultimoSysPk;
	}//FIN METODO

	public static final String aes_encrypt(Connection connection, String palabra) {
    	String query = "SELECT AES_ENCRYPT('" + palabra + "','ShiftF6')";
    	String desencriptacion = "";
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			while (resultSet.next()) {
				desencriptacion = resultSet.getString(1);
			}//FIN WHILE
			return desencriptacion;
		}catch (SQLException ex) {
			Notificacion.dialogoException(ex);
			return desencriptacion;
		}//FIN TRY-CATCH
    }//FIN METODO

	public static final String aes_decrypt(Connection connection, String palabra) {
    	String query = "SELECT AES_DECRYPT('" + palabra + "','ShiftF6')";
    	String desencriptacion = "";
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			while (resultSet.next()) {
				desencriptacion = resultSet.getString(1);
			}//FIN WHILE
			return desencriptacion;
		}catch (SQLException ex) {
			Notificacion.dialogoException(ex);
			return desencriptacion;
		}//FIN TRY-CATCH
    }//FIN METODO

}//FIN CLASE