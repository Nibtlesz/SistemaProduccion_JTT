package mx.shf6.produccion.view;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.util.Callback;
import mx.shf6.produccion.MainApp;
import mx.shf6.produccion.model.ArchivoProyecto;
import mx.shf6.produccion.model.Cliente;
import mx.shf6.produccion.model.Proyecto;
import mx.shf6.produccion.model.dao.ArchivoProyectoDAO;
import mx.shf6.produccion.utilities.GestorArchivos;
import mx.shf6.produccion.utilities.Notificacion;
import mx.shf6.produccion.utilities.PTableColumn;

public class DialogoArchivos {
	//PROPIEDADES
	private MainApp mainApp;
	private Proyecto proyecto;
	private ArrayList<ArchivoProyecto> listaArchivoProyecto;
	private ArchivoProyecto archivoProyecto ;
	private Cliente cliente;
	
	//VARIABLES
	
	//COMPONENTES INTERFAZ
	@FXML private Label etiquetaNombreProyecto;
	@FXML private TextField campoTextoBusqueda;
	@FXML private TableView<ArchivoProyecto> tablaArchivoProyecto;
	@FXML private PTableColumn<ArchivoProyecto, String> columnaCodigo;
	@FXML private PTableColumn<ArchivoProyecto, String> columnaDescripcion;
	@FXML private PTableColumn<ArchivoProyecto, String> columnaAcciones;
		
			
	//INICIA COMPONENTES INTERFAZ USUARIO
	@FXML private void initialize() {
		this.inicializaComponentes();
		this.inicializaTabla();
	}//FIN METODO
	
	//ACCESO CLASE PRINCIPAL
	public void setMainApp(MainApp mainApp, Proyecto proyecto, Cliente cliente) {
		this.mainApp = mainApp;
		this.proyecto = proyecto;
		this.cliente = cliente;
		this.archivoProyecto = new ArchivoProyecto();
		this.listaArchivoProyecto = ArchivoProyectoDAO.readArchivoProyectoCliente(this.mainApp.getConnection(),this.proyecto.getSysPK());
		this.etiquetaNombreProyecto.setText(this.proyecto.getCodigo());
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
		this.columnaCodigo.setCellValueFactory(cellData -> cellData.getValue().codigoProperty());
		this.columnaDescripcion.setCellValueFactory(cellData -> cellData.getValue().descripcionProperty());
		this.inicializarColumnaAcciones();
	}//FIN METODO.
	
	private void actualizarTabla() {
		this.tablaArchivoProyecto.setItems(null);
		this.listaArchivoProyecto.clear();
		this.listaArchivoProyecto = ArchivoProyectoDAO.readArchivoProyectoCliente(this.mainApp.getConnection(), this.proyecto.getSysPK());
		this.tablaArchivoProyecto.setItems(ArchivoProyectoDAO.toObservableList(this.listaArchivoProyecto));
	}//FIN METODO
		
	@FXML private void buscarRegistroTabla() {
		this.tablaArchivoProyecto.setItems(null);
		this.listaArchivoProyecto.clear();
		this.listaArchivoProyecto = ArchivoProyectoDAO.readArchivoProyecto(this.mainApp.getConnection(), this.campoTextoBusqueda.getText(), this.proyecto.getSysPK());
		this.tablaArchivoProyecto.setItems(ArchivoProyectoDAO.toObservableList(this.listaArchivoProyecto));
	}//FIN METODO
	
