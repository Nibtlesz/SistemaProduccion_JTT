package mx.shf6.produccion.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import mx.shf6.produccion.model.DetalleProceso;
import mx.shf6.produccion.utilities.Notificacion;

public class DetalleProcesoDAO {

	//METODO PARA CREAR UN REGISTRO
	public static boolean createDetalleProceso(Connection connection, DetalleProceso detalleProceso) {
		String consulta = "INSERT INTO detalleprocesos (Operacion, Descripcion, TiempoPreparacion, TiempoOperacion, CentroTrabajoFK, GrupoTrabajoFK, ProcesoFK, Cantidad, Componente, Herramienta) VALUES (?,?,?,?,?,?,?,?,?,?)";
		try {
			PreparedStatement sentenciaPreparada = connection.prepareStatement(consulta);
			sentenciaPreparada.setInt(1, detalleProceso.getOperacion());
			sentenciaPreparada.setString(2, detalleProceso.getDescripcion());
			sentenciaPreparada.setDouble(3, detalleProceso.getTiempoPreparacion());
			sentenciaPreparada.setDouble(4, detalleProceso.getTiempoOperacion());
			sentenciaPreparada.setInt(5, detalleProceso.getCentroTrabajoFK());
			sentenciaPreparada.setInt(6, detalleProceso.getGrupoTrabajoFK());
			sentenciaPreparada.setInt(7, detalleProceso.getProcesoFK());
			sentenciaPreparada.setString(8,detalleProceso.getCantidad());
			sentenciaPreparada.setString(9, detalleProceso.getComponentes());
			sentenciaPreparada.setString(10, detalleProceso.getHerramientas());
			sentenciaPreparada.execute();
		return true;
		} catch(SQLException ex) {
			Notificacion.dialogoException(ex);
			return false;
		}//FIN TRY CATCH
	}//FIN METODO REGISTRO

