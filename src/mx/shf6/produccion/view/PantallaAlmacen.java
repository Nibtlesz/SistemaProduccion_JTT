package mx.shf6.produccion.view;


import java.sql.Connection;
import java.util.ArrayList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
import mx.shf6.produccion.model.Almacen;
import mx.shf6.produccion.model.dao.AlmacenDAO;
import mx.shf6.produccion.utilities.Notificacion;
import mx.shf6.produccion.utilities.PTableColumn;

public class PantallaAlmacen {
	//PROPIEDADES
	private MainApp mainApp;
	private Connection conexion;
	private Almacen almacen;
	private ArrayList<Almacen> listaAlmacenes;

	//VARIABLES

	//CONSTANTES

	//COMPONENTES INTERFAZ
	@FXML private TableView<Almacen> tablaAlmacenes;
	@FXML private PTableColumn <Almacen, String> columnaCodigo;
	@FXML private PTableColumn <Almacen, String> columnaDescripcion;
	@FXML private PTableColumn <Almacen, String> columnaAcciones;
	@FXML private TextField campoTextoBuscar;

	//METODOS
	@FXML private void initialize(){
		this.actualizarComponentes();
		this.inicializarTabla();
	}//FIN METODO

	public void setMainApp(MainApp mainApp){
		this.mainApp = mainApp;
		this.conexion = this.mainApp.getConnection();
		this.almacen = new Almacen();
		this.listaAlmacenes = new ArrayList<Almacen>();
		this.actualizarTabla();
	}//FIN METODO

