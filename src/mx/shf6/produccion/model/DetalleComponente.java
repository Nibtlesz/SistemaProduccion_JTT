package mx.shf6.produccion.model;

import java.sql.Connection;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import mx.shf6.produccion.model.dao.ComponenteDAO;

public class DetalleComponente {

	private ObjectProperty<Integer> sysPK;
	private ObjectProperty<Integer> componenteSuperiorFK;
	private ObjectProperty<Integer> componenteInferiorFK;
	private ObjectProperty<Integer> cantidad;
	private StringProperty notas;
	private StringProperty descripcionComponenteInferior;
	private StringProperty numeroParteComponenteInferior;
	private StringProperty numeroDescripcionComponenteIferior;
	private StringProperty descripcionComponenteSuperior;
	private StringProperty numeroParteComponenteSuperior;
	private StringProperty tipoComponenteInferior;
	private StringProperty tipoComponenteSuperior;



	public DetalleComponente() {
		this(0, 0, 0, 0, "", "", "", "", "", "", "", "");
	}//FIN CONSTRUCTOR

	public DetalleComponente(int sysPK, int componenteSuperiorFK, int componenteInferiorFK, Integer cantidad, String notas, String descripcionComponenteInferior, String numeroParteComponenteInferior, String numeroDescripcionComponenteIferior, String descripcionComponenteSuperior, String numeroParteComponenteSuperior, String tipoComponenteInferior, String tipoComponenteSuperior) {
		this.sysPK = new SimpleObjectProperty<Integer>(sysPK);
		this.componenteSuperiorFK = new SimpleObjectProperty<Integer>(componenteSuperiorFK);
		this.componenteInferiorFK = new SimpleObjectProperty<Integer>(componenteInferiorFK);
		this.cantidad = new SimpleObjectProperty<Integer>(cantidad);
		this.notas = new SimpleStringProperty(notas);
		this.descripcionComponenteInferior = new SimpleStringProperty(descripcionComponenteInferior);
		this.numeroParteComponenteInferior = new SimpleStringProperty(numeroParteComponenteInferior);
		this.numeroDescripcionComponenteIferior = new SimpleStringProperty(numeroDescripcionComponenteIferior);
		this.descripcionComponenteSuperior = new SimpleStringProperty(descripcionComponenteSuperior);
		this.numeroParteComponenteSuperior = new SimpleStringProperty(numeroParteComponenteSuperior);
		this.tipoComponenteInferior = new SimpleStringProperty(tipoComponenteInferior);
		this.tipoComponenteSuperior = new SimpleStringProperty(tipoComponenteSuperior);
	}//FIN CONSTRUCTOR

	public void setSysPK(int sysPK) {
		this.sysPK.set(sysPK);
	}//FIN METODO

	public int getSysPK() {
		return this.sysPK.get();
	}//FIN METODO

	public ObjectProperty<Integer> sysPKProperty() {
		return this.sysPK;
	}//FIN METODO

	public void setComponenteSuperiorFK(int componenteSuperiorFK) {
		this.componenteSuperiorFK.set(componenteSuperiorFK);
	}//FIN METODO

	public int getComponenteSuperiorFK() {
		return this.componenteSuperiorFK.get();
	}//FIN METODO

	public ObjectProperty<Integer> componenteSuperiorFKProperty() {
		return this.componenteSuperiorFK;
	}//FIN METODO

	public Componente getComponenteSuperior(Connection connection) {
		return ComponenteDAO.readComponente(connection, this.getComponenteSuperiorFK());
	}//FIN METODO

	public void setComponenteInferiorFK(int componenteInferiorFK) {
		this.componenteInferiorFK.set(componenteInferiorFK);
	}//FIN METODO

	public int getComponenteInferiorFK() {
		return this.componenteInferiorFK.get();
	}//FIN METODO

	public ObjectProperty<Integer> componenteInferiorFKProperty() {
		return this.componenteInferiorFK;
	}//FIN METODO

