package mx.shf6.produccion.view;

import java.sql.Connection;
import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import mx.shf6.produccion.MainApp;
import mx.shf6.produccion.model.Componente;
import mx.shf6.produccion.model.DetalleProceso;
import mx.shf6.produccion.model.Proceso;
import mx.shf6.produccion.model.TipoComponente;
import mx.shf6.produccion.model.dao.DetalleProcesoDAO;
import mx.shf6.produccion.utilities.Notificacion;
import mx.shf6.produccion.utilities.PTableColumn;


public class DialogoDetalleProceso {
	
	//CONSTANTES
	public static int VER = 1;
	public static int CREAR = 2;
	public static int EDITAR = 3;

	//PROPIEDADES
	private MainApp mainApp;
	private Connection conexion;
	private DetalleProceso detalleProceso;
	private Proceso proceso;
	private ArrayList<DetalleProceso> listaDetalleProceso;
	private Componente componente;
	
	//VARIABLES
	
	//COMPONENTES INTERFAZ USUARIO
	@FXML private TextField campoTextoDescripcion;
	@FXML private TextField campoTextoNumero;
	@FXML private TableView<DetalleProceso> tablaDetalleProceso;
	@FXML private PTableColumn<DetalleProceso, Integer> operacionColumna;
	@FXML private PTableColumn<DetalleProceso, String> descripcionColumna;
	@FXML private PTableColumn<DetalleProceso, Double> tiempoPreparacionColumna;
	@FXML private PTableColumn<DetalleProceso, Double> tiempoOperacionColumna;
	@FXML private PTableColumn<DetalleProceso, String> centroTrabajoColumna;
	@FXML private PTableColumn<DetalleProceso, String> grupoTrabajoColumna;
	@FXML private PTableColumn<DetalleProceso, String> componenteColumna;
	@FXML private PTableColumn<DetalleProceso, String> cantidadColumna;
	@FXML private PTableColumn<DetalleProceso, String> herramientaColumna;
	
	//INICIALIZAR COMPONENTES CONTROLAN INTERFAZ USUARIO
	@FXML private void initialize() {
		this.detalleProceso = new DetalleProceso();
	}//FIN METODO
	
	//ACCESO A CLASE PRINCIPAL CONTROLA VISTAS
	public void setMainApp(MainApp mainApp, Proceso proceso) {
		this.mainApp = mainApp;
		this.conexion = this.mainApp.getConnection();
		this.proceso = proceso;
		this.componente = this.proceso.getComponente(this.conexion);
		this.listaDetalleProceso = DetalleProcesoDAO.readDetalleProcesoFK(this.mainApp.getConnection(), proceso.getSysPK());
		this.inicializaTabla();
		this.actualizarTabla();
		this.actualizarDatos();
	}//FIN METODO

	
	//INICIALIZA LOS COMPONENTES DE LA TABLA DE PROCESOS
	private void inicializaTabla() {
		if (this.componente.getTipoComponente() != TipoComponente.ENSAMBLE && this.componente.getTipoComponente() != TipoComponente.SUB_ENSAMBLE) {
			this.operacionColumna.setCellValueFactory(cellData -> cellData.getValue().operacionProperty());
			this.descripcionColumna.setCellValueFactory(cellData -> cellData.getValue().descripcionProperty());
			this.tiempoPreparacionColumna.setCellValueFactory(cellData -> cellData.getValue().tiempoPreparacionProperty());
			this.tiempoOperacionColumna.setCellValueFactory(cellData -> cellData.getValue().tiempoOperacionProperty());
			this.centroTrabajoColumna.setCellValueFactory(cellData -> cellData.getValue().nombreCentroTrabajoProperty());
			this.grupoTrabajoColumna.setCellValueFactory(cellData -> cellData.getValue().nombreGrupoTrabajoProperty());
			this.componenteColumna.setPercentageWidth(0);
			this.cantidadColumna.setPercentageWidth(0);
			this.herramientaColumna.setPercentageWidth(0);
			this.descripcionColumna.setPercentageWidth(0.65);
		} else {
			this.operacionColumna.setCellValueFactory(cellData -> cellData.getValue().operacionProperty());
			this.descripcionColumna.setCellValueFactory(cellData -> cellData.getValue().descripcionProperty());
			this.tiempoPreparacionColumna.setCellValueFactory(cellData -> cellData.getValue().tiempoPreparacionProperty());
			this.tiempoOperacionColumna.setCellValueFactory(cellData -> cellData.getValue().tiempoOperacionProperty());
			this.centroTrabajoColumna.setCellValueFactory(cellData -> cellData.getValue().nombreCentroTrabajoProperty());
			this.grupoTrabajoColumna.setCellValueFactory(cellData -> cellData.getValue().nombreGrupoTrabajoProperty());
			this.componenteColumna.setCellValueFactory(cellData -> cellData.getValue().componentesProperty());
			this.cantidadColumna.setCellValueFactory(cellData -> cellData.getValue().cantidadProperty());
			this.herramientaColumna.setCellValueFactory(cellData -> cellData.getValue().herramientaProperty());
		}//FIN IF/ELSE
	}//FIN METODO
	
