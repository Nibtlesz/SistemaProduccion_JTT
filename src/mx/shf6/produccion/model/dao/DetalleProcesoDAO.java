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
		String consulta = "INSERT INTO ut_detalleprocesos (uf_Operacion, uf_Descripcion, uf_TiempoPreparacion, uf_TiempoOperacion, uf_CentroTrabajoFK, uf_GrupoTrabajoFK, uf_ProcesoFK) VALUES (?,?,?,?,?,?,?)";
		try {
			PreparedStatement sentenciaPreparada = connection.prepareStatement(consulta);
			sentenciaPreparada.setInt(1, detalleProceso.getOperacion());
			sentenciaPreparada.setString(2, detalleProceso.getDescripcion());
			sentenciaPreparada.setDouble(3, detalleProceso.getTiempoPreparacion());
			sentenciaPreparada.setDouble(4, detalleProceso.getTiempoOperacion());
			sentenciaPreparada.setInt(5, detalleProceso.getCentroTrabajoFK());
			sentenciaPreparada.setInt(6, detalleProceso.getGrupoTrabajoFK());
			sentenciaPreparada.setInt(7, detalleProceso.getProcesoFK());
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
		String consulta = "SELECT ut_detalleprocesos.Sys_PK, ut_detalleprocesos.uf_Operacion, ut_detalleprocesos.uf_Descripcion, ut_detalleprocesos.uf_TiempoPreparacion,\r\n" + 
				" ut_detalleprocesos.uf_TiempoOperacion, ut_detalleprocesos.uf_CentroTrabajoFK, ut_centrotrabajo.uf_Descripcion, ut_detalleprocesos.uf_GrupoTrabajoFK, \r\n" + 
					" ut_grupotrabajo.uf_Codigo, ut_detalleprocesos.uf_ProcesoFK\r\n" + 
				" FROM ut_detalleprocesos \r\n" + 
				" INNER JOIN ut_centrotrabajo ON ut_detalleprocesos.uf_CentroTrabajoFK = ut_centrostrabajo.Sys_PK \r\n" + 
				" INNER JOIN ut_grupotrabajo ON ut_detalleprocesos.uf_GrupoTrabajoFK = ut_grupostrabajo.Sys_PK \r\n" + 
				" INNER JOIN ut_procesos ON ut_detalleprocesos.uf_ProcesoFK = ut_procesos.Sys_PK  ORDER BY uf_Operacion";
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
		String consulta = "SELECT Sys_PK, uf_Operacion, uf_Descripcion, uf_TiempoPreparacion, uf_TiempoOperacion, uf_CentroTrabajoFK, uf_GrupoTrabajoFK, uf_ProcesoFK FROM ut_detalleprocesos WHERE Sys_PK =" + sysPK;
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
			}//FIN WHILE
		} catch(SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY/CATCH
		return detalleProceso;
	}//FIN METODO
		
		//METODO PARA OBTENER UN PROCESO
		public static ArrayList<DetalleProceso> readDetalleProcesoFK(Connection connection, int procesoFK) {
			ArrayList<DetalleProceso> arrayListDetalleProceso = new ArrayList<DetalleProceso>();
			String consulta = "SELECT ut_detalleprocesos.Sys_PK, ut_detalleprocesos.uf_Operacion, ut_detalleprocesos.uf_Descripcion, ut_detalleprocesos.uf_TiempoPreparacion,\r\n" + 
					" ut_detalleprocesos.uf_TiempoOperacion, ut_detalleprocesos.uf_CentroTrabajoFK, ut_centrotrabajo.uf_Descripcion, ut_detalleprocesos.uf_GrupoTrabajoFK, \r\n" + 
					" ut_grupotrabajo.uf_Codigo, ut_detalleprocesos.uf_ProcesoFK\r\n " + 
					" FROM ut_detalleprocesos \r\n" + 
					" INNER JOIN ut_centrotrabajo ON ut_detalleprocesos.uf_CentroTrabajoFK = ut_centrotrabajo.Sys_PK \r\n" + 
					" INNER JOIN ut_grupotrabajo ON ut_detalleprocesos.uf_GrupoTrabajoFK = ut_grupotrabajo.Sys_PK \r\n" + 
					" INNER JOIN ut_procesos ON ut_detalleprocesos.uf_ProcesoFK = ut_procesos.Sys_PK WHERE uf_ProcesoFK = " + procesoFK + " ORDER BY uf_Operacion";
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
					arrayListDetalleProceso.add(detalleProceso);
				}//FIN-WHILE
			} catch (SQLException ex) {
				Notificacion.dialogoException(ex);
			}//FIN TRY-CATCH
			return arrayListDetalleProceso;
		}//FIN METODO

		//METODO PARA ACTUALIZAR UN REGISTRO
		public static boolean updateDetalleProceso(Connection connection, DetalleProceso detalleProceso) {
			String consulta = "UPDATE ut_detalleprocesos SET uf_Operacion = ?, uf_Descripcion = ?, uf_TiempoPreparacion = ?, uf_TiempoOperacion = ?, uf_CentroTrabajoFK = ?, uf_GrupoTrabajoFK = ?, uf_ProcesoFK = ? WHERE Sys_PK = ?";
			try {
				PreparedStatement sentenciaPreparada = connection.prepareStatement(consulta);
				sentenciaPreparada.setInt(1, detalleProceso.getOperacion());
				sentenciaPreparada.setString(2, detalleProceso.getDescripcion());
				sentenciaPreparada.setDouble(3, detalleProceso.getTiempoPreparacion());
				sentenciaPreparada.setDouble(4, detalleProceso.getTiempoOperacion());
				sentenciaPreparada.setInt(5, detalleProceso.getCentroTrabajoFK());
				sentenciaPreparada.setInt(6, detalleProceso.getGrupoTrabajoFK());
				sentenciaPreparada.setInt(7, detalleProceso.getProcesoFK());
				sentenciaPreparada.setInt(8, detalleProceso.getSysPK());
				sentenciaPreparada.execute();
				return true;
			} catch (SQLException ex) {
				Notificacion.dialogoException(ex);
				return false;
			}//FIN TRY/CATCH
		}//FIN METODO

		//METODO PARA ELIMINAR UN REGISTRO
		public static boolean deleteDetalleProceso(Connection connection, DetalleProceso detalleProceso) {
			String consulta = "DELETE FROM ut_detalleprocesos WHERE Sys_PK = ?";
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
		String consulta = "SELECT MAX(uf_Operacion) FROM ut_detalleprocesos WHERE uf_ProcesoFK = " + proceso + "";
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
		String consulta = "SELECT SUM(uf_TiempoPreparacion), SUM(uf_TiempoOperacion) FROM ut_detalleprocesos WHERE uf_ProcesoFK = " + proceso;
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
		String consulta = "SELECT MIN(Sys_PK), uf_CentroTrabajoFK FROM ut_detalleprocesos WHERE uf_ProcesoFK = " + proceso;
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
