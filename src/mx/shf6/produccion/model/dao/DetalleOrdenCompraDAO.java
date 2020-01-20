package mx.shf6.produccion.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import mx.shf6.produccion.model.Cliente;
import mx.shf6.produccion.model.Componente;
import mx.shf6.produccion.model.DetalleOrdenCompra;
import mx.shf6.produccion.model.OrdenCompra;
import mx.shf6.produccion.utilities.Dimensiones;
import mx.shf6.produccion.utilities.Notificacion;

public class DetalleOrdenCompraDAO {

	public static final boolean create(Connection connection, DetalleOrdenCompra detalleOrdenCompra) {
		String query = "INSERT INTO detalleordencompras (PlanoOrdenamiento, Item, FechaCliente, EntregaFinal, PorEntregar, Saldo, ProcesoPintura, OrdenCompraFK, ComponenteFK) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
		try {
			PreparedStatement sentenciaPreparada = connection.prepareStatement(query);
			sentenciaPreparada.setString(1, detalleOrdenCompra.getPlanoOrdenamiento());
			sentenciaPreparada.setString(2, detalleOrdenCompra.getItem());
			sentenciaPreparada.setDate(3, detalleOrdenCompra.getFechaCliente());
			sentenciaPreparada.setDate(4, detalleOrdenCompra.getEntregaFinal());
			sentenciaPreparada.setInt(5, detalleOrdenCompra.getPorEntregar());
			sentenciaPreparada.setInt(6, detalleOrdenCompra.getSaldo());
			sentenciaPreparada.setString(7, detalleOrdenCompra.getProcesoPintura());
			sentenciaPreparada.setInt(8, detalleOrdenCompra.getOrdenCompraFK().getSysPK());
			sentenciaPreparada.setInt(9, detalleOrdenCompra.getComponenteFK().getSysPK());
			sentenciaPreparada.execute();
			return true;
		} catch(SQLException ex) {
			Notificacion.dialogoException(ex);
			return false;
		}//FIN TRY/CATCH
	}//FIN METODO
	
	public static final ArrayList<DetalleOrdenCompra> read(Connection connection) {
		ArrayList<DetalleOrdenCompra> arrayListDetalleOrdenCompra = new ArrayList<DetalleOrdenCompra>();
		String query = "SELECT * FROM infodetalleordencompras";
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(query);
			while(resultados.next()) {
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
				arrayListDetalleOrdenCompra.add(detalleOrdenCompra);
			}//FIN WHILE
		} catch(SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY/CATCH
		return arrayListDetalleOrdenCompra;
	}//FIN METODO
	
	public static final ArrayList<DetalleOrdenCompra> readPorOrdenCompra(Connection connection, OrdenCompra ordenCompra) {
		ArrayList<DetalleOrdenCompra> arrayListDetalleOrdenCompra = new ArrayList<DetalleOrdenCompra>();
		String query = "SELECT * FROM infodetalleordencompras WHERE OrdenCompraSysPK =" + ordenCompra.getSysPK();
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(query);
			while(resultados.next()) {
				DetalleOrdenCompra detalleOrdenCompra = new DetalleOrdenCompra();
				detalleOrdenCompra.setSysPK(resultados.getInt("DetalleOrdenComprasSysPK"));
				detalleOrdenCompra.setPorEntregar(resultados.getInt("DetalleOrdenComprasCantidad"));
				detalleOrdenCompra.setSaldo(resultados.getInt("DetalleOrdenComprasEnProduccion"));
				detalleOrdenCompra.setOrdenCompraFK(ordenCompra);
				Componente componente = new Componente();
				componente.setSysPK(resultados.getInt("DetalleOrdenComprasProductoSysPK"));
				componente.setNumeroParte(resultados.getString("DetalleOrdenComprasProductoCodigo"));
				componente.setDescripcion(resultados.getString("DetalleOrdenComprasProductoDescripcion"));
				detalleOrdenCompra.setComponenteFK(componente);
				arrayListDetalleOrdenCompra.add(detalleOrdenCompra);
			}//FIN WHILE
		} catch(SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY/CATCH
		return arrayListDetalleOrdenCompra;
	}//FIN METODO
	
	public static final boolean update(Connection connection, DetalleOrdenCompra detalleOrdenCompra) {
		String query = "UPDATE dventa SET uf_EnProduccion = uf_EnProduccion + ? WHERE Sys_PK = ?";
		try {
			PreparedStatement sentenciaPreparada = connection.prepareStatement(query);
			sentenciaPreparada.setInt(1, detalleOrdenCompra.getSaldo());
			sentenciaPreparada.setInt(2, detalleOrdenCompra.getSysPK());
			sentenciaPreparada.execute();
			return true;
		} catch(SQLException ex) {
			Notificacion.dialogoException(ex);
			return false;
		}//FIN TRY/CATCH
	}//FIN METODO
	
	public static final boolean delete(Connection connection, DetalleOrdenCompra detalleOrdenCompra) {
		String query = "DELETE FROM detalleordencompras WHERE Sys_PK = ?";
		try {
			PreparedStatement sentenciaPreparada = connection.prepareStatement(query);
			sentenciaPreparada.setInt(1, detalleOrdenCompra.getSysPK());
			sentenciaPreparada.execute();
			return true;
		} catch(SQLException ex) {
			Notificacion.dialogoException(ex);
			return false;
		}//FIN TRY/CATCH
	}//FIN METODO
	
	public static final ResultSet readNombreColumnas (Connection connection, int ordenCompra){
		ResultSet resultados = null;
		String query = "SELECT OrdenCompraFolio, detalleOrdenComprasItem, DetalleOrdenComprasComponentesNumeroParte, DetalleOrdenComprasComponentesDescripcion, DetalleOrdenComprasFechaCliente, DetalleOrdenComprasPorEntregar, DetalleOrdenComprasComponentesTipoComponente, DetalleOrdenComprasProcesoPintura, OrdenCompraPMP  FROM infodetalleordencompras WHERE OrdenCompraSysPK = " + ordenCompra;
		try {
			Statement sentencia =  connection.createStatement();
			resultados = sentencia.executeQuery(query);
		}catch(SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY-CATCH
		return resultados;
	}//FIN METODO
}//FIN CLASE