	//ACTUALIZAR DATOS
	private void actualizarDatos() {
		this.campoTextoDescripcion.setText("HOJA DE PROCESO " + componente.getTipoComponente());
		this.campoTextoDescripcion.setFocusTraversable(false);
		this.campoTextoNumero.setText(componente.getNumeroParte());
		this.campoTextoNumero.setFocusTraversable(false);
	}//FIN METODO
	
	//ACTUALIZA LA TABLA CON LOS ULTIMOS CAMBIOS EN LA BASE DE DATOS
	private void actualizarTabla() {
		this.tablaDetalleProceso.setItems(null);
		this.listaDetalleProceso.clear();
		this.listaDetalleProceso = DetalleProcesoDAO.readDetalleProcesoFK(this.mainApp.getConnection(), this.proceso.getSysPK());
		this.tablaDetalleProceso.setItems(DetalleProcesoDAO.toObservableList(this.listaDetalleProceso));
	}//FIN METODO
	
	//AGREGAR DETALLE DE PROCESO
	private void agregarDetalle() {
		DetalleProceso detalleProceso = new DetalleProceso();
		detalleProceso = this.mainApp.iniciarDialogoAgregarDetalleProceso(this.detalleProceso, DialogoAgregarDetalleProceso.CREAR, this.proceso.getSysPK(), this.componente);
		if (detalleProceso != null) {
			DetalleProcesoDAO.createDetalleProceso(this.mainApp.getConnection(), detalleProceso);
			this.actualizarTabla();
		} else {
			Notificacion.dialogoAlerta(AlertType.ERROR, "", "No se pudo agregar el detalle de proceso");
		}//FIN IF-ELSE
	}//FIN METODO	
	
	//MODIFICAR DETALLE DE PROCESO
	private void modificarDetalle() {
		DetalleProceso detalleProceso = this.tablaDetalleProceso.getSelectionModel().getSelectedItem();
		detalleProceso = this.mainApp.iniciarDialogoAgregarDetalleProceso(detalleProceso, DialogoAgregarDetalleProceso.EDITAR, this.proceso.getSysPK(), this.componente);
		if(detalleProceso != null) {
			DetalleProcesoDAO.updateDetalleProceso(this.mainApp.getConnection(), detalleProceso);
			this.actualizarTabla();
		} else {
			Notificacion.dialogoAlerta(AlertType.ERROR, "", "No se pudo modificar el detalle de proceso");
		}//FIN IF-ELSE
	}//FIN METODO
	
	//QUITAR DETALLE DE PROCESO
	private void quitarDetalle() {
		DetalleProceso detalleProceso = this.tablaDetalleProceso.getSelectionModel().getSelectedItem();
		if(detalleProceso != null) {
			if(Notificacion.dialogoPreguntar("", "¿Realmente deseas quitar el detalle de proceso?")) {
				DetalleProcesoDAO.deleteDetalleProceso(this.mainApp.getConnection(), detalleProceso);
				this.actualizarTabla();
			}//FIN IF
		} else {
			Notificacion.dialogoAlerta(AlertType.ERROR, "", "Debes seleccionar un registro");
		}//FIN IF-ELSE
	}//FIN METODO
	
	
	//MANEJDORES DE COMPONENTES
	@FXML private void manejadorBotonCerrar() {
		this.mainApp.getEscenarioDialogos().close();
	}//FIN METODO
	
	@FXML private void manejadorModificarDetalle() {
		this.modificarDetalle();
	}//FIN METODO
	
	@FXML private void manejadorAgregarComponente() {
		this.agregarDetalle();
	}//FIN METODO
	
	@FXML private void manejadorBotonQuitarComponente() {
		this.quitarDetalle();
	}//FIN METODO
}//FIN CLASE
