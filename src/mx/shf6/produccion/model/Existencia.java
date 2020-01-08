package mx.shf6.produccion.model;

import java.sql.Connection;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import mx.shf6.produccion.model.dao.AlmacenDAO;
import mx.shf6.produccion.model.dao.ComponenteDAO;

public class Existencia {

	// PROPIEDADES
	private ObjectProperty<Integer> sysPk;
	private ObjectProperty<Double> existencia;
	private ObjectProperty<Integer> componenteFK;
	private ObjectProperty<Integer> almacenFK;
	private StringProperty numeroComponente;
	private StringProperty descripcionComponente;
	private StringProperty codigoAlmacen;
	private StringProperty descripcionAlmacen;

	// CONSTRUCTOR SIN PARAMETROS
	public Existencia() {
		this(0, 0.0, 0, 0, "", "", "", "");
	}

	// CONSTRUCTOR CON PARAMETROS
	public Existencia(Integer sysPK, Double existencia, Integer componenteFK, Integer almacenFK,
			String numeroComponente, String descripcionComponente, String codigoAlmacen, String descripcionAlmacen) {
		this.sysPk = new SimpleObjectProperty<Integer>(sysPK);
		this.existencia = new SimpleObjectProperty<Double>(existencia);
		this.componenteFK = new SimpleObjectProperty<Integer>(componenteFK);
		this.almacenFK = new SimpleObjectProperty<Integer>(almacenFK);
		this.numeroComponente = new SimpleStringProperty(numeroComponente);
		this.descripcionComponente = new SimpleStringProperty(descripcionComponente);
		this.codigoAlmacen = new SimpleStringProperty(codigoAlmacen);
		this.descripcionAlmacen = new SimpleStringProperty(descripcionAlmacen);
	}

	// METODOS DE ACCESO A "SYSPK"
	public void setSysPK(Integer sysPK) {
		this.sysPk.set(sysPK);
	}// FIN METODO

	public Integer getSysPk() {
		return this.sysPk.get();
	}// FIN METODO

	public ObjectProperty<Integer> sysPKProperty() {
		return this.sysPk;
	}// FIN METODO

	// METODOS DE ACCESO A "EXISTENCIA"
	public void setExistencia(Double existencia) {
		this.existencia.set(existencia);
	}// FIN METODO

	public Double getExistencia() {
		return this.existencia.get();
	}// FIN METODO

	public ObjectProperty<Double> existenciaProperty() {
		return this.existencia;
	}// FIN METODO

	// METODOS DE ACCESO A "COMPONENTEFK"
	public void setComponenteFK(Integer componenteFK) {
		this.componenteFK.set(componenteFK);
	}// FIN METODO

	public Integer getComponenteFK() {
		return this.componenteFK.get();
	}// FIN METODO

	public ObjectProperty<Integer> componenteFKProperty() {
		return this.componenteFK;
	}// FIN METODO

	public Componente getComponente(Connection connection) {
		return ComponenteDAO.readComponente(connection, this.getComponenteFK());
	}// FIN METODO

	// METODO DE ACCESO A "ALMACENFK"
	public void setAlmacenFK(Integer almacenFK) {
		this.almacenFK.set(almacenFK);
	}// FIN METODO

	public Integer getAlmacenFK() {
		return this.almacenFK.get();
	}// FIN METODO

	public ObjectProperty<Integer> almacenFKProperty() {
		return this.almacenFK;
	}// FIN METODO

	public Almacen getAlmacen(Connection connection) {
		return AlmacenDAO.readPorSysPK(connection, this.getAlmacenFK());
	}// FIN METODO

	// METODO DE ACCESO A "NUMEROCOMPONENTE"
	public void setNumeroComponente(String numeroComponente) {
		this.numeroComponente.set(numeroComponente);
	}// FIN METODO

	public String getNumeroComponente() {
		return this.numeroComponente.get();
	}// FIN METODO

	public StringProperty numeroComponenteProperty() {
		return this.numeroComponente;
	}// FIN METODO

	// METODOS DE ACCESO A "DESCRIPCIONCOMPONENTE"
	public void setDescripcionComponente(String descripcionComponente) {
		this.descripcionComponente.set(descripcionComponente);
	}// FIN METODO

	public String getDescripcionComponente() {
		return this.descripcionComponente.get();
	}// FIN METODO

	public StringProperty descripcionComponenteProperty() {
		return this.descripcionComponente;
	}// FIN METODO

	// METODOS DE ACCESO A "CODIGOALMACEN"
	public void setCodigoAlmacen(String codigoAlmacen) {
		this.codigoAlmacen.set(codigoAlmacen);
	}// FIN METODO

	public String getCodigoAlmacen() {
		return this.codigoAlmacen.get();
	}// FIN METODO

	public StringProperty codigoAlmacenProperty() {
		return this.codigoAlmacen;
	}// FIN METODO

	// METODOS DE ACCESO A "DESCRIPCIONALMACEN"
	public void setDescripcionAlmacen(String descripcionAlmacen) {
		this.descripcionAlmacen.set(descripcionAlmacen);
	}// FIN METODO

	public String getDescripcionAlmacen() {
		return this.descripcionAlmacen.get();
	}// FIN METODO

	public StringProperty descripcionAlmacenProperty() {
		return this.descripcionAlmacen;
	}// FIN METODO

}//FIN CLASE
