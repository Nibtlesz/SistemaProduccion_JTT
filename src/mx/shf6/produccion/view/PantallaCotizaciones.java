package mx.shf6.produccion.view;

import java.sql.Date;
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
import mx.shf6.produccion.model.Cotizacion;
import mx.shf6.produccion.model.DocumentosCuentasXCobrar;
import mx.shf6.produccion.model.dao.CotizacionDAO;
import mx.shf6.produccion.model.dao.Seguridad;
import mx.shf6.produccion.utilities.GenerarDocumento;
import mx.shf6.produccion.utilities.Notificacion;
import mx.shf6.produccion.utilities.TransaccionSQL;
import mx.shf6.produccion.model.DetalleCotizacion;
import mx.shf6.produccion.model.dao.DetalleCotizacionDAO;
import mx.shf6.produccion.model.dao.DocumentosCuentasXCobrarDAO;

public class PantallaCotizaciones {

	//PROPIEDADES
	private MainApp mainApp;
	private Cotizacion cotizacion;
	private Cliente cliente;
	private ArrayList<Cotizacion> listaCotizaciones;

	//COMPONENTES INTERZAS USUARIO
	@FXML private TableView<Cotizacion> tablaCotizacion;
	@FXML private TableColumn<Cotizacion, String> clienteColumna;
	@FXML private TableColumn<Cotizacion, String> referenciaColumna;
	@FXML private TableColumn<Cotizacion, Date> fechaColumna;
	@FXML private TableColumn<Cotizacion, String> statusColumna;
	@FXML private TableColumn<Cotizacion, String> observacionesColumna;
	@FXML private TableColumn<Cotizacion, String> accionesColumn;
	@FXML private TextField buscarCotizacion;

	//INICIALIZA COMPONENTES CONTROLAN INTERFAZ USUARIO
	@FXML private void initialize() {
		this.cotizacion = new Cotizacion();
		this.buscarCotizacion.setOnKeyPressed(new EventHandler<KeyEvent>() {
    		@Override
    		public void handle(KeyEvent event) {
    			if (event.getCode().equals(KeyCode.ENTER))
    				buscarButtonHandler();
    		}//FIN METODO
    	});//FIN SENTENCIA
		this.inicializaTabla();
	}//FIN METODO

	//ACTUALIZA LA TABLA DE ACUERDO AL CRITERIO DE BÚSQUEDA
	@FXML private void buscarButtonHandler() {
    	if (Seguridad.verificarAcceso(this.mainApp.getConnection(), this.mainApp.getUsuario().getGrupoUsuarioFk(), "rClientes")) {
    		tablaCotizacion.setItems(null);
    		listaCotizaciones.clear();
    		if(cliente != null)
    			listaCotizaciones = CotizacionDAO.readCotizacionCliente(this.mainApp.getConnection(), cliente.getSysPK(), this.buscarCotizacion.getText());
    		else
    			listaCotizaciones = CotizacionDAO.readCotizacion(this.mainApp.getConnection(), this.buscarCotizacion.getText());
    		tablaCotizacion.setItems(CotizacionDAO.toObservableList(listaCotizaciones));
    	} else {
    		Notificacion.dialogoAlerta(AlertType.WARNING, "Error", "No tienes permiso para realizar esta acción.");
    	}//FIN IF-ELSE

    }//FIN METODO

	//ACCESO CLASE PRINCIPAL CONTROLA VISTAS
	public void setMainApp(MainApp mainApp, Cliente cliente) {
		this.mainApp = mainApp;
		this.cliente = cliente;
		if (cliente != null)
			listaCotizaciones = CotizacionDAO.readCotizacionCliente(this.mainApp.getConnection(), this.cliente.getSysPK());
		else
			listaCotizaciones = CotizacionDAO.readCotizacion(this.mainApp.getConnection());
		this.actualizarTabla();
	}//FIN METODO

