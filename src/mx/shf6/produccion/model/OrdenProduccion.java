package mx.shf6.produccion.model;

import java.sql.Date;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class OrdenProduccion {
	
	//PROPIEDADES
	private ObjectProperty<Integer> sysPK;
	private ObjectProperty<Date> fecha;
	private StringProperty lote;
	private ObjectProperty<Integer> status;
	private ObjectProperty<Integer> detalleOrdenCompraFK;
	private StringProperty cliente;
	private StringProperty ordenCompra;
	private ObjectProperty<Integer> componenteFK;
	private StringProperty cNumeroParte;
	private StringProperty cDescripcion;
	private ObjectProperty<Double> cantidad;
	private ObjectProperty<Date> fechaEntrega;
	
	//CONSTANTES
	public static final int PENDIENTE = 0;
	public static final int EN_PROCESO = 1;
	public static final int PARO = 2;
	public static final int TERMINADO = 3;
	
	//CONSTRUCTOR VACIO
	public OrdenProduccion() {
		this(0, new Date(System.currentTimeMillis()), "", 0, 0, "", "", 0, "", "", 0.0, new Date(System.currentTimeMillis()));
	}//FIN CONSTRUCTOR
	
	//CONSTRUCTOR LLENO
	public OrdenProduccion(Integer sysPK, Date fecha, String lote, Integer status, Integer detalleOrdenCompraFK, String cliente, String ordenCompra, Integer componenteFK, String cNumeroParte, String cDescripcion, Double cantidad, Date fechaEntrega) {
		this.sysPK = new SimpleObjectProperty<Integer>(sysPK);
		this.fecha = new SimpleObjectProperty<Date>(fecha);
		this.lote = new SimpleStringProperty(lote);
		this.status = new SimpleObjectProperty<Integer>(status);
		this.detalleOrdenCompraFK = new SimpleObjectProperty<Integer>(detalleOrdenCompraFK);
		this.cliente = new SimpleStringProperty(cliente);
		this.ordenCompra = new SimpleStringProperty(ordenCompra);
		this.componenteFK = new SimpleObjectProperty<Integer>(componenteFK);
		this.cNumeroParte = new SimpleStringProperty(cNumeroParte);
		this.cDescripcion =  new SimpleStringProperty(cDescripcion);
		this.cantidad = new SimpleObjectProperty<Double>(cantidad);
		this.fechaEntrega = new SimpleObjectProperty<Date>(fechaEntrega);
	}//FIN CONSTRUCTOR
	
	//METODOS DE ACCESO A SYSPK
	public void setSysPK(Integer sysPK) {
		this.sysPK.set(sysPK);
	}//FIN METODO
	
	public Integer getSysPK() {
		return sysPK.get();
	}//FIN METODO
	
	public ObjectProperty<Integer> sysPKProperty() {
		return sysPK;
	}//FIN METODO
	//FIN METODOS DE ACCESO SYSPK
	
	//METODOS DE ACCESO A FECHA
	public void setFecha(Date fecha) {
		this.fecha.set(fecha);
	}//FIN METODO
	
	public Date getFecha() {
		return fecha.get();
	}//FIN METODO
	
	public ObjectProperty<Date> fechaProperty() {
		return fecha;
	}//FIN METODO
	//FIN METODOS DE ACCESO A FECHA
	
	//METODOS DE ACCESO A LOTE
	public void setLote(String lote) {
		this.lote.set(lote);
	}//FIN METODO
	
	public String getLote() {
		return lote.get();
	}//FIN METODO
	
	public StringProperty loteProperty() {
		return lote;
	}//FIN METODO
	//FIN METODOS DE ACCESO A LOTE
	
	//METODOS DE ACCESO A STATUS
	public void setStatus(Integer status) {
		this.status.set(status);
	}//FIN METODO
	
	public Integer getStatus() {
		return status.get();
	}//FIN METODO
	
	public ObjectProperty<Integer> statusProperty() {
		return status;
	}//FIN METODO
	//FIN METODOS DE ACCESO A STATUS

	//METODOS DE ACCESO A DETALLECOTIZACIONFK
	public void setDetalleOrdenCompraFK(Integer detalleOrdenCompraFK) {
		this.detalleOrdenCompraFK.set(detalleOrdenCompraFK);
	}//FIN METODO
	
	public Integer getDetalleCotizacionFK() {
		return detalleOrdenCompraFK.get();
	}//FIN METODO
	
	public ObjectProperty<Integer> detalleOrdenCompraFKProperty() {
		return detalleOrdenCompraFK;
	}//FIN METODO
	//FIN METODOS DE ACCESO A DETALLECOTIZACIONFK
	
	//METODOS DE ACCESO A CLIENTE
	public void setCliente(String cliente) {
		this.cliente.set(cliente);
	}//FIN METODO
	
	public String getCliente() {
		return cliente.get();
	}//FIN METODO
	
	public StringProperty clienteProperty() {
		return cliente;
	}//FIN METODO
	//FIN METODOS DE ACCESO A CLIENTE
	
	//METODOS DE ACCESO A ORDEN COMPRA
	public void setOrdenCompra(String ordenCompra) {
		this.ordenCompra.set(ordenCompra);
	}//FIN METODO
	
	public String getOrdenCompra() {
		return ordenCompra.get();
	}//FIN METODO
	
	public StringProperty ordenCompraProperty() {
		return ordenCompra;
	}//FIN METODO
	//FIN METODOS DE ACCESO A COTIZACION
	
	public void setComponenteFK(Integer componenteFK) {
		this.componenteFK.set(componenteFK);
	}//FIN METODO
	
	public Integer getComponenteFK() {
		return this.componenteFK.get();
	}//FIN METODO
	
	public ObjectProperty<Integer> componenteFKProperty() {
		return this.componenteFK;
	}//FIN METODO
	
	//METODOS DE ACCESO A PROYECTO
	public void setNumeroParte(String cNumeroParte) {
		this.cNumeroParte.set(cNumeroParte);
	}//FIN METODO
	
	public String getNumeroParte() {
		return this.cNumeroParte.get();
	}//FIN METODO
	
	public StringProperty numeroParteProperty() {
		return cNumeroParte;
	}//FIN METODO
	//FIN METODOS DE ACCESO A PROYECTO
	
	//METODOS DE ACCESO A COMPONENTE
	public void setDescripcion(String cDescripcion) {
		this.cDescripcion.set(cDescripcion);
	}//FIN METODO
	
	public String getcDescripcion() {
		return this.cDescripcion.get();
	}//FIN METODO
	
	public StringProperty descripcionProperty() {
		return cDescripcion;
	}//FIN METODO
	//FIN METODOS DE ACCESO A COMPONENTE
	
	public void setCantidad(Double cantidad) {
		this.cantidad.set(cantidad);
	}//FIN METODO
	
	public Double getCantidad() {
		return this.cantidad.get();
	}//FIN METODO
	
	public ObjectProperty<Double> cantidadProperty() {
		return this.cantidad;
	}//FIN METODO
	
	//METODOS PARA ACCESO A FECHA ENTREGA
	public void setFechaEntrega(Date fechaEntrega) {
		this.fechaEntrega.set(fechaEntrega);
	}//FIN METODO
	
	public Date getFechaEntrega() {
		return this.fechaEntrega.get();
	}//FIN METODO
	
	public ObjectProperty<Date> fechaEntregaProperty(){
		return this.fechaEntrega;
	}//FIN METODO
	
	public StringProperty descripcionStatusProperty() {
		switch (this.getStatus()) {
			case 0:
				return new SimpleStringProperty("Pendiente");
			case 1:
				return new SimpleStringProperty("En proceso");
			case 2:
				return new SimpleStringProperty("En paro");
			case 3: 
				return new SimpleStringProperty("Terminado");
			case 4:
			return new SimpleStringProperty("Entregado");
		}//FIN SWITCH
		return new SimpleStringProperty();
	}//FIN METODO
}//FIN CLASE
