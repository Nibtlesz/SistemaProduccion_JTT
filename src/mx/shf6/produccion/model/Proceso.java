package mx.shf6.produccion.model;

import java.sql.Connection;
import java.sql.Date;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import mx.shf6.produccion.model.dao.CentroTrabajoDAO;
import mx.shf6.produccion.model.dao.ClienteDAO;
import mx.shf6.produccion.model.dao.ComponenteDAO;
import mx.shf6.produccion.model.dao.EmpleadoDAO;
import javafx.beans.property.ObjectProperty;

public class Proceso {

	//PROPIEDADES
	private ObjectProperty<Integer> sysPK;
	private ObjectProperty<Date> fecha;
	private ObjectProperty<Integer> cantidad;
	private ObjectProperty<Integer> ordenamiento;
	private ObjectProperty<Integer> nivel;
	private ObjectProperty<Integer> centroTrabajoFK;
	private StringProperty nombreCentroTrabajo;
	//private IntegerProperty tiempo;
	private ObjectProperty<Integer> componenteFK;
	private StringProperty nombreComponente;
	private StringProperty descripcionComponente;
	private ObjectProperty<Integer> empleadoFK;
	private StringProperty nombreEmpleado;
	private StringProperty nombreCliente;
	private ObjectProperty<Integer> clienteFK;
	private StringProperty tipoComponente;
	private StringProperty revisionComponente;
	private ObjectProperty<Integer> debit;

	//VARIABLES

	//CONSTANTES

	//CONSTRUCTOR VACIO
	public Proceso() {
		this(0, new Date(System.currentTimeMillis()),0,0,0,0, "", 0, "", "", 0, "", 0, "", "", "", 0);
	}//FIN CONSTRUCTOR

	//CONSTRUCTOR CON PARAMETROS
	public Proceso(Integer sysPK, Date fecha, Integer cantidad, Integer ordenamiento, Integer nivel, Integer centroTrabajoFK, String nombreCentroTrabajo, 
			Integer componenteFK, String nombreComponente, String descripcionComponente, Integer empleadoFK, String nombreEmpleado,
			Integer clienteFK, String nombreCliente, String tipoComponente, String revisionComponente, Integer debit){
		this.sysPK = new SimpleObjectProperty<Integer>(sysPK);
		this.fecha = new SimpleObjectProperty<Date>(fecha);
		this.cantidad = new SimpleObjectProperty<Integer>(cantidad);
		this.ordenamiento = new SimpleObjectProperty<Integer>(ordenamiento);
		this.nivel = new SimpleObjectProperty<Integer>(nivel);
		this.centroTrabajoFK = new SimpleObjectProperty<Integer>(centroTrabajoFK);
		this.nombreCentroTrabajo = new SimpleStringProperty(nombreCentroTrabajo);
		//this.tiempo = new SimpleIntegerProperty(tiempo);
		this.componenteFK = new SimpleObjectProperty<Integer>(componenteFK);
		this.nombreComponente = new SimpleStringProperty(nombreComponente);
		this.descripcionComponente = new SimpleStringProperty(descripcionComponente);
		this.empleadoFK = new SimpleObjectProperty<Integer>(empleadoFK);
		this.nombreEmpleado = new SimpleStringProperty(nombreEmpleado);
		this.clienteFK = new SimpleObjectProperty<Integer>(clienteFK);
		this.nombreCliente = new SimpleStringProperty(nombreCliente);
		this.tipoComponente = new SimpleStringProperty(tipoComponente);
		this.revisionComponente = new SimpleStringProperty(revisionComponente);
		this.debit = new SimpleObjectProperty<Integer>(debit);
	}//FIN CONSTRUCTOR

	//METODOS PARA ACCESO A "SYSPK"
	public void setSysPK(Integer sysPK) {
		this.sysPK.set(sysPK);
	}//FIN METODO
	
	public Integer getSysPK() {
		return sysPK.get();
	}//FIN METODO

