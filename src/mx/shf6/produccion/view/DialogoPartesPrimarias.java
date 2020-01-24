package mx.shf6.produccion.view;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import mx.shf6.produccion.MainApp;
import mx.shf6.produccion.model.Componente;
import mx.shf6.produccion.model.DetalleComponente;
import mx.shf6.produccion.model.DetalleHojaViajera;
import mx.shf6.produccion.model.DetalleProceso;
import mx.shf6.produccion.model.HojaViajera;
import mx.shf6.produccion.model.OrdenProduccion;
import mx.shf6.produccion.model.dao.ComponenteDAO;
import mx.shf6.produccion.model.dao.DetalleComponenteDAO;
import mx.shf6.produccion.model.dao.DetalleHojaViajeraDAO;
import mx.shf6.produccion.model.dao.DetalleProcesoDAO;
import mx.shf6.produccion.model.dao.HojaViajeraDAO;
import mx.shf6.produccion.model.dao.OrdenProduccionDAO;
import mx.shf6.produccion.model.dao.ProcesoDAO;
import mx.shf6.produccion.utilities.GenerarDocumento;
import mx.shf6.produccion.utilities.Notificacion;
import mx.shf6.produccion.utilities.PTableColumn;
import mx.shf6.produccion.utilities.TransaccionSQL;

public class DialogoPartesPrimarias {

	//PROPIEDADES
	private MainApp mainApp;
	private Connection conexion;
	private OrdenProduccion ordenProduccion;
	private Componente componenteRaiz;
	private DetalleComponente componenteHojaViajera;
	private DetalleComponente detalleComponenteRaiz;
	private ArrayList<DetalleComponente> listaPartePrimaria;

	//VARIABLES
	Integer cantidad = 0;
	int i = 0;
	int tamañoArrayPartesPrimarias = 0;
	String nombreNumeroComponente;

	//CONSTANTES

	//COMPONENTES INTERFAZ
	@FXML private TableView<DetalleComponente> tablaPartesPrimarias;
	@FXML private PTableColumn<DetalleComponente, String> columnaNumeroPartePrimaria;
	@FXML private PTableColumn<DetalleComponente, String> columnaDescripcionPartePrimaria;
	@FXML private PTableColumn<DetalleComponente, String> columnaDescripcionMateriaPrima;
	@FXML private PTableColumn<DetalleComponente, String> columnaNumeroMateriaPrima;
	@FXML private PTableColumn<DetalleComponente, String> columnaAcciones;

	@FXML private Label campoTextoComponente;


	//INICIA COMPONENTES INTERFAZ USUARIO
	@FXML private void initialize() {
		inicializarTabla();
	}// FIN METODO

	//ACCESO CLASE PRINCIPAL
	public void setMainApp(MainApp mainApp, OrdenProduccion ordenProduccion) {
		this.mainApp = mainApp;
		this.conexion = this.mainApp.getConnection();
		this.ordenProduccion = ordenProduccion;
		this.listaPartePrimaria = new ArrayList<DetalleComponente>();
		this.componenteRaiz = new Componente();

		obtenerPartesPrimarias(this.ordenProduccion.getComponenteFK());
		actualizarTabla();

	}//FIN METODO

	private void inicializarTabla(){
		this.columnaNumeroPartePrimaria.setCellValueFactory(cellData -> cellData.getValue().getCodigo().numeroParteProperty());
		this.columnaDescripcionPartePrimaria.setCellValueFactory(cellData -> cellData.getValue().getCodigo().descripcionProperty());
		this.columnaDescripcionMateriaPrima.setCellValueFactory(cellData -> cellData.getValue().getMaterialFK().descripcionProperty());
		this.columnaNumeroMateriaPrima.setCellValueFactory(cellData -> cellData.getValue().getMaterialFK().codigoProperty());
		iniciarColumnaAcciones();
	}//FIN METODO

