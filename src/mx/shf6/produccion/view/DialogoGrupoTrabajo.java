package mx.shf6.produccion.view;

import java.sql.Connection;

import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import mx.shf6.produccion.MainApp;
import mx.shf6.produccion.model.GrupoTrabajo;
import mx.shf6.produccion.model.dao.GrupoTrabajoDAO;
import mx.shf6.produccion.utilities.Notificacion;
import mx.shf6.produccion.utilities.RestriccionTextField;

public class DialogoGrupoTrabajo {

	
	//PROPIEDADES
	private MainApp mainApp;
	private GrupoTrabajo grupoTrabajo;
	private Connection conexion;
	
	//VARIABLES
	private int opcion;
	//FIN VARIABLES
	
	//CONSTANTES
    static final int CREAR = 1;
	static final int VER = 2;
	static final int EDITAR = 3;
	//FIN CONSTANTES
	
	//COMPONENTES INTERFAZ
	@FXML
	private TextField campoTextoCodigo;
	@FXML
	private TextField campoTextoDescripcion;
	//FIN COMPONENTES

	//INICIA COMPONENTES DE INTERFAZ DE USUARIO
	@FXML
	private void initialize() {
		grupoTrabajo = new GrupoTrabajo();
		RestriccionTextField.limitarNumeroCaracteres(this.campoTextoCodigo, 8);
	}//FIN METODO COMPONENTES
	
	//ACCESO A CLASE PRINCIPAL
	public void setMainApp(MainApp mainApp, GrupoTrabajo grupoTrabajo, int opcion) {
		this.mainApp = mainApp;
		this.conexion = this.mainApp.getConnection();
		this.grupoTrabajo = grupoTrabajo;
		this.opcion = opcion;
		this.conexion = mainApp.getConnection();
		inicializarComponentes();
	}
	
	//INICIALIZAR COMPONENTES
	private void inicializarComponentes() {
		if (this.opcion == CREAR) {
			this.campoTextoCodigo.setText("");
			this.campoTextoCodigo.setDisable(false);
			this.campoTextoDescripcion.setText("");
			this.campoTextoDescripcion.setDisable(false);	
		}else if (this.opcion == VER){
			this.campoTextoCodigo.setText(grupoTrabajo.getCodigo());
			this.campoTextoCodigo.setDisable(true);
			this.campoTextoDescripcion.setText(grupoTrabajo.getDescripcion());
			this.campoTextoDescripcion.setDisable(true);	
		} else if (this.opcion == EDITAR) {
			this.campoTextoCodigo.setText(grupoTrabajo.getCodigo());
			this.campoTextoCodigo.setDisable(false);
			this.campoTextoDescripcion.setText(grupoTrabajo.getDescripcion());
			this.campoTextoDescripcion.setDisable(false);
		}//FIN ELSE-IF
	}//FIN METODO COMPONENTES
	
	//VALIDAR DATOS
	private boolean validarDatos() {
		if (this.campoTextoCodigo.getText().isEmpty()) {
			Notificacion.dialogoAlerta(AlertType.INFORMATION,"","El campo \"Código\" no puede estar vacio");
			return false;
		} else if (this.campoTextoDescripcion.getText().isEmpty()){
			Notificacion.dialogoAlerta(AlertType.INFORMATION, "", "El campo \"Descripción\" no puede estar vacio");
			return false;
		}//FIN ELSE-IF
		return true;
	}//FIN METODO VALIDAR
	
	//MANEJADOR DE COMPONENTES
	//MANEJADOR BOTON ACEPTAR
	@FXML private void manejadorBotonAceptar() {
		if (validarDatos()) {
			if (opcion == CREAR) {
				this.grupoTrabajo.setCodigo(this.campoTextoCodigo.getText());
				this.grupoTrabajo.setDescripcion(this.campoTextoDescripcion.getText());
				if (GrupoTrabajoDAO.createGrupoTrabajo(this.conexion, this.grupoTrabajo)) {
					Notificacion.dialogoAlerta(AlertType.INFORMATION, "", "El registro se creo de forma correcta");
					this.mainApp.getEscenarioDialogos().close();
				} else 
					Notificacion.dialogoAlerta(AlertType.INFORMATION, "", "No se puedo crear el registro, revisa que la informacion sea correcta");
			}else if ( opcion == EDITAR) {
				this.grupoTrabajo.setCodigo(this.campoTextoCodigo.getText());
				this.grupoTrabajo.setDescripcion(this.campoTextoDescripcion.getText());		
				if (GrupoTrabajoDAO.updateGrupoTrabajo(this.conexion, this.grupoTrabajo)) {
					Notificacion.dialogoAlerta(AlertType.INFORMATION, "", "El registro se actualizo de forma correcta");
					this.mainApp.getEscenarioDialogos().close();
				} else 
					Notificacion.dialogoAlerta(AlertType.INFORMATION,"", "No se pudo actualizar el registro, revisa que la informacion sea correcta");
			}else if (opcion == VER) {
				this.mainApp.getEscenarioDialogos().close();
			}
		}//FIN ELSE-IF
	}//FIN MANEJADOR BOTON ACEPTAR
	
	@FXML 
	private void manejadorBotonCerrar() {
		this.mainApp.getEscenarioDialogos().close();
	}//FIN BOTON CERRAR
}



