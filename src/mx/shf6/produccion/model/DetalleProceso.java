package mx.shf6.produccion.model;

import java.sql.Connection;
import java.sql.Date;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import mx.shf6.produccion.model.dao.ComponenteDAO;
import mx.shf6.produccion.model.dao.ProcesoDAO;

public class DetalleProceso {

	//PROPIEDADES
	private ObjectProperty<Integer> sysPK;
	private ObjectProperty<Integer> operacion;
	private StringProperty descripcion;
	private ObjectProperty<Double> tiempoPreparacion;
	private ObjectProperty<Double> tiempoOperacion;
	private ObjectProperty<Integer> centroTrabajoFK;
	private StringProperty nombreCentroTrabajo;
	private ObjectProperty<Integer> grupoTrabajoFK;
	private StringProperty nombreGrupoTrabajo;
	private ObjectProperty<Integer> procesoFK;
	private StringProperty nombreProceso;
	private StringProperty cantidad;
	private StringProperty componentes;
	private StringProperty herramienta;
	private ObjectProperty<Date> fecha;

	//VARIABLES

	//CONSTANTES

	//CONSTRUCTOR VACIO
	public DetalleProceso() {
		this(0, 0, "", 0.0, 0.0, 0, "", 0, "",0, "", "", "", "", new Date(System.currentTimeMillis()));
	}//FIN CONSTRUCTOR

