package mx.shf6.produccion.view;



import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import mx.shf6.produccion.MainApp;
import mx.shf6.produccion.model.ArchivoProyecto;
import mx.shf6.produccion.model.Proyecto;
import mx.shf6.produccion.model.dao.ArchivoProyectoDAO;
import mx.shf6.produccion.utilities.Notificacion;
import mx.shf6.produccion.utilities.RestriccionTextField;

public class DialogoArchivoProyecto {
	//PROPIEDADES
	private MainApp mainApp;
	private Proyecto proyecto;
	private ArchivoProyecto archivoProyecto;

	
	
	//VARIABLES
	private int opcion;
	
	//CONSTANTES
	public static final int CREAR = 1;
	public static final int VER = 2;
	public static final int EDITAR = 3;
	
	//COMPONENTES INTERFAZ
	@FXML private TextField campoTextoCodigo;
	@FXML private TextField campoTextoDescripcion;

		
	
	//INICIA COMPONENTES INTERFAZ USUARIO
	@FXML private void initialize() {
		this.proyecto = new Proyecto();
		this.archivoProyecto = new ArchivoProyecto();
		
		RestriccionTextField.limitarNumeroCaracteres(this.campoTextoCodigo, 16);		
	}//FIN METODO
	
	//ACCESO CLASE PRINCIPAL
	public void setMainApp(MainApp mainApp, ArchivoProyecto archivoProyecto , int opcion , Proyecto proyecto) {
		this.mainApp = mainApp;
		this.archivoProyecto = archivoProyecto;
		this.opcion =opcion;
		this.proyecto = proyecto;		
		this.inicializarComponentes();
		
	}//FIN METODO
	
	//INICIALIZA COMPONENTE
	private void inicializarComponentes() {		
		if (this.opcion == CREAR) {
			this.campoTextoCodigo.setText("");
			this.campoTextoCodigo.setDisable(false);
			this.campoTextoDescripcion.setText("");
			this.campoTextoDescripcion.setDisable(false);
			
			
			
		} else if (this.opcion == VER) {
			this.campoTextoCodigo.setText(this.archivoProyecto.getCodigo());
			this.campoTextoCodigo.setDisable(true);
			this.campoTextoDescripcion.setText(this.archivoProyecto.getDescripcion());
			this.campoTextoDescripcion.setDisable(true);
		
		} else if (this.opcion == EDITAR) {
			this.campoTextoCodigo.setText(this.archivoProyecto.getCodigo());
			this.campoTextoCodigo.setDisable(false);
			this.campoTextoDescripcion.setText(this.archivoProyecto.getDescripcion());
			this.campoTextoDescripcion.setDisable(false);
			
		}//FIN METODO
	}//FIN METODO
	
	//VALIDAR DATOS
	private boolean validarDatos() {
		if (this.campoTextoCodigo.getText().isEmpty()) {
			Notificacion.dialogoAlerta(AlertType.ERROR, "", "El campo \"Código\" no puede estar vacio");
			return false;
		} else if (this.campoTextoDescripcion.getText().isEmpty()) {
			Notificacion.dialogoAlerta(AlertType.ERROR, "", "El campo \"Descripción\" no puede estar vacio");
			return false;
		}//FIN IF/ESLE
		return true;
	}//FIN METODO
	
	//MANEJADORES COMPONENTES	
	@FXML private void vmanejadorBotonAceptar() {
			
		if(this.validarDatos()) {
			if (this.opcion == CREAR) {
				this.archivoProyecto.setCodigo(this.campoTextoCodigo.getText());
				this.archivoProyecto.setDescripcion(this.campoTextoDescripcion.getText());								
			    this.archivoProyecto.setProyectoFK(this.proyecto.getSysPK());
			    				
				if (ArchivoProyectoDAO.createArchivoProyecto(this.mainApp.getConnection(), this.archivoProyecto)) {
					Notificacion.dialogoAlerta(AlertType.INFORMATION, "", "El registro se creo de forma correcta");
					this.mainApp.getEscenarioDialogosAlterno().close();
				} else
					Notificacion.dialogoAlerta(AlertType.INFORMATION, "", "No se pudo crear el registro, revisa que la información sea correcta");
			} else if (this.opcion == EDITAR) {
				this.archivoProyecto.setCodigo(this.campoTextoCodigo.getText());
				this.archivoProyecto.setDescripcion(this.campoTextoDescripcion.getText());
								
				if (ArchivoProyectoDAO.updateArchivoProyecto(this.mainApp.getConnection(), this.archivoProyecto)) {
					Notificacion.dialogoAlerta(AlertType.INFORMATION, "", "El registro se actualizo de forma correcta");
					this.mainApp.getEscenarioDialogosAlterno().close();
				} else
					Notificacion.dialogoAlerta(AlertType.INFORMATION, "", "No se pudo actualizar el registro, revisa que la información sea correcta");
			} else if ( this.opcion == VER) {
				this.mainApp.getEscenarioDialogosAlterno().close();
			}//FIN ELSE IF
		}//FIN IF
		
	}//FIN METODO
	
	@FXML private void manejadorBotonCerrar() {
		this.mainApp.getEscenarioDialogosAlterno().close();
	}//FIN METODO
}
