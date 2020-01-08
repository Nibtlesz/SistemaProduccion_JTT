package mx.shf6.produccion.model;

import java.sql.Connection;
import java.sql.Date;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import mx.shf6.produccion.model.dao.CategoriaDAO;

public class Cardex {
	
	//PROPIEDADES
	private ObjectProperty<Integer> sysPK;
	private ObjectProperty<Date> fecha;
	private StringProperty referencia;
	private StringProperty nota;
	private ObjectProperty<Integer> tipo;
	private ObjectProperty<Integer> categoriaFK;
	
	//VARIABLES
	
	//CONSTANTES
	public static final int ENTRADA = 0;
	public static final int SALIDA = 1;
	public static final int TRASPASOS = 2;
	
	//CONSTRUCTOR VACIO
	public Cardex() {
		this (0, new Date(System.currentTimeMillis()), "", "", -1, 0);
	}//FIN CONSTRUCTOR

	//CONSTRUCTOR
	public Cardex(Integer sysPK, Date fecha, String referencia, String nota, Integer tipo, Integer categoriaFK) {
		this.sysPK = new SimpleObjectProperty<Integer>(sysPK);
		this.fecha = new SimpleObjectProperty<Date> (fecha);
		this.referencia = new SimpleStringProperty(referencia);
		this.nota = new SimpleStringProperty(nota);
		this.tipo = new SimpleObjectProperty<Integer>(tipo);
		this.categoriaFK = new SimpleObjectProperty<Integer>(categoriaFK);
	}//FIN CONSTRUCTOR
	
	//METODOS PARA ACCEDER A SYSPK
	public void setSysPK (Integer sysPK) {
		this.sysPK.set(sysPK);
	}//FIN METODO
	
	public Integer getSysPK() {
		return this.sysPK.get();
	}//FIN METODO
	
	public ObjectProperty<Integer> sysPKProperty(){
		return this.sysPK;
	}//FIN METODO
	
	//METODOS PARA ACCEDER A FECHA
	public void setFecha(Date fecha) {
		this.fecha.set(fecha);
	}//FIN METODO
	
	public Date getFecha () {
		return this.fecha.get();
	}//FIN METODO
	
	public ObjectProperty<Date> fechaProperty(){
		return this.fecha;
	}//FIN METODO
	
	//METODOS PARA ACCEDER A REFERENCIA
	public void setReferencia(String referencia) {
		this.referencia.set(referencia);
	}//FIN METODO
	
	public String getReferencia() {
		return this.referencia.get();
	}//FIN METODO
	
	public StringProperty referencia() {
		return this.referencia;
	}//FIN METODO
	
	//METODOS PARA ACCEDER A NOTAS
	public void setNota (String nota) {
		this.nota.set(nota);
	}//FIN
	
	public String getNota() {
		return this.nota.get();
	}//FIN METODO
	
	public StringProperty notaProperty () {
		return this.nota;
	}//FIN METODO
	
	//METODOS PARA ACCEDER A TIPO
	public void setTipo (Integer tipo) {
		this.tipo.set(tipo);
	}//FIN METODO
	
	public Integer getTipo() {
		return this.tipo.get();
	}//FIN METODO
	
	public ObjectProperty<Integer> tipoProperty(){
		return this.tipo;
	}//FIN METODO
	
	public StringProperty tipoTextoProperty() {
		switch(this.getTipo()) {
			case ENTRADA:
				return new SimpleStringProperty("Entrada");
			case SALIDA: 
				return new SimpleStringProperty("Salida");
			case TRASPASOS:
				return new SimpleStringProperty("Traspasos");			
		}//FIN SWITCH
		return new SimpleStringProperty("");
	}//FIN METODO
	
	//METODOS PARA ACCEDER A CATEGORIAFK
	public void setCategoriaFK (Integer categoriaFK) {
		this.categoriaFK.set(categoriaFK);
	}//FIN DE METODO
	
	public Integer getCategoriaFK() {
		return this.categoriaFK.get();
	}//FIN METODO
	
	public ObjectProperty<Integer> categoriaFKProperty(){
		return this.categoriaFK;
	}//FIN METODO
	
	public Categoria getCategoria(Connection connection) {
		return CategoriaDAO.readPorSysPK(connection, this.getCategoriaFK());
	}//FIN METODO
}//FIN CLASE 
