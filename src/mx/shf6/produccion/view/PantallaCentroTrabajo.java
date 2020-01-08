package mx.shf6.produccion.view;

import java.sql.Connection;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import mx.shf6.produccion.MainApp;
import mx.shf6.produccion.model.CentroTrabajo;
import mx.shf6.produccion.model.dao.CentroTrabajoDAO;
import mx.shf6.produccion.utilities.Notificacion;
import mx.shf6.produccion.utilities.PTableColumn;
import javafx.util.Callback;

public class PantallaCentroTrabajo {

	// PROPIEDADES
	private MainApp mainApp;
	@SuppressWarnings("unused")
	private Connection connection;
	private CentroTrabajo centroTrabajo;
	private ArrayList<CentroTrabajo> listaCentroTrabajo;
	// FIN PROPIEDADES

	// VARIABLES

	// CONSTANTES

	// COMPONENTES INTERFAZ
	@FXML private TableView<CentroTrabajo> tablaCentroTrabajo;
	@FXML private PTableColumn<CentroTrabajo, String> columnaCodigo;
	@FXML private PTableColumn<CentroTrabajo, String> columnaDescripcion;
	@FXML private PTableColumn<CentroTrabajo, String> columnaGrupoTrabajo;
	@FXML private PTableColumn<CentroTrabajo, String> columnaAcciones;
	@FXML private TextField campoBuscar;
	// FIN CONPONENTES

