package mx.shf6.produccion.utilities;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.shf6.produccion.model.Cliente;
import mx.shf6.produccion.model.Componente;
import mx.shf6.produccion.model.Cotizacion;
import mx.shf6.produccion.model.DetalleCardex;
import mx.shf6.produccion.model.DetalleComponente;
import mx.shf6.produccion.model.DetalleCotizacion;
import mx.shf6.produccion.model.DetalleProceso;
import mx.shf6.produccion.model.Empleado;
import mx.shf6.produccion.model.Proceso;
import mx.shf6.produccion.model.TipoComponente;
import mx.shf6.produccion.model.Usuario;
import mx.shf6.produccion.model.dao.ComponenteDAO;
import mx.shf6.produccion.model.dao.DetalleComponenteDAO;
import mx.shf6.produccion.model.dao.DetalleCotizacionDAO;
import mx.shf6.produccion.model.dao.DetalleProcesoDAO;
import mx.shf6.produccion.model.dao.EmpleadoDAO;
import mx.shf6.produccion.model.dao.ProcesoDAO;
import mx.shf6.produccion.view.DialogoMovimientoInventario;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

public class GenerarDocumento {
	//PROPIEDADES
	private static List<DetalleCotizacion> datosTabla;
	private static List<DetalleProceso> tablaDetalleProceso;

