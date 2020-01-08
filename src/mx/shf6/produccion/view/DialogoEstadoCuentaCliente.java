package mx.shf6.produccion.view;


import java.sql.Connection;
import java.sql.Date;

import java.util.ArrayList;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import mx.shf6.produccion.MainApp;
import mx.shf6.produccion.model.Cliente;
import mx.shf6.produccion.model.DocumentosCuentasXCobrar;
import mx.shf6.produccion.model.dao.DocumentosCuentasXCobrarDAO;
import mx.shf6.produccion.utilities.PTableColumn;
public class DialogoEstadoCuentaCliente {

	//PROPIEDADES
	private MainApp mainApp;
	private Connection conexion;
	private Cliente cliente;
	private ArrayList<DocumentosCuentasXCobrar> listaDetalleCXC;
	private DocumentosCuentasXCobrar documentosCuentasXCobrar;

	//VARIABLES
	int documentosAMostrar = 3;

	//COMPONENTES INTERFAZ

	@FXML private TableView<DocumentosCuentasXCobrar> tablaDetalleDCXC;
	@FXML private PTableColumn<DocumentosCuentasXCobrar, String> columnaReferencia;
	@FXML private PTableColumn<DocumentosCuentasXCobrar, Date> columnaFecha;
	@FXML private PTableColumn<DocumentosCuentasXCobrar, Double> columnaImporte;
	@FXML private PTableColumn<DocumentosCuentasXCobrar, Double> columnaPagos;
	@FXML private PTableColumn<DocumentosCuentasXCobrar, Double> columnaSaldo;
	@FXML private PTableColumn<DocumentosCuentasXCobrar, String> columnaAcciones;
	@FXML private DatePicker selectorFechaInicio;
	@FXML private DatePicker selectorFechaFin;
	@FXML private TextField campoTextoBuscar;
	@FXML private TabPane tabPane;
	@FXML private Label etiquetaNombreCliente;
	@FXML private MenuItem itemAplicarPagos;

	//INICIA COMPONENTES INTERFAZ USUARIO
	@FXML private void initialize() {

	}//FIN METODO

	//ACCESO CLASE PRINCIPAL
	public void setMainApp(MainApp mainApp, Cliente cliente) {
		this.mainApp = mainApp;
		this.conexion = this.mainApp.getConnection();
		this.cliente = cliente;
		this.listaDetalleCXC = new ArrayList<DocumentosCuentasXCobrar>();
		this.documentosCuentasXCobrar = new DocumentosCuentasXCobrar();

		this.actualizarComponentes();
		this.inicializaTabla();
		this.actualizarTabla();
	}//FIN METODO

	private void actualizarComponentes(){
		this.etiquetaNombreCliente.setText(this.cliente.getNombre());
		this.campoTextoBuscar.setOnKeyPressed(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent event) {
				if (event.getCode().equals(KeyCode.ENTER)){}
					manejadorBotonActualizar();
	    	}//FIN METODO
	    });//FIN SENTENCIA

		tabPane.getSelectionModel().selectedItemProperty().addListener (new ChangeListener<Tab>() {
			@Override
			public void changed(ObservableValue<? extends Tab> observable, Tab oldValue, Tab newValue) {
				if(newValue.getText().equals("Pendientes")){
					documentosAMostrar = DocumentosCuentasXCobrar.PENDIENTES;
					itemAplicarPagos.setDisable(false);
					columnaPagos.setCellValueFactory(cellData -> cellData.getValue().pagosProperty());
				} else if(newValue.getText().equals("Movimientos")){
					documentosAMostrar = DocumentosCuentasXCobrar.MOVIMIENTOS;
					columnaPagos.setCellValueFactory(cellData -> cellData.getValue().haberProperty());
					itemAplicarPagos.setDisable(true);
				} else if(newValue.getText().equals("Saldados")){
					documentosAMostrar = DocumentosCuentasXCobrar.SALDADOS;
					itemAplicarPagos.setDisable(true);
					columnaPagos.setCellValueFactory(cellData -> cellData.getValue().pagosProperty());
				}//FIN ELSE IF
				actualizarTabla();
			}//FIN METODO
		});//FIN SENTENCIA

		this.selectorFechaInicio.setEditable(false);
		this.selectorFechaFin.setEditable(false);
		this.selectorFechaInicio.setValue(new Date(System.currentTimeMillis()).toLocalDate());
		this.selectorFechaFin.setValue(new Date(System.currentTimeMillis()).toLocalDate());

		this.selectorFechaInicio.setOnAction(e -> this.actualizarTabla());
		this.selectorFechaFin.setOnAction(e -> this.actualizarTabla());

	}//FIN METODO
	private void inicializaTabla() {
		this.columnaReferencia.setCellValueFactory(cellData -> cellData.getValue().referenciaProperty());
		this.columnaFecha.setCellValueFactory(cellData -> cellData.getValue().fechaProperty());
		this.columnaImporte.setCellValueFactory(cellData -> cellData.getValue().debeProperty());
		if(documentosAMostrar == DocumentosCuentasXCobrar.MOVIMIENTOS)
			this.columnaPagos.setCellValueFactory(cellData -> cellData.getValue().haberProperty());
		else
			this.columnaPagos.setCellValueFactory(cellData -> cellData.getValue().pagosProperty());
		this.columnaSaldo.setCellValueFactory(cellData -> cellData.getValue().saldoProperty());
	}//FIN METODO


	private void actualizarTabla() {
		this.tablaDetalleDCXC.setItems(null);
		this.listaDetalleCXC.clear();

		if(documentosAMostrar == DocumentosCuentasXCobrar.PENDIENTES)
			this.listaDetalleCXC = DocumentosCuentasXCobrarDAO.readPendientesPorClienteFK(conexion, this.cliente.getSysPK(), Date.valueOf(this.selectorFechaInicio.getValue()), Date.valueOf(this.selectorFechaFin.getValue()), this.campoTextoBuscar.getText());
		else if (documentosAMostrar == DocumentosCuentasXCobrar.MOVIMIENTOS)
			this.listaDetalleCXC = DocumentosCuentasXCobrarDAO.readMovimientosPorClienteFK(conexion, this.cliente.getSysPK(), Date.valueOf(this.selectorFechaInicio.getValue()), Date.valueOf(this.selectorFechaFin.getValue()), this.campoTextoBuscar.getText());
		else if  (documentosAMostrar == DocumentosCuentasXCobrar.SALDADOS)
			this.listaDetalleCXC = DocumentosCuentasXCobrarDAO.readSaldadosPorClienteFK(conexion, this.cliente.getSysPK(), Date.valueOf(this.selectorFechaInicio.getValue()), Date.valueOf(this.selectorFechaFin.getValue()), this.campoTextoBuscar.getText());

		this.tablaDetalleDCXC.setItems(FXCollections.observableArrayList(listaDetalleCXC));
	}//FIN METODO

	//MANEJADORES
	@FXML private void manejadorBotonNuevoRecibo() {
		this.mainApp.iniciarDialogoRecibo(this.cliente);
	}//FIN METODO

	@FXML private void manejadorBotonActualizar() {
		this.actualizarTabla();
	}//FIN METODO

	@FXML private void manejadorAplicarPagos() {
		this.documentosCuentasXCobrar = this.tablaDetalleDCXC.getSelectionModel().getSelectedItem();
		this.mainApp.iniciarDialogoAplicarPagos(this.documentosCuentasXCobrar);
		actualizarTabla();
	}//FIN METODO

	@FXML private void manejadorBotonCerrar() {
		this.mainApp.getEscenarioDialogos().close();
	}//FIN METODO
}//FIN CLASE