	// INICIALIZAR COMPONENTES DE INTERFAZ
	@FXML
	private void initialize() {
		inicializaComponentes();
	}

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
		this.centroTrabajo = new CentroTrabajo();
		this.listaCentroTrabajo = CentroTrabajoDAO.readCentroTrabajo(this.mainApp.getConnection());
		this.connection = this.mainApp.getConnection();
		inicializarTabla();
		actualizarTabla();
	}// FIN METODO
	
	//INICIALIZAR COMPONENTES
	private void inicializaComponentes() {
		this.campoBuscar.setOnKeyReleased(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode().equals(KeyCode.ENTER))
					buscarRegistro();
			}// FIN METODO

		});// FIN SENTENCIA
	}// FIN METODO
	
	// METODO INICIALIZAR TABLA
	private void inicializarTabla() {
		this.columnaCodigo.setCellValueFactory(CellData -> CellData.getValue().codigoProperty());
		this.columnaDescripcion.setCellValueFactory(CellData -> CellData.getValue().descripcionProperty());
		this.columnaGrupoTrabajo.setCellValueFactory(CellData -> CellData.getValue().codigoGrupoTrabajo());
		inicializarColumnaAcciones();
	}// FIN METODO INICIALIZAR
	
	
	// METODO ACTUALIZAR TABLA
	@FXML
	public void actualizarTabla() {
		this.tablaCentroTrabajo.setItems(null);
		this.listaCentroTrabajo.clear();
		this.listaCentroTrabajo = CentroTrabajoDAO.readCentroTrabajo(this.mainApp.getConnection());
		this.tablaCentroTrabajo.setItems(FXCollections.observableArrayList(this.listaCentroTrabajo));
	}// FIN METODO ACTUALIZAR

	// METODO BUSCAR REGISTRO
	private void buscarRegistro() {
		tablaCentroTrabajo.setItems(null);
		listaCentroTrabajo.clear();
		listaCentroTrabajo = CentroTrabajoDAO.readCentroTrabajoLike(this.mainApp.getConnection(),
				campoBuscar.getText());
		tablaCentroTrabajo.setItems(FXCollections.observableList(listaCentroTrabajo));
	}// FIN METODO BUSCAR REGISTRO

	// INICIALIZAR COLUMNA ACCIONES
	private void inicializarColumnaAcciones() {
		this.columnaAcciones.setCellValueFactory(new PropertyValueFactory<>("DUM"));
		Callback<TableColumn<CentroTrabajo, String>, TableCell<CentroTrabajo, String>> cellFactory = param -> {
			final TableCell<CentroTrabajo, String> cell = new TableCell<CentroTrabajo, String>() {
				final Button botonVer = new Button("Ver");
				final Button botonEditar = new Button("Editar");
				final Button botonEliminar = new Button("Eliminar");
				final HBox cajaBotones = new HBox(botonVer, botonEditar, botonEliminar);

				@Override
				public void updateItem(String item, boolean empty) {
					// INICALIZA BOTONES
					botonVer.setGraphic(
							new ImageView(new Image(MainApp.class.getResourceAsStream("view/images/1x/VerIcono.png"))));
					botonVer.setPrefSize(16.0, 16.0);
					botonVer.setPadding(Insets.EMPTY);
					botonVer.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
					botonVer.setStyle("-fx-background-color: transparent");
					botonVer.setCursor(Cursor.HAND);
					botonVer.setTooltip(new Tooltip("Ver registro"));

					botonEditar.setGraphic(new ImageView(
							new Image(MainApp.class.getResourceAsStream("view/images/1x/ActualizarIcono.png"))));
					botonEditar.setPrefSize(16.0, 16.0);
					botonEditar.setPadding(Insets.EMPTY);
					botonEditar.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
					botonEditar.setStyle("-fx-background-color: transparent");
					botonEditar.setCursor(Cursor.HAND);
					botonEditar.setTooltip(new Tooltip("Editar registro"));

					botonEliminar.setGraphic(new ImageView(
							new Image(MainApp.class.getResourceAsStream("view/images/1x/EliminarIcono.png"))));
					botonEliminar.setPrefSize(16.0, 16.0);
					botonEliminar.setPadding(Insets.EMPTY);
					botonEliminar.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
					botonEliminar.setStyle("-fx-background-color: transparent");
					botonEliminar.setCursor(Cursor.HAND);
					botonEliminar.setTooltip(new Tooltip("Eliminar regsitro"));

					super.updateItem(item, empty);
					if (empty) {
						super.setGraphic(null);
						super.setText(null);
					} else {

						// MANEJADORES PARA LOS BOTONES
						botonVer.setOnAction(event -> {
							centroTrabajo = getTableView().getItems().get(getIndex());
							botonVer(centroTrabajo);
						});// FIN MANEJADDOR

						botonEditar.setOnAction(event -> {
							centroTrabajo = getTableView().getItems().get(getIndex());
							botonEditar(centroTrabajo);
						});// FIN MANEJADDOR

						botonEliminar.setOnAction(event -> {
							centroTrabajo = getTableView().getItems().get(getIndex());
							botonEliminar(centroTrabajo);
						});// FIN MANEJADDOR

						cajaBotones.setSpacing(2);
						super.setGraphic(cajaBotones);
						super.setText(null);
					} // FIN IF ELSE

				}// FIN METODO

			};// FIN INSTRUCCION
			return cell;
		};// FIN INSTRUCCION
		columnaAcciones.setCellFactory(cellFactory);
	}// FIN METODO
	@FXML
	private void botonCrear() {
		this.mainApp.iniciarDialogoCentroTrabajo(centroTrabajo, DialogoCentroTrabajo.CREAR);
		this.actualizarTabla();
	}// FIN METODO
	
	//BOTON VER 
	private void botonVer(CentroTrabajo centroTrabajo) {
		this.mainApp.iniciarDialogoCentroTrabajo(centroTrabajo, DialogoCentroTrabajo.VER);
		this.actualizarTabla();
	}// FIN METODO

	//BOTONEDITAR
	private void botonEditar(CentroTrabajo centroTrabajo) {
		this.mainApp.iniciarDialogoCentroTrabajo(centroTrabajo, DialogoCentroTrabajo.EDITAR);
		this.actualizarTabla();
	}// FIN METODO

	private void botonEliminar(CentroTrabajo centroTrabajo) {
		if (Notificacion.dialogoPreguntar("", "Estas a punto de eliminar el registro, ¿Deseas continuar?"))
			CentroTrabajoDAO.deleteCentroTrabajo(mainApp.getConnection(), centroTrabajo);
			this.actualizarTabla();
	}// FIN METODO
	
	// MANEJADORES COMPONENTES

	@FXML
	private void manejadorBotonBuscar() {
		this.buscarRegistro();
	}

	@FXML
	private void manejadorBotonActualizar() {
		this.actualizarTabla();
	}// FIN METODO

	

}
