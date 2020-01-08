package mx.shf6.produccion.model;

import java.sql.Connection;
import java.sql.Date;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import mx.shf6.produccion.model.dao.AlmacenDAO;
import mx.shf6.produccion.model.dao.CardexDAO;
import mx.shf6.produccion.model.dao.ComponenteDAO;

public class DetalleCardex {

	//PROPIEDADES
	private ObjectProperty<Integer> sysPK;
	private ObjectProperty<Double> entrada;
	private ObjectProperty<Double> salida;
	private ObjectProperty<Integer> cardexFK;
	private ObjectProperty<Integer> almacenFK;
	private ObjectProperty<Integer> componenteFK;
	private ObjectProperty<Double> existenciaComponente;
	private ObjectProperty<Date> fecha;

	//PROPIEDADES COMPONENTEFK
	private StringProperty noPartesComponente;
	private StringProperty descripcionComponente;

	//PROPIEDADES ALMACENFK
	private StringProperty codigoAlmacen;
	private StringProperty descripcionAlmacen;

	//CONSTRUCTOR VACIO
	public DetalleCardex() {
		this (0, 0.0, 0.0, 0, 0, 0, new Date(System.currentTimeMillis()), "", "", "", "");
	}//FIN CONSTRUCTOR

	//CONSTRUCTOR
	public DetalleCardex(Integer sysPK, double entrada, double salida, Integer cardexFK, Integer almacenFK, Integer componenteFK, Date fecha, String noPartesComponente, String descripcionComponente, String codigoAlmacen, String descripcionAlmacen) {
		this.sysPK = new SimpleObjectProperty<Integer> (sysPK);
		this.entrada = new SimpleObjectProperty<Double> (entrada);
		this.salida = new SimpleObjectProperty<Double> (salida);
		this.cardexFK = new SimpleObjectProperty<Integer> (cardexFK);
		this.almacenFK = new SimpleObjectProperty<Integer> (almacenFK);
		this.componenteFK = new SimpleObjectProperty<Integer> (componenteFK);
		this.existenciaComponente = new SimpleObjectProperty<Double>(0.0);
		this.fecha = new SimpleObjectProperty<Date>(fecha);
		this.noPartesComponente = new SimpleStringProperty(noPartesComponente);
		this.descripcionComponente = new SimpleStringProperty(descripcionComponente);
		this.codigoAlmacen = new SimpleStringProperty(codigoAlmacen);
		this.descripcionAlmacen = new SimpleStringProperty(descripcionAlmacen);
	}//FIN CONSTRUCTOR

	//METODOS PARA ACCEDER AL SYSPK
	public void setSysPK (Integer sysPK) {
		this.sysPK.set(sysPK);
	}//FIN METODO

	public Integer getSysPK() {
		return this.sysPK.get();
	}//FIN METODO

	public ObjectProperty<Integer> sysPKProperty(){
		return this.sysPK;
	}//FIN METODO

	//METODOS PARA ACCEDER A ENTRADA
	public void setEntrada (Double entrada) {
		this.entrada.set(entrada);
	}//FIN METODO

	public Double getEntrada () {
		return this.entrada.get();
	}//FIN METODO

	public ObjectProperty<Double> entradaProperty(){
		return this.entrada;
	}//FIN METODO

	//METODOS PARA ACCEDER A SALIDA
	public void setSalida(Double salida) {
		this.salida.set(salida);
	}//FIN METODO

	public Double getSalida () {
		return this.salida.get();
	}//FIN METODO

	public ObjectProperty<Double> salidaProperty(){
		return this.salida;
	}//FIN METODO

	//METODOS PARA ACCEDER A CARDEXFK
	public void setCardexFK (Integer cardexFK) {
		this.cardexFK.set(cardexFK);
	}//FIN METODO

	public Integer getCardexFK() {
		return this.cardexFK.get();
	}//FIN METODO

	public ObjectProperty<Integer> cardexFKProperty(){
		return this.cardexFK;
	}//FIN METODO

	public Cardex getCardex(Connection connection) {
		return CardexDAO.readPorSysPK(connection, this.getCardexFK());
	}//FIN METODO