	private void iniciarColumnaAcciones() {

		this.columnaAcciones.setCellValueFactory(new PropertyValueFactory<>("DUM"));
		Callback<TableColumn<DetalleComponente, String>, TableCell<DetalleComponente, String>> cellFactory = param -> {

			final TableCell<DetalleComponente, String> cell = new TableCell<DetalleComponente, String>() {
				final Button botonHojaViajera = new Button("HojaViajera");
				final Button botonProceso = new Button("HojaProceso");
				final Button botonDibujo = new Button("HojaProceso");
				final HBox acciones = new HBox(botonHojaViajera);

				//PARA MOSTRAR LOS DIALOGOS
				@Override
				public void updateItem(String item, boolean empty) {
					botonHojaViajera.setGraphic(new ImageView(new Image(MainApp.class.getResourceAsStream("view/images/1x/HojaRutaIcono.png"))));
					botonHojaViajera.setPrefSize(16.0, 16.0);
					botonHojaViajera.setPadding(Insets.EMPTY);
					botonHojaViajera.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
					botonHojaViajera.setStyle("-fx-background-color: transparent;");
					botonHojaViajera.setCursor(Cursor.HAND);
					botonHojaViajera.setTooltip(new Tooltip("Hoja Viajera"));

		        	botonProceso.setGraphic(new ImageView(new Image(MainApp.class.getResourceAsStream("view/images/1x/DocumentIcon.png"))));
		        	botonProceso.setPrefSize(16.0, 16.0);
		        	botonProceso.setPadding(Insets.EMPTY);
		        	botonProceso.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
		        	botonProceso.setStyle("-fx-background-color: transparent;");
		        	botonProceso.setCursor(Cursor.HAND);
		        	botonProceso.setTooltip(new Tooltip("Hoja de Proceso"));

					botonDibujo.setGraphic(new ImageView(new Image(MainApp.class.getResourceAsStream("view/images/1x/DibujoIcono.png"))));
					botonDibujo.setPrefSize(16.0, 16.0);
					botonDibujo.setPadding(Insets.EMPTY);
					botonDibujo.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
					botonDibujo.setStyle("-fx-background-color: transparent");
					botonDibujo.setCursor(Cursor.HAND);
					botonDibujo.setTooltip(new Tooltip("Ver dibujo"));

					acciones.setSpacing(3);
		        	acciones.setPrefWidth(80.0);
		        	acciones.setAlignment(Pos.CENTER_LEFT);
		        	super.updateItem(item, empty);

		        	if (empty) {
		        		super.setGraphic(null);
		                super.setText(null);
		        	} else {

		        		botonHojaViajera.setOnAction(event -> {
		        			componenteHojaViajera = getTableView().getItems().get(getIndex());
		        			manejadorVerHojaViajera(componenteHojaViajera);
		        		});

		            	botonProceso.setOnAction(event -> {
		            		componenteHojaViajera = getTableView().getItems().get(getIndex());
		            		//GenerarDocumento.generarHojaProceso(mainApp.getConnection(), ProcesoDAO.readProcesoComponenteFK(conexion, ComponenteDAO.readComponenteNumeroParte(conexion, componenteHojaViajera.getNumeroParteComponenteSuperior()).getSysPK()), componenteRaiz.getSysPK());
		            	});

						botonDibujo.setOnAction(event -> {
							componenteHojaViajera = getTableView().getItems().get(getIndex());
							//manejadorBotonDibujo(ComponenteDAO.readComponenteNumeroParte(conexion, componenteHojaViajera.getNumeroParteComponenteSuperior()));
						});//FIN MANEJADDOR

		        		setGraphic(acciones);
		        		setText(null);
		        	}//FIN IF ELSE
				}//FIN METODO
			};//FIN METODO
			return cell;
		};//FIN METODO
		columnaAcciones.setCellFactory(cellFactory);
	}//FIN METODO
	
