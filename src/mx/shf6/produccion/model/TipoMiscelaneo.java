package mx.shf6.produccion.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TipoMiscelaneo {
	
	//PROPIEDADES
	private ObjectProperty<Integer> sysPK;
	private StringProperty codigo;
	private StringProperty descripcion;
	private StringProperty status;
	
	//CONSTRUCTOR VACIO
	public TipoMiscelaneo() {
		this(0, "", "", "");
	}//FIN CONSTRUCTOR

	//CONSTRUCTOR CON PARAMETROS
	public TipoMiscelaneo(Integer sysPK, String codigo, String descripcion, String status) {
		this.sysPK = new SimpleObjectProperty<Integer>(sysPK);
		this.codigo = new SimpleStringProperty(codigo);
		this.descripcion = new SimpleStringProperty(descripcion);
		this.status = new SimpleStringProperty(status);
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
	
	//METODOS DE ACCESO A "CODIGO"
	public void setCodigo(String codigo) {
		this.codigo.set(codigo);
	}//FIN METODO
	
	public String getCodigo() {
		return this.codigo.get();
	}//FIN METODO
	
	public StringProperty codigoProperty(){
		return this.codigo;
	}//FIN METODO
	//FIN METODOS "CODIGO"
	
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
	
	public void setStatus(int status) {
		this.status.set(Status.toString(status));
	}//FIN METODO
	
	public String getStatus() {
		return this.status.get();
	}//FIN METODO
	
	public StringProperty statusProperty() {
		return this.status;
	}//FIN METODO
	
	public int getStatusFK() {
		return Status.toInt(this.getStatus()); 
	}//FIN METODO
}//FIN CLASE