package mx.shf6.produccion.model;





import java.sql.Connection;


import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import mx.shf6.produccion.model.dao.PuestoDAO;

public class Empleado {

	//PROPIEDADES 
	private ObjectProperty<Integer> sysPK;
	private StringProperty codigo;
	private StringProperty nombre;
	private ObjectProperty<Integer> puestoFK;
	private StringProperty codigoPuesto;
	
	//VARIABLES
	
	//CONSTRUCTORES
	
	public Empleado() {
		this(0, "", "", 0, "");		
	}//FIN CONSTRUCTOR
	
	public Empleado(Integer sysPK, String codigo, String nombre, Integer puestoFK, String codigoPuesto) {
		this.sysPK = new SimpleObjectProperty<Integer>(sysPK);
		this.codigo = new SimpleStringProperty(codigo);
		this.nombre = new SimpleStringProperty(nombre);
		this.puestoFK = new SimpleObjectProperty<Integer>(puestoFK);
		this.codigoPuesto = new SimpleStringProperty(codigoPuesto);
	}//FIN CONSTRUCTOR
	
	///METODO PARA ACCESO AL "SYS_PK"
	
	//METODO SET SYSPK
	public void setSysPK(Integer sysPK) {
		this.sysPK.set(sysPK);	
	}//FIN METODO
	
	public Integer getSysPK() {
		return sysPK.get();
	}//FIN METODO
	
	public ObjectProperty<Integer> sysPKProperty() {
		return sysPK;
	}//FIN METODO 
	//FIN METODOS SYS_PK
	
	//METODO SET CODIGO
	public void setCodigo(String codigo) {
		this.codigo.set(codigo);	
	}//FIN METODO
	
	///METODO PARA ACCESO AL "CODIGO"
	public String getCodigo() {
		return codigo.get();
	}//FIN METODO
	
	public StringProperty codigoProperty() {
		return codigo;
	}//FIN METODO
	//FIN METODOS CODIGO

	public void setNombre(String nombre) {
		this.nombre.set(nombre);	
	}//FIN METODO
	
	///METODO PARA ACCESO AL "NOMBRE"
	public String getNombre() {
		return nombre.get();
	}//FIN METODO
		
	public StringProperty nombreProperty() {
		return nombre;
	}//FIN METODO
	//FIN METODOS NOMBRE
	
	public void setPuestoFK(Integer puestoFK) {
		this.puestoFK.set(puestoFK);	
	}//FIN METODO
	
	///METODO PARA ACCESO AL "PUESTO"
	public int getPuestoFK() {
		return puestoFK.get();
	}//FIN METODO
	
	//METODO PROPERTY PUESTO
	public ObjectProperty<Integer> puestoFKProperty() {
		return puestoFK;
	}//FIN METODO
	
	
	//METODO SET CODIGO PUESTO
	public void setCodigoPuesto(String codigoPuesto) {
		this.codigoPuesto.set(codigoPuesto);
	}//FIN METODO SET
	
	//METODO GET CODIGO PUESTO
	public String getCodigoPuesto() {
		return codigoPuesto.get();
	} //FIN METODO
	
	public StringProperty codigoPuesto() {
		return this.codigoPuesto;
	}
	
	
	public Puesto getPuesto(Connection conection) {
		return PuestoDAO.readPuesto(conection, this.getPuestoFK());
	}//FIN METODO
	//FIN METODOS PUESTO

}//FIN CLASE
