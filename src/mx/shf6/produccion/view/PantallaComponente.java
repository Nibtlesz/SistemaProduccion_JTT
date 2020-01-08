package mx.shf6.produccion.view;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.RadioMenuItem;
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
import mx.shf6.produccion.model.Componente;
import mx.shf6.produccion.model.TipoComponente;
import mx.shf6.produccion.model.dao.ComponenteDAO;
import mx.shf6.produccion.utilities.GestorArchivos;
import mx.shf6.produccion.utilities.Notificacion;
import mx.shf6.produccion.utilities.PTableColumn;

public class PantallaComponente {

	//PROPIEDADES
	private MainApp mainApp;
	private Componente componente;
	private ArrayList<Componente> listaComponente;
	
	//VARIABLES
	
	//COMPONENTES INTERFAZ
	@FXML private TextField campoTextoBusqueda;
	@FXML private TableView<Componente> tablaComponente;
	@FXML private PTableColumn<Componente, String> columnaNumeroParte;
	@FXML private PTableColumn<Componente, String> columnaDescripcion;
	@FXML private PTableColumn<Componente, String> columnaTipoComponente;
	//@FXML private PTableColumn<Componente, String> columnaTipoMaterial;
	@FXML private PTableColumn<Componente, Double> columnaCosto;
	@FXML private PTableColumn<Componente, String> columnaNotas;
	@FXML private PTableColumn<Componente, String> columnaStatus;
	@FXML private PTableColumn<Componente, String> columnaAcciones;
	@FXML private RadioMenuItem checkMenuTodos;
	@FXML private RadioMenuItem checkMenuMateriaPrima;
	@FXML private RadioMenuItem checkMenuMiscelaneo;
	@FXML private RadioMenuItem checkMenuPartePrimaria;
	@FXML private RadioMenuItem checkMenuSubEnsamble;
	@FXML private RadioMenuItem checkMenuEnsamble;
	
	//INICIA COMPONENTES INTERFAZ USUARIO
	@FXML private void initialize() {
		this.componente = new Componente();
		this.inicializaComponentes();
	}//FIN METODO
	
