package mx.shf6.produccion.view;

import javafx.fxml.FXML;
import mx.shf6.produccion.MainApp;

public class PantallaInicio {

	//PROPIEDADES
	private MainApp mainApp;
	
	//COMPONENTES INTERFAZ DE USUARIO
	
	//INICIALIZA LOS COMPONENTES QUE SE CONTROLAN EN LA INTERFAZ DE USUARIO
	@FXML private void initialize() {
		
	}//FIN METODO
	
	//ACCESO A LA CLASE PRINCIPAL QUE CONTOLA LAS VISTAS
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}//FIN METODO
	
	@FXML private void cerrarButtonHandler() {
		this.mainApp.stop();
	}//FIN HANDLER
	
	@FXML private void sesionButtonHandler() {
		this.mainApp.iniciarPantallaSesion();
	}//FIN HANDLER
	
}//FIN CLASE
