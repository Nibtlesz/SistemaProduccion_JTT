package mx.shf6.produccion.view;

import java.io.File;
import java.util.ArrayList;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import mx.shf6.produccion.MainApp;
import mx.shf6.produccion.model.Cliente;
//import mx.shf6.produccion.model.Componente;
import mx.shf6.produccion.model.Proyecto;
import mx.shf6.produccion.model.dao.ProyectoDAO;
import mx.shf6.produccion.utilities.Notificacion;
import mx.shf6.produccion.utilities.PTableColumn;

public class DialogoProyectos {
	//PROPIEDADES
	private MainApp mainApp;
	private Proyecto proyecto;
	private ArrayList<Proyecto> listaProyecto;
	private Cliente cliente;

	//VARIABLES

	//COMPONENTES INTERFAZ
	@FXML private Label etiquetaCliente;
	@FXML private TextField campoTextoBusqueda;
	@FXML private TableView<Proyecto> tablaProyecto;
	@FXML private PTableColumn<Proyecto, String> columnaCodigo;
	@FXML private PTableColumn<Proyecto, String> columnaDescripcion;
	@FXML private PTableColumn<Proyecto, String> columnaCarpeta;
	@FXML private PTableColumn<Proyecto, String> columnaEspecificacionTecnica;
	@FXML private PTableColumn<Proyecto, Double> columnaCostoDirecto;
	@FXML private PTableColumn<Proyecto, Double> columnaCostoIndirecto;
	@FXML private PTableColumn<Proyecto, Double> columnaPrecio;
	@FXML private PTableColumn<Proyecto, String> columnaAcciones;

	//INICIA COMPONENTES INTERFAZ USUARIO
	@FXML private void initialize() {
		this.proyecto = new Proyecto();
		this.inicializaComponentes();
		this.inicializaTabla();
	}//FIN METODO

	//ACCESO CLASE PRINCIPAL
	public void setMainApp(MainApp mainApp, Cliente cliente) {
		this.mainApp = mainApp;
		this.cliente = cliente;
		this.listaProyecto = ProyectoDAO.readProyectoCliente(this.mainApp.getConnection(),this.cliente.getSysPK());
		this.actualizarTabla();
		this.etiquetaCliente.setText(this.cliente.getNombre());
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
		this.columnaCarpeta.setCellValueFactory(cellData -> cellData.getValue().carpetaProperty());
		this.columnaEspecificacionTecnica.setCellValueFactory(cellData -> cellData.getValue().especificacionTecnicaProperty());
		this.columnaCostoDirecto.setCellValueFactory(cellData -> cellData.getValue().costoDirectoProperty());
		this.columnaCostoIndirecto.setCellValueFactory(cellData -> cellData.getValue().costoIndirectoProperty());
		this.columnaPrecio.setCellValueFactory(cellData -> cellData.getValue().precioProperty());
		this.inicializarColumnaAcciones();
	}//FIN METODO

	private void actualizarTabla() {
		this.tablaProyecto.setItems(null);
		this.listaProyecto.clear();
		this.listaProyecto = ProyectoDAO.readProyectoCliente(this.mainApp.getConnection(), this.cliente.getSysPK());
		this.tablaProyecto.setItems(ProyectoDAO.toObservableList(this.listaProyecto));
	}//FIN METODO

	@FXML private void buscarRegistroTabla() {
		this.tablaProyecto.setItems(null);
		this.listaProyecto.clear();
		this.listaProyecto = ProyectoDAO.readProyecto(this.mainApp.getConnection(), this.campoTextoBusqueda.getText(), this.cliente.getSysPK());

		this.tablaProyecto.setItems(ProyectoDAO.toObservableList(this.listaProyecto));
	}//FIN METODO