	//ACCESO CLASE PRINCIPAL
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
		this.listaComponente = ComponenteDAO.readComponente(this.mainApp.getConnection());
		this.inicializaTabla();
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
		this.columnaNumeroParte.setCellValueFactory(cellData -> cellData.getValue().numeroParteProperty());
		this.columnaDescripcion.setCellValueFactory(cellData -> cellData.getValue().descripcionProperty());
		this.columnaTipoComponente.setCellValueFactory(cellData -> cellData.getValue().tipoComponenteProperty());
		//this.columnaTipoMaterial.setCellValueFactory(cellData -> cellData.getValue().getMaterial(this.mainApp.getConnection()).descripcionProperty());
		this.columnaCosto.setCellValueFactory(cellData -> cellData.getValue().costoProperty());
		this.columnaNotas.setCellValueFactory(cellData -> cellData.getValue().notasProperty());
		this.columnaStatus.setCellValueFactory(cellData -> cellData.getValue().statusProperty());
		this.inicializarColumnaAcciones();
	}//FIN METODO.
	
	private void actualizarTabla() {
		this.tablaComponente.setItems(null);
		this.listaComponente.clear();
		if (this.checkMenuTodos.isSelected())
			this.listaComponente = ComponenteDAO.readComponente(this.mainApp.getConnection());
		else if (this.checkMenuMateriaPrima.isSelected())
			this.listaComponente = ComponenteDAO.readComponenteTipoComponente(this.mainApp.getConnection(), TipoComponente.toCharacter(TipoComponente.MATERIA_PRIMA));
		else if (this.checkMenuMiscelaneo.isSelected())
			this.listaComponente = ComponenteDAO.readComponenteTipoComponente(this.mainApp.getConnection(), TipoComponente.toCharacter(TipoComponente.COMPRADO));
		else if (this.checkMenuPartePrimaria.isSelected())
			this.listaComponente = ComponenteDAO.readComponenteTipoComponente(this.mainApp.getConnection(), TipoComponente.toCharacter(TipoComponente.PARTE_PRIMARIA));
		else if (this.checkMenuSubEnsamble.isSelected())
			this.listaComponente = ComponenteDAO.readComponenteTipoComponente(this.mainApp.getConnection(), TipoComponente.toCharacter(TipoComponente.SUB_ENSAMBLE));
		else if (this.checkMenuEnsamble.isSelected())
			this.listaComponente = ComponenteDAO.readComponenteTipoComponente(this.mainApp.getConnection(), TipoComponente.toCharacter(TipoComponente.ENSAMBLE));
		this.tablaComponente.setItems(ComponenteDAO.toObservableList(this.listaComponente));
	}//FIN METODO
	
	@FXML private void buscarRegistroTabla() {
		this.tablaComponente.setItems(null);
		this.listaComponente.clear();
		if (this.checkMenuTodos.isSelected())
			this.listaComponente = ComponenteDAO.readComponente(this.mainApp.getConnection(), campoTextoBusqueda.getText());
		else if (this.checkMenuMateriaPrima.isSelected())
			this.listaComponente = ComponenteDAO.readComponenteTipoComponenteParte(this.mainApp.getConnection(), TipoComponente.toCharacter(TipoComponente.MATERIA_PRIMA), campoTextoBusqueda.getText());
		else if (this.checkMenuMiscelaneo.isSelected())
			this.listaComponente = ComponenteDAO.readComponenteTipoComponenteParte(this.mainApp.getConnection(), TipoComponente.toCharacter(TipoComponente.COMPRADO), campoTextoBusqueda.getText());
		else if (this.checkMenuPartePrimaria.isSelected())
			this.listaComponente = ComponenteDAO.readComponenteTipoComponenteParte(this.mainApp.getConnection(), TipoComponente.toCharacter(TipoComponente.PARTE_PRIMARIA), campoTextoBusqueda.getText());
		else if (this.checkMenuSubEnsamble.isSelected())
			this.listaComponente = ComponenteDAO.readComponenteTipoComponenteParte(this.mainApp.getConnection(), TipoComponente.toCharacter(TipoComponente.SUB_ENSAMBLE), campoTextoBusqueda.getText());
		else if (this.checkMenuEnsamble.isSelected())
			this.listaComponente = ComponenteDAO.readComponenteTipoComponenteParte(this.mainApp.getConnection(), TipoComponente.toCharacter(TipoComponente.ENSAMBLE), campoTextoBusqueda.getText());
		this.tablaComponente.setItems(ComponenteDAO.toObservableList(this.listaComponente));
	}//FIN METODO
	
	private void inicializarColumnaAcciones() {
		this.columnaAcciones.setCellValueFactory(new PropertyValueFactory<>("DUM"));
		Callback<TableColumn<Componente, String>, TableCell<Componente, String>> cellFactory = param -> {
			final TableCell<Componente, String> cell = new TableCell<Componente, String>() {
				final Button botonVer = new Button("Ver");
				final Button botonEditar = new Button("Editar");
				final Button botonEliminar = new Button("Eliminar");
				final Button botonDibujo = new Button("Dibujo");
				final Button botonDetalle = new Button("Detalle");
				final HBox cajaBotones = new HBox(botonVer, botonEditar, botonEliminar, botonDibujo, botonDetalle);
				
				
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
					
					botonDibujo.setGraphic(new ImageView(new Image(MainApp.class.getResourceAsStream("view/images/1x/DibujoIcono.png"))));
					botonDibujo.setPrefSize(16.0, 16.0);
					botonDibujo.setPadding(Insets.EMPTY);
					botonDibujo.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
					botonDibujo.setStyle("-fx-background-color: transparent");
					botonDibujo.setCursor(Cursor.HAND);
					botonDibujo.setTooltip(new Tooltip("Ver dibujo"));
					
					botonDetalle.setGraphic(new ImageView(new Image(MainApp.class.getResourceAsStream("view/images/1x/DetalleIcono.png"))));
					botonDetalle.setPrefSize(16.0, 16.0);
					botonDetalle.setPadding(Insets.EMPTY);
					botonDetalle.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
					botonDetalle.setStyle("-fx-background-color: transparent");
					botonDetalle.setCursor(Cursor.HAND);
					botonDetalle.setTooltip(new Tooltip("Detalle Ensamble"));
					
					super.updateItem(item, empty);
					if (empty) {
						super.setGraphic(null);
						super.setText(null);
					} else {
						
						//MANEJADORES PARA LOS BOTONES
						botonVer.setOnAction(event -> {
							componente = getTableView().getItems().get(getIndex());
							manejadorBotonVer(componente);
						});//FIN MANEJADDOR
						
						botonEditar.setOnAction(event -> {
							componente = getTableView().getItems().get(getIndex());
							manejadorBotonEditar(componente);
						});//FIN MANEJADDOR
						
						botonEliminar.setOnAction(event -> {
							componente = getTableView().getItems().get(getIndex());
							manejadorBotonEliminar(componente);
						});//FIN MANEJADDOR
						
						botonDibujo.setOnAction(event -> {
							componente = getTableView().getItems().get(getIndex());
							manejadorBotonDibujo(componente);
						});//FIN MANEJADDOR
						
						botonDetalle.setOnAction(event -> {
							componente = getTableView().getItems().get(getIndex());
							manejadorBotonDetalle(componente);
						});//FIN MANEJADDOR
						
						cajaBotones.setAlignment(Pos.CENTER);
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
		this.mainApp.iniciarDialogoComponente(componente, DialogoComponente.CREAR);
		this.actualizarTabla();
	}//FIN METODO
	
	@FXML private void manejadorBotonActualizar() {
		this.actualizarTabla();
	}//FIN METODO
	
	private void manejadorBotonVer(Componente componente) {
		this.mainApp.iniciarDialogoComponente(componente, DialogoComponente.VER);
	}//FIN METODO
	
	private void manejadorBotonEditar(Componente componente) {
		this.mainApp.iniciarDialogoComponente(componente, DialogoComponente.EDITAR);
		this.actualizarTabla();
	}//FIN METODO
	
	private void manejadorBotonEliminar(Componente componente) {
		if (Notificacion.dialogoPreguntar("", "Estas a punto de eliminar el registro, ¿Deseas continuar?"))
			ComponenteDAO.deleteComponente(this.mainApp.getConnection(), componente);
		this.actualizarTabla();
	}//FIN METODO
	
	private void manejadorBotonDibujo(Componente componente) {
		String rutaArchivoDibujo = MainApp.RAIZ_SERVIDOR + "Dibujos\\" +  this.componente.getCliente(this.mainApp.getConnection()).getNombre() + "\\" + this.componente.getNumeroParte() + ".pdf";
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
			if (archivoCliente == null)
				//Notificacion.dialogoAlerta(AlertType.ERROR, "", "Aun no has seleccionado un archivo");
				System.out.println("");
			else {
				File rutaCarpetaDibujo = new File(MainApp.RAIZ_SERVIDOR + "Dibujos\\" +  this.componente.getCliente(this.mainApp.getConnection()).getNombre());
				rutaCarpetaDibujo.mkdirs();
				if (GestorArchivos.cargarArchivo(archivoCliente, rutaArchivoDibujo))
					Notificacion.dialogoAlerta(AlertType.INFORMATION, "", "El archivo se ha guardado de forma correcta");
				else
					Notificacion.dialogoAlerta(AlertType.INFORMATION, "", "El archivo no se pudo cargar al sistema");
			}//FIN IF ELSE
		}//FIN IF/ELSE
	}//FIN METODO
	
	private void manejadorBotonDetalle(Componente componente) {
		if (TipoComponente.isComponenteBasico(componente.getTipoComponente())) {
			Notificacion.dialogoAlerta(AlertType.WARNING, "", "No existe detalle de ensamble para este componente");
		} else {
			this.mainApp.iniciarDialogoDetalleComponente(componente);
		}//FIN IF/ELSE
	}//FIN METODO
	
	@FXML private void manejadorBotonFiltroTodos() {
		this.actualizarTabla();
	}//FIN METODO
	
	@FXML private void manejadorBotonMateriasPrimas() {
		this.mainApp.iniciarDialogoTipoMateriaPrima();
	}//FIN METODO
		
	@FXML private void manejadorBotonMaterial() {
		this.mainApp.iniciarDialogoMaterial();
	}//FIN METODO
	
	@FXML private void manejadorBotonMiscelaneos() {
		this.mainApp.iniciarDialogoTipoMiscelaneo();
	}//FIN METODO
	
	@FXML private void manejadoBotonAcabados() {
		this.mainApp.iniciarDialogoAcabado();
	}//FIN METODO
	
	@FXML private void manejadorBotonTratamiento() {
		this.mainApp.iniciarDialogoTratamiento();
	}//FIN METODO
}//FIN CLASE