	public static void generaCotizacion(Connection connection, Cotizacion cotizacion, Usuario usuario) {
		try {
			Empleado empleado = new Empleado();
			empleado = EmpleadoDAO.readEmpleado(connection, usuario.getEmpleadoFK());

			JasperReport jasperReport = (JasperReport) JRLoader.loadObjectFromFile("resources/COTIZACION.jasper");
			datosTabla = DetalleCotizacionDAO.toList(DetalleCotizacionDAO.readCotizacionDetalle(connection, cotizacion.getSysPK()));
			JRBeanCollectionDataSource itemsTabla = new JRBeanCollectionDataSource(datosTabla);
			Map<String,Object> parameters = new HashMap<String,Object>();
			parameters.put("Fecha", cotizacion.getFecha());
			parameters.put("Referencia", cotizacion.getReferencia());
			parameters.put("Cliente", cotizacion.getCliente(connection).getNombre());
			String domicilio = cotizacion.getCliente(connection).getDomicilio(connection).showDomicilioCompleto();
			if (domicilio != null)
				parameters.put("Domicilio", domicilio);
			else
				parameters.put("Domicilio", "");
			parameters.put("Solicitante", cotizacion.getSolicitante());
			parameters.put("AreaDepartamento", cotizacion.getAreaDepartamento());
			parameters.put("TelefonoFax", cotizacion.getTelefonoFax());
			parameters.put("Email", cotizacion.getEmail());
			parameters.put("TipoServicio", cotizacion.getTipoServicio());
			parameters.put("TablaReporte", itemsTabla);
			parameters.put("FechaEntrega", cotizacion.getFechaEntrega());
			parameters.put("CondicionEmbarque", cotizacion.getCondicionEmbarque());
			parameters.put("CondicionPago", cotizacion.getCondicionPago());
			parameters.put("Moneda", cotizacion.getDescripcionMoneda());
			parameters.put("TipoCambio", cotizacion.getTipoCambio());
			parameters.put("Observaciones", cotizacion.getObservaciones());
			parameters.put("Vigencia", cotizacion.getVigencia());
			parameters.put("JefePlaneacion", usuario.getUsuario());
			parameters.put("Puesto", empleado.getPuesto(connection).getDescripcion());
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());
			JasperViewer jasperView = new JasperViewer(jasperPrint, false);
			jasperView.setVisible(true);
			jasperView.setTitle("Cotizacion: " + cotizacion.getReferencia());
		} catch (JRException jre) {
			Notificacion.dialogoException(jre);
		}//TRY/CATH
	}//END METHOD


	public static void generarHojaProceso(Connection conexion, int sysPK) {
		JasperReport jasperReport;
		try {
			Proceso procesito = new Proceso();
			procesito = ProcesoDAO.readProcesoHoja(conexion, sysPK);
			if (procesito.getTipoComponente().equals("E") || procesito.getTipoComponente().equals("A")) {
				jasperReport = (JasperReport) JRLoader.loadObjectFromFile("resources/hojaProcesoRev.jasper");
			} else {
				jasperReport = (JasperReport) JRLoader.loadObjectFromFile("resources/hojaProceso2.jasper");
			}
			tablaDetalleProceso = DetalleProcesoDAO.toList(DetalleProcesoDAO.readDetalleProcesoFK(conexion, sysPK));
			JRBeanCollectionDataSource itemsTabla = new JRBeanCollectionDataSource(tablaDetalleProceso);
			Map<String,Object> parameters = new HashMap<String,Object>();
			parameters.put("pNoParte", procesito.getDescripcionComponente());
			if (procesito.getRevisionComponente() != null)
				parameters.put("pRev", procesito.getRevisionComponente());
			else
				parameters.put("pRev", " ");
			parameters.put("pNombre", procesito.getNombreComponente());
			parameters.put("pCliente", procesito.getNombreCliente());
			parameters.put("pFecha", procesito.getFecha().toString());
			if (procesito.getCantidad() == 0)
				parameters.put("pCantidad", " ");
			else
				parameters.put("pCantidad", procesito.getCantidad().toString());

			if (procesito.getOrdenamiento() == 0)
				parameters.put("pOrdenamiento", " ");
			else
				parameters.put("pOrdenamiento", procesito.getOrdenamiento().toString());
			parameters.put("pNivel", procesito.getNivel().toString());
			parameters.put("pDestino", procesito.getNombreTrabajo());
			if (procesito.getDebit().equals(0) || procesito.getDebit() == null)
				parameters.put("pDebit", " ");
			else
				parameters.put("pDebit", procesito.getDebit().toString());
			DetalleComponente detalleComponente = DetalleComponenteDAO.readDetalleComponenteSuperiorFKObject(conexion, procesito.getComponenteFK());
			Componente componente = ComponenteDAO.readComponente(conexion, detalleComponente.getComponenteInferiorFK());
			parameters.put("pMaterial", componente.getNumeroParte());
			parameters.put("Parameter1", itemsTabla);
			parameters.put("pElaboro", procesito.getEmpleado());
			Date fecha = new Date();
			 SimpleDateFormat dt1 = new SimpleDateFormat("dd-MM-yyyy");
			 String fechaActual = dt1.format(fecha);
			 parameters.put("pFechaActual", fechaActual);

			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());
			JasperViewer jasperView = new JasperViewer(jasperPrint, false);
			jasperView.setVisible(true);
		} catch (JRException e) {
			Notificacion.dialogoException(e);
		}//FIN TRY CATCH
	}//FIN METODO

	public static void generaValeMovimientoInventario(Connection connection, ArrayList <DetalleCardex> listaDetalleCardex, int tipoMovimiento) {
		String titulo = "";
		String referencia = "";
		try {
			if(tipoMovimiento == DialogoMovimientoInventario.ENTRADA)
				titulo = "VALE ENTRADA";
			else if(tipoMovimiento == DialogoMovimientoInventario.SALIDA )
				titulo = "VALE SALIDA";

			for(DetalleCardex detalleCardex : listaDetalleCardex ){
				referencia = detalleCardex.getCardex(connection).getReferencia();
			}

			Map<String,Object> parameters = new HashMap<String,Object>();
			parameters.put("TipoMovimiento", tipoMovimiento );
			parameters.put("Titulo", titulo);
			parameters.put("Referencia", referencia);

			JasperReport jasperReport = (JasperReport) JRLoader.loadObjectFromFile("resources/ValeMovimientoInventario.jasper");
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JRBeanCollectionDataSource(listaDetalleCardex));
			JasperViewer jasperView = new JasperViewer(jasperPrint, false);
			jasperView.setVisible(true);
		} catch (JRException jre) {
			Notificacion.dialogoException(jre);
		}//TRY/CATH
	}//END METHOD

	public static void generaListaMateriales(Connection connection, ArrayList <DetalleComponente> listaPartePrimaria, String titulo) {
		try {
			Map<String,Object> parameters = new HashMap<String,Object>();
			parameters.put("Titulo", titulo);
			JasperReport jasperReport = (JasperReport) JRLoader.loadObjectFromFile("resources/ListaMateriales.jasper");
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JRBeanCollectionDataSource(listaPartePrimaria));
			JasperViewer jasperView = new JasperViewer(jasperPrint, false);
			jasperView.setVisible(true);
		} catch (JRException jre) {
			Notificacion.dialogoException(jre);
		}//TRY/CATH
	}//END METHOD

	public static void generaHojaViajera(Connection connection, Cliente cliente, Componente componente, ArrayList<DetalleProceso> listaProcesos) {
		JasperReport jasperReport;
		try {
			if(listaProcesos.size()>0 && listaProcesos.size()<10){
				for(int i= listaProcesos.size() + 1; i<=10; i++){
					DetalleProceso detalleProceso = new DetalleProceso();
					detalleProceso.setOperacion(i);
					listaProcesos.add(detalleProceso);
				}//FIN FOR
			}//FIN IF

			JRBeanCollectionDataSource itemsTabla = new JRBeanCollectionDataSource(listaProcesos);
			Map<String,Object> parameters = new HashMap<String,Object>();
			parameters.put("Cliente", cliente.getNombre());
			parameters.put("NoDiseño", componente.getNumeroParte());
			parameters.put("Descripcion", componente.getDescripcion());
			parameters.put("ListaProcesos", itemsTabla);
			if (componente.getTipoComponente() == TipoComponente.ENSAMBLE || componente.getTipoComponente() == TipoComponente.SUB_ENSAMBLE)
				jasperReport = (JasperReport) JRLoader.loadObjectFromFile("resources/HojaViajeraEnsamble.jasper");
			else
				jasperReport = (JasperReport) JRLoader.loadObjectFromFile("resources/HojaViajera.jasper");
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters,  new JREmptyDataSource());
			JasperViewer jasperView = new JasperViewer(jasperPrint, false);
			jasperView.setVisible(true);
		} catch (JRException jre) {
			Notificacion.dialogoException(jre);
		}//TRY/CATH
	}//END METHOD

}//FIN CLASE
