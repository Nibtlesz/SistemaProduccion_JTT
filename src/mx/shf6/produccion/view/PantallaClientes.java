package mx.shf6.produccion.view;

import java.util.ArrayList;
import java.io.*;

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
import mx.shf6.produccion.model.Cliente;
import mx.shf6.produccion.model.dao.ClienteDAO;
import mx.shf6.produccion.model.dao.DomicilioDAO;
import mx.shf6.produccion.model.dao.Seguridad;
import mx.shf6.produccion.utilities.Notificacion;
import mx.shf6.produccion.utilities.PTableColumn;

public class PantallaClientes {

	//PROPIEDADES
	private MainApp mainApp;
	private Cliente cliente;
	private ArrayList<Cliente> listaClientes;


	//COMPONENTES INTERZAS USUARIO
	@FXML private TableView<Cliente> tablaCliente;
	@FXML private PTableColumn<Cliente, String> codigoColumna;
	@FXML private PTableColumn<Cliente, String> nombreColumna;
	@FXML private PTableColumn<Cliente, String> registroContribuyentesColumna;
	@FXML private PTableColumn<Cliente, String> telefonoColumna;
	@FXML private PTableColumn<Cliente, String> correoColumna;
	@FXML private PTableColumn<Cliente, Double> saldoColumna;
	@FXML private PTableColumn<Cliente, String> accionesColumn;
	@FXML private TextField buscarCliente;

	//INICIALIZA COMPONENTES CONTROLAN INTERFAZ USUARIO
	@FXML private void initialize() {
		this.cliente = new Cliente();
		this.buscarCliente.setOnKeyPressed(new EventHandler<KeyEvent>() {
    		@Override
    		public void handle(KeyEvent event) {
    			if (event.getCode().equals(KeyCode.ENTER))
    				buscarButtonHandler();
    		}//FIN METODO
    	});//FIN SENTENCIA
		this.inicializaTabla();
	}//FIN METODO

	//ACTUALIZA LA TABLA DE ACUERDO AL CRITERIO DE BÚSQUEDA
	@SuppressWarnings("unchecked")
	@FXML private void buscarButtonHandler() {
    	if (Seguridad.verificarAcceso(this.mainApp.getConnection(), this.mainApp.getUsuario().getGrupoUsuarioFk(), "rClientes")) {
    		tablaCliente.setItems(null);
    		listaClientes.clear();
    		listaClientes = ClienteDAO.readCliente(this.mainApp.getConnection(), this.buscarCliente.getText());
    		tablaCliente.setItems(ClienteDAO.toObservableList(listaClientes));
    		this.tablaCliente.getSortOrder().addAll(this.codigoColumna);
    	} else {
    		Notificacion.dialogoAlerta(AlertType.WARNING, "Error", "No tienes permiso para realizar esta acción.");
    	}//FIN IF-ELSE

    }//FIN METODO

