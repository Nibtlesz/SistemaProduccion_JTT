package mx.shf6.produccion.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Rol {
	
	//PROPIEDADES
	private ObjectProperty<Integer> sysPk;
	private StringProperty codigoItem;
	private StringProperty descripcion;
	private BooleanProperty seleccionado;
	
	//CONSTRUCTOR SIN PARAMETROS
	public Rol() {
		this(0,"","", false);
	}//FIN CONSTRUCTOR
	
	//CONSTRUCTOR CON PARAMETROS
	public Rol(Integer sysPk, String codigoItem, String descripcion, boolean seleccionado) {
		this.sysPk =  new SimpleObjectProperty<Integer>(sysPk);
		this.codigoItem = new SimpleStringProperty(codigoItem);
		this.descripcion = new SimpleStringProperty(descripcion);
		this.seleccionado = new SimpleBooleanProperty(seleccionado);
	}//FIN CONSTRUCTOR
	
	//METODOS PARA ACCESO A "SYSPK"
	public void setSysPk(Integer sysPk) {
		this.sysPk.set(sysPk);
	}//FIN METODO
	
	public Integer getSysPk() {
		return this.sysPk.get();
	}//FIN METODO
	
	public ObjectProperty<Integer> sysPkProperty() {
		return this.sysPk;
	}//FIN METODO
	//FIN METODOS "SYSPK"
	
	//METODOS PARA ACCESO A "CODIGO ITEM"
	public void setCodigoItem(String codigoItem) {
		this.codigoItem.set(codigoItem);
	}//FIN METODO
	
	public String getCodigoItem() {
		return this.codigoItem.get();
	}//FIN METODO
	
	public StringProperty codigoItemProperty() {
		return this.codigoItem;
	}//FIN METODO
	//FIN METODOS "CODIGO ITEM"
	
	//METODOS DE ACCESO PARA "DESCRIPCION"
	public void setDescripcion(String descripcion) {
		this.descripcion.set(descripcion);
	}//FIN METODO
	
	public String getDescripcion() {
		return this.descripcion.get();
	}//FIN METODO
	
	public StringProperty descripcionProperty() {
		return this.descripcion;
	}//FIN METODO
	//FIN METODOS "DESCRIPCION"
	
	public String showInformacion() {
		String informacionRol = "INFORMACIÓN DE ROL \nSysPK: " + this.getSysPk() + "\n"
				+ "Código Item: " + this.getCodigoItem() + "\n"
						+ "Descripción: " + this.getDescripcion();
		return informacionRol;
	}//FIN METODO
	
	//METODOS PARA ACCEDER A SELECCIONADO
	public void setSeleccionado(boolean seleccionado) {
		this.seleccionado.set(seleccionado);
	}//FIN METODO
	
	public Boolean getSeleccionado() {
		return this.seleccionado.get();
	}//FIN METODO
	
	public BooleanProperty seleccionadoProperty() {
		return this.seleccionado;
	}//FIN METODO
}//FIN CLASE
