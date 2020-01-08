package mx.shf6.produccion.view;

import java.sql.Connection;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import mx.shf6.produccion.MainApp;
import mx.shf6.produccion.model.Almacen;
import mx.shf6.produccion.model.dao.AlmacenDAO;
import mx.shf6.produccion.utilities.Notificacion;
import javafx.scene.control.Alert.AlertType;


public class DialogoAlmacen {

	// PROPIEDADES
	private MainApp mainApp;
	private Almacen almacen;
	private Connection conexion;

	// VARIABLES
	private int opcion;

	// CONSTANTES
	public final static int MOSTRAR = 1;
	public final static int CREAR = 2;
	public final static int EDITAR = 3;

	// COMPONENTES INTERFAZ
	@FXML private TextField campoTextoCodigo;
	@FXML private TextField campoTextoDescripcion;

	//METODOS
	@FXML private void initialize() {
	}// FIN METODO

	public void setMainApp(MainApp mainApp, Almacen almacen, Integer opcion) {
		this.mainApp = mainApp;
		this.conexion = this.mainApp.getConnection();
		this.almacen = almacen;
		this.opcion = opcion;
		this.mostrarDatosInterfaz();
	}// FIN METODO

	public void mostrarDatosInterfaz() {
		if (opcion == MOSTRAR) {
			this.campoTextoCodigo.setText(this.almacen.getCodigo());
			this.campoTextoCodigo.setDisable(true);
			this.campoTextoDescripcion.setText(this.almacen.getDescripcion());
			this.campoTextoDescripcion.setDisable(true);
		} else if (this.opcion == CREAR) {
			this.campoTextoCodigo.setText("");
			this.campoTextoCodigo.setDisable(false);
			this.campoTextoDescripcion.setText("");
			this.campoTextoDescripcion.setDisable(false);
		} else if (opcion == EDITAR) {
			this.campoTextoCodigo.setText(this.almacen.getCodigo());
			this.campoTextoCodigo.setDisable(false);
			this.campoTextoDescripcion.setText(this.almacen.getDescripcion());
			this.campoTextoDescripcion.setDisable(false);
		} // FIN ELSE IF
	}// FIN METODO

	public boolean validacion() {
		if (this.campoTextoCodigo.getText().isEmpty()) {
			Notificacion.dialogoAlerta(AlertType.ERROR, "", "El campo \"codigo\" no puede estar vacio");
			return false;
		} else if (this.campoTextoDescripcion.getText().isEmpty()) {
			Notificacion.dialogoAlerta(AlertType.ERROR, "", "El campo \"descripción\" no puede estar vacio");
			return false;
		} // FIN IF/ESLE
		return true;
	}// FIN METODO

	private void accionarBotonAceptar() {
		this.almacen.setCodigo(this.campoTextoCodigo.getText());
		this.almacen.setDescripcion(this.campoTextoDescripcion.getText());

		if (this.opcion == MOSTRAR)
			this.mainApp.getEscenarioDialogos().close();

		if (this.validacion()) {
			if (this.opcion == CREAR) {
				if (AlmacenDAO.create(this.conexion, almacen)) {
					 Notificacion.dialogoAlerta(AlertType.INFORMATION, "", "¡El registro se guardó de forma correcta!");
					this.mainApp.getEscenarioDialogos().close();
				} else
					Notificacion.dialogoAlerta(AlertType.INFORMATION, "","No se pudo crear el registro, revisa que la información sea correcta");
			} else if (this.opcion == EDITAR) {
				if (AlmacenDAO.update(this.conexion, almacen)) {
					 Notificacion.dialogoAlerta(AlertType.INFORMATION, "", "¡El registro se actualizó de forma correcta!");
					this.mainApp.getEscenarioDialogos().close();
				} else
					Notificacion.dialogoAlerta(AlertType.INFORMATION, "","No se pudo actualizar el registro, revisa que la información sea correcta");
			} // FIN IF-ELSE
		} // FIN IF
	}// FIN METODO

	// MANEJADOR DE COMPONENTES
	@FXML
	private void manejadorBotonAceptar() {
		this.accionarBotonAceptar();
	}// FIN METODO

	@FXML
	private void manejadorBotonCancelar() {
		this.mainApp.getEscenarioDialogos().close();
	}// FIN METODO

}
