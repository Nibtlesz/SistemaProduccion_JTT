package mx.shf6.produccion.model;

import java.sql.Connection;
import java.sql.Date;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;
import mx.shf6.produccion.model.dao.EmpleadoDAO;
import mx.shf6.produccion.model.dao.GrupoUsuarioDAO;

public class Usuario {

	//PROPIEDADES
	private ObjectProperty<Integer> sysPk;
	private StringProperty usuario;
	private StringProperty contrasena;
	private StringProperty correoElectronico;
	private ObjectProperty<Date> fechaRegistro;
	private ObjectProperty<Date> fechaBloqueo;
	private ObjectProperty<Integer> status;
	private ObjectProperty<Integer> grupoUsuarioFk;
	private ObjectProperty<Image> imagenPerfil;
	private StringProperty nombreGrupoUsuario;
	private ObjectProperty<Integer> empleadoFK;
	private StringProperty nombreEmpleado;

	//CONSTANTES
	public static final int BLOQUEADO = 0;
	public static final int ACTIVO = 1;
	public static final int BAJA = 2;

	//CONSTRUCTOR SIN PARAMETROS
	public Usuario() {
		this(0,"","","",new Date(System.currentTimeMillis()),new Date(System.currentTimeMillis()),0,0,null, "", 0, "");
	}//FIN CONSTRUCTOR

	//CONSTRUCTOR CON PARAMETROS
	public Usuario(Integer sysPk, String usuario, String contrasena, String correoElectronico, Date fechaRegistro, Date fechaBloqueo,
			Integer status, Integer grupoUsuarioFk, Image imagenPerfil, String nombreGrupoUsuario, int empleadoFK, String nombreEmpleado) {
		this.sysPk = new SimpleObjectProperty<Integer>(sysPk);
		this.usuario = new SimpleStringProperty(usuario);
		this.contrasena = new SimpleStringProperty(contrasena);
		this.correoElectronico = new SimpleStringProperty(correoElectronico);
		this.fechaRegistro = new SimpleObjectProperty<Date>(fechaRegistro);
		this.fechaBloqueo = new SimpleObjectProperty<Date>(fechaBloqueo);
		this.status = new SimpleObjectProperty<Integer>(status);
		this.grupoUsuarioFk = new SimpleObjectProperty<Integer>(grupoUsuarioFk);
		this.imagenPerfil = new SimpleObjectProperty<Image>(imagenPerfil);
		this.nombreGrupoUsuario = new SimpleStringProperty(nombreGrupoUsuario);
		this.empleadoFK = new SimpleObjectProperty<Integer>(empleadoFK);
		this.nombreEmpleado = new SimpleStringProperty(nombreEmpleado);
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
	}//FIN METODO
	//FINT METODOS "SYSPK"

	//METODOS PARA ACCESO A "NOMBRE"
	public void setUsuario(String usuario) {
		this.usuario.set(usuario);
	}//FIN METODO

	public String getUsuario() {
		return this.usuario.get();
	}//FIN METODO

	public StringProperty usuarioProperty() {
		return this.usuario;
	}//FIN METODO
	//FIN METODOS "NOMBRE"

	//METODOS PARA ACCESO A "CONTRASEÑA"
	public void setContrasena(String contrasena) {
		this.contrasena.set(contrasena);
	}//FIN METODO

	public String getContrasena() {
		return this.contrasena.get();
	}//FIN METODO

	public StringProperty contrasenaProperty() {
		return this.contrasena;
	}//FIN METODO
	//FIN METODOS "CONTRASENA"