	//METODO PARA OBTENER UN REGISTRO
	public static ArrayList<DetalleProceso> readDetalleProceso(Connection connection) {
		ArrayList<DetalleProceso> arrayListDetalleProceso = new ArrayList<DetalleProceso>();
		String consulta = "SELECT detalleprocesos.Sys_PK, detalleprocesos.Operacion, detalleprocesos.Descripcion, detalleprocesos.TiempoPreparacion,\r\n" + 
				" detalleprocesos.TiempoOperacion, detalleprocesos.CentroTrabajoFK, centrostrabajo.Descripcion, detalleprocesos.GrupoTrabajoFK, \r\n" + 
					" grupostrabajo.Codigo, detalleprocesos.ProcesoFK, detalleprocesos.Cantidad, detalleprocesos.Componente, detalleprocesos.Herramienta\r\n" + 
				" FROM detalleprocesos \r\n" + 
				" INNER JOIN centrostrabajo ON detalleprocesos.CentroTrabajoFK = centrostrabajo.Sys_PK \r\n" + 
				" INNER JOIN grupostrabajo ON detalleprocesos.GrupoTrabajoFK = grupostrabajo.Sys_PK \r\n" + 
				" INNER JOIN procesos ON detalleprocesos.ProcesoFK = procesos.Sys_PK  ORDER BY Operacion";
		try {
			Statement sentencia = connection.createStatement();
				ResultSet resultados = sentencia.executeQuery(consulta);
			while(resultados.next()) {
				DetalleProceso detalleProceso = new DetalleProceso();
				detalleProceso.setSysPK(resultados.getInt(1));
				detalleProceso.setOperacion(resultados.getInt(2));
				detalleProceso.setDescripcion(resultados.getString(3));
				detalleProceso.setTiempoPreparacion(resultados.getDouble(4));
				detalleProceso.setTiempoOperacion(resultados.getDouble(5));
				detalleProceso.setCentroTrabajoFK(resultados.getInt(6));
				detalleProceso.setNombreCentroTrabajo(resultados.getString(7));
				detalleProceso.setGrupoTrabajoFK(resultados.getInt(8));
				detalleProceso.setNombreGrupoTrabajo(resultados.getString(9));
				detalleProceso.setProcesoFK(resultados.getInt(10));
				detalleProceso.setCantidad(resultados.getString(11));
				detalleProceso.setComponentes(resultados.getString(12));
				detalleProceso.setHerramienta(resultados.getString(13));
				arrayListDetalleProceso.add(detalleProceso);
			}//FIN WHILE
		} catch(SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY/CATCH
		return arrayListDetalleProceso;
	}//FIN METODO

	//METODO PARA OBTENER UN REGISTRO POR SYSPK
	public static DetalleProceso readDetalleProceso(Connection connection, int sysPK) {
		DetalleProceso detalleProceso = new DetalleProceso();
		String consulta = "SELECT Sys_PK, Operacion, Descripcion, TiempoPreparacion, TiempoOperacion, CentroTrabajoFK, GrupoTrabajoFK, ProcesoFK, Componente, Cantidad, Herramienta FROM detalleprocesos WHERE Sys_PK =" + sysPK;
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(consulta);
			while(resultados.next()) {
				detalleProceso.setSysPK(resultados.getInt(1));
				detalleProceso.setOperacion(resultados.getInt(2));
				detalleProceso.setDescripcion(resultados.getString(3));
				detalleProceso.setTiempoPreparacion(resultados.getDouble(4));
				detalleProceso.setTiempoOperacion(resultados.getDouble(5));
				detalleProceso.setCentroTrabajoFK(resultados.getInt(6));
				detalleProceso.setGrupoTrabajoFK(resultados.getInt(7));
				detalleProceso.setProcesoFK(resultados.getInt(8));
				detalleProceso.setComponentes(resultados.getString(9));
				detalleProceso.setCantidad(resultados.getString(10));
				detalleProceso.setHerramienta(resultados.getString(11));
			}//FIN WHILE
		} catch(SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY/CATCH
		return detalleProceso;
	}//FIN METODO
		
		//METODO PARA OBTENER UN PROCESO
		public static ArrayList<DetalleProceso> readDetalleProcesoFK(Connection connection, int procesoFK) {
			ArrayList<DetalleProceso> arrayListDetalleProceso = new ArrayList<DetalleProceso>();
			String consulta = "SELECT detalleprocesos.Sys_PK, detalleprocesos.Operacion, detalleprocesos.Descripcion, detalleprocesos.TiempoPreparacion,\r\n" + 
					" detalleprocesos.TiempoOperacion, detalleprocesos.CentroTrabajoFK, centrostrabajo.Descripcion, detalleprocesos.GrupoTrabajoFK, \r\n" + 
					" grupostrabajo.Codigo, detalleprocesos.ProcesoFK, detalleprocesos.Cantidad, detalleprocesos.Componente, detalleprocesos.Herramienta\r\n " + 
					" FROM detalleprocesos \r\n" + 
					" INNER JOIN centrostrabajo ON detalleprocesos.CentroTrabajoFK = centrostrabajo.Sys_PK \r\n" + 
					" INNER JOIN grupostrabajo ON detalleprocesos.GrupoTrabajoFK = grupostrabajo.Sys_PK \r\n" + 
					" INNER JOIN procesos ON detalleprocesos.ProcesoFK = procesos.Sys_PK WHERE ProcesoFK = " + procesoFK + " ORDER BY Operacion";
			try {
				Statement sentencia = connection.createStatement();
				ResultSet resultados = sentencia.executeQuery(consulta);
				while (resultados.next()) {
					DetalleProceso detalleProceso = new DetalleProceso();
					detalleProceso.setSysPK(resultados.getInt(1));
					detalleProceso.setOperacion(resultados.getInt(2));
					detalleProceso.setDescripcion(resultados.getString(3));
					detalleProceso.setTiempoPreparacion(resultados.getDouble(4));
					detalleProceso.setTiempoOperacion(resultados.getDouble(5));
					detalleProceso.setCentroTrabajoFK(resultados.getInt(6));
					detalleProceso.setNombreCentroTrabajo(resultados.getString(7));
					detalleProceso.setGrupoTrabajoFK(resultados.getInt(8));
					detalleProceso.setNombreGrupoTrabajo(resultados.getString(9));
					detalleProceso.setProcesoFK(resultados.getInt(10));
					detalleProceso.setCantidad(resultados.getString(11));
					detalleProceso.setComponentes(resultados.getString(12));
					detalleProceso.setHerramienta(resultados.getString(13));
					arrayListDetalleProceso.add(detalleProceso);
				}//FIN-WHILE
			} catch (SQLException ex) {
				Notificacion.dialogoException(ex);
			}//FIN TRY-CATCH
			return arrayListDetalleProceso;
		}//FIN METODO

		//METODO PARA ACTUALIZAR UN REGISTRO
		public static boolean updateDetalleProceso(Connection connection, DetalleProceso detalleProceso) {
			String consulta = "UPDATE detalleprocesos SET Operacion = ?, Descripcion = ?, TiempoPreparacion = ?, TiempoOperacion = ?, CentroTrabajoFK = ?, GrupoTrabajoFK = ?, ProcesoFK = ?, Componente = ?, Cantidad = ?, Herramienta = ? WHERE Sys_PK = ?";
			try {
				PreparedStatement sentenciaPreparada = connection.prepareStatement(consulta);
				sentenciaPreparada.setInt(1, detalleProceso.getOperacion());
				sentenciaPreparada.setString(2, detalleProceso.getDescripcion());
				sentenciaPreparada.setDouble(3, detalleProceso.getTiempoPreparacion());
				sentenciaPreparada.setDouble(4, detalleProceso.getTiempoOperacion());
				sentenciaPreparada.setInt(5, detalleProceso.getCentroTrabajoFK());
				sentenciaPreparada.setInt(6, detalleProceso.getGrupoTrabajoFK());
				sentenciaPreparada.setInt(7, detalleProceso.getProcesoFK());
				sentenciaPreparada.setString(8, detalleProceso.getComponentes());
				sentenciaPreparada.setString(9, detalleProceso.getCantidad());	
				sentenciaPreparada.setString(10,  detalleProceso.getHerramientas());
				sentenciaPreparada.setInt(11, detalleProceso.getSysPK());
				sentenciaPreparada.execute();
				return true;
			} catch (SQLException ex) {
				Notificacion.dialogoException(ex);
				return false;
			}//FIN TRY/CATCH
		}//FIN METODO

		//METODO PARA ELIMINAR UN REGISTRO
		public static boolean deleteDetalleProceso(Connection connection, DetalleProceso detalleProceso) {
			String consulta = "DELETE FROM detalleprocesos WHERE Sys_PK = ?";
			try {
				PreparedStatement sentenciaPreparada = connection.prepareStatement(consulta);
				sentenciaPreparada.setInt(1, detalleProceso.getSysPK());
				sentenciaPreparada.execute();
				return true;
			} catch(SQLException ex) {
				Notificacion.dialogoException(ex);
			return false;
			}//FIN TRY/CATCH
		}//FIN METODO
		
	public static int ultimaOperacion(Connection connection, int proceso) {
		int ultimaOperacion = 0;
		String consulta = "SELECT MAX(Operacion) FROM detalleprocesos WHERE ProcesoFK = " + proceso + "";
		try {
			Statement statement = connection.createStatement();
			ResultSet resulset = statement.executeQuery(consulta);
			while (resulset.next()) {
				ultimaOperacion = resulset.getInt(1);
			}//FIN WHILE
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY CATCH
		return ultimaOperacion;
	}//FIN METODO
	
	public static DetalleProceso tiemposProceso(Connection connection,int proceso) {
		DetalleProceso detalleProceso = new DetalleProceso();
		String consulta = "SELECT SUM(TiempoPreparacion), SUM(TiempoOperacion) FROM detalleprocesos WHERE ProcesoFK = " + proceso;
		try {
			Statement statement = connection.createStatement();
			ResultSet resulset = statement.executeQuery(consulta);
			while (resulset.next()) {
				detalleProceso.setTiempoPreparacion(resulset.getDouble(1));
				detalleProceso.setTiempoOperacion(resulset.getDouble(2));
			}//FIN WHILE
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY CATCH
		return detalleProceso;
	}//FIN METODO
		
	public static DetalleProceso primeraOperacion(Connection connection, int proceso) {
		DetalleProceso detalleProceso = new DetalleProceso();
		String consulta = "SELECT MIN(Sys_PK), CentroTrabajoFK FROM detalleprocesos WHERE ProcesoFK = " + proceso;
		try {
			Statement statement = connection.createStatement();
			ResultSet resulset = statement.executeQuery(consulta);
			while (resulset.next()) {
				detalleProceso.setSysPK(resulset.getInt(1));
				detalleProceso.setCentroTrabajoFK(resulset.getInt(2));
			}//FIN WHILE
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY CATCH
		return detalleProceso;
	}//FIN METODO
		
	public static ObservableList<DetalleProceso> toObservableList(ArrayList<DetalleProceso> arrayList) {
		ObservableList<DetalleProceso> listaObservableDetalleProceso = FXCollections.observableArrayList();
		for (DetalleProceso detalleProceso : arrayList) {
			listaObservableDetalleProceso.add(detalleProceso);
		}
		return listaObservableDetalleProceso;
	}//FIN METODO
		
	public static List<DetalleProceso> toList(ArrayList<DetalleProceso> arrayList) {
		List<DetalleProceso> listaDetalleProceso = new ArrayList<DetalleProceso>();
		for (DetalleProceso detalleProceso : arrayList) {
			listaDetalleProceso.add(detalleProceso);
		}
		return listaDetalleProceso;
	}//FIN METODO
}//FIN CLASE
