package mx.shf6.produccion.model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import mx.shf6.produccion.model.OrdenProduccion;
import mx.shf6.produccion.utilities.Notificacion;

public class OrdenProduccionDAO {

	//METODO PARA CREAR UN REGISTRO
	public static boolean createOrdenProduccion(Connection connection, OrdenProduccion ordenProduccion) {
		String consulta = "INSERT INTO ut_ordenesproduccion (uf_Fecha, uf_Lote, uf_Cantidad, uf_Status, uf_DVentaFK) VALUES (CURDATE(),?,?,0,?)";
		try {
			PreparedStatement sentenciaPreparada = connection.prepareStatement(consulta);
			sentenciaPreparada.setString(1, ordenProduccion.getLote());
			sentenciaPreparada.setInt(2, ordenProduccion.getCantidad());
			sentenciaPreparada.setInt(3, ordenProduccion.getDetalleCotizacionFK());
			sentenciaPreparada.execute();
			return true;
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
			return false;
		}//FIN TRY CATCH
	}//FIN METODO
	
	//METODO PARA OBTENER UN REGISTRO PARA GRAFICAS
	public static ArrayList<OrdenProduccion> readLoteProduccion(Connection connection) {
		ArrayList<OrdenProduccion> arrayListaOrdenProduccion = new ArrayList<OrdenProduccion>();
		String consulta = "SELECT Sys_PK, uf_Fecha, uf_Lote, uf_Cantidad, uf_Status, uf_DVentaFK FROM ut_ordenesproduccion WHERE uf_Status != 3 AND uf_Status != 4";
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(consulta);
			while (resultados.next()) {
				OrdenProduccion orden = new OrdenProduccion();
				orden.setSysPK(resultados.getInt(1));
				orden.setFecha(resultados.getDate(2));
				orden.setLote(resultados.getString(3));
				orden.setCantidad(resultados.getInt(4));
				orden.setStatus(resultados.getInt(5));
				orden.setDetalleOrdenCompraFK(resultados.getInt(6));
				arrayListaOrdenProduccion.add(orden);
			}//FIN WHILE
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY CATCH
		return arrayListaOrdenProduccion;
	}//FIN METODO
	
	//METODO PARA OBTENER UN REGISTRO PANTALLAORDENPRODUCCION
	public static ArrayList<OrdenProduccion> readOrdenProduccion(Connection connection) {
		ArrayList<OrdenProduccion> arrayListaOrdenProduccion = new ArrayList<OrdenProduccion>();
		String consulta = "SELECT ut_ordenesproduccion.Sys_PK, ut_ordenesproduccion.uf_Fecha, ut_ordenesproduccion.uf_Status, " + 
				"ut_ordenesproduccion.uf_DVentaFK, cliente.Nombre, venta.Referencia, producto.Sys_PK, producto.Codigo, producto.Descripcion, ut_ordenesproduccion.uf_Cantidad " + 
				"FROM ut_ordenesproduccion INNER JOIN dventa ON ut_ordenesproduccion.uf_DVentaFK = dventa.Sys_PK " + 
				"INNER JOIN producto ON dventa.IProducto = producto.Sys_PK " + 
				"INNER JOIN venta ON dventa.FK_Venta_Detalle = venta.Sys_PK " + 
				"INNER JOIN cliente ON venta.ICliente = cliente.Sys_PK"
				+ " ORDER BY  uf_Fecha ASC";
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(consulta);
			while (resultados.next()) {
				OrdenProduccion orden = new OrdenProduccion();
				orden.setSysPK(resultados.getInt(1));
				orden.setFecha(resultados.getDate(2));
				orden.setStatus(resultados.getInt(3));
				orden.setDetalleOrdenCompraFK(resultados.getInt(4));
				orden.setCliente(resultados.getString(5));
				orden.setLote(resultados.getString(6));
				orden.setOrdenCompra(resultados.getString(6));
				orden.setComponenteFK(resultados.getInt(7));
				orden.setNumeroParte(resultados.getString(8));
				orden.setDescripcion(resultados.getString(9));
				orden.setCantidad(resultados.getInt(10));
				arrayListaOrdenProduccion.add(orden);
			}//FIN WHILE
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY CATCH
		return arrayListaOrdenProduccion;
	}//FIN METODO
	
