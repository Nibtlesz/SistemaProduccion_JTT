package mx.shf6.produccion.view;

import java.sql.Connection;
import java.sql.Date;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import mx.shf6.produccion.MainApp;
import mx.shf6.produccion.model.Cliente;
import mx.shf6.produccion.model.OrdenCompra;
import mx.shf6.produccion.model.dao.ClienteDAO;
import mx.shf6.produccion.model.dao.OrdenCompraDAO;
import mx.shf6.produccion.utilities.Notificacion;
import mx.shf6.produccion.utilities.RestriccionTextField;

public class DialogoOrdenCompra {
	
	//PROPIEDADES
	private MainApp mainApp;
	private Connection connection;
	private OrdenCompra ordenCompra;
	
	//VARIABLES
	private int opcion;
	
	//CONSTANTES
	public static final int CREAR = 0;
	public static final int VER = 1;
	public static final int EDITAR = 2;
	
	//COMPONENTES DE LA INTERFAZ
	@FXML private TextField textFieldFolio;
	@FXML private DatePicker datePickerFechaPedido;
	@FXML private ComboBox<Cliente> comboBoxClientes;
	@FXML private TextField textFieldPMP;
	@FXML private TextArea textAreaComentarios;
	
	//METODOS
	@FXML private void initialize() {
		this.ordenCompra = new OrdenCompra();
	}//FIN METODO
	
	public void setMainApp(MainApp mainApp, OrdenCompra ordenCompra, int opcion) {
		this.mainApp = mainApp;
		this.connection = this.mainApp.getConnection();
		this.ordenCompra = ordenCompra;
		this.opcion = opcion;
		initComponentes();
		showInterfaz();
	}//FIN METODO
	
	private void initComponentes() {
		RestriccionTextField.limitarNumeroCaracteres(textFieldFolio, 32);
		RestriccionTextField.limitarNumeroCaracteres(textFieldPMP, 8);
		this.comboBoxClientes.setItems(FXCollections.observableArrayList(ClienteDAO.readCliente(connection)));
	}//FIN METODO
	
	private void showInterfaz() {
		if (this.opcion == VER) {
			this.textFieldFolio.setText(this.ordenCompra.getFolio());
			this.textFieldFolio.setDisable(true);
			this.datePickerFechaPedido.setValue(this.ordenCompra.getFechaPedido().toLocalDate());
			this.datePickerFechaPedido.setDisable(true);
			this.comboBoxClientes.getSelectionModel().select(this.ordenCompra.getClienteFK());
			this.comboBoxClientes.setDisable(true);
			this.textFieldPMP.setText(this.ordenCompra.getPMP());
			this.textFieldPMP.setDisable(true);
			this.textAreaComentarios.setText(this.ordenCompra.getComentarios());
			this.textAreaComentarios.setDisable(true);
		} else if (this.opcion == EDITAR) {
			this.textFieldFolio.setText(this.ordenCompra.getFolio());
			this.textFieldFolio.setDisable(false);
			this.datePickerFechaPedido.setValue(this.ordenCompra.getFechaPedido().toLocalDate());
			this.datePickerFechaPedido.setDisable(false);
			this.comboBoxClientes.getSelectionModel().select(this.ordenCompra.getClienteFK());
			this.comboBoxClientes.setDisable(false);
			this.textFieldPMP.setText(this.ordenCompra.getPMP());
			this.textFieldPMP.setDisable(false);
			this.textAreaComentarios.setText(this.ordenCompra.getComentarios());
			this.textAreaComentarios.setDisable(false);
		}//FIN IF/ELSE			
	}//FIN METODO
	
	private boolean checkComponentes() {
		if (this.textFieldFolio.getText().isEmpty()) {
			Notificacion.dialogoAlerta(AlertType.ERROR, "", "El campo \"Folio\" no puede estar vacio");
			return false;
		} else if (this.datePickerFechaPedido.getValue() == null) {
			Notificacion.dialogoAlerta(AlertType.ERROR, "", "El campo \"Fecha pedido\" no puede estar vacio");
			return false;
		} else if (this.comboBoxClientes.getValue() == null) {
			Notificacion.dialogoAlerta(AlertType.ERROR, "", "El campo \"Cliente\" no puede estar vacio");
			return false;
		} else if (this.textFieldPMP.getText().isEmpty()) {
			Notificacion.dialogoAlerta(AlertType.ERROR, "", "El campo \"PMP\" no puede estar vacio");
			return false;
		}//FIN IF/ELSE
		return true;
	}//FIN METODO
	
	private boolean saveInformacion() {
		this.ordenCompra.setFolio(this.textFieldFolio.getText());
		this.ordenCompra.setFechaPedido(Date.valueOf(this.datePickerFechaPedido.getValue()));
		this.ordenCompra.setClienteFK(this.comboBoxClientes.getSelectionModel().getSelectedItem());
		this.ordenCompra.setPMP(this.textFieldPMP.getText());
		this.ordenCompra.setComentarios(this.textAreaComentarios.getText());
		if (this.opcion == CREAR) {
			if (OrdenCompraDAO.create(connection, ordenCompra))
				return true;
			else
				return false;
		} else if (this.opcion == EDITAR) {
			if (OrdenCompraDAO.update(connection, ordenCompra))
				return true;
			else
				return false;
		} else 
			return true;
	}//FIN METODO
	
	//MANEJADORES
	@FXML private void manejadorBotonAceptar() {
		if (this.checkComponentes()) {
			if (this.saveInformacion()) {
				if (this.opcion != VER)
					Notificacion.dialogoAlerta(AlertType.CONFIRMATION, "", "El registro se realizó correctamente");
				this.mainApp.getEscenarioDialogos().close();
			} else {
				Notificacion.dialogoAlerta(AlertType.ERROR, "", "No se pudo realizar el registro correctamente, revisa que la información sea correcta");
			}//FIN IF/ELSE
		}
	}//FIN METODO
	
	@FXML private void manejadorBotonCerrar() {
		this.mainApp.getEscenarioDialogos().close();
	}//FIN METODO
}//FIN CLASE
