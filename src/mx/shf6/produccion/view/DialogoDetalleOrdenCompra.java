package mx.shf6.produccion.view;

import java.security.SecureRandom;
import java.sql.Connection;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Locale;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import javafx.scene.Cursor;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import mx.shf6.produccion.MainApp;
import mx.shf6.produccion.model.DetalleOrdenCompra;
import mx.shf6.produccion.model.DetalleOrdenProduccion;
import mx.shf6.produccion.model.OrdenCompra;
import mx.shf6.produccion.model.OrdenProduccion;
import mx.shf6.produccion.model.dao.DetalleOrdenCompraDAO;
import mx.shf6.produccion.model.dao.DetalleOrdenProduccionDAO;
import mx.shf6.produccion.model.dao.OrdenProduccionDAO;
import mx.shf6.produccion.utilities.Notificacion;
import mx.shf6.produccion.utilities.PTableColumn;

public class DialogoDetalleOrdenCompra {
	
	//PROPIEDADES
	private MainApp mainApp;
	private Connection connection;
	private OrdenCompra ordenCompra;
	private ArrayList<DetalleOrdenCompra> arrayListdetalleOrdenCompra;
	
	//COMPONENTES DE LA INTERFAZ
	@FXML private TableView<DetalleOrdenCompra> tableViewDetalleOrdenCompra;
	@FXML private PTableColumn<DetalleOrdenCompra, String> tableColumnDiseno;
	@FXML private PTableColumn<DetalleOrdenCompra, String> tableColumnDescripcion;
	@FXML private PTableColumn<DetalleOrdenCompra, Integer> tableColumnPorEntregar;
	@FXML private PTableColumn<DetalleOrdenCompra, String> tableColumnAcciones;
	@FXML private TextField textFieldFolio;
	@FXML private TextField textFieldCliente;
	
	//METODOS
	@FXML private void initialize() {
		this.ordenCompra = new OrdenCompra();
		initTabla();
	}//FIN METODO

	public void setMainApp(MainApp mainApp, OrdenCompra ordenCompra) {
		this.mainApp = mainApp;
		this.connection = this.mainApp.getConnection();
		this.ordenCompra = ordenCompra;
		this.arrayListdetalleOrdenCompra = new ArrayList<DetalleOrdenCompra>();
		updateTable();
		updateDatos();
	}//FIN METODO
	
	private void initTabla() {
		this.tableColumnDiseno.setCellValueFactory(cellData -> cellData.getValue().getComponenteFK().numeroParteProperty());
		this.tableColumnDescripcion.setCellValueFactory(cellData -> cellData.getValue().getComponenteFK().descripcionProperty());
		this.tableColumnPorEntregar.setCellValueFactory(cellData -> cellData.getValue().porEntregarProperty());
		initColumnaAcciones();
	}//FIN METODO
	
