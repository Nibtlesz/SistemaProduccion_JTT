package mx.shf6.produccion.view;

import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import mx.shf6.produccion.MainApp;
import mx.shf6.produccion.model.CentroTrabajo;
import mx.shf6.produccion.model.Componente;
import mx.shf6.produccion.model.DetalleProceso;
import mx.shf6.produccion.model.GrupoTrabajo;
import mx.shf6.produccion.model.TipoComponente;
import mx.shf6.produccion.model.dao.CentroTrabajoDAO;
import mx.shf6.produccion.model.dao.DetalleProcesoDAO;
import mx.shf6.produccion.model.dao.GrupoTrabajoDAO;
import mx.shf6.produccion.utilities.AutoCompleteComboBoxListener;
import mx.shf6.produccion.utilities.Notificacion;
import mx.shf6.produccion.utilities.RestriccionTextField;

public class DialogoAgregarDetalleProceso {
	
	//VARIABLES
	private int syspk;
	
	//CONSTANTES
	public static int VER = 1;
	public static int CREAR = 2;
	public static int EDITAR = 3;
	
	//PROPIEDADES
	private MainApp mainApp;
	private int opcion;
	private DetalleProceso detalleProceso;
	private Componente componente;
	private ArrayList<CentroTrabajo> listaCentroTrabajo;
	private ArrayList<GrupoTrabajo> listaGrupoTrabajo;
	private ObservableList<String> observableListaCentroTrabajo;
	private ObservableList<String> observableListaGrupoTrabajo;	
	
	//COMPONENTE INTERFAZ
	@FXML private TextField campoOperacion;
	@FXML private TextField campoDescripcion;
	@FXML private TextField campoTiempoOperacion;
	@FXML private TextField campoTiempoPreparacion;
	@FXML private ComboBox<String> comboBoxCentroTrabajo;
	@FXML private ComboBox<String> comboBoxGrupoTrabajo;
	@FXML private TextField campoComponentes;
	@FXML private TextField campoCantidad;
	@FXML private TextField campoHerramienta;
	@FXML private Label labelComboboxParte;
	@FXML private Label labelCantidad;
	@FXML private Label labelHerramienta;
	
	//INICIALIZAR
	@FXML private void initialize() {
		restricciones();
	}//FIN METODO
	
	//ACCESO A LA CLASE PRINCIPAL
	public void setMainApp(MainApp mainApp, DetalleProceso detalleProceso, int opcion, int syspk, Componente componente) {
		this.mainApp = mainApp;
		this.detalleProceso = detalleProceso;
		this.componente = componente;
		this.syspk = syspk;
		this.opcion = opcion;
		this.observableListaCentroTrabajo = FXCollections.observableArrayList();
		this.observableListaGrupoTrabajo = FXCollections.observableArrayList();
		this.listaCentroTrabajo = CentroTrabajoDAO.readCentroTrabajo(this.mainApp.getConnection());
		this.listaGrupoTrabajo = GrupoTrabajoDAO.readGrupoTrabajo(this.mainApp.getConnection());
		inicializarCombos();
		mostrarDatosInterfaz();
	}//FIN METODO
	
	//RESTRICCIONES
	private void restricciones() {
		RestriccionTextField.soloNumeros(this.campoOperacion);
		RestriccionTextField.limitarNumeroCaracteres(this.campoOperacion, 4);
		RestriccionTextField.limitarPuntoDecimal(campoTiempoPreparacion);
		RestriccionTextField.limitarPuntoDecimal(campoTiempoOperacion);
	}//FIN METODO
	
	//INICIALIZAR COMBOS
	private void inicializarCombos() {
		listaCentroTrabajo = CentroTrabajoDAO.readCentroTrabajo(this.mainApp.getConnection());
		for (CentroTrabajo trabajo : listaCentroTrabajo)
			this.observableListaCentroTrabajo.add(trabajo.getDescripcion());
		this.comboBoxCentroTrabajo.setItems(this.observableListaCentroTrabajo);
		new AutoCompleteComboBoxListener(comboBoxCentroTrabajo);
		
		listaGrupoTrabajo = GrupoTrabajoDAO.readGrupoTrabajo(this.mainApp.getConnection());
		for (GrupoTrabajo grupo : listaGrupoTrabajo)
			this.observableListaGrupoTrabajo.add(grupo.getCodigo());
		this.comboBoxGrupoTrabajo.setItems(this.observableListaGrupoTrabajo);
		new AutoCompleteComboBoxListener(comboBoxGrupoTrabajo);
		
	}//FIN METODO
	