	private void actualizarComponentes(){
		this.campoTextoBuscar.setOnKeyPressed(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent event) {
				if (event.getCode().equals(KeyCode.ENTER))
					manejadorBotonBuscar();
	    	}//FIN METODO
	    });//FIN SENTENCIA
	}//FIN METODO


	private void inicializarTabla() {
		this.columnaCodigo.setCellValueFactory(cellData -> cellData.getValue().codigoProperty());
		this.columnaDescripcion.setCellValueFactory(cellData -> cellData.getValue().descripcionProperty());

		columnaAcciones.setCellValueFactory(new PropertyValueFactory<>("DUM"));
		Callback<TableColumn<Almacen, String>, TableCell<Almacen, String>> cellFactory = param -> {

			final TableCell<Almacen, String> cell = new TableCell<Almacen, String>() {
				final Button botonVer = new Button("V");
				final Button botonEliminar = new Button("D");
				final Button botonEditar = new Button("U");
				final HBox acciones = new HBox(botonVer, botonEditar, botonEliminar);

				@Override
				public void updateItem(String item, boolean empty) {
					botonVer.setGraphic(new ImageView(new Image(MainApp.class.getResourceAsStream("view/images/1x/ViewIcon.png"))));
					botonVer.setPrefSize(18.0, 18.0);
					botonVer.setPadding(Insets.EMPTY);
					botonVer.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
					botonVer.setStyle("-fx-background-color: transparent;");
					botonVer.setCursor(Cursor.HAND);
					botonVer.setTooltip(new Tooltip("Ver producto"));

					botonEliminar.setGraphic(new ImageView(new Image(MainApp.class.getResourceAsStream("view/images/1x/EliminarIcono.png"))));
					botonEliminar.setPrefSize(16.0, 16.0);
					botonEliminar.setPadding(Insets.EMPTY);
					botonEliminar.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
					botonEliminar.setStyle("-fx-background-color: transparent;");
					botonEliminar.setCursor(Cursor.HAND);
					botonEliminar.setTooltip(new Tooltip("Eliminar producto"));

					botonEditar.setGraphic(new ImageView(new Image(MainApp.class.getResourceAsStream("view/images/1x/ActualizarIcono.png"))));
					botonEditar.setPrefSize(16.0, 16.0);
					botonEditar.setPadding(Insets.EMPTY);
					botonEditar.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
					botonEditar.setStyle("-fx-background-color: transparent;");
					botonEditar.setCursor(Cursor.HAND);
					botonEditar.setTooltip(new Tooltip("Editar producto"));

					acciones.setSpacing(3);
					acciones.setPrefWidth(80.0);
					acciones.setAlignment(Pos.CENTER_LEFT);
					super.updateItem(item, empty);

					if (empty) {
						setGraphic(null);
						setText(null);
					} else {

						botonVer.setOnAction(event -> {
							almacen = getTableView().getItems().get(getIndex());
							verAlmacen(almacen);
						});// FIN LISTENER

						botonEditar.setOnAction(event -> {
							almacen = getTableView().getItems().get(getIndex());
							editarAlmacen(almacen);
						});// FIN LISTENER

						botonEliminar.setOnAction(event -> {
							almacen = getTableView().getItems().get(getIndex());
							if (Notificacion.dialogoPreguntar("", "¿Deseas eliminar el registro?"))
								eliminarAlmacen(almacen);
						});// FIN LISTENER
						setGraphic(acciones);
						setText(null);
					} // FIN IF/ELSE
				}// FIN METODO
			};// FIN METODO
			return cell;
		};// FIN METODO
		columnaAcciones.setCellFactory(cellFactory);
	}// FIN METODO

	private void actualizarTabla() {
		this.tablaAlmacenes.setItems(null);
		this.listaAlmacenes.clear();
		this.listaAlmacenes = AlmacenDAO.readTodos(conexion);
		this.tablaAlmacenes.setItems(AlmacenDAO.toObservableList(listaAlmacenes));
	}// FIN METODO

	private void buscarAlmacen() {
		this.tablaAlmacenes.setItems(null);
		this.listaAlmacenes.clear();
		this.listaAlmacenes = AlmacenDAO.readTodosParecidos(conexion, this.campoTextoBuscar.getText());
		this.tablaAlmacenes.setItems(AlmacenDAO.toObservableList(listaAlmacenes));
	}// FIN METODO

	private void agregarAlmacen() {
		this.mainApp.iniciarDialogoAlmacen(almacen, DialogoAlmacen.CREAR);
		this.actualizarTabla();
	}// FIN METODO

	private void verAlmacen(Almacen almacen) {
		this.mainApp.iniciarDialogoAlmacen(almacen, DialogoAlmacen.MOSTRAR);
		this.actualizarTabla();
	}// FIN METODO

	private void editarAlmacen(Almacen almacen) {
		this.mainApp.iniciarDialogoAlmacen(almacen, DialogoAlmacen.EDITAR);
		this.actualizarTabla();
	}// FIN METODO

	private void eliminarAlmacen(Almacen almacen) {
		AlmacenDAO.delete(this.conexion, almacen);
		this.actualizarTabla();
	}// FIN METODO

	@FXML private void manejadorBotonActualizar() {
		actualizarTabla();
	}//FIN METODO

	@FXML private void manejadorBotonAgregar() {
			this.agregarAlmacen();
	}//FIN METODO

	@FXML private void manejadorBotonBuscar() {
	    this.buscarAlmacen();
	 }//FIN METODO

	@FXML private void manejadorBotonCerrar() {
		this.mainApp.getEscenarioDialogos().close();
	}//FIN METODO

	@FXML private void manejadorBotonAlmacenes() {
		this.mainApp.iniciarPantallaAlmacen();
	}//FIN METODO

	@FXML private void manejadorBotonEntradas() {
		this.mainApp.iniciarDialogoMovimientoInventario(DialogoMovimientoInventario.ENTRADA);
	}//FIN METODO

	@FXML private void manejadorBotonSalidas() {
		this.mainApp.iniciarDialogoMovimientoInventario(DialogoMovimientoInventario.SALIDA);
	}//FIN METODO

	@FXML private void manejadorBotonTraspasos() {
		this.mainApp.iniciarDialogoMovimientoInventario(DialogoMovimientoInventario.TRASPASO);
	}//FIN METODO

	@FXML private void manejadorBotonExistencias() {
		this.mainApp.iniciarPantallaExistencia();
	}//FIN METODO

}
