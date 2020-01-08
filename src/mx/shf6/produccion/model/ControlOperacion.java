package mx.shf6.produccion.model;

import java.sql.Date;
import java.sql.Timestamp;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ControlOperacion {
	//PROPIEDADES
	private ObjectProperty<Integer> sysPK;
	private ObjectProperty<Integer> cantidad;
	private ObjectProperty<Timestamp> horaFechaInicio;
	private ObjectProperty<Date> fechaEstimada;
	private ObjectProperty<Timestamp> horaFechaFinal;
	private ObjectProperty<Integer> centroTrabajoFK;
	@SuppressWarnings("unused")
	private StringProperty nombreCentroTrabajo;
	private ObjectProperty<Integer> codigoParoFK;
	@SuppressWarnings("unused")
	private StringProperty descripcionParo;
	private ObjectProperty<Integer> componenteFK;
	@SuppressWarnings("unused")
	private StringProperty numeroParte;
	private ObjectProperty<Integer> detalleProcesoFK;
	@SuppressWarnings("unused")
	private StringProperty descripcionDetalleProceso;
	private ObjectProperty<Integer> detalleOrdenProduccionFK;
	private StringProperty numeroSerie;
	private StringProperty numeroLote;
	private ObjectProperty<Integer> status;
	private ObjectProperty<Integer> nivel;
	
	//CONSTRUCTOR VACIO
	public ControlOperacion() {
		this(0, 0, new Timestamp(0), new Date(System.currentTimeMillis()), new Timestamp(0), 0, 0, 0, 0, 0, "", "", 0, 0);
	}//FIN CONSTRUCTOR
	
	//CONSTRUCTOR LLENO
	public ControlOperacion(Integer sysPK, Integer cantidad, Timestamp horaFechaInicio, Date horaFechaEstimada, Timestamp horaFechaFinal, Integer centroTrabajoFK,Integer codigoParoFK, Integer componenteFK, Integer detalleProcesoFK, Integer detalleOrdenProduccionFK, String numeroSerie, String numeroLote, Integer status, Integer nivel) {
		this.sysPK = new SimpleObjectProperty<Integer>(sysPK);
		this.cantidad = new SimpleObjectProperty<Integer>(cantidad);
		this.horaFechaInicio = new SimpleObjectProperty<Timestamp>(horaFechaInicio);
		this.fechaEstimada = new SimpleObjectProperty<Date>(horaFechaEstimada);
		this.horaFechaFinal = new SimpleObjectProperty<Timestamp>(horaFechaFinal);
		this.centroTrabajoFK = new SimpleObjectProperty<Integer>(centroTrabajoFK);
		this.codigoParoFK = new SimpleObjectProperty<Integer>(codigoParoFK);
		this.componenteFK = new SimpleObjectProperty<Integer>(componenteFK);
		this.detalleProcesoFK = new SimpleObjectProperty<Integer>(detalleProcesoFK);
		this.detalleOrdenProduccionFK = new SimpleObjectProperty<Integer>(detalleOrdenProduccionFK);
		this.numeroSerie = new SimpleStringProperty(numeroSerie);
		this.numeroLote = new SimpleStringProperty(numeroLote);
		this.status = new SimpleObjectProperty<Integer>(status);
		this.nivel = new SimpleObjectProperty<Integer>(nivel);
	}//FIN CONSTRUCTOR
	
	//METODOS DE ACCESO A SYSPK
	public void setSysPK(Integer sysPK) {
		this.sysPK.set(sysPK);
	}//FIN METODO
	
	public Integer getSysPK() {
		return sysPK.get();
	}//FIN METODO
	
	public ObjectProperty<Integer> sysPKProperty() {
		return sysPK;
	}//FIN METODO
	//FIN METODOS DE ACCESO A SYSPK
	
	//METODOS DE ACCESO A CANTIDAD
	public void setCantidad(Integer cantidad) {
		this.cantidad.set(cantidad);
	}//FIN METODO
	
	public Integer getCantidad() {
		return cantidad.get();
	}//FIN METODO
	
	public ObjectProperty<Integer> cantidadProperty() {
		return cantidad;
	}//FIN METODO
	//FIN METODOS DE ACCESO A CANTIDAD
	
	//METODOS DE ACCESO A HORAFECHAINICIO
	public void setHoraFechaInicio(Timestamp horaFechaInicio) {
		this.horaFechaInicio.set(horaFechaInicio);
	}//FIN METODO
	
	public Timestamp getHoraFechaInicio() {
		return horaFechaInicio.get();
	}//FIN METODO
	
	public ObjectProperty<Timestamp> horaFechaInicioProperty() {
		return horaFechaInicio;
	}//FIN METODO
	//FIN METODOS DE ACCESO A HORAFECHAINICIO
	
	//METODOS DE ACCESO A HORAFECHAESTIMADAq
	public void setFechaEstimada(Date fechaEstimada) {
		this.fechaEstimada.set(fechaEstimada);
	}//FIN METODO
	
	public Date getFechaEstimada() {
		return fechaEstimada.get();
	}//FIN METODO
	
	public ObjectProperty<Date> fechaEstimadaProperty() {
		return fechaEstimada;
	}//FIN METODO
	//FIN METODOS DE ACCESO A HORAFECHAESTIMADA	
	
	//METODOS DE ACCESO A HORAFECHAFINAL
	public void setHoraFechaFinal(Timestamp horaFechaFinal) {
		this.horaFechaFinal.set(horaFechaFinal);
	}//FIN METODO
	
	public Timestamp getHoraFechaFinal() {
		return horaFechaFinal.get();
	}//FIN METODO
	
	public ObjectProperty<Timestamp> horaFechaFinalProperty() {
		return horaFechaFinal;
	}//FIN METODO
	//FIN METODOS DE ACCESO A HORAFECHAFINAL
	
	//METODOS DE ACCESO A CENTROTRABAJOFK
	public void setCentroTrabajoFK(Integer centroTrabajoFK) {
		this.centroTrabajoFK.set(centroTrabajoFK);
	}//FIN METODO
	
	public Integer getCentroTrabajo() {
		return centroTrabajoFK.get();
	}//FIN METODO
	
	public ObjectProperty<Integer> centroTrabajoFKProperty() {
		return centroTrabajoFK;
	}//FIN METODO
	//FIN METODOS DE ACCESO A CENTROTRABAJOFK
	
	//METODOS DE ACCESO A CODIGOPAROFK
	public void setCodigoParo(Integer codigoParoFK) {
		this.codigoParoFK.set(codigoParoFK);
	}//FIN METODO
	
	public Integer getCodigoParoFK() {
		return codigoParoFK.get();
	}//FIN METODO
	
	public ObjectProperty<Integer> codigoParoFK() {
		return codigoParoFK;
	}//FIN METODO
	//FIN METODOS DE ACCESO A CODIGOPAROFK
	
	//METODOS DE ACCESO A COMPONENTEFK
	public void setComponenteFK(Integer componenteFK) {
		this.componenteFK.set(componenteFK);
	}//FIN METODO
	
	public Integer getComponenteFK() {
		return componenteFK.get();
	}//FIN METODO
	
	public ObjectProperty<Integer> componenteFKProperty() {
		return componenteFK;
	}//FIN METODO
	//FIN METODOS DE ACCESO A COMPONENTEFK
	
	//METODOS DE ACCESO A DETALLEPROCESOFK
	public void setDetalleProcesoFK(Integer detalleProcesoFK) {
		this.detalleProcesoFK.set(detalleProcesoFK);
	}//FIN METODO
	
	public Integer getDetalleProcesoFK() {
		return detalleProcesoFK.get();
	}//FIN METODO
	
	public ObjectProperty<Integer> detalleProcesoFKProperty() {
		return detalleProcesoFK;
	}//FIN METODO
	//FIN METODOS DE ACCESO A DETALLEPROCESOFK
	
	//METODOS DE ACCESO A DETALLELOTEPRODUCCIONFK
	public void setDetalleOrdenProduccionFK(Integer detalleOrdenProduccion) {
		this.detalleOrdenProduccionFK.set(detalleOrdenProduccion);
	}//FIN METODO
	
	public Integer getDetalleOrdenProduccionFK() {
		return detalleOrdenProduccionFK.get();
	}//FIN METODO
	
	public ObjectProperty<Integer> detalleOrdenProduccionFKProperty() {
		return detalleOrdenProduccionFK;
	}//FIN METODO
	//FIN METODOS DE ACCESO A DETALLELOTEPRODUCCIONFK
	
	//METODOS DE ACCESO A NUMEROSERIE
	public void setNumeroSerie(String numeroSerie) {
		this.numeroSerie.set(numeroSerie);
	}//FIN METODO
	
	public String getNumeroSerie() {
		return numeroSerie.get();
	}//FIN METODO
	
	public StringProperty numeroSerieProperty() {
		return numeroSerie;
	}//FIN METODO
	//FIN METODOS DE ACCESO A NUMEROSERIE
	
	//METODOS DE ACCESO A NUMEROLOTE
	public void setNumeroLote(String numeroLote) {
		this.numeroLote.set(numeroLote);
	}//FIN METODO
	
	public String getNumeroLote() {
		return numeroLote.get();
	}//FIN METODO
	
	public StringProperty numeroLoteProperty() {
		return numeroLote;
	}//FIN METODO
	//FIN METODOS DE ACCESO A NUMEROLOTE
	
	//METODOS DE ACCESO A STATUS
	public void setStatus(Integer status) {
		this.status.set(status);
	}//FIN METODO
		
	public Integer getStatus() {
		return status.get();
	}//FIN METODO
	
	public ObjectProperty<Integer> statusProperty() {
		return status;
	}//FIN METODO
		
	public StringProperty detalleStatusProperty() {
		switch(this.getStatus()) {
			case 0:
				return new SimpleStringProperty("Pendiente");
			case 1:
				return new SimpleStringProperty("En proceso");
			case 2: 
				return new SimpleStringProperty("Paro");
			case 3:
				return new SimpleStringProperty("Terminado");
		}//FIN SWITCH
		return new SimpleStringProperty();
	}//FIN METODO
	
	//METODOS PARA ACCEDER A NIVEL
	public void setNivel(int nivel) {
		this.nivel.set(nivel);
	}//FIN METODO
	
	public int getNivel() {
		return this.nivel.get();
	}//FIN METODO
	
	public ObjectProperty<Integer> nivelProperty(){
		return this.nivel;
	}//FIN METODO
}//FIN CLASE
