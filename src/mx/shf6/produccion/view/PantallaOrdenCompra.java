package mx.shf6.produccion.view;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.function.Predicate;



import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import mx.shf6.produccion.MainApp;
import mx.shf6.produccion.model.OrdenCompra;
import mx.shf6.produccion.model.dao.DetalleOrdenCompraDAO;
import mx.shf6.produccion.model.dao.OrdenCompraDAO;
import mx.shf6.produccion.utilities.LeerArchivo;
import mx.shf6.produccion.utilities.Notificacion;
import mx.shf6.produccion.utilities.PTableColumn;

public class PantallaOrdenCompra {

	//PROPIEDADES
	private MainApp mainApp;
	private Connection connection;
	private ArrayList<OrdenCompra> arrayListOrdenCompra;
	private File file;
	
	//VARIABLES
	private String RUTA = "resources/";
	
	//CONSTANTES
	
	//COMPONENTES DE LA INTERFAZ
	@FXML private TableView<OrdenCompra> tableViewOrdenCompra;
	@FXML private PTableColumn<OrdenCompra, String> tableColumnFolio;
	@FXML private PTableColumn<OrdenCompra, Date> tableColumnFechaPedido;
	@FXML private PTableColumn<OrdenCompra, String> tableColumnCliente;
	@FXML private PTableColumn<OrdenCompra, String> tableColumnComentarios;
	@FXML private PTableColumn<OrdenCompra, String> tableColumnAcciones;
	@FXML private TextField textFieldBusqueda;
	
