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
import mx.shf6.produccion.model.Material;
import mx.shf6.produccion.model.dao.MaterialDAO;
import mx.shf6.produccion.utilities.PTableColumn;

public class DialogoMaterial {

	//PROPIEDADES
	private MainApp mainApp;
	private Material material;
	private ArrayList<Material> listaMaterial;
	
	//VARIABLES
	
	//COMPONENTES INTERFAZ
	@FXML private TextField campoTextoBusqueda;
	@FXML private TableView<Material> tablaMaterial;
	@FXML private PTableColumn<Material, Integer> columnaSysPK;
	@FXML private PTableColumn<Material, String> columnaCodigo;
	@FXML private PTableColumn<Material, String> columnaDescripcion;
	@FXML private PTableColumn<Material, Double> columnaExistencia;
	@FXML private PTableColumn<Material, String> columnaGradoMaterial;
	@FXML private PTableColumn<Material, String> columnaStatus;
	@FXML private PTableColumn<Material, String> columnaAcciones;
	
	//INICIA COMPONENTES INTERFAZ USUARIO
	@FXML private void initialize() {
		this.material = new Material();
		this.inicializaComponentes();
		this.inicializaTabla();
	}//FIN METODO
	
	//ACCESO CLASE PRINCIPAL
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
		this.listaMaterial = MaterialDAO.readMaterial(this.mainApp.getConnection());
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
		//this.columnaCantidad.setCellValueFactory(cellData -> cellData.getValue().existenciaProperty());
		this.columnaGradoMaterial.setCellValueFactory(cellData -> cellData.getValue().gradoMaterialProperty());
		this.columnaStatus.setCellValueFactory(cellData -> cellData.getValue().statusProperty());
		this.inicializarColumnaAcciones();
	}//FIN METODO.
	
	private void actualizarTabla() {
		this.tablaMaterial.setItems(null);
		this.listaMaterial.clear();
		this.listaMaterial = MaterialDAO.readMaterial(this.mainApp.getConnection());
		this.tablaMaterial.setItems(MaterialDAO.toObservableList(this.listaMaterial));
	}//FIN METODO
	
	@FXML private void buscarRegistroTabla() {
		this.tablaMaterial.setItems(null);
		this.listaMaterial.clear();
		this.listaMaterial = MaterialDAO.readMaterial(this.mainApp.getConnection(), this.campoTextoBusqueda.getText());
		this.tablaMaterial.setItems(MaterialDAO.toObservableList(this.listaMaterial));
	}//FIN METODO
	
	private void inicializarColumnaAcciones() {
		this.columnaAcciones.setCellValueFactory(new PropertyValueFactory<>("DUM"));
		Callback<TableColumn<Material, String>, TableCell<Material, String>> cellFactory = param -> {
			final TableCell<Material, String> cell = new TableCell<Material, String>() {
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
							material = getTableView().getItems().get(getIndex());
							manejadorBotonVer(material);
						});//FIN MANEJADDOR
						
						botonEditar.setOnAction(event -> {
							material = getTableView().getItems().get(getIndex());
							manejadorBotonEditar(material);
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
		this.mainApp.iniciarDialogoAgregarMaterial(material, DialogoAgregarMaterial.CREAR);
		this.actualizarTabla();
	}//FIN METODO
	
	@FXML private void manejadorBotonActualizar() {
		this.actualizarTabla();
	}//FIN METODO
	
	private void manejadorBotonVer(Material material) {
		this.mainApp.iniciarDialogoAgregarMaterial(material, DialogoAgregarMaterial.VER);
		this.actualizarTabla();
	}//FIN METODO
	
	private void manejadorBotonEditar(Material material) {
		this.mainApp.iniciarDialogoAgregarMaterial(material, DialogoAgregarMaterial.EDITAR);
		this.actualizarTabla();
	}//FIN METODO
	
	@FXML private void manejadorBotonCerrar() {
		this.mainApp.getEscenarioDialogos().close();
	}
		
}//FIN CLASE
