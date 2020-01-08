package mx.shf6.produccion.view;

import java.sql.Connection;
import java.util.ArrayList;

import javafx.collections.FXCollections;
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import mx.shf6.produccion.MainApp;
import mx.shf6.produccion.model.GrupoUsuario;
import mx.shf6.produccion.model.dao.GrupoUsuarioDAO;
import mx.shf6.produccion.model.dao.Seguridad;
import mx.shf6.produccion.utilities.Notificacion;
import mx.shf6.produccion.utilities.PTableColumn;

public class DialogoGrupoUsuario {
	
	//PROPIEDADES
	private MainApp mainApp;
	private Connection connection;
	private ArrayList<GrupoUsuario> listaGrupoUsuario;
	private GrupoUsuario grupoUsuario;
	
	//VARIABLES
	
	//CONSTANTES
	
	//COMPONENTES INTERFAZ
	@FXML private TableView<GrupoUsuario> tablaGrupoUsuario;
	@FXML private PTableColumn<GrupoUsuario, String> columnaGrupo;
	@FXML private PTableColumn<GrupoUsuario, String> columnaDescripcion;
	@FXML private PTableColumn<GrupoUsuario, String> acciones;
	@FXML private TextField campoBusqueda;
	
	//METODOS
	@FXML private void initialize () {
		this.listaGrupoUsuario = new ArrayList<GrupoUsuario>();
		this.grupoUsuario = new GrupoUsuario();
		this.inicializarComponentes();
	}//FIN METODO
	
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
		this.connection = this.mainApp.getConnection();
		this.inicializarTabla();
		this.actualizarTabla();
	}//FIN METODO
	
	private void inicializarComponentes() {
		this.campoBusqueda.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode().equals(KeyCode.ENTER))
					buscarRegistro();
			}//FIN METODO
		});//FIN SENTENCIA
	}//FIN METODO
	
	private void inicializarTabla() {
		this.columnaGrupo.setCellValueFactory(cellData -> cellData.getValue().nombreProperty());
		this.columnaDescripcion.setCellValueFactory(cellData -> cellData.getValue().descripcionProperty());
		this.columnaAcciones();
	}//FIN METODO
	
	private void actualizarTabla() {
		this.tablaGrupoUsuario.setItems(null);
		this.listaGrupoUsuario.clear();
		this.listaGrupoUsuario = GrupoUsuarioDAO.readTodos(this.connection);
		this.tablaGrupoUsuario.setItems(FXCollections.observableArrayList(this.listaGrupoUsuario));
	}//FIN METODO
	
	private void buscarRegistro() {
		this.tablaGrupoUsuario.setItems(null);
		this.listaGrupoUsuario.clear();
		this.listaGrupoUsuario = GrupoUsuarioDAO.readPorNombreDescripcionLike(this.connection, this.campoBusqueda.getText());
		this.tablaGrupoUsuario.setItems(FXCollections.observableArrayList(this.listaGrupoUsuario));
	}//FIN METODO
	
	private void columnaAcciones() {
		this.acciones.setCellValueFactory(new PropertyValueFactory<>("DUM"));
		Callback<TableColumn<GrupoUsuario, String>, TableCell<GrupoUsuario, String>> cellFactory =  param -> {
        	
			final TableCell<GrupoUsuario, String> cell = new TableCell<GrupoUsuario, String>() {  
        		final Button botonVer = new Button("V");
        		final Button botonEditar = new Button("E");
        		final Button botonEliminar = new Button("B");
        		final Button botonGestionar = new Button("P");
        		final HBox acciones = new HBox(botonVer, botonEditar, botonEliminar, botonGestionar);
        		
        		
		        //PARA MOSTRAR LOS DIALOGOS DE INSTITUCION
		        @Override
		        public void updateItem(String item, boolean empty) {
		        	botonVer.setGraphic(new ImageView(new Image(MainApp.class.getResourceAsStream("view/images/1x/ViewIcon.png"))));
		        	botonVer.setPrefSize(18.0, 18.0);
		        	botonVer.setPadding(Insets.EMPTY);
		        	botonVer.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
		        	botonVer.setStyle("-fx-background-color: transparent;");		        	
		        	botonVer.setCursor(Cursor.HAND);
		        	botonVer.setTooltip(new Tooltip("Ver cliente"));
		        	
		        	botonEditar.setGraphic(new ImageView(new Image(MainApp.class.getResourceAsStream("view/images/1x/ActualizarIcono.png"))));
		        	botonEditar.setPrefSize(16.0, 16.0);
		        	botonEditar.setPadding(Insets.EMPTY);
		        	botonEditar.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
		        	botonEditar.setStyle("-fx-background-color: transparent;");
		        	botonEditar.setCursor(Cursor.HAND);
		        	botonEditar.setTooltip(new Tooltip("Editar cliente"));
		        	
		        	botonEliminar.setGraphic(new ImageView(new Image(MainApp.class.getResourceAsStream("view/images/1x/RemoveIcon.png"))));
		        	botonEliminar.setPrefSize(16.0, 16.0);
		        	botonEliminar.setPadding(Insets.EMPTY);
		        	botonEliminar.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
		        	botonEliminar.setStyle("-fx-background-color: transparent;");
		        	botonEliminar.setCursor(Cursor.HAND);
		        	botonEliminar.setTooltip(new Tooltip("Eliminar cliente"));
		        	
		        	botonGestionar.setGraphic(new ImageView(new Image(MainApp.class.getResourceAsStream("view/images/1x/AsignarRolIcono.png"))));
		        	botonGestionar.setPrefSize(16.0, 16.0);
		        	botonGestionar.setPadding(Insets.EMPTY);
		        	botonGestionar.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
		        	botonGestionar.setStyle("-fx-background-color: transparent;");
		        	botonGestionar.setCursor(Cursor.HAND);
		        	botonGestionar.setTooltip(new Tooltip("Gestionar Permisos"));
		        	
		        	acciones.setSpacing(3);
		        	acciones.setPrefWidth(80.0);
		        	acciones.setAlignment(Pos.CENTER_LEFT);
		        	super.updateItem(item, empty);
		        	
		        	
		        	if (empty) {
		        		setGraphic(null);
		                setText(null);
		            } else {
		     	
		            	botonVer.setOnAction(event -> {
		            		if(Seguridad.verificarAcceso(mainApp.getConnection(), mainApp.getUsuario().getGrupoUsuarioFk(), "rGrupoUsuario")) { 
		            			grupoUsuario = getTableView().getItems().get(getIndex());
		            			verGrupoUsuario(grupoUsuario);
		            		}else
		            			Notificacion.dialogoAlerta(AlertType.WARNING, "Error", "No tienes permiso para realizar esta acción.");		            		
		            	});//FIN LISTENER
		            	
		            	botonEditar.setOnAction(event -> {
		            		if(Seguridad.verificarAcceso(mainApp.getConnection(), mainApp.getUsuario().getGrupoUsuarioFk(), "uGrupoUsuario")) { 
		            			grupoUsuario = getTableView().getItems().get(getIndex());
		            			modificarGrupoUsuario(grupoUsuario);
		            		}else
		            			Notificacion.dialogoAlerta(AlertType.WARNING, "Error", "No tienes permiso para realizar esta acción.");
		            	});//FIN LISTENER
		            
		            	botonEliminar.setOnAction(event -> {
		            		if(Seguridad.verificarAcceso(mainApp.getConnection(), mainApp.getUsuario().getGrupoUsuarioFk(), "dGrupoUsuario")) {
		            			grupoUsuario = getTableView().getItems().get(getIndex());
		            			eliminarGrupoUsuario(grupoUsuario);
		            		} else
		            			Notificacion.dialogoAlerta(AlertType.WARNING, "Error", "No tienes permiso para realizar esta acción.");		        					                	
		                });//FIN LISTENER
		            	
		            	botonGestionar.setOnAction(event -> {
		            		if(Seguridad.verificarAcceso(mainApp.getConnection(), mainApp.getUsuario().getGrupoUsuarioFk(), "uRolGrupoUsuario")) {
		            			grupoUsuario = getTableView().getItems().get(getIndex());
		            			esquemaSeguridad(grupoUsuario);
		            		} else
		            			Notificacion.dialogoAlerta(AlertType.WARNING, "Error", "No tienes permiso para realizar esta acción.");	
		            	});//FIN LISTENER
		            		
		            	setGraphic(acciones);		                
		                setText(null);
		                
		            }//FIN IF/ELSE
		        }//FIN METODO
		    };//FIN METODO
		    return cell;
		};//FIN METODO
		this.acciones.setCellFactory(cellFactory);
	}//FIN METODO
	
	private void verGrupoUsuario(GrupoUsuario grupoUsuario) {
		this.mainApp.iniciarDialogoAgregarGrupoUsuario(grupoUsuario, DialogoAgregarGrupoUsuario.VER);
	}//FIN METODO
	
	private void modificarGrupoUsuario(GrupoUsuario grupoUsuario) {
		this.mainApp.iniciarDialogoAgregarGrupoUsuario(grupoUsuario, DialogoAgregarGrupoUsuario.MODIFICAR);
	}//FIN METODO
	
	private void eliminarGrupoUsuario(GrupoUsuario grupoUsuario) {
		if (Notificacion.dialogoPreguntar("", "Estas a punto de eliminar el registro, ¿Deseas continuar?"))
			GrupoUsuarioDAO.delete(this.connection, grupoUsuario);
		this.actualizarTabla();
	}//FIN METODO
	
	private void esquemaSeguridad(GrupoUsuario grupoUsuario) {
		this.mainApp.iniciarDialogoEsquemaSeguridad(grupoUsuario);
	}//FIN METODO
	
	//MANEJADORES
	@FXML private void manejadorBotonBuscar() {
		this.buscarRegistro();
	}//FIN METODO
	
	@FXML private void manejadorBotonActualizar() {
		this.actualizarTabla();
	}//FIN METODO
	
	@FXML private void manejadorBotonCrear() {
		this.mainApp.iniciarDialogoAgregarGrupoUsuario(grupoUsuario, DialogoAgregarGrupoUsuario.CREAR);
		this.actualizarTabla();
	}//FIN METODO
	
	@FXML private void manejadorBotonCerrar() {
		this.mainApp.getEscenarioDialogos().close();
	}//FIN METODO
}//FIN CLASE