	//INICIALIZA COMPONENTES
	private void mostrarDatosInterfaz() {
		if (this.componente.getTipoComponente() != TipoComponente.ENSAMBLE && this.componente.getTipoComponente() != TipoComponente.SUB_ENSAMBLE) {
			if (this.opcion == CREAR) {
				this.campoOperacion.setText(String.valueOf((DetalleProcesoDAO.ultimaOperacion(this.mainApp.getConnection(), syspk))+1));
				this.campoOperacion.setEditable(false);
				this.campoOperacion.setDisable(true);
				this.campoOperacion.setFocusTraversable(false);
				this.campoDescripcion.setUserData("");
				this.campoDescripcion.setDisable(false);
				this.campoTiempoPreparacion.setUserData("");
				this.campoTiempoOperacion.setDisable(false);
				this.campoTiempoOperacion.setUserData("");
				this.campoOperacion.setDisable(false);
				this.comboBoxCentroTrabajo.getSelectionModel().select("");
				this.comboBoxCentroTrabajo.setDisable(false);
				this.comboBoxGrupoTrabajo.getSelectionModel().select("");
				this.comboBoxGrupoTrabajo.setDisable(false);
				this.campoComponentes.setVisible(false);
				this.labelComboboxParte.setVisible(false);
				this.labelCantidad.setVisible(false);
				this.campoCantidad.setVisible(false);
				this.labelHerramienta.setVisible(false);
				this.campoHerramienta.setVisible(false);
			} else if (this.opcion == EDITAR) {
				this.campoOperacion.setText(String.valueOf(this.detalleProceso.getOperacion()));
				this.campoOperacion.setDisable(false);
				this.campoDescripcion.setText(this.detalleProceso.getDescripcion());
				this.campoDescripcion.setDisable(false);
				this.campoTiempoPreparacion.setText(String.valueOf(this.detalleProceso.getTiempoPreparacion()));
				this.campoTiempoOperacion.setDisable(false);
				this.campoTiempoOperacion.setText(String.valueOf(this.detalleProceso.getTiempoOperacion()));
				this.campoOperacion.setDisable(false);
				this.comboBoxCentroTrabajo.setValue(this.detalleProceso.getNombreCentroTrabajo());
				this.comboBoxCentroTrabajo.setDisable(false);
				this.comboBoxGrupoTrabajo.setValue(this.detalleProceso.getNombreGrupoTrabajo());
				this.campoComponentes.setVisible(false);
				this.labelComboboxParte.setVisible(false);
				this.labelCantidad.setVisible(false);
				this.campoCantidad.setVisible(false);
				this.labelHerramienta.setVisible(false);
				this.campoHerramienta.setVisible(false);				
			}//FIN IF ELSE
		} else {
			if (this.opcion == CREAR ) {
				this.campoOperacion.setText(String.valueOf((DetalleProcesoDAO.ultimaOperacion(this.mainApp.getConnection(), syspk))+1));
				this.campoOperacion.setEditable(false);
				this.campoOperacion.setDisable(true);
				this.campoOperacion.setFocusTraversable(false);
				this.campoDescripcion.setUserData("");
				this.campoDescripcion.setDisable(false);
				this.campoTiempoPreparacion.setUserData("");
				this.campoTiempoOperacion.setDisable(false);
				this.campoTiempoOperacion.setUserData("");
				this.campoOperacion.setDisable(false);
				this.comboBoxCentroTrabajo.getSelectionModel().select("");
				this.comboBoxCentroTrabajo.setDisable(false);
				this.comboBoxGrupoTrabajo.getSelectionModel().select("");
				this.comboBoxGrupoTrabajo.setDisable(false);
				this.campoComponentes.setText("");
				this.campoComponentes.setDisable(false);
				this.campoCantidad.setText("");
				this.campoCantidad.setDisable(false);
				this.campoHerramienta.setText("");
				this.campoHerramienta.setDisable(false);
			} else if (this.opcion == EDITAR) {
				this.campoOperacion.setText(String.valueOf(this.detalleProceso.getOperacion()));
				this.campoOperacion.setDisable(false);
				this.campoDescripcion.setText(this.detalleProceso.getDescripcion());
				this.campoDescripcion.setDisable(false);
				this.campoTiempoPreparacion.setText(String.valueOf(this.detalleProceso.getTiempoPreparacion()));
				this.campoTiempoOperacion.setDisable(false);
				this.campoTiempoOperacion.setText(String.valueOf(this.detalleProceso.getTiempoOperacion()));
				this.campoOperacion.setDisable(false);
				this.comboBoxCentroTrabajo.setValue(this.detalleProceso.getNombreCentroTrabajo());
				this.comboBoxCentroTrabajo.setDisable(false);
				this.comboBoxGrupoTrabajo.setValue(this.detalleProceso.getNombreGrupoTrabajo());
				this.comboBoxGrupoTrabajo.setDisable(false);
				this.campoComponentes.setText(this.detalleProceso.getComponentes());
				this.campoComponentes.setDisable(false);
				this.campoCantidad.setText(String.valueOf(this.detalleProceso.getCantidad()));
				this.campoCantidad.setDisable(false);
				this.campoHerramienta.setText(this.detalleProceso.getHerramientas());
				this.campoHerramienta.setDisable(false);
			}//FIN IF-ELSE
		}//FIN IF ELSE			
	}//FIN METODO
	
