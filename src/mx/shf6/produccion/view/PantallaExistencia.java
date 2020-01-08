package mx.shf6.produccion.view;

import java.sql.Connection;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import mx.shf6.produccion.MainApp;
import mx.shf6.produccion.model.Existencia;
import mx.shf6.produccion.model.dao.AlmacenDAO;
import mx.shf6.produccion.model.dao.ExistenciaDAO;
import mx.shf6.produccion.utilities.PTableColumn;

public class PantallaExistencia {

	// PROPIEDADES
	private MainApp mainApp;
	private Connection conexion;
	private ArrayList<Existencia> listaExistencias;
	private ObservableList<String> observableListAlmacen;

	// VARIABLES
	private String almacenDescripcion;

	// CONSTANTES

	// COMPONENTES INTERFAZ
	@FXML private TableView<Existencia> tablaExistencias;
	@FXML private PTableColumn<Existencia, String> columnaNumeroParte;
	@FXML private PTableColumn<Existencia, String> columnaComponente;
	@FXML private PTableColumn<Existencia, String> columnaAlmacen;
	@FXML private PTableColumn<Existencia, Double> columnaCantidad;
	@FXML private TextField campoTextoBuscar;
	@FXML private ComboBox<String> comboBoxAlmacen;

	// METODOS
	@FXML
	private void initialize() {
		this.actualizarComponentes();
		this.inicializarTabla();
	}// FIN METODO

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
		this.conexion = this.mainApp.getConnection();
		this.listaExistencias = new ArrayList<Existencia>();
		this.observableListAlmacen = FXCollections.observableArrayList();
		this.almacenDescripcion = "";

		this.observableListAlmacen = AlmacenDAO.readDescripcion(conexion);
		this.observableListAlmacen.add("Todos");
		comboBoxAlmacen.setItems(observableListAlmacen);
		comboBoxAlmacen.setValue("Todos");
		this.comboBoxAlmacen.valueProperty().addListener((ov, oldvalue, newvalue) -> {
			buscarAlmacen();
		});
		this.actualizarTabla();

	}// FIN METODO

	private void actualizarComponentes() {
		this.campoTextoBuscar.setOnKeyPressed(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent event) {
				if (event.getCode().equals(KeyCode.ENTER))
					manejadorBotonBuscar();
			}// FIN METODO
		});// FIN SENTENCIA
	}// FIN METODO

	private void inicializarTabla() {
		this.columnaNumeroParte.setCellValueFactory(cellData->cellData.getValue().numeroComponenteProperty());
		this.columnaComponente.setCellValueFactory(cellData->cellData.getValue().descripcionComponenteProperty());
		this.columnaAlmacen.setCellValueFactory(cellData->cellData.getValue().descripcionAlmacenProperty());
		this.columnaCantidad.setCellValueFactory(cellData->cellData.getValue().existenciaProperty());
	}// FIN METODO

	private void actualizarTabla() {
		this.tablaExistencias.setItems(null);
		this.listaExistencias.clear();
		this.listaExistencias = ExistenciaDAO.readTodosPorAlmacen(conexion, this.almacenDescripcion);
		this.tablaExistencias.setItems(ExistenciaDAO.toObservableList(listaExistencias));
	}// FIN METODO

	private void buscarAlmacen() {
		if( comboBoxAlmacen.getValue().equals("Todos"))
			almacenDescripcion = "";
		else
			this.almacenDescripcion = comboBoxAlmacen.getValue();
		this.tablaExistencias.setItems(null);
		this.listaExistencias.clear();
		this.listaExistencias = ExistenciaDAO.readTodosPorComponenteAlmacenParecido(conexion, this.campoTextoBuscar.getText(), this.almacenDescripcion);
		this.tablaExistencias.setItems(ExistenciaDAO.toObservableList(listaExistencias));
	}// FIN METODO

	@FXML
	private void manejadorBotonActualizar() {
		this.comboBoxAlmacen.setValue("Todos");
		this.almacenDescripcion = "";
		actualizarTabla();
	}// FIN METODO

	@FXML
	private void manejadorBotonBuscar() {
		this.buscarAlmacen();
	}// FIN METODO

	@FXML
	private void manejadorBotonCerrar() {
		this.mainApp.getEscenarioDialogos().close();
	}// FIN METODO

	@FXML private void manejadorBotonAlmacenes() {
		this.mainApp.iniciarPantallaAlmacen();
	}//FIN METODO

	@FXML private void manejadorBotonEntradas() {
		this.mainApp.iniciarDialogoMovimientoInventario(DialogoMovimientoInventario.ENTRADA);
		actualizarTabla();
	}//FIN METODO

	@FXML private void manejadorBotonSalidas() {
		this.mainApp.iniciarDialogoMovimientoInventario(DialogoMovimientoInventario.SALIDA);
		actualizarTabla();
	}//FIN METODO

	@FXML private void manejadorBotonTraspasos() {
		this.mainApp.iniciarDialogoMovimientoInventario(DialogoMovimientoInventario.TRASPASO);
		actualizarTabla();
	}//FIN METODO

	@FXML private void manejadorBotonExistencias() {
		this.mainApp.iniciarPantallaExistencia();
		actualizarTabla();
	}//FIN METODO

}
