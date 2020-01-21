package mx.shf6.produccion.view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import mx.shf6.produccion.MainApp;
import mx.shf6.produccion.model.Componente;
import mx.shf6.produccion.model.DetalleComponente;
import mx.shf6.produccion.model.dao.ComponenteDAO;
import mx.shf6.produccion.utilities.AutoCompleteComboBoxListener;
import mx.shf6.produccion.utilities.Notificacion;

public class DialogoAgregarDetalleComponente {

	//PROPIEDADES
	private MainApp mainApp;
	private Componente componente;
	private DetalleComponente detalleComponente;
	
	//COMPONENTES INTERFAZ
	@FXML private ComboBox<String> comboBoxComponentes;
	@FXML private TextField campoTextoCantidad;
	@FXML private TextField campoTextoNotas;
	
	//INICIA COMPONENTES INTERFAZ USUARIO
	@FXML private void initialize() {
		this.componente = new Componente();
		this.detalleComponente = new DetalleComponente();
		this.inicializarComponentes();
	}//FIN METODO
	
	//ACCESO CLASE PRINCIPAL
	public void setMainApp(MainApp mainApp, Componente componente) {
		this.mainApp = mainApp;
		this.componente = componente;
		ObservableList<String> listaComponentes = ComponenteDAO.listaNumerosParte(this.mainApp.getConnection());
		this.comboBoxComponentes.setItems(listaComponentes);
		new AutoCompleteComboBoxListener(comboBoxComponentes);
	}//FIN METODO
	
	//INICIALIZA COMPONENTE
	private void inicializarComponentes() {
		// VALIDA ENTRADA NUMERICA
		this.campoTextoCantidad.textProperty().addListener(new ChangeListener<String>() {
		    @Override
		    public void changed(ObservableValue<? extends String> observable, String oldValue, 
		        String newValue) {
		        if (!newValue.matches("\\d*\\.?"))
		        	campoTextoCantidad.setText(newValue.replaceAll("[^\\d^\\.]", ""));
		    }//FIN LISTENER
		});//FIN SENTENCIA
		
		//MANEJADOR PARA TEXTFIELD		
		this.campoTextoCantidad.setOnKeyPressed(new EventHandler<KeyEvent>() {
    		@Override
    		public void handle(KeyEvent event) {
    			if (event.getCode().equals(KeyCode.ENTER))
    				validarDatos();
    		}//FIN METODO
    	});//FIN SENTENCIA
	}//FIN METODO
	
	//VALIDAR DATOS
	private void validarDatos() {
		if (this.comboBoxComponentes.getSelectionModel().getSelectedItem().isEmpty())
			Notificacion.dialogoAlerta(AlertType.ERROR, "", "El campo \"Componente\" no puedes estar vacio");
		else if (this.comboBoxComponentes.getSelectionModel().getSelectedItem().isEmpty())
			Notificacion.dialogoAlerta(AlertType.ERROR, "", "El campo \"Componente\" no puedes estar vacio");
		else {
		/*	this.detalleComponente.setComponenteSuperiorFK(this.componente.getSysPK());
			this.detalleComponente.setComponenteInferiorFK(ComponenteDAO.readComponenteNumeroParte(this.mainApp.getConnection(), this.comboBoxComponentes.getSelectionModel().getSelectedItem()).getSysPK());
			this.detalleComponente.setCantidad(Integer.parseInt(this.campoTextoCantidad.getText()));
			this.detalleComponente.setNotas(this.campoTextoNotas.getText());*/
			this.mainApp.getEscenarioDialogosAlterno().close();
		}//FIN METODO
	}//FIN METODO

	public DetalleComponente getDetalleComponente() {
		return this.detalleComponente;
	}//FIN METODO
	
	//MANEJADORES COMPONENTES	
	@FXML private void manejadorBotonAceptar() {
		this.validarDatos();
	}//FIN METODO
	
	@FXML private void manejadorBotonCerrar() {
		this.detalleComponente = null;
		this.mainApp.getEscenarioDialogosAlterno().close();
	}//FIN METODO
	
}//FIN CLASE
