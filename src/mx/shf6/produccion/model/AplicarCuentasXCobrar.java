package mx.shf6.produccion.model;

import java.sql.Date;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class AplicarCuentasXCobrar {

	//PROPIEDADES
	private ObjectProperty<Integer> sysPK;
	private ObjectProperty<Integer> dcxc;
	private ObjectProperty<Integer> aplicadoA;
	private ObjectProperty<Double> importe;
	private ObjectProperty<Date> fecha;

	//CONSTRUCTOR VACIO
	public AplicarCuentasXCobrar() {
		this(0, 0, 0, 0.0, new Date(System.currentTimeMillis()));
	}

	//CONSTRUCTOR CON PARAMETROS
	public AplicarCuentasXCobrar(int sysPK, int dcxcFK, int aplicadoA, Double importe, Date fecha ){
		this.sysPK = new SimpleObjectProperty<Integer>(sysPK);
		this.dcxc = new SimpleObjectProperty<Integer>(dcxcFK);
		this.aplicadoA = new SimpleObjectProperty<Integer>(aplicadoA);
		this.importe = new SimpleObjectProperty<Double>(importe);
		this.fecha = new SimpleObjectProperty<Date>(fecha);
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

	//METODOS DE ACCESO A DCXCFK
	public void setDcxcFK(int dcxc) {
		this.dcxc.set(dcxc);
	}//FIN METODO

	public int getDcxcFK() {
		return this.dcxc.get();
	}//FIN METODO

	public ObjectProperty<Integer> dcxcFKProperty() {
		return this.dcxc;
	}//FIN METODO

	//METODOS DE ACCESO A APLICADOA
	public void setAplicadoA(int aplicadoA) {
		this.aplicadoA.set(aplicadoA);
	}//FIN METODO

	public int getAplicadoA() {
		return this.aplicadoA.get();
	}//FIN METODO

	public ObjectProperty<Integer> aplicadoAProperty() {
		return this.aplicadoA;
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

	//METODOS DE ACCESO A FECHA
	public void setFecha(Date fecha) {
		this.fecha.set(fecha);
	}//FIN METODO

	public Date getFecha() {
		return this.fecha.get();
	}//FIN METODO

	public ObjectProperty<Date> fechaProperty() {
		return this.fecha;
	}//FIN METODO
}//FIN CLASE
