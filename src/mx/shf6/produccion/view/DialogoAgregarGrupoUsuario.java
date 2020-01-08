package mx.shf6.produccion.view;

import java.sql.Connection;

import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import mx.shf6.produccion.MainApp;
import mx.shf6.produccion.model.GrupoUsuario;
import mx.shf6.produccion.model.dao.GrupoUsuarioDAO;
import mx.shf6.produccion.utilities.Notificacion;

public class DialogoAgregarGrupoUsuario {
	
	//PROPIEDADES
	private MainApp mainApp;
	private Connection connection;
	private GrupoUsuario grupoUsuario;
	
	//VARIABLES
	private int opciones;
	
	//CONSTANTES
	public static final int CREAR = 1;
	public static final int MODIFICAR = 2;
	public static final int VER = 3;
	
	//COMPONENTES INTERFAZ
	@FXML private TextField campoGrupo;
	@FXML private TextField campoDescripcion;
	
	//METODOS
	@FXML private void initialize() {
		
	}//FIN METODO
	
	public void setMainApp(MainApp mainApp, GrupoUsuario grupoUsuario, int opciones) {
		this.mainApp = mainApp;
		this.connection = this.mainApp.getConnection();
		this.grupoUsuario = grupoUsuario;
		this.opciones = opciones;
		iniciarComponentes();
	}//FIN METODO
	
	private void iniciarComponentes() {
		if (this.opciones == CREAR) {
			this.campoGrupo.setText("");
			this.campoGrupo.setDisable(false);
			this.campoDescripcion.setText("");
			this.campoDescripcion.setDisable(false);
		}else if (this.opciones == MODIFICAR) {
			this.campoGrupo.setText(this.grupoUsuario.getNombre());
			this.campoGrupo.setDisable(false);
			this.campoDescripcion.setText(this.grupoUsuario.getDescripcion());
			this.campoDescripcion.setDisable(false);
		}else if (this.opciones == VER) {
			this.campoGrupo.setText(this.grupoUsuario.getNombre());
			this.campoGrupo.setDisable(true);
			this.campoDescripcion.setText(this.grupoUsuario.getDescripcion());
			this.campoDescripcion.setDisable(true);
		}//FIN IF-ELSE
	}//FIN METODO
	
	private boolean validacion() {
		if (this.campoGrupo.getText().isEmpty()) {
			Notificacion.dialogoAlerta(AlertType.ERROR, "", "El campo Grupo no puede estar vacio.");
			return false;
		}else if (this.campoDescripcion.getText().isEmpty()) {
			Notificacion.dialogoAlerta(AlertType.ERROR, "", "El campo Descripción no puede estar vacio");
			return false;
		}//FIN IF-ELSE
		return true;
	}//FIN METODO
	
	private void asignarDatos() {
		if (this.validacion()) {
			if (this.opciones == CREAR) {
				this.grupoUsuario.setNombre(this.campoGrupo.getText());
				this.grupoUsuario.setDescripcion(this.campoDescripcion.getText());
				if (GrupoUsuarioDAO.create(this.connection, this.grupoUsuario)) {
					Notificacion.dialogoAlerta(AlertType.INFORMATION, "", "El registro se creo de forma correcta");
					this.mainApp.getEscenarioDialogosAlterno().close();
				} else
					Notificacion.dialogoAlerta(AlertType.INFORMATION, "", "No se pudo crear el registro, revisa que la información sea correcta");
				//FIN IF-ELSE
			}else if (this.opciones == MODIFICAR) {
				this.grupoUsuario.setNombre(this.campoGrupo.getText());
				this.grupoUsuario.setDescripcion(this.campoDescripcion.getText());
				if (GrupoUsuarioDAO.update(this.connection, this.grupoUsuario)) {
					Notificacion.dialogoAlerta(AlertType.INFORMATION, "", "El registro se creo de forma correcta");
					this.mainApp.getEscenarioDialogosAlterno().close();
				} else
					Notificacion.dialogoAlerta(AlertType.INFORMATION, "", "No se pudo crear el registro, revisa que la información sea correcta");
				//FIN IF - ELSE
			}else if (this.opciones == VER) {
				this.mainApp.getEscenarioDialogosAlterno().close();
			}//FIN IF-ELSE
		}//FIN IF
	}//FIN METODO
	
	//MANEJADORES
	@FXML private void manejadorBotonAceptar() {
		this.asignarDatos();
	}//FIN METODO
	
	@FXML private void manejadorBotonCancelar() {
		this.mainApp.getEscenarioDialogosAlterno().close();
	}//FIN METODO
}//FIN METODO
