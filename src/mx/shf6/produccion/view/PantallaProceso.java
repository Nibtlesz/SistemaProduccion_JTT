package mx.shf6.produccion.view;


import java.sql.Date;
import java.util.ArrayList;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Callback;
import mx.shf6.produccion.MainApp;
import mx.shf6.produccion.model.Proceso;
import mx.shf6.produccion.model.dao.ProcesoDAO;
import mx.shf6.produccion.utilities.Notificacion;
import mx.shf6.produccion.utilities.PTableColumn;
import mx.shf6.produccion.utilities.GenerarDocumento;

public class PantallaProceso {
	
	//PROPIEDADES
	private MainApp mainApp;
	private Proceso proceso;
	private ArrayList<Proceso> listaProceso;
	
	//VARIABLES
	
	
	//COMPONENTES INTERFAZ USUARIO
	@FXML private TableView<Proceso> tablaProceso;
	@FXML private PTableColumn<Proceso, Date> fechaColumna;
	@FXML private PTableColumn<Proceso, Integer> cantidadColumna;
	@FXML private PTableColumn<Proceso, Integer> ordenamientoColumna;	
	@FXML private PTableColumn<Proceso, Integer> nivelColumna;
	@FXML private PTableColumn<Proceso, String> nombreCentroTrabajoColumna;
	@FXML private PTableColumn<Proceso, String> nombreParteComponenteColumna;
	@FXML private PTableColumn<Proceso, String> nombreEmpleadoColumna;
	@FXML private PTableColumn<Proceso, Integer> DebitColumn;
	@FXML private PTableColumn<Proceso, String> accionesColumna;
	@FXML private TextField buscarProceso;
	
	//INICIALIZA COMPONENTES CONTROLAN INTERFAZ USUARIO
	@FXML private void initialize() {
		this.proceso = new Proceso();
		this.inicializaComponentes();
	}//FIN METODO
	