	private void obtenerPartesPrimarias(int componenteFK){
		componenteRaiz = ComponenteDAO.readComponente(conexion, componenteFK);
		this.nombreNumeroComponente = componenteRaiz.getNumeroParte();
		this.campoTextoComponente.setText(this.nombreNumeroComponente + " x " + this.ordenProduccion.getCantidad());

		ArrayList<DetalleComponente> listaDetalleComponente = new ArrayList<DetalleComponente>();
		listaDetalleComponente = DetalleComponenteDAO.readDetalleComponenteSuperiorFK(conexion, componenteFK);
		for(DetalleComponente detalleComponente : listaDetalleComponente){
			detalleComponente.setCantidad(this.ordenProduccion.getCantidad());
			listaPartePrimaria.add(detalleComponente);
			cantidad = detalleComponente.getCantidad();
			detalleComponenteRaiz = new DetalleComponente();
			detalleComponenteRaiz.setCodigo(detalleComponente.getCodigo());
			detalleComponenteRaiz.setMaterialFK(detalleComponente.getMaterialFK());
			detalleComponenteRaiz.setCantidad(this.ordenProduccion.getCantidad());
		}//FIN FOR
	}//FIN METODO

	private void actualizarTabla(){
		this.tablaPartesPrimarias.setItems(null);
		this.tablaPartesPrimarias.setItems(FXCollections.observableArrayList(this.listaPartePrimaria));
	}//FIN METODO

	private boolean accionBotonHojaViajera(DetalleComponente componenteHojaViajera) {
		HojaViajera hojaViajera = HojaViajeraDAO.readHojaViajeraPorOrdenProduccionComponente(this.conexion, this.ordenProduccion.getSysPK(), ComponenteDAO.readComponenteNumeroParte(this.conexion, componenteHojaViajera.getCodigo().getNumeroParte()).getSysPK());
		ArrayList<DetalleProceso> listaDetallesProceso = DetalleProcesoDAO.readDetalleProcesoFK(this.conexion, ProcesoDAO.readProcesoComponenteFK(this.conexion, ComponenteDAO.readComponenteNumeroParte(this.conexion, componenteHojaViajera.getCodigo().getNumeroParte()).getSysPK()));
		//Componente componente = ComponenteDAO.readComponente(this.conexion, hojaViajera.getComponenteFK());
		
		if (hojaViajera.getSysPK() == 0) {
			hojaViajera.setCantidad(this.ordenProduccion.getCantidad());
			hojaViajera.setCodigoParoFK(1);
			hojaViajera.setComponenteFK(ComponenteDAO.readComponenteNumeroParte(this.conexion, componenteHojaViajera.getCodigo().getNumeroParte()).getSysPK());
			hojaViajera.setNumeroLote(this.ordenProduccion.getLote());
			hojaViajera.setOrdenProduccionFK(this.ordenProduccion.getSysPK());
			hojaViajera.setStatus(HojaViajera.EN_PROCESO);
			TransaccionSQL.setStatusTransaccion(this.conexion, TransaccionSQL.AUTOCOMMIT_OFF);
			if (HojaViajeraDAO.createControlOperaciones(this.conexion, hojaViajera)) {
				hojaViajera = HojaViajeraDAO.readHojaViajeraPorOrdenProduccionComponente(this.conexion, this.ordenProduccion.getSysPK(), ComponenteDAO.readComponenteNumeroParte(this.conexion, componenteHojaViajera.getCodigo().getNumeroParte()).getSysPK());
				listaDetallesProceso = DetalleProcesoDAO.readDetalleProcesoFK(this.conexion, ProcesoDAO.readProcesoComponenteFK(this.conexion, ComponenteDAO.readComponenteNumeroParte(this.conexion, componenteHojaViajera.getCodigo().getNumeroParte()).getSysPK()));
				for (DetalleProceso detalleProceso : listaDetallesProceso) {
					DetalleHojaViajera detalleHojaViajera = new DetalleHojaViajera();
					detalleHojaViajera.setDetalleProcesoOperacion(detalleProceso.getOperacion());
					detalleHojaViajera.setDetalleProcesoDescripcion(detalleProceso.getDescripcion());
					detalleHojaViajera.setDetalleProcesoFK(detalleProceso.getSysPK());
					detalleHojaViajera.setHojaViajeraFK(hojaViajera.getSysPK());
					if (detalleProceso.getOperacion() == 1)
						detalleHojaViajera.setCantidadEnProceso(hojaViajera.getCantidad());
					else
						detalleHojaViajera.setCantidadEnProceso(0);
					detalleHojaViajera.setCantidadTerminado(0);
					if (detalleProceso.getOperacion() == 1)
						detalleHojaViajera.setFechaHoraInicio(new Timestamp(System.currentTimeMillis()));
					else
						detalleHojaViajera.setFechaHoraInicio(null);
					detalleHojaViajera.setFechaHoraFinal(null);
					if (!DetalleHojaViajeraDAO.createDetalleHojaViajera(this.conexion, detalleHojaViajera)) {
						TransaccionSQL.setStatusTransaccion(this.conexion, TransaccionSQL.ROLLBACK_TRANSACTION);
						return false;
					}//FIN IF
				}//FIN FOR
				TransaccionSQL.setStatusTransaccion(this.conexion, TransaccionSQL.COMMIT_TRANSACTION);
				Notificacion.dialogoAlerta(AlertType.CONFIRMATION, "", "La hoja viajera se genero de forma correcta");
				//printHojaViajera(hojaViajera, listaDetallesProceso);
				
					this.mainApp.iniciarDialogoDetalleHojaViajera(hojaViajera, listaDetallesProceso);
				return true;
			} else {
				Notificacion.dialogoAlerta(AlertType.INFORMATION, "", "La hoja viajera no se pudo generar");
				TransaccionSQL.setStatusTransaccion(this.conexion, TransaccionSQL.ROLLBACK_TRANSACTION);
				return false;
			}//FIN IF/ELSE
		} else {
			//printHojaViajera(hojaViajera, listaDetallesProceso);
				this.mainApp.iniciarDialogoDetalleHojaViajera(hojaViajera, listaDetallesProceso);
			return true;
		}
	}//FIN METODO