	public ObjectProperty<Integer> sysPKProperty() {
    	return sysPK;
    }//FIN METODO
	//FIN METODOS "SYSPK"

	//METODOS PARA ACCESO A FECHA
	public void setFecha(Date fecha) {
		this.fecha.set(fecha);
	}//FIN METODO
	
	public Date getFecha() {
		return fecha.get();
	}//FIN METODO

	public ObjectProperty<Date> fechaProperty() {
		return fecha;
	}//FIN METODO
	//FIN METODOS "FECHA"

	//METODOS PARA ACCESO A "CANTIDAD"
	public void setCantidad(Integer cantidad) {
        this.cantidad.set(cantidad);
    }//FIN METODO
	
	public Integer getCantidad() {
        return cantidad.get();
    }//FIN METODO

	public ObjectProperty<Integer> cantidadProperty() {
        return cantidad;
    }//FIN METODO
	//FIN METODOS "CANTIDAD"

	//METODO PARA ACCESO A "ORDENAMIENTO"
	 public void setOrdenamiento(Integer ordenamiento) {
		 this.ordenamiento.set(ordenamiento);
	 }//FIN METODO
	
	public Integer getOrdenamiento() {
        return ordenamiento.get();
    }//FIN METODO

	 public ObjectProperty<Integer> ordenamientoProperty() {
	      return ordenamiento;
	 }//FIN METODO
	//FIN METODOS "ORDENAMIENTO"

	 //METODO PARA ACCESO A "NIVEL"
	 public void setNivel(Integer nivel) {
	   	this.nivel.set(nivel);
	 }//FIN METODO
		
	 public Integer getNivel() {
		return nivel.get();
	}//FIN METODO

	public ObjectProperty<Integer> nivelProperty() {
    	return nivel;
    }//FIN METODO
	//FIN METODOS "NIVEL"

	//METODOS PARA ACCESO A "CENTROTRABAJOFK"
	public void setCentroTrabajoFK(Integer centroTrabajoFK) {
    	this.centroTrabajoFK.set(centroTrabajoFK);
    }//FIN METODO
	
	public Integer getCentroTrabajoFK() {
		return centroTrabajoFK.get();
	}//FIN METODO

	public ObjectProperty<Integer> centroTrabajoFKProperty() {
    	return centroTrabajoFK;
    }//FIN METODO
	
	public CentroTrabajo getCentroTrabajo(Connection connection) {
		return CentroTrabajoDAO.readCentroTrabajo(connection, this.getCentroTrabajoFK());
	}//FIN METODO
	
	public void setNombreCentroTrabajo(String nombreCentroTrabajo) {
		this.nombreCentroTrabajo.set(nombreCentroTrabajo);
	}//FIN METODO
	
	public String getNombreTrabajo() {
		return this.nombreCentroTrabajo.get();
	}//FIN METODO
	
	public StringProperty nombreCentroTrabajoProperty() {
		return this.nombreCentroTrabajo;
	}//FIN METODO
	//FIN METODOS "CENTROTRABAJOFK"

	//METODOS PARA ACCESO A "TIEMPO"
	/*
	public Integer getTiempo() {
		return tiempo.get();
	}//FIN METODO

	 public void setTiempo(Integer tiempo) {
	    this.tiempo.set(tiempo);
	 }//FIN METODO

	 public IntegerProperty tiempoProperty() {
	   	return tiempo;
	 }//FIN METODO
	//FIN METODOS A "TIEMPO"
	 */

	//METODOS PARA ACCESO A "COMPONENTEFK"
	public void setComponenteFK(Integer componenteFK) {
    	this.componenteFK.set(componenteFK);
    }//FIN METODO
	
	public Integer getComponenteFK() {
		return componenteFK.get();
	}//FIN METODO

	public ObjectProperty<Integer> componenteFKProperty() {
    	return componenteFK;
    }//FIN METODO
	
	public Componente getComponente(Connection connection) {
		return ComponenteDAO.readComponente(connection, this.getComponenteFK());
	}//FIN METODO
	