	//METODOS
	@FXML private void initialize() {
		initTabla();
	}//FIN METODO
	
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
		this.connection = this.mainApp.getConnection();
		this.arrayListOrdenCompra = new ArrayList<OrdenCompra>();
		updateTablaOrdenCompra();
	}//FIN METODO
	
	private void initTabla() {
		this.tableColumnFolio.setCellValueFactory(cellData -> cellData.getValue().folioProperty());
		this.tableColumnFechaPedido.setCellValueFactory(cellData -> cellData.getValue().fechaPedidoProperty());
		this.tableColumnCliente.setCellValueFactory(cellData -> cellData.getValue().getClienteFK().nombreProperty());
		this.tableColumnComentarios.setCellValueFactory(cellData -> cellData.getValue().comentariosProperty());
		initColumnaAcciones();
	}//FIN METODO
	
	private void initColumnaAcciones() {
		this.tableColumnAcciones.setCellValueFactory(new PropertyValueFactory<>("DUM"));
		Callback<TableColumn<OrdenCompra, String>, TableCell<OrdenCompra, String>> cellFactory = param -> {
			final TableCell<OrdenCompra, String> cell = new TableCell<OrdenCompra, String>() {
				final Button botonVer = new Button("Ver");
				final Button botonEditar = new Button("Editar");
				final Button botonEliminar = new Button("Eliminar");
				final Button botonDetalles = new Button("Detalles");
				final Button botonEnviar = new Button("Enviar");
				final HBox cajaBotones = new HBox(botonVer, botonDetalles);
				
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
					
					botonDetalles.setGraphic(new ImageView(new Image(MainApp.class.getResourceAsStream("view/images/1x/DetalleIcono.png"))));
					botonDetalles.setPrefSize(16.0, 16.0);
					botonDetalles.setPadding(Insets.EMPTY);
					botonDetalles.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
					botonDetalles.setStyle("-fx-background-color: transparent");
					botonDetalles.setCursor(Cursor.HAND);
					botonDetalles.setTooltip(new Tooltip("Detalles orden compra"));
					
					botonEnviar.setGraphic(new ImageView(new Image(MainApp.class.getResourceAsStream("view/images/1x/mailSmall.png"))));
					botonEnviar.setPrefSize(16.0, 16.0);
					botonEnviar.setPadding(Insets.EMPTY);
					botonEnviar.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
					botonEnviar.setStyle("-fx-background-color: transparent");
					botonEnviar.setCursor(Cursor.HAND);
					botonEnviar.setTooltip(new Tooltip("Enviar orden compra"));
					
					super.updateItem(item, empty);
					if (empty) {
						super.setGraphic(null);
						super.setText(null);
					} else {
						//MANEJADOR DE BOTONES
						botonVer.setOnAction(event -> {
							manejadorBotonMostrar(getTableView().getItems().get(getIndex()));
						});//FIN MANEJADOR
						
						botonEditar.setOnAction(event -> {
							manejadorBotonEditar(getTableView().getItems().get(getIndex()));
						});//FIN MANEJADOR
						
						botonEliminar.setOnAction(event -> {
							manejadorBotonEliminar(getTableView().getItems().get(getIndex()));
						});//FIN MANEJADOR
						
						botonDetalles.setOnAction(event -> {
							manejadorBotonAgregarDetalle(getTableView().getItems().get(getIndex()));
						});//FIN MANEJADOR
						
						botonEnviar.setOnAction(event -> {
							manejadorEnviarCorreo(getTableView().getItems().get(getIndex()));
						});//FIN MANEJADOR
						
						cajaBotones.setSpacing(2);
						super.setGraphic(cajaBotones);
						super.setText(null);
					}//FIN IF/ELSE
				}//FIN METODO
			};//FIN INSTRUCCION
			return cell;
		};//FIN INSTRUCCION
		tableColumnAcciones.setCellFactory(cellFactory);
	}//FIN METODO
	
	private void updateTablaOrdenCompra() {
		this.arrayListOrdenCompra.clear();
		this.tableViewOrdenCompra.setItems(null);
		this.arrayListOrdenCompra = OrdenCompraDAO.read(connection);
		
		ObjectProperty<Predicate<OrdenCompra>> filtroBusqueda = new SimpleObjectProperty<>();
		filtroBusqueda.bind(Bindings.createObjectBinding(() -> ordenCompra -> textFieldBusqueda.getText().isEmpty() ||
				ordenCompra.getFolio().toLowerCase().contains(textFieldBusqueda.getText().toLowerCase()) ||
					ordenCompra.getPMP().toLowerCase().contains(textFieldBusqueda.getText().toLowerCase()) ||
						ordenCompra.getClienteFK().getNombre().toLowerCase().contains(textFieldBusqueda.getText().toLowerCase()),
				textFieldBusqueda.textProperty()));
		
		FilteredList<OrdenCompra> filteredOrdenCompra = new FilteredList<OrdenCompra>(FXCollections.observableArrayList(this.arrayListOrdenCompra));
		tableViewOrdenCompra.setItems(filteredOrdenCompra);
		filteredOrdenCompra.predicateProperty().bind(Bindings.createObjectBinding(() -> filtroBusqueda.get(), filtroBusqueda));
	}//FIN METODO
	
	private void exportToExcel(OrdenCompra ordenCompra) throws SQLException {
		ResultSet resultados = DetalleOrdenCompraDAO.readNombreColumnas(connection, ordenCompra.getSysPK());
		this.file = new File (this.RUTA + ordenCompra.getFolio() +".xls");
		int row = 0;
		//FORMATO FUENTE DEL CONTENIDO
		WritableFont fuente = new WritableFont( WritableFont.ARIAL, 8, WritableFont.NO_BOLD );
		WritableCellFormat formatoCelda = new WritableCellFormat(fuente);
		
		WritableFont fTitulo = new WritableFont( WritableFont.ARIAL, 9, WritableFont.BOLD );
		WritableCellFormat formatoCelda2 = new WritableCellFormat(fTitulo);
		
		//INTERFAZ PARA LA HOJA DE CALCULO
		WritableSheet hojaExcel =  null;
		WritableWorkbook libro = null;
		
		//CONFIGURACION PARA GENERAR LA HOJA DE CALCULO
		WorkbookSettings configuracion = new WorkbookSettings();
		configuracion.setLocale(new Locale("es", "MX"));
		
		try {
			libro = Workbook.createWorkbook(file, configuracion);
			//HOJA CON NOMBRE DE LA TABLA
			libro.createSheet("Consulta", 0);
			hojaExcel = libro.getSheet(0);
			
			ArrayList<String> arrayListColumnas = new ArrayList<String>();
			arrayListColumnas.add("Folio");
			arrayListColumnas.add("PTDA");
			arrayListColumnas.add("Diseño");
			arrayListColumnas.add("Descripción");
			arrayListColumnas.add("FechaEntrega");
			arrayListColumnas.add("Cantidad");
			arrayListColumnas.add("Tipo");
			arrayListColumnas.add("Proceso");		
				
			try {
				//NOMBRE DE LAS FILAS
				for (int i = 1; i <= resultados.getMetaData().getColumnCount(); i++) {
					Label registro2 = new Label (i, 0, arrayListColumnas.get(i-1), formatoCelda2);
					try {
						hojaExcel.addCell(registro2);
					}catch (WriteException ex) {
						Notificacion.dialogoException(ex);
					}//FIN TRY-CATCH
				}//FIN FOR	
			
				while (resultados.next()) {
					for (int i = 1; i <= resultados.getMetaData().getColumnCount(); i++) {
						Label registro = new Label (i, row + 1, resultados.getString(i), formatoCelda);
						try {
							hojaExcel.addCell(registro);
						}catch (WriteException ex) {
						Notificacion.dialogoException(ex);
						}//FIN TRY-CATCH
					}//FIN FOR
					row ++;
				}//FIN WHILE
				resultados.close();
			}catch (SQLException ex) {
				Notificacion.dialogoException(ex);
			}//FIN TRY/CATCH
		
			//GUARDANDO EN LA RUTA
			try {
				libro.write();
				libro.close();
				//Desktop.getDesktop().open(file);
			}catch (WriteException ex) {
				Notificacion.dialogoException(ex);
			}//FIN TRY-CATCH
		
		}catch (IOException ex) {
			Notificacion.dialogoAlerta(AlertType.WARNING, "", "EL archivo se encuentra actualmente abierto, ciérralo para poder exportar");
		}//FIN TRY-CATCH
	}//FIN METODO
	
	private void sendOrdenCompra(OrdenCompra ordenCompra) {
		try {
			this.exportToExcel(ordenCompra);
			LeerArchivo.leerUsuario();
			ProcessBuilder outlook = new ProcessBuilder();
			outlook.command(LeerArchivo.rutaOutlook,"/c","ipm.note", "/m", LeerArchivo.correos, "/a", this.RUTA + ordenCompra.getFolio() + ".xls");
			outlook.start();
		} catch (IOException | SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY/CATCH*/
	}//FIN METODO
	
	//MANEJADORES
	private void manejadorBotonMostrar(OrdenCompra ordenCompra) {
		this.mainApp.iniciarDialogoOrdenCompra(ordenCompra, DialogoOrdenCompra.VER);
	}//FIN METODO
	
	private void manejadorBotonEditar(OrdenCompra ordenCompra) {
		this.mainApp.iniciarDialogoOrdenCompra(ordenCompra, DialogoOrdenCompra.EDITAR);
		updateTablaOrdenCompra();
	}//FIN METODO
	
	private void manejadorBotonEliminar(OrdenCompra ordenCompra) {
		if (Notificacion.dialogoPreguntar("", "Estas a punto de eliminar el registro, ¿Deseas continuar?")) {
			OrdenCompraDAO.delete(connection, ordenCompra);
			updateTablaOrdenCompra();
		}//FIN IF
	}//FIN METODO
	
	private void manejadorEnviarCorreo(OrdenCompra ordenCompra) {
		this.sendOrdenCompra(ordenCompra);
	}//FIN METODO
	
	private void manejadorBotonAgregarDetalle(OrdenCompra ordenCompra) {
		this.mainApp.iniciarDialogoDetalleOrdenCompra(ordenCompra);
	}//FIN METODO
	
	@FXML private void manejadorBotonActualizar() {
		updateTablaOrdenCompra();
	}//FIN METODO
	
	@FXML private void manejadorBotonBuscar() {
		updateTablaOrdenCompra();
	}//FIN METODO
	
	@FXML private void manejadorBotonCrear() {
		this.mainApp.iniciarDialogoOrdenCompra(new OrdenCompra(), DialogoOrdenCompra.CREAR);
		updateTablaOrdenCompra();
	}//FIN METODO
}//FIN CLASE
