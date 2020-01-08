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
		String consulta = "INSERT INTO ordenesproduccion (Fecha, Lote, Status, DetalleOrdenCompraFK) VALUES (CURDATE(),?,0,?)";
		try {
			PreparedStatement sentenciaPreparada = connection.prepareStatement(consulta);
			sentenciaPreparada.setString(1, ordenProduccion.getLote());
			sentenciaPreparada.setInt(2, ordenProduccion.getDetalleCotizacionFK());
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
		String consulta = "SELECT Sys_PK, Fecha, Lote, Status, DetalleOrdenCompraFK FROM ordenesproduccion WHERE Status != 3 AND Status != 4";
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(consulta);
			while (resultados.next()) {
				OrdenProduccion orden = new OrdenProduccion();
				orden.setSysPK(resultados.getInt(1));
				orden.setFecha(resultados.getDate(2));
				orden.setLote(resultados.getString(3));
				orden.setStatus(resultados.getInt(4));
				orden.setDetalleOrdenCompraFK(resultados.getInt(5));
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
		String consulta = "SELECT ordenesproduccion.Sys_PK, ordenesproduccion.Fecha, ordencompras.PMP, ordenesproduccion.Status, \r\n" + 
				"ordenesproduccion.DetalleOrdenCompraFK, clientes.Nombre, ordencompras.Folio, componentes.Sys_PK, componentes.NumeroParte, componentes.Descripcion, detalleordencompras.PorEntregar \r\n" + 
				"FROM ordenesproduccion INNER JOIN detalleordencompras ON ordenesproduccion.DetalleOrdenCompraFK = detalleordencompras.Sys_PK\r\n" + 
				"INNER JOIN componentes ON detalleordencompras.ComponenteFK = componentes.Sys_PK\r\n" + 
				"INNER JOIN ordencompras ON detalleordencompras.OrdenCompraFK = ordencompras.Sys_PK\r\n" + 
				"INNER JOIN clientes ON ordencompras.ClienteFK = clientes.Sys_PK"
				+ " ORDER BY  Fecha ASC";
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(consulta);
			while (resultados.next()) {
				OrdenProduccion orden = new OrdenProduccion();
				orden.setSysPK(resultados.getInt(1));
				orden.setFecha(resultados.getDate(2));
				orden.setLote(resultados.getString(3));
				orden.setStatus(resultados.getInt(4));
				orden.setDetalleOrdenCompraFK(resultados.getInt(5));
				orden.setCliente(resultados.getString(6));
				orden.setOrdenCompra(resultados.getString(7));
				orden.setComponenteFK(resultados.getInt(8));
				orden.setNumeroParte(resultados.getString(9));
				orden.setDescripcion(resultados.getString(10));
				orden.setCantidad(resultados.getDouble(11));
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
		String consulta = "SELECT ordenesproduccion.Sys_PK, ordenesproduccion.Fecha, ordencompras.PMP, ordenesproduccion.Status, ordenesproduccion.DetalleOrdenCompraFK, clientes.Nombre, ordencompras.Folio, componentes.Sys_PK, componentes.NumeroParte, componentes.Descripcion, detalleordencompras.PorEntregar" + 
				" FROM ordenesproduccion INNER JOIN detalleordencompras ON ordenesproduccion.DetalleOrdenCompraFK = detalleordencompras.Sys_PK " +  
				"INNER JOIN componentes ON detalleordencompras.ComponenteFK = componentes.Sys_PK " + 
				"INNER JOIN ordencompras ON detalleordencompras.OrdenCompraFK = ordencompras.Sys_PK " + 
				"INNER JOIN clientes ON ordencompras.ClienteFK = clientes.Sys_PK " +
				" WHERE ordenesproduccion.Fecha BETWEEN '"+ fechaInicio +"' AND '" + fechaFinal +"' ORDER BY Fecha ASC";
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(consulta);
			while (resultados.next()) {
				OrdenProduccion orden = new OrdenProduccion();
				orden.setSysPK(resultados.getInt(1));
				orden.setFecha(resultados.getDate(2));
				orden.setLote(resultados.getString(3));
				orden.setStatus(resultados.getInt(4));
				orden.setDetalleOrdenCompraFK(resultados.getInt(5));
				orden.setCliente(resultados.getString(6));
				orden.setOrdenCompra(resultados.getString(7));
				orden.setComponenteFK(resultados.getInt(8));
				orden.setNumeroParte(resultados.getString(9));
				orden.setDescripcion(resultados.getString(10));
				orden.setCantidad(resultados.getDouble(11));
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
		String consulta = "SELECT ordenesproduccion.Sys_PK, ordenesproduccion.Fecha, ordencompras.PMP, ordenesproduccion.Status, \r\n" + 
				"ordenesproduccion.DetalleOrdenCompraFK, clientes.Nombre, ordencompras.Folio, componentes.Sys_PK, componentes.NumeroParte, componentes.Descripcion, detalleordencompras.PorEntregar \r\n" +  
				"FROM ordenesproduccion INNER JOIN detalleordencompras ON ordenesproduccion.DetalleOrdenCompraFK = detalleordencompras.Sys_PK " + 
				"INNER JOIN componentes ON detalleordencompras.ComponenteFK = componentes.Sys_PK\r\n" + 
				"INNER JOIN ordencompras ON detalleordencompras.OrdenCompraFK = ordencompras.Sys_PK\r\n" + 
				"INNER JOIN clientes ON ordencompras.ClienteFK = clientes.Sys_PK WHERE clientes.Nombre LIKE '%" + like + " %' "
				+ "OR cotizaciones.Referencia LIKE '%" + like + "%' OR ordenesproduccion.Lote LIKE '%" + like + "%' OR proyectos.Codigo LIKE '%" + like + "%'"
				+ " OR componentes.NumeroParte LIKE '%" + like + "%'";
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(consulta);
			while (resultados.next()) {
				OrdenProduccion orden = new OrdenProduccion();
				orden.setSysPK(resultados.getInt(1));
				orden.setFecha(resultados.getDate(2));
				orden.setLote(resultados.getString(3));
				orden.setStatus(resultados.getInt(4));
				orden.setDetalleOrdenCompraFK(resultados.getInt(5));
				orden.setCliente(resultados.getString(6));
				orden.setOrdenCompra(resultados.getString(7));
				orden.setComponenteFK(resultados.getInt(8));
				orden.setNumeroParte(resultados.getString(9));
				orden.setDescripcion(resultados.getString(10));
				orden.setCantidad(resultados.getDouble(11));
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
		String consulta = "SELECT ordenesproduccion.Sys_PK, ordenesproduccion.Fecha, ordencompras.PMP, ordenesproduccion.Status, \r\n" + 
				"ordenesproduccion.DetalleOrdenCompraFK, clientes.Nombre, ordencompras.Folio, componentes.Sys_PK, componentes.NumeroParte, componentes.Descripcion, detalleordencompras.PorEntregar \r\n" +  
				"FROM ordenesproduccion INNER JOIN detalleordencompras ON ordenesproduccion.DetalleOrdenCompraFK = detalleordencompras.Sys_PK " + 
				"INNER JOIN componentes ON detalleordencompras.ComponenteFK = componentes.Sys_PK\r\n" + 
				"INNER JOIN ordencompras ON detalleordencompras.OrdenCompraFK = ordencompras.Sys_PK\r\n" + 
				"INNER JOIN clientes ON ordencompras.ClienteFK = clientes.Sys_PK \r\n"
				+ "WHERE ordenesproduccion.Status = " + status;
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(consulta);
			while (resultados.next()) {
				OrdenProduccion orden = new OrdenProduccion();
				orden.setSysPK(resultados.getInt(1));
				orden.setFecha(resultados.getDate(2));
				orden.setLote(resultados.getString(3));
				orden.setStatus(resultados.getInt(4));
				orden.setDetalleOrdenCompraFK(resultados.getInt(5));
				orden.setCliente(resultados.getString(6));
				orden.setOrdenCompra(resultados.getString(7));
				orden.setComponenteFK(resultados.getInt(8));
				orden.setNumeroParte(resultados.getString(9));
				orden.setDescripcion(resultados.getString(10));
				orden.setCantidad(resultados.getDouble(11));
				arrayListaOrdenProduccion.add(orden);
			}//FIN WHILE
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY CATCH
		return arrayListaOrdenProduccion;
	}//FIN METODO
	
	public static OrdenProduccion searchOrdenProduccion(Connection connection, int detalleOrdenCompraFK) {
		OrdenProduccion orden = new OrdenProduccion();
		String consulta = " SELECT Sys_PK, Fecha, Lote, Status, DetalleOrdenCompraFK FROM ordenesproduccion WHERE DetalleOrdenCompraFK = " + detalleOrdenCompraFK;
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(consulta);
			while (resultados.next()) {
				orden.setSysPK(resultados.getInt(1));
				orden.setFecha(resultados.getDate(2));
				orden.setLote(resultados.getString(3));
				orden.setStatus(resultados.getInt(4));
				orden.setDetalleOrdenCompraFK(resultados.getInt(5));
			}//FIN WHILE
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY CATCH
		return orden;
	}//FIN METODO
	
	public static final int ultimoSysPK(Connection connection) {
		String query = "SELECT Sys_PK FROM ordenesproduccion order by Sys_PK asc";
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
		String query = "SELECT Sys_PK FROM ordenesproduccion WHERE Lote = '" + lote + "' AND Status != 3 AND Status != 4";
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
        String query = "SELECT Sys_PK, Fecha, Lote, Status, DetalleOrdenCompraFK FROM ordenesproduccion WHERE lote = '" + lote + "'";
        try {
            Statement sentencia = connection.createStatement();
            ResultSet resultados = sentencia.executeQuery(query);
            while(resultados.next()) {
            	orden.setSysPK(resultados.getInt(1));
				orden.setFecha(resultados.getDate(2));
				orden.setLote(resultados.getString(3));
				orden.setStatus(resultados.getInt(4));
				orden.setDetalleOrdenCompraFK(resultados.getInt(5));
            }//FIN WHILE
        }catch(SQLException ex) {
            Notificacion.dialogoException(ex);
        }//FIN TRY-CATCH
        return orden;
    }//FIN METODO
	
	//METODO PARA OBTENER LA FECHA INICIAL Y FECHA DE ENTREGA DEL LOTE
	public static final OrdenProduccion fechasPorLote(Connection connection, int sysPK){
		OrdenProduccion orden = new OrdenProduccion();
		String query = "SELECT ordenesproduccion.Fecha, detallecotizaciones.FechaEntrega FROM  ordenesproduccion INNER JOIN detalleordencompras ON ordenesproduccion.DetalleOrdenCompraFK = detalleordencompras.Sys_PK WHERE ordenesproduccion.Sys_PK = " + sysPK;
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(query);
			while(resultados.next()) {
				orden.setFecha(resultados.getDate(1));
				orden.setFechaEntrega(resultados.getDate(2));
			}//FIN WHILE
		}catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY-CATCH
		return orden;
	}//FIN METODO
		
	public static ObservableList<OrdenProduccion> toObservableList(ArrayList<OrdenProduccion> arrayList) {
		ObservableList<OrdenProduccion> listaObservableOrdenProduccion = FXCollections.observableArrayList();
		for (OrdenProduccion orden : arrayList) 
			listaObservableOrdenProduccion.add(orden);
		return listaObservableOrdenProduccion;
	}//FIN METODO
}//FIN CLASE