	public Componente getComponenteInferior(Connection connection) {
		return ComponenteDAO.readComponente(connection, this.getComponenteInferiorFK());
	}//FIN METODO

	public void setCantidad(Integer cantidad) {
		this.cantidad.set(cantidad);
	}//FIN METODO

	public Integer getCantidad()	{
		return this.cantidad.get();
	}//FIN METODO

	public ObjectProperty<Integer> cantidadProperty() {
		return this.cantidad;
	}//FIN METODO

	public void setNotas(String notas) {
		this.notas.set(notas);
	}//FIN METODO

	public String getNotas() {
		return this.notas.get();
	}//FIN METODO

	public StringProperty notasProperty() {
		return this.notas;
	}//FIN METODO

	public void setDescripcionComponenteInferior(String descripcionComponenteInferior) {
		this.descripcionComponenteInferior.set(descripcionComponenteInferior);
	}//FIN METODO

	public String getDescripcionComponenteInferior() {
		return this.descripcionComponenteInferior.get();
	}//FIN METODO

	public StringProperty descripcionComponenteInferiorProperty() {
		return this.descripcionComponenteInferior;
	}//FIN METODO

	public void setNumeroParteComponenteInferior(String numeroParteComponenteInferior) {
		this.numeroParteComponenteInferior.set(numeroParteComponenteInferior);
	}//FIN METODO

	public String getNumeroParteComponenteInferior() {
		return this.numeroParteComponenteInferior.get();
	}//FIN METODO

	public StringProperty numeroParteComponenteInferiorProperty() {
		return this.numeroParteComponenteInferior;
	}//FIN METODO

	public void setNumeroDescripcionComponenteIferior() {
		this.numeroDescripcionComponenteIferior.set(getNumeroParteComponenteInferior() +" "+getDescripcionComponenteInferior());
	}//FIN METODO

	public String getNumeroDescripcionComponenteIferior(){
		return this.numeroDescripcionComponenteIferior.get();
	}

	public StringProperty numeroDescripcionComponenteIferiorProperty() {
		return this.numeroDescripcionComponenteIferior;
	}//FIN METODO

	public void setDescripcionComponenteSuperior(String descripcionComponenteSuperior) {
		this.descripcionComponenteSuperior.set(descripcionComponenteSuperior);
	}//FIN METODO

	public String getDescripcionComponenteSuperior() {
		return this.descripcionComponenteSuperior.get();
	}//FIN METODO

	public StringProperty descripcionComponenteSuperiorProperty() {
		return this.descripcionComponenteSuperior;
	}//FIN METODO

	public void setNumeroParteComponenteSuperior(String numeroParteComponenteSuperior) {
		this.numeroParteComponenteSuperior.set(numeroParteComponenteSuperior);
	}//FIN METODO

	public String getNumeroParteComponenteSuperior() {
		return this.numeroParteComponenteSuperior.get();
	}//FIN METODO

	public StringProperty numeroParteComponenteSuperiorProperty() {
		return this.numeroParteComponenteSuperior;
	}//FIN METODO

	public void setTipoComponenteInferior(String tipoComponenteInferior) {
		this.tipoComponenteInferior.set(TipoComponente.toString(tipoComponenteInferior));
	}//FIN METODO

	public String getTipoComponenteInferior() {
		return this.tipoComponenteInferior.get();
	}//FIN METODO

	public StringProperty tipoComponenteInferiorProperty() {
		return this.tipoComponenteInferior;
	}//FIN METODO

	public void setTipoComponenteSuperior(String tipoComponenteSuperior) {
		this.tipoComponenteSuperior.set(TipoComponente.toString(tipoComponenteSuperior));
	}//FIN METODO

	public String getTipoComponenteSuperior() {
		return this.tipoComponenteSuperior.get();
	}//FIN METODO

	public StringProperty tipoComponenteSuperiorProperty() {
		return this.tipoComponenteSuperior;
	}//FIN METODO

}//FIN CLASE
