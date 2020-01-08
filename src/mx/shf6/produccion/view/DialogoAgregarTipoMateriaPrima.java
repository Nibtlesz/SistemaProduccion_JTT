package mx.shf6.produccion.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import mx.shf6.produccion.MainApp;
import mx.shf6.produccion.model.Status;
import mx.shf6.produccion.model.TipoMateriaPrima;
import mx.shf6.produccion.model.dao.TipoMateriaPrimaDAO;
import mx.shf6.produccion.utilities.Notificacion;

public class DialogoAgregarTipoMateriaPrima {

	//PROPIEDADES
	private MainApp mainApp;
	private TipoMateriaPrima tipoMateriaPrima;
	
	//VARIABLES
	private int opcion;
	
	//CONSTANTES
	public static final int CREAR = 1;
	public static final int VER = 2;
	public static final int EDITAR = 3;
	
	//COMPONENTES INTERFAZ
	@FXML private TextField campoTextoCodigo;
	@FXML private TextField campoTextoDescripcion;
	@FXML private ComboBox<String> comboBoxStatus;
	
	//INICIA COMPONENTES INTERFAZ USUARIO
	@FXML private void initialize() {
		this.tipoMateriaPrima = new TipoMateriaPrima();
		ObservableList<String> listaStatus = FXCollections.observableArrayList("No Visible", "Visible");
		this.comboBoxStatus.setItems(listaStatus);
	}//FIN METODO
	
	//ACCESO CLASE PRINCIPAL
	public void setMainApp(MainApp mainApp, TipoMateriaPrima tipoMateriaPrima, int opcion) {
		this.mainApp = mainApp;
		this.tipoMateriaPrima = tipoMateriaPrima;
		this.opcion = opcion;
		this.inicializarComponentes();
	}//FIN METODO
	
	//INICIALIZA COMPONENTE
	private void inicializarComponentes() {		
		if (this.opcion == CREAR) {
			this.campoTextoCodigo.setText("");
			this.campoTextoCodigo.setDisable(false);
			this.campoTextoDescripcion.setText("");
			this.campoTextoDescripcion.setDisable(false);
			this.comboBoxStatus.getSelectionModel().select("");
			this.comboBoxStatus.setDisable(false);
		} else if (this.opcion == VER) {
			this.campoTextoCodigo.setText(this.tipoMateriaPrima.getCodigo());
			this.campoTextoCodigo.setDisable(true);
			this.campoTextoDescripcion.setText(this.tipoMateriaPrima.getDescripcion());
			this.campoTextoDescripcion.setDisable(true);
			this.comboBoxStatus.getSelectionModel().select(this.tipoMateriaPrima.getStatus());
			this.comboBoxStatus.setDisable(true);
		} else if (this.opcion == EDITAR) {
			this.campoTextoCodigo.setText(this.tipoMateriaPrima.getCodigo());
			this.campoTextoCodigo.setDisable(false);
			this.campoTextoDescripcion.setText(this.tipoMateriaPrima.getDescripcion());
			this.campoTextoDescripcion.setDisable(false);
			this.comboBoxStatus.getSelectionModel().select(this.tipoMateriaPrima.getStatus());
			this.comboBoxStatus.setDisable(false);
		}//FIN METODO
	}//FIN METODO
	
	//VALIDAR DATOS
	private boolean validarDatos() {
		if (this.campoTextoCodigo.getText().isEmpty() || String.valueOf(this.campoTextoCodigo.getText().charAt(0)).equals(" ")) {
			Notificacion.dialogoAlerta(AlertType.ERROR, "", "El campo \"Código\" no puede estar vacio");
			return false;
		} else if (this.campoTextoDescripcion.getText().isEmpty() || String.valueOf(this.campoTextoCodigo.getText().charAt(0)).equals(" ")) {
			Notificacion.dialogoAlerta(AlertType.ERROR, "", "El campo \"Descripción\" no puede estar vacio");
			return false;
		} else if (this.comboBoxStatus.getSelectionModel().getSelectedItem().isEmpty()) {
			Notificacion.dialogoAlerta(AlertType.ERROR, "", "El campo \"Descripción\" no puede estar vacio");
			return false;
		}//FIN IF/ESLE
		return true;
	}//FIN METODO
	
	//MANEJADORES COMPONENTES	
	@FXML private void vmanejadorBotonAceptar() {
		if (this.validarDatos()) {
			if (this.opcion == CREAR) {
				this.tipoMateriaPrima.setCodigo(this.campoTextoCodigo.getText());
				this.tipoMateriaPrima.setDescripcion(this.campoTextoDescripcion.getText());
				this.tipoMateriaPrima.setStatus(Status.toInt(this.comboBoxStatus.getSelectionModel().getSelectedItem()));
				if (TipoMateriaPrimaDAO.createTipoMateriaPrima(this.mainApp.getConnection(), this.tipoMateriaPrima)) {
					Notificacion.dialogoAlerta(AlertType.INFORMATION, "", "El registro se creo de forma correcta");
					this.mainApp.getEscenarioDialogosAlterno().close();
				} else
					Notificacion.dialogoAlerta(AlertType.INFORMATION, "", "No se pudo crear el registro, revisa que la información sea correcta");
			} else if (this.opcion == EDITAR) {
				this.tipoMateriaPrima.setCodigo(this.campoTextoCodigo.getText());
				this.tipoMateriaPrima.setDescripcion(this.campoTextoDescripcion.getText());
				this.tipoMateriaPrima.setStatus(Status.toInt(this.comboBoxStatus.getSelectionModel().getSelectedItem()));
				if (TipoMateriaPrimaDAO.updateTipoMateriaPrima(this.mainApp.getConnection(), this.tipoMateriaPrima)) {
					Notificacion.dialogoAlerta(AlertType.INFORMATION, "", "El registro se actualizo de forma correcta");
					this.mainApp.getEscenarioDialogosAlterno().close();
				} else
					Notificacion.dialogoAlerta(AlertType.INFORMATION, "", "No se pudo actualizar el registro, revisa que la información sea correcta");
			} else if (this.opcion == VER)
				this.mainApp.getEscenarioDialogosAlterno().close();	
		}//FIN IF
	}//FIN METODO
	
	@FXML private void manejadorBotonCerrar() {
		this.mainApp.getEscenarioDialogosAlterno().close();
	}//FIN METODO
	
}//FIN CLASE
