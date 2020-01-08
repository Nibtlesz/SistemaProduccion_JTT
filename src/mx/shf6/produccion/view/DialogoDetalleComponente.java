package mx.shf6.produccion.view;

import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import mx.shf6.produccion.MainApp;
import mx.shf6.produccion.model.Componente;
import mx.shf6.produccion.model.DetalleComponente;
import mx.shf6.produccion.model.dao.DetalleComponenteDAO;
import mx.shf6.produccion.utilities.Notificacion;
import mx.shf6.produccion.utilities.PTableColumn;

public class DialogoDetalleComponente {

	//PROPIEDADES
	private MainApp mainApp;
	private Componente componente;
	private ArrayList<DetalleComponente> listaDetalleComponente;
	
	//VARIABLES
	
	//COMPONENTES INTERFAZ
	@FXML private TextField campoTextoDescripcion;
	@FXML private TextField campoTextoNumeroParte;
	@FXML private TableView<DetalleComponente> tablaDetalleComponente;
	@FXML private PTableColumn<DetalleComponente, String> columnaNumeroParte;
	@FXML private PTableColumn<DetalleComponente, String> columnaDescripcion;
	@FXML private PTableColumn<DetalleComponente, Double> columnaCantidad;
	@FXML private PTableColumn<DetalleComponente, String> columnaNotas;
	
	//INICIA COMPONENTES INTERFAZ USUARIO
	@FXML private void initialize() {
		this.componente = new Componente();
	}//FIN METODO
	
	//ACCESO CLASE PRINCIPAL
	public void setMainApp(MainApp mainApp, Componente componente) {
		this.mainApp = mainApp;
		this.componente = componente;
		this.listaDetalleComponente = DetalleComponenteDAO.readDetalleComponenteSuperiorFK(this.mainApp.getConnection(), componente.getSysPK());
		this.inicializarTabla();
		this.actualizarDatos();
		this.actualizarTabla();
	}//FIN METODO
	
	//INICIALIZA TABLA
	private void inicializarTabla() {
		this.columnaNumeroParte.setCellValueFactory(cellData -> cellData.getValue().getComponenteInferior(this.mainApp.getConnection()).numeroParteProperty());
		this.columnaDescripcion.setCellValueFactory(cellData -> cellData.getValue().getComponenteInferior(this.mainApp.getConnection()).descripcionProperty());
		this.columnaCantidad.setCellValueFactory(cellData -> cellData.getValue().cantidadProperty());
		this.columnaNotas.setCellValueFactory(cellData -> cellData.getValue().notasProperty());
	}//FIN METODO
	
	//ACTUALIZAR DATOS
	private void actualizarDatos() {
		this.campoTextoDescripcion.setText(this.componente.getDescripcion());
		this.campoTextoNumeroParte.setText(this.componente.getNumeroParte());
	}//FIN METODO
	
	//ACTUALIZAR TABLA
	private void actualizarTabla() {
		this.tablaDetalleComponente.setItems(null);
		this.listaDetalleComponente.clear();
		this.listaDetalleComponente = DetalleComponenteDAO.readDetalleComponenteSuperiorFK(this.mainApp.getConnection(), this.componente.getSysPK());
		this.tablaDetalleComponente.setItems(DetalleComponenteDAO.toObservableList(this.listaDetalleComponente));
	}//FIN METODO
		
	//AGREGAR UN COMPONENTE AL DETALLE
	private void agregarComponente() {
		DetalleComponente detalleComponente = new DetalleComponente();
		detalleComponente = this.mainApp.iniciarDialogoAgregarDetalleComponente(this.componente);
		if (detalleComponente != null) {
			DetalleComponenteDAO.createDetalleComponente(this.mainApp.getConnection(), detalleComponente);
			this.actualizarTabla();
		} else
			Notificacion.dialogoAlerta(AlertType.ERROR, "", "No se pudo agregar el componente");
	}//FIN METODO
	
	//ELIMINA EL COMPONENTE AL DETALLE
	private void quitarComponente() {
		DetalleComponente detalleComponente = this.tablaDetalleComponente.getSelectionModel().getSelectedItem();
		if (detalleComponente != null) {
			if (Notificacion.dialogoPreguntar("", "¿Realmente deseas quitar el componente?")) {
				DetalleComponenteDAO.deleteDetalleComponente(this.mainApp.getConnection(), detalleComponente);
				this.actualizarTabla();
			}//FIN IF
		} else
			Notificacion.dialogoAlerta(AlertType.ERROR, "", "Debes seleccionar un registro");
	}//FIN METODO
		
	//MANEJADORES COMPONENTES
	@FXML private void manejadorBotonCerrar() {
		this.mainApp.getEscenarioDialogos().close();
	}//FIN METODO
	
	@FXML private void manejadorBotonAgregarComponente() {
		this.agregarComponente();
	}//FIN METODO
	
	@FXML private void manejadorBotonQuitarComponente() {
		this.quitarComponente();
	}//FIN METODO
	
}//FIN CLASE
	