package mx.shf6.produccion.model;

import java.sql.Connection;
import java.sql.Date;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import mx.shf6.produccion.model.dao.ClienteDAO;
import mx.shf6.produccion.model.dao.FolioDAO;

public class Cotizacion {

	//PROPIEDADES
	private ObjectProperty<Integer> sysPK;
	private StringProperty referencia;	
	private ObjectProperty<Date> fecha;
	private ObjectProperty<Integer> status;
	private StringProperty solicitante;
	private StringProperty areaDepartamento;
	private StringProperty telefonoFax;
	private StringProperty email;
	private StringProperty tipoServicio;
	private ObjectProperty<Date> fechaEntrega;
	private StringProperty condicionEmbarque;
	private StringProperty condicionPago;
	private ObjectProperty<Integer> moneda;
	private ObjectProperty<Double> tipoCambio;
	private StringProperty observaciones;
	private StringProperty vigencia;	
	private ObjectProperty<Integer> clienteFK;
	private ObjectProperty<Integer> folioFK;
	
	//CONSTANTES
	public static final int PENDIENTE = 0;
	public static final int APROBADA = 1;
	public static final int CANCELADA = 2;
	
	public static final int MXN = 0;
	public static final int USD = 1;
	public static final int EUR = 2;
	
	//CONSTRUCTOR VACIO
	public Cotizacion() {
		this(-1, "", null, -1, "", "", "", "", "", null, "", "", -1, 0.0, "", "", -1, -1);
	}//FIN CONSTRUCTOR
	
	//CONSTRUCTOR CON PARAMETROS
	public Cotizacion(int sysPK, String referencia, Date fecha, int status, String solicitante, 
			String areaDepartamento, String telefonoFax, String email, String tipoServicio, 
			Date fechaEntrega, String condicionEmbarque, String condicionPago, int moneda, 
			double tipoCambio, String observaciones, String vigencia, int folioFK, int clienteFK) {
		
		this.sysPK = new SimpleObjectProperty<Integer>(sysPK);
		this.referencia = new SimpleStringProperty(referencia);
		this.fecha = new SimpleObjectProperty<Date>(fecha);
		this.status = new SimpleObjectProperty<Integer>(status);
		this.solicitante = new SimpleStringProperty(solicitante);
		this.areaDepartamento =  new SimpleStringProperty(areaDepartamento);
		this.telefonoFax = new SimpleStringProperty(telefonoFax);
		this.email = new SimpleStringProperty(email);
		this.tipoServicio = new SimpleStringProperty(tipoServicio);
		this.fechaEntrega = new SimpleObjectProperty<Date>(fechaEntrega);
		this.condicionEmbarque = new SimpleStringProperty(condicionEmbarque);
		this.condicionPago = new SimpleStringProperty(condicionPago);
		this.moneda = new SimpleObjectProperty<Integer>(moneda);
		this.tipoCambio = new SimpleObjectProperty<Double>(tipoCambio);
		this.observaciones = new SimpleStringProperty(observaciones);
		this.vigencia = new SimpleStringProperty(vigencia);
		this.folioFK = new SimpleObjectProperty<Integer>(folioFK);
		this.clienteFK = new SimpleObjectProperty<Integer>(clienteFK);
	}//FIN CONSTRUCTOR
	
	
	//METODOS DE ACCESO A "SYPK"
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
	
	//METODOS DE ACCESO A "REFERENCIA"
	public void setReferencia(String referencia) {
		this.referencia.set(referencia);
	}//FIN METODO
	
	public String getReferencia() {
		return this.referencia.get();
	}//FIN METODO
	
	public StringProperty referenciaProperty() {
		return this.referencia;
	}//FIN METODO
	//FIN METODOS "REFERENCIA"
	
	//METODOS DE ACCESO A "FECHA"
	public void setFecha(Date fecha) {
		this.fecha.set(fecha);
	}//FIN METODO
	
	public Date getFecha() {
		return this.fecha.get();
	}//FIN METODO
	
	public ObjectProperty<Date> fechaProperty(){
		return this.fecha;
	}//FIN METODO
	//FIN METODOS "FECHA"
	
