package mx.shf6.produccion.view;

import java.sql.Connection;
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
import mx.shf6.produccion.model.Cliente;
import mx.shf6.produccion.model.Comprador;
import mx.shf6.produccion.model.dao.CompradorDAO;
import mx.shf6.produccion.utilities.Notificacion;
import mx.shf6.produccion.utilities.PTableColumn;

public class DialogoCompradores {
	
	//PROPIEDADES
	private MainApp mainApp;
	private Connection connection;
	private Cliente cliente;
	private Comprador comprador;
	private ArrayList<Comprador> listaCompradores;
	
	
	//COMPONENTENS DE LA INTERFAZ
	@FXML private TableView<Comprador> tablaCompradores;
	@FXML private PTableColumn<Comprador, String> nombreColumna;
	@FXML private PTableColumn<Comprador, String> correoColumna;
	@FXML private PTableColumn<Comprador, String> telefonoColumna;
	@FXML private PTableColumn<Comprador, String> telefonoAuxColumna;
	@FXML private PTableColumn<Comprador, String> areaDptoColumna;
	@FXML private PTableColumn<Comprador, String> accionesColumna;
	@FXML private TextField campoBusqueda;
	
	//METODOS
	@FXML private void initialize() {
		this.comprador = new Comprador();
		this.listaCompradores = new ArrayList<Comprador>();
		inicializarComponentes();
		inicializarTabla();
	}//FIN METODO

	public void setMainApp(MainApp mainApp, Cliente cliente) {
		this.mainApp = mainApp;
		this.cliente = cliente;
		this.connection = this.mainApp.getConnection();
		actualizarTabla();
	}//FIN METODO
	
	private void inicializarComponentes() {
		this.campoBusqueda.setOnKeyReleased(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				if (event.getCode().equals(KeyCode.ENTER))
					buscarRegistro();
			}//FIN METODO
		});//FIN SENTENCIA
	}//FIN METODO
	
	private void inicializarTabla() {
		this.nombreColumna.setCellValueFactory(cellData -> cellData.getValue().nombreProperty());
		this.correoColumna.setCellValueFactory(cellData -> cellData.getValue().correoProperty());
		this.telefonoColumna.setCellValueFactory(cellData -> cellData.getValue().telefonoProperty());
		this.telefonoAuxColumna.setCellValueFactory(cellData -> cellData.getValue().telefonoAuxiliarProperty());
		this.areaDptoColumna.setCellValueFactory(cellData -> cellData.getValue().areaDepartamentoProperty());
		inicializarAccionesColumna();
	}//FIN METODO
	
	private void actualizarTabla() {
		this.tablaCompradores.setItems(null);		
		this.listaCompradores.clear();
		this.listaCompradores = CompradorDAO.readCompradores(this.connection, this.cliente.getSysPK());
		this.tablaCompradores.setItems(FXCollections.observableArrayList(listaCompradores));
	}//FIN METODO
	
	private void buscarRegistro() {
		this.tablaCompradores.setItems(null);		
		this.listaCompradores.clear();
		this.listaCompradores = CompradorDAO.readCompradorNombre(this.connection, this.campoBusqueda.getText(), this.cliente.getSysPK());
		this.tablaCompradores.setItems(FXCollections.observableArrayList(listaCompradores));
	}//FIN METODO
	
	private void inicializarAccionesColumna() {
		this.accionesColumna.setCellValueFactory(new PropertyValueFactory<>("DUM"));
		Callback<TableColumn<Comprador, String>, TableCell<Comprador, String>> cellFactory = param -> {
			final TableCell<Comprador, String> cell = new TableCell<Comprador, String>(){
				final Button botonVer = new Button("Ver");
				final Button botonEditar = new Button("Editar");
				final Button botonEliminar = new Button("Eliminar");
				final HBox cajaBotones = new HBox(botonVer, botonEditar,botonEliminar);
				
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

					botonEliminar.setGraphic(new ImageView(new Image(MainApp.class.getResourceAsStream("view/images/1x/EliminarIcono.png"))));
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

						//MANEJADORES PARA LOS BOTONES
						botonVer.setOnAction(event -> {
							comprador = getTableView().getItems().get(getIndex());
							manejadorBotonVer(comprador);
						});//FIN MANEJADDOR

						botonEditar.setOnAction(event -> {
							comprador = getTableView().getItems().get(getIndex());
							manejadorBotonEditar(comprador);
						});//FIN MANEJADDOR

						botonEliminar.setOnAction(event -> {
							comprador = getTableView().getItems().get(getIndex());
							manejadorBotonEliminar(comprador);
						});//FIN MANEJADDOR
						
						cajaBotones.setSpacing(2);
						super.setGraphic(cajaBotones);
						super.setText(null);
					}//FIN IF-ELSE
				}//FIN METODO
			};//FIN SENTENCIA
			return cell;		
		};//FIN SENTENCIA
		this.accionesColumna.setCellFactory(cellFactory);
	}//FIN METODO
	
	//MANEJADORES
	@FXML private void manejadorBotonCrear() {
		this.mainApp.iniciarDialogoAgregarComprador(comprador, cliente, DialogoAgregarComprador.CREAR);
		actualizarTabla();
	}//FIN METODO
	
	@FXML private void manejadorBotonActualizar() {
		actualizarTabla();
	}//FIN METODO
	
	@FXML private void manejadorBotonCerrar() {
		this.mainApp.getEscenarioDialogos().close();
	}//FIN METODO
	
	@FXML private void manejadorBotonBuscar() {
		buscarRegistro();
	}//FIN METODO
	
	private void manejadorBotonVer(Comprador comprador) {
		this.mainApp.iniciarDialogoAgregarComprador(comprador, cliente, DialogoAgregarComprador.VER);
		actualizarTabla();
	}//FIN METODO
	
	private void manejadorBotonEditar(Comprador comprador) {
		this.mainApp.iniciarDialogoAgregarComprador(comprador, cliente, DialogoAgregarComprador.EDITAR);
		actualizarTabla();
	}//FIN METODO
	
	private void manejadorBotonEliminar(Comprador comprador) {
		if (Notificacion.dialogoPreguntar("", "Estas a punto de eliminar el registro, ¿Deseas continuar?")) 
			CompradorDAO.deleteComprador(this.connection, comprador);
		this.actualizarTabla();
	}//FIN METODO
}//FIN CLASE
