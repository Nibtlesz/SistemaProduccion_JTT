package mx.shf6.produccion.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class DetalleOrdenProduccion {
	
	//PROPIEDADES
	private ObjectProperty<Integer> sysPK;
	private StringProperty numeroSerie;
	private ObjectProperty<Integer> status; 
	private ObjectProperty<Integer> ordenProduccionFK;
	private StringProperty loteOrdenProduccionFK;
	
	//CONSTANTES
	public static final int PENDIENTE = 0;
	public static final int EN_PROCESO = 1;
	public static final int PARO = 2;
	public static final int TERMINADO = 3;
	
	//CONSTRUCTOR VACIO
	public DetalleOrdenProduccion() {
		this(0,"",0,0,"");
	}//FIN CONSTRUCTOR
	
	//CONSTRUCTOR LLENO
	public DetalleOrdenProduccion(Integer sysPK, String numeroSerie, Integer status, Integer ordenProduccionFK, String loteOrdenProduccionFK) {
		this.sysPK = new SimpleObjectProperty<Integer>(sysPK);
		this.numeroSerie = new SimpleStringProperty(numeroSerie);
		this.status = new SimpleObjectProperty<Integer>(status);
		this.ordenProduccionFK = new SimpleObjectProperty<Integer>(ordenProduccionFK);
		this.loteOrdenProduccionFK =  new SimpleStringProperty(loteOrdenProduccionFK);
	}//FIN CONSTRUCTO
	
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
	//FIN METODOS DE ACCESO A NUMERO DE SERIE
	
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
	
	//public String getStringStatus() {
	//FIN METODOS DE ACCESO A STATUS
	
	//METODOS DE ACCESO A ORDENPRODUCCIONFK
	public void setOrdenProduccionFK(Integer ordenProduccionFK) {
		this.ordenProduccionFK.set(ordenProduccionFK);
	}//FIN METODO
	
	public Integer getOrdenProduccionFK() {
		return ordenProduccionFK.get();
	}//FIN METODO
	
	public ObjectProperty<Integer> ordenProduccionFK() {
		return ordenProduccionFK;
	}//FIN METODO
	//FIN METODOS DE ACCESO A ORDENPRODUCCIONFK
	
	//METODOS DE ACCESO A LOTEORDENPRODUCCIONFK 
	public void setLoteOrdenProduccionFK(String loteOrdenProduccionFK) {
		this.loteOrdenProduccionFK.set(loteOrdenProduccionFK);
	}//FIN METODO
	
	public String getLoteOrdenProduccionFK() {
		return loteOrdenProduccionFK.get();
	}//FIN METODO
	
	public StringProperty loteOrdenProduccionFKProperty() {
		return loteOrdenProduccionFK;
	}//FIN METODO
	//FIN METODOS DE ACCESO A LOTEORDENPRODUCCIONFK 
}//FIN CLASE
