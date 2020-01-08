package mx.shf6.produccion.model;

import java.sql.Date;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class DetalleOrdenCompra {

	//PROPIEDADES
	private ObjectProperty<Integer> sysPK;
	private StringProperty planoOrdenamiento;
	private StringProperty item;
	private ObjectProperty<Date> fechaCliente;
	private ObjectProperty<Date> entregaFinal;
	private ObjectProperty<Integer> porEntregar;
	private ObjectProperty<Integer> saldo;
	private StringProperty procesoPintura;
	private ObjectProperty<OrdenCompra> ordenCompraFK;
	private ObjectProperty<Componente> componenteFK;
	
	//CONSTRUCTOR
	public DetalleOrdenCompra() {
		this(0,"", "", new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()), 0, 0, "", new OrdenCompra(), new Componente());
	}//FIN CONSTRUCTOR

	public DetalleOrdenCompra(Integer sysPK, String planoOrdenamiento, String item, Date fechaCliente, Date entregaFinal, Integer porEntregar, Integer saldo, String procesoPintura,
			OrdenCompra ordenCompraFK, Componente componenteFK) {
		this.sysPK = new SimpleObjectProperty<Integer>(sysPK);
		this.planoOrdenamiento = new SimpleStringProperty(planoOrdenamiento);
		this.item = new SimpleStringProperty(item);
		this.fechaCliente = new SimpleObjectProperty<Date>(fechaCliente);
		this.entregaFinal = new SimpleObjectProperty<Date>(entregaFinal);
		this.porEntregar = new SimpleObjectProperty<Integer>(porEntregar);
		this.saldo = new SimpleObjectProperty<Integer>(saldo);
		this.procesoPintura = new SimpleStringProperty(procesoPintura);
		this.ordenCompraFK = new SimpleObjectProperty<OrdenCompra>(ordenCompraFK);
		this.componenteFK = new SimpleObjectProperty<Componente>(componenteFK);
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
	
	public void setPlanoOdernamiento(String planoOrdenamiento) {
		this.planoOrdenamiento.set(planoOrdenamiento);
	}//FIN METODO
	
	public String getPlanoOrdenamiento() {
		return this.planoOrdenamiento.get();
	}//FIN METODO
	
	public StringProperty planoOrdenamientoProperty() {
		return this.planoOrdenamiento;
	}//FIN METODO
	
	public void setItem(String item) {
		this.item.set(item);
	}//FIN METODO
	
	public String getItem() {
		return this.item.get();
	}//FIN METODO
	
	public StringProperty itemProperty() {
		return this.item;
	}//FIN METODO
	
	public void setFechaCliente(Date fechaCliente) {
		this.fechaCliente.set(fechaCliente);
	}//FIN METODO
	
	public Date getFechaCliente() {
		return this.fechaCliente.get();
	}//FIN METODO
	
	public ObjectProperty<Date> fechaClienteProperty() {
		return this.fechaCliente;
	}//FIN METODO
	
	public void setEntregaFinal(Date entregaFinal) {
		this.entregaFinal.set(entregaFinal);
	}//FIN METODO
	
	public Date getEntregaFinal() {
		return this.entregaFinal.get();
	}//FIN METODO
	
	public ObjectProperty<Date> entregaFinalProperty() {
		return this.entregaFinal;
	}//FIN METODO
	
	public void setPorEntregar(Integer porEntregar) {
		this.porEntregar.set(porEntregar);
	}//FIN METODO
	
	public Integer getPorEntregar() {
		return this.porEntregar.get();
	}//FIN METODO
	
	public ObjectProperty<Integer> porEntregarProperty() {
		return this.porEntregar;
	}//FIN METODO
	
	public void setSaldo(Integer saldo) {
		this.saldo.set(saldo);
	}//FIN METODO
	
	public Integer getSaldo() {
		return this.saldo.get();
	}//FIN METODO
	
	public ObjectProperty<Integer> saldoProperty() {
		return this.saldo;
	}//FIN METODO
	
	public void setProcesoPintura(String procesoPitura) {
		this.procesoPintura.set(procesoPitura);
	}//FIN METODO
	
	public String getProcesoPintura() {
		return this.procesoPintura.get();
	}//FIN METODO
	
	public StringProperty procesoPinturaProperty() {
		return this.procesoPintura;
	}//FIN METODO
	
	public void setOrdenCompraFK(OrdenCompra ordenCompra) {
		this.ordenCompraFK.set(ordenCompra);
	}//FIN METODO
	
	public OrdenCompra getOrdenCompraFK() {
		return this.ordenCompraFK.get();
	}//FIN METODO
	
	public ObjectProperty<OrdenCompra> ordenCompraProperty() {
		return this.ordenCompraFK;
	}//FIN METODO
	
	public void setComponenteFK(Componente componente) {
		this.componenteFK.set(componente);
	}//FIN METODO
	
	public Componente getComponenteFK() {
		return this.componenteFK.get();
	}//FIN METODO
	
	public ObjectProperty<Componente> componenteProperty() {
		return this.componenteFK;
	}//FIN METODO
}//FIN CLASE