	//INICIALIZA LOS COMPONENTES DE LA TABLA DE COTIZACIONES
	private void inicializaTabla() {
    	clienteColumna.setCellValueFactory(cellData -> cellData.getValue().getCliente(this.mainApp.getConnection()).nombreProperty());
        referenciaColumna.setCellValueFactory(cellData -> cellData.getValue().referenciaProperty());
        fechaColumna.setCellValueFactory(cellData -> cellData.getValue().fechaProperty());
        statusColumna.setCellValueFactory(cellData -> cellData.getValue().descripcionStatusProperty());
        observacionesColumna.setCellValueFactory(cellData -> cellData.getValue().observacionesProperty());

        accionesColumn.setCellValueFactory(new PropertyValueFactory<>("DUM"));
        Callback<TableColumn<Cotizacion, String>, TableCell<Cotizacion, String>> cellFactory =  param -> {

        	final TableCell<Cotizacion, String> cell = new TableCell<Cotizacion, String>() {
        		final Button botonVer = new Button("V");
        		final Button botonEditar = new Button("E");
        		final Button botonEliminar = new Button("B");
        		final Button botonAgregar = new Button("A");
        		final Button botonAprobar = new Button ("Aprobar");
        		final Button botonCancelar= new Button ("C");
        		final HBox acciones = new HBox(botonVer, botonEditar, botonEliminar, botonAgregar, botonAprobar, botonCancelar);

		        //PARA MOSTRAR LOS DIALOGOS DE INSTITUCION
		        @Override
		        public void updateItem(String item, boolean empty) {
		        	botonVer.setGraphic(new ImageView(new Image(MainApp.class.getResourceAsStream("view/images/1x/ViewIcon.png"))));
		        	botonVer.setPrefSize(18.0, 18.0);
		        	botonVer.setPadding(Insets.EMPTY);
		        	botonVer.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
		        	botonVer.setStyle("-fx-background-color: transparent;");
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

					botonAgregar.setGraphic(new ImageView(new Image(MainApp.class.getResourceAsStream("view/images/1x/DetalleIcono.png"))));
					botonAgregar.setPrefSize(16.0, 16.0);
					botonAgregar.setPadding(Insets.EMPTY);
					botonAgregar.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
					botonAgregar.setStyle("-fx-background-color: transparent");
					botonAgregar.setCursor(Cursor.HAND);
					botonAgregar.setTooltip(new Tooltip("Detalle Cotización"));

					botonAprobar.setGraphic(new ImageView(new Image(MainApp.class.getResourceAsStream("view/images/1x/AprobarIcono.png"))));
					botonAprobar.setPrefSize(16.0, 16.0);
					botonAprobar.setPadding(Insets.EMPTY);
					botonAprobar.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
					botonAprobar.setStyle("-fx-background-color: transparent");
					botonAprobar.setCursor(Cursor.HAND);
					botonAprobar.setTooltip(new Tooltip("Aprobar Cotización"));

					botonCancelar.setGraphic(new ImageView(new Image(MainApp.class.getResourceAsStream("view/images/1x/NoAprobarIcono.png"))));
					botonCancelar.setPrefSize(16.0, 16.0);
					botonCancelar.setPadding(Insets.EMPTY);
					botonCancelar.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
					botonCancelar.setStyle("-fx-background-color: transparent");
					botonCancelar.setCursor(Cursor.HAND);
					botonCancelar.setTooltip(new Tooltip("Cancelar Cotización"));

		        	acciones.setSpacing(2);
		        	acciones.setPrefWidth(80.0);
		        	acciones.setAlignment(Pos.CENTER_LEFT);
		        	super.updateItem(item, empty);


		        	if (empty) {
		        		setGraphic(null);
		                setText(null);

		            } else {

		            	//ABRE EL DIALOGO PARA VER LOS DATOS DE LA COTIZACION
		            	botonVer.setOnAction(event -> {
		            		if(Seguridad.verificarAcceso(mainApp.getConnection(), mainApp.getUsuario().getGrupoUsuarioFk(), "rCotizacion")) {
		            			cotizacion = getTableView().getItems().get(getIndex());
		            			mainApp.iniciarDialogoCotizacion(cotizacion, DialogoCotizacion.VER, cliente);
		            		}else
		            			Notificacion.dialogoAlerta(AlertType.WARNING, "Error", "No tienes permiso para realizar esta acción.");
		            	});//FIN LISTENER

		            	//ABRE EL DIALOGO PARA EDITAR LA COTIZACION.
		            	botonEditar.setOnAction(event -> {
		            		if(Seguridad.verificarAcceso(mainApp.getConnection(), mainApp.getUsuario().getGrupoUsuarioFk(), "uCotizacion")) {
		            			cotizacion = getTableView().getItems().get(getIndex());
		            			mainApp.iniciarDialogoCotizacion(cotizacion, DialogoCotizacion.EDITAR, cliente);
		            		}else
		            			Notificacion.dialogoAlerta(AlertType.WARNING, "Error", "No tienes permiso para realizar esta acción.");
		                });//FIN LISTENER

		            	//ABRE EL DIALOGO PARA BORRAR LA COTIZACION
		            	botonEliminar.setOnAction(event -> {
		            		if(Seguridad.verificarAcceso(mainApp.getConnection(), mainApp.getUsuario().getGrupoUsuarioFk(), "dCotizacion")) {
		            			cotizacion = getTableView().getItems().get(getIndex());
			            		if (Notificacion.dialogoPreguntar("Confirmación para eliminar", "¿Desea eliminar la cotizacion " + cotizacion.getReferencia() + "?")){
			            			CotizacionDAO.deleteCotizacion(mainApp.getConnection(), cotizacion);
			            			actualizarTabla();
			            		}//FIN IF
		            		} else
		            			Notificacion.dialogoAlerta(AlertType.WARNING, "Error", "No tienes permiso para realizar esta acción.");
		                });//FIN LISTENER

		            	//ABRE EL DIALOGO PARA ABRIR EL DETALLE DE COTIZACION
		            	botonAgregar.setOnAction(event -> {
		            		if(Seguridad.verificarAcceso(mainApp.getConnection(), mainApp.getUsuario().getGrupoUsuarioFk(), "uCotizacion")) {
		            			cotizacion = getTableView().getItems().get(getIndex());
		            			mainApp.iniciarPantallaDetalleCotizacion(cotizacion);
		            			actualizarTabla();
		            		} else
		            			Notificacion.dialogoAlerta(AlertType.WARNING, "Error", "No tienes permiso para realizar esta acción.");
		                });//FIN LISTENER

		            	//ACEPTA LA COTIZACION
		            	botonAprobar.setOnAction(event -> {
		            		if(Seguridad.verificarAcceso(mainApp.getConnection(), mainApp.getUsuario().getGrupoUsuarioFk(), "uCotizacion")) {
		            			cotizacion = getTableView().getItems().get(getIndex());
		            			if (Notificacion.dialogoPreguntar("Confirmación para aprobación", "¿Desea APROBAR la cotizacion " + cotizacion.getReferencia() + "?")){
			            			cotizacion.setStatus(Cotizacion.APROBADA);

			            			TransaccionSQL.setStatusTransaccion(mainApp.getConnection(), TransaccionSQL.AUTOCOMMIT_OFF);
			            			if(CotizacionDAO.updateCotizacion(mainApp.getConnection(), cotizacion)){
			            				GenerarDocumento.generaCotizacion(mainApp.getConnection(), cotizacion, mainApp.getUsuario());
			            				actualizarTabla();
			            				Double saldo = 0.0;

				            			ArrayList <DetalleCotizacion> listDetalleCotizacion = new ArrayList<DetalleCotizacion>();
				            			listDetalleCotizacion = DetalleCotizacionDAO.readCotizacionDetalle(mainApp.getConnection(), cotizacion.getSysPK());
				            			for(DetalleCotizacion detalleCotizacion : listDetalleCotizacion){
				            				saldo = saldo + (detalleCotizacion.getPrecio()  * detalleCotizacion.getCantidad());
				            			}//FIN FOR

				            			DocumentosCuentasXCobrar documentosCuentasXCobrar = new DocumentosCuentasXCobrar();
				            			documentosCuentasXCobrar.setDocumento(DocumentosCuentasXCobrar.COTIZACION);
				            			documentosCuentasXCobrar.setDebe(saldo);
				            			documentosCuentasXCobrar.setClienteFK(cotizacion.getClienteFK());
				            			documentosCuentasXCobrar.setCotizacionFK(cotizacion.getSysPK());
				            			documentosCuentasXCobrar.setReferencia(cotizacion.getReferencia());
				            			documentosCuentasXCobrar.setNotas("Cotización");

				            			if(DocumentosCuentasXCobrarDAO.create(mainApp.getConnection(), documentosCuentasXCobrar)){
				            				TransaccionSQL.setStatusTransaccion(mainApp.getConnection(), TransaccionSQL.COMMIT_TRANSACTION);
				            				Notificacion.dialogoAlerta(AlertType.INFORMATION, "", "Cotización Aprobada");
				            			}else
				            				TransaccionSQL.setStatusTransaccion(mainApp.getConnection(), TransaccionSQL.ROLLBACK_TRANSACTION);//FIN IF ELSE CREATE DCXC
			            			}else {
			            				TransaccionSQL.setStatusTransaccion(mainApp.getConnection(), TransaccionSQL.ROLLBACK_TRANSACTION);
			            				Notificacion.dialogoAlerta(AlertType.INFORMATION, "", "No se pudo Aprobar la Cotización");
			            			}//FIN IF UPDATE COTIZACION
			            		}//FIN IF NOTIFICACION
		            		} else
		            			Notificacion.dialogoAlerta(AlertType.WARNING, "Error", "No tienes permiso para realizar esta acción.");
		                });//FIN LISTENER

		            	//CANCELA LA COTIZACION
		            	botonCancelar.setOnAction(event -> {
		            		if(Seguridad.verificarAcceso(mainApp.getConnection(), mainApp.getUsuario().getGrupoUsuarioFk(), "uCotizacion")) {
		            			cotizacion = getTableView().getItems().get(getIndex());
		            			if (Notificacion.dialogoPreguntar("Confirmación para cancelación", "¿Desea CANCELAR la cotizacion " + cotizacion.getReferencia() + "?")) {
			            			cotizacion.setStatus(Cotizacion.CANCELADA);

			             			TransaccionSQL.setStatusTransaccion(mainApp.getConnection(), TransaccionSQL.AUTOCOMMIT_OFF);
			            			if(CotizacionDAO.updateCotizacion(mainApp.getConnection(), cotizacion)){
				            			actualizarTabla();

				            			DocumentosCuentasXCobrar documentosCuentasXCobrarActual = new DocumentosCuentasXCobrar();
				            			documentosCuentasXCobrarActual = DocumentosCuentasXCobrarDAO.readPorCotizacionFK(mainApp.getConnection(),  cotizacion.getSysPK());

				            			DocumentosCuentasXCobrar documentosCuentasXCobrar = new DocumentosCuentasXCobrar();
				            			documentosCuentasXCobrar.setReferencia(cotizacion.getReferencia());
				            			documentosCuentasXCobrar.setDocumento(DocumentosCuentasXCobrar.COTIZACION);
				            			documentosCuentasXCobrar.setHaber(documentosCuentasXCobrarActual.getDebe());
				            			documentosCuentasXCobrar.setXAplicar(documentosCuentasXCobrarActual.getPagos());
				            			documentosCuentasXCobrar.setNotas("Bonificación");
				            			documentosCuentasXCobrar.setClienteFK(cotizacion.getClienteFK());
				            			documentosCuentasXCobrar.setCotizacionFK(cotizacion.getSysPK());

				            			documentosCuentasXCobrarActual.setPagos(0.0);
				            			documentosCuentasXCobrarActual.setBonificaciones(documentosCuentasXCobrarActual.getDebe());

				            			if(DocumentosCuentasXCobrarDAO.create(mainApp.getConnection(), documentosCuentasXCobrar)){
				            				if(DocumentosCuentasXCobrarDAO.updateBonificaciones(mainApp.getConnection(), documentosCuentasXCobrarActual)){
				            					TransaccionSQL.setStatusTransaccion(mainApp.getConnection(), TransaccionSQL.COMMIT_TRANSACTION);
				            					Notificacion.dialogoAlerta(AlertType.INFORMATION, "", "Cotización Cancelada");
				            				}else
				            					TransaccionSQL.setStatusTransaccion(mainApp.getConnection(), TransaccionSQL.ROLLBACK_TRANSACTION);// UPDATE DCXC
				            			} else
				            				TransaccionSQL.setStatusTransaccion(mainApp.getConnection(), TransaccionSQL.ROLLBACK_TRANSACTION); //FIN IF CREATE DCXC
		            				} else {
		            					TransaccionSQL.setStatusTransaccion(mainApp.getConnection(), TransaccionSQL.ROLLBACK_TRANSACTION);
		            					Notificacion.dialogoAlerta(AlertType.INFORMATION, "", "No se pudo Cancelar la Cotización");
		            				}//FIN IF ELSE UPDATE COTIZACION
		            			}//FIN IF NOTIFICACION
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
		accionesColumn.setCellFactory(cellFactory);
    }//FIN METODO

	@FXML private void manejadorBotonNuevo() {
		this.cotizacion = new Cotizacion();
		this.mainApp.iniciarDialogoCotizacion(this.cotizacion, DialogoCotizacion.CREAR, cliente);
		this.actualizarTabla();
	}//FIN METODO

	@FXML private void manejadorBotonActualizar() {
		this.actualizarTabla();
	}//FIN METODO

	//ACTUALIZA LA TABLA CON LOS ULTIMOS CAMBIOS EN LA BASE DE DATOS
	private void actualizarTabla() {
		tablaCotizacion.setItems(null);
		listaCotizaciones.clear();
		if (cliente != null)
			listaCotizaciones = CotizacionDAO.readCotizacionCliente(this.mainApp.getConnection(), this.cliente.getSysPK());
		else
			listaCotizaciones = CotizacionDAO.readCotizacion(this.mainApp.getConnection());
		if (!listaCotizaciones.isEmpty()) {
			//this.asignarVariables();
			tablaCotizacion.setItems(CotizacionDAO.toObservableList(listaCotizaciones));
	    	buscarCotizacion.setText("");
		}//FIN IF


	}//FIN METODO

}//FIN CLASE