	//METODO PARA OBTENER UN REGISTRO POR FECHA
	public static ArrayList<OrdenProduccion> dateOrdenProduccion(Connection connection, Date fechaInicio, Date fechaFinal) {
		ArrayList<OrdenProduccion> arrayListaOrdenProduccion = new ArrayList<OrdenProduccion>();
		String consulta = "SELECT ut_ordenesproduccion.Sys_PK, ut_ordenesproduccion.uf_Fecha, ut_ordenesproduccion.uf_Status, " + 
				"ut_ordenesproduccion.uf_DVentaFK, cliente.Nombre, venta.Referencia, producto.Sys_PK, producto.Codigo, producto.Descripcion, ut_ordenesproduccion.uf_Cantidad " + 
				"FROM ut_ordenesproduccion INNER JOIN dventa ON ut_ordenesproduccion.uf_DVentaFK = dventa.Sys_PK " + 
				"INNER JOIN producto ON dventa.IProducto = producto.Sys_PK " + 
				"INNER JOIN venta ON dventa.FK_Venta_Detalle = venta.Sys_PK " + 
				"INNER JOIN cliente ON venta.ICliente = cliente.Sys_PK" +
				" WHERE ut_ordenesproduccion.uf_Fecha BETWEEN '"+ fechaInicio +"' AND '" + fechaFinal +"' ORDER BY uf_Fecha ASC";
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(consulta);
			while (resultados.next()) {
				OrdenProduccion orden = new OrdenProduccion();
				orden.setSysPK(resultados.getInt(1));
				orden.setFecha(resultados.getDate(2));
				orden.setStatus(resultados.getInt(3));
				orden.setDetalleOrdenCompraFK(resultados.getInt(4));
				orden.setCliente(resultados.getString(5));
				orden.setLote(resultados.getString(6));
				orden.setOrdenCompra(resultados.getString(6));
				orden.setComponenteFK(resultados.getInt(7));
				orden.setNumeroParte(resultados.getString(8));
				orden.setDescripcion(resultados.getString(9));
				orden.setCantidad(resultados.getInt(10));
				arrayListaOrdenProduccion.add(orden);
			}//FIN WHILE
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY CATCH
		return arrayListaOrdenProduccion;
	}//FIN METODO
	
	//METODO PARA OBTENER UN REGISTRO POR BUSQUEDA
	public static ArrayList<OrdenProduccion> searchOrdenProduccion(Connection connection, String like) {
		ArrayList<OrdenProduccion> arrayListaOrdenProduccion = new ArrayList<OrdenProduccion>();
		String consulta = "SELECT ut_ordenesproduccion.Sys_PK, ut_ordenesproduccion.uf_Fecha, ut_ordenesproduccion.uf_Status, " + 
				"ut_ordenesproduccion.uf_DVentaFK, cliente.Nombre, venta.Referencia, producto.Sys_PK, producto.Codigo, producto.Descripcion, ut_ordenesproduccion.uf_Cantidad " + 
				"FROM ut_ordenesproduccion INNER JOIN dventa ON ut_ordenesproduccion.uf_DVentaFK = dventa.Sys_PK " + 
				"INNER JOIN producto ON dventa.IProducto = producto.Sys_PK " + 
				"INNER JOIN venta ON dventa.FK_Venta_Detalle = venta.Sys_PK " + 
				"INNER JOIN cliente ON venta.ICliente = cliente.Sys_PK"
				+ " WHERE clientes.Nombre LIKE '%" + like + " %' "
				+ "OR venta.Referencia LIKE '%" + like + "%' OR producto.Codigo LIKE '%" + like + "%'";
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(consulta);
			while (resultados.next()) {
				OrdenProduccion orden = new OrdenProduccion();
				orden.setSysPK(resultados.getInt(1));
				orden.setFecha(resultados.getDate(2));
				orden.setStatus(resultados.getInt(3));
				orden.setDetalleOrdenCompraFK(resultados.getInt(4));
				orden.setCliente(resultados.getString(5));
				orden.setLote(resultados.getString(6));
				orden.setOrdenCompra(resultados.getString(6));
				orden.setComponenteFK(resultados.getInt(7));
				orden.setNumeroParte(resultados.getString(8));
				orden.setDescripcion(resultados.getString(9));
				orden.setCantidad(resultados.getInt(10));
				arrayListaOrdenProduccion.add(orden);
			}//FIN WHILE
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY CATCH
		return arrayListaOrdenProduccion;
	}//FIN METODO
	
	//METODO PARA OBTENER UN REGISTRO POR STATUS
	public static ArrayList<OrdenProduccion> statusOrdenProduccion(Connection connection, int status) {
		ArrayList<OrdenProduccion> arrayListaOrdenProduccion = new ArrayList<OrdenProduccion>();
		String consulta = "SELECT ut_ordenesproduccion.Sys_PK, ut_ordenesproduccion.uf_Fecha, ut_ordenesproduccion.uf_Status, " + 
				"ut_ordenesproduccion.uf_DVentaFK, cliente.Nombre, venta.Referencia, producto.Sys_PK, producto.Codigo, producto.Descripcion, ut_ordenesproduccion.uf_Cantidad " + 
				"FROM ut_ordenesproduccion INNER JOIN dventa ON ut_ordenesproduccion.uf_DVentaFK = dventa.Sys_PK " + 
				"INNER JOIN producto ON dventa.IProducto = producto.Sys_PK " + 
				"INNER JOIN venta ON dventa.FK_Venta_Detalle = venta.Sys_PK " + 
				"INNER JOIN cliente ON venta.ICliente = cliente.Sys_PK"
				+ " WHERE ut_ordenesproduccion.uf_Status = " + status;
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(consulta);
			while (resultados.next()) {
				OrdenProduccion orden = new OrdenProduccion();
				orden.setSysPK(resultados.getInt(1));
				orden.setFecha(resultados.getDate(2));
				orden.setStatus(resultados.getInt(3));
				orden.setDetalleOrdenCompraFK(resultados.getInt(4));
				orden.setCliente(resultados.getString(5));
				orden.setLote(resultados.getString(6));
				orden.setOrdenCompra(resultados.getString(6));
				orden.setComponenteFK(resultados.getInt(7));
				orden.setNumeroParte(resultados.getString(8));
				orden.setDescripcion(resultados.getString(9));
				orden.setCantidad(resultados.getInt(10));
				arrayListaOrdenProduccion.add(orden);
			}//FIN WHILE
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY CATCH
		return arrayListaOrdenProduccion;
	}//FIN METODO
	
