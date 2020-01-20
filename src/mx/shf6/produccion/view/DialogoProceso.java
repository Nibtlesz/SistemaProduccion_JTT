package mx.shf6.produccion.view;


import java.sql.Date;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Alert.AlertType;
import mx.shf6.produccion.MainApp;
import mx.shf6.produccion.model.CentroTrabajo;
import mx.shf6.produccion.model.Componente;
import mx.shf6.produccion.model.Empleado;
import mx.shf6.produccion.model.Proceso;
import mx.shf6.produccion.model.dao.CentroTrabajoDAO;
import mx.shf6.produccion.model.dao.ComponenteDAO;
import mx.shf6.produccion.model.dao.EmpleadoDAO;
import mx.shf6.produccion.model.dao.ProcesoDAO;
import mx.shf6.produccion.utilities.AutoCompleteComboBoxListener;
import mx.shf6.produccion.utilities.Notificacion;

public class DialogoProceso {
	
	//PROPIEDADES
	private MainApp mainApp;
	private Proceso proceso;
	private CentroTrabajo centroTrabajo;
	private Componente componente;
	private Empleado empleado;
	private ArrayList<CentroTrabajo> listaCentrosTrabajo;
	private ArrayList<Componente> listaComponentes;
	private ArrayList<Empleado> listaEmpleados;
	private ObservableList<String> observableCentroTrabajo;
	private ObservableList<String> observableComponentes;
	private ObservableList<String> observableEmpleados;
	
	//VARIABLES
	private int opcion;
	
	//CONSTANTES
	public static final int MOSTRAR = 1;
	public static final int CREAR = 2;
	public static final int EDITAR = 3;
	
	//COMPONENTES INTERFAZ
	@FXML private DatePicker campoDateFecha;
	@FXML private ComboBox<String> comboBoxDestino;
	@FXML private ComboBox<String> comboTextoComponentes;
	@FXML private ComboBox<String> comboBoxEmpleados;
	
	//ACCESO A LA CLASE PRINCIPAL
	public void setMainApp(MainApp mainApp, Proceso proceso, Integer opcion) {
		this.mainApp = mainApp;
		this.proceso = proceso;
		this.opcion = opcion;
		this.observableCentroTrabajo = FXCollections.observableArrayList();
		this.observableComponentes = FXCollections.observableArrayList();
		this.observableEmpleados = FXCollections.observableArrayList();
		this.componente = ComponenteDAO.readComponente(this.mainApp.getConnection(), proceso.getComponenteFK());
		this.centroTrabajo = CentroTrabajoDAO.readCentroTrabajo(this.mainApp.getConnection(), proceso.getCentroTrabajoFK());
		this.empleado = EmpleadoDAO.readEmpleado(this.mainApp.getConnection(), proceso.getEmpleadoFK());
		inicializarCombosCT();
		this.inicializarComponentes();
	}//FIN METODO
	
	//INICIALIZAR COMBOS
	private void inicializarCombosCT() {
		listaCentrosTrabajo = CentroTrabajoDAO.readCentroTrabajo(this.mainApp.getConnection());
		for (CentroTrabajo trabajo : listaCentrosTrabajo) 
			this.observableCentroTrabajo.add(trabajo.getDescripcion());
		this.comboBoxDestino.setItems(this.observableCentroTrabajo);
		new AutoCompleteComboBoxListener(comboBoxDestino);
		
		listaComponentes = ComponenteDAO.readComponente(this.mainApp.getConnection());
		for (Componente componente : listaComponentes)
			this.observableComponentes.add(componente.getNumeroParte());
		this.comboTextoComponentes.setItems(this.observableComponentes);
		new AutoCompleteComboBoxListener(comboTextoComponentes);
		
		listaEmpleados = EmpleadoDAO.readEmpleado(this.mainApp.getConnection());
		for (Empleado empleado : listaEmpleados)
			this.observableEmpleados.add(empleado.getNombre());
		this.comboBoxEmpleados.setItems(this.observableEmpleados);
		new AutoCompleteComboBoxListener(comboBoxEmpleados);
	}//FIN METODO
	
