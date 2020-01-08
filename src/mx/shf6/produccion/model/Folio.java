package mx.shf6.produccion.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Folio {
	
	// PROPIEDADES
	private ObjectProperty<Integer> sysPK;
	private StringProperty folio;
	private ObjectProperty<Integer> serie;
	private StringProperty descripcion;
	
	//CONSTRUCTOR VACIO
	public Folio() {
		this(-1, "", -1, "");
	}//FIN CONSTRUCTOR

	//CONSTRUCTOR CON PARAMETROS
	public Folio(Integer sysPK, String folio, int serie, String descripcion) {
		this.sysPK = new SimpleObjectProperty<Integer>(sysPK);
		this.folio = new SimpleStringProperty(folio);		
		this.serie = new SimpleObjectProperty<Integer>(serie);
		this.descripcion = new SimpleStringProperty(descripcion);
	}//FIN CONSTRUCTOR
	
	//METODOS PARA ACCESO A "SYSPK"
	public void setSysPK(Integer sysPK) {
		this.sysPK.set(sysPK);
	}//FIN METODO
		
	public Integer getSysPK() {
		return this.sysPK.get();
	}//FIN METODO
		
	public ObjectProperty<Integer> sysPKProperty() {
		return this.sysPK;
	}//FIN METODO
	//FIN METODOS "SYSPK"
	
	//METODOS DE ACCESO A "FOLIO"
	public void setFolio(String folio) {
		this.folio.set(folio);
	}//FIN METODO
	
	public String getFolio() {
		return this.folio.get();
	}//FIN METODO
	
	public StringProperty folioProperty(){
		return this.folio;
	}//FIN METODO
	//FIN METODOS "FOLIO"
	
	//METODOS DE ACCESO A "SERIE"
	public void setSerie(int serie) {
		this.serie.set(serie);
	}//FIN METODO
	
	public int getSerie() {
		return this.serie.get();
	}//FIN METODO
	
	public ObjectProperty<Integer> serieProperty(){
		return this.serie;
	}//FIN METODO
	//FIN METODOS "SERIE"
	
	//METODOS DE ACCESO A "DESCRIPCION"
	public void setDescripcion (String descripcion) {
		this.descripcion.set(descripcion);
	}//FIN METODO
	
	public String getDescripcion() {
		return this.descripcion.get();
	}//FIN METODO
	
	public StringProperty descripcionProperty() {
		return this.descripcion;
	}//FIN METODO
	//FIN METODOS DE ACCESO A "DESCRIPCION"
}//FIN CLASE