	public static OrdenProduccion searchOrdenProduccion(Connection connection, int detalleOrdenCompraFK) {
		OrdenProduccion orden = new OrdenProduccion();
		String consulta = " SELECT Sys_PK, uf_Fecha, uf_Lote, uf_Cantidad, uf_Status, uf_DVentaFK FROM ut_ordenesproduccion WHERE uf_DVentaFK = " + detalleOrdenCompraFK;
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(consulta);
			while (resultados.next()) {
				orden.setSysPK(resultados.getInt(1));
				orden.setFecha(resultados.getDate(2));
				orden.setLote(resultados.getString(3));
				orden.setCantidad(resultados.getInt(4));
				orden.setStatus(resultados.getInt(5));
				orden.setDetalleOrdenCompraFK(resultados.getInt(6));
			}//FIN WHILE
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY CATCH
		return orden;
	}//FIN METODO
	
	public static final int ultimoSysPK(Connection connection) {
		String query = "SELECT Sys_PK FROM ut_ordenesproduccion order by Sys_PK asc";
		int ultimoSyspk = 0;
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			while (resultSet.next()) {
				ultimoSyspk = resultSet.getInt(1);
			}//FIN WHILE
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY CATCH
		return ultimoSyspk;
	}//FIN METODO
	
	public static final int sysPKOrdenProduccion(Connection connection, String lote) {
		String query = "SELECT Sys_PK FROM ut_ordenesproduccion WHERE uf_Lote = '" + lote + "' AND uf_Status != 3 AND uf_Status != 4";
		int sysPK = 0;
		try {
			Statement statement = connection.createStatement();
			ResultSet resulset = statement.executeQuery(query);
			while (resulset.next()) {
				sysPK = resulset.getInt(1);
			}//FIN WHILE
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY CATCH
		return sysPK;
	} //FIN METODO
	
	//METODO PARA OBTENER LA ORDEN PRODUCCION SEGUN EL LOTE
    public static final OrdenProduccion loteOrdenProduccion (Connection connection, String lote) {
        OrdenProduccion orden = new OrdenProduccion();
        String query = "SELECT Sys_PK, uf_Fecha, uf_Lote, uf_Cantidad, uf_Status, uf_DVentaFK FROM ut_ordenesproduccion WHERE uf_Lote = '" + lote + "'";
        try {
            Statement sentencia = connection.createStatement();
            ResultSet resultados = sentencia.executeQuery(query);
            while(resultados.next()) {
            	orden.setSysPK(resultados.getInt(1));
				orden.setFecha(resultados.getDate(2));
				orden.setLote(resultados.getString(3));
				orden.setCantidad(resultados.getInt(4));
				orden.setStatus(resultados.getInt(5));
				orden.setDetalleOrdenCompraFK(resultados.getInt(6));
            }//FIN WHILE
        }catch(SQLException ex) {
            Notificacion.dialogoException(ex);
        }//FIN TRY-CATCH
        return orden;
    }//FIN METODO
    
    public static final boolean update(Connection connection, OrdenProduccion ordenProduccion) {
    	String query = "UPDATE ut_ordenesproduccion SET uf_Status = ? WHERE Sys_PK = ?";
    	try {
    		PreparedStatement sentenciaPreparada = connection.prepareStatement(query);
    		sentenciaPreparada.setInt(1, ordenProduccion.getStatus());
    		sentenciaPreparada.setInt(2, ordenProduccion.getSysPK());
    		sentenciaPreparada.execute();
    		return true;
    	} catch (SQLException ex) {
    		Notificacion.dialogoException(ex);
    		return false;
    	}//FIN TRY/CATCH
    }//FIN METODO
			
	public static ObservableList<OrdenProduccion> toObservableList(ArrayList<OrdenProduccion> arrayList) {
		ObservableList<OrdenProduccion> listaObservableOrdenProduccion = FXCollections.observableArrayList();
		for (OrdenProduccion orden : arrayList) 
			listaObservableOrdenProduccion.add(orden);
		return listaObservableOrdenProduccion;
	}//FIN METODO
}//FIN CLASE
