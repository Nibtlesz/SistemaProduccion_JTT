package mx.shf6.produccion.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class HojaViajera {

	//PROPIEDADES
	private ObjectProperty<Integer> sysPK;
	private ObjectProperty<Integer> cantidad;
	private ObjectProperty<Integer> codigoParoFK;
	private StringProperty descripcionParo;
	private ObjectProperty<Integer> componenteFK;
	private StringProperty numeroParte;
	private ObjectProperty<Integer> ordenProduccionFK;
	private StringProperty numeroLote;
	private ObjectProperty<Integer> status;
	
	//CONSTANTES
	public static final int PENDIENTE = 0;
	public static final int EN_PROCESO = 1;
	public static final int PARO = 2;
	public static final int TERMINADO = 3;
	
	//CONSTRUCTOR VACIO
	public HojaViajera() {
		this(0, 0, 0, "", 0, "", 0, "", 0);
	}//FIN CONSTRUCTOR
	
	//CONSTRUCTOR LLENO
	public HojaViajera(Integer sysPK, Integer cantidad, Integer codigoParoFK, String descripcionParo, Integer componenteFK, String numeroParte, Integer ordenProduccionFK, String numeroLote, Integer status) {
		this.sysPK = new SimpleObjectProperty<Integer>(sysPK);
		this.cantidad = new SimpleObjectProperty<Integer>(cantidad);
		this.codigoParoFK = new SimpleObjectProperty<Integer>(codigoParoFK);
		this.descripcionParo = new SimpleStringProperty(descripcionParo);
		this.componenteFK = new SimpleObjectProperty<Integer>(componenteFK);
		this.numeroParte = new SimpleStringProperty(numeroParte);
		this.ordenProduccionFK = new SimpleObjectProperty<Integer>(ordenProduccionFK);
		this.numeroLote = new SimpleStringProperty(numeroLote);
		this.status = new SimpleObjectProperty<Integer>(status);
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
					
	//METODOS DE ACCESO A CODIGOPAROFK
	public void setCodigoParoFK(Integer codigoParoFK) {
		this.codigoParoFK.set(codigoParoFK);
	}//FIN METODO
	
	public Integer getCodigoParoFK() {
		return codigoParoFK.get();
	}//FIN METODO
	
	public ObjectProperty<Integer> codigoParoFK() {
		return codigoParoFK;
	}//FIN METODO
	//FIN METODOS DE ACCESO A CODIGOPAROFK
	
	public void setDescripcionParo(String descripcionParo) {
		this.descripcionParo.set(descripcionParo);
	}//FIN METODO
	
	public String getDescripcionParo() {
		return this.descripcionParo.get();
	}//FIN METODO
	
	public StringProperty descripcionParoProperty() {
		return this.descripcionParo;
	}
	
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
	public void setNumeroParte(String numeroParte) {
		this.numeroParte.set(numeroParte);
	}//FIN METODO
	
	public String getDetalleProcesoFK() {
		return this.numeroParte.get();
	}//FIN METODO
	
	public StringProperty detalleProcesoFKProperty() {
		return this.numeroParte;
	}//FIN METODO
	//FIN METODOS DE ACCESO A DETALLEPROCESOFK
	
	//METODOS DE ACCESO A DETALLELOTEPRODUCCIONFK
	public void setOrdenProduccionFK(Integer ordenProduccion) {
		this.ordenProduccionFK.set(ordenProduccion);
	}//FIN METODO
	
	public Integer getOrdenProduccionFK() {
		return ordenProduccionFK.get();
	}//FIN METODO
	
	public ObjectProperty<Integer> ordenProduccionFKProperty() {
		return ordenProduccionFK;
	}//FIN METODO
	//FIN METODOS DE ACCESO A DETALLELOTEPRODUCCIONFK
	
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

}//FIN CLASE
