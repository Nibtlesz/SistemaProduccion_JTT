package mx.shf6.produccion.view;


import java.io.File;
import java.sql.SQLException;
import java.text.DecimalFormat;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.control.Alert.AlertType;
import mx.shf6.produccion.MainApp;
import mx.shf6.produccion.model.Cliente;
import mx.shf6.produccion.model.Domicilio;
import mx.shf6.produccion.model.Folio;
import mx.shf6.produccion.model.Proyecto;
import mx.shf6.produccion.model.dao.ClienteDAO;
import mx.shf6.produccion.model.dao.DomicilioDAO;
import mx.shf6.produccion.model.dao.FolioDAO;
import mx.shf6.produccion.model.dao.ProyectoDAO;
import mx.shf6.produccion.model.dao.SepomexDAO;
import mx.shf6.produccion.utilities.AutoCompleteComboBoxListener;
import mx.shf6.produccion.utilities.Notificacion;
import mx.shf6.produccion.utilities.RestriccionTextField;

public class DialogoClientes  {

	//PROPIEDADES
	private MainApp mainApp;
	private Cliente cliente;
	private File renameRuta;
	private Domicilio domicilio;
	

	private SepomexDAO sepomexDAO;

	private ObservableList<String> listaEstados;
	private ObservableList<String> listaMunicipios;
	private ObservableList<String> listaPaises;
	
	//VARIABLES
	private int opcion;
	
	//CONSTANTES
	public final static int MOSTRAR = 1;
	public final static int CREAR = 2;
	public final static int EDITAR = 3;
	
	
	//COMPONENTES INTERFAZ 
	@FXML private TextField nombreField;
	@FXML private TextField codigoField;
	@FXML private Label saldoField;
	@FXML private Label numeroProyectosField;
	@FXML private TextField registroContribuyenteField;
	@FXML private TextField telefonoField;
	@FXML private TextField correoField;
	@FXML private TextField fechaRegistroField;
	@FXML private TextField rutaField;
	@FXML private TextField calleField;
	@FXML private TextField numeroExteriorField;
	@FXML private TextField numeroInteriorField;
	@FXML private TextField coloniaField;
	@FXML private TextField localidadField;
	@FXML private ComboBox<String> statusCombo;
	@FXML private ComboBox<String> municipioCombo;
	@FXML private ComboBox<String> estadoCombo;
	@FXML private ComboBox<String> paisCombo;
	@FXML private TextField codigoPostalField;
	@FXML private ImageView eliminar;
	@FXML private ImageView editar;
	@FXML private ImageView guardar;
	
	//INICIALIZA LOS COMPOMENTES QUE SE CONTROLAN EN LA INTERFAZ DE USUARIO
	@FXML private void initialize() {
		this.domicilio = new Domicilio();
		this.sepomexDAO = new SepomexDAO();
		this.cliente = new Cliente();
		
		RestriccionTextField.limitarNumeroCaracteres(this.codigoPostalField, 8);
		RestriccionTextField.limitarNumeroCaracteres(this.codigoField, 8);
		RestriccionTextField.soloLetras(this.nombreField);		
		RestriccionTextField.limitarNumeroCaracteres(this.registroContribuyenteField, 16);
		RestriccionTextField.limitarNumeroCaracteres(this.telefonoField, 16);

		ObservableList<String> listaStatus = FXCollections.observableArrayList("Bloqueado", "Activo", "Baja");
		this.statusCombo.setItems(listaStatus);	
		
		
	}//FIN METODO
	
	//ACCESO CLASE PRINCIPAL
	public void setMainApp(MainApp mainApp, Cliente cliente, Integer opcion) {
		this.mainApp = mainApp;
		this.cliente = cliente;
		this.opcion = opcion;
		this.domicilio = cliente.getDomicilio(this.mainApp.getConnection());

		this.renameRuta = new File(MainApp.RAIZ_SERVIDOR + "Clientes\\" + this.cliente.getNombre());
		
		this.listaPaises = sepomexDAO.leerPaises(mainApp.getConnection());
		paisCombo.setItems(listaPaises);
		new AutoCompleteComboBoxListener(paisCombo);
		paisCombo.valueProperty().addListener(new ChangeListener<String>(){
			
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (paisCombo.getValue().equals("México")) {
					estadoCombo.setDisable(false);
					municipioCombo.setDisable(false);
					
					inicializarEstado();
				}else {
					
					estadoCombo.setDisable(true);
					municipioCombo.setDisable(true);
				}//FIN IF-ELSE
			}//FIN METODO
		});//FIN LISTENER		
		
