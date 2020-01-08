package mx.shf6.produccion.view;

import java.sql.Connection;
import java.sql.Date;

import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import mx.shf6.produccion.MainApp;
import mx.shf6.produccion.model.DetalleEntregaOrdenCompra;
import mx.shf6.produccion.model.DetalleOrdenCompra;
import mx.shf6.produccion.model.dao.DetalleEntregaOrdenCompraDAO;
import mx.shf6.produccion.model.dao.DetalleOrdenCompraDAO;
import mx.shf6.produccion.utilities.Notificacion;
import mx.shf6.produccion.utilities.RestriccionTextField;

public class DialogoAgregarDetalleEntregaOrdenCompra {
	
	//PROPIEDADES
	private MainApp mainApp;
	private Connection connection;
	private DetalleEntregaOrdenCompra detalleEntregaOrdenCompra;
	private DetalleOrdenCompra detalleOrdenCompra;
	
	//VARIABLES
	private int saldo;
	
	//CONSTANTES

	//COMPONENTES DE LA INTERFAZ
	@FXML private TextField textFieldFactura;
	@FXML private TextField textFieldCantidad;
	@FXML private DatePicker datePickerFecha;
	
	//METODOS
	@FXML private void initialize() {
		initComponentes();
	}//FIN METODO
	
	public void setMainApp(MainApp mainApp, DetalleEntregaOrdenCompra detalleEntregaOrdenCompra, DetalleOrdenCompra detalleOrdenCompra) {
		this.mainApp = mainApp;
		this.connection = this.mainApp.getConnection();
		this.detalleEntregaOrdenCompra = detalleEntregaOrdenCompra;
		this.detalleOrdenCompra = detalleOrdenCompra;
	}//FIN METODO
	
	private void initComponentes() {
		RestriccionTextField.soloNumeros(textFieldCantidad);
		RestriccionTextField.limitarNumeroCaracteres(textFieldFactura, 16);
	}//FIN METODO
	
	private boolean checkComponentes() {
		if (this.textFieldCantidad.getText().isEmpty()) {
			Notificacion.dialogoAlerta(AlertType.ERROR, "", "El campo \"Cantidad \" no puede estar vacio");
			return false;
		} else if (this.datePickerFecha.getValue() == null) {
			Notificacion.dialogoAlerta(AlertType.ERROR, "", "El campo \"Fecha \" no puede estar vacio");
			return false;
		}//FIN IF/ELSE
		return true;
	}//FIN METODO
	
	private boolean saveInformacion() {
		this.saldo = detalleOrdenCompra.getSaldo();
		if (checkComponentes()) {
			this.detalleEntregaOrdenCompra.setFactura(this.textFieldFactura.getText());
			this.detalleEntregaOrdenCompra.setCantidad(Integer.valueOf(this.textFieldCantidad.getText()));
			this.detalleEntregaOrdenCompra.setFecha(Date.valueOf(this.datePickerFecha.getValue()));
			this.detalleEntregaOrdenCompra.setDetalleOrdenCompraFK(this.detalleOrdenCompra);
			this.saldo = this.saldo - this.detalleEntregaOrdenCompra.getCantidad();
			
			if (this.saldo >= 0) {
				this.detalleOrdenCompra.setSaldo(saldo);
				if (DetalleEntregaOrdenCompraDAO.create(connection, detalleEntregaOrdenCompra)) {
					DetalleOrdenCompraDAO.update(connection, detalleOrdenCompra);
					return true;
				}else
					return false;
			}else {
				Notificacion.dialogoAlerta(AlertType.ERROR, "", "No puedes entregar mas componentes de los solicitados por el cliente");
				return false;
			}//FIN IF/ELSE	
		}else 
			return false;
	}//FIN METODO
	
	//MANEJADORES
	@FXML private void manejadorBotonAceptar() {
		if (saveInformacion()) {
			Notificacion.dialogoAlerta(AlertType.INFORMATION, "", "Registro creado correctamente");
			this.mainApp.getEscenarioDialogosAlternoSecundario().close();
		}//FIN IF
	}//FIN METODO

	@FXML private void manejadorBotonCerrar() {
		this.mainApp.getEscenarioDialogosAlternoSecundario().close();
	}//FIN METODO
}//FIN CLASE
