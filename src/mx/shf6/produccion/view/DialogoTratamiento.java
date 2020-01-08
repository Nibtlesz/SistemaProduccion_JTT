package mx.shf6.produccion.view;

import java.util.ArrayList;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
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
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import mx.shf6.produccion.MainApp;
import mx.shf6.produccion.model.Tratamiento;
import mx.shf6.produccion.model.dao.TratamientoDAO;
import mx.shf6.produccion.utilities.PTableColumn;

public class DialogoTratamiento {

	//PROPIEDADES
	private MainApp mainApp;
	private Tratamiento tratamiento;
	private ArrayList<Tratamiento> listaTratamiento;
	
	//VARIABLES
	
	//COMPONENTES INTERFAZ
	@FXML private TextField campoTextoBusqueda;
	@FXML private TableView<Tratamiento> tablaTratamiento;
	@FXML private PTableColumn<Tratamiento, Integer> columnaSysPK;
	@FXML private PTableColumn<Tratamiento, String> columnaCodigo;
	@FXML private PTableColumn<Tratamiento, String> columnaDescripcion;
	@FXML private PTableColumn<Tratamiento, String> columnaStatus;
	@FXML private PTableColumn<Tratamiento, String> columnaAcciones;
	
	//INICIA COMPONENTES INTERFAZ USUARIO
	@FXML private void initialize() {
		this.tratamiento = new Tratamiento();
		this.inicializaComponentes();
		this.inicializaTabla();
	}//FIN METODO
	
	//ACCESO CLASE PRINCIPAL
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
		this.listaTratamiento = TratamientoDAO.readTratamiento(this.mainApp.getConnection());
		this.actualizarTabla();
	}//FIN METODO
	
	private void inicializaComponentes() {
		this.campoTextoBusqueda.setOnKeyReleased(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				if (event.getCode().equals(KeyCode.ENTER))
					buscarRegistroTabla();
			}//FIN METODO
			
		});//FIN SENTENCIA
	}//FIN METODO
	
	private void inicializaTabla() {
		this.columnaSysPK.setCellValueFactory(cellData -> cellData.getValue().sysPKProperty());
		this.columnaCodigo.setCellValueFactory(cellData -> cellData.getValue().codigoProperty());
		this.columnaDescripcion.setCellValueFactory(cellData -> cellData.getValue().descripcionProperty());
		this.columnaStatus.setCellValueFactory(cellData -> cellData.getValue().statusProperty());
		this.inicializarColumnaAcciones();
	}//FIN METODO.
	
	private void actualizarTabla() {
		this.tablaTratamiento.setItems(null);
		this.listaTratamiento.clear();
		this.listaTratamiento = TratamientoDAO.readTratamiento(this.mainApp.getConnection());
		this.tablaTratamiento.setItems(TratamientoDAO.toObservableList(this.listaTratamiento));
	}//FIN METODO
	
	@FXML private void buscarRegistroTabla() {
		this.tablaTratamiento.setItems(null);
		this.listaTratamiento.clear();
		this.listaTratamiento = TratamientoDAO.readTratamiento(this.mainApp.getConnection(), this.campoTextoBusqueda.getText());
		this.tablaTratamiento.setItems(TratamientoDAO.toObservableList(this.listaTratamiento));
	}//FIN METODO
	
	private void inicializarColumnaAcciones() {
		this.columnaAcciones.setCellValueFactory(new PropertyValueFactory<>("DUM"));
		Callback<TableColumn<Tratamiento, String>, TableCell<Tratamiento, String>> cellFactory = param -> {
			final TableCell<Tratamiento, String> cell = new TableCell<Tratamiento, String>() {
				final Button botonVer = new Button("Ver");
				final Button botonEditar = new Button("Editar");
				final HBox cajaBotones = new HBox(botonVer, botonEditar);
				
				@Override
				public void updateItem(String item, boolean empty) {
					//INICALIZA BOTONES
					botonVer.setGraphic(new ImageView(new Image(MainApp.class.getResourceAsStream("view/images/1x/VerIcono.png"))));
					botonVer.setPrefSize(16.0, 16.0);
					botonVer.setPadding(Insets.EMPTY);
					botonVer.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
					botonVer.setStyle("-fx-background-color: transparent");
					botonVer.setCursor(Cursor.HAND);
					botonVer.setTooltip(new Tooltip("Ver registro"));
					
					botonEditar.setGraphic(new ImageView(new Image(MainApp.class.getResourceAsStream("view/images/1x/ActualizarIcono.png"))));
					botonEditar.setPrefSize(16.0, 16.0);
					botonEditar.setPadding(Insets.EMPTY);
					botonEditar.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
					botonEditar.setStyle("-fx-background-color: transparent");
					botonEditar.setCursor(Cursor.HAND);
					botonEditar.setTooltip(new Tooltip("Editar registro"));
					
					super.updateItem(item, empty);
					if (empty) {
						super.setGraphic(null);
						super.setText(null);
					} else {
						
						//MANEJADORES PARA LOS BOTONES
						botonVer.setOnAction(event -> {
							tratamiento = getTableView().getItems().get(getIndex());
							manejadorBotonVer(tratamiento);
						});//FIN MANEJADDOR
						
						botonEditar.setOnAction(event -> {
							tratamiento = getTableView().getItems().get(getIndex());
							manejadorBotonEditar(tratamiento);
						});//FIN MANEJADDOR
						
						cajaBotones.setSpacing(2);
						super.setGraphic(cajaBotones);
						super.setText(null);
					}//FIN IF ELSE
						
				}//FIN METODO
				
			};//FIN INSTRUCCION
			return cell;
		};//FIN INSTRUCCION
		columnaAcciones.setCellFactory(cellFactory);
	}//FIN METODO
	
	//MANEJADORES COMPONENTES
	@FXML private void manejadorBotonCrear() {
		this.mainApp.iniciarDialogoAgregarTratamiento(tratamiento, DialogoAgregarTipoMateriaPrima.CREAR);
		this.actualizarTabla();
	}//FIN METODO
	
	@FXML private void manejadorBotonActualizar() {
		this.actualizarTabla();
	}//FIN METODO
	
	private void manejadorBotonVer(Tratamiento tratamiento) {
		this.mainApp.iniciarDialogoAgregarTratamiento(tratamiento, DialogoAgregarTipoMateriaPrima.VER);
		this.actualizarTabla();
	}//FIN METODO
	
	private void manejadorBotonEditar(Tratamiento tratamiento) {
		this.mainApp.iniciarDialogoAgregarTratamiento(tratamiento, DialogoAgregarTipoMateriaPrima.EDITAR);
		this.actualizarTabla();
	}//FIN METODO
	
	@FXML private void manejadoBotonCerrar() {
		this.mainApp.getEscenarioDialogos().close();
	}
		
}//FIN CLASE