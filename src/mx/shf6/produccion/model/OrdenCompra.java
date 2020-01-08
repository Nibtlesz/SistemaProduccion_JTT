package mx.shf6.produccion.model;

import java.sql.Date;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class OrdenCompra {

	//PROPIEDADES
	private ObjectProperty<Integer> sysPK;
	private ObjectProperty<Date> fechaPedido;
	private StringProperty folio;
	private StringProperty pmp;
	private StringProperty comentarios;
	private ObjectProperty<Cliente> clienteFK;
	
	//CONSTRUCTOR
	public OrdenCompra() {
		this (0, new Date(System.currentTimeMillis()), "", "", "", new Cliente());
	}//FIN CONSTRUCTOR

	public OrdenCompra(Integer sysPK, Date fechaPedido, String folio, String pmp, String comentarios, Cliente clienteFK) {
		this.sysPK = new SimpleObjectProperty<Integer>(sysPK);
		this.fechaPedido = new SimpleObjectProperty<Date>(fechaPedido);
		this.folio = new SimpleStringProperty(folio);
		this.pmp = new SimpleStringProperty(pmp);
		this.comentarios = new SimpleStringProperty(comentarios);
		this.clienteFK = new SimpleObjectProperty<Cliente>(clienteFK);
	}//FIN CONSTRUCTOR
	
	//METODOS DE ACCESO
	public void setSysPK(Integer sysPK) {
		this.sysPK.set(sysPK);
	}//FIN METODO
	
	public Integer getSysPK() {
		return this.sysPK.get();
	}//FIN METODO
	
	public ObjectProperty<Integer> sysPKProperty() {
		return this.sysPK;
	}//FIN METODO
	
	public void setFechaPedido(Date fechaPedido) {
		this.fechaPedido.set(fechaPedido);
	}//FIN METODO
	
	public Date getFechaPedido() {
		return this.fechaPedido.get();
	}//FIN METODO
	
	public ObjectProperty<Date> fechaPedidoProperty() {
		return this.fechaPedido;
	}//FIN METODO
	
	public void setFolio(String folio) {
		this.folio.set(folio);
	}//FIN METODO
	
	public String getFolio() {
		return this.folio.get();
	}//FIN METODO
	
	public StringProperty folioProperty() {
		return this.folio;
	}//FIN METODO
	
	public void setPMP(String pmp) {
		this.pmp.set(pmp);
	}//FIN METODO
	
	public String getPMP() {
		return this.pmp.get();
	}//FIN METODO
	
	public StringProperty pmpProperty() {
		return this.pmp;
	}//FIN METODO
	
	public void setComentarios(String comentarios) {
		this.comentarios.set(comentarios);
	}//FIN METODO
	
	public String getComentarios() {
		return this.comentarios.get();
	}//FIN METODO
	
	public StringProperty comentariosProperty() {
		return this.comentarios;
	}//FIN METODO
	
	public void setClienteFK (Cliente cliente) {
		this.clienteFK.set(cliente);
	}//FIN METODO
	
	public Cliente getClienteFK() {
		return this.clienteFK.get();
	}//FIN METODO
	
	public ObjectProperty<Cliente> clienteFKProperty() {
		return this.clienteFK;
	}//FIN METODO
}//FIN CLASE