	//METODOS DE ACCESO A "STATUS"
	public void setStatus(Integer status) {
		this.status.set(status);
	}//FIN METODO
	
	public Integer getStatus() {
		return this.status.get();
	}//FIN METODO
	
	public ObjectProperty<Integer> statusProperty() {
		return this.status;
	}//FIN METODO	

	public String getDescripcionStatus() {
		switch (this.getStatus()) {
		case 0:
			return "Pendiente";
		case 1:
			return "Aprobada";
		case 2:
			return "Cancelada";
		}//FIN WTITCH
		return "";
	}//FIN METODO
	
	public void setNumeroStatus(String status) {
		switch (status) {
		case "Pendiente":
			this.setStatus(0);
			break;
		case "Aprobada":
			this.setStatus(1);
			break;
		case "Cancelada":
			this.setStatus(2);
			break;
		}//FIN WTITCH
	}//FIN METODO
	
	public StringProperty descripcionStatusProperty() {
		switch (this.getStatus()) {
		case 0:
			return new SimpleStringProperty("Pendiente");
		case 1:
			return new SimpleStringProperty("Aprobada");
		case 2:
			return new SimpleStringProperty("Cancelada");
		}//FIN WTITCH
		return new SimpleStringProperty();
	}//FIN METODO
	//FIN METODOS "STATUS"
	
	//METODOS DE ACCESO A "SOLICITANTE"
	public void setSolicitante(String solicitante) {
		this.solicitante.set(solicitante);
	}//FIN METODO
	
	public String getSolicitante() {
		return this.solicitante.get();
	}//FIN METODO
	
	public StringProperty solicitanteProperty() {
		return this.solicitante;
	}//FIN METODO
	//FIN METODOS "SOLICITANTE"
	
	//METODOS DE ACCESO A "AREADEPARTAMENTO"
	public void setAreaDepartamento(String areaDepartamento) {
		this.areaDepartamento.set(areaDepartamento);
	}//FIN METODO
	
	public String getAreaDepartamento() {
		return this.areaDepartamento.get();
	}//FIN METODO
	
	public StringProperty areaDepartamentoProperty() {
		return this.areaDepartamento;
	}//FIN METODO
	//FIN METODOS "AREADEPARTAMENTO"
	
	//METODOS DE ACCESO A "TELEFONOFAX"
	public void setTelefonoFax(String telefonoFax) {
		this.telefonoFax.set(telefonoFax);
	}//FIN METODO
	
	public String getTelefonoFax() {
		return this.telefonoFax.get();
	}//FIN METODO
	
	public StringProperty telefonoFaxProperty() {
		return this.telefonoFax;
	}//FIN METODO
	//FIN METODOS "TELEFONOFAX"
	
	//METODOS DE ACCESO A "EMAIL"
	public void setEmail(String email) {
		this.email.set(email);
	}//FIN METODO
	
	public String getEmail() {
		return this.email.get();
	}//FIN METODO
	
	public StringProperty emailProperty() {
		return this.email;
	}//FIN METODO
	//FIN METODOS "EMAIL"
	
	//METODOS DE ACCESO A "TIPOSERVICIO"
	public void setTipoServicio(String tipoServicio) {
		this.tipoServicio.set(tipoServicio);
	}//FIN METODO
	
	public String getTipoServicio() {
		return this.tipoServicio.get();
	}//FIN METODO
	
	public StringProperty tipoServicioProperty() {
		return this.tipoServicio;
	}//FIN METODO
	//FIN METODOS "TIPOSERVICIO"
	
	//METODOS DE ACCESO A "FECHA ENTREGA"
	public void setFechaEntrega(Date fechaEntrega) {
		this.fechaEntrega.set(fechaEntrega);
	}//FIN METODO
	
	public Date getFechaEntrega() {
		return this.fechaEntrega.get();
	}//FIN METODO
	
	public ObjectProperty<Date> fechaEntregaProperty() {
		return this.fechaEntrega;
	}//FIN METODO
	//FIN METODOS "FECHA ENTREGA"
	
	//METODOS DE ACCESO A "CONDICIONEMBARQUE"
	public void setCondicionEmbarque(String condicionEmbarque) {
		this.condicionEmbarque.set(condicionEmbarque);
	}//FIN METODO
	
