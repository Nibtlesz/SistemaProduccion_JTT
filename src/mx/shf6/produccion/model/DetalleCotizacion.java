package mx.shf6.produccion.model;

import java.sql.Connection;
import java.sql.Date;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import mx.shf6.produccion.model.dao.CotizacionDAO;
import mx.shf6.produccion.model.dao.ProyectoDAO;

public class DetalleCotizacion {

	//PROPIEDADES	
	private ObjectProperty<Integer> sysPK;
	private ObjectProperty<Double> cantidad;
	private ObjectProperty<Double> precio;
	private ObjectProperty<Double> costo;
	private ObjectProperty<Date> fechaEntrega;
	private StringProperty observaciones;
	private ObjectProperty<Integer> proyectoFK;
	private ObjectProperty<Integer> cotizacionFK;
	private ObjectProperty<Integer> partida;
	private StringProperty numeroDibujo;
	private StringProperty nombreProyecto;
	
	//CONSTRUCTOR VACIO
	public DetalleCotizacion() {
		this(-1, 0.0, 0.0, 0.0, new Date(System.currentTimeMillis()), "", -1, -1, -1, "", "");
	}//FIN CONTRUCTOR
	
	//CONSTRUCTOR CON PARAMETROS
	public DetalleCotizacion(int sysPK, double cantidad, double precio, double costo, Date fechaEntrega, String observaciones, int proyectoFK, int cotizacionFK, int partida, String numeroDibujo, String nombreProyecto) {
		this.sysPK = new SimpleObjectProperty<Integer>(sysPK);
		this.cantidad = new SimpleObjectProperty<Double>(cantidad);
		this.precio = new SimpleObjectProperty<Double>(precio);
		this.costo = new SimpleObjectProperty<Double>(costo);
		this.fechaEntrega = new SimpleObjectProperty<Date>(fechaEntrega);
		this.observaciones = new SimpleStringProperty(observaciones);
		this.proyectoFK = new SimpleObjectProperty<Integer>(proyectoFK);
		this.cotizacionFK = new SimpleObjectProperty<Integer>(cotizacionFK);
		this.partida = new SimpleObjectProperty<Integer>(partida);
		this.numeroDibujo = new SimpleStringProperty(numeroDibujo);
		this.nombreProyecto = new SimpleStringProperty(nombreProyecto);
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
	
	//METODOS DE ACCESO A "CANTIDAD"
	public void setCantidad(Double cantidad) {
		this.cantidad.set(cantidad);
	}//FIN METODO
	
	public Double getCantidad() {
		return this.cantidad.get();
	}//FIN METODO
	
	public ObjectProperty<Double> cantidadProperty() {
		return this.cantidad;
	}//FIN METODO
	//FIN METODOS "CANTIDAD"
	
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
	//FIN METODOS "PRECIO"
	
	//METODOS DE ACCESO A "COSTO"
	public void setCosto(Double costo) {
		this.costo.set(costo);
	}//FIN METODO
	
	public Double getCosto() {
		return this.costo.get();
	}//FIN METODO
	
	public ObjectProperty<Double> costoProperty() {
		return this.costo;
	}//FIN METODO
	//FIN METODOS "COSTO"
	
	//METODOS DE ACCESO A "FECHA ENTREGA"
	public void setFechaEntrega(Date fechaEntrega) {
		this.fechaEntrega.set(fechaEntrega);
	}//FIN METODO
	
	public Date getFechaEntrega() {
		return this.fechaEntrega.get();
	}//FIN METODO
	
	public ObjectProperty<Date> fechaEntregaProperty() {
		return fechaEntrega;
	}//FIN METODO
	//FIN METODOS "FECHA ENTREGA"
	
	//METODOS DE ACCESO A "OBSERVACIONES"
	public void setObservaciones(String observaciones) {
		this.observaciones.set(observaciones);
	}//FIN METODO
	
	public String getObservaciones() {
		return this.observaciones.get();
	}//FIN METODO
	
	public StringProperty observacionesProperty() {
		return this.observaciones;
	}//FIN METODO
	//FIN METODOS "OBSERVACIONES"
	
	//METODOS DE ACCESO A "PROYECTO"
	public void setProyectoFK(Integer proyectoFK) {
		this.proyectoFK.set(proyectoFK);
	}//FIN METODO
	
	public Integer getProyectoFK() {
		return this.proyectoFK.get();
	}//FIN METODO
	
	public Proyecto getProyecto(Connection connection) {
		return ProyectoDAO.readProyecto(connection, this.getProyectoFK());
	}//FIN METODO
	//FIN METODOS "PROYECTO"
	
	//METODOS DE ACCESO A "COTIZACION"
	public void setCotizacionFK(Integer cotizacionFK) {
		this.cotizacionFK.set(cotizacionFK);
	}//FIN METODO
	
	public Integer getCotizacionFK() {
		return this.cotizacionFK.get();
	}//FIN METODO
	
	public Cotizacion getCotizacion(Connection connection) {
		return CotizacionDAO.readCotizacion(connection, this.getCotizacionFK());
	}//FIN METODO
	//FIN METODOS "COTIZACION"
	
	public void setPartida(int partida) {
		this.partida.set(partida);
	}//FIN METODOS
	
	public int getPartida() {
		return this.partida.get();
	}//FIN METODO
	
	public ObjectProperty<Integer> partidaProperty (){
		return this.partida;
	}//FIN METODO
	
	public void setNumeroDibujo(String numeroDibujo) {
		this.numeroDibujo.set(numeroDibujo);
	}//FIN METODO
	
	public String getNumeroDibujo() {
		return this.numeroDibujo.get();
	}//FIN METODO
	
	public StringProperty numeroDibujoProperty() {
		return this.numeroDibujo;
	}//FIN METODO
	
	public void setNombreProyecto(String nombreProyecto) {
		this.nombreProyecto.set(nombreProyecto);
	}//FIN METODO
	
	public String getNombreProyecto() {
		return this.nombreProyecto.get();
	}//FIN METODO
	
	public StringProperty nombreProyectoProperty() {
		return this.nombreProyecto;
	}//FIN METODO
}//FIN CLASE