	//CONSTRUCTOR CON PARAMETROS
	public DetalleProceso(Integer sysPK, Integer operacion, String descripcion, Double tiempoPreparacion, Double tiempoOperacion, Integer centroTrabajoFK, String nombreCentroTrabajo, Integer grupoTrabajoFK, String nombreGrupoTrabajo, Integer procesoFK, String nombreProceso, String cantidad, String componente, String herramienta, Date fecha) {
		this.sysPK = new SimpleObjectProperty<Integer>(sysPK);
		this.operacion = new SimpleObjectProperty<Integer>(operacion);
		this.descripcion = new SimpleStringProperty(descripcion);
		this.tiempoPreparacion = new SimpleObjectProperty<Double>(tiempoPreparacion);
		this.tiempoOperacion = new SimpleObjectProperty<Double>(tiempoOperacion);
		this.centroTrabajoFK = new SimpleObjectProperty<Integer>(centroTrabajoFK);
		this.nombreCentroTrabajo = new SimpleStringProperty(nombreCentroTrabajo);
		this.grupoTrabajoFK = new SimpleObjectProperty<Integer>(grupoTrabajoFK);
		this.nombreGrupoTrabajo =  new SimpleStringProperty(nombreGrupoTrabajo);
		this.procesoFK = new SimpleObjectProperty<Integer>(procesoFK);
		this.nombreProceso = new SimpleStringProperty(nombreProceso);
		this.cantidad = new SimpleStringProperty(cantidad);
		this.componentes = new SimpleStringProperty(componente);
		this.herramienta = new SimpleStringProperty(herramienta);
		this.fecha = new SimpleObjectProperty<Date>(fecha);
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

	//METODOS PARA ACCESO A "OPERACION"
	public void setOperacion(Integer operacion) {
		this.operacion.set(operacion);
	}//FIN METODO
	
	public Integer getOperacion() {
		return operacion.get();
	}//FIN METODO

	public ObjectProperty<Integer> operacionProperty() {
    	return operacion;
    }//FIN METODO
	//FIN METODOS "OPERACION"

	//METODOS PARA ACCESO A "DESCRIPCION"
	public void setDescripcion(String descripcion) {
		this.descripcion.set(descripcion);
	}//FIN METODO
	
	public String getDescripcion() {
		return descripcion.get();
	}//FIN METODO

	public StringProperty descripcionProperty() {
    	return descripcion;
    }//FIN METODO
	//FIN METODOS "DESCRIPCION"

	//METODOS PARA ACCESO A "PREPARACION"
	public void setTiempoPreparacion(Double tiempoPreparacion) {
		this.tiempoPreparacion.set(tiempoPreparacion);
	}//FIN METODO
	
	public Double getTiempoPreparacion() {
		return tiempoPreparacion.get();
	}//FIN METODO

	public ObjectProperty<Double> tiempoPreparacionProperty() {
    	return tiempoPreparacion;
    }//FIN METODO
	//FIN METODOS "PREPARACION"

	//METODOS PARA ACCESO A "TIEMPOOPERACION"
	public void setTiempoOperacion(Double tiempoOperacion) {
		this.tiempoOperacion.set(tiempoOperacion);
	}//FIN METODO
	
	public Double getTiempoOperacion() {
		return tiempoOperacion.get();
	}//FIN METODO

	public ObjectProperty<Double> tiempoOperacionProperty() {
    	return tiempoOperacion;
    }//FIN METODO
	//FIN METODOS "TIEMPOOPERACION"

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
	
	public void setNombreCentroTrabajo(String nombreCentroTrabajo) {
		this.nombreCentroTrabajo.set(nombreCentroTrabajo);
	}//FIN METODO
	
	public String getNombreCentroTrabajo() {
		return nombreCentroTrabajo.get();
	}//FIN METODO
	
	public StringProperty nombreCentroTrabajoProperty() {
		return nombreCentroTrabajo;
	}//FIN METODO
	//FIN METODOS "CENTROTRABAJOFK"

	//METODOS PARA ACCESO A "GRUPOTRABAJOFK"
	public void setGrupoTrabajoFK(Integer grupoTrabajoFK) {
		this.grupoTrabajoFK.set(grupoTrabajoFK);
	}//FIN METODO
	
	public Integer getGrupoTrabajoFK() {
		return grupoTrabajoFK.get();
	}//FIN METODO

	public ObjectProperty<Integer> grupoTrabajoFKProperty() {
	    return grupoTrabajoFK;
	}//FIN METODO
	
	public void setNombreGrupoTrabajo(String nombreGrupoTrabajo) {
		this.nombreGrupoTrabajo.set(nombreGrupoTrabajo);
	}//FIN METODO
	
	public String getNombreGrupoTrabajo() {
		return nombreGrupoTrabajo.get();
	}//FIN METODO
	
	public StringProperty nombreGrupoTrabajoProperty() {
		return nombreGrupoTrabajo;
	}//FIN METODO
	//FIN METODOS "GRUPOTRABAJOFK"

	//METODOS PARA ACCESO A "PROCESOFK"
	public void setProcesoFK(Integer procesoFK) {
		this.procesoFK.set(procesoFK);
	}//FIN METODO
	
	public Integer getProcesoFK() {
		return procesoFK.get();
	}//FIN METODO

	public ObjectProperty<Integer> procesoFKProperty() {
    	return procesoFK;
    }//FIN METODO
	
	public void setNombreProceso(String nombreProceso) {
		this.nombreProceso.set(nombreProceso);
	}//FIN METODO
	
	public String getNombreProceso() {
		return nombreProceso.get();
	}//FIN METODO
	
	public StringProperty nombreProcesoProperty() {
		return nombreProceso;
	}//FIN METODO
	
	public Componente getComponente(Connection connection) {
		return ComponenteDAO.readComponente(connection, this.getProcesoFK());
	}//FIN METODO
	
	public Proceso getCantidad(Connection connection) {
		return ProcesoDAO.readProceso(connection, this.getProcesoFK());
	}
	//FIN METODOS "PROCESOFK"
	
	//METODOS PARA ACCESO A CANTIDAD
	public void setCantidad(String cantidad) {
		this.cantidad.set(cantidad);
	}//FIN METODO
	
	public String getCantidad() {
		return cantidad.get();
	}//FIN METODO
	
	public StringProperty cantidadProperty() {
		return cantidad;
	}//FIN METODO
	//FIN METODOS CANTIDAD
	
	//METODOS DE ACCESO A COMPONENTES
	public void setComponentes(String componentes) {
		this.componentes.set(componentes);
	}//FIN METODO
	
	public String getComponentes() {
		return componentes.get();
	}//FIN METODO
	
	public StringProperty componentesProperty() {
		return componentes;
	}//FIN METODO
	//FIN METODOS DE ACCESO A COMPONENTES
	
	//METODOS DE ACCESO HERRAMIENTA
	public void setHerramienta(String herramienta) {
		this.herramienta.set(herramienta);
	}//FIN METODO
	
	public String getHerramientas() {
		return this.herramienta.get();
	}//FIN METODO
	
	public StringProperty herramientaProperty() {
		return this.herramienta;
	}//FIN METODO
	//FIN METODOS DE ACCESO HERRAMIENTA

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
