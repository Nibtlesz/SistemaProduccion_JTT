package mx.shf6.produccion.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import mx.shf6.produccion.model.Cliente;
import mx.shf6.produccion.model.OrdenCompra;
import mx.shf6.produccion.utilities.Notificacion;

public class OrdenCompraDAO {

	public static final boolean create(Connection connection, OrdenCompra ordenCompra) {
		String query = "INSERT INTO ordencompras(FechaPedido, Folio, PMP, Comentarios, ClienteFK) VALUES (?, ?, ?, ?, ?)";
		try {
			PreparedStatement sentenciaPreparada = connection.prepareStatement(query);
			sentenciaPreparada.setDate(1, ordenCompra.getFechaPedido());
			sentenciaPreparada.setString(2, ordenCompra.getFolio());
			sentenciaPreparada.setString(3, ordenCompra.getPMP());
			sentenciaPreparada.setString(4, ordenCompra.getComentarios());
			sentenciaPreparada.setInt(5, ordenCompra.getClienteFK().getSysPK());
			sentenciaPreparada.execute();
			return true;
		} catch(SQLException ex) {
			Notificacion.dialogoException(ex);
			return false;
		}//FIN TRY/CATCH
	}//FIN METODO
	
	public static final ArrayList<OrdenCompra> read(Connection connection) {
		ArrayList<OrdenCompra> arrayListOrdenCompra = new ArrayList<OrdenCompra>();
		String query = "SELECT * FROM infoordencompras WHERE OrdenCompraDocumento = 2";
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(query);
			while (resultados.next()) {
				OrdenCompra ordenCompra = new OrdenCompra();
				ordenCompra.setSysPK(resultados.getInt("OrdenCompraSysPK"));
				ordenCompra.setFechaPedido(resultados.getDate("OrdenCompraFechaPedido"));
				ordenCompra.setFolio(resultados.getString("OrdenCompraFolio"));
				ordenCompra.setComentarios(resultados.getString("OrdenCompraComentarios"));
				Cliente cliente = new Cliente();
				cliente.setSysPK(resultados.getInt("OrdenCompraClienteSysPK"));
				cliente.setCodigo(resultados.getString("OrdenCompraClientesCodigo"));
				cliente.setNombre(resultados.getString("OrdenCompraClientesNombre"));
				cliente.setStatus(resultados.getInt("OrdenCompraClientesStatus"));
				cliente.setRegistroContribuyente(resultados.getString("OrdenCompraClientesRegistroContribuyente"));
				cliente.setTelefono(resultados.getString("OrdenCompraClientesTelefono"));
				cliente.setCorreo(resultados.getString("OrdenCompraClientesCorreo"));
				cliente.setDomicilioFK(resultados.getInt("OrdenCompraClientesDomicilioFK"));
				ordenCompra.setClienteFK(cliente);
				arrayListOrdenCompra.add(ordenCompra);
			}//FIN WHILE
		} catch(SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY/CATCH
		return arrayListOrdenCompra;
	}//FIN METODO
	
	public static final boolean update(Connection connection, OrdenCompra ordenCompra) {
		String query = "UPDATE ordencompras SET FechaPedido = ?, Folio = ?, PMP = ?, Comentarios = ?, ClienteFK = ? WHERE Sys_PK = ?";
		try {
			PreparedStatement sentenciaPreparada = connection.prepareStatement(query);
			sentenciaPreparada.setDate(1, ordenCompra.getFechaPedido());
			sentenciaPreparada.setString(2, ordenCompra.getFolio());
			sentenciaPreparada.setString(3, ordenCompra.getPMP());
			sentenciaPreparada.setString(4, ordenCompra.getComentarios());
			sentenciaPreparada.setInt(5, ordenCompra.getClienteFK().getSysPK());
			sentenciaPreparada.setInt(6, ordenCompra.getSysPK());
			sentenciaPreparada.execute();
			return true;
		} catch(SQLException ex) {
			Notificacion.dialogoException(ex);
			return false;
		}//FIN TRY/CATCH
	}//FIN METODO
	
	public static final boolean delete(Connection connection, OrdenCompra ordenCompra) {
		String query = "DELETE FROM ordencompras WHERE Sys_PK = ?";
		try {
			PreparedStatement sentenciaPreparada = connection.prepareStatement(query);
			sentenciaPreparada.setInt(1, ordenCompra.getSysPK());
			sentenciaPreparada.execute();
			return true;
		} catch(SQLException ex) {
			Notificacion.dialogoException(ex);
			return false;
		}//FIN TRY/CATCH
	}//FIN METODO
}//FIN CLASE