	//ACCESO CLASE PRINCIPAL CONTROLA VISTAS
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
		this.listaProceso = ProcesoDAO.readProceso(this.mainApp.getConnection());
		this.inicializaTabla();
		this.actualizarTabla();
	}//FIN METODO
		
	private void inicializaComponentes() {
		this.buscarProceso.setOnKeyReleased(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode().equals(KeyCode.ENTER)) {
					buscarProceso();
				}
			}//FIN METODO
		});//FIN SENTENCIA
	}//FIN METODO
		
	//INICIALIZA LOS COMPONENTES DE LA TABLA DE PROCESOS
	private void inicializaTabla() {
		this.fechaColumna.setCellValueFactory(cellData -> cellData.getValue().fechaProperty());
		this.nombreCentroTrabajoColumna.setCellValueFactory(cellData -> cellData.getValue().nombreCentroTrabajoProperty());
		this.nombreParteComponenteColumna.setCellValueFactory(cellData -> cellData.getValue().nombreComponenteProperty());
		this.nombreEmpleadoColumna.setCellValueFactory(cellData -> cellData.getValue().nombreEmpleadoProperty());
		this.DebitColumn.setCellValueFactory(cellData -> cellData.getValue().debitProperty());
		this.inicializarColumnaAcciones();
	}//FIN METODO
	
	//ACTUALIZA LA TABLA CON LOS ULTIMOS CAMBIOS EN LA BASE DE DATOS
	private void actualizarTabla() {
		this.tablaProceso.setItems(null);
		this.listaProceso.clear();
		this.listaProceso = ProcesoDAO.readProceso(this.mainApp.getConnection());
		this.tablaProceso.setItems(ProcesoDAO.toObservableList(this.listaProceso));
		//this.buscarProceso.setText("");
	}//FIN METODO
	
	//ACTUALIZA LA TABLA DE ACUERDO AL CRITERIO DE BÚSQUEDA
	@FXML private void buscarProceso() {
		this.tablaProceso.setItems(null);
		this.listaProceso.clear();
		this.listaProceso = ProcesoDAO.readProceso(this.mainApp.getConnection(), this.buscarProceso.getText());
		this.tablaProceso.setItems(ProcesoDAO.toObservableList(this.listaProceso));
	}//FIN METODO
	
	private void inicializarColumnaAcciones() {
	
		this.accionesColumna.setCellValueFactory(new PropertyValueFactory<>("DUM"));
		Callback<TableColumn<Proceso, String>, TableCell<Proceso, String>> cellFactory = param -> {
			
			final TableCell<Proceso, String> cell = new TableCell<Proceso, String>() {
				final Button botonVer = new Button("Ver");
				final Button botonModificar = new Button("Modificar");
				final Button botonEliminar = new Button("Eliminar");
				final Button botonDetalle = new Button("Detalles");
				final Button botonProceso = new Button("Hoja de proceso");
				final HBox acciones = new HBox(botonVer, botonModificar, botonEliminar, botonDetalle, botonProceso);
				
				//PARA MOSTRAR LOS DIALOGOS
				@Override
				public void updateItem(String item, boolean empty) {
					botonVer.setGraphic(new ImageView(new Image(MainApp.class.getResourceAsStream("view/images/1x/ViewIcon.png"))));
					botonVer.setPrefSize(16.0, 16.0);
					botonVer.setPadding(Insets.EMPTY);
					botonVer.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
					botonVer.setStyle("-fx-background-color: transparent;");
					botonVer.setCursor(Cursor.HAND);
		        	botonVer.setTooltip(new Tooltip("Ver proceso"));
		        	
		        	botonModificar.setGraphic(new ImageView(new Image(MainApp.class.getResourceAsStream("view/images/1x/ActualizarIcono.png"))));
		        	botonModificar.setPrefSize(16.0, 16.0);
		        	botonModificar.setPadding(Insets.EMPTY);
		        	botonModificar.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
		        	botonModificar.setStyle("-fx-background-color: transparent;");
		        	botonModificar.setCursor(Cursor.HAND);
		        	botonModificar.setTooltip(new Tooltip("Modificar datos del proceso"));
		        	
		        	botonEliminar.setGraphic(new ImageView(new Image(MainApp.class.getResourceAsStream("view/images/1x/RemoveIcon.png"))));
		        	botonEliminar.setPrefSize(16.0, 16.0);
		        	botonEliminar.setPadding(Insets.EMPTY);
		        	botonEliminar.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
		        	botonEliminar.setStyle("-fx-background-color: transparent;");
		        	botonEliminar.setCursor(Cursor.HAND);
		        	botonEliminar.setTooltip(new Tooltip("Eliminar proceso"));
		        	
		        	botonDetalle.setGraphic(new ImageView(new Image(MainApp.class.getResourceAsStream("view/images/1x/DetalleIcono.png"))));
		        	botonDetalle.setPrefSize(16.0, 16.0);
		        	botonDetalle.setPadding(Insets.EMPTY);
		        	botonDetalle.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
		        	botonDetalle.setStyle("-fx-background-color: transparent;");
		        	botonDetalle.setCursor(Cursor.HAND);
		        	botonDetalle.setTooltip(new Tooltip("Detalles"));
		        	
		        	botonProceso.setGraphic(new ImageView(new Image(MainApp.class.getResourceAsStream("view/images/1x/DocumentIcon.png"))));
		        	botonProceso.setPrefSize(16.0, 16.0);
		        	botonProceso.setPadding(Insets.EMPTY);
		        	botonProceso.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
		        	botonProceso.setStyle("-fx-background-color: transparent;");
		        	botonProceso.setCursor(Cursor.HAND);
		        	botonProceso.setTooltip(new Tooltip("Hoja de Proceso"));
		        	
		        	acciones.setSpacing(3);
		        	acciones.setPrefWidth(80.0);
		        	acciones.setAlignment(Pos.CENTER_LEFT);
		        	super.updateItem(item, empty);
		        	
		        	
		        	if (empty) {
		        		super.setGraphic(null);
		                super.setText(null);
		            } else {
		            	//MANEJADORES PARA LOS BOTONES
		            	botonVer.setOnAction(event -> {
		            		proceso = getTableView().getItems().get(getIndex());
		            		manejadorBotonVer(proceso);
		            	});//FIN MANEJADOR
		            	
		            	botonEliminar.setOnAction(event -> {
		            		proceso = getTableView().getItems().get(getIndex());
		            		manejadorBotonEliminar(proceso);
		            	});//FIN MANEJADOR
		            	
		            	botonModificar.setOnAction(event -> {
		            		proceso = getTableView().getItems().get(getIndex());
		            		manejadorBotonModificar(proceso);
		            	});//FIN MANEJADOR
		            	
		            	botonDetalle.setOnAction(event -> {
		            		proceso = getTableView().getItems().get(getIndex());
		            		manejadorBotonDetalles(proceso);
		            	});
		            	
		            	botonProceso.setOnAction(event -> {
		            		proceso = getTableView().getItems().get(getIndex()); 		
		            		GenerarDocumento.generarHojaProceso(mainApp.getConnection(), proceso.getSysPK());
		            	});
		            	setGraphic(acciones);
		            	setText(null);
		            }//FIN IF-ELSE
				}//FIN METODO
			};//FIN METODO
			return cell;
		};//FIN METODO
		accionesColumna.setCellFactory(cellFactory);
	}//FIN METODO
	

	//ACTUALIZAR DATOS
	@FXML private void manejadorBotonActualizar() {
		this.actualizarTabla();
	}//FIN METODO
	 
	//CREAR PROCESO
	@FXML private void manejadorBotonCrear() {
		this.mainApp.iniciarDialogoProceso(proceso, DialogoProceso.CREAR);
		this.actualizarTabla();
	}

	//VER DATOS
	private void manejadorBotonVer(Proceso proceso) {
		this.mainApp.iniciarDialogoProceso(proceso, DialogoProceso.MOSTRAR);
		this.actualizarTabla();
	}//FIN METODO
	
	//ELIMINAR PROCESO
	 private void manejadorBotonEliminar(Proceso proceso) {
		if (Notificacion.dialogoPreguntar("", "Estas a punto de eliminar el registro, ¿Deseas continuar?"))
			ProcesoDAO.deleteProceso(this.mainApp.getConnection(), proceso);
		this.actualizarTabla();
	}//FIN METODO
	 
	//MODIFICAR PROCESO
	private void manejadorBotonModificar(Proceso proceso) {
		this.mainApp.iniciarDialogoProceso(proceso, DialogoProceso.EDITAR);
		this.actualizarTabla();
	}//FIN METODO
	
	//VER MAS DETALLES DE PROCESO
	private void manejadorBotonDetalles(Proceso proceso) {
		this.mainApp.iniciarDialogoDetalleProceso(proceso);
	}//FIN METODO
	
}//FIN CLASE
