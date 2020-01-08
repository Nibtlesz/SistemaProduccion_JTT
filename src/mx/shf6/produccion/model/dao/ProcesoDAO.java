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
		String consulta = "INSERT INTO procesos (Fecha, Cantidad, Ordenamiento, Nivel, CentroTrabajoFK, ComponenteFK, EmpleadoFK, Debit) VALUES (?,?,?,?,?,?,?,?)";
		try {
			PreparedStatement sentenciaPreparada = connection.prepareStatement(consulta);
			sentenciaPreparada.setDate(1, proceso.getFecha());
			sentenciaPreparada.setInt(2, proceso.getCantidad());
			sentenciaPreparada.setInt(3, proceso.getOrdenamiento());
			sentenciaPreparada.setInt(4, proceso.getNivel());
			sentenciaPreparada.setInt(5, proceso.getCentroTrabajoFK());
			sentenciaPreparada.setInt(6, proceso.getComponenteFK());
			sentenciaPreparada.setInt(7, proceso.getEmpleadoFK());
			sentenciaPreparada.setInt(8, proceso.getDebit());
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
		String consulta = "SELECT procesos.Sys_PK, procesos.Fecha, procesos.Cantidad, procesos.Ordenamiento, procesos.Nivel, procesos.CentroTrabajoFK, centrostrabajo.Descripcion, procesos.ComponenteFK, componentes.NumeroParte, procesos.EmpleadoFK, empleados.Nombre, procesos.Debit FROM procesos INNER JOIN componentes ON procesos.ComponenteFK = componentes.Sys_PK INNER JOIN empleados ON procesos.EmpleadoFK = empleados.Sys_PK INNER JOIN centrostrabajo ON procesos.CentroTrabajoFK = centrostrabajo.Sys_PK ORDER BY procesos.Fecha";
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(consulta);
			while (resultados.next()) {
				Proceso proceso = new Proceso();
				proceso.setSysPK(resultados.getInt(1));
				proceso.setFecha(resultados.getDate(2));
				proceso.setCantidad(resultados.getInt(3));
				proceso.setOrdenamiento(resultados.getInt(4));
				proceso.setNivel(resultados.getInt(5));
				proceso.setCentroTrabajoFK(resultados.getInt(6));
				proceso.setNombreCentroTrabajo(resultados.getString(7));
				proceso.setComponenteFK(resultados.getInt(8));
				proceso.setNombreComponente(resultados.getString(9));
				proceso.setEmpleadoFK(resultados.getInt(10));
				proceso.setNombreEmpleado(resultados.getString(11));
				proceso.setDebit(resultados.getInt(12));
				arrayListProceso.add(proceso);
			}//FIN WHILE
		} catch(SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY/CATCH
		return arrayListProceso;
	}//FIN METODO

	public static Proceso readProcesoHoja(Connection connection, int procesoFK) {
		Proceso proceso = new Proceso();
		String consulta = "SELECT procesos.Sys_PK, procesos.Fecha, procesos.Cantidad, procesos.Ordenamiento, procesos.Nivel,\r\n" + 
				"				centrostrabajo.Descripcion, componentes.NumeroParte, componentes.Descripcion, clientes.Nombre, empleados.Nombre,"
				+ "			    componentes.TipoComponente, componentes.Revision, procesos.Debit, procesos.ComponenteFK\r\n" + 
				"				FROM procesos \r\n" + 
				"                INNER JOIN empleados ON procesos.EmpleadoFK = empleados.Sys_PK\r\n" + 
				"				INNER JOIN componentes ON procesos.ComponenteFK = componentes.Sys_PK\r\n" + 
				"				INNER JOIN clientes ON componentes.ClienteFK = clientes.Sys_PK\r\n" + 
				"				INNER JOIN centrostrabajo ON procesos.CentroTrabajoFK = centrostrabajo.Sys_PK"
				+ " WHERE procesos.Sys_PK = " + procesoFK;
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(consulta);
			while (resultados.next()) {
				proceso.setSysPK(resultados.getInt(1));
				proceso.setFecha(resultados.getDate(2));	
				proceso.setCantidad(resultados.getInt(3));
				proceso.setOrdenamiento(resultados.getInt(4));
				proceso.setNivel(resultados.getInt(5));
				proceso.setNombreCentroTrabajo(resultados.getString(6));
				proceso.setDescripcionComponente(resultados.getString(7));
				proceso.setNombreComponente(resultados.getString(8));
				proceso.setNombreCliente(resultados.getString(9));
				proceso.setNombreEmpleado(resultados.getString(10));
				proceso.setTipoComponente(resultados.getString(11));
				proceso.setRevisionComponente(resultados.getString(12));
				proceso.setDebit(resultados.getInt(13));
				proceso.setComponenteFK(resultados.getInt(14));
			}//FIN WHILE
		} catch(SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY/CATCH
		return proceso;
	}//FIN METODO
	
	//METODO PARA OBTENER UN REGISTRO POR SYSPK
	public static Proceso readProceso(Connection connection, int sysPK) {
		Proceso proceso = new Proceso();
		String consulta = "SELECT Sys_PK, Fecha, Cantidad, Ordenamiento, Nivel, CentroTrabajoFK, ComponenteFK, EmpleadoFK, Debit FROM procesos WHERE Sys_PK = " + sysPK;
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(consulta);
			while (resultados.next()) {
				proceso.setSysPK(resultados.getInt(1));
				proceso.setFecha(resultados.getDate(2));
				proceso.setCantidad(resultados.getInt(3));
				proceso.setOrdenamiento(resultados.getInt(4));
				proceso.setNivel(resultados.getInt(5));
				proceso.setCentroTrabajoFK(resultados.getInt(6));
				proceso.setComponenteFK(resultados.getInt(7));
				proceso.setEmpleadoFK(resultados.getInt(8));
				proceso.setDebit(resultados.getInt(9));
			}//FIN WHILE
		} catch(SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY/CATCH
		return proceso;
	}//FIN METODO

	//METODO PARA OBTENER UN REGISTRO POR SYSPK
	public static int readProcesoComponenteFK(Connection connection, int componenteFK) {
		int procesoSysPK = 0;
		String consulta = "SELECT Sys_PK FROM procesos WHERE ComponenteFK = " + componenteFK;
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
		String consulta = "SELECT procesos.Sys_PK, procesos.Fecha, procesos.Cantidad, procesos.Ordenamiento, procesos.Nivel, procesos.CentroTrabajoFK, centrostrabajo.Descripcion, procesos.ComponenteFK, componentes.NumeroParte, procesos.EmpleadoFK, empleados.Nombre, procesos.Debit FROM procesos INNER JOIN componentes ON procesos.ComponenteFK = componentes.Sys_PK INNER JOIN empleados ON procesos.EmpleadoFK = empleados.Sys_PK INNER JOIN centrostrabajo ON procesos.CentroTrabajoFK = centrostrabajo.Sys_PK WHERE componentes.NumeroParte LIKE '%" + like + "%' OR empleados.Nombre LIKE '%" + like + "%' OR centrostrabajo.Descripcion LIKE '%" + like + "%'";
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(consulta);
			while (resultados.next()) {
				Proceso proceso = new Proceso();
				proceso.setSysPK(resultados.getInt(1));
				proceso.setFecha(resultados.getDate(2));
				proceso.setCantidad(resultados.getInt(3));
				proceso.setOrdenamiento(resultados.getInt(4));
				proceso.setNivel(resultados.getInt(5));
				proceso.setCentroTrabajoFK(resultados.getInt(6));
				proceso.setNombreCentroTrabajo(resultados.getString(7));
				proceso.setComponenteFK(resultados.getInt(8));
				proceso.setNombreComponente(resultados.getString(9));
				proceso.setEmpleadoFK(resultados.getInt(10));
				proceso.setNombreEmpleado(resultados.getString(11));
				proceso.setDebit(resultados.getInt(12));
				arrayListProceso.add(proceso);
			}//FIN WHILE
		} catch(SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY/CATCH
		return arrayListProceso;
	}//FIN METODO

		//METODO PARA ACTUALIZAR UN REGISTRO
		public static boolean updateProceso(Connection connection, Proceso proceso) {
			String consulta = "UPDATE procesos SET Fecha = ?, Cantidad = ?, Ordenamiento = ?, Nivel = ?, CentroTrabajoFK = ?, ComponenteFK = ?, EmpleadoFK = ?, Debit = ? WHERE Sys_PK = ?";
			try {
				PreparedStatement sentenciaPreparada = connection.prepareStatement(consulta);
				sentenciaPreparada.setDate(1, proceso.getFecha());
				sentenciaPreparada.setInt(2, proceso.getCantidad());
				sentenciaPreparada.setInt(3, proceso.getOrdenamiento());
				sentenciaPreparada.setInt(4, proceso.getNivel());
				sentenciaPreparada.setInt(5, proceso.getCentroTrabajoFK());
				sentenciaPreparada.setInt(6, proceso.getComponenteFK());
				sentenciaPreparada.setInt(7, proceso.getEmpleadoFK());	
				sentenciaPreparada.setInt(8, proceso.getDebit());
				sentenciaPreparada.setInt(9, proceso.getSysPK());
				sentenciaPreparada.execute();
				return true;
			} catch (SQLException ex) {
				Notificacion.dialogoException(ex);
				return false;
			}//FIN TRY/CATCH
		}//FIN METODO

		//METODO PARA ELIMINAR UN REGISTRO
		public static boolean deleteProceso(Connection connection, Proceso proceso) {
			String consulta = "DELETE FROM procesos WHERE Sys_PK = ?";
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
