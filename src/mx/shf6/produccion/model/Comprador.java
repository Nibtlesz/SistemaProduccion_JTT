package mx.shf6.produccion.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Comprador {
	
	//PROPIEDADES
	private ObjectProperty<Integer> sysPK;
	private StringProperty nombre;
	private StringProperty correo;
	private StringProperty telefono;
	private StringProperty telefonoAuxiliar;
	private StringProperty areaDepartamento;
	private ObjectProperty<Integer> clienteFK;
	
	//CONSTRUCTOR VACIO
	public Comprador() {
		this(0, "", "", "", "", "", 0);
	}//FIN METODO
	
	//CONSTRUCTOR LLENO
	public Comprador(Integer sysPK, String nombre, String correo, String telefono, String telefonoAuxiliar, String areaDepartamento, Integer clienteFK) {
		this.sysPK = new SimpleObjectProperty<Integer>(sysPK);
		this.nombre = new SimpleStringProperty(nombre);
		this.correo = new SimpleStringProperty(correo);
		this.telefono = new SimpleStringProperty(telefono);
		this.telefonoAuxiliar = new SimpleStringProperty(telefonoAuxiliar);
		this.areaDepartamento = new SimpleStringProperty(areaDepartamento);
		this.clienteFK = new SimpleObjectProperty<Integer>(clienteFK);
	}//FIN METODO
	
	//METODOS DE ACCESO A SYSPK
	public void setSysPK(Integer SysPK) {
		this.sysPK.set(SysPK);
	}//FIN METODO
	
	public Integer getSysPK() {
		return this.sysPK.get();
	}//FIN METODO
	
	public ObjectProperty<Integer> sysPKProperty() {
		return this.sysPK;
	}//FIN METODO
	//FIN METODOS DE ACCESO A SYSPK
	
	//METODOS DE ACCESO A NOMBRE
	public void setNombre(String nombre) {
		this.nombre.set(nombre);
	}//FIN METODO
	
	public String getNombre() {
		return this.nombre.get();
	}//FIN METODO
	
	public StringProperty nombreProperty() {
		return this.nombre;
	}//FIN METODO
	//FIN METODOS DE ACCESO A NOMBRE
	
	//METODOS DE ACCESO A CORREO
	public void setCorreo(String correo) {
		this.correo.set(correo);
	}//FIN METODO
	
	public String getCorreo() {
		return this.correo.get();
	}//FIN METODO
	
	public StringProperty correoProperty () {
		return this.correo;
	}//FIN METODO
	//FIN METODOS DE ACCESO A CORREO
	
	//METODOS DE ACCESO A TELEFONO
	public void setTelefono(String telefono) {
		this.telefono.set(telefono);
	}//FIN METODO
	
	public String getTelefono() {
		return this.telefono.get();
	}//FIN METODO
	
	public StringProperty telefonoProperty() {
		return this.telefono;
	}//FIN METODO
	//FIN METODOS DE ACCESO A TELEFONO
	
	//METODOS DE ACCESO A TELEFONOAUXILIAR
	public void setTelefonoAuxiliar(String telefonoAuxiliar) {
		this.telefonoAuxiliar.set(telefonoAuxiliar);
	}//FIN METODO
	
	public String getTelefonoAuxiliar() {
		return this.telefonoAuxiliar.get();
	}//FIN METODO
	
	public StringProperty telefonoAuxiliarProperty() {
		return this.telefonoAuxiliar;
	}//FIN METODO
	//FIN METODOS DE ACCESO A TELEFONOAUXILIAR
	
	//METODOS DE ACCESO A AREADEPARTAMENTO
	public void setAreaDepartamento(String areaDepartamento) {
		this.areaDepartamento.set(areaDepartamento);
	}//FIN METODO
	
	public String getAreaDepartamento() {
		return this.areaDepartamento.get();
	}//FIN METODO
	
	public StringProperty areaDepartamentoProperty() {
		return this.areaDepartamento;
	}//FIN METODO
	//FIN METODOS DE ACCESO A AREADEPARTAMENTO
	
	//METODOS DE ACCESO A CLIENTESFK
	public void setClienteFK(Integer clienteFK) {
		this.clienteFK.set(clienteFK);
	}//FIN METODO
	
	public Integer getClienteFK() {
		return this.clienteFK.get();
	}//FIN METODO
	
	public ObjectProperty<Integer> clienteFKProperty() {
		return this.clienteFK;
	}//FIN METODO
	//FIN METODOS DE ACCESO A CLIENTESFK
}//FIN CLASE