	private void initColumnaAcciones() {
		this.tableColumnAcciones.setCellValueFactory(new PropertyValueFactory<>("DUM"));
		Callback<TableColumn<DetalleOrdenCompra, String>, TableCell<DetalleOrdenCompra, String>> cellFactory = param -> {
			final TableCell<DetalleOrdenCompra, String> cell = new TableCell<DetalleOrdenCompra, String>() {
				final Button botonVer = new Button("V");
				final Button botonEditar = new Button("E");
				final Button botonEliminar = new Button("B");
				final Button botonEntrega = new Button("P");
				final Button botonIniciarOrdenProduccion = new Button("I");
				final HBox cajaBotones = new HBox(botonIniciarOrdenProduccion);
				
				@Override
				public void updateItem(String item, boolean empty) {
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

					botonEliminar.setGraphic(new ImageView(new Image(MainApp.class.getResourceAsStream("view/images/1x/EliminarIcono.png"))));
					botonEliminar.setPrefSize(16.0, 16.0);
					botonEliminar.setPadding(Insets.EMPTY);
					botonEliminar.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
					botonEliminar.setStyle("-fx-background-color: transparent");
					botonEliminar.setCursor(Cursor.HAND);
					botonEliminar.setTooltip(new Tooltip("Eliminar registro"));
					
					botonEntrega.setGraphic(new ImageView(new Image(MainApp.class.getResourceAsStream("view/images/1x/EstructuraNivelesIcono.png"))));
					botonEntrega.setPrefSize(16.0, 16.0);
					botonEntrega.setPadding(Insets.EMPTY);
					botonEntrega.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
					botonEntrega.setStyle("-fx-background-color: transparent");
					botonEntrega.setCursor(Cursor.HAND);
					botonEntrega.setTooltip(new Tooltip("Fechas de entrega"));
					
					botonIniciarOrdenProduccion.setGraphic(new ImageView(new Image(MainApp.class.getResourceAsStream("view/images/1x/AprobarIcono.png"))));
					botonIniciarOrdenProduccion.setPrefSize(16.0, 16.0);
					botonIniciarOrdenProduccion.setPadding(Insets.EMPTY);
					botonIniciarOrdenProduccion.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
					botonIniciarOrdenProduccion.setStyle("-fx-background-color: transparent");
					botonIniciarOrdenProduccion.setCursor(Cursor.HAND);
					botonIniciarOrdenProduccion.setTooltip(new Tooltip("Generar Orden de trabajo"));
					
					super.updateItem(item, empty);
					if (empty) {
						super.setGraphic(null);
						super.setText(null);
					} else {
						botonVer.setOnAction(event -> {
							manejadorBotonVer(getTableView().getItems().get(getIndex()));
						});
						
						botonEditar.setOnAction(event -> {
							manejadorBotonEditar(getTableView().getItems().get(getIndex()));
						});
						
						botonEliminar.setOnAction(event -> {
							manejadorBotonQuitar(getTableView().getItems().get(getIndex()));
						});
						
						botonEntrega.setOnAction(event -> {
							manejadorBotonEntrega(getTableView().getItems().get(getIndex()));
						});
						
						botonIniciarOrdenProduccion.setOnAction(event -> {
							manejadorBotonIniciarProduccion(getTableView().getItems().get(getIndex()));
						});//FIN LISTENER
						
						cajaBotones.setSpacing(2);
						super.setGraphic(cajaBotones);
						super.setText(null);
					}//FIN IF/ELSE
				}//FIN METODO
			};//FIN INSTRUCCION
			return cell;
		};//FIN INSTRUCCION
		this.tableColumnAcciones.setCellFactory(cellFactory);
	}//FIN METODO
	
	private void updateTable() {
		this.arrayListdetalleOrdenCompra.clear();
		this.tableViewDetalleOrdenCompra.setItems(null);
		this.arrayListdetalleOrdenCompra = DetalleOrdenCompraDAO.readPorOrdenCompra(connection, ordenCompra);
		this.tableViewDetalleOrdenCompra.setItems(FXCollections.observableArrayList(this.arrayListdetalleOrdenCompra));
	}//FIN METODO
	
	private void updateDatos() {
		this.textFieldCliente.setText(this.ordenCompra.getClienteFK().getNombre());
		this.textFieldFolio.setText("No. Folio: " + this.ordenCompra.getFolio());
	}//FIN METODO
	
	//MANEJADORES
	@FXML private void manejadorBotonCerrar() {
		this.mainApp.getEscenarioDialogos().close();
	}//FIN METODO
	
	@FXML private void manejadorBotonAgregar() {
		this.mainApp.iniciarDialogoAgregarDetalleOrdenCompra(new DetalleOrdenCompra(), ordenCompra, DialogoAgregarDetalleOrdenCompra.CREAR);
		this.updateTable();
	}//FIN METODO

	private void manejadorBotonQuitar(DetalleOrdenCompra detalleOrdenCompra) {
		if (Notificacion.dialogoPreguntar("", "¿Realmente deseas quitar el componente?")) {
			DetalleOrdenCompraDAO.delete(this.connection, detalleOrdenCompra);
			this.updateTable();
		}//FIN METODO
	}//FIN METODO
	
	private void manejadorBotonEntrega(DetalleOrdenCompra detalleOrdenCompra) {
		this.mainApp.iniciarDialogoDetalleEntregaOrdenCompra(detalleOrdenCompra);
	}//FIN METODO
	
	private void manejadorBotonVer(DetalleOrdenCompra detalleOrdenCompra) {
		this.mainApp.iniciarDialogoAgregarDetalleOrdenCompra(detalleOrdenCompra, ordenCompra, DialogoAgregarDetalleOrdenCompra.MOSTRAR);
	}//FIN METDO
	
	private void manejadorBotonEditar(DetalleOrdenCompra detalleOrdenCompra) {
		this.mainApp.iniciarDialogoAgregarDetalleOrdenCompra(detalleOrdenCompra, ordenCompra, DialogoAgregarDetalleOrdenCompra.EDITAR);
		this.updateTable();
	}//FIN METODO
	
	private void manejadorBotonIniciarProduccion(DetalleOrdenCompra detalleOrdenCompra) {
		
	}//FIN METODO
}//FIN CLASE