	//METODOS PARA ACCEDER A ALMACENFK
	public void setAlmacenFK(Integer almacenFK) {
		this.almacenFK.set(almacenFK);
	}//FIN METODO

	public Integer getAlmacenFK() {
		return this.almacenFK.get();
	}//FIN METODO

	public ObjectProperty<Integer> almacenFKProperty(){
		return this.almacenFK;
	}//FIN METODO

	public Almacen getAlmacen(Connection connection) {
		return AlmacenDAO.readPorSysPK(connection, this.getAlmacenFK());
	}//FIN METODO

	//METODOS PARA ACCEDER A COMPONENTEFK
	public void setComponenteFK(Integer componenteFK) {
		this.componenteFK.set(componenteFK);
	}//FIN METODO

	public Integer getComponenteFK() {
		return this.componenteFK.get();
	}//FIN METODO

	public ObjectProperty<Integer> componenteFKProperty(){
		return this.componenteFK;
	}//FIN METODO

	public Componente getComponente (Connection connection) {
		return ComponenteDAO.readComponente(connection, this.getAlmacenFK());
	}//FIN METODO

	//METODOS PARA ACCEDER A FECHA
	public void setFecha(Date fecha) {
		this.fecha.set(fecha);
	}//FIN METODO

	public Date getFecha() {
		return this.fecha.get();
	}//FIN METODO

	public ObjectProperty<Date> fechaProperty(){
		return this.fecha;
	}//FIN METODO

	//METODOS PARA ACCEDER A NOPARTESCOMPONENTE
	public void setNoPartesComponente(String noPartesComponentes) {
		this.noPartesComponente.set(noPartesComponentes);
	}//FIN METODO

	public String getNoPartesComponente() {
		return this.noPartesComponente.get();
	}//FIN METODO

	public StringProperty noPartesComponenteProperty() {
		return this.noPartesComponente;
	}//FIN METODO

	//METODOS PARA ACCEDER A DESCRIPCIONCOMPONENTE
	public void setDescripcionComponente (String descripcionComponente) {
		this.descripcionComponente.set(descripcionComponente);
	}//FIN METODO

	public String getDescripcionComponente() {
		return this.descripcionComponente.get();
	}//FIN METODO

	public StringProperty descripcionComponenteProperty() {
		return this.descripcionComponente;
	}//FIN METODO

	//METODOS PARA ACCEDER A CODIGOALMACEN
	public void setCodigoAlmacen(String codigoAlmacen) {
		this.codigoAlmacen.set(codigoAlmacen);
	}//FIN METODO

	public String getCodigoAlmacen() {
		return this.codigoAlmacen.get();
	}//FIN METODO

	public StringProperty codigoAlmacenProperty() {
		return this.codigoAlmacen;
	}//FIN METODO

	//METODO PARA ACCEDER A DESCRIPCIONALMACEN
	public void setDescripcionAlmacen(String descripcionAlmacen) {
		this.descripcionAlmacen.set(descripcionAlmacen);
	}//FIN METODO

	public String getDescripcionAlmacen() {
		return this.descripcionAlmacen.get();
	}//FIN METODO

	public StringProperty descripcionAlmacenProperty() {
		return this.descripcionAlmacen;
	}//FIN METODO

	//METODOS PARA ACCEDER A EXISTENCIACOMPONENTE
	public void setExistenciaComponente(double existenciaComponente){
		this.existenciaComponente.set(existenciaComponente);
	}//FIN METODO

	public double getExistenciaComponente() {
		return this.existenciaComponente.get();
	}//FIN METODO

	public ObjectProperty<Double> existenciaComponenteProperty(){
		return this.existenciaComponente;
	}//FIN METODO

	//METODOS PARA ACCEDER A NUEVA EXISTENCIA
	public Double getNuevaExistencia() {
		Double nuevaExistencia = 0.0;
		if (this.getEntrada() > 0) {
			nuevaExistencia = this.getExistenciaComponente() + this.getEntrada();
		}else {
			nuevaExistencia = this.getExistenciaComponente() - this.getSalida();
		}
		return nuevaExistencia;
	}//FIN METODO

	public ObjectProperty<Double> nuevaExistenciaProperty(){
		return new SimpleObjectProperty<Double>(this.getNuevaExistencia());
	}//FIN METODO
}//FIN CLASE
