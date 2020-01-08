package mx.shf6.produccion.view;

import java.util.ArrayList;


import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;
import javafx.scene.layout.HBox;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.Cursor;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import mx.shf6.produccion.MainApp;
import mx.shf6.produccion.model.Rol;
import mx.shf6.produccion.model.dao.RolDAO;
import mx.shf6.produccion.utilities.Notificacion;
import mx.shf6.produccion.utilities.PTableColumn;

public class DialogoPermiso {
	
	
	//PROPIEDADES
	private MainApp mainApp;
	private Rol permiso;
	private ArrayList<Rol> listaPermisos;
	
	//VARIABLES
	
	//COMPONENTES INTERFAZ PERMISOS
	@FXML private TableView<Rol> tablaPermisos;
	@FXML private PTableColumn<Rol, String> codigoItemColumna;
	@FXML private PTableColumn<Rol, String> descripcionColumna;
	@FXML private PTableColumn<Rol, String> acciones;
	@FXML private TextField campoBuscar;
	
	//INICIALIZAR COMPONENTES 
	@FXML private void initialize() {
		this.permiso = new Rol();
		this.inicializarComponentes();
	}//FIN METODO
	
	//ACCESO A CLASE PRINCIPAL CONTROLA VISTAS
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
		this.listaPermisos = RolDAO.readTodos(this.mainApp.getConnection());
		this.inicializarTabla();
		this.actualizarTabla();
	}//FIN METODO
	
	private void inicializarComponentes() {
		this.campoBuscar.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode().equals(KeyCode.ENTER))
					buscarRegistro();
			}//FIN METODO
		});//FIN SENTENCIA
	}//FIN METODO
	
	private void inicializarTabla() {
		this.codigoItemColumna.setCellValueFactory(cellData -> cellData.getValue().codigoItemProperty());
		this.descripcionColumna.setCellValueFactory(cellData -> cellData.getValue().descripcionProperty());
		this.columnaAcciones();
	}//FIN METODO
	
	//ACTUALIZAR LA TABLA CON LOS ULTIMOS CAMBIOS EN LA BASE DE DATOS
	private void actualizarTabla() {
		this.tablaPermisos.setItems(null);
		this.listaPermisos.clear();
		this.listaPermisos = RolDAO.readTodos(this.mainApp.getConnection());
		this.tablaPermisos.setItems(RolDAO.toObservableList(listaPermisos));
	}//FIN METODO
	
	private void buscarRegistro() {
		this.tablaPermisos.setItems(null);
		this.listaPermisos.clear();
		this.listaPermisos = RolDAO.readCodigoLike(this.mainApp.getConnection(), this.campoBuscar.getText());
		this.tablaPermisos.setItems(FXCollections.observableArrayList(this.listaPermisos));
	}
	
	private void columnaAcciones() {
		this.acciones.setCellValueFactory(new PropertyValueFactory<>("DUM"));
		Callback<TableColumn<Rol, String>, TableCell<Rol, String>> cellFactory = param -> {
			
			final TableCell<Rol, String> cell = new TableCell<Rol, String>() {
				final Button botonVer = new Button("Ver");
				final Button botonEditar = new Button("Editar");
				final Button botonEliminar = new Button("Eliminar");
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
		        	botonVer.setTooltip(new Tooltip("Ver permiso"));
		        	
		        	botonEditar.setGraphic(new ImageView(new Image(MainApp.class.getResourceAsStream("view/images/1x/ActualizarIcono.png"))));
		        	botonEditar.setPrefSize(16.0, 16.0);
		        	botonEditar.setPadding(Insets.EMPTY);
		        	botonEditar.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
		        	botonEditar.setStyle("-fx-background-color: transparent;");
		        	botonEditar.setCursor(Cursor.HAND);
		        	botonEditar.setTooltip(new Tooltip("Editar permiso"));
		        	
		        	botonEliminar.setGraphic(new ImageView(new Image(MainApp.class.getResourceAsStream("view/images/1x/RemoveIcon.png"))));
		        	botonEliminar.setPrefSize(16.0, 16.0);
		        	botonEliminar.setPadding(Insets.EMPTY);
		        	botonEliminar.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
		        	botonEliminar.setStyle("-fx-background-color: transparent;");
		        	botonEliminar.setCursor(Cursor.HAND);
		        	botonEliminar.setTooltip(new Tooltip("Eliminar permiso"));
		        	
		        	
		        	acciones.setSpacing(3);
		        	acciones.setPrefWidth(80.0);
		        	acciones.setAlignment(Pos.CENTER_LEFT);
		        	super.updateItem(item, empty);
		        	
		        	if (empty) {
		        		setGraphic(null);
		        		setText(null);
		        	} else {
		        		
		        		//MANEJADORES PARA LOS BOTONES
		            	botonVer.setOnAction(event -> {
		            		permiso = getTableView().getItems().get(getIndex());
		            		manejadorBotonVer(permiso);
		            	});//FIN MANEJADOR
		            	
		            	botonEliminar.setOnAction(event -> {
		            		permiso = getTableView().getItems().get(getIndex());
		            		manejadorBotonEliminar(permiso);
		            	});//FIN MANEJADOR
		            	
		            	botonEditar.setOnAction(event -> {
		            		permiso = getTableView().getItems().get(getIndex());
		            		manejadorBotonModificar(permiso);
		            	});//FIN MANEJADOR
		            	
		            	setGraphic(acciones);
		            	setText(null);
		        	}//FIN IF ELSE
		        }//FIN METODO
			};//FIN METODO
			return cell;
		};//FIN METODO
		this.acciones.setCellFactory(cellFactory);
	}//FIN METODO

	
	@FXML private void manejadorBotonCrear() {
		this.mainApp.iniciarDialogoAgregarPermiso(permiso, DialogoAgregarPermiso.CREAR);
		this.actualizarTabla();
	} 
	
	//VER PERMISO
	private void manejadorBotonVer(Rol permiso) {
		this.mainApp.iniciarDialogoAgregarPermiso(permiso, DialogoAgregarPermiso.VER);
	}//FIN METODO
	
	//MODIFICAR PERMISO
	private void manejadorBotonModificar(Rol permiso) {
		this.mainApp.iniciarDialogoAgregarPermiso(permiso, DialogoAgregarPermiso.EDITAR);
		this.actualizarTabla();
	}//FIN METODO
	
	//QUITAR PERMISO
	private void manejadorBotonEliminar(Rol permiso) {
		if (Notificacion.dialogoPreguntar("", "¿Realmente desea quitar esté permiso?"))
			RolDAO.delete(this.mainApp.getConnection(), permiso);
		this.actualizarTabla();
	}//FIN METODO
	
	@FXML private void manejadorBotonActualizar() {
		this.actualizarTabla();
	}
	
	@FXML private void manejadorBotonCerrar() {
		this.mainApp.getEscenarioDialogos().close();
	}//FIN METODO
	
	//MANEJADORES
	@FXML private void manejadorBotonBuscar() {
		this.buscarRegistro();
	}//FIN METODO
}//FIN CLASE
