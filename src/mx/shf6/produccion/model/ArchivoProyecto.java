package mx.shf6.produccion.model;

import java.sql.Connection;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import mx.shf6.produccion.model.dao.ProyectoDAO;

public class ArchivoProyecto {

	//PROPIEDADES
	private ObjectProperty<Integer> sysPK;
	private StringProperty codigo;
	private StringProperty descripcion;
	public ObjectProperty<Integer> proyectoFK;
	
	//CONTRUCTOR SIN PARAMETROS
	public ArchivoProyecto() {
		this(-1,"","",0);
	}//FIN CONSTUCTOR
	
	//CONTRUCTOR CON PARAMETROS
	public ArchivoProyecto(Integer sysPK, String codigo, String descripcion,  int proyectoFK) {
		this.sysPK = new SimpleObjectProperty<Integer>(sysPK);
		this.codigo = new SimpleStringProperty(codigo);
		this.descripcion = new SimpleStringProperty(descripcion);
		this.proyectoFK = new SimpleObjectProperty<Integer>(proyectoFK);
		
	}//FIN CONSTRUCTOR
	
	//METODOS DE ACCESO A "SYSPK"
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
	
	public StringProperty codigoProperty() {
		return this.codigo;
	}//FIN METODO
	//FIN METODOS "CODIGO"
	
	//METODOS DE ACCESO A "DESCRIPCION"
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
			
	//METODOS PARA ACCESO A "PROYECTOFK"
	public void setProyectoFK(Integer proyectoFK) {
		this.proyectoFK.set(proyectoFK);
	}//FIN METODO
	
	public Integer getProyectoFK() {
		return this.proyectoFK.get();
	}//FIN METODO
		
	public Proyecto getProyecto(Connection connection) {
		return ProyectoDAO.readProyecto(connection, this.getProyectoFK());
	}//FIN METODO
	//FIN METODOS "PROYECTOFK"
		
}//FIN METODO