	private void inicializarColumnaAcciones() {
		this.columnaAcciones.setCellValueFactory(new PropertyValueFactory<>("DUM"));
		Callback<TableColumn<ArchivoProyecto, String>, TableCell<ArchivoProyecto, String>> cellFactory = param -> {
			
			final TableCell<ArchivoProyecto, String> cell = new TableCell<ArchivoProyecto, String>() {
				final Button botonVer = new Button("Ver");
				final Button botonEditar = new Button("Editar");
				final Button botonArchivo = new Button("Archivo");
				final Button botonEliminar = new Button("Eliminar");
				final HBox cajaBotones = new HBox(botonVer, botonEditar,botonArchivo, botonEliminar);
				
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
					
					botonArchivo.setGraphic(new ImageView(new Image(MainApp.class.getResourceAsStream("view/images/1x/DibujoIcono.png"))));
		        	botonArchivo.setPrefSize(16.0, 16.0);
		        	botonArchivo.setPadding(Insets.EMPTY);
		        	botonArchivo.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
		        	botonArchivo.setStyle("-fx-background-color: transparent;");
		        	botonArchivo.setCursor(Cursor.HAND);
		        	botonArchivo.setTooltip(new Tooltip("Ver Dibujo"));
					
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
							archivoProyecto = getTableView().getItems().get(getIndex());
							manejadorBotonVer(archivoProyecto);
						});//FIN MANEJADDOR
						
						botonEditar.setOnAction(event -> {
							archivoProyecto = getTableView().getItems().get(getIndex());
							manejadorBotonEditar(archivoProyecto);
						});//FIN MANEJADDOR
						
						botonArchivo.setOnAction(event -> {
							archivoProyecto = getTableView().getItems().get(getIndex());
							manejadorBotonDibujo(archivoProyecto);
						});//FIN MANEJADDOR
						
						botonEliminar.setOnAction(event -> {
							archivoProyecto = getTableView().getItems().get(getIndex());
							manejadorBotonEliminar(archivoProyecto);
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
		
		this.mainApp.iniciarDialogoArchivoProyecto(archivoProyecto, DialogoArchivoProyecto.CREAR, proyecto);
		this.actualizarTabla();
	}//FIN METODO
	
	@FXML private void manejadorBotonActualizar() {
		this.actualizarTabla();
	}//FIN METODO
	
	private void manejadorBotonVer(ArchivoProyecto archivoProyecto) {
		this.mainApp.iniciarDialogoArchivoProyecto(archivoProyecto, DialogoArchivoProyecto.VER, proyecto);
		this.actualizarTabla();
	}//FIN METODO
	
	private void manejadorBotonEditar(ArchivoProyecto archivoProyecto) {
		this.mainApp.iniciarDialogoArchivoProyecto(archivoProyecto, DialogoArchivoProyecto.EDITAR, proyecto);
		this.actualizarTabla();
	}//FIN METODO
	
	private void manejadorBotonDibujo(ArchivoProyecto archivoProyecto) {
		String rutaArchivoDibujo = MainApp.RAIZ_SERVIDOR + "Clientes\\" + cliente.getNombre()  + "\\Proyectos\\" + this.proyecto.getCodigo() + "\\" + this.archivoProyecto.getCodigo() +  ".pdf";
		File archivoDibujo = new File(rutaArchivoDibujo);
		if (archivoDibujo.exists()) {
			//Notificacion.dialogoAlerta(AlertType.CONFIRMATION, "", "El archivo se va abrir...");
			try {
				Desktop.getDesktop().open(archivoDibujo);
			} catch (IOException ex) {
				Notificacion.dialogoException(ex);
			}//FIN TRY/CATCH
		} else {
			FileChooser escogerArchivo = new FileChooser();
			List<String> listaExtensiones = new ArrayList<String>();
			listaExtensiones.add("*.PDF");
			ExtensionFilter filtroExtensiones = new ExtensionFilter("Archivos de dibujo y diseño (*.pdf)", listaExtensiones);
			escogerArchivo.getExtensionFilters().add(filtroExtensiones);
			File archivoCliente = escogerArchivo.showOpenDialog(this.mainApp.getEscenarioPrincipal());
			if (archivoCliente == null) {
				Notificacion.dialogoAlerta(AlertType.ERROR, "", "Aun no has seleccionado un archivo");
				System.out.println("");
			}else {
				File rutaCarpetaDibujo = new File(MainApp.RAIZ_SERVIDOR + "Clientes\\" + "\\Proyectos\\" +this.proyecto.getCodigo()  );
				rutaCarpetaDibujo.mkdirs();
				if (GestorArchivos.cargarArchivo(archivoCliente, rutaArchivoDibujo))
					Notificacion.dialogoAlerta(AlertType.INFORMATION, "", "El archivo se ha guardado de forma correcta");
				else
					Notificacion.dialogoAlerta(AlertType.INFORMATION, "", "El archivo no se pudo cargar al sistema");
			}//FIN IF ELSE
		}//FIN IF/ELSE
	}//FIN METODO
	
	private void manejadorBotonEliminar(ArchivoProyecto archivoProyecto) {
		if (Notificacion.dialogoPreguntar("", "Estas a punto de eliminar el registro, ¿Deseas continuar?")) {
			String rutaArchivoDibujo = MainApp.RAIZ_SERVIDOR + "Clientes\\" + cliente.getNombre()  + "\\Proyectos\\" + this.proyecto.getCodigo() + "\\" + this.archivoProyecto.getCodigo() +  ".pdf";
			File archivoDibujo = new File(rutaArchivoDibujo);
			archivoDibujo.delete();
			ArchivoProyectoDAO.deleteArchivoProyecto(this.mainApp.getConnection(), archivoProyecto);
		}
		this.actualizarTabla();
	}//FIN METODO
	
	@FXML private void manejadorBotonCerrar() {
		this.mainApp.getEscenarioDialogos().close();
	}//FIN METODO

}
