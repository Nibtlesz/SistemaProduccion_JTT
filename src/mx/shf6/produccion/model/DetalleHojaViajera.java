package mx.shf6.produccion.model;

import java.sql.Connection;
import java.sql.Timestamp;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import mx.shf6.produccion.model.dao.DetalleProcesoDAO;

public class DetalleHojaViajera {
	
	//PROPIEDADES
	private ObjectProperty<Integer> sysPK;
	private ObjectProperty<Integer> hojaViajeraFK;
	private ObjectProperty<Integer> detalleProcesoFK;
	private ObjectProperty<Integer> detalleProcesoOperacion;
	private StringProperty detalleProcesoDescripcion;
	private ObjectProperty<Integer> cantidadEnProceso;
	private ObjectProperty<Integer> cantidadTerminado;
	private ObjectProperty<Timestamp> fechaHoraInicio;
	private ObjectProperty<Timestamp> fechaHoraFinal;

	//CONSTRUCTORES
	public DetalleHojaViajera() {
		this(0, 0, 0, 0, "", 0, 0, new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()));
	}//FIN CONSTRUCTOR
	
	public DetalleHojaViajera(Integer sysPK, Integer hojaViajeraFK, Integer detalleProcesoFK, Integer detalleProcesoOperacion, String detalleProcesoDescripcion, Integer cantidadEnProceso, Integer cantidadTerminado, Timestamp fechaHoraInicio, Timestamp fechaHoraFinal) {
		this.sysPK = new SimpleObjectProperty<Integer>(sysPK);
		this.hojaViajeraFK = new SimpleObjectProperty<Integer>(hojaViajeraFK);
		this.detalleProcesoFK = new SimpleObjectProperty<Integer>(detalleProcesoFK);
		this.detalleProcesoOperacion = new SimpleObjectProperty<Integer>(detalleProcesoOperacion);
		this.detalleProcesoDescripcion = new SimpleStringProperty(detalleProcesoDescripcion);
		this.cantidadEnProceso = new SimpleObjectProperty<Integer>(cantidadEnProceso);
		this.cantidadTerminado = new SimpleObjectProperty<Integer>(cantidadTerminado);
		this.fechaHoraInicio = new SimpleObjectProperty<Timestamp>(fechaHoraInicio);
		this.fechaHoraFinal = new SimpleObjectProperty<Timestamp>(fechaHoraFinal);
	}//FIN CONSTRUCTOR
	
	//METODOS
	public void setSysPK(Integer sysPK) {
		this.sysPK.set(sysPK);
	}//FIN METODO
	
	public Integer getSysPK() {
		return this.sysPK.get();
	}//FIN METODO
	
	public ObjectProperty<Integer> sysPKProperty() {
		return this.sysPK;
	}//FIN METODO
	
	public void setHojaViajeraFK(Integer hojaViajeraFK) {
		this.hojaViajeraFK.set(hojaViajeraFK);
	}//FIN METODO
	
	public Integer getHojaViajeraFK() {
		return this.hojaViajeraFK.get();
	}//FIN METODO
	
	public ObjectProperty<Integer> hojaViajeraFKProperty() {
		return this.hojaViajeraFK;
	}//FIN METODO
	
	public void setDetalleProcesoFK(Integer detalleProcesoFK) {
		this.detalleProcesoFK.set(detalleProcesoFK);
	}//FIN METODO
	
	public Integer getDetalleProcesoFK() {
		return this.detalleProcesoFK.get();
	}//FIN METODO
	
	public ObjectProperty<Integer> detalleProcesoFKProperty() {
		return this.detalleProcesoFK;
	}//FIN METODO
	
	public DetalleProceso getDetalleProceso(Connection connection) {
		return DetalleProcesoDAO.readDetalleProceso(connection, this.getDetalleProcesoFK());
	}//FIN METODO
	
	public void setDetalleProcesoOperacion(Integer detalleProcesoOperacion) {
		this.detalleProcesoOperacion.set(detalleProcesoOperacion);
	}//FIN METODO
	
	public Integer getDetalleProcesoOperacion() {
		return this.detalleProcesoOperacion.get();
	}//FIN METODO
	
	public ObjectProperty<Integer> detalleProcesoOperacionProperty() {
		return this.detalleProcesoOperacion;
	}//FIN METODO
	
	public void setDetalleProcesoDescripcion(String detalleProcesoDescripcion) {
		this.detalleProcesoDescripcion.set(detalleProcesoDescripcion);
	}//FIN METODO
	
	public String getDetalleProcesoDescripcion() {
		return this.detalleProcesoDescripcion.get();
	}//FIN METODO
	
	public StringProperty detalleProcesoDescripcionProperty() {
		return this.detalleProcesoDescripcion;
	}//FIN METODO
	
	public void setCantidadEnProceso(Integer cantidadEnProceso) {
		this.cantidadEnProceso.set(cantidadEnProceso);
	}//FIN METODO
	
	public Integer getCantidadEnProceso() {
		return this.cantidadEnProceso.get();
	}//FIN METODO
	
	public ObjectProperty<Integer> cantidadEnProcesoProperty() {
		return this.cantidadEnProceso;
	}//FIN METODO
	
	public void setCantidadTerminado(Integer cantidadTerminado) {
		this.cantidadTerminado.set(cantidadTerminado);
	}//FIN METODO
	
	public Integer getCantidadTermiando() {
		return this.cantidadTerminado.get();
	}//FIN METODO
	
	public ObjectProperty<Integer> cantidadTerminadoProperty() {
		return this.cantidadTerminado;
	}//FIN METODO
	
	public void setFechaHoraInicio(Timestamp fechaHoraInicio) {
		this.fechaHoraInicio.set(fechaHoraInicio);
	}//FIN METODO
	
	public Timestamp getFechaHoraInicio() {
		return this.fechaHoraInicio.get();
	}//FIN METODO
	
	public ObjectProperty<Timestamp> fechaHoraInicioProperty() {
		return this.fechaHoraInicio;
	}//FIN METODO
	
	public void setFechaHoraFinal(Timestamp fechaHoraFinal) {
		this.fechaHoraFinal.set(fechaHoraFinal);
	}//FIN METODO
	
	public Timestamp getFechaHoraFinal() {
		return this.fechaHoraFinal.get();
	}//FIN METODO
	
	public ObjectProperty<Timestamp> fechaHoraFinalProperty() {
		return this.fechaHoraFinal;
	}//FIN METODO
	
}//FIN CLASE
