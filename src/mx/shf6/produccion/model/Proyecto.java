package mx.shf6.produccion.model;

import java.sql.Connection;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import mx.shf6.produccion.model.dao.ClienteDAO;
import mx.shf6.produccion.model.dao.ComponenteDAO;



public class Proyecto {
	
	//PROPIEDADES
	private ObjectProperty<Integer> sysPK;
	private StringProperty codigo;
	private StringProperty descripcion;
	private StringProperty carpeta;
	private StringProperty especificacionTecnica;
	private ObjectProperty<Double> costoIndirecto;
	private ObjectProperty<Double> precio;
	private ObjectProperty<Double> costoDirecto;
	public ObjectProperty<Integer> clienteFK;
	private ObjectProperty<Integer> componenteFK;
	
	//CONTRUCTOR SIN PARAMETROS
	public Proyecto() {
		this(-1,"","","","",0.0,0.0,0.0,0,0);
	}//FIN CONSTUCTOR
	
	//CONTRUCTOR CON PARAMETROS
	public Proyecto(Integer sysPK, String codigo, String descripcion, String carpeta, String especificacionTecnica, Double costoDirecto, Double costoIndirecto, Double precio,  int clienteFK, int componenteFK) {
		this.sysPK = new SimpleObjectProperty<Integer>(sysPK);
		this.codigo = new SimpleStringProperty(codigo);
		this.descripcion = new SimpleStringProperty(descripcion);
		this.carpeta = new SimpleStringProperty(carpeta);
		this.especificacionTecnica = new SimpleStringProperty(especificacionTecnica);
		this.costoDirecto = new SimpleObjectProperty<Double>(costoDirecto);
		this.costoIndirecto = new SimpleObjectProperty<Double>(costoIndirecto);
		this.precio = new SimpleObjectProperty<Double>(precio);
		this.clienteFK = new SimpleObjectProperty<Integer>(clienteFK);
		this.componenteFK = new SimpleObjectProperty<Integer>(componenteFK);
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
	
	//METODOS DE ACCESO A "CARPETA"
	public void setCarpeta(String carpeta) {
		this.carpeta.set(carpeta);
	}//FIN METODO
	
	public String getCarpeta() {
		return this.carpeta.get();
	}//FIN METODO
	
	public StringProperty carpetaProperty() {
		return this.carpeta;
	}//FIN METODO
	//FIN METODOS "CARPETA"
	
	//METODO DE ACCESO A "ESPECIFICACION TECNICA"
	public void setEspecificacionTenica(String especificacionTecnica) {
		this.especificacionTecnica.set(especificacionTecnica);
	}//FIN METODO
	
	public String getEspecificacionTecnica() {
		return this.especificacionTecnica.get();
	}//FIN METODO
	
	public StringProperty especificacionTecnicaProperty() {
		return this.especificacionTecnica;
	}//FIN METODO
	//FIN METODOS "ESPECIFICACION TECNICA"
	
	//METODOS DE ACCESO A "COSTO DIRECTO"
	public void setCostoDirecto(Double costoDirecto) {
		this.costoDirecto.set(costoDirecto);
	}//FIN METODO
				
	public Double getCostoDirecto() {
		return this.costoDirecto.get();
	}//FIN METODO
				
	public ObjectProperty<Double> costoDirectoProperty() {
		return this.costoDirecto;
	}//FIN METODO
		//FIN METODOS "COSTO DIRECTO"
	
	//METODOS DE ACCESO A "COSTO INDIRECTO"
	public void setCostoIndirecto(Double costoIndirecto) {
		this.costoIndirecto.set(costoIndirecto);
	}//FIN METODO
			
	public Double getCostoIndirecto() {
		return this.costoIndirecto.get();
	}//FIN METODO
			
	public ObjectProperty<Double> costoIndirectoProperty() {
		return this.costoIndirecto;
	}//FIN METODO
	//FIN METODOS "COSTO INDIRECTO"
	
	//METODOS DE ACCESO A "PRECIO"
	public void setPrecio(Double precio) {
		this.precio.set(precio);
	}//FIN METODO
				
	public Double getPrecio() {
		return this.precio.get();
	}//FIN METODO
				
	public ObjectProperty<Double> precioProperty() {
		return this.precio;
	}//FIN METODO
	//FIN METODOS "COSTO INDIRECTO"
		
	//METODOS PARA ACCESO A "CLIENTEFK"
	public void setClienteFK(Integer clienteFK) {
		this.clienteFK.set(clienteFK);
	}//FIN METODO
	
	public Integer getClienteFK() {
		return this.clienteFK.get();
	}//FIN METODO
		
	public Cliente getCliente(Connection connection) {
		return ClienteDAO.readCliente(connection, this.getClienteFK());
	}//FIN METODO
	//FIN METODOS "CLIENTE"
	
	public void setComponenteFK(int componenteFK) {
		this.componenteFK.set(componenteFK);
	}//FIN METODO
	
	public int getComponenteFK() {
		return this.componenteFK.get();
	}//FIN METODO
	
	public ObjectProperty<Integer> componenteFKProperty() {
		return this.componenteFK;
	}//FIN METODO
	
	public Componente getComponente(Connection connection) {
		return ComponenteDAO.readComponente(connection, this.getComponenteFK());
	}//FIN METODO
		
}//FIN METODO