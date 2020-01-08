package mx.shf6.produccion.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.Time;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import mx.shf6.produccion.model.dao.UsuarioDAO;

public class Sesion {

	//PROPIEDADES
	private ObjectProperty<Integer> sysPk;
	private ObjectProperty<Date> fechaApertura;
	private ObjectProperty<Time> horaApertura;
	private ObjectProperty<Date> fechaCierre;
	private ObjectProperty<Time> horaCierre;
	private ObjectProperty<Integer> usuarioFk;
	
	//CONSTRUCTOR SIN PARAMETROS
	public Sesion() {
		this(0,null,null,null,null,0);
	}//FIN CONSTRUCTOR
	
	//CONSTRUCTOR CON PARAMETROS
	public Sesion(Integer sysPk, Date fechaApertura, Time horaApertura, Date fechaCierre, Time horaCierre, int usuarioFk) {
		this.sysPk = new SimpleObjectProperty<Integer>(sysPk);
		this.fechaApertura = new SimpleObjectProperty<Date>(fechaApertura);
		this.horaApertura = new SimpleObjectProperty<Time>(horaApertura);
		this.fechaCierre = new SimpleObjectProperty<Date>(fechaCierre);
		this.horaCierre = new SimpleObjectProperty<Time>(horaCierre);
		this.usuarioFk = new SimpleObjectProperty<Integer>(usuarioFk);
	}//FIN CONSTRUCTOR
	
	//METODOS PARA ACCESO A "SYSPK"
	public void setSysPk(Integer sysPk) {
		this.sysPk.set(sysPk);
	}//FIN METODO
	
	public Integer getSysPk() {
		return this.sysPk.get();
	}//FIN METODO
	
	public ObjectProperty<Integer> sysPkProperty() {
		return this.sysPk;
	}//FIN METODOD
	//FIN METODOS "SYSPK"
	
	//METODOS PARA ACCESO A "FECHA APERTURA"
	public void setFechaApertura(Date fechaApertura) {
		this.fechaApertura.set(fechaApertura);
	}//FIN METODO
	
	public Date getFechaApertura() {
		return this.fechaApertura.get();
	}//FIN METODO
	
	public ObjectProperty<Date> fechaAperturaProperty() {
		return this.fechaApertura;
	}//FIN METODO
	//FIN METODOS "FECHA APERTURA"
	
	//METODOS PARA ACCESO A "HORA APERTURA"
	public void setHoraApertura(Time horaApertura) {
		this.horaApertura.set(horaApertura);
	}//FIN METODO
	
	public Time getHoraApertura() {
		return this.horaApertura.get();
	}//FIN METODO
	
	public ObjectProperty<Time> horaAperturaProperty() {
		return this.horaApertura;
	}//FIN METODO
	//FIN METODOS "HORA APERTURA"
	
	//METODOS PARA ACCESO A "FECHA CIERRE"
	public void setFechaCierre(Date fechaCierre) {
		this.fechaCierre.set(fechaCierre);
	}//FIN METODO
	
	public Date getFechaCierre() {
		if (this.fechaCierre.get() == null)
			return Date.valueOf("1999-01-01");
		else
			return this.fechaCierre.get();
	}//FIN METODO
	
	public ObjectProperty<Date> fechaCierreProperty() {
		return this.fechaCierre;
	}//FIN METODO
	//FIN METODOS "FECHA CIERRE"
	
	//METODOS PARA ACCESO A "HORA CIERRE"
	public void setHoraCierre(Time horaCierre) {
		this.horaCierre.set(horaCierre);
	}//FIN METODO
	
	public Time getHoraCierre() {
		if (this.horaCierre.get() == null)
			return Time.valueOf("00:00:00");
		else
			return this.horaCierre.get();
	}//FIN METODO
	
	public ObjectProperty<Time> horaCierreProperty() {
		return this.horaCierre;
	}//FIN METODO
	//FIN METODOS "HORA CIERRE"
	
	//METODOS PARA ACCESO A "USUARIO"
	public void setUsuarioFk(Integer usuarioFk) {
		this.usuarioFk.set(usuarioFk);
	}//FIN METODO
	
	public Integer getusuarioFk() {
		return this.usuarioFk.get();
	}//FIN METODO
	
	public Usuario getUsuario(Connection connection) {
		return UsuarioDAO.readPorCampo(connection, "SysPK", "" + this.getusuarioFk()).get(0);
	}//FIN METODO
	//FIN METODOS "USUARIO"
	
	public String showInformacion(Connection connection) {
		String informacionSesion = "INFORMACIÓN DE SESIÓN \nSysPk: " + this.getSysPk() + "\n"
				+ "Inicio Sesión: " + this.getFechaApertura().toString() + " @ " + this.getHoraApertura().toString() + "\n"
						+ "Cierre Sesión: " + this.getFechaCierre().toString() + " @ " + this.getHoraCierre().toString() + "\n"
								+ this.getUsuario(connection).showInformacion();
		return informacionSesion;
	}//FIN METODO
	
}//FIN CLASE
