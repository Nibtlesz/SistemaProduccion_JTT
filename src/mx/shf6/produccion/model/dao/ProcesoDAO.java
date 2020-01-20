package mx.shf6.produccion.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import mx.shf6.produccion.model.Proceso;
import mx.shf6.produccion.utilities.Notificacion;

public class ProcesoDAO {

	//METODO PARA CREAR UN REGISTRO
	public static boolean createProceso(Connection connection, Proceso proceso) {
		String consulta = "INSERT INTO ut_procesos (uf_Fecha, uf_ProductoFK, uf_CentroTrabajoFK, uf_EmpleadoFK) VALUES (?,?,?,?)";
		try {
			PreparedStatement sentenciaPreparada = connection.prepareStatement(consulta);
			sentenciaPreparada.setDate(1, proceso.getFecha());
			sentenciaPreparada.setInt(2, proceso.getComponenteFK());
			sentenciaPreparada.setInt(3, proceso.getCentroTrabajoFK());
			sentenciaPreparada.setInt(4, proceso.getEmpleadoFK());
			sentenciaPreparada.execute();
			return true;
		} catch(SQLException ex) {
			Notificacion.dialogoException(ex);
			return false;
		}//FIN TRY CATCH
	}//FIN METODO REGISTRO

	//METODO PARA OBTENER UN REGISTRO
	public static ArrayList<Proceso> readProceso(Connection connection) {
		ArrayList<Proceso> arrayListProceso = new ArrayList<Proceso>();
		String consulta = "SELECT ut_procesos.Sys_PK, ut_procesos.uf_Fecha, ut_procesos.uf_CentroTrabajoFK, ut_centrotrabajo.uf_Descripcion, ut_procesos.uf_ProductoFK, producto.Codigo, ut_procesos.uf_EmpleadoFK, ut_empleados.uf_Nombre FROM ut_procesos INNER JOIN producto ON ut_procesos.uf_ProductoFK = producto.Sys_PK INNER JOIN ut_empleados ON ut_procesos.uf_EmpleadoFK = ut_empleados.Sys_PK INNER JOIN ut_centrotrabajo ON ut_procesos.uf_CentroTrabajoFK = ut_centrotrabajo.Sys_PK ORDER BY ut_procesos.uf_Fecha";
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(consulta);
			while (resultados.next()) {
				Proceso proceso = new Proceso();
				proceso.setSysPK(resultados.getInt(1));
				proceso.setFecha(resultados.getDate(2));
				proceso.setCentroTrabajoFK(resultados.getInt(3));
				proceso.setNombreCentroTrabajo(resultados.getString(4));
				proceso.setComponenteFK(resultados.getInt(5));
				proceso.setNombreComponente(resultados.getString(6));
				proceso.setEmpleadoFK(resultados.getInt(7));
				proceso.setNombreEmpleado(resultados.getString(8));
				arrayListProceso.add(proceso);
			}//FIN WHILE
		} catch(SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY/CATCH
		return arrayListProceso;
	}//FIN METODO

	public static Proceso readProcesoHoja(Connection connection, int procesoFK) {
		Proceso proceso = new Proceso();
		String consulta = "SELECT ut_procesos.Sys_PK, ut_procesos.uf_Fecha, ut_centrotrabajo.uf_Descripcion, producto.Codigo, producto.Descripcion, ut_empleados.uf_Nombre,"
				+ " ut_procesos.uf_ProductoFK FROM ut_procesos INNER JOIN ut_empleados ON ut_procesos.uf_EmpleadoFK = ut_empleados.Sys_PK " + 
				" INNER JOIN producto ON ut_procesos.uf_ProductoFK = producto.Sys_PK INNER JOIN ut_centrotrabajo ON ut_procesos.uf_CentroTrabajoFK = ut_centrotrabajo.Sys_PK"
				+ " WHERE ut_procesos.Sys_PK = " + procesoFK;
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(consulta);
			while (resultados.next()) {
				proceso.setSysPK(resultados.getInt(1));
				proceso.setFecha(resultados.getDate(2));
				proceso.setNombreCentroTrabajo(resultados.getString(3));
				proceso.setDescripcionComponente(resultados.getString(4));
				proceso.setNombreComponente(resultados.getString(5));
				proceso.setNombreEmpleado(resultados.getString(6));
				proceso.setComponenteFK(resultados.getInt(7));
			}//FIN WHILE
		} catch(SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY/CATCH
		return proceso;
	}//FIN METODO
	
	//METODO PARA OBTENER UN REGISTRO POR SYSPK
	public static Proceso readProceso(Connection connection, int sysPK) {
		Proceso proceso = new Proceso();
		String consulta = "SELECT Sys_PK, uf_Fecha, uf_CentroTrabajoFK, uf_ComponenteFK, uf_EmpleadoFK FROM ut_procesos WHERE Sys_PK = " + sysPK;
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(consulta);
			while (resultados.next()) {
				proceso.setSysPK(resultados.getInt(1));
				proceso.setFecha(resultados.getDate(2));
				proceso.setCentroTrabajoFK(resultados.getInt(3));
				proceso.setComponenteFK(resultados.getInt(4));
				proceso.setEmpleadoFK(resultados.getInt(5));
			}//FIN WHILE
		} catch(SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY/CATCH
		return proceso;
	}//FIN METODO

