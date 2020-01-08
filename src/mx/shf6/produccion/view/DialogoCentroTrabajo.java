package mx.shf6.produccion.view;

import java.sql.Connection;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import mx.shf6.produccion.MainApp;
import mx.shf6.produccion.model.CentroTrabajo;
import mx.shf6.produccion.model.GrupoTrabajo;
import mx.shf6.produccion.model.dao.CentroTrabajoDAO;
import mx.shf6.produccion.model.dao.GrupoTrabajoDAO;
import mx.shf6.produccion.utilities.Notificacion;
import mx.shf6.produccion.utilities.RestriccionTextField;


public class DialogoCentroTrabajo {

	//PROPIEDADES
	private MainApp mainApp;
	private CentroTrabajo centroTrabajo;
	private Connection conexion;
	@SuppressWarnings("unused")
	private GrupoTrabajo grupoTrabajo;
	private ObservableList<String> observableGrupoTrabajo;
	private ArrayList<GrupoTrabajo> listaGrupoTrabajo;
	
	//VARIABLES
	private int opcion;
	
	//CONSTANTES
	 static final int CREAR = 1;
	 static final int VER = 2;
	 static final int EDITAR = 3;
	//FIN CONSTANTE
	
	//COMPONENTES INTERFAZ
	@FXML private TextField campoTextoCodigo;
	@FXML private TextField campoTextoDescripcion;
	@FXML private ComboBox<String> comboGrupoTrabajo;
	
	//INICIALIZA COMPONENTES INTERFAZ
	 @FXML  private void initialize() {
		
		RestriccionTextField.limitarNumeroCaracteres(campoTextoCodigo, 8);
		
	}
	
	//ACCESO A LA CLASE PRINCIPAL
	public void setMainApp(MainApp mainApp, CentroTrabajo centroTrabajo, int opcion) {
		this.mainApp = mainApp;
		this.centroTrabajo = centroTrabajo;
		this.observableGrupoTrabajo = FXCollections.observableArrayList();
		this.conexion = this.mainApp.getConnection();
		this.opcion = opcion;
		this.grupoTrabajo = new GrupoTrabajo();
		this.inicializarComponentes();
		this.llenarComboCentroTrabajo();
		
	}//FIN ACCESO A CLASE PRINCIPAL
	
	//LLENAR COMBO BOX
	private void llenarComboCentroTrabajo() {
		this.listaGrupoTrabajo = GrupoTrabajoDAO.readGrupoTrabajo(conexion);
		for(GrupoTrabajo grupo: listaGrupoTrabajo)
			this.observableGrupoTrabajo.add(grupo.getCodigo());
		this.comboGrupoTrabajo.setItems(observableGrupoTrabajo);		
	}//FIN LLENADO COMBO
	
	
	//METODO OBTENER DATO
	private void obtenerCombo() {
		this.centroTrabajo.setCodigo(this.campoTextoCodigo.getText());
		this.centroTrabajo.setDescripcion(this.campoTextoDescripcion.getText());
		this.centroTrabajo.setgrupoTrabajoFK(this.listaGrupoTrabajo.get(this.comboGrupoTrabajo.getSelectionModel().getSelectedIndex()).getSysPK());
	}//FIN METODO OBTENER COMBO
	
	//INICIALIZAR COMPONENTES
	private void inicializarComponentes() {
		if (this.opcion == CREAR) {
			this.campoTextoCodigo.setText("");
			this.campoTextoCodigo.setDisable(false);
			this.campoTextoDescripcion.setText("");
			this.campoTextoDescripcion.setDisable(false);
			this.comboGrupoTrabajo.setValue(centroTrabajo.getGrupoTrabajoFK(conexion).getCodigo());
			this.comboGrupoTrabajo.setDisable(false);
		} else if(this.opcion == VER){
			this.campoTextoCodigo.setText(centroTrabajo.getCodigo());
			this.campoTextoCodigo.setDisable(true);
			this.campoTextoDescripcion.setText(centroTrabajo.getDescripcion());
			this.campoTextoDescripcion.setDisable(true);
			this.comboGrupoTrabajo.setValue(centroTrabajo.getGrupoTrabajoFK(conexion).getCodigo());
			this.comboGrupoTrabajo.setDisable(true);
		}else if(this.opcion == EDITAR){
			this.campoTextoCodigo.setText(centroTrabajo.getCodigo());
			this.campoTextoCodigo.setDisable(false);
			this.campoTextoDescripcion.setText(centroTrabajo.getDescripcion());
			this.campoTextoDescripcion.setDisable(false);
			this.comboGrupoTrabajo.getSelectionModel().select(GrupoTrabajoDAO.readGrupoTrabajo(this.mainApp.getConnection(), this.centroTrabajo.getGrupoTrabajoFK()).getCodigo());
			this.comboGrupoTrabajo.setDisable(false);
		}//FIN ELSE-IF
	}//FIN METODO

	//METODO VALIDAR DATOS
	private boolean validarDAtos() {
		if (this.campoTextoCodigo.getText().isEmpty()) {
			Notificacion.dialogoAlerta(AlertType.INFORMATION, "", "El campo \"Código\" no puede estar vacio");
			return false;
		} else if(this.campoTextoDescripcion.getText().isEmpty()){
			Notificacion.dialogoAlerta(AlertType.INFORMATION, "", "El campo \"Descripción\" no puede estar vacio");
			return false;
		} else if(this.comboGrupoTrabajo.getSelectionModel().getSelectedItem().isEmpty()) {
			Notificacion.dialogoAlerta(AlertType.INFORMATION,"", "El campo \"Grupo de trabajo\" no puede quedar vacio");
			return false;
		}//FIN ELSE-IF
	return true;
	}// FIN METODO VALIDAR

	
	//MANEJADOR DE COMPONENTES
	
	//MANEJADOR BOTON ACEPTAR
	@FXML private void manejadorBotonAceptar() {
		if (validarDAtos()) {
			if (opcion == CREAR) {
				this.obtenerCombo();
				if (CentroTrabajoDAO.createCentroTrabajo(conexion, centroTrabajo)) {
					Notificacion.dialogoAlerta(AlertType.INFORMATION, "", "El registro se creo de forma correcta");
					this.mainApp.getEscenarioDialogos().close();
				} else {
					Notificacion.dialogoAlerta(AlertType.INFORMATION, "", "No se pudo crear el Resgistro, revisa que la información sea correcta");
				}
			} else if (opcion == EDITAR) {
				obtenerCombo();
				if (CentroTrabajoDAO.updateCentroTrabajo(conexion, centroTrabajo)) {
					Notificacion.dialogoAlerta(AlertType.INFORMATION, "", "El registro se actualizo de forma correcta");
					this.mainApp.getEscenarioDialogos().close();
				}else {
					Notificacion.dialogoAlerta(AlertType.INFORMATION, "", "No se pudo actualizar el registro, revisa que la información sea correcta");
				}
			}else if (opcion == VER) {
				this.mainApp.getEscenarioDialogos().close();
			}
		}//FIN ELSE-IF
	}//FIN METODO MANEJADOR
	
	//BOTON CERRAR
	@FXML 
	private void manejadorBotonCerrar() {
		this.mainApp.getEscenarioDialogos().close();
	}//FIN BOTON CERRAR

}//FIN CLASE