	public String getCondicionEmbarque() {
		return this.condicionEmbarque.get();
	}//FIN METODO
	
	public StringProperty condicionEmbarqueProperty() {
		return this.condicionEmbarque;
	}//FIN METODO
	//FIN METODOS "CONDICIONEMBARQUE"
	
	//METODOS DE ACCESO A "CONDICIONPAGO"
	public void setCondicionPago(String condicionPago) {
		this.condicionPago.set(condicionPago);
	}//FIN METODO
	
	public String getCondicionPago() {
		return this.condicionPago.get();
	}//FIN METODO
	
	public StringProperty condicionPagoProperty() {
		return this.condicionPago;
	}//FIN METODO
	//FIN METODOS "CONDICIONPAGO"
	
	//METODOS DE ACCESO A "MONEDA"
	public void setMoneda(Integer moneda) {
		this.moneda.set(moneda);
	}//FIN METODO
	
	public Integer getMoneda() {
		return this.moneda.get();
	}//FIN METODO
	
	public ObjectProperty<Integer> monedaProperty() {
		return this.moneda;
	}//FIN METODO	

	public String getDescripcionMoneda() {
		switch (this.getMoneda()) {
		case 0:
			return "MXN";
		case 1:
			return "USD";
		case 2:
			return "EUR";
		}//FIN WTITCH
		return "";
	}//FIN METODO
	
	public void setNumeroMoneda(String moneda) {
		switch (moneda) {
		case "MXN":
			this.setMoneda(0);
			break;
		case "USD":
			this.setMoneda(1);
			break;
		case "EUR":
			this.setMoneda(2);
			break;
		}//FIN SSWTITCH
	}//FIN METODO
	
	public StringProperty descripcionMonedaProperty() {
		switch (this.getMoneda()) {
		case 0:
			return new SimpleStringProperty("MXN");
		case 1:
			return new SimpleStringProperty("USD");
		case 2:
			return new SimpleStringProperty("EUR");
		}//FIN WTITCH
		return new SimpleStringProperty();
	}//FIN METODO
	//FIN METODOS "MONEDA"
	
	//METODOS DE ACCESO A "TIPOCAMBIO"
	public void setTipoCambio(Double tipoCambio) {
		this.tipoCambio.set(tipoCambio);
	}//FIN METODO
	
	public Double getTipoCambio() {
		return this.tipoCambio.get();
	}//FIN METODO
	
	public ObjectProperty<Double> tipoCambioProperty() {
		return this.tipoCambio;
	}//FIN METODO
	//FIN METODOS "TIPOCAMBIO"
	
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
	
	//METODOS DE ACCESO A "VIGENCIA"
	public void setVigencia(String vigencia) {
		this.vigencia.set(vigencia);
	}//FIN METODO
	
	public String getVigencia() {
		return this.vigencia.get();
	}//FIN METODO
	
	public StringProperty vigenciaProperty() {
		return this.vigencia;
	}//FIN METODO
	//FIN METODOS "VIGENCIA"
	
	//METODOS DE ACCESO A "FOLIO"
	public void setFolioFK(Integer folioFK) {
		this.folioFK.set(folioFK);
	}//FIN METODO
	
	public Integer getFolioFK() {
		return this.folioFK.get();
	}//FIN METODO
	
	public ObjectProperty<Integer> folioFKProperty(){
		return this.folioFK;
	}//FIN METODO
	
	public Folio getFolio(Connection connection) {
		return FolioDAO.readFolio(connection, this.getFolioFK());
	}//FIN METODO	
	//FIN METODOS "FOLIO"
	
	//METODOS DE ACCESO A "CLIENTE"
	public void setClienteFK(Integer clienteFK) {
		this.clienteFK.set(clienteFK);
	}//FIN METODO
	
	public Integer getClienteFK() {
		return this.clienteFK.get();
	}//FIN METODO
	
	public ObjectProperty<Integer> clienteFKProperty(){
		return this.clienteFK;
	}//FIN METODO
	
	public Cliente getCliente(Connection connection) {
		return ClienteDAO.readCliente(connection, this.getClienteFK());
	}//FIN METODO
	//FIN METODOS "CLIENTE"
}//FIN CLASE
