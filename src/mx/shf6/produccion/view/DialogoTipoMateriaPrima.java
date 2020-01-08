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
import mx.shf6.produccion.model.TipoMateriaPrima;
import mx.shf6.produccion.model.dao.TipoMateriaPrimaDAO;
import mx.shf6.produccion.utilities.PTableColumn;

public class DialogoTipoMateriaPrima {

	//PROPIEDADES
	private MainApp mainApp;
	private TipoMateriaPrima tipoMateriaPrima;
	private ArrayList<TipoMateriaPrima> listaTipoMateriaPrima;
	
	//VARIABLES
	
	//COMPONENTES INTERFAZ
	@FXML private TextField campoTextoBusqueda;
	@FXML private TableView<TipoMateriaPrima> tablaTipoMateriaPrima;
	@FXML private PTableColumn<TipoMateriaPrima, Integer> columnaSysPK;
	@FXML private PTableColumn<TipoMateriaPrima, String> columnaCodigo;
	@FXML private PTableColumn<TipoMateriaPrima, String> columnaDescripcion;
	@FXML private PTableColumn<TipoMateriaPrima, String> columnaStatus;
	@FXML private PTableColumn<TipoMateriaPrima, String> columnaAcciones;
	
	//INICIA COMPONENTES INTERFAZ USUARIO
	@FXML private void initialize() {
		this.tipoMateriaPrima = new TipoMateriaPrima();
		this.inicializaComponentes();
		this.inicializaTabla();
	}//FIN METODO
	
	//ACCESO CLASE PRINCIPAL
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
		this.listaTipoMateriaPrima = TipoMateriaPrimaDAO.readTipoMateriaPrima(this.mainApp.getConnection());
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
		this.tablaTipoMateriaPrima.setItems(null);
		this.listaTipoMateriaPrima.clear();
		this.listaTipoMateriaPrima = TipoMateriaPrimaDAO.readTipoMateriaPrima(this.mainApp.getConnection());
		this.tablaTipoMateriaPrima.setItems(TipoMateriaPrimaDAO.toObservableList(this.listaTipoMateriaPrima));
	}//FIN METODO
	
	@FXML private void buscarRegistroTabla() {
		this.tablaTipoMateriaPrima.setItems(null);
		this.listaTipoMateriaPrima.clear();
		this.listaTipoMateriaPrima = TipoMateriaPrimaDAO.readTipoMateriaPrima(this.mainApp.getConnection(), this.campoTextoBusqueda.getText());
		this.tablaTipoMateriaPrima.setItems(TipoMateriaPrimaDAO.toObservableList(this.listaTipoMateriaPrima));
	}//FIN METODO
	
	private void inicializarColumnaAcciones() {
		this.columnaAcciones.setCellValueFactory(new PropertyValueFactory<>("DUM"));
		Callback<TableColumn<TipoMateriaPrima, String>, TableCell<TipoMateriaPrima, String>> cellFactory = param -> {
			final TableCell<TipoMateriaPrima, String> cell = new TableCell<TipoMateriaPrima, String>() {
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
							tipoMateriaPrima = getTableView().getItems().get(getIndex());
							manejadorBotonVer(tipoMateriaPrima);
						});//FIN MANEJADDOR
						
						botonEditar.setOnAction(event -> {
							tipoMateriaPrima = getTableView().getItems().get(getIndex());
							manejadorBotonEditar(tipoMateriaPrima);
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
		this.mainApp.iniciarDialogoAgregarTipoMateriaPrima(tipoMateriaPrima, DialogoAgregarTipoMateriaPrima.CREAR);
		this.actualizarTabla();
	}//FIN METODO
	
	@FXML private void manejadorBotonActualizar() {
		this.actualizarTabla();
	}//FIN METODO
	
	@FXML private void manejadorBotonCerrar() {
		this.mainApp.getEscenarioDialogos().close();
	}
	
	private void manejadorBotonVer(TipoMateriaPrima tipoMateriaPrima) {
		this.mainApp.iniciarDialogoAgregarTipoMateriaPrima(tipoMateriaPrima, DialogoAgregarTipoMateriaPrima.VER);
		this.actualizarTabla();
	}//FIN METODO
	
	private void manejadorBotonEditar(TipoMateriaPrima tipoMateriaPrima) {
		this.mainApp.iniciarDialogoAgregarTipoMateriaPrima(tipoMateriaPrima, DialogoAgregarTipoMateriaPrima.EDITAR);
		this.actualizarTabla();
	}//FIN METODO
		
}//FIN CLASE