	//INICIALIZA COMPONENTE
	private void inicializarComponentes() {
		if (this.opcion == MOSTRAR) {
			this.campoDateFecha.setValue(this.proceso.getFecha().toLocalDate());
			this.campoDateFecha.setDisable(true);
			this.comboBoxDestino.setValue(centroTrabajo.getDescripcion());
			this.comboBoxDestino.setDisable(true);
			this.comboTextoComponentes.setValue(componente.getNumeroParte());
			this.comboTextoComponentes.setDisable(true);
			this.comboBoxEmpleados.setValue(empleado.getNombre());
			this.comboBoxEmpleados.setDisable(true);
		} else if (this.opcion == CREAR) {
			this.campoDateFecha.setUserData("");
			this.campoDateFecha.setDisable(false);
			this.comboBoxDestino.getSelectionModel().select("");
			this.comboBoxDestino.setDisable(false);
			this.comboTextoComponentes.getSelectionModel().select("");
			this.comboTextoComponentes.setDisable(false);
			this.comboBoxEmpleados.getSelectionModel().select("");
			this.comboBoxEmpleados.setDisable(false);
		} else if (this.opcion == EDITAR) {
			this.campoDateFecha.setValue(this.proceso.getFecha().toLocalDate());
			this.campoDateFecha.setDisable(false);
			this.comboBoxDestino.setValue(centroTrabajo.getDescripcion());
			this.comboBoxDestino.setDisable(false);
			this.comboTextoComponentes.setValue(componente.getNumeroParte());
			this.comboTextoComponentes.setDisable(false);
			this.comboBoxEmpleados.setValue(empleado.getNombre());
			this.comboBoxEmpleados.setDisable(false);
		}//FIN IF-ELSE
	}//FIN METODO
	
	//VALIDAR METODOS
	private boolean validarDatos() {
		if (this.campoDateFecha.getValue() == null) {
			Notificacion.dialogoAlerta(AlertType.ERROR, "", "El campo \"Fecha \" no puede estar vacio");
			return false;
		}//FIN IF-ELSE
		return true;
	}//FIN METODO
	
	//MANEJADORES COMPONENTES
	@FXML private void manejadorBotonAceptar() {
		if (this.validarDatos()) {
			if (this.opcion == CREAR) {
				this.proceso.setFecha(Date.valueOf(this.campoDateFecha.getValue()));
				CentroTrabajo centroFK = CentroTrabajoDAO.readCentroTrabajoNombre(this.mainApp.getConnection(), comboBoxDestino.getValue());
				this.proceso.setCentroTrabajoFK(centroFK.getSysPK());
				Componente componenteFK = ComponenteDAO.readComponenteNumeroParte(this.mainApp.getConnection(), comboTextoComponentes.getValue().toString());
				this.proceso.setComponenteFK(componenteFK.getSysPK());
				Empleado empleadoFK = EmpleadoDAO.readEmpleadoPorNombre(this.mainApp.getConnection(), comboBoxEmpleados.getValue());
				this.proceso.setEmpleadoFK(empleadoFK.getSysPK());
				if (ProcesoDAO.createProceso(this.mainApp.getConnection(), this.proceso)) {
					manejadorBotonCerrar();
					Notificacion.dialogoAlerta(AlertType.INFORMATION, "", "El registro se creo de forma correcta");
				} else {
					Notificacion.dialogoAlerta(AlertType.INFORMATION, "", "No se pudo crear el registro, revisa que la información sea correcta.");
				}//FIN IF-ELSE
				
			} else if (this.opcion == EDITAR) {
				this.proceso.setFecha(Date.valueOf(this.campoDateFecha.getValue()));
				CentroTrabajo centroFK = CentroTrabajoDAO.readCentroTrabajoNombre(this.mainApp.getConnection(), comboBoxDestino.getValue());
				this.proceso.setCentroTrabajoFK(centroFK.getSysPK());
				Componente componenteFK = ComponenteDAO.readComponenteNumeroParte(this.mainApp.getConnection(), comboTextoComponentes.getValue());
				this.proceso.setComponenteFK(componenteFK.getSysPK());
				Empleado empleadoFK = EmpleadoDAO.readEmpleadoPorNombre(this.mainApp.getConnection(), comboBoxEmpleados.getValue());
				this.proceso.setEmpleadoFK(empleadoFK.getSysPK());
				
				if (ProcesoDAO.updateProceso(this.mainApp.getConnection(), this.proceso)) {
					manejadorBotonCerrar();
					Notificacion.dialogoAlerta(AlertType.INFORMATION, "", "El registro se modifico correctamente");
				} else {
					Notificacion.dialogoAlerta(AlertType.INFORMATION, "", "No se pudo modificar el registro, revisa la información sea correcta");
				}//FIN IF-ELSE
			} else if (this.opcion == MOSTRAR) {
				manejadorBotonCerrar();
			}//FIN IF-ELSE
		}//FIN METODO
	}//FIN METODO
	
	@FXML private void manejadorBotonCerrar() {
		this.mainApp.getEscenarioDialogos().close();
	}//FIN METODO
	
}//FIN CLASE