	private void updateStatusOrdenProduccion() {
		int enProduccion = 0;
		for (DetalleHojaViajera detalle :DetalleHojaViajeraDAO.readOrdenProduccion(conexion, this.ordenProduccion.getSysPK())) {
			if(detalle.getCantidadEnProceso() != 0)
				enProduccion = enProduccion + 1;
		}//FIN FOR
		
		if(enProduccion != 0) {
			this.ordenProduccion.setStatus(1);
			OrdenProduccionDAO.update(conexion, ordenProduccion);
		} else {
			this.ordenProduccion.setStatus(3);
			OrdenProduccionDAO.update(conexion, ordenProduccion);
		}//FIN IF/ELSE	
	}//FIN METODO
	
	//MANEJADORES COMPONENTES
	private void manejadorVerHojaViajera(DetalleComponente componenteHojaViajera) {
		accionBotonHojaViajera(componenteHojaViajera);
	}//FIN METODO

	@FXML private void manejadorBotonAceptar() {
	}//FIN METODO

	@FXML private void manejadorBotonCerrar() {
		updateStatusOrdenProduccion();
		this.mainApp.getEscenarioDialogos().close();
	}//FIN METODO

	@FXML private void manejadorBotonImprimir() {
		GenerarDocumento.generaListaMateriales(conexion, listaPartePrimaria, this.nombreNumeroComponente);
	}//FIN METODO

	/*private void manejadorBotonDibujo(Componente componente) {
		String rutaArchivoDibujo = MainApp.RAIZ_SERVIDOR + "Dibujos\\" +  componente.getCliente(this.mainApp.getConnection()).getNombre() + "\\" + componente.getNumeroParte() + ".pdf";
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
				File rutaCarpetaDibujo = new File(MainApp.RAIZ_SERVIDOR + "Dibujos\\" +  componente.getCliente(this.mainApp.getConnection()).getNombre());
				rutaCarpetaDibujo.mkdirs();
				if (GestorArchivos.cargarArchivo(archivoCliente, rutaArchivoDibujo))
					Notificacion.dialogoAlerta(AlertType.INFORMATION, "", "El archivo se ha guardado de forma correcta");
				else
					Notificacion.dialogoAlerta(AlertType.INFORMATION, "", "El archivo no se pudo cargar al sistema");
			}//FIN IF ELSE
		}//FIN IF/ELSE
	}//FIN METODO*/

}//FIN CLASE
