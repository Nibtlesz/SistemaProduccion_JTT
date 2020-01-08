package mx.shf6.produccion.view;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import mx.shf6.produccion.MainApp;
import mx.shf6.produccion.model.Rol;
import mx.shf6.produccion.model.dao.RolDAO;
import mx.shf6.produccion.utilities.Notificacion;
import mx.shf6.produccion.utilities.RestriccionTextField;

public class DialogoAgregarPermiso {
	//VARIABLES
	
	//CONSTANTES
	public static int VER = 1;
	public static int CREAR = 2;
	public static int EDITAR = 3;
	
	//PROPIEDADES
	private MainApp mainApp;
	private int opcion;
	private Rol permiso;
	
	//COMPONENTE INTERFAZ
	@FXML private TextField campoCodigo;
	@FXML private TextField campoDescripcion;
	
	//INICIALIZAR 
	@FXML private void initialize() {
		RestriccionTextField.limitarNumeroCaracteres(campoCodigo, 32);
		RestriccionTextField.limitarNumeroCaracteres(campoDescripcion, 32);
	}//FIN METODO
	
	//ACCESO A LA CLASE PRINCIPAL
	public void setMainApp(MainApp mainApp, Rol permiso, int opcion) {
		this.mainApp = mainApp;
		this.permiso = permiso;
		this.opcion = opcion;
		
		mostrarDatos();
	}//FIN METODO
	
	//INICIALIZAR COMPONENTES
	private void mostrarDatos() {
		if (this.opcion == CREAR) {
			this.campoCodigo.setUserData("");
			this.campoCodigo.setDisable(false);
			this.campoDescripcion.setUserData("");
			this.campoDescripcion.setDisable(false);
		} else if (this.opcion == EDITAR) {
			this.campoCodigo.setText(this.permiso.getCodigoItem());
			this.campoCodigo.setDisable(false);
			this.campoDescripcion.setText(this.permiso.getDescripcion());
			this.campoDescripcion.setDisable(false);
		} else if (this.opcion == VER) {
			this.campoCodigo.setText(this.permiso.getCodigoItem());
			this.campoCodigo.setDisable(true);
			this.campoDescripcion.setText(this.permiso.getDescripcion());
			this.campoDescripcion.setDisable(true);
		}//FIN METODO
	}//FIN METODO
	
	//VALIDAR DATOS
	private boolean validarDatos() {
		if (this.campoCodigo.getText().isEmpty()) {
			Notificacion.dialogoAlerta(AlertType.ERROR, "", "El campo \"Código\" no puede estar vacio");
			return false;
		} else if(this.campoDescripcion.getText().isEmpty()) {
			Notificacion.dialogoAlerta(AlertType.ERROR, "", "El campo \"Descripción\" no puede estar vacio");
			return false;
		}//FIN IF ELSE
		return true;
	}//FIN METODO
	
	@FXML private void manejadorBotonAceptar() {
		if(this.validarDatos()) {
			if (this.opcion ==  CREAR) {
				this.permiso.setCodigoItem(this.campoCodigo.getText());
				this.permiso.setDescripcion(this.campoDescripcion.getText());
				
				if (RolDAO.create(this.mainApp.getConnection(), permiso)) {
					Notificacion.dialogoAlerta(AlertType.INFORMATION, "", "El registro se agrego exitosamente");
					this.mainApp.getEscenarioDialogosAlterno().close();
				} else {
					Notificacion.dialogoAlerta(AlertType.INFORMATION, "", "El registro no se pudo agregar, revise que la información sea correcta");
				}
			} else if (this.opcion == EDITAR) {
				this.permiso.setCodigoItem(this.campoCodigo.getText());
				this.permiso.setDescripcion(this.campoDescripcion.getText());
				
				if (RolDAO.update(this.mainApp.getConnection(), permiso)) {
					Notificacion.dialogoAlerta(AlertType.INFORMATION, "", "El registro se actualizo exitosamente");
					this.mainApp.getEscenarioDialogosAlterno().close();
				} else {
					Notificacion.dialogoAlerta(AlertType.INFORMATION, "", "El registro no se pudo actualizar, revise que la información sea correcta");
				}
			} else if (this.opcion == VER) {
				this.mainApp.getEscenarioDialogosAlterno().close();
			}//FIN IF ELSE
		}//FIN METODO
	}//FIN METODO
	
	
	@FXML private void manejadorBotonCerrar() {
		this.mainApp.getEscenarioDialogosAlterno().close();
	}//FIN METODO
	
}//FIN CLASE