	private void inicializarColumnaAcciones() {
		this.columnaAcciones.setCellValueFactory(new PropertyValueFactory<>("DUM"));
		Callback<TableColumn<Proyecto, String>, TableCell<Proyecto, String>> cellFactory = param -> {
			final TableCell<Proyecto, String> cell = new TableCell<Proyecto, String>() {
				final Button botonVer = new Button("Ver");
				final Button botonEditar = new Button("Editar");
				final Button botonArchivo = new Button("Archivo");
				final Button botonEliminar = new Button("Eliminar");
				//final Button botonListaComponentes = new Button("Lista Componentes");
				final Button botonEstructuraNiveles = new Button("EstructuraNiveles");
				final Button botonPartesPrimarias = new Button("PartesPrimarias");
				final HBox cajaBotones = new HBox(botonVer, botonEditar,botonEliminar,botonArchivo);

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
					botonEliminar.setTooltip(new Tooltip("Eliminar registro"));

					botonArchivo.setGraphic(new ImageView(new Image(MainApp.class.getResourceAsStream("view/images/1x/DocumentIcon.png"))));
		        	botonArchivo.setPrefSize(16.0, 16.0);
		        	botonArchivo.setPadding(Insets.EMPTY);
		        	botonArchivo.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
		        	botonArchivo.setStyle("-fx-background-color: transparent;");
		        	botonArchivo.setCursor(Cursor.HAND);
		        	botonArchivo.setTooltip(new Tooltip("Documento registro"));

		        /*	botonListaComponentes.setGraphic(new ImageView(new Image(MainApp.class.getResourceAsStream("view/images/1x/DibujoIcono.png"))));
		        	botonListaComponentes.setPrefSize(16.0, 16.0);
		        	botonListaComponentes.setPadding(Insets.EMPTY);
		        	botonListaComponentes.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
		        	botonListaComponentes.setStyle("-fx-background-color: transparent;");
		        	botonListaComponentes.setCursor(Cursor.HAND);
		        	botonListaComponentes.setTooltip(new Tooltip("Lista componentes"));*/

		        	botonEstructuraNiveles.setGraphic(new ImageView(new Image(MainApp.class.getResourceAsStream("view/images/1x/ProyectoIcono.png"))));
		        	botonEstructuraNiveles.setPrefSize(16.0, 16.0);
		        	botonEstructuraNiveles.setPadding(Insets.EMPTY);
		        	botonEstructuraNiveles.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
		        	botonEstructuraNiveles.setStyle("-fx-background-color: transparent;");
		        	botonEstructuraNiveles.setCursor(Cursor.HAND);
		        	botonEstructuraNiveles.setTooltip(new Tooltip("Estructura Niveles"));

		        	botonPartesPrimarias.setGraphic(new ImageView(new Image(MainApp.class.getResourceAsStream("view/images/1x/ProyectoIcono.png"))));
		        	botonPartesPrimarias.setPrefSize(16.0, 16.0);
		        	botonPartesPrimarias.setPadding(Insets.EMPTY);
		        	botonPartesPrimarias.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
		        	botonPartesPrimarias.setStyle("-fx-background-color: transparent;");
		        	botonPartesPrimarias.setCursor(Cursor.HAND);
		        	botonPartesPrimarias.setTooltip(new Tooltip("Partes Primarias"));


					super.updateItem(item, empty);
					if (empty) {
						super.setGraphic(null);
						super.setText(null);
					} else {

						//MANEJADORES PARA LOS BOTONES
						botonVer.setOnAction(event -> {
							proyecto = getTableView().getItems().get(getIndex());
							manejadorBotonVer(proyecto);
						});//FIN MANEJADDOR

						botonEditar.setOnAction(event -> {
							proyecto = getTableView().getItems().get(getIndex());
							manejadorBotonEditar(proyecto);
						});//FIN MANEJADDOR

						botonArchivo.setOnAction(event -> {
							proyecto = getTableView().getItems().get(getIndex());
							mainApp.iniciarDialogoArchivos(proyecto , cliente);
						});//FIN MANEJADDOR

						botonEliminar.setOnAction(event -> {
							proyecto = getTableView().getItems().get(getIndex());
							manejadorBotonEliminar(proyecto);
						});//FIN MANEJADDOR

						/*botonListaComponentes.setOnAction(event -> {
							proyecto = getTableView().getItems().get(getIndex());
							manejadorBotonListaComponentes(proyecto);
						});//FIN MANEJADDOR*/

						botonEstructuraNiveles.setOnAction(event -> {
							proyecto = getTableView().getItems().get(getIndex());
							manejadorBotonEstructuraNiveles(proyecto);
						});//FIN MANEJADDOR

						botonPartesPrimarias.setOnAction(event -> {
							proyecto = getTableView().getItems().get(getIndex());
							//manejadorBotonPartesPrimarias(proyecto);
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

		this.mainApp.iniciarDialogoProyectosCliente(proyecto, DialogoProyectosCliente.CREAR, cliente);
		this.actualizarTabla();
	}//FIN METODO

	@FXML private void manejadorBotonActualizar() {
		this.actualizarTabla();
	}//FIN METODO

	private void manejadorBotonVer(Proyecto proyecto) {
		this.mainApp.iniciarDialogoProyectosCliente(proyecto, DialogoProyectosCliente.VER, cliente);
		this.actualizarTabla();
	}//FIN METODO

	private void manejadorBotonEditar(Proyecto proyecto) {
		this.mainApp.iniciarDialogoProyectosCliente(proyecto, DialogoProyectosCliente.EDITAR, cliente);
		this.actualizarTabla();
	}//FIN METODO

	private void manejadorBotonEliminar(Proyecto proyecto) {
		if (Notificacion.dialogoPreguntar("", "Estas a punto de eliminar el registro, ¿Deseas continuar?")) {
			File ruta = new File(MainApp.RAIZ_SERVIDOR + "Clientes\\" + this.cliente.getNombre() + "\\Proyectos\\" +this.proyecto.getCodigo());
			ruta.delete();
			ProyectoDAO.deleteProyecto(this.mainApp.getConnection(), proyecto);
		}
		this.actualizarTabla();
	}//FIN METODO

	/*private void manejadorBotonListaComponentes(Proyecto proyecto) {
		Notificacion.dialogoDetalleMensaje(Componente.mostrarInformacionEnsamble(this.mainApp.getConnection(), proyecto.getComponente(this.mainApp.getConnection()), 0, ""));
	}//FIN METODO*/

	private void manejadorBotonEstructuraNiveles(Proyecto proyecto) {
		this.mainApp.iniciarDialogoEstructuraNiveles(proyecto);
	}//FIN METODO

	/*private void manejadorBotonPartesPrimarias(Proyecto proyecto) {
		this.mainApp.iniciarDialogoPartesPrimarias(proyecto, new OrdenProduccion());
	}//FIN METODO*/

	@FXML private void manejadorBotonCerrar() {
		this.mainApp.getEscenarioDialogosAlternoSecundario().close();
	}//FIN METODO


}//FIN CLASE