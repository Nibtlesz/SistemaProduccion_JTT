package mx.shf6.produccion.view;

import java.sql.Connection;
import java.sql.Date;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import mx.shf6.produccion.MainApp;
import mx.shf6.produccion.model.DetalleEntregaOrdenCompra;
import mx.shf6.produccion.model.DetalleOrdenCompra;
import mx.shf6.produccion.model.dao.DetalleEntregaOrdenCompraDAO;
import mx.shf6.produccion.model.dao.DetalleOrdenCompraDAO;
import mx.shf6.produccion.utilities.Notificacion;
import mx.shf6.produccion.utilities.PTableColumn;

public class DialogoDetalleEntregaOrdenCompra {
	
	//PROPIEDADES
	private MainApp mainApp;
	private Connection connection;
	private DetalleOrdenCompra detalleOrdenCompra;
	private ArrayList<DetalleEntregaOrdenCompra> arrayListDetalleEntregaOrdenCompra;
	
	//VARIABLES
	private int saldo;
	
	//CONSTANTES
	
	//COMPONENTES DE LA INTERFAZ
	@FXML private TableView<DetalleEntregaOrdenCompra> tableViewDetalleEntregaOrdenCompra;
	@FXML private PTableColumn<DetalleEntregaOrdenCompra, String> tableColumnFactura;
	@FXML private PTableColumn<DetalleEntregaOrdenCompra, Integer> tableColumnCantidad;
	@FXML private PTableColumn<DetalleEntregaOrdenCompra, Date> tableColumnFecha;
	@FXML private TextField textFieldComponente;
	//METODOS
	@FXML private void initialize() {
		this.detalleOrdenCompra = new DetalleOrdenCompra();
		initTabla();
	}//FIN METODO
	
	public void setMainApp(MainApp mainApp, DetalleOrdenCompra detalleOrdenCompra) {
		this.mainApp = mainApp;
		this.connection = this.mainApp.getConnection();
		this.detalleOrdenCompra = detalleOrdenCompra;
		this.arrayListDetalleEntregaOrdenCompra = new ArrayList<DetalleEntregaOrdenCompra>();
		this.saldo = detalleOrdenCompra.getSaldo();
		updateTable();
		showDatos();
	}//FIN METODO
	
	private void initTabla() {
		this.tableColumnFactura.setCellValueFactory(cellData -> cellData.getValue().facturaProperty());
		this.tableColumnCantidad.setCellValueFactory(cellData -> cellData.getValue().cantidadProperty());
		this.tableColumnFecha.setCellValueFactory(cellData -> cellData.getValue().fechaProperty());
	}//FIN METODO
	
	private void updateTable() {
		this.arrayListDetalleEntregaOrdenCompra.clear();
		this.tableViewDetalleEntregaOrdenCompra.setItems(null);
		this.arrayListDetalleEntregaOrdenCompra = DetalleEntregaOrdenCompraDAO.readPorDetalleOrdenCompra(connection, detalleOrdenCompra);
		this.tableViewDetalleEntregaOrdenCompra.setItems(FXCollections.observableArrayList(this.arrayListDetalleEntregaOrdenCompra));
	}//FIN METODO
	
	private void showDatos() {
		this.textFieldComponente.setText(this.detalleOrdenCompra.getComponenteFK().getDescripcion());
	}//FIN METODO
	
	private void deleteRegistro() {
		DetalleEntregaOrdenCompra detalleEntregaOrdenCompra = this.tableViewDetalleEntregaOrdenCompra.getSelectionModel().getSelectedItem();
		if (detalleEntregaOrdenCompra != null) {
			if (Notificacion.dialogoPreguntar("", "¿Deseas eliminar el registro?")) {
				if (DetalleEntregaOrdenCompraDAO.delete(connection, detalleEntregaOrdenCompra)) {
					this.saldo = saldo + detalleEntregaOrdenCompra.getCantidad();
					this.detalleOrdenCompra.setSaldo(saldo);
					DetalleOrdenCompraDAO.update(connection, detalleOrdenCompra);
					Notificacion.dialogoAlerta(AlertType.INFORMATION, "", "Registro eliminado");
					updateTable();
				}else
					Notificacion.dialogoAlerta(AlertType.ERROR, "", "No se pudo eliminar el registro");
			}//FIN IF
		}else
			Notificacion.dialogoAlerta(AlertType.ERROR, "", "No has seleccionado un registro");	
	}//FIN METODO
	
	//MANEJADORES
	@FXML private void manejadorBotonAgregar() {
		this.mainApp.iniciarDialogoAgregarDetalleEntregaOrdenCompra(new DetalleEntregaOrdenCompra(), this.detalleOrdenCompra);
		updateTable();
	}//FIN METODO
	
	@FXML private void manejadorBotonQuitar() {
		deleteRegistro();
	}//FIN METODO
	
	@FXML private void manejadorBotonCerrar() {
		this.mainApp.getEscenarioDialogosAlterno().close();
	}//FIN METODO
}//FIN CLASE