	//METODO PARA OBTENER UN REGISTRO POR SYSPK
	public static int readProcesoComponenteFK(Connection connection, int componenteFK) {
		int procesoSysPK = 0;
		String consulta = "SELECT Sys_PK FROM ut_procesos WHERE uf_ProductoFK = " + componenteFK;
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(consulta);
			while (resultados.next()) {
				procesoSysPK = resultados.getInt(1);
			}//FIN WHILE
		} catch(SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY/CATCH
		return procesoSysPK;
	}//FIN METODO
	
	//METODO PARA OBTENER UN REGISTRO POR LIKE
	public static ArrayList<Proceso> readProceso(Connection connection, String like) {
		ArrayList<Proceso> arrayListProceso = new ArrayList<Proceso>();
		String consulta = "SELECT ut_procesos.Sys_PK, ut_procesos.uf_Fecha, ut_procesos.uf_CentroTrabajoFK, ut_centrotrabajo.uf_Descripcion, ut_procesos.uf_ProductoFK, producto.Codigo, ut_procesos.uf_EmpleadoFK, ut_empleados.uf_Nombre FROM ut_procesos INNER JOIN producto ON ut_procesos.uf_ProductoFK = producto.Sys_PK INNER JOIN ut_empleados ON ut_procesos.uf_EmpleadoFK = ut_empleados.Sys_PK INNER JOIN ut_centrotrabajo ON ut_procesos.uf_CentroTrabajoFK = ut_centrotrabajo.Sys_PK WHERE producto.Codigo LIKE '%" + like + "%' OR ut_empleados.uf_Nombre LIKE '%" + like + "%' OR ut_centrotrabajo.uf_Descripcion LIKE '%" + like + "%'";
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(consulta);
			while (resultados.next()) {
				Proceso proceso = new Proceso();
				proceso.setSysPK(resultados.getInt(1));
				proceso.setFecha(resultados.getDate(2));
				proceso.setCentroTrabajoFK(resultados.getInt(3));
				proceso.setNombreCentroTrabajo(resultados.getString(4));
				proceso.setComponenteFK(resultados.getInt(5));
				proceso.setNombreComponente(resultados.getString(6));
				proceso.setEmpleadoFK(resultados.getInt(7));
				proceso.setNombreEmpleado(resultados.getString(8));
				arrayListProceso.add(proceso);
			}//FIN WHILE
		} catch(SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY/CATCH
		return arrayListProceso;
	}//FIN METODO

		//METODO PARA ACTUALIZAR UN REGISTRO
		public static boolean updateProceso(Connection connection, Proceso proceso) {
			String consulta = "UPDATE ut_procesos SET uf_Fecha = ?, uf_ProductoFK = ?, uf_CentroTrabajoFK = ?, uf_EmpleadoFK = ? WHERE Sys_PK = ?";
			try {
				PreparedStatement sentenciaPreparada = connection.prepareStatement(consulta);
				sentenciaPreparada.setDate(1, proceso.getFecha());
				sentenciaPreparada.setInt(2, proceso.getComponenteFK());
				sentenciaPreparada.setInt(3, proceso.getCentroTrabajoFK());
				sentenciaPreparada.setInt(4, proceso.getEmpleadoFK());	
				sentenciaPreparada.setInt(5, proceso.getSysPK());
				sentenciaPreparada.execute();
				return true;
			} catch (SQLException ex) {
				Notificacion.dialogoException(ex);
				return false;
			}//FIN TRY/CATCH
		}//FIN METODO

		//METODO PARA ELIMINAR UN REGISTRO
		public static boolean deleteProceso(Connection connection, Proceso proceso) {
			String consulta = "DELETE FROM ut_procesos WHERE Sys_PK = ?";
			try {
				PreparedStatement sentenciaPreparada = connection.prepareStatement(consulta);
				sentenciaPreparada.setInt(1, proceso.getSysPK());
				sentenciaPreparada.execute();
				return true;
			} catch(SQLException ex) {
				Notificacion.dialogoException(ex);
			return false;
			}//FIN TRY/CATCH
		}//FIN METODO

		//METODO PARA CONVERTIR ARRAYLIST EN OBSERVABLELIST
		public static ObservableList<Proceso> toObservableList(ArrayList<Proceso> arrayList) {
			ObservableList<Proceso> listaObservableProceso = FXCollections.observableArrayList();
			for (Proceso proceso : arrayList) {
				listaObservableProceso.add(proceso);
			}
		    return listaObservableProceso;
		}//FIN METODO

}//FIN CLASE
