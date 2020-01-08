package mx.shf6.produccion.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.Time;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import mx.shf6.produccion.model.dao.FolioDAO;

public class Recibo {

	//PROPIEDADES
	private ObjectProperty<Integer> sysPK;
	private ObjectProperty<Date> fecha;
	private ObjectProperty<Time> hora;
	private ObjectProperty<Integer> clienteFK;
	private StringProperty referencia;
	private StringProperty notas;
	private ObjectProperty<Integer> folioFK;
	private ObjectProperty<Double> importe;

	//CONTRUCTOR VACIO
	public Recibo() {
		this(0, new Date(System.currentTimeMillis()), new Time(System.currentTimeMillis()), 0, "", "", 0, 0.0);
	}//FIN CONSTRUCTOR

	//CONSTRUCTOR CON PARAMETROS
	public Recibo(int sysPK, Date fecha, Time hora, int clienteFK, String referencia, String notas, int folioFK, Double importe){
		this.sysPK = new SimpleObjectProperty<Integer>(sysPK);
		this.fecha = new SimpleObjectProperty<Date>(fecha);
		this.hora = new SimpleObjectProperty<Time>(hora);
		this.clienteFK = new SimpleObjectProperty<Integer>(clienteFK);
		this.referencia = new SimpleStringProperty(referencia);
		this.notas = new SimpleStringProperty(notas);
		this.folioFK = new SimpleObjectProperty<Integer>(folioFK);
		this.importe = new SimpleObjectProperty<Double>(importe);
	}//FIN CONSTRUCTOR

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

	//METODO DE ACCESO HORA
	public void setHora(Time hora) {
		this.hora.set(hora);
	}//FIN METODO

	public Time getHora() {
		return this.hora.get();
	}//FIN METODO

	public ObjectProperty<Time> horaProperty() {
		return this.hora;
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

	//METODOS DE ACCESO A IMPORTE
	public void setImporte(Double importe) {
		this.importe.set(importe);
	}//FIN METODO

	public Double getImporte() {
		return this.importe.get();
	}//FIN METODO

	public ObjectProperty<Double> importeProperty() {
		return this.importe;
	}//FIN METODO


}//FIN CLASE
