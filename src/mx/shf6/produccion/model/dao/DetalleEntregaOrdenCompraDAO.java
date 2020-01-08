package mx.shf6.produccion.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import mx.shf6.produccion.model.Cliente;
import mx.shf6.produccion.model.Componente;
import mx.shf6.produccion.model.DetalleEntregaOrdenCompra;
import mx.shf6.produccion.model.DetalleOrdenCompra;
import mx.shf6.produccion.model.OrdenCompra;
import mx.shf6.produccion.utilities.Dimensiones;
import mx.shf6.produccion.utilities.Notificacion;

public class DetalleEntregaOrdenCompraDAO {

	public static final boolean create(Connection connection, DetalleEntregaOrdenCompra detalleEntregaOrdenCompra) {
		String query = "INSERT INTO detalleentregaordencompras(Factura, Cantidad, Fecha, DetalleOrdenCompraFK) VALUES (?, ?, ?, ?)";
		try {
			PreparedStatement sentenciaPreparada = connection.prepareStatement(query);
			sentenciaPreparada.setString(1, detalleEntregaOrdenCompra.getFactura());
			sentenciaPreparada.setInt(2, detalleEntregaOrdenCompra.getCantidad());
			sentenciaPreparada.setDate(3, detalleEntregaOrdenCompra.getFecha());
			sentenciaPreparada.setInt(4, detalleEntregaOrdenCompra.getDetalleOrdenCompraFK().getSysPK());
			sentenciaPreparada.execute();
			return true;
		} catch(SQLException ex) {
			Notificacion.dialogoException(ex);
			return false;
		}//FIN TRY/CATCH
	}//FIN METODO
	
