package mx.shf6.produccion.view;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import mx.shf6.produccion.MainApp;
import mx.shf6.produccion.model.DetalleHojaViajera;
import mx.shf6.produccion.model.DetalleProceso;
import mx.shf6.produccion.model.HojaViajera;
import mx.shf6.produccion.model.dao.ComponenteDAO;
import mx.shf6.produccion.model.dao.DetalleHojaViajeraDAO;
import mx.shf6.produccion.utilities.Notificacion;
import mx.shf6.produccion.utilities.PTableColumn;

public class DialogoDetalleHojaViajera {

	//PROPIEDADES
	private MainApp mainApp;
	private Connection conexion;
	private HojaViajera hojaViajera;
	private ArrayList<DetalleHojaViajera> listaDetallesHojaViajera;
	//private ArrayList<DetalleProceso> listaDetalleProceso;

	//VARIABLES
	Double cantidad = 0.0;
	int i = 0;
	int tamañoArrayPartesPrimarias = 0;
	String nombreNumeroComponente;

	//CONSTANTES

	//COMPONENTES INTERFAZ
	@FXML private TableView<DetalleHojaViajera> tablaDetalleHojaViajera;
	@FXML private PTableColumn<DetalleHojaViajera, Integer> columnaOperacion;
	@FXML private PTableColumn<DetalleHojaViajera, String> columnaProceso;
	@FXML private PTableColumn<DetalleHojaViajera, Integer> columnaEnProceso;
	@FXML private PTableColumn<DetalleHojaViajera, Integer> ColumnaTerminado;
	@FXML private PTableColumn<DetalleHojaViajera, Timestamp> columnaFHInicio;
	@FXML private PTableColumn<DetalleHojaViajera, Timestamp> columnaFHFinal;
	@FXML private Label campoTextoTitulo;
	@FXML private Label campoTextoSubtitulo;


	//INICIA COMPONENTES INTERFAZ USUARIO
	@FXML private void initialize() {
		inicializarTabla();
	}// FIN METODO


	//ACCESO CLASE PRINCIPAL
	public void setMainApp(MainApp mainApp, HojaViajera hojaViajera, ArrayList<DetalleProceso> listaDetalleProceso) {
		this.mainApp = mainApp;
		this.conexion = this.mainApp.getConnection();
		this.hojaViajera = hojaViajera;
		this.listaDetallesHojaViajera = new ArrayList<DetalleHojaViajera>();
		//this.listaDetalleProceso = listaDetalleProceso;
		actualizarTabla();
		inicializarComponentes();
	}//FIN METODO

	private void inicializarTabla() {
		this.columnaOperacion.setCellValueFactory(cellData -> cellData.getValue().detalleProcesoOperacionProperty());
		this.columnaProceso.setCellValueFactory(cellData -> cellData.getValue().detalleProcesoDescripcionProperty());
		this.columnaEnProceso.setCellValueFactory(cellData -> cellData.getValue().cantidadEnProcesoProperty());
		this.ColumnaTerminado.setCellValueFactory(cellData -> cellData.getValue().cantidadTerminadoProperty());
		this.columnaFHInicio.setCellValueFactory(cellData -> cellData.getValue().fechaHoraInicioProperty());
		this.columnaFHFinal.setCellValueFactory(cellData -> cellData.getValue().fechaHoraFinalProperty());
	}//FIN METODO
	
	private void inicializarComponentes() {
		this.campoTextoTitulo.setText(ComponenteDAO.readComponente(this.conexion, this.hojaViajera.getComponenteFK()).getNumeroParte());
		this.campoTextoSubtitulo.setText("Control de producción de componentes primarios");
	}//FIN METODO
	
	private void actualizarTabla(){
		this.tablaDetalleHojaViajera.setItems(null);
		this.listaDetallesHojaViajera = DetalleHojaViajeraDAO.readHojaViajeraPorOrdenProduccionComponente(this.conexion, this.hojaViajera.getSysPK());
		this.tablaDetalleHojaViajera.setItems(FXCollections.observableArrayList(listaDetallesHojaViajera));
	}//FIN METODO
	

	//MANEJADORES
	@FXML private void manejadorBotonLiberar() {
		if (this.tablaDetalleHojaViajera.getSelectionModel().getSelectedItem() != null) {
			this.mainApp.iniciarDialogoActualizarDetalleHojaViajera(this.tablaDetalleHojaViajera.getSelectionModel().getSelectedItem(), this.listaDetallesHojaViajera.size(), DialogoActualizarDetalleHojaViajera.LIBERAR);
		} else
			Notificacion.dialogoAlerta(AlertType.WARNING, "", "Debes seleccionar un registro de la tabla");
		this.actualizarTabla();
	}//FIN METODO

	@FXML private void manejadorBotonCerrar() {
		this.mainApp.getEscenarioDialogosAlterno().close();
	}//FIN METODO

	@FXML private void manejadorBotonImprimir() {
	}//FIN METODO
}//FIN CLASE
