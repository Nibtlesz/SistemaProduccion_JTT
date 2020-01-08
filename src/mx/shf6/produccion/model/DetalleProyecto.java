package mx.shf6.produccion.model;

import com.mysql.jdbc.Connection;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import mx.shf6.produccion.model.dao.ProyectoDAO;

public class DetalleProyecto {

	//PROPIEDADES
	private ObjectProperty<Integer> sysPK;
	private StringProperty codigoParte;
	private StringProperty descripcion;
	private StringProperty rutaArchivo;
	private ObjectProperty<Integer> proyectoFK;
	
	//CONSTRUCTOR VACIO
	public DetalleProyecto() {
		this(-1, "", "", "", -1);
	}//FIN CONSTRUCTOR

	//CONSTRUCTOR CON PARAMETROS
	public DetalleProyecto(int sysPK, String codigoParte, String descripcion, String rutaArchivo, int proyectoFK) {
		this.sysPK = new SimpleObjectProperty<Integer>(sysPK);
		this.codigoParte = new SimpleStringProperty(codigoParte);
		this.descripcion = new SimpleStringProperty(descripcion);
		this.rutaArchivo = new SimpleStringProperty(rutaArchivo);
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
	
	//METODOS DE ACCESO A "CODIGOPARTE"
	public void setCodigoParte(String codigoParte) {
		this.codigoParte.set(codigoParte);
	}//FIN METODO
	
	public String getCodigoParte() {
		return this.codigoParte.get();
	}//FIN METODO
	
	public StringProperty codigoParteProperty() {
		return this.codigoParte;
	}//FIN METODO
	//FIN METODOS "CODIGOPARTE"
	
	//FIN METODOS DE ACCESO A "DESCRIPCION"
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
	
	//METODOS DE ACCESO A "RUTA ARCHIVO"
	public void setRutaArchivo(String rutaArchivo) {
		this.rutaArchivo.set(rutaArchivo);
	}//FIN METODO
	
	public String getRutaArchivo() {
		return this.rutaArchivo.get();
	}//FIN METODO
	
	public StringProperty rutaArchivoProperty() {
		return this.rutaArchivo;
	}//FIN METODO
	//FIN METODOS "RUTA ARCHIVO"
	
	//METODOS DE ACCESO A "PROYECTO"
	public void setProyectoFK(int proyectoFK) {
		this.proyectoFK.set(proyectoFK);
	}//FIN METODO
	
	public int getProyectoFK() {
		return this.proyectoFK.get();
	}//FIN METODO
	
	public ObjectProperty<Integer> proyectoFK(){
		return this.proyectoFK;
	}//FIN METODO
	
	public Proyecto getProyecto(Connection connection) {
		return ProyectoDAO.readProyecto(connection, this.getProyectoFK());
	}//FIN METODO
	//FIN METODOS "PROYECTO"
	
}//FIN CLASE
