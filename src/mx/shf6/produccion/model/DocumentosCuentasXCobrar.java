package mx.shf6.produccion.model;

import java.sql.Date;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class DocumentosCuentasXCobrar {

	//PROPIEDADES
	private ObjectProperty<Integer> sysPK;
	private StringProperty referencia;
	private ObjectProperty<Date> fecha;
	private ObjectProperty<Integer>  documento;
	private StringProperty notas;
	private ObjectProperty<Double> debe;
	private ObjectProperty<Double> haber;
	private ObjectProperty<Double> xaplicar;
	private ObjectProperty<Double> pagos;
	private ObjectProperty<Double> bonificaciones;
	private ObjectProperty<Integer> clienteFK;
	private ObjectProperty<Integer> cotizacionFK;
	private ObjectProperty<Integer> reciboFK;
	private ObjectProperty<Double> saldo;
	private StringProperty importeAplicar;

	//CONSTANTES PARA DOCUMENTO
	public static final int COTIZACION = 1;
	public static final int RECIBO = 2;

	public static final int PENDIENTES = 3;
	public static final int MOVIMIENTOS = 4;
	public static final int SALDADOS = 5;

	//CONSTRUCTOR VACIO
	public DocumentosCuentasXCobrar() {
		this(0, "", new Date(System.currentTimeMillis()), 0, "", 0.0, 0.0, 0.0, 0.0, 0.0, 0, 0, 0, 0.0, "0.0");
	}

	//CONSTRUCTOR CON PARAMETROS
	public DocumentosCuentasXCobrar(int sysPk, String referencia, Date fecha, int documento, String notas, Double debe, Double haber, Double xaplicar, Double pagos, Double bonificaciones, int clienteFK, int cotizacionFK, int reciboFK, Double saldo, String importeAplicar){
		this.sysPK = new SimpleObjectProperty<Integer>(sysPk);
		this.referencia = new SimpleStringProperty(referencia);
		this.fecha = new SimpleObjectProperty<Date>(fecha);
		this.documento = new SimpleObjectProperty<Integer>(documento);
		this.notas = new SimpleStringProperty(notas);
		this.debe = new SimpleObjectProperty<Double>(debe);
		this.haber = new SimpleObjectProperty<Double>(haber);
		this.xaplicar = new SimpleObjectProperty<Double>(xaplicar);
		this.pagos = new SimpleObjectProperty<Double>(pagos);
		this.bonificaciones =  new SimpleObjectProperty<Double>(bonificaciones);
		this.clienteFK = new SimpleObjectProperty<Integer>(clienteFK);
		this.cotizacionFK = new SimpleObjectProperty<Integer>(cotizacionFK);
		this.reciboFK = new SimpleObjectProperty<Integer>(reciboFK);
		this.saldo = new SimpleObjectProperty<Double>(saldo);
		this.importeAplicar = new SimpleStringProperty (importeAplicar);
	}

	//METODOS DE ACCESO A SYSPK
	public void setSysPK(int sysPK) {
		this.sysPK.set(sysPK);
	}//FIN METODO

	public int getSysPK() {
		return this.sysPK.get();
	}//FIN METODO

	public ObjectProperty<Integer> sysPKProperty() {
		return this.sysPK;
	}//FIN METODO

	//METODOS DE ACCESO A REFERENCIA
	public void setReferencia(String referencia) {
		this.referencia.set(referencia);
	}//FIN METODO

	public String getReferencia() {
		return this.referencia.get();
	}//FIN METODO

	public StringProperty referenciaProperty() {
		return this.referencia;
	}//FIN METODO

	//METODO DE ACCESO FECHA
	public void setFecha(Date fecha) {
		this.fecha.set(fecha);
	}//FIN METODO

	public Date getFecha() {
		return this.fecha.get();
	}//FIN METODO

	public ObjectProperty<Date> fechaProperty() {
		return this.fecha;
	}//FIN METODO

	//METODO DE ACCESO A DOCUMENTO
	public void setDocumento(int documento) {
		this.documento.set(documento);
	}//FIN METODO

	public int getDocumento() {
		return this.documento.get();
	}//FIN METODO

	public ObjectProperty<Integer> documentoProperty() {
		return this.documento;
	}//FIN METODO

	//METODOS DE ACCESO A NOTAS
	public void setNotas(String notas) {
		this.notas.set(notas);
	}//FIN METODO

	public String getNotas() {
		return this.notas.get();
	}//FIN METODO

	public StringProperty notasProperty() {
		return this.notas;
	}//FIN METODO

	//METODO DE ACCESO A DEBE
	public void setDebe(Double debe) {
		this.debe.set(debe);
	}//FIN METODO

	public Double getDebe() {
		return this.debe.get();
	}//FIN METODO

	public ObjectProperty<Double> debeProperty() {
		return this.debe;
	}//FIN METODO

	//METODO DE ACCESO A HABER
	public void setHaber(Double haber) {
		this.haber.set(haber);
	}//FIN METODO

	public Double getHaber() {
		return this.haber.get();
	}//FIN METODO

	public ObjectProperty<Double> haberProperty() {
		return this.haber;
	}//FIN METODO

	//METODO DE ACCESO A XAPLICAR
	public void setXAplicar(Double xaplicar) {
		this.xaplicar.set(xaplicar);
	}//FIN METODO

	public Double getXAplicar() {
		return this.xaplicar.get();
	}//FIN METODO

	public ObjectProperty<Double> xAplicarProperty() {
		return this.xaplicar;
	}//FIN METODO

	//METODO DE ACCESO A PAGOS
	public void setPagos(Double pagos) {
		this.pagos.set(pagos);
	}//FIN METODO

	public Double getPagos() {
		return this.pagos.get();
	}//FIN METODO

	public ObjectProperty<Double> pagosProperty() {
		return this.pagos;
	}//FIN METODO

	//METODO DE ACCESO A BONIFICACIONES
	public void setBonificaciones(Double bonificaciones) {
		this.bonificaciones.set(bonificaciones);
	}//FIN METODO

	public Double getBonificaciones() {
		return this.bonificaciones.get();
	}//FIN METODO

	public ObjectProperty<Double> bonificacionesProperty() {
		return this.bonificaciones;
	}//FIN METODO

	//METODOS DE ACCESO A CLIENTEFK
	public void setClienteFK(int clienteFK) {
		this.clienteFK.set(clienteFK);
	}//FIN METODO

	public int getClienteFK() {
		return this.clienteFK.get();
	}//FIN METODO

	public ObjectProperty<Integer> clienteFKProperty() {
		return this.clienteFK;
	}//FIN METODO

	//METODOS DE ACCESO A COTIZACIONFK
	public void setCotizacionFK(int cotizacionFK) {
		this.cotizacionFK.set(cotizacionFK);
	}//FIN METODO

	public int getCotizacionFK() {
		return this.cotizacionFK.get();
	}//FIN METODO

	public ObjectProperty<Integer> cotizacionFKProperty() {
		return this.cotizacionFK;
	}//FIN METODO

	//METODOS DE ACCESO A RECIBOFK
	public void setReciboFK(int reciboFK) {
		this.reciboFK.set(reciboFK);
	}//FIN METODO

	public int getReciboFK() {
		return this.reciboFK.get();
	}//FIN METODO

	public ObjectProperty<Integer> reciboFKProperty() {
		return this.reciboFK;
	}//FIN METODO

	//METODO DE ACCESO A SALDO
	public void setSaldo(Double saldo) {
		if(saldo >= 0)
			this.saldo.set(saldo);
		else
			this.saldo.set(0.0);
	}//FIN METODO

	public Double getSaldo() {
		return this.saldo.get();
	}//FIN METODO

	public ObjectProperty<Double> saldoProperty() {
		return this.saldo;
	}//FIN METODO

	//METODO DE ACCESO A IMPORTE A APLICAR
	public void setImporteAplicar(String importeAplicar) {
		if(importeAplicar.equals(""))
			this.importeAplicar.set("0.0");
		else
			this.importeAplicar.set(importeAplicar);
	}//FIN METODO

	public String getImporteAplicar() {
		return this.importeAplicar.get();
	}//FIN METODO

	public StringProperty importeAplicarProperty() {
		return this.importeAplicar;
	}//FIN METODO
}//FIN CLASE