		this.mostrarDatosInterfaz();
	}//FIN METODO
	
	private void inicializarEstado() {
		this.listaEstados = sepomexDAO.leerEstados(mainApp.getConnection()); 
		estadoCombo.setItems(listaEstados);
		new AutoCompleteComboBoxListener(estadoCombo);
		estadoCombo.valueProperty().addListener((ChangeListener<String>) (ov, t, t1) -> {
			municipioCombo.getSelectionModel().clearSelection();
			listaMunicipios = this.sepomexDAO.leerMunicipios(this.mainApp.getConnection(), estadoCombo.getValue()); 
			municipioCombo.setItems(listaMunicipios);
			new AutoCompleteComboBoxListener(municipioCombo);
		});//FIN SENTENCIA
	}//FIN METODO
	
	//MUESTRA LOS DATOS DEl CLIENTE
	private void mostrarDatosInterfaz() {
		
		if (this.opcion == MOSTRAR) {
				
			this.codigoField.setText(this.cliente.getCodigo());
			this.codigoField.setDisable(true);
			
			this.nombreField.setText(this.cliente.getNombre());
			this.nombreField.setDisable(true);
			
			this.registroContribuyenteField.setText(this.cliente.getRegistroContribuyente());
			this.registroContribuyenteField.setDisable(true);
			
			this.telefonoField.setText(this.cliente.getTelefono());
			this.telefonoField.setDisable(true);
			
			this.correoField.setText(this.cliente.getCorreo());
			this.correoField.setDisable(true);
			
			this.fechaRegistroField.setText(this.cliente.getFechaRegistro().toString());
			this.fechaRegistroField.setDisable(true);
			
			this.rutaField.setText(MainApp.RAIZ_SERVIDOR +"Clientes\\" +  this.cliente.getNombre());
			this.rutaField.setDisable(true);
			
			this.statusCombo.setValue(this.cliente.getDescripcionStatus());
			this.statusCombo.setDisable(true);
			
			this.calleField.setText(this.cliente.getDomicilio(this.mainApp.getConnection()).getCalle());
			this.calleField.setDisable(true);
			
			this.numeroExteriorField.setText(this.cliente.getDomicilio(this.mainApp.getConnection()).getNumeroExterior());
			this.numeroExteriorField.setDisable(true);
			
			this.numeroInteriorField.setText(this.cliente.getDomicilio(this.mainApp.getConnection()).getNumeroInterior());
			this.numeroInteriorField.setDisable(true);
			
			this.coloniaField.setText(this.cliente.getDomicilio(this.mainApp.getConnection()).getColonia());
			this.coloniaField.setDisable(true);
			
			this.localidadField.setText(this.cliente.getDomicilio(this.mainApp.getConnection()).getLocalidad());
			this.localidadField.setDisable(true);
			
			this.paisCombo.setValue(this.cliente.getDomicilio(this.mainApp.getConnection()).getPais());
			this.paisCombo.setDisable(true);
			
			this.estadoCombo.setValue(this.cliente.getDomicilio(this.mainApp.getConnection()).getEstado());
			this.estadoCombo.setDisable(true);
			
			this.municipioCombo.setValue(this.cliente.getDomicilio(this.mainApp.getConnection()).getMunicipio());
			this.municipioCombo.setDisable(true);
			
			this.codigoPostalField.setText(this.cliente.getDomicilio(this.mainApp.getConnection()).getCodigoPostal());
			this.codigoPostalField.setDisable(true);
			
			this.numeroProyectosField.setText(String.valueOf(proyectosRealizados(this.cliente.getSysPK())));
			
			this.eliminar.setDisable(false);
			this.editar.setDisable(false);
			this.guardar.setDisable(true);
			
			
		} else if (this.opcion == CREAR) {
			this.codigoField.setText("<A>");
			this.codigoField.setDisable(true);
			
			this.nombreField.setText("");
			this.nombreField.setDisable(false);
			
			this.registroContribuyenteField.setText("");
			this.registroContribuyenteField.setDisable(false);
			
			this.telefonoField.setText("");
			this.telefonoField.setDisable(false);
			
			this.correoField.setText("");
			this.correoField.setDisable(false);
			
			this.fechaRegistroField.setDisable(true);
			
			this.rutaField.setText(MainApp.RAIZ_SERVIDOR +"Clientes\\");
			this.rutaField.setDisable(true);
			
			this.statusCombo.setValue("");
			this.statusCombo.setDisable(false);
			
			this.calleField.setText("");
			this.calleField.setDisable(false);
			
			this.numeroExteriorField.setText("");
			this.numeroExteriorField.setDisable(false);
			
			this.numeroInteriorField.setText("");
			this.numeroInteriorField.setDisable(false);
			
			this.coloniaField.setText("");
			this.coloniaField.setDisable(false);
			
			this.localidadField.setText("");
			this.localidadField.setDisable(false);
			
			
			this.codigoPostalField.setText("");
			this.codigoPostalField.setDisable(false);	
			
			this.eliminar.setDisable(true);
			this.editar.setDisable(true);
			this.guardar.setDisable(false);
			
		}else if(this.opcion == EDITAR){
				
			this.codigoField.setText(this.cliente.getCodigo());
			this.codigoField.setDisable(false);
			
			this.nombreField.setText(this.cliente.getNombre());
			this.nombreField.setDisable(false);
			
			this.registroContribuyenteField.setText(this.cliente.getRegistroContribuyente());
			this.registroContribuyenteField.setDisable(false);
			
			this.telefonoField.setText(this.cliente.getTelefono());
			this.telefonoField.setDisable(false);
			
			this.correoField.setText(this.cliente.getCorreo());
			this.correoField.setDisable(false);
			
			this.fechaRegistroField.setText(this.cliente.getFechaRegistro().toString());
			this.fechaRegistroField.setDisable(true);
			
			this.rutaField.setText(MainApp.RAIZ_SERVIDOR +"Clientes\\" +  this.cliente.getNombre());
			this.rutaField.setDisable(true);
			
			this.statusCombo.setValue(this.cliente.getDescripcionStatus());
			this.statusCombo.setDisable(false);
			
			this.calleField.setText(this.cliente.getDomicilio(this.mainApp.getConnection()).getCalle());
			this.calleField.setDisable(false);
			
			this.numeroExteriorField.setText(this.cliente.getDomicilio(this.mainApp.getConnection()).getNumeroExterior());
			this.numeroExteriorField.setDisable(false);
			
			this.numeroInteriorField.setText(this.cliente.getDomicilio(this.mainApp.getConnection()).getNumeroInterior());
			this.numeroInteriorField.setDisable(false);
			
			this.coloniaField.setText(this.cliente.getDomicilio(this.mainApp.getConnection()).getColonia());
			this.coloniaField.setDisable(false);
			
			this.localidadField.setText(this.cliente.getDomicilio(this.mainApp.getConnection()).getLocalidad());
			this.localidadField.setDisable(false);
			
			this.paisCombo.setValue(this.cliente.getDomicilio(this.mainApp.getConnection()).getPais());
			this.paisCombo.setDisable(false);
			
			this.estadoCombo.setValue(this.cliente.getDomicilio(this.mainApp.getConnection()).getEstado());
			this.estadoCombo.setDisable(false);
			
			this.municipioCombo.setValue(this.cliente.getDomicilio(this.mainApp.getConnection()).getMunicipio());
			this.municipioCombo.setDisable(false);
			
			this.codigoPostalField.setText(this.cliente.getDomicilio(this.mainApp.getConnection()).getCodigoPostal());
			this.codigoPostalField.setDisable(false);
			
			this.numeroProyectosField.setText(String.valueOf(proyectosRealizados(this.cliente.getSysPK())));
			
			this.eliminar.setDisable(true);
			this.editar.setDisable(true);
			this.guardar.setDisable(false);
		}//FIN IF/ELSE
	}//FIN METODO
	
	
	//VALIDACION DE CAMPOS
	public boolean validacion(){
		if (this.codigoField.getText().isEmpty()) {
			Notificacion.dialogoAlerta(AlertType.ERROR, "", "El campo \"Código\" no puede estar vacio");
			return false;
		} else if (this.nombreField.getText().isEmpty()) {
			Notificacion.dialogoAlerta(AlertType.ERROR, "", "El campo \"Nombre\" no puede estar vacio");
			return false;
		}else if (this.registroContribuyenteField.getText().isEmpty()) {
			Notificacion.dialogoAlerta(AlertType.ERROR, "", "El campo \"Registro Contribuyente\" no puede estar vacio");
			return false;
		}else if (this.telefonoField.getText().isEmpty()) {
			Notificacion.dialogoAlerta(AlertType.ERROR, "", "El campo \"Telefono\" no puede estar vacio");
			return false;
		}else if (this.correoField.getText().isEmpty()) {
			Notificacion.dialogoAlerta(AlertType.ERROR, "", "El campo \"Correo\" no puede estar vacio");
			return false;
		} else if (this.statusCombo.getSelectionModel().getSelectedItem().isEmpty()) {
			Notificacion.dialogoAlerta(AlertType.ERROR, "", "El campo \"Estado Cliente\" no puede estar vacio");
			return false;
		}else if (this.calleField.getText().isEmpty()) {
			Notificacion.dialogoAlerta(AlertType.ERROR, "", "El campo \"Calle\" no puede estar vacio");
			return false;
		}else if (this.numeroExteriorField.getText().isEmpty()) {
			Notificacion.dialogoAlerta(AlertType.ERROR, "", "El campo \"Numero Exterior\" no puede estar vacio");
			return false;
		}else if (this.coloniaField.getText().isEmpty()) {
			Notificacion.dialogoAlerta(AlertType.ERROR, "", "El campo \"Colonia\" no puede estar vacio");
			return false;
		}else if (this.localidadField.getText().isEmpty()) {
			Notificacion.dialogoAlerta(AlertType.ERROR, "", "El campo \"Localidad\" no puede estar vacio");
			return false;
		}else if (this.codigoPostalField.getText().isEmpty()) {
			Notificacion.dialogoAlerta(AlertType.ERROR, "", "El campo \"Codigo Postal\" no puede estar vacio");
			return false;
		}else if (RestriccionTextField.validarEmail(this.correoField.getText()) == false) {
			Notificacion.dialogoAlerta(AlertType.ERROR, "", "El correo no es valido.");
			return false;
		}else if (this.paisCombo.getEditor().getText().equals("") || this.paisCombo.getSelectionModel().getSelectedItem().isEmpty()) {
			Notificacion.dialogoAlerta(AlertType.ERROR, "", "El campo \"Pais\" no puede estar vacio");
			return false;
		}else if (this.paisCombo.getValue().equals("México")) {
			 if (this.estadoCombo.getEditor().getText().equals("") || this.estadoCombo.getSelectionModel().getSelectedItem().isEmpty()) {
					Notificacion.dialogoAlerta(AlertType.ERROR, "", "El campo \"Estado\" no puede estar vacio");
					return false;
				}else if (this.municipioCombo.getEditor().getText().equals("") || this.municipioCombo.getSelectionModel().getSelectedItem().isEmpty()) {
					Notificacion.dialogoAlerta(AlertType.ERROR, "", "El campo \"Municipio\" no puede estar vacio");
					return false;
				}//FIN IF-ELSE
		}//FIN IF-ELSE
		return true;		
	}//FIN METODO
	
	//MANEJADOR DE COMPONENTES
	@FXML
	private void btnEliminar() {
		if (Notificacion.dialogoPreguntar("Confirmación para eliminar", "¿Desea eliminar a " + cliente.getNombre() + "?")){
			if(ClienteDAO.deleteCliente(mainApp.getConnection(), cliente)) {
				DomicilioDAO.deleteDomicilio(mainApp.getConnection(),cliente.getDomicilio(mainApp.getConnection()));
				File ruta = new File(MainApp.RAIZ_SERVIDOR +"Clientes\\" + cliente.getNombre());
	    		ruta.delete();
			}			
			cerrarDialogoButtonHandler();			
		}//FIN IF
	}//FIN METODO
	
	@FXML
	private void btnGuardar()  {	
		if(this.validacion()) {
			if(this.opcion == CREAR) {
				DecimalFormat decimalFormat = new DecimalFormat("000000");
				Folio folio = FolioDAO.readFolioByFolio(this.mainApp.getConnection(), Cliente.FOLIO);
				String codigo = folio.getFolio() + decimalFormat.format(folio.getSerie() + 1);
				this.cliente.setCodigo(codigo);
				this.cliente.setNombre(this.nombreField.getText());
				this.cliente.setRegistroContribuyente(this.registroContribuyenteField.getText());
				this.cliente.setTelefono(this.telefonoField.getText());
				this.cliente.setCorreo(this.correoField.getText());
				this.cliente.setRutaCarpeta(MainApp.RAIZ_SERVIDOR +"Clientes\\" +  this.nombreField.getText());
				this.cliente.setNumeroStatus(this.statusCombo.getValue());
				
				this.domicilio.setCalle(this.calleField.getText());
				this.domicilio.setNumeroExterior(this.numeroExteriorField.getText());
				this.domicilio.setNumeroInterior(this.numeroInteriorField.getText());
				this.domicilio.setColonia(this.coloniaField.getText());
				this.domicilio.setLocalidad(this.localidadField.getText());
				this.domicilio.setPais(this.paisCombo.getValue());
				this.domicilio.setMunicipio(this.municipioCombo.getValue());
				this.domicilio.setEstado(this.estadoCombo.getValue());
				this.domicilio.setCodigoPostal(this.codigoPostalField.getText());
							
				try {
					this.mainApp.getConnection().setAutoCommit(false);
					if(DomicilioDAO.createDomicilio(this.mainApp.getConnection(), this.domicilio)) {
						this.cliente.setDomicilioFK(DomicilioDAO.ultimoSysPk(this.mainApp.getConnection()));
						
						if (ClienteDAO.createCliente(this.mainApp.getConnection(), this.cliente)) {
							this.mainApp.getConnection().commit();
														
							File ruta = new File(MainApp.RAIZ_SERVIDOR +"Clientes\\" +  this.nombreField.getText() + "\\Proyectos");
							ruta.mkdirs();
							Notificacion.dialogoAlerta(AlertType.INFORMATION, "", "El registro se creo de forma correcta");
							
							this.mainApp.getEscenarioDialogos().close();
						} else {
							this.mainApp.getConnection().rollback();
							Notificacion.dialogoAlerta(AlertType.INFORMATION, "", "No se pudo crear el registro, revisa que la información sea correcta");
						}
					}else {
						this.mainApp.getConnection().rollback();
						Notificacion.dialogoAlerta(AlertType.INFORMATION, "", "No se pudo crear el registro, revisa que la información sea correcta");
					}	//FIN IF
				} catch (SQLException ex) {
					Notificacion.dialogoException(ex);
				}//FIN TRY/CATCH
				
			}else if(this.opcion == EDITAR) {
				this.cliente.setCodigo(this.codigoField.getText());
				this.cliente.setNombre(this.nombreField.getText());
				this.cliente.setRegistroContribuyente(this.registroContribuyenteField.getText());
				this.cliente.setTelefono(this.telefonoField.getText());
				this.cliente.setCorreo(this.correoField.getText());
				this.cliente.setRutaCarpeta(MainApp.RAIZ_SERVIDOR +"Clientes\\" +  this.nombreField.getText());
				this.cliente.setNumeroStatus(this.statusCombo.getValue());
				
				this.domicilio.setCalle(this.calleField.getText());
				this.domicilio.setNumeroExterior(this.numeroExteriorField.getText());
				this.domicilio.setNumeroInterior(this.numeroInteriorField.getText());
				this.domicilio.setColonia(this.coloniaField.getText());
				this.domicilio.setLocalidad(this.localidadField.getText());
				this.domicilio.setPais(this.paisCombo.getValue());
				this.domicilio.setMunicipio(this.municipioCombo.getValue());
				this.domicilio.setEstado(this.estadoCombo.getValue());
				this.domicilio.setCodigoPostal(this.codigoPostalField.getText());
				
				
				try {
					this.mainApp.getConnection().setAutoCommit(false);
					if(DomicilioDAO.updateDomicilio(this.mainApp.getConnection(), this.domicilio)) {
						if (ClienteDAO.updateCliente(this.mainApp.getConnection(), this.cliente)) {
							this.mainApp.getConnection().commit();

							this.renameRuta.renameTo(new File(MainApp.RAIZ_SERVIDOR +"Clientes\\" +  this.nombreField.getText() ));
			
							Notificacion.dialogoAlerta(AlertType.INFORMATION, "", "El registro actualizado. ");
							this.mainApp.getEscenarioDialogos().close();
						} else {
							this.mainApp.getConnection().rollback();
							Notificacion.dialogoAlerta(AlertType.INFORMATION, "", "No se pudo actualizar el registro, revisa que la información sea correcta");
						}
					}else {
						this.mainApp.getConnection().rollback();
						Notificacion.dialogoAlerta(AlertType.INFORMATION, "", "No se pudo actualizar el registro, revisa que la información sea correcta");
					}	//FIN IF
				} catch (SQLException ex) {
					Notificacion.dialogoException(ex);
				}//FIN TRY/CATCH	
			}//FIN ELSE IF
		}//FIN IF
		
		
	}//FIN METODO
	
	@FXML private void btnEditar() {
		this.opcion = EDITAR;
		this.mostrarDatosInterfaz();
	}//FIN METODO
	
	private int proyectosRealizados(int clienteFK) {
		int contador = 0;
		for(@SuppressWarnings("unused") Proyecto proyecto : ProyectoDAO.readProyectoCliente(this.mainApp.getConnection(), clienteFK)) 
			contador = contador + 1;
		return contador;
	}//FIN METODO 
	
	@FXML private void cerrarDialogoButtonHandler() {
		this.mainApp.getEscenarioDialogos().close();
	}//FIN METODO
}//FIN METODO