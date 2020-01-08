package mx.shf6.produccion.view;

import java.sql.Date;
import java.text.DecimalFormat;
import java.util.ArrayList;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import mx.shf6.produccion.MainApp;
import mx.shf6.produccion.model.Cliente;
import mx.shf6.produccion.model.Comprador;
import mx.shf6.produccion.model.Cotizacion;
import mx.shf6.produccion.model.Folio;
import mx.shf6.produccion.model.dao.ClienteDAO;
import mx.shf6.produccion.model.dao.CompradorDAO;
import mx.shf6.produccion.model.dao.CotizacionDAO;
import mx.shf6.produccion.model.dao.FolioDAO;
import mx.shf6.produccion.utilities.AutoCompleteComboBoxListener;
import mx.shf6.produccion.utilities.Notificacion;
import mx.shf6.produccion.utilities.RestriccionTextField;

public class DialogoCotizacion {
	
	//CONSTANTES
	public final static int VER = 1;
	public final static int CREAR = 2;
	public final static int EDITAR = 3;

	//PROPIEDADES
	private MainApp mainApp;
	private Cotizacion cotizacion;
	private int opcion;
	private Cliente cliente;
	private DecimalFormat decimalFormat;
	private ArrayList<Folio> listaFolios;
	private ArrayList<Cliente> listaClientes;
	private ObservableList<String> listaLetraFolio;
	private ObservableList<String> listaNombreClientes;
	private ObservableList<String> listaMonedas;
	private ObservableList<String> listaCompradores;
	
	//COMPONENTES INTERZAS USUARIO
	@FXML private ComboBox<String> comboBoxFolio;
	@FXML private ComboBox<String> comboBoxClientes;
	@FXML private ComboBox<String> comboSolicito;
	@FXML private TextField campoTextoAreaDepartamento;
	@FXML private TextField campoTextoTelefonoFax;
	@FXML private TextField campoTextoEmail;
	@FXML private TextField campoTextoTipoServicio;
	@FXML private DatePicker campoFechaEntrega;
	@FXML private TextField campoTextoCondicionEmbarque;
	@FXML private TextField campoTextoCondicionPago;
	@FXML private ComboBox<String> comboBoxMonedas;
	@FXML private TextField campoTextoTipoCambio;
	@FXML private TextField campoTextoObservaciones;
	@FXML private TextField campoTextoVigencia;
	
	//INICIALIZA COMPONENTES CONTROLAN INTERFAZ USUARIO
	@FXML private void initialize() {
		RestriccionTextField.soloLetras(this.campoTextoAreaDepartamento);
		RestriccionTextField.limitarNumeroCaracteres(this.campoTextoTelefonoFax, 16);
		RestriccionTextField.limitarNumeroCaracteres(this.campoTextoEmail, 64);
		RestriccionTextField.soloLetras(this.campoTextoTipoServicio);
		RestriccionTextField.soloLetras(this.campoTextoCondicionEmbarque);
		RestriccionTextField.limitarNumeroCaracteres(this.campoTextoCondicionPago, 64);
		RestriccionTextField.limitarPuntoDecimal(this.campoTextoTipoCambio);
		RestriccionTextField.limitarNumeroCaracteres(this.campoTextoObservaciones, 64);
		RestriccionTextField.limitarNumeroCaracteres(this.campoTextoVigencia, 64);
		listaMonedas = FXCollections.observableArrayList();
		listaClientes = new ArrayList<Cliente>();
		listaFolios = new ArrayList<Folio>();
		listaNombreClientes = FXCollections.observableArrayList();
		listaLetraFolio = FXCollections.observableArrayList();
		listaMonedas.add("MXN");
		listaMonedas.add("USD");
		listaMonedas.add("EUR");
		comboBoxMonedas.setItems(listaMonedas);		
		this.decimalFormat = new DecimalFormat("0000000");
	}//FIN METODO
		