	//VALIDAR DATOS
	private boolean validarDatos() {
		if (this.campoOperacion.getText().isEmpty()) {
			Notificacion.dialogoAlerta(AlertType.ERROR, "", "El campo \"Descripción \" no puede estar vacio");
			return false;
		} else if (this.campoDescripcion.getText().isEmpty()) {
			Notificacion.dialogoAlerta(AlertType.ERROR, "", "El campo \"Operacion \" no puede estar vacio");
			return false;
		} else if (this.comboBoxCentroTrabajo.getSelectionModel().isEmpty()) {
			Notificacion.dialogoAlerta(AlertType.ERROR, "", "El campo \"Centro de trabajo\" no puede estar vacio");
			return false;
		} else if (this.comboBoxGrupoTrabajo.getSelectionModel().isEmpty()) {
			Notificacion.dialogoAlerta(AlertType.ERROR, "", "El campo \"Grupo de trabajo\" no puede estar vacio");
			return false;
		}//FIN IF ELSE
		return true;
	}//FIN METODO
	
	private void obtenerInformacion() {
		if (this.validarDatos()) {
			if (this.componente.getTipoComponente() != TipoComponente.ENSAMBLE && this.componente.getTipoComponente() != TipoComponente.SUB_ENSAMBLE) {
				this.detalleProceso.setOperacion(Integer.parseInt(this.campoOperacion.getText()));
				this.detalleProceso.setDescripcion(this.campoDescripcion.getText());
				if (campoTiempoPreparacion.getText().isEmpty())
					this.detalleProceso.setTiempoPreparacion(0.0);
				else
					this.detalleProceso.setTiempoPreparacion(Double.parseDouble(this.campoTiempoPreparacion.getText()));
				if (campoTiempoOperacion.getText().isEmpty())
					this.detalleProceso.setTiempoOperacion(0.0);
				else
					this.detalleProceso.setTiempoOperacion(Double.parseDouble(this.campoTiempoOperacion.getText()));
				CentroTrabajo centroFK = CentroTrabajoDAO.readCentroTrabajoNombre(this.mainApp.getConnection(), comboBoxCentroTrabajo.getValue());
				this.detalleProceso.setCentroTrabajoFK(centroFK.getSysPK());
				GrupoTrabajo grupoFK = GrupoTrabajoDAO.readGrupoTrabajoNombre(this.mainApp.getConnection(), comboBoxGrupoTrabajo.getValue());
				this.detalleProceso.setGrupoTrabajoFK(grupoFK.getSysPK());this.detalleProceso.setProcesoFK(this.syspk);
				this.detalleProceso.setCantidad("");
				this.detalleProceso.setComponentes("");
				this.detalleProceso.setHerramienta("");			
				this.mainApp.getEscenarioDialogosAlterno().close();
			} else {
				this.detalleProceso.setOperacion(Integer.parseInt(this.campoOperacion.getText()));
				this.detalleProceso.setDescripcion(this.campoDescripcion.getText());
				if (campoTiempoPreparacion.getText().isEmpty())
					this.detalleProceso.setTiempoPreparacion(0.0);
				else
					this.detalleProceso.setTiempoPreparacion(Double.parseDouble(this.campoTiempoPreparacion.getText()));
				if (campoTiempoOperacion.getText().isEmpty())
					this.detalleProceso.setTiempoOperacion(0.0);
				else
					this.detalleProceso.setTiempoOperacion(Double.parseDouble(this.campoTiempoOperacion.getText()));
				CentroTrabajo centroFK = CentroTrabajoDAO.readCentroTrabajoNombre(this.mainApp.getConnection(), comboBoxCentroTrabajo.getValue());
				this.detalleProceso.setCentroTrabajoFK(centroFK.getSysPK());
				GrupoTrabajo grupoFK = GrupoTrabajoDAO.readGrupoTrabajoNombre(this.mainApp.getConnection(), comboBoxGrupoTrabajo.getValue());
				this.detalleProceso.setGrupoTrabajoFK(grupoFK.getSysPK());
				this.detalleProceso.setProcesoFK(this.syspk);
				if (campoCantidad.getText().isEmpty())
					this.detalleProceso.setCantidad("");
				else
					this.detalleProceso.setCantidad(this.campoCantidad.getText());
				if (campoComponentes.getText().isEmpty())
					this.detalleProceso.setComponentes("");
				else	
					this.detalleProceso.setComponentes(campoComponentes.getText());
				if (campoHerramienta.getText().isEmpty())
					this.detalleProceso.setHerramienta("");
				else
					this.detalleProceso.setHerramienta(campoHerramienta.getText());			
				this.mainApp.getEscenarioDialogosAlterno().close();
			}//FIN IF-ELSE
		}//FIN IF
	}//FIN METODO
	
	//RETORNO DE OBJETO
	public DetalleProceso getDetalleProceso() {
		return this.detalleProceso;
	}//FIN METODO
	
	//BOTON ACEPTAR
	@FXML private void manejadorBotonAceptar() {
		this.obtenerInformacion();
	}//FIN METODO
	
	//BOTON CERRAR
	@FXML private void manejadorBotonCerrar() {
		this.detalleProceso = null;
		this.mainApp.getEscenarioDialogosAlterno().close();
	}//FIN MODELO	
}//FIN CLASE
