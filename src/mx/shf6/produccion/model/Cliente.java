package mx.shf6.produccion.model;

import java.sql.Connection;
import java.sql.Date;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import mx.shf6.produccion.model.dao.DomicilioDAO;

public class Cliente {

	//PROPIEDADES
	private ObjectProperty<Integer> sysPK;
	private StringProperty codigo;
	private StringProperty nombre;
	private ObjectProperty<Integer> status;
	private ObjectProperty<Date> fechaRegistro;
	private StringProperty registroContribuyente;
	private StringProperty telefono;
	private StringProperty correo;
	private StringProperty rutaCarpeta;
	private ObjectProperty<Integer> domicilioFK;
	
	//CONSTANTES PARA "STATUS"
	public static final int BLOQUEADO = 0;
	public static final int ACTIVO = 1;
	public static final int BAJA = 2;
	public static final String FOLIO = "CL";
	
	//CONSTRUCTOR VACIO
	public Cliente () {
		this(0,"","",0,null,"","","","",0);
	}//FIN CONSTRUCTOR
	
	public Cliente (Integer sysPK, String codigo, String nombre, int status, Date fechaRegistro, String registroContribuyente, String telefono, String correo, String rutaCarpeta, int domicilioFK) {
		this.sysPK = new SimpleObjectProperty<Integer>(sysPK);
		this.codigo = new SimpleStringProperty(codigo);
		this.nombre = new SimpleStringProperty(nombre);
		this.status = new SimpleObjectProperty<Integer>(status);
		this.fechaRegistro = new SimpleObjectProperty<Date>(fechaRegistro);
		this.registroContribuyente = new SimpleStringProperty(registroContribuyente);
		this.telefono = new SimpleStringProperty(telefono);
		this.correo = new SimpleStringProperty(correo);
		this.rutaCarpeta = new SimpleStringProperty(rutaCarpeta);
		this.domicilioFK = new SimpleObjectProperty<Integer>(domicilioFK);
	}//FIN CONSTRUCTOR
	
	//METODOS PARA ACCESO A "SYSPK"
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
	
	//METODOS DE ACCESO A "CODIGO"
	public void setCodigo(String codigo) {
		this.codigo.set(codigo);
	}//FIN METODO
	
	public String getCodigo() {
		return this.codigo.get();
	}//FIN METODO
	
	public StringProperty codigoProperty() {
		return this.codigo;
	}//FIN METODO
	//FIN METODOS "CODIGO"
	
	//METODOS DE ACCESO A "NOMBRE"
	public void setNombre(String nombre) {
		this.nombre.set(nombre);
	}//FIN METODO
	
	public String getNombre() {
		return this.nombre.get();
	}//FIN METODO
	
	public StringProperty nombreProperty() {
		return this.nombre;
	}//FIN METODO
	//FIN METODOS "NOMBRE"
	
	//METODOS DE ACCESO A "STATUS"
	public void setStatus(Integer status) {
		this.status.set(status);
	}//FIN METODO
	
	public Integer getStatus() {
		return this.status.get();
	}//FIN METODO
	
	public String getDescripcionStatus() {
		switch (this.getStatus()) {
		case 0:
			return "Bloqueado";
		case 1:
			return "Activo";
		case 2:
			return "Baja";
		}//FIN WTITCH
		return "";
	}//FIN METODO
	
	public void setNumeroStatus(String status) {
		switch (status) {
		case "Bloqueado":
			this.setStatus(0);
			break;
		case "Activo":
			this.setStatus(1);
			break;
		case "Baja":
			this.setStatus(2);
			break;
		}//FIN WTITCH
	}//FIN METODO
	//FIN METODOS "STATUS"
	
	//METODO DE ACCESO A "FECHA REGISTRO"
	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro.set(fechaRegistro);
	}//FIN METODO
	
	public Date getFechaRegistro() {
		return this.fechaRegistro.get();
	}//FIN METODO
	
	public ObjectProperty<Date> fechaRegistroProperty(){
		return this.fechaRegistro;
	}//FIN METODO
	//FIN METODOS "FECHA REGISTRO"
	
	//METODOS DE ACCESO A "REGISTRO CONTRIBUYENTE"
	public void setRegistroContribuyente(String registroContribuyente) {
		this.registroContribuyente.set(registroContribuyente);
	}//FIN METODO
	
	public String getRegistroContribuyente() {
		return this.registroContribuyente.get();
	}//FIN METODO
	
	public StringProperty registroContribuyenteProperty(){
		return this.registroContribuyente;
	}//FIN METODO
	//FIN METODOS "REGISTRO CONTRIBUYENTE"
	
	//METODOS DE ACCESO A "TELEFONO"
	public void setTelefono(String telefono) {
		this.telefono.set(telefono);
	}//FIN METODO
	
	public String getTelefono() {
		return this.telefono.get();
	}//FIN METODO
	
	public StringProperty telefonoProperty() {
		return this.telefono;
	}//FIN METODO
	//FIN METODOS "TELEFONO"
	
	//METODOS DE ACCESO A "CORREO"
	public void setCorreo(String correo) {
		this.correo.set(correo);
	}//FIN METODO
	
	public String getCorreo() {
		return this.correo.get();
	}//FIN METODO
	
	public StringProperty correoProperty() {
		return this.correo;
	}//FIN METODO
	//FIN METODOS "CORREO"
	
	//METODOS DE ACCESO A "RUTA CARPETA"
	public void setRutaCarpeta(String rutaCarpeta) {
		this.rutaCarpeta.set(rutaCarpeta);
	}//FIN METODO
	
	public String getRutaCarpeta() {
		return this.rutaCarpeta.get();
	}//FIN METODO
	
	public StringProperty rutaCarpetaProperty() {
		return this.rutaCarpeta;
	}//FIN METODO
	//FIN METODOS "RUTA CARPETA"
	
	//METODOS PARA ACCESO A "DOMICILIO"
	public void setDomicilioFK(Integer domicilioFk) {
		this.domicilioFK.set(domicilioFk);
	}//FIN METODO
	
	public Integer getDomicilioFK() {
		return this.domicilioFK.get();
	}//FIN METODO
	
	public Domicilio getDomicilio(Connection connection) {
		return DomicilioDAO.readDomicilio(connection, this.getDomicilioFK());
	}//FIN METODO
	//FIN METODOS "DOMICILIO"

	@Override
	public String toString() {
		return this.getNombre();
	}//FIN METODO
}//FIN METODO
