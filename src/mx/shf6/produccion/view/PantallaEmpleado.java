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
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import mx.shf6.produccion.MainApp;
import mx.shf6.produccion.model.Empleado;
import mx.shf6.produccion.model.dao.EmpleadoDAO;
import mx.shf6.produccion.utilities.Notificacion;
import mx.shf6.produccion.utilities.PTableColumn;

public class PantallaEmpleado {

	// PROPIEDADES
	private MainApp mainApp;
	private Connection connection;
	private Empleado empleado;
	private ArrayList<Empleado> listaEmpleado;

	// VARAIBLES

	// CONSTANTES

	// COMPONENTES INTERFAZ
	@FXML private TextField campoTextoBusqueda;
	@FXML private TableView<Empleado> tablaEmpleado;
	@FXML private PTableColumn<Empleado, String> columnaCodigo;
	@FXML private PTableColumn<Empleado, String> columnaNombre;
	@FXML private PTableColumn<Empleado, String> columnaPuesto;
	@FXML private PTableColumn<Empleado, String> columnaAcciones;

	// METODOS

	// METODO COMPONENTES INTERFAZ
	@FXML
	private void initialize() {
		inicializaComponentes(); 
	}

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
		this.empleado = new Empleado();
		this.listaEmpleado = EmpleadoDAO.readEmpleado(this.mainApp.getConnection());
		this.connection = this.mainApp.getConnection();
		inicializarTabla();
		actualizarTabla();
	}// FIN METODO

	// METODO INICIALIZAR TABLA
	private void inicializarTabla() {
		this.columnaCodigo.setCellValueFactory(CellData -> CellData.getValue().codigoProperty());
		this.columnaNombre.setCellValueFactory(CellData -> CellData.getValue().nombreProperty());
		this.columnaPuesto.setCellValueFactory(CellData -> CellData.getValue().getPuesto(this.connection).descripcionProperty());
		inicializarColumnaAcciones();
	}// FIN METODO

	// METODO ACTUALIZAR TABLA
	@FXML private void actualizarTabla() {
		this.tablaEmpleado.setItems(null);
		this.listaEmpleado.clear();
		this.listaEmpleado = EmpleadoDAO.readEmpleado(this.mainApp.getConnection());
		this.tablaEmpleado.setItems(FXCollections.observableArrayList(listaEmpleado));

	}// FIN METODO ACTUALIZAR TABLA

	private void inicializaComponentes() {
		this.campoTextoBusqueda.setOnKeyReleased(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode().equals(KeyCode.ENTER))
					buscarRegistro();
			}// FIN METODO

		});// FIN SENTENCIA
	}// FIN METODO

	// METODO BUSCAR REGISTRO
		public void buscarRegistro() {
		tablaEmpleado.setItems(null);
		listaEmpleado.clear();
		listaEmpleado = EmpleadoDAO.readEmpleadoLike(mainApp.getConnection(), campoTextoBusqueda.getText());
		tablaEmpleado.setItems(FXCollections.observableArrayList(listaEmpleado));
	}// FIN BUSCAR REGISTRO

	// INICIALIZAR COLUMNA ACCIONES
	private void inicializarColumnaAcciones() {
		this.columnaAcciones.setCellValueFactory(new PropertyValueFactory<>("DUM"));
		Callback<TableColumn<Empleado, String>, TableCell<Empleado, String>> cellFactory = param -> {
			final TableCell<Empleado, String> cell = new TableCell<Empleado, String>() {
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
							empleado = getTableView().getItems().get(getIndex());
							botonVer(empleado);
						});// FIN MANEJADDOR

						botonEditar.setOnAction(event -> {
							empleado = getTableView().getItems().get(getIndex());
							botonEditar(empleado);
						});// FIN MANEJADDOR

						botonEliminar.setOnAction(event -> {
							empleado = getTableView().getItems().get(getIndex());
							botonEliminar(empleado);
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


	private void botonVer(Empleado empleado) {
		this.mainApp.iniciarDialogoEmpleado(empleado, DialogoEmpleado.VER);
		this.actualizarTabla();
	}// FIN METODO

	private void botonEditar(Empleado empleado) {
		this.mainApp.iniciarDialogoEmpleado(empleado, DialogoEmpleado.EDITAR);
		this.actualizarTabla();
	}// FIN METODO

	private void botonEliminar(Empleado empleado) {
		if (Notificacion.dialogoPreguntar("", "Estas a punto de eliminar el registro, ¿Deseas continuar?"))
			 EmpleadoDAO.deleteEmpleado(this.mainApp.getConnection(), empleado);
			this.actualizarTabla();
	}// FIN METODO
	
	// MANEJADORES COMPONENTES

	@FXML
	private void manejadorBotonCrear() {
		 this.mainApp.iniciarDialogoEmpleado(empleado, DialogoEmpleado.CREAR);
		this.actualizarTabla();
	}// FIN METODO
	
	
	@FXML
	private void manejadorBotonBuscar() {
		buscarRegistro();
	}

	@FXML
	private void manejadorBotonActualizar() {
		this.actualizarTabla();
	}// FIN METODO


}