	public static final ArrayList<DetalleEntregaOrdenCompra> read(Connection connection) {
		ArrayList<DetalleEntregaOrdenCompra> arrayListDetalleEntregaOrdenCompra = new ArrayList<DetalleEntregaOrdenCompra>();
		String query = "SELECT * FROM infodetalleentregaordencompras";
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(query);
			while(resultados.next()) {
				DetalleEntregaOrdenCompra detalleEntregaOrdenCompra = new DetalleEntregaOrdenCompra();
				detalleEntregaOrdenCompra.setSysPK(resultados.getInt("DetalleEntregaOrdenComprasSysPK"));
				detalleEntregaOrdenCompra.setFactura(resultados.getString("DetalleEntregaOrdenComprasFactura"));
				detalleEntregaOrdenCompra.setCantidad(resultados.getInt("DetalleEntregaOrdenComprasCantidad"));
				detalleEntregaOrdenCompra.setFecha(resultados.getDate("DetalleEntregaOrdenComprasFecha"));
				DetalleOrdenCompra detalleOrdenCompra = new DetalleOrdenCompra();
				detalleOrdenCompra.setSysPK(resultados.getInt("DetalleOrdenComprasSysPK"));
				detalleOrdenCompra.setPlanoOdernamiento(resultados.getString("DetalleOrdenComprasPlanoOrdenamiento"));
				detalleOrdenCompra.setItem(resultados.getString("DetalleOrdenComprasItem"));
				detalleOrdenCompra.setFechaCliente(resultados.getDate("DetalleOrdenComprasFechaCliente"));
				detalleOrdenCompra.setEntregaFinal(resultados.getDate("DetalleOrdenComprasEntregaFinal"));
				detalleOrdenCompra.setPorEntregar(resultados.getInt("DetalleOrdenComprasPorEntregar"));
				detalleOrdenCompra.setSaldo(resultados.getInt("DetalleOrdenComprasSaldo"));
				detalleOrdenCompra.setProcesoPintura(resultados.getString("DetalleOrdenComprasProcesoPintura"));
				OrdenCompra ordenCompra = new OrdenCompra();
				ordenCompra.setSysPK(resultados.getInt("OrdenCompraSysPK"));
				ordenCompra.setFechaPedido(resultados.getDate("OrdenCompraFechaPedido"));
				ordenCompra.setFolio(resultados.getString("OrdenCompraFolio"));
				ordenCompra.setPMP(resultados.getString("OrdenCompraPMP"));
				ordenCompra.setComentarios("OrdenCompraComentarios");
				Cliente cliente = new Cliente();
				cliente.setSysPK(resultados.getInt("OrdenCompraClienteSysPK"));
				cliente.setCodigo(resultados.getString("OrdenCompraClientesCodigo"));
				cliente.setNombre(resultados.getString("OrdenCompraClientesNombre"));
				cliente.setStatus(resultados.getInt("OrdenCompraClientesStatus"));
				cliente.setFechaRegistro(resultados.getDate("OrdenCompraClientesFechaRegistro"));
				cliente.setRegistroContribuyente(resultados.getString("OrdenCompraClientesRegistroContribuyente"));
				cliente.setTelefono(resultados.getString("OrdenCompraClientesTelefono"));
				cliente.setCorreo(resultados.getString("OrdenCompraClientesCorreo"));
				cliente.setRutaCarpeta(resultados.getString("OrdenCompraClientesRutaCarpeta"));
				cliente.setDomicilioFK(resultados.getInt("OrdenCompraClientesDomicilioFK"));
				ordenCompra.setClienteFK(cliente);
				detalleOrdenCompra.setOrdenCompraFK(ordenCompra);
				Componente componente = new Componente();
				componente.setSysPK(resultados.getInt("DetalleOrdenComprasComponentesSysPK"));
				componente.setNumeroParte(resultados.getString("DetalleOrdenComprasComponentesNumeroParte"));
				componente.setDescripcion(resultados.getString("DetalleOrdenComprasComponentesDescripcion"));
				Dimensiones dimensiones = new Dimensiones();
				dimensiones.setLargo(resultados.getDouble("DetalleOrdenComprasComponentesLargo"));
				dimensiones.setUnidadLargo(resultados.getString("DetalleOrdenComprasComponentesUnidadLargo"));
				dimensiones.setAncho(resultados.getDouble("DetalleOrdenComprasComponentesAncho"));
				dimensiones.setUnidadAncho(resultados.getString("DetalleOrdenComprasComponantesUnidadAncho"));
				dimensiones.setAlto(resultados.getDouble("DetalleOrdenComprasComponentesAlto"));
				dimensiones.setUnidadAlto(resultados.getString("DetalleOrdenComprasComponentesUnidadAlto"));
				componente.setGradoMaterial(resultados.getString("DetalleOrdenComprasComponentesGrado"));
				dimensiones.setEspesor(resultados.getDouble("DetalleOrdenComprasComponentesEspesor"));
				dimensiones.setUnidadEspesor(resultados.getString("DetalleOrdenComprasComponentesUnidadEspesor"));
				dimensiones.setDiametroExterior(resultados.getDouble("DetalleOrdenComprasComponentesDiametroExterno"));
				dimensiones.setUnidadDExt(resultados.getString("DetalleOrdenComprasComponentesUnidadDExt"));
				dimensiones.setDiametroInterior(resultados.getDouble("DetalleOrdenComprasComponentesDiametroInterno"));
				dimensiones.setUnidadDInt(resultados.getString("DetalleOrdenComprasComponentesUnidadDInt"));
				dimensiones.setAlto2(resultados.getDouble("DetalleOrdenComprasComponentesAlto2"));
				dimensiones.setUnidadAlto2(resultados.getString("DetalleOrdenComprasComponentesUnidadAlto2"));
				dimensiones.setAnchoTotal(resultados.getDouble("DetalleOrdenComprasComponentesAnchoTotal"));
				dimensiones.setUnidadAnchoTotal(resultados.getString("DetalleOrdenComprasComponentesUnidadAnchoTotal"));
				dimensiones.setCodigoCatalogo(resultados.getString("DetalleOrdenComprasComponentesCodigoCatalogo"));
				componente.setTipoComponente(resultados.getString("DetalleOrdenComprasComponentesTipoComponente"));
				componente.setCosto(resultados.getDouble("DetalleOrdenComprasComponentesCosto"));
				componente.setCostoDirecto(resultados.getDouble("DetalleOrdenComprasComponentesCostoDirecto"));
				componente.setCostoIndirecto(resultados.getDouble("DetalleOrdenComprasComponentesCostoIndirecto"));
				componente.setMaterialFK(resultados.getInt("DetalleOrdenComprasComponentesMaterialFK"));;
				componente.setTipoMiscelaneoFK(resultados.getInt("DetalleOrdenComprasComponentesTipoMiscelaneoFK"));
				componente.setTipoMateriaPrimaFK(resultados.getInt("DetalleOrdenComprasComponentesTipoMateriaPrimaFK"));
				componente.setAcabadoFK(resultados.getInt("DetalleOrdenComprasComponentesAcabadoFK"));
				componente.setTratamientoFK(resultados.getInt("DetalleOrdenComprasTratamientoFK"));
				componente.setNotas(resultados.getString("DetalleOrdenComprasComponentesNotas"));
				componente.setStatus(resultados.getInt("DetalleOrdenComprasComponentesStatus"));
				componente.setConsecutivo(resultados.getInt("DetalleOrdenComprasComponentesConsecutivo"));
				componente.setClienteFK(resultados.getInt("OrdenCompraClienteSysPK"));
				componente.setEsInterno(resultados.getInt("DetalleOrdenComprasComponentesEsInterno"));
				componente.setHilos(resultados.getString("DetalleOrdenComprasComponentesHilos"));
				componente.setRevision(resultados.getString("DetalleOrdenComprasComponentesRevision"));
				componente.setDimensiones(dimensiones);
				detalleOrdenCompra.setComponenteFK(componente);
				detalleEntregaOrdenCompra.setDetalleOrdenCompraFK(detalleOrdenCompra);
				arrayListDetalleEntregaOrdenCompra.add(detalleEntregaOrdenCompra);
			}//FIN WHILE
		} catch(SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY/CATCH
		return arrayListDetalleEntregaOrdenCompra;
	}//FIN METODO
	
