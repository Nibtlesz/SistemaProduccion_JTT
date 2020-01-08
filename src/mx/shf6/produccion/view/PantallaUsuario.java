package mx.shf6.produccion.view;


import java.sql.Connection;
import java.sql.Date;
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
import mx.shf6.produccion.model.Usuario;
import mx.shf6.produccion.model.dao.Seguridad;
import mx.shf6.produccion.model.dao.UsuarioDAO;
import mx.shf6.produccion.utilities.Notificacion;
import mx.shf6.produccion.utilities.PTableColumn;

public class PantallaUsuario {
	
	//PROPIEDADES
	private MainApp mainApp;
	private Connection connection;
	private ArrayList<Usuario> listaUsuarios;
	private Usuario usuario;
	
	//VARIABLES
	
	//CONSTANTES
	
	//COMPONENTES INTERFAZ
	@FXML private TableView<Usuario> tablaUsuario;
	@FXML private PTableColumn<Usuario, String> columnaUsuario;
	@FXML private PTableColumn<Usuario, String> columnaCorreo;
	@FXML private PTableColumn<Usuario, Date> columnaFechaRegistro;
	@FXML private PTableColumn<Usuario, Date> columnaFechaBloqueo;
	@FXML private PTableColumn<Usuario, String> columnaStatus;
	@FXML private PTableColumn<Usuario, String> acciones;
	@FXML private TextField campoBuscar;
	
	//METODOS
	@FXML private void initialize() {
		this.listaUsuarios = new ArrayList<Usuario>();
		this.usuario = new Usuario();
		this.iniciarComponentes();
	}//FIN METODO

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
		this.connection = this.mainApp.getConnection();
		this.inicializarTabla();
		this.actualizarTabla();
	}//FIN METODO
	
	private void iniciarComponentes() {
		this.campoBuscar.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode().equals(KeyCode.ENTER))
					buscarRegistro();
			}//FIN METODO
		});//FIN SENTENCIA
	}//FIN METODO
	
	private void inicializarTabla() {
		this.columnaUsuario.setCellValueFactory(cellData -> cellData.getValue().usuarioProperty());
		this.columnaCorreo.setCellValueFactory(cellData -> cellData.getValue().correoElectronicoProperty());
		this.columnaFechaRegistro.setCellValueFactory(cellData -> cellData.getValue().fechaRegistroProperty());
		this.columnaFechaBloqueo.setCellValueFactory(cellData -> cellData.getValue().fechaBloqueProperty());
		this.columnaStatus.setCellValueFactory(cellData -> cellData.getValue().descripcionStatusProperty());
		inicializarColumnaAcciones();
	}//FIN METODO
	
	private void actualizarTabla() {
		this.tablaUsuario.setItems(null);
		this.listaUsuarios.clear();
		this.listaUsuarios = UsuarioDAO.readTodos(this.connection);
		this.tablaUsuario.setItems(FXCollections.observableArrayList(this.listaUsuarios));
	}//FIN METODO
	
	private void buscarRegistro() {
		this.tablaUsuario.setItems(null);
		this.listaUsuarios.clear();
		this.listaUsuarios = UsuarioDAO.readPorUsuarioCorreoLike(this.connection, this.campoBuscar.getText());
		this.tablaUsuario.setItems(FXCollections.observableArrayList(this.listaUsuarios));
	}//FIN METODO
	
	private void inicializarColumnaAcciones() {
		this.acciones.setCellValueFactory(new PropertyValueFactory<>("DUM"));
        Callback<TableColumn<Usuario, String>, TableCell<Usuario, String>> cellFactory =  param -> {
        	
        	final TableCell<Usuario, String> cell = new TableCell<Usuario, String>() {  
        		final Button botonVer = new Button("V");
        		final Button botonEditar = new Button("E");
        		final Button botonEliminar = new Button("B");
        		final HBox acciones = new HBox(botonVer, botonEditar, botonEliminar);
        		
        		
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
		        	
		        	acciones.setSpacing(3);
		        	acciones.setPrefWidth(80.0);
		        	acciones.setAlignment(Pos.CENTER_LEFT);
		        	super.updateItem(item, empty);
		        	
		        	
		        	if (empty) {
		        		setGraphic(null);
		                setText(null);
		            } else {
		            	
		            	botonVer.setOnAction(event -> {
		            		if(Seguridad.verificarAcceso(mainApp.getConnection(), mainApp.getUsuario().getGrupoUsuarioFk(), "rUsuario")) { 
		            			usuario = getTableView().getItems().get(getIndex());
		            			verUsuario(usuario);
		            		}else
		            			Notificacion.dialogoAlerta(AlertType.WARNING, "Error", "No tienes permiso para realizar esta acción.");		            		
		            	});//FIN LISTENER
		            	
		            	botonEditar.setOnAction(event -> {
		            		if(Seguridad.verificarAcceso(mainApp.getConnection(), mainApp.getUsuario().getGrupoUsuarioFk(), "uUsuario")) { 
		            			usuario = getTableView().getItems().get(getIndex());
		            			modificarUsuario(usuario);
		            		}else
		            			Notificacion.dialogoAlerta(AlertType.WARNING, "Error", "No tienes permiso para realizar esta acción.");
		            	});//FIN LISTENER
		            
		            	botonEliminar.setOnAction(event -> {
		            		if(Seguridad.verificarAcceso(mainApp.getConnection(), mainApp.getUsuario().getGrupoUsuarioFk(), "dUsuario")) {
		            			usuario = getTableView().getItems().get(getIndex());
		            			eliminarUsuario(usuario);
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
	
	private void verUsuario(Usuario usuario) {
		this.mainApp.iniciarDialogoUsuario(usuario, DialogoUsuario.VER);
	}//FIN METODO
	
	private void modificarUsuario(Usuario usuario) {
		this.mainApp.iniciarDialogoUsuario(usuario, DialogoUsuario.EDITAR);
		this.actualizarTabla();
	}//FIN METODO
	
	private void eliminarUsuario(Usuario usuario) {
		if (Notificacion.dialogoPreguntar("", "Estas a punto de eliminar el registro, ¿Deseas continuar?"))
			UsuarioDAO.eliminar(this.connection, usuario);
		this.actualizarTabla();
	}//FIN METODO
	
	//MANEJADORES
	@FXML private void manejadorBotonActualizar() {
		this.actualizarTabla();
	}//FIN METODO
	
	@FXML private void manejadorBotonBuscar() {
		this.buscarRegistro();
	}//FIN METODO
	
	@FXML private void manejadorBotonCrear() {
		this.mainApp.iniciarDialogoUsuario(usuario, DialogoUsuario.CREAR);
		this.actualizarTabla();
	}//FIN METODO
	
	@FXML private void manejadorBotonPermisos() {
		this.mainApp.iniciarDialogoPermiso();
	}//FIN METODO
	
	@FXML private void manejadorBotonGrupoUsuario() {
		this.mainApp.iniciarDialogoGrupoUsuario();
	}//FIN METODO
}//FIN CLASE 