	//ACCESO CLASE PRINCIPAL CONTROLA VISTAS
	public void setMainApp(MainApp mainApp, Cotizacion cotizacion, int opcion, Cliente cliente) {
		this.cotizacion = cotizacion;		
		this.mainApp = mainApp;
		this.opcion = opcion;		
		this.cliente = cliente;
		listaClientes = ClienteDAO.readCliente(this.mainApp.getConnection());
		listaFolios = FolioDAO.readFolio(this.mainApp.getConnection());
		if (cliente != null) {
			for (Cliente nombreCliente : listaClientes)
				this.listaNombreClientes.add(nombreCliente.getNombre());
		}else {
			for (Cliente nombreCliente: listaClientes)
				this.listaNombreClientes.add(nombreCliente.getNombre());
		}//FIN IF-ELSE		
		for (Folio folio : listaFolios) 
			this.listaLetraFolio.add(folio.getFolio());
		this.comboBoxClientes.setItems(listaNombreClientes);
		this.comboBoxFolio.setItems(listaLetraFolio);
		this.comboBoxMonedas.valueProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (newValue == "MXN") {
					campoTextoTipoCambio.setText("1");
					campoTextoTipoCambio.setDisable(true);
				}else {
					campoTextoTipoCambio.setText("");
					campoTextoTipoCambio.setDisable(false);
				}//FIN IF
			}//FIN METODO
		});//FIN LISTENER
		this.inicializarCompradores();
		this.mostrarDatosInterfaz();
	}//FIN METODO	
	
	private void inicializarCompradores() {	
		comboBoxClientes.valueProperty().addListener((ChangeListener<String>) (ov, t, t1) -> {
			Cliente cliente = ClienteDAO.readClienteNombre(this.mainApp.getConnection(), comboBoxClientes.getValue());
			comboSolicito.getSelectionModel().clearSelection();
			listaCompradores = CompradorDAO.leerCompradores(this.mainApp.getConnection(), cliente.getSysPK());
			comboSolicito.setItems(listaCompradores);
			new AutoCompleteComboBoxListener(comboSolicito);
		});
		
		comboSolicito.valueProperty().addListener((ChangeListener<String>) (ov, t, t1) -> {
			Comprador comprador = CompradorDAO.readCompradorNombre(this.mainApp.getConnection(), comboSolicito.getValue());
			
			if (!comprador.equals(null)) {
				this.campoTextoTelefonoFax.setText(comprador.getTelefono());
				this.campoTextoTelefonoFax.setDisable(true);
				this.campoTextoEmail.setText(comprador.getCorreo());
				this.campoTextoEmail.setDisable(true);
				this.campoTextoAreaDepartamento.setText(comprador.getAreaDepartamento());
				this.campoTextoAreaDepartamento.setDisable(true);
			}
		});
	}
		
	//MUESTRA DATOS DEL LA SOLICITUD
	private void mostrarDatosInterfaz() {
		if (this.opcion == CREAR) {
			this.comboBoxFolio.getSelectionModel().select(0);
			this.comboBoxFolio.setDisable(true);
			if(this.cliente != null) {
				this.comboBoxClientes.getSelectionModel().select(this.cliente.getNombre());
				this.comboBoxClientes.setDisable(true);
			}else {
				this.comboBoxClientes.getSelectionModel().select("");
				this.comboBoxClientes.setDisable(false);
			}			
			
			this.campoTextoAreaDepartamento.setText("");
			this.campoTextoAreaDepartamento.setDisable(false);
			this.campoTextoTelefonoFax.setText("");
			this.campoTextoTelefonoFax.setDisable(false);
			this.campoTextoEmail.setText("");
			this.campoTextoEmail.setDisable(false);
			this.campoTextoTipoServicio.setText("");
			this.campoTextoTipoServicio.setDisable(false);
			this.campoFechaEntrega.setUserData("");
			this.campoFechaEntrega.setDisable(false);
			this.campoTextoCondicionEmbarque.setText("");
			this.campoTextoCondicionEmbarque.setDisable(false);
			this.campoTextoCondicionPago.setText("");
			this.campoTextoCondicionPago.setDisable(false);
			this.comboBoxMonedas.getSelectionModel().select("");
			this.comboBoxMonedas.setDisable(false);
			this.campoTextoObservaciones.setText("");
			this.campoTextoObservaciones.setDisable(false);
			this.campoTextoVigencia.setText("");
			this.campoTextoVigencia.setDisable(false);
			
		} else if (this.opcion == VER) {
			this.comboBoxFolio.setDisable(true);
			this.comboBoxFolio.setValue(this.cotizacion.getFolio(this.mainApp.getConnection()).getFolio());
			this.comboBoxClientes.getSelectionModel().select(this.cotizacion.getCliente(this.mainApp.getConnection()).getNombre());
			this.comboBoxClientes.setDisable(true);
			this.comboSolicito.getSelectionModel().select(this.cotizacion.getSolicitante());
			this.comboSolicito.setDisable(true);
			this.campoTextoAreaDepartamento.setText(this.cotizacion.getAreaDepartamento());
			this.campoTextoAreaDepartamento.setDisable(true);
			this.campoTextoTelefonoFax.setText(this.cotizacion.getTelefonoFax());
			this.campoTextoTelefonoFax.setDisable(true);
			this.campoTextoEmail.setText(this.cotizacion.getEmail());
			this.campoTextoEmail.setDisable(true);
			this.campoTextoTipoServicio.setText(this.cotizacion.getTipoServicio());
			this.campoTextoTipoServicio.setDisable(true);
			this.campoFechaEntrega.setValue(this.cotizacion.getFechaEntrega().toLocalDate());
			this.campoFechaEntrega.setDisable(true);
			this.campoTextoCondicionEmbarque.setText(this.cotizacion.getCondicionEmbarque());
			this.campoTextoCondicionEmbarque.setDisable(true);
			this.campoTextoCondicionPago.setText(this.cotizacion.getCondicionPago());
			this.campoTextoCondicionPago.setDisable(true);
			this.comboBoxMonedas.getSelectionModel().select(this.cotizacion.getDescripcionMoneda());
			this.comboBoxMonedas.setDisable(true);
			this.campoTextoTipoCambio.setText(this.cotizacion.getTipoCambio().toString());
			this.campoTextoTipoCambio.setDisable(true);
			this.campoTextoObservaciones.setText(this.cotizacion.getObservaciones());
			this.campoTextoObservaciones.setDisable(true);
			this.campoTextoVigencia.setText(this.cotizacion.getVigencia());
			this.campoTextoVigencia.setDisable(true);
			
		} else if (this.opcion == EDITAR) {
			this.comboBoxFolio.setDisable(true);
			this.comboBoxFolio.setValue(this.cotizacion.getFolio(this.mainApp.getConnection()).getFolio());
			this.comboBoxClientes.getSelectionModel().select(this.cotizacion.getCliente(this.mainApp.getConnection()).getNombre());
			this.comboBoxClientes.setDisable(true);
			this.comboSolicito.getSelectionModel().select(this.cotizacion.getSolicitante());
			this.comboSolicito.setDisable(false);
			this.campoTextoAreaDepartamento.setText(this.cotizacion.getAreaDepartamento());
			this.campoTextoAreaDepartamento.setDisable(true);
			this.campoTextoTelefonoFax.setText(this.cotizacion.getTelefonoFax());
			this.campoTextoTelefonoFax.setDisable(true);
			this.campoTextoEmail.setText(this.cotizacion.getEmail());
			this.campoTextoEmail.setDisable(true);
			this.campoTextoTipoServicio.setText(this.cotizacion.getTipoServicio());
			this.campoTextoTipoServicio.setDisable(false);
			this.campoFechaEntrega.setValue(this.cotizacion.getFechaEntrega().toLocalDate());
			this.campoFechaEntrega.setDisable(false);
			this.campoTextoCondicionEmbarque.setText(this.cotizacion.getCondicionEmbarque());
			this.campoTextoCondicionEmbarque.setDisable(false);
			this.campoTextoCondicionPago.setText(this.cotizacion.getCondicionPago());
			this.campoTextoCondicionPago.setDisable(false);
			this.comboBoxMonedas.getSelectionModel().select(this.cotizacion.getDescripcionMoneda());
			this.comboBoxMonedas.setDisable(false);
			if (this.cotizacion.getDescripcionMoneda().equals("MXN")) {
				this.campoTextoTipoCambio.setText(this.cotizacion.getTipoCambio().toString());
				this.campoTextoTipoCambio.setDisable(true);
			}else {
				this.campoTextoTipoCambio.setText(this.cotizacion.getTipoCambio().toString());
				this.campoTextoTipoCambio.setDisable(false);
			}//FIN IF-ELSE
			this.campoTextoObservaciones.setText(this.cotizacion.getObservaciones());
			this.campoTextoObservaciones.setDisable(false);
			this.campoTextoVigencia.setText(this.cotizacion.getVigencia());
			this.campoTextoVigencia.setDisable(false);			
		}//FIN IF/ELSE
	}//FIN METODO
	
	//FIN METODO
	private boolean validarDatos() {
		if (this.comboBoxFolio.getSelectionModel().getSelectedItem() == null) {
			Notificacion.dialogoAlerta(AlertType.ERROR, "", "El campo \"Folio\" no puede estar vacio");
			return false;
		}else if (this.comboBoxClientes.getSelectionModel().getSelectedItem().isEmpty()) {
			Notificacion.dialogoAlerta(AlertType.ERROR, "", "El campo \"Cliente\" no puede estar vacio");
			return false;
		}else if (this.comboSolicito.getSelectionModel().getSelectedItem().isEmpty()) {
			Notificacion.dialogoAlerta(AlertType.ERROR, "", "El campo \"Solicitó\" no puede estar vacio");
			return false;
		}else if (this.campoTextoTipoServicio.getText().isEmpty()) {
			Notificacion.dialogoAlerta(AlertType.ERROR, "", "El campo \"Tipo Servicio\" no puede estar vacio");
			return false;
		}else if (this.campoTextoCondicionPago.getText().isEmpty()) {
			Notificacion.dialogoAlerta(AlertType.ERROR, "", "El campo \"Condiciones Pago\" no puede estar vacio");
			return false;
		} else if (this.campoFechaEntrega.getValue() == null) {
			Notificacion.dialogoAlerta(AlertType.ERROR, "", "El campo \"Días Entrega\" no puede estar vacio");
			return false;
		}else if (this.comboBoxMonedas.getSelectionModel().getSelectedItem().isEmpty()) {
			Notificacion.dialogoAlerta(AlertType.ERROR, "", "El campo \"Moneda\" no puede estar vacio");
			return false;
		}else if (this.campoTextoTipoCambio.getText().isEmpty()) {
			Notificacion.dialogoAlerta(AlertType.ERROR, "", "El campo \"Tipo Cambio\" no puede estar vacio");
			return false;
		}else if (this.campoTextoVigencia.getText().isEmpty()) {
			Notificacion.dialogoAlerta(AlertType.ERROR, "", "El campo \"Vigencia\" no puede estar vacio");
			return false;
		}//FIN IF/ESLE
		return true;
	}//FIN METODO
	
	@FXML private void manejadorBotonAceptar() {
		if (this.validarDatos()) {
			if (this.opcion == CREAR) {
				String consecutivo = decimalFormat.format(listaFolios.get(comboBoxFolio.getSelectionModel().getSelectedIndex()).getSerie());
				this.cotizacion.setReferencia(listaFolios.get(comboBoxFolio.getSelectionModel().getSelectedIndex()).getFolio() + consecutivo);
				this.cotizacion.setStatus(Cotizacion.PENDIENTE);
				this.cotizacion.setClienteFK(listaClientes.get(comboBoxClientes.getSelectionModel().getSelectedIndex()).getSysPK());
				this.cotizacion.setSolicitante(comboSolicito.getValue());
				this.cotizacion.setAreaDepartamento(this.campoTextoAreaDepartamento.getText());
				this.cotizacion.setTelefonoFax(this.campoTextoTelefonoFax.getText());
				this.cotizacion.setEmail(this.campoTextoEmail.getText());
				this.cotizacion.setTipoServicio(this.campoTextoTipoServicio.getText());
				this.cotizacion.setFechaEntrega(Date.valueOf(this.campoFechaEntrega.getValue()));
				this.cotizacion.setCondicionEmbarque(this.campoTextoCondicionEmbarque.getText());
				this.cotizacion.setCondicionPago(this.campoTextoCondicionPago.getText());
				this.cotizacion.setNumeroMoneda(this.comboBoxMonedas.getSelectionModel().getSelectedItem());
				this.cotizacion.setTipoCambio(Double.parseDouble(this.campoTextoTipoCambio.getText()));
				this.cotizacion.setObservaciones(this.campoTextoObservaciones.getText());
				this.cotizacion.setVigencia(this.campoTextoVigencia.getText());
				this.cotizacion.setFolioFK(listaFolios.get(comboBoxFolio.getSelectionModel().getSelectedIndex()).getSysPK());				
				if (CotizacionDAO.createCotizacion(this.mainApp.getConnection(), this.cotizacion)) {
					Notificacion.dialogoAlerta(AlertType.INFORMATION, "", "El registro se creo de forma correcta");
					this.mainApp.getEscenarioDialogos().close();
				} else
					Notificacion.dialogoAlerta(AlertType.INFORMATION, "", "No se pudo crear el registro, revisa que la información sea correcta");
			} else if (this.opcion == EDITAR) {
				this.cotizacion.setClienteFK(listaClientes.get(comboBoxClientes.getSelectionModel().getSelectedIndex()).getSysPK());
				this.cotizacion.setSolicitante(comboSolicito.getValue());
				this.cotizacion.setAreaDepartamento(this.campoTextoAreaDepartamento.getText());
				this.cotizacion.setTelefonoFax(this.campoTextoTelefonoFax.getText());
				this.cotizacion.setEmail(this.campoTextoEmail.getText());
				this.cotizacion.setTipoServicio(this.campoTextoTipoServicio.getText());
				this.cotizacion.setFechaEntrega(Date.valueOf(this.campoFechaEntrega.getValue()));
				this.cotizacion.setCondicionEmbarque(this.campoTextoCondicionEmbarque.getText());
				this.cotizacion.setCondicionPago(this.campoTextoCondicionPago.getText());
				this.cotizacion.setNumeroMoneda(this.comboBoxMonedas.getSelectionModel().getSelectedItem());
				this.cotizacion.setTipoCambio(Double.parseDouble(this.campoTextoTipoCambio.getText()));
				this.cotizacion.setObservaciones(this.campoTextoObservaciones.getText());
				this.cotizacion.setVigencia(this.campoTextoVigencia.getText());				
				if (CotizacionDAO.updateCotizacion(this.mainApp.getConnection(), this.cotizacion)) {
					Notificacion.dialogoAlerta(AlertType.INFORMATION, "", "El registro se actualizo de forma correcta");
					this.mainApp.getEscenarioDialogos().close();
				} else
					Notificacion.dialogoAlerta(AlertType.INFORMATION, "", "No se pudo actualizar el registro, revisa que la información sea correcta");
			} else if (this.opcion == VER)
				this.mainApp.getEscenarioDialogos().close();
		}//FIN METODO
	}//FIN METODO
	
	@FXML private void manejadorBotonCerrar() {
		this.mainApp.getEscenarioDialogos().close();
	}//FIN METODO
}//FIN CLASE