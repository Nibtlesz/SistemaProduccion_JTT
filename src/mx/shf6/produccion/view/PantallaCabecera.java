package mx.shf6.produccion.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import mx.shf6.produccion.MainApp;

public class PantallaCabecera {

	//PROPIEDADES
	private MainApp mainApp;
	
	//COMPONENTES INTERFAZ USUARIO
	@FXML private Label labelBienvenida;
	
	//VARIABLES
	
	//INICIALIZA COMPONENTES INTERFAZ DE USUARIO
	@FXML private void initialize() {
	}//FIN METODO
	
	//ACCESO CLASE PRINCIPAL
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
		this.labelBienvenida.setText("¡Buen día, " + this.mainApp.getUsuario().getUsuario() + "!");
	}//FIN METODO
	
	//MANEJADORES COMPONENTES
	@FXML private void cerrarButtonHandler() {
		this.mainApp.stop();
	}//FIN METODO
	
	@FXML private void minimizarHandler() {
		this.mainApp.iniciarPantallaInicio();
	}//FIN METODO
	
}//FIN CLASE
