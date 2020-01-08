package mx.shf6.produccion.view;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import mx.shf6.produccion.MainApp;
import mx.shf6.produccion.model.Cliente;
import mx.shf6.produccion.model.Comprador;
import mx.shf6.produccion.model.dao.CompradorDAO;
import mx.shf6.produccion.utilities.Notificacion;
import mx.shf6.produccion.utilities.RestriccionTextField;

public class DialogoAgregarComprador {
	
	//PROPIEDADES
	private MainApp mainApp;
	private Comprador comprador;
	private Cliente cliente;
	
	//VARIABLES
	private int opcion;
	
	//CONSTANTES
	public static final int CREAR = 1;
	public static final int VER = 2;
	public static final int EDITAR = 3;
	
	//COMPONENTES INTERFAZ
	@FXML private TextField campoNombre;
	@FXML private TextField campoCorreo;
	@FXML private TextField campoTelefono;
	@FXML private TextField campoTelefonoAuxiliar;
	@FXML private TextField campoAreaDepartamento;
	
	//INICIA COMPONENTES INTERFAZ USUARIO
	@FXML private void initialize() {
		RestriccionTextField.limitarNumeroCaracteres(campoNombre, 64);
		RestriccionTextField.limitarNumeroCaracteres(campoTelefono, 16);
		//RestriccionTextField.limitarNumeroCaracteres(campoTelefonoAuxiliar, 16);
		RestriccionTextField.limitarNumeroCaracteres(campoCorreo, 64);
		RestriccionTextField.limitarNumeroCaracteres(campoAreaDepartamento, 32);
	}//FIN METODO
	
	public void setMainApp(MainApp mainApp, Comprador comprador, Cliente cliente, int opcion) {
		this.mainApp = mainApp;
		this.cliente = cliente;
		this.opcion = opcion;
		this.comprador = comprador;
		this.inicializarComponentes();
	}
	
	public void inicializarComponentes() {
		if (this.opcion == CREAR) {
			this.campoNombre.setText("");
			this.campoNombre.setDisable(false);
			this.campoCorreo.setText("");
			this.campoCorreo.setDisable(false);
			this.campoTelefono.setText("");
			this.campoTelefono.setDisable(false);
			this.campoTelefonoAuxiliar.setText("");
			this.campoTelefonoAuxiliar.setDisable(false);
			this.campoAreaDepartamento.setText("");
			this.campoAreaDepartamento.setDisable(false);
		} else if (this.opcion == VER) {
			this.campoNombre.setText(this.comprador.getNombre());
			this.campoNombre.setDisable(true);
			this.campoCorreo.setText(this.comprador.getCorreo());
			this.campoCorreo.setDisable(true);
			this.campoTelefono.setText(this.comprador.getTelefono());
			this.campoTelefono.setDisable(true);
			this.campoTelefonoAuxiliar.setText(this.comprador.getTelefonoAuxiliar());
			this.campoTelefonoAuxiliar.setDisable(true);
			this.campoAreaDepartamento.setText(this.comprador.getAreaDepartamento());
			this.campoAreaDepartamento.setDisable(true);
		} else if (this.opcion == EDITAR) {
			this.campoNombre.setText(this.comprador.getNombre());
			this.campoNombre.setDisable(false);
			this.campoCorreo.setText(this.comprador.getCorreo());
			this.campoCorreo.setDisable(false);
			this.campoTelefono.setText(this.comprador.getTelefono());
			this.campoTelefono.setDisable(false);
			this.campoTelefonoAuxiliar.setText(this.comprador.getTelefonoAuxiliar());
			this.campoTelefonoAuxiliar.setDisable(false);
			this.campoAreaDepartamento.setText(this.comprador.getAreaDepartamento());
			this.campoAreaDepartamento.setDisable(false);
		}//FIN IF ELSE
	}//FIN METODO
	
	//VALIDAR DATOS
	private boolean validarDatos() {
		if (this.campoNombre.getText().isEmpty()) {
			Notificacion.dialogoAlerta(AlertType.ERROR, "", "El campo \"Nombre\" no puede estar vacio");
			return false;
		} else if (this.campoCorreo.getText().isEmpty()) {
			Notificacion.dialogoAlerta(AlertType.ERROR, "", "El campo \"Correo\" no puede estar vacio");
			return false;
		} else if (this.campoTelefono.getText().isEmpty()) {
			Notificacion.dialogoAlerta(AlertType.ERROR, "", "El campo \"Teléfono\" no puede estar vacio");
			return false;
		} else if (this.campoAreaDepartamento.getText().isEmpty()) {
			Notificacion.dialogoAlerta(AlertType.ERROR, "", "El campo \"Area / Departamento \" no puede estar vacio");
			return false;
		} else if (RestriccionTextField.validarEmail(this.campoCorreo.getText()) == false) {
			Notificacion.dialogoAlerta(AlertType.ERROR, "", "El correo no es valido");
			return false;
		}//FIN IF ELSE
		return true;
	}//FIN METODO
	
	//MANEJADORES
	@FXML private void manejadorBotonAceptar() {
		if (this.validarDatos()) {
			if (this.opcion == CREAR) {
				this.comprador = new Comprador();
				this.comprador.setNombre(this.campoNombre.getText());
				this.comprador.setCorreo(this.campoCorreo.getText());
				this.comprador.setTelefono(this.campoTelefono.getText());
				this.comprador.setTelefonoAuxiliar(this.campoTelefonoAuxiliar.getText());
				this.comprador.setAreaDepartamento(this.campoAreaDepartamento.getText());
				this.comprador.setClienteFK(cliente.getSysPK());
				
				if (CompradorDAO.createCromprador(this.mainApp.getConnection(), comprador)) {
					Notificacion.dialogoAlerta(AlertType.INFORMATION, "", "Se creo exitosamente el registro.");
					this.mainApp.getEscenarioDialogosAlterno().close();
				} else {
					Notificacion.dialogoAlerta(AlertType.INFORMATION, "", "No se pudo crear el registro, revise la información introducida.");
				}//FIN IF ELSE	
			} else if (this.opcion == EDITAR) {
				this.comprador.setNombre(this.campoNombre.getText());
				this.comprador.setCorreo(this.campoCorreo.getText());
				this.comprador.setTelefono(this.campoTelefono.getText());
				this.comprador.setTelefonoAuxiliar(this.campoTelefonoAuxiliar.getText());
				this.comprador.setAreaDepartamento(this.campoAreaDepartamento.getText());
				this.comprador.setClienteFK(cliente.getSysPK());
				
				if (CompradorDAO.updateComprador(this.mainApp.getConnection(), comprador)) {
					Notificacion.dialogoAlerta(AlertType.INFORMATION, "", "Se actualizáron exitosamente el registro.");
					this.mainApp.getEscenarioDialogosAlterno().close();
				} else {
					Notificacion.dialogoAlerta(AlertType.INFORMATION, "", "No se pudo actualizar el registro, revise la información introducida.");
				}//FIN IF ELSE	
			} else if (this.opcion == VER) {
				this.mainApp.getEscenarioDialogosAlterno().close();
			}//FIN IF ELSE
		}//FIN IF ELSE
	}//FIN METODO
	
	@FXML private void manejadorBotonCerrar() {
		this.mainApp.getEscenarioDialogosAlterno().close();
	}//FIN METODO
	
}//FIN CLASE
