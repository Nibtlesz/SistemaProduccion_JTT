package mx.shf6.produccion.model;

import java.sql.Date;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class DetalleEntregaOrdenCompra {

	//PROPIEDADES
	private ObjectProperty<Integer> sysPK;
	private StringProperty factura;
	private ObjectProperty<Integer> cantidad;
	private ObjectProperty<Date> fecha;
	private ObjectProperty<DetalleOrdenCompra>  detalleOrdenCompraFK;
	
	//CONSTRUCTOR
	public DetalleEntregaOrdenCompra() {
		this(0, "", 0, new Date(System.currentTimeMillis()), new DetalleOrdenCompra());
	}//FIN CONSTRUCTOR

	public DetalleEntregaOrdenCompra(Integer sysPK, String factura, Integer cantidad, Date fecha, DetalleOrdenCompra detalleOrdenCompraFK) {
		this.sysPK = new SimpleObjectProperty<Integer>(sysPK);
		this.factura = new SimpleStringProperty(factura);
		this.cantidad = new SimpleObjectProperty<Integer>(cantidad);
		this.fecha = new SimpleObjectProperty<Date>(fecha);
		this.detalleOrdenCompraFK = new SimpleObjectProperty<DetalleOrdenCompra>(detalleOrdenCompraFK);
	}//FIN CONSTRUCTOR
	
	//METODO ACCESO
	public void setSysPK(Integer sysPK) {
		this.sysPK.set(sysPK);
	}//FIN METODO
	
	public Integer getSysPK() {
		return this.sysPK.get();
	}//FIN METODO
	
	public ObjectProperty<Integer> sysPKProperty() {
		return this.sysPK;
	}//FIN METODO
	
	public void setFactura(String factura) {
		this.factura.set(factura);
	}//FIN METODO
	
	public String getFactura() {
		return this.factura.get();
	}//FIN METODO
	
	public StringProperty facturaProperty() {
		return this.factura;
	}//FIN METODO
	
	public void setCantidad(Integer cantidad) {
		this.cantidad.set(cantidad);
	}//FIN METODO
	
	public Integer getCantidad() {
		return this.cantidad.get();
	}//FIN METODO
	
	public ObjectProperty<Integer> cantidadProperty() {
		return this.cantidad;
	}//FIN METODO
	
	public void setFecha(Date fecha) {
		this.fecha.set(fecha);
	}//FIN METODO
	
	public Date getFecha() {
		return this.fecha.get();
	}//FIN METODO
	
	public ObjectProperty<Date> fechaProperty() {
		return this.fecha;
	}//FIN METODO
	
	public void setDetalleOrdenCompraFK(DetalleOrdenCompra detalleOrdenCompra) {
		this.detalleOrdenCompraFK.set(detalleOrdenCompra);
	}//FIN METODO
	
	public DetalleOrdenCompra getDetalleOrdenCompraFK() {
		return this.detalleOrdenCompraFK.get();
	}//FIN METODO
	
	public ObjectProperty<DetalleOrdenCompra> detalleOrdenCompraProperty() {
		return this.detalleOrdenCompraFK;
	}//FIN METODO
}//FIN CLASE
