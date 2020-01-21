package mx.shf6.produccion.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Componente {

	//PROPIEDADES
	private ObjectProperty<Integer> sysPK;
	private StringProperty numeroParte;
	private StringProperty descripcion;

	//CONSTRUCTOR VACIO
	public Componente() {
		this(0, "", "");
	}//FIN CONSTRUCTOR

	public Componente(int sysPK, String numeroParte, String descripcion) {
		this.sysPK = new SimpleObjectProperty<Integer>(sysPK);
		this.numeroParte = new SimpleStringProperty(numeroParte);
		this.descripcion = new SimpleStringProperty(descripcion);
	}//FIN CONSTRUCTOR

	public void setSysPK(int sysPK) {
		this.sysPK.set(sysPK);
	}//FIN METODO

	public int getSysPK() {
		return this.sysPK.get();
	}//FIN METODO

	public ObjectProperty<Integer> sysPKProperty() {
		return this.sysPK;
	}//FIN METODO

	public void setNumeroParte(String numeroParte) {
		this.numeroParte.set(numeroParte);
	}//FIN METODO

	public String getNumeroParte() {
		return this.numeroParte.get();
	}//FIN METODO

	public StringProperty numeroParteProperty() {
		return this.numeroParte;
	}//FIN METODO

	public void setDescripcion(String descripcion) {
		this.descripcion.set(descripcion);
	}//FIN METODO

	public String getDescripcion() {
		return this.descripcion.get();
	}//FIN METODO

	public StringProperty descripcionProperty() {
		return this.descripcion;
	}//FIN METODO
}//FIN CLASE