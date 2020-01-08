package mx.shf6.produccion.model;


import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

	public class Puesto {

	//PROPIEDADES 
	private ObjectProperty<Integer> sysPK;
	private StringProperty codigo;
	private StringProperty descripcion;
	
	//VARIABLES
	
	//CONSTRUCTORES
	public Puesto() {
		this(0, "", "");
	}//FIN CONSTRUCTOR
	
	//CONTRUCTOR LLENO
	public Puesto(Integer sysPK, String codigo, String descripcion) {
		this.sysPK = new SimpleObjectProperty<Integer>(sysPK);
		this.codigo = new SimpleStringProperty(codigo);
		this.descripcion = new SimpleStringProperty(descripcion);
	}//FIN CONSTRUCTOR

	//METODOS
	//METODO SET SYSPK
	public void setSysPK(Integer sysPK) {
		this.sysPK.set(sysPK);	
	}//FIN METODO
	
	//METODO GET SYSPK
	public int getSysPK() {
		return sysPK.get();
	}//FIN METODO
	
	//METODO PROPERTY SYSPK
	public ObjectProperty<Integer> sysPKProperty() {
		return sysPK;
	}//FIN METODO
	
	//METODO SET CODIGO
	public void setCodigo(String codigo) {
		this.codigo.set(codigo);	
	}//FIN METODO
	
	//METODO GET CODIGO
	public String getCodigo() {
		return codigo.get();
	}//FIN METODO
	
	//METODO PROPERTY CODIGO
	public StringProperty codigoProperty() {
		return codigo;
	}//FIN METODO
	
	//METODO SET DESCRIPCION
	public void setDescripcion(String descripcion) {
		this.descripcion.set(descripcion);	
	}//FIN METODO
	
	//METODO GET DESCRIPCION
	public String getDescripcion() {
		return descripcion.get();
	}//FIN METODO

	//METODO PROPERTY DESCRIPCION
	public StringProperty descripcionProperty() {
		return descripcion;
	}//FIN METODO
}//FIN CLASE