	public static final ArrayList<DetalleEntregaOrdenCompra> readPorDetalleOrdenCompra(Connection connection, DetalleOrdenCompra detalleOrdenCompra) {
		ArrayList<DetalleEntregaOrdenCompra> arrayListDetalleEntregaOrdenCompra = new ArrayList<DetalleEntregaOrdenCompra>();
		String query = "SELECT * FROM infodetalleentregaordencompras WHERE DetalleOrdenComprasSysPK =" + detalleOrdenCompra.getSysPK();
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(query);
			while(resultados.next()) {
				DetalleEntregaOrdenCompra detalleEntregaOrdenCompra = new DetalleEntregaOrdenCompra();
				detalleEntregaOrdenCompra.setSysPK(resultados.getInt("DetalleEntregaOrdenComprasSysPK"));
				detalleEntregaOrdenCompra.setFactura(resultados.getString("DetalleEntregaOrdenComprasFactura"));
				detalleEntregaOrdenCompra.setCantidad(resultados.getInt("DetalleEntregaOrdenComprasCantidad"));
				detalleEntregaOrdenCompra.setFecha(resultados.getDate("DetalleEntregaOrdenComprasFecha"));
				detalleEntregaOrdenCompra.setDetalleOrdenCompraFK(detalleOrdenCompra);
				arrayListDetalleEntregaOrdenCompra.add(detalleEntregaOrdenCompra);
			}//FIN WHILE
		} catch(SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY/CATCH
		return arrayListDetalleEntregaOrdenCompra;
	}//FIN METODO
	
	public static final boolean update(Connection connection, DetalleEntregaOrdenCompra detalleEntregaOrdenCompra) {
		String query = "UPDATE detalleentregaordencompras SET Factura = ?, Cantidad = ?, Fecha = ?, DetalleOrdenCompraFK = ? WHERE Sys_PK = ?";
		try {
			PreparedStatement sentenciaPreparada = connection.prepareStatement(query);
			sentenciaPreparada.setString(1, detalleEntregaOrdenCompra.getFactura());
			sentenciaPreparada.setInt(2, detalleEntregaOrdenCompra.getCantidad());
			sentenciaPreparada.setDate(3, detalleEntregaOrdenCompra.getFecha());
			sentenciaPreparada.setInt(4, detalleEntregaOrdenCompra.getDetalleOrdenCompraFK().getSysPK());
			sentenciaPreparada.setInt(5, detalleEntregaOrdenCompra.getSysPK());
			sentenciaPreparada.execute();
			return true;
		} catch(SQLException ex) {
			Notificacion.dialogoException(ex);
			return false;
		}//FIN TRY/CATCH
	}//FIN METODO
	
	public static final boolean delete(Connection connection, DetalleEntregaOrdenCompra detalleEntregaOrdenCompra) {
		String query = "DELETE FROM detalleentregaordencompras WHERE Sys_PK = ?";
		try {
			PreparedStatement sentenciaPreparada = connection.prepareStatement(query);
			sentenciaPreparada.setInt(1, detalleEntregaOrdenCompra.getSysPK());
			sentenciaPreparada.execute();
			return true;
		} catch(SQLException ex) {
			Notificacion.dialogoException(ex);
			return false;
		}//FIN TRY/CATCH
	}//FIN METODO
}//FIN CLASE
