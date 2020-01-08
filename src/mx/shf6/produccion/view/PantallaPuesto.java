package mx.shf6.produccion.view;

import java.util.ArrayList;

import javafx.collections.FXCollections;
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
import mx.shf6.produccion.model.Puesto;
import mx.shf6.produccion.model.dao.PuestoDAO;
import mx.shf6.produccion.utilities.Notificacion;
import mx.shf6.produccion.utilities.PTableColumn;

public class PantallaPuesto {

	// PROPIEDADES
	private MainApp mainApp;
	private Puesto puesto;
	private ArrayList<Puesto> listaPuesto;

	// VARIABLES

	// CONSTANTES

	// COMPONENTES INTERFAZ
	@FXML private TableView<Puesto> tablaPuesto;
	@FXML private PTableColumn<Puesto, String> columnaCodigo;
	@FXML private PTableColumn<Puesto, String> columnaDescripcion;
	@FXML private PTableColumn<Puesto, String> columnaAcciones;
	@FXML private TextField buscarPuesto;

	// INICIALIZA COMPONENTES QUE CONTRALAN INTERFAZ
	@FXML
	private void initialize() {
		inicializaComponentes();
	}

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
		this.puesto = new Puesto();
		this.listaPuesto = PuestoDAO.readPuesto(this.mainApp.getConnection());
		actualizarTabla();
		inicializarTabla();
	}// FIN METODO
	
	//INICIALIZAR COMPONENTES
	private void inicializaComponentes() {
		this.buscarPuesto.setOnKeyReleased(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode().equals(KeyCode.ENTER))
					buscarRegistroTabla();
			}// FIN METODO

		});// FIN SENTENCIA
	}// FIN METODO

	// METODO INICIALIZAR TABLA
	private void inicializarTabla() {
		this.columnaCodigo.setCellValueFactory(CellData -> CellData.getValue().codigoProperty());
		this.columnaDescripcion.setCellValueFactory(CellData -> CellData.getValue().descripcionProperty());
		inicializarColumnaAcciones();
	}// FIN METODO

	// METODO ACTUALIZAR TABLA
	@FXML
	public void actualizarTabla() {
		this.tablaPuesto.setItems(null);
		this.listaPuesto.clear();
		this.listaPuesto = PuestoDAO.readPuesto(mainApp.getConnection());
		this.tablaPuesto.setItems(FXCollections.observableArrayList(listaPuesto));
	}// FIN METODO ACTUALIZAR


	// BUSCAR REGISTRO
	public void buscarRegistroTabla() {

		tablaPuesto.setItems(null);
		listaPuesto.clear();
		listaPuesto = PuestoDAO.readPuestoLike(mainApp.getConnection(), buscarPuesto.getText());
		this.tablaPuesto.setItems(FXCollections.observableArrayList(listaPuesto));
	}// FIN METODO BUSCAR

	// INICIALIZAR COLUMNA ACCIONES
	private void inicializarColumnaAcciones() {
		this.columnaAcciones.setCellValueFactory(new PropertyValueFactory<>("DUM"));
		Callback<TableColumn<Puesto, String>, TableCell<Puesto, String>> cellFactory = param -> {
			final TableCell<Puesto, String> cell = new TableCell<Puesto, String>() {
				final Button botonVer = new Button("Ver");
				final Button botonEditar = new Button("Editar");
				final Button botonEliminar = new Button("Eliminar");
				final HBox cajaBotones = new HBox(botonVer, botonEditar, botonEliminar);

				@Override
				public void updateItem(String item, boolean empty) {
					// INICALIZA BOTONES
					botonVer.setGraphic(new ImageView(new Image(MainApp.class.getResourceAsStream("view/images/1x/VerIcono.png"))));
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
							puesto = getTableView().getItems().get(getIndex());
							botonVerPuesto(puesto);
						});// FIN MANEJADDOR

						botonEditar.setOnAction(event -> {
							puesto = getTableView().getItems().get(getIndex());
							BotonEditar(puesto);
						});// FIN MANEJADDOR

						botonEliminar.setOnAction(event -> {
							puesto = getTableView().getItems().get(getIndex());
							BotonEliminar(puesto);
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
	
		//METODO BOTON VER PUESTO
		private void botonVerPuesto(Puesto puesto) {
			this.mainApp.iniciarDialogoPuesto(puesto, DialogoPuesto.VER);
			this.actualizarTabla();
		}// FIN METODO

		//METODO BOTON EDITAR
		private void BotonEditar(Puesto puesto) {
			this.mainApp.iniciarDialogoPuesto(puesto, DialogoPuesto.EDITAR);
			this.actualizarTabla();
		}// FIN METODO 

		// METODO BOTON ELIMINAR
		private void BotonEliminar(Puesto puesto) {
			if (Notificacion.dialogoPreguntar("", "Estas a punto de eliminar el registro, ¿Deseas continuar?"));
				PuestoDAO.deletePuesto(this.mainApp.getConnection(), puesto);
			this.actualizarTabla();
		}// FIN MANEJADOR ELIMINAR
	
	// MANEJADORES COMPONENTES
	@FXML
	private void manejadorBotonBuscar() {
		this.buscarRegistroTabla();
	}// FIN MANEJADOR BUSCAR

	@FXML
	private void manejadorBotonCrear() {
		this.mainApp.iniciarDialogoPuesto(puesto, DialogoPuesto.CREAR);
		this.actualizarTabla();
	}// FIN METODO

	@FXML
	private void manejadorBotonActualizar() {
		this.actualizarTabla();
	}// FIN METODO

	

}