	//ACCESO CLASE PRINCIPAL CONTROLA VISTAS
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
		listaClientes = ClienteDAO.readCliente(this.mainApp.getConnection());
		this.actualizarTabla();
		//asignarVariables();
	}//FIN METODO

	//INICIALIZA LOS COMPONENTES DE LA TABLA DE CLIENTES
	private void inicializaTabla() {
    	codigoColumna.setCellValueFactory(cellData -> cellData.getValue().codigoProperty());
        nombreColumna.setCellValueFactory(cellData -> cellData.getValue().nombreProperty());
        registroContribuyentesColumna.setCellValueFactory(cellData -> cellData.getValue().registroContribuyenteProperty());
        telefonoColumna.setCellValueFactory(cellData -> cellData.getValue().telefonoProperty());
        correoColumna.setCellValueFactory(cellData -> cellData.getValue().correoProperty());

        accionesColumn.setCellValueFactory(new PropertyValueFactory<>("DUM"));
        Callback<TableColumn<Cliente, String>, TableCell<Cliente, String>> cellFactory =  param -> {

        	final TableCell<Cliente, String> cell = new TableCell<Cliente, String>() {
        		final Button botonVer = new Button("V");
        		final Button botonEliminar = new Button("B");
        		final Button botonEstadoCuenta = new Button("EC");
        		final Button botonCarpeta = new Button("C");
        		final Button botonArchivo = new Button("A");
        		final Button botonAgregarComprador = new Button("AC");
        		final HBox acciones = new HBox(botonVer, botonEliminar, botonEstadoCuenta, botonCarpeta, botonArchivo, botonAgregarComprador);


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

		        	botonEliminar.setGraphic(new ImageView(new Image(MainApp.class.getResourceAsStream("view/images/1x/RemoveIcon.png"))));
		        	botonEliminar.setPrefSize(16.0, 16.0);
		        	botonEliminar.setPadding(Insets.EMPTY);
		        	botonEliminar.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
		        	botonEliminar.setStyle("-fx-background-color: transparent;");
		        	botonEliminar.setCursor(Cursor.HAND);
		        	botonEliminar.setTooltip(new Tooltip("Eliminar cliente"));

		        	botonEstadoCuenta.setGraphic(new ImageView(new Image(MainApp.class.getResourceAsStream("view/images/1x/AccountIcon.png"))));
		        	botonEstadoCuenta.setPrefSize(16.0, 16.0);
		        	botonEstadoCuenta.setPadding(Insets.EMPTY);
		        	botonEstadoCuenta.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
		        	botonEstadoCuenta.setStyle("-fx-background-color: transparent;");
		        	botonEstadoCuenta.setCursor(Cursor.HAND);
		        	botonEstadoCuenta.setTooltip(new Tooltip("Estado de cuenta del cliente"));

		        	botonCarpeta.setGraphic(new ImageView(new Image(MainApp.class.getResourceAsStream("view/images/1x/FolderIcon.png"))));
		        	botonCarpeta.setPrefSize(16.0, 16.0);
		        	botonCarpeta.setPadding(Insets.EMPTY);
		        	botonCarpeta.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
		        	botonCarpeta.setStyle("-fx-background-color: transparent;");
		        	botonCarpeta.setCursor(Cursor.HAND);
		        	botonCarpeta.setTooltip(new Tooltip("Abrir carpeta de proyectos"));

		        	botonArchivo.setGraphic(new ImageView(new Image(MainApp.class.getResourceAsStream("view/images/1x/ProyectoIcono.png"))));
		        	botonArchivo.setPrefSize(16.0, 16.0);
		        	botonArchivo.setPadding(Insets.EMPTY);
		        	botonArchivo.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
		        	botonArchivo.setStyle("-fx-background-color: transparent;");
		        	botonArchivo.setCursor(Cursor.HAND);
		        	botonArchivo.setTooltip(new Tooltip("Archivos del cliente"));

		        	botonAgregarComprador.setGraphic(new ImageView(new Image(MainApp.class.getResourceAsStream("view/images/1x/CustomerIcon_1.png"))));
		        	botonAgregarComprador.setPrefSize(16.0, 16.0);
		        	botonAgregarComprador.setPadding(Insets.EMPTY);
		        	botonAgregarComprador.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
		        	botonAgregarComprador.setStyle("-fx-background-color: transparent;");
		        	botonAgregarComprador.setCursor(Cursor.HAND);
		        	botonAgregarComprador.setTooltip(new Tooltip("Agregar comprador"));

		        	acciones.setSpacing(3);
		        	acciones.setPrefWidth(80.0);
		        	acciones.setAlignment(Pos.CENTER_LEFT);
		        	super.updateItem(item, empty);


		        	if (empty) {
		        		setGraphic(null);
		                setText(null);
		            } else {

		            	//ABRE EL DIALOGO PARA VER LOS DATOS DEL CLIENTE
		            	botonVer.setOnAction(event -> {
		            		if(Seguridad.verificarAcceso(mainApp.getConnection(), mainApp.getUsuario().getGrupoUsuarioFk(), "rCliente")) {
		            			cliente = getTableView().getItems().get(getIndex());
		            			verCliente(cliente);
		            		}else
		            			Notificacion.dialogoAlerta(AlertType.WARNING, "Error", "No tienes permiso para realizar esta acción.");
		            	});//FIN LISTENER

		            	//ABRE EL DIALOGO PARA BORRAR EL CLIENTE
		            	botonEliminar.setOnAction(event -> {
		            		if(Seguridad.verificarAcceso(mainApp.getConnection(), mainApp.getUsuario().getGrupoUsuarioFk(), "dCliente")) {
			        			cliente = getTableView().getItems().get(getIndex());
			        			if (Notificacion.dialogoPreguntar("Confirmación para eliminar.", "¿Desea eliminar a " + cliente.getNombre() + "?")){
			        				eliminarCliente(cliente);
			        				actualizarTabla();
			        			}
		            		} else
		            			Notificacion.dialogoAlerta(AlertType.WARNING, "Error", "No tienes permiso para realizar esta acción.");
		                });//FIN LISTENER

		            	//ABRE EL DIALOGO PARA EDITAR LOS DATOS DEL CLIENTE
		            	botonEstadoCuenta.setOnAction(event -> {
		            		if(Seguridad.verificarAcceso(mainApp.getConnection(), mainApp.getUsuario().getGrupoUsuarioFk(), "rCliente")) {
		            			cliente = getTableView().getItems().get(getIndex());
//		            			mainApp.iniciarDialogoCotizacionCliente(cliente);
		            			mainApp.iniciarDialogoEstadoCuentaCliente(cliente);
		            			actualizarTabla();
			                } else
		            			Notificacion.dialogoAlerta(AlertType.WARNING, "Error", "No tienes permiso para realizar esta acción.");
		            	});//FIN LISTENER

		            	botonCarpeta.setOnAction(event -> {
		            		if(Seguridad.verificarAcceso(mainApp.getConnection(), mainApp.getUsuario().getGrupoUsuarioFk(), "rCliente")) {
		            			cliente = getTableView().getItems().get(getIndex());
		            			/*File ruta = new File(MainApp.RAIZ_SERVIDOR +"Clientes\\" + cliente.getNombre());
								ruta.mkdirs();*/
		            			try {
		            				Runtime.getRuntime().exec("explorer.exe /n, " + MainApp.RAIZ_SERVIDOR +"Clientes\\" + cliente.getNombre()+ "\\Proyectos");

								} catch (IOException e) {

									e.printStackTrace();
								}
			                } else
		            			Notificacion.dialogoAlerta(AlertType.WARNING, "Error", "No tienes permiso para realizar esta acción.");
		            	});//FIN LISTENER

		            	botonArchivo.setOnAction(event -> {
		            		if(Seguridad.verificarAcceso(mainApp.getConnection(), mainApp.getUsuario().getGrupoUsuarioFk(), "rCliente")) {
		            			cliente = getTableView().getItems().get(getIndex());
		            			mainApp.iniciarDialogoProyectos(cliente);
		            			actualizarTabla();
			                } else
		            			Notificacion.dialogoAlerta(AlertType.WARNING, "Error", "No tienes permiso para realizar esta acción.");
		            	});//FIN LISTENER

		            	botonAgregarComprador.setOnAction(event -> {
		            		cliente = getTableView().getItems().get(getIndex());
		            		mainApp.iniciarDialogoCompradores(cliente);
		            	});

		            	setGraphic(acciones);
		                setText(null);
		            }//FIN IF/ELSE
		        }//FIN METODO
		    };//FIN METODO
		    return cell;
		};//FIN METODO
		accionesColumn.setCellFactory(cellFactory);
    }//FIN METODO


	@FXML private void nuevoCliente() {
		Cliente cliente = new Cliente();
		this.mainApp.iniciarDialogoClientes(cliente, DialogoClientes.CREAR);
		this.actualizarTabla();
	}//FIN METODO

	//ACTUALIZA LA TABLA CON LOS ULTIMOS CAMBIOS EN LA BASE DE DATOS
	@SuppressWarnings("unchecked")
	@FXML private void actualizarTabla() {
		tablaCliente.setItems(null);
		listaClientes.clear();
		listaClientes = ClienteDAO.readCliente(this.mainApp.getConnection());
		tablaCliente.setItems(ClienteDAO.toObservableList(listaClientes));
	    buscarCliente.setText("");
	    this.tablaCliente.getSortOrder().addAll(this.codigoColumna);
	}//FIN METODO

	private void verCliente(Cliente cliente) {
		this.mainApp.iniciarDialogoClientes(cliente, DialogoClientes.MOSTRAR);
		this.actualizarTabla();
	}//FIN METODO

	private void eliminarCliente(Cliente cliente) {
		if (ClienteDAO.deleteCliente(mainApp.getConnection(), cliente)) {
			DomicilioDAO.deleteDomicilio(mainApp.getConnection(),cliente.getDomicilio(mainApp.getConnection()));
			File ruta = new File(MainApp.RAIZ_SERVIDOR +"Clientes\\" + cliente.getNombre());
    		ruta.delete();
		}//FIN IF
	}//FIN METODO

}//FIN CLASE