	public void setNombreComponente(String nombreComponente) {
		this.nombreComponente.set(nombreComponente);
	}//FIN METODO
	
	public String getNombreComponente() {
		return this.nombreComponente.get();
	}//FIN METODO
	
	public StringProperty nombreComponenteProperty() {
		return this.nombreComponente;
	}//FIN METODO
	
	public void setClienteFK(int clienteFK) {
		this.clienteFK.set(clienteFK);
	}//FIN METODO
	
	public Integer getClienteFK() {
		return this.clienteFK.get();
	}//FIN METODO
	
	public ObjectProperty<Integer> clienteFKProperty() {
		return this.clienteFK;
	}//FIN METODO
	
	public void setDescripcionComponente(String descripcionComponente) {
		this.descripcionComponente.set(descripcionComponente);
	}//FIN METODO
	
	public String getDescripcionComponente() {
		return this.descripcionComponente.get();
	}//FIN METODO
	
	public StringProperty descripcionComponenteProperty() {
		return this.descripcionComponente;
	}//FIN METODO
	
	public void setTipoComponente(String tipoComponente) {
		this.tipoComponente.set(tipoComponente);
	}//FIN METODO
	
	public String getTipoComponente() {
		return this.tipoComponente.get();
	}//FIN METODO
	
	public StringProperty tipoComponenteProperty() {
		return this.tipoComponente;
	}//FIN METODO
	
	public void setRevisionComponente(String revisionComponente) {
		this.revisionComponente.set(revisionComponente);
	}//FIN METODO
	
	public String getRevisionComponente() {
		return this.revisionComponente.get();
	}//FIN METODO
	
	public StringProperty revisionComponenteProperty() {
		return this.revisionComponente;
	}//FIN METODO
	//FIN METODOS "COMPONENTEFK"

	//METODOS PARA ACCESO A "EMPLEADOFK"
	public void setEmpleadoFK(Integer empleadoFK) {
	    this.empleadoFK.set(empleadoFK);
	 }//FIN METODO
	
	public Integer getEmpleadoFK() {
		return empleadoFK.get();
	}//FIN METODO

	 public ObjectProperty<Integer> empleadoFKProperty() {
	    return empleadoFK;
	 }//FIN METODO
	 
	 public Empleado getEmpleado(Connection connection) {
		return EmpleadoDAO.readEmpleado(connection, this.getEmpleadoFK());
	 }//FIN METODO
	 
	 public void setNombreEmpleado(String nombreEmpleado) {
		this.nombreEmpleado.set(nombreEmpleado); 
	 }//FIN METODO
	 
	 public String getEmpleado() {
		 return nombreEmpleado.get();
	 }//FIN METODO
	 
	 public StringProperty nombreEmpleadoProperty() {
		 return this.nombreEmpleado;
	 }//FIN METODO
	//FIN METODOS "EMPLEADOFK"
	 
	 //METODOS DE ACCESO A CLIENTE
	 public Cliente getCliente(Connection connection) {
		return ClienteDAO.readCliente(connection, this.getClienteFK());
	}//FIN METODO
	 
	 public void setNombreCliente(String nombreCliente) {
		this.nombreCliente.set(nombreCliente);
	}//FIN METODO
	
	public String getNombreCliente() {
		return this.nombreCliente.get();
	}//FIN METODO
	
	public StringProperty nombreClienteProperty() {
		return this.nombreCliente;
	}//FIN METODO
	//FIN METODOS DE ACCESO A CLIENTES
	
	//METODOS DE ACCESO A DEBIT
	public void setDebit(Integer debit) {
		this.debit.set(debit);
	}//FIN METODO
	
	public Integer getDebit() {
		return this.debit.get();
	}//FIN METODO
	
	public ObjectProperty<Integer> debitProperty() {
		return this.debit;
	}//FIN METODO
	//FIN DE METODOS DE ACCESO A DEBIT

}//FIN CLASE