	//METODOS PARA ACCESO A "CORREO ELECTRONICO"
	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico.set(correoElectronico);
	}//FIN METODO

	public String getCorreoElectronico( ) {
		return this.correoElectronico.get();
	}//FIN METODO

	public StringProperty correoElectronicoProperty() {
		return this.correoElectronico;
	}//FIN METODO
	//FIN METODOS "CORREO ELECTRONICO"

	//METODOS PARA ACCESO A "FECHA REGISTRO"
	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro.set(fechaRegistro);
	}//FIN METODO

	public Date getFechaRegistro() {
		if (this.fechaRegistro.get() == null)
			return new Date(System.currentTimeMillis());
		else
			return this.fechaRegistro.get();
	}//FIN METODO

	public ObjectProperty<Date> fechaRegistroProperty() {
		return this.fechaRegistro;
	}//END METODO
	//END METODOS "FECHA REGISTRO"

	//METODOS PARA ACCESO A "FECHA BLOQUEO"
	public void setFechaBloqueo(Date fechaBloqueo) {
		this.fechaBloqueo.set(fechaBloqueo);
	}//FIN METODO

	public Date getFechaBloqueo() {
		if (this.fechaBloqueo.get() == null)
			return new Date(System.currentTimeMillis());
		else
			return this.fechaBloqueo.get();
	}//FIN METODO

	public ObjectProperty<Date> fechaBloqueProperty() {
		return this.fechaBloqueo;
	}//FIN METODO
	//FIN METODOS "FECHA BLOQUEO"

	//METODOS PARA ACCESO A "STATUS"
	public void setStatus(Integer status) {
		this.status.set(status);
	}//FIN METODO

	public Integer getStatus() {
		return this.status.get();
	}//FIN METODO

	public ObjectProperty<Integer> statusProperty() {
		return this.status;
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

	public StringProperty descripcionStatusProperty() {
		switch (this.getStatus()) {
		case 0:
			return new SimpleStringProperty("Bloqueado");
		case 1:
			return new SimpleStringProperty("Activo");
		case 2:
			return new SimpleStringProperty("Baja");
		}//FIN WTITCH
		return new SimpleStringProperty();
	}//FIN METODO
	//FIN METODO "STATUS"

	//METODOS PARA ACCESO A "GRUPO USUARIO"
	public void setGrupoUsuarioFk(Integer grupoUsuarioFk) {
		this.grupoUsuarioFk.set(grupoUsuarioFk);
	}//FIN METODO

	public Integer getGrupoUsuarioFk() {
		return this.grupoUsuarioFk.get();
	}//FIN METODO

	public GrupoUsuario getGrupoUsuario(Connection connection) {
		return GrupoUsuarioDAO.readPorSysPK(connection, getGrupoUsuarioFk());
	}//FIN METODO
	//FIN METODOS "GRUPO USUARIO"

	//METODOS PARA ACCESO A "IMAGEN PERFIL"
	public void setImagenPerfil(Image imagenPerfil) {
		this.imagenPerfil.set(imagenPerfil);
	}//FIN METODO

	public Image getImagenPerfil() {
		return this.imagenPerfil.get();
	}//FIN METODO

	public ObjectProperty<Image> imagenPerfilProperty() {
		return this.imagenPerfil;
	}//FIN METODO
	//FIN METODOS DE ACCESO

	public String showInformacion() {
		String informacionUsuario = "INFORMACIÓN DEL USUARIO \nSysPk: " + this.getSysPk() + "\n"
				+ "Nombre: " + this.getUsuario() + "\n"
						+ "Contraseña: " + this.getContrasena() + "\n"
								+ "Correo Electrónico: " + this.getCorreoElectronico() + "\n"
										+ "Fecha Registro: " + this.getFechaRegistro().toString() + "\n"
												+ "Fecha Bloqueo: " + this.getFechaBloqueo().toString() + "\n"
														+ "Status: " + this.getStatus() + "\n";
		return informacionUsuario;
	}//FIN METODO

	//METODO PARA ACCEDER A NOMBREGRUPOUSUARIO
	public void setNombreGrupoUsuario (String nombreGrupo) {
		this.nombreGrupoUsuario.set(nombreGrupo);
	}//FIN METODO

	public String getNombreGrupoUsuario() {
		return this.nombreGrupoUsuario.get();
	}//FIN METODO

	public StringProperty nombreGrupoUsuarioProperty() {
		return this.nombreGrupoUsuario;
	}//FIN METODO

	//METODOS PARA ACCESO A "EMPLEADOFK"
	public void setEmpleadoFK(Integer empleadoFK) {
		this.empleadoFK.set(empleadoFK);
	}//FIN METODO

	public Integer getEmpleadoFK() {
		return this.empleadoFK.get();
	}//FIN METODO

	public ObjectProperty<Integer> empleadoFKProperty() {
		return this.empleadoFK;
	}//FIN METODO

	public Empleado getEmpleado(Connection connection){
		return EmpleadoDAO.readEmpleado(connection, getEmpleadoFK());
	}//FIN METODO
	//FINT METODOS "EMPLEADOFK"

	//METODO PARA ACCEDER A NOMBREEMPLEADO
	public void setNombreEmpleado (String nombreEmpleado) {
		this.nombreEmpleado.set(nombreEmpleado);
	}//FIN METODO

	public String getNombreEmpleado() {
		return this.nombreEmpleado.get();
	}//FIN METODO

	public StringProperty nombreEmpleadoProperty() {
		return this.nombreEmpleado;
	}//FIN METODO
}//FIN CLASE
