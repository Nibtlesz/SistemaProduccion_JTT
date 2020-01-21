package mx.shf6.produccion.view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import mx.shf6.produccion.MainApp;
import mx.shf6.produccion.model.TipoComponente;
import mx.shf6.produccion.model.Componente;
import mx.shf6.produccion.model.dao.AcabadoDAO;
import mx.shf6.produccion.model.dao.ClienteDAO;
import mx.shf6.produccion.model.dao.ComponenteDAO;
import mx.shf6.produccion.model.dao.MaterialDAO;
import mx.shf6.produccion.model.dao.TipoMateriaPrimaDAO;
import mx.shf6.produccion.model.dao.TipoMiscelaneoDAO;
import mx.shf6.produccion.model.dao.TratamientoDAO;
import mx.shf6.produccion.utilities.Dimensiones;
import mx.shf6.produccion.utilities.Notificacion;
import mx.shf6.produccion.utilities.RestriccionTextField;

public class DialogoComponente {

	//PROPIEDADES
	private MainApp mainApp;
	private Componente componente;

	
	//VARIABLES
	private int opcion;
	
	//CONSTANTES
	public static final int CREAR = 1;
	public static final int VER = 2;
	public static final int EDITAR = 3;
	
	//COMPONENTES INTERFAZ
	@FXML private TextField campoTextoNumeroParte;
	@FXML private TextField campoTextoDescripcion;
	@FXML private TextField campoTextoLargo;
	@FXML private ComboBox<String> comboBoxLargo;
	@FXML private TextField campoTextoAncho;
	@FXML private ComboBox<String> comboBoxAncho;
	@FXML private TextField campoTextoAlto;
	@FXML private ComboBox<String> comboBoxAlto;
	@FXML private TextField campoTextoDiametroInterior;
	@FXML private ComboBox<String> comboBoxDiametroInterior;
	@FXML private TextField campoTextoDiametroExterior;
	@FXML private ComboBox<String> comboBoxDiametroExterior;
	@FXML private TextField campoTextoAlto2;
	@FXML private ComboBox<String> comboBoxAlto2;
	@FXML private TextField campoAnchoTotal;
	@FXML private ComboBox<String> comboBoxAnchoTotal;
	@FXML private TextField campoTextoEspesor;
	@FXML private ComboBox<String> comboBoxEspesor;
	@FXML private TextField campoCodigoCatalogo;
	@FXML private TextField campoTextoGradoMaterial;
	@FXML private TextField campoTextoCosto;
	@FXML private TextField campoTextoCostoDirecto;
	@FXML private TextField campoTextoCostoIndirecto;
	@FXML private TextArea campoTextoNotas;
	@FXML private ComboBox<String> comboBoxStatus;
	@FXML private TextField campoTextoConsecutivo;
	@FXML private ComboBox<String> comboBoxTipoComponente;
	@FXML private ComboBox<String> comboBoxMaterial;
	@FXML private ComboBox<String> comboBoxCliente;
	@FXML private ComboBox<String> comboBoxTipoMateriaPrima;
	@FXML private ComboBox<String> comboBoxTipoMiscelaneo;
	@FXML private ComboBox<String> comboBoxAcabado;
	@FXML private ComboBox<String> comboBoxTratamiento;
	@FXML private CheckBox checkHabilitar;
	@FXML private TextField campoTextoHilos;
	@FXML private TextField campoTextoRevision;
	
	//INICIA COMPONENTES INTERFAZ USUARIO
	@FXML private void initialize() {
		this.componente = new Componente();
		
		RestriccionTextField.limitarPuntoDecimal(campoAnchoTotal);
		RestriccionTextField.limitarPuntoDecimal(campoTextoAlto);
		RestriccionTextField.limitarPuntoDecimal(campoTextoAlto2);
		RestriccionTextField.limitarPuntoDecimal(campoTextoAncho);
		RestriccionTextField.limitarPuntoDecimal(campoTextoCosto);
		RestriccionTextField.limitarPuntoDecimal(campoTextoCostoDirecto);
		RestriccionTextField.limitarPuntoDecimal(campoTextoCostoIndirecto);
		RestriccionTextField.limitarPuntoDecimal(campoTextoDiametroExterior);
		RestriccionTextField.limitarPuntoDecimal(campoTextoDiametroInterior);
		RestriccionTextField.limitarPuntoDecimal(campoTextoEspesor);
		RestriccionTextField.limitarPuntoDecimal(campoTextoLargo);
		RestriccionTextField.limitarNumeroCaracteres(campoTextoRevision, 2);
	}//FIN METODO
	
	//ACCESO CLASE PRINCIPAL
	public void setMainApp(MainApp mainApp, Componente componente, int opcion) {
		this.mainApp = mainApp;
		this.componente = componente;
		this.opcion = opcion;
		this.inicializarCombos();
		this.inicializarComponentes();
	}//FIN METODO
	
	private void inicializarCombos() {
		ObservableList<String> listaStatus = FXCollections.observableArrayList("No Visible", "Visible");
		this.comboBoxStatus.setItems(listaStatus);
		ObservableList<String> listaComponentes = FXCollections.observableArrayList(TipoComponente.COMPRADO, TipoComponente.MATERIA_PRIMA, TipoComponente.PARTE_PRIMARIA);
		this.comboBoxTipoComponente.setItems(listaComponentes);
		this.comboBoxTipoComponente.valueProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (opcion == CREAR) {
					if (newValue == TipoComponente.COMPRADO) {
						comboBoxCliente.getSelectionModel().select("");
						comboBoxCliente.setDisable(true);
						comboBoxMaterial.getSelectionModel().select("");
						comboBoxMaterial.setDisable(false);
						comboBoxTipoMateriaPrima.getSelectionModel().select("");
						comboBoxTipoMateriaPrima.setDisable(true);
						comboBoxTipoMiscelaneo.getSelectionModel().select("");
						comboBoxTipoMiscelaneo.setDisable(false);
						comboBoxAcabado.getSelectionModel().select("");
						comboBoxAcabado.setDisable(true);
						comboBoxTratamiento.getSelectionModel().select("");
						comboBoxTratamiento.setDisable(false);
						campoTextoLargo.setText("");
						campoTextoLargo.setDisable(false);
						comboBoxLargo.getSelectionModel().select("");
						comboBoxLargo.setDisable(false);
						campoTextoAncho.setText("");
						campoTextoAncho.setDisable(false);
						comboBoxAncho.getSelectionModel().select("");
						comboBoxAncho.setDisable(false);
						campoTextoAlto.setText("");
						campoTextoAlto.setDisable(false);
						comboBoxAlto.getSelectionModel().select("");
						comboBoxAlto.setDisable(false);
						campoTextoNumeroParte.setDisable(true);
						campoTextoGradoMaterial.setText("");
						campoTextoGradoMaterial.setDisable(false);
						campoTextoEspesor.setText("");
						campoTextoEspesor.setDisable(false);
						comboBoxEspesor.getSelectionModel().select("");
						comboBoxEspesor.setDisable(false);
						campoTextoDiametroExterior.setText("");
						campoTextoDiametroExterior.setDisable(false);
						comboBoxDiametroExterior.getSelectionModel().select("");
						comboBoxDiametroExterior.setDisable(false);
						campoTextoDiametroInterior.setText("");
						campoTextoDiametroInterior.setDisable(false);
						comboBoxDiametroInterior.getSelectionModel().select("");
						comboBoxDiametroInterior.setDisable(false);
						campoTextoAlto2.setText("");
						campoTextoAlto2.setDisable(false);
						comboBoxAlto2.getSelectionModel().select("");
						comboBoxAlto2.setDisable(false);
						campoAnchoTotal.setText("");
						campoAnchoTotal.setDisable(false);
						comboBoxAnchoTotal.getSelectionModel().select("");
						comboBoxAnchoTotal.setDisable(false);
						campoTextoEspesor.setText("");
						campoTextoEspesor.setDisable(false);
						comboBoxEspesor.getSelectionModel().select("");
						comboBoxEspesor.setDisable(false);
						campoCodigoCatalogo.setText("");
						campoCodigoCatalogo.setDisable(false);
						campoTextoHilos.setText("");
						campoTextoHilos.setDisable(false);
						campoTextoRevision.setText("");
						campoTextoRevision.setDisable(false);
					} else if (newValue == TipoComponente.MATERIA_PRIMA) {
						comboBoxCliente.getSelectionModel().select("");
						comboBoxCliente.setDisable(true);
						comboBoxMaterial.getSelectionModel().select("");
						comboBoxMaterial.setDisable(false);
						comboBoxTipoMateriaPrima.getSelectionModel().select("");
						comboBoxTipoMateriaPrima.setDisable(false);
						comboBoxTipoMiscelaneo.getSelectionModel().select("");
						comboBoxTipoMiscelaneo.setDisable(true);
						comboBoxAcabado.getSelectionModel().select("");
						comboBoxAcabado.setDisable(false);
						comboBoxTratamiento.getSelectionModel().select("");
						comboBoxTratamiento.setDisable(true);
						campoTextoLargo.setText("");
						campoTextoLargo.setDisable(false);
						comboBoxLargo.getSelectionModel().select("");
						comboBoxLargo.setDisable(false);
						campoTextoAncho.setText("");
						campoTextoAncho.setDisable(false);
						comboBoxAncho.getSelectionModel().select("");
						comboBoxAncho.setDisable(false);
						campoTextoAlto.setText("");
						campoTextoAlto.setDisable(false);
						comboBoxAlto.getSelectionModel().select("");
						comboBoxAlto.setDisable(false);
						campoTextoNumeroParte.setDisable(true);
						campoTextoGradoMaterial.setText("");
						campoTextoGradoMaterial.setDisable(false);
						campoTextoEspesor.setText("");
						campoTextoEspesor.setDisable(false);
						comboBoxEspesor.getSelectionModel().select("");
						comboBoxEspesor.setDisable(false);
						campoTextoDiametroExterior.setText("");
						campoTextoDiametroExterior.setDisable(false);
						comboBoxDiametroExterior.getSelectionModel().select("");
						comboBoxDiametroExterior.setDisable(false);
						campoTextoDiametroInterior.setText("");
						campoTextoDiametroInterior.setDisable(false);
						comboBoxDiametroInterior.getSelectionModel().select("");
						comboBoxDiametroInterior.setDisable(false);
						campoTextoAlto2.setText("");
						campoTextoAlto2.setDisable(false);
						comboBoxAlto2.getSelectionModel().select("");
						comboBoxAlto2.setDisable(false);
						campoAnchoTotal.setText("");
						campoAnchoTotal.setDisable(false);
						comboBoxAnchoTotal.getSelectionModel().select("");
						comboBoxAnchoTotal.setDisable(false);
						campoTextoEspesor.setText("");
						campoTextoEspesor.setDisable(false);
						comboBoxEspesor.getSelectionModel().select("");
						comboBoxEspesor.setDisable(false);
						campoCodigoCatalogo.setText("");
						campoCodigoCatalogo.setDisable(false);
						campoTextoHilos.setText("");
						campoTextoHilos.setDisable(false);
						campoTextoRevision.setText("");
						campoTextoRevision.setDisable(false);
					} else if (newValue == TipoComponente.SUB_ENSAMBLE || newValue == TipoComponente.PARTE_PRIMARIA) {
						comboBoxCliente.getSelectionModel().select("");
						comboBoxCliente.setDisable(false);
						comboBoxMaterial.getSelectionModel().select("");
						comboBoxMaterial.setDisable(false);
						comboBoxTipoMateriaPrima.getSelectionModel().select("");
						comboBoxTipoMateriaPrima.setDisable(true);
						comboBoxTipoMiscelaneo.getSelectionModel().select("");
						comboBoxTipoMiscelaneo.setDisable(true);
						comboBoxAcabado.getSelectionModel().select("");
						comboBoxAcabado.setDisable(true);
						comboBoxTratamiento.getSelectionModel().select("");
						comboBoxTratamiento.setDisable(true);
						campoTextoLargo.setText("");
						campoTextoLargo.setDisable(false);
						comboBoxLargo.getSelectionModel().select("");
						comboBoxLargo.setDisable(false);
						campoTextoAncho.setText("");
						campoTextoAncho.setDisable(false);
						comboBoxAncho.getSelectionModel().select("");
						comboBoxAncho.setDisable(false);
						campoTextoAlto.setText("");
						campoTextoAlto.setDisable(false);
						comboBoxAlto.getSelectionModel().select("");
						comboBoxAlto.setDisable(false);
						campoTextoNumeroParte.setDisable(true);
						campoTextoGradoMaterial.setText("");
						campoTextoGradoMaterial.setDisable(false);
						campoTextoEspesor.setText("");
						campoTextoEspesor.setDisable(false);
						comboBoxEspesor.getSelectionModel().select("");
						comboBoxEspesor.setDisable(false);
						campoTextoDiametroExterior.setText("");
						campoTextoDiametroExterior.setDisable(false);
						comboBoxDiametroExterior.getSelectionModel().select("");
						comboBoxDiametroExterior.setDisable(false);
						campoTextoDiametroInterior.setText("");
						campoTextoDiametroInterior.setDisable(false);
						comboBoxDiametroInterior.getSelectionModel().select("");
						comboBoxDiametroInterior.setDisable(false);
						campoTextoAlto2.setText("");
						campoTextoAlto2.setDisable(false);
						comboBoxAlto2.getSelectionModel().select("");
						comboBoxAlto2.setDisable(false);
						campoAnchoTotal.setText("");
						campoAnchoTotal.setDisable(false);
						comboBoxAnchoTotal.getSelectionModel().select("");
						comboBoxAnchoTotal.setDisable(false);
						campoTextoEspesor.setText("");
						campoTextoEspesor.setDisable(false);
						comboBoxEspesor.getSelectionModel().select("");
						comboBoxEspesor.setDisable(false);
						campoCodigoCatalogo.setText("");
						campoCodigoCatalogo.setDisable(false);
						campoTextoHilos.setText("");
						campoTextoHilos.setDisable(false);
						campoTextoRevision.setText("");
						campoTextoRevision.setDisable(false);
					} else if (newValue == TipoComponente.ENSAMBLE) {
						comboBoxCliente.getSelectionModel().select("");
						comboBoxCliente.setDisable(false);
						comboBoxMaterial.getSelectionModel().select("");
						comboBoxMaterial.setDisable(true);
						comboBoxTipoMateriaPrima.getSelectionModel().select("");
						comboBoxTipoMateriaPrima.setDisable(true);
						comboBoxTipoMiscelaneo.getSelectionModel().select("");
						comboBoxTipoMiscelaneo.setDisable(true);
						comboBoxAcabado.getSelectionModel().select("");
						comboBoxAcabado.setDisable(true);
						comboBoxTratamiento.getSelectionModel().select("");
						comboBoxTratamiento.setDisable(true);
						campoTextoLargo.setText("");
						campoTextoLargo.setDisable(true);
						comboBoxLargo.getSelectionModel().select("");
						comboBoxLargo.setDisable(true);
						campoTextoAncho.setText("");
						campoTextoAncho.setDisable(true);
						comboBoxAncho.getSelectionModel().select("");
						comboBoxAncho.setDisable(true);
						campoTextoAlto.setText("");
						campoTextoAlto.setDisable(true);
						comboBoxAlto.getSelectionModel().select("");
						comboBoxAlto.setDisable(true);
						campoTextoNumeroParte.setDisable(false);
						campoTextoGradoMaterial.setText("");
						campoTextoGradoMaterial.setDisable(true);
						campoTextoEspesor.setText("");
						campoTextoEspesor.setDisable(true);
						comboBoxEspesor.getSelectionModel().select("");
						comboBoxEspesor.setDisable(true);
						campoTextoDiametroExterior.setText("");
						campoTextoDiametroExterior.setDisable(true);
						comboBoxDiametroExterior.getSelectionModel().select("");
						comboBoxDiametroExterior.setDisable(true);
						campoTextoDiametroInterior.setText("");
						campoTextoDiametroInterior.setDisable(true);
						comboBoxDiametroInterior.getSelectionModel().select("");
						comboBoxDiametroInterior.setDisable(true);
						campoTextoAlto2.setText("");
						campoTextoAlto2.setDisable(true);
						comboBoxAlto2.getSelectionModel().select("");
						comboBoxAlto2.setDisable(true);
						campoAnchoTotal.setText("");
						campoAnchoTotal.setDisable(true);
						comboBoxAnchoTotal.getSelectionModel().select("");
						comboBoxAnchoTotal.setDisable(true);
						campoTextoEspesor.setText("");
						campoTextoEspesor.setDisable(true);
						comboBoxEspesor.getSelectionModel().select("");
						comboBoxEspesor.setDisable(true);
						campoCodigoCatalogo.setText("");
						campoCodigoCatalogo.setDisable(true);
						campoTextoHilos.setText("");
						campoTextoHilos.setDisable(true);
						campoTextoRevision.setText("");
						campoTextoRevision.setDisable(false);
					}//FIN IF/ELSE
				}
			} //FIN METODO
			
		});//FIN SENTENCIA
		
		ObservableList<String> listaClientes = ClienteDAO.listaNombresClientes(this.mainApp.getConnection());
		this.comboBoxCliente.setItems(listaClientes);
		ObservableList<String> listaMateriales = MaterialDAO.listaTiposMaterial(this.mainApp.getConnection());
		this.comboBoxMaterial.setItems(listaMateriales);
		ObservableList<String> listaTipoMateriaPrima = TipoMateriaPrimaDAO.listaTiposMateriaPrima(this.mainApp.getConnection());
		this.comboBoxTipoMateriaPrima.setItems(listaTipoMateriaPrima);
		ObservableList<String> listaTipoMiscelaneos = TipoMiscelaneoDAO.listaTiposMiscelaneo(this.mainApp.getConnection());
		this.comboBoxTipoMiscelaneo.setItems(listaTipoMiscelaneos);
		ObservableList<String> listaAcabados = AcabadoDAO.listaTiposAcabado(this.mainApp.getConnection());
		this.comboBoxAcabado.setItems(listaAcabados);
		ObservableList<String> listaTratamientos = TratamientoDAO.listaTiposTratamiento(this.mainApp.getConnection());
		this.comboBoxTratamiento.setItems(listaTratamientos);
		
		ObservableList<String> listaMedidas = FXCollections.observableArrayList("mm", "inch");
		this.comboBoxAlto.setItems(listaMedidas);
		this.comboBoxAlto2.setItems(listaMedidas);
		this.comboBoxAncho.setItems(listaMedidas);
		this.comboBoxAnchoTotal.setItems(listaMedidas);
		this.comboBoxDiametroExterior.setItems(listaMedidas);
		this.comboBoxDiametroInterior.setItems(listaMedidas);
		this.comboBoxEspesor.setItems(listaMedidas);
		this.comboBoxLargo.setItems(listaMedidas);
	}//FIN METODO
	
	//INICIALIZA COMPONENTE
	private void inicializarComponentes() {		
		if (this.opcion == CREAR) {
			comboBoxCliente.getSelectionModel().select("");
			comboBoxCliente.setDisable(false);
			comboBoxMaterial.getSelectionModel().select("");
			comboBoxMaterial.setDisable(false);
			comboBoxTipoMateriaPrima.getSelectionModel().select("");
			comboBoxTipoMateriaPrima.setDisable(true);
			comboBoxTipoMiscelaneo.getSelectionModel().select("");
			comboBoxTipoMiscelaneo.setDisable(true);
			comboBoxAcabado.getSelectionModel().select("");
			comboBoxAcabado.setDisable(true);
			comboBoxTratamiento.getSelectionModel().select("");
			comboBoxTratamiento.setDisable(true);
			campoTextoLargo.setText("");
			campoTextoLargo.setDisable(false);
			comboBoxLargo.getSelectionModel().select("");
			comboBoxLargo.setDisable(false);
			campoTextoAncho.setText("");
			campoTextoAncho.setDisable(false);
			comboBoxAncho.getSelectionModel().select("");
			comboBoxAncho.setDisable(false);
			campoTextoAlto.setText("");
			campoTextoAlto.setDisable(false);
			comboBoxAlto.getSelectionModel().select("");
			comboBoxAlto.setDisable(false);
			campoTextoNumeroParte.setDisable(true);
			campoTextoGradoMaterial.setText("");
			campoTextoGradoMaterial.setDisable(false);
			campoTextoEspesor.setText("");
			campoTextoEspesor.setDisable(false);
			comboBoxEspesor.getSelectionModel().select("");
			comboBoxEspesor.setDisable(false);
			campoTextoDiametroExterior.setText("");
			campoTextoDiametroExterior.setDisable(false);
			comboBoxDiametroExterior.getSelectionModel().select("");
			comboBoxDiametroExterior.setDisable(false);
			campoTextoDiametroInterior.setText("");
			campoTextoDiametroInterior.setDisable(false);
			comboBoxDiametroInterior.getSelectionModel().select("");
			comboBoxDiametroInterior.setDisable(false);
			campoTextoAlto2.setText("");
			campoTextoAlto2.setDisable(false);
			comboBoxAlto2.getSelectionModel().select("");
			comboBoxAlto2.setDisable(false);
			campoAnchoTotal.setText("");
			campoAnchoTotal.setDisable(false);
			comboBoxAnchoTotal.getSelectionModel().select("");
			comboBoxAnchoTotal.setDisable(false);
			campoTextoEspesor.setText("");
			campoTextoEspesor.setDisable(false);
			comboBoxEspesor.getSelectionModel().select("");
			comboBoxEspesor.setDisable(false);
			campoCodigoCatalogo.setText("");
			campoCodigoCatalogo.setDisable(false);
			campoTextoConsecutivo.setDisable(true);
			campoTextoHilos.setText("");
			campoTextoHilos.setDisable(false);
			campoTextoRevision.setText("");
			campoTextoRevision.setDisable(false);
			
		} else if (this.opcion == VER) {
			
			this.campoTextoNumeroParte.setText(this.componente.getNumeroParte());
			this.campoTextoNumeroParte.setDisable(true);
			this.campoTextoDescripcion.setText(this.componente.getDescripcion());
			this.campoTextoDescripcion.setDisable(true);
			this.campoTextoConsecutivo.setDisable(true);
			this.comboBoxTipoComponente.setDisable(true);
			
		} else if (this.opcion == EDITAR) {
			
			this.campoTextoNumeroParte.setText(this.componente.getNumeroParte());
			this.campoTextoNumeroParte.setDisable(true);
			this.campoTextoDescripcion.setText(componente.getDescripcion());
			this.campoTextoDescripcion.setDisable(false);
	
		}//FIN METODO
		
		checkHabilitar.selectedProperty().addListener((ov, oldValue, newValue) -> {
			if(checkHabilitar.isSelected())
				this.campoTextoNumeroParte.setDisable(false);
			else
				this.campoTextoNumeroParte.setDisable(true);
		});
			
	}//FIN METODO
	
	//VALIDAR DATOS
	private boolean validarDatos() {
		if (this.comboBoxTipoComponente.getSelectionModel().isEmpty()) {
			Notificacion.dialogoAlerta(AlertType.ERROR, "", "El campo \"Tipo Componente\" no puede estar vacio");
			return false;
		} else if (this.comboBoxMaterial.getSelectionModel().isEmpty() && this.comboBoxTipoComponente.getSelectionModel().getSelectedItem() != TipoComponente.ENSAMBLE) {
			Notificacion.dialogoAlerta(AlertType.ERROR, "", "El campo \"Material\" no puede estar vacio");
			return false;
		} else if (this.comboBoxCliente.getSelectionModel().isEmpty() && (this.comboBoxTipoComponente.getSelectionModel().getSelectedItem() == TipoComponente.PARTE_PRIMARIA || this.comboBoxTipoComponente.getSelectionModel().getSelectedItem() == TipoComponente.ENSAMBLE) ) {
			Notificacion.dialogoAlerta(AlertType.ERROR, "", "El campo \"Cliente\" no puede estar vacio");
			return false;
		} else if (this.comboBoxTipoMateriaPrima.getSelectionModel().isEmpty() && this.comboBoxTipoComponente.getSelectionModel().getSelectedItem() == TipoComponente.MATERIA_PRIMA) {
			Notificacion.dialogoAlerta(AlertType.ERROR, "", "El campo \"Tipo Materia Prima\" no puede estar vacio");
			return false;
		} else if (this.comboBoxTipoMiscelaneo.getSelectionModel().isEmpty() && this.comboBoxTipoComponente.getSelectionModel().getSelectedItem() == TipoComponente.COMPRADO) {
			Notificacion.dialogoAlerta(AlertType.ERROR, "", "El campo \"Tipo Miscelaneo\" no puede estar vacio");
			return false;
		} else if (this.comboBoxAcabado.getSelectionModel().isEmpty() && this.comboBoxTipoComponente.getSelectionModel().getSelectedItem() == TipoComponente.MATERIA_PRIMA) {
			Notificacion.dialogoAlerta(AlertType.ERROR, "", "El campo \"Acabado\" no puede estar vacio");
			return false;
		} else if (this.comboBoxTratamiento.getSelectionModel().isEmpty() && this.comboBoxTipoComponente.getSelectionModel().getSelectedItem() == TipoComponente.COMPRADO) {
			Notificacion.dialogoAlerta(AlertType.ERROR, "", "El campo \"Tratamiento\" no puede estar vacio");
			return false;
		} else if (this.campoTextoDescripcion.getText().isEmpty()) {
			Notificacion.dialogoAlerta(AlertType.ERROR, "", "El campo \"Descripcion\" no puede estar vacio");
			return false;
		} else if (!this.campoTextoAlto.getText().isEmpty() && this.comboBoxAlto.getSelectionModel().getSelectedItem().isEmpty()) {
			Notificacion.dialogoAlerta(AlertType.ERROR, "", "El campo \"Altura\" debe de contener un unidad de medida");
			return false;
		} else if (!this.campoTextoLargo.getText().isEmpty() && this.comboBoxLargo.getSelectionModel().getSelectedItem().isEmpty()) {
			Notificacion.dialogoAlerta(AlertType.ERROR, "", "El campo \"Largo\" debe de contener un unidad de medida");
			return false;
		} else if (!this.campoTextoAncho.getText().isEmpty() && this.comboBoxAncho.getSelectionModel().getSelectedItem().isEmpty()) {
			Notificacion.dialogoAlerta(AlertType.ERROR, "", "El campo \"Ancho\" debe de contener un unidad de medida");
			return false;
		} else if (!this.campoTextoAlto2.getText().isEmpty() && this.comboBoxAlto2.getSelectionModel().getSelectedItem().isEmpty()) {
			Notificacion.dialogoAlerta(AlertType.ERROR, "", "El campo \"Alto2\" debe de contener un unidad de medida");
			return false;
		} else if (!this.campoTextoEspesor.getText().isEmpty() && this.comboBoxEspesor.getSelectionModel().getSelectedItem().isEmpty()) {
			Notificacion.dialogoAlerta(AlertType.ERROR, "", "El campo \"Espesor\" debe de contener un unidad de medida");
			return false;
		} else if (!this.campoAnchoTotal.getText().isEmpty() && this.comboBoxAnchoTotal.getSelectionModel().getSelectedItem().isEmpty()) {
			Notificacion.dialogoAlerta(AlertType.ERROR, "", "El campo \"Ancho total\" debe de contener un unidad de medida");
			return false;
		} else if (!this.campoTextoDiametroExterior.getText().isEmpty() && this.comboBoxDiametroExterior.getSelectionModel().getSelectedItem().isEmpty()) {
			Notificacion.dialogoAlerta(AlertType.ERROR, "", "El campo \"Diametro exterior\" debe de contener un unidad de medida");
			return false;
		} else if (!this.campoTextoDiametroInterior.getText().isEmpty() && this.comboBoxDiametroInterior.getSelectionModel().getSelectedItem().isEmpty()) {
			Notificacion.dialogoAlerta(AlertType.ERROR, "", "El campo \"Diametro interior\"  debe de contener un unidad de medida");
			return false;
		} else if (this.comboBoxStatus.getSelectionModel().isEmpty()) {
			Notificacion.dialogoAlerta(AlertType.ERROR, "", "El combo \"Status\" debe estar seleccionado");
			return false;
		}//FIN IF ELSE
		return true;
	}//FIN METODO
	
	//MANEJADORES COMPONENTES	
	@FXML private void manejadorBotonAceptar() {
		if (this.opcion == VER)
			this.mainApp.getEscenarioDialogos().close();
		else {
			if (this.validarDatos()) {
				if (this.opcion == CREAR) {
					if(checkHabilitar.isSelected()) {
						this.componente.setNumeroParte(this.campoTextoNumeroParte.getText());
					}
					
					this.componente.setDescripcion(this.campoTextoDescripcion.getText());
						
					Dimensiones dimensiones = new Dimensiones();
					if (this.campoTextoLargo.getText().isEmpty())
						dimensiones.setLargo(0.0);
					else
						dimensiones.setLargo(Double.parseDouble(this.campoTextoLargo.getText()));
					if (this.campoTextoAncho.getText().isEmpty())
						dimensiones.setAncho(0.0);
					else
						dimensiones.setAncho(Double.parseDouble(this.campoTextoAncho.getText()));
					if (this.campoTextoAlto.getText().isEmpty())
						dimensiones.setAlto(0.0);
					else
						dimensiones.setAlto(Double.parseDouble(this.campoTextoAlto.getText()));
					if (this.campoTextoEspesor.getText().isEmpty())
						dimensiones.setEspesor(0.0);
					else
						dimensiones.setEspesor(Double.parseDouble(this.campoTextoEspesor.getText()));
					if (this.campoTextoDiametroExterior.getText().isEmpty())
						dimensiones.setDiametroExterior(0.0);
					else
						dimensiones.setDiametroExterior(Double.parseDouble(this.campoTextoDiametroExterior.getText()));
					if (this.campoTextoDiametroInterior.getText().isEmpty())
						dimensiones.setDiametroInterior(0.0);
					else
						dimensiones.setDiametroInterior(Double.parseDouble(this.campoTextoDiametroInterior.getText()));
					if (this.campoTextoAlto2.getText().isEmpty())
						dimensiones.setAlto2(0.0);
					else
						dimensiones.setAlto2(Double.parseDouble(this.campoTextoAlto2.getText()));
					if (this.campoAnchoTotal.getText().isEmpty())
						dimensiones.setAnchoTotal(0.0);
					else
						dimensiones.setAnchoTotal(Double.parseDouble(this.campoAnchoTotal.getText()));
					
					if (this.comboBoxAlto.getSelectionModel().isEmpty())
						dimensiones.setUnidadAlto("mm");
					else
						dimensiones.setUnidadAlto(this.comboBoxAlto.getValue());
					if (this.comboBoxAlto2.getSelectionModel().isEmpty())
						dimensiones.setUnidadAlto2("mm");
					else
						dimensiones.setUnidadAlto2(this.comboBoxAlto2.getValue());
					if (this.comboBoxAncho.getSelectionModel().isEmpty())
						dimensiones.setUnidadAncho("mm");
					else
						dimensiones.setUnidadAncho(this.comboBoxAncho.getValue());
					if (this.comboBoxAnchoTotal.getSelectionModel().isEmpty())
						dimensiones.setUnidadAnchoTotal("mm");
					else
						dimensiones.setUnidadAnchoTotal(this.comboBoxAnchoTotal.getValue());
					if (this.comboBoxDiametroExterior.getSelectionModel().isEmpty())
						dimensiones.setUnidadDExt("mm");
					else
						dimensiones.setUnidadDExt(this.comboBoxDiametroExterior.getValue());
					if (this.comboBoxDiametroInterior.getSelectionModel().isEmpty())
						dimensiones.setUnidadDInt("mm");
					else
						dimensiones.setUnidadDInt(this.comboBoxDiametroInterior.getValue());
					if (this.comboBoxEspesor.getSelectionModel().isEmpty())
						dimensiones.setUnidadEspesor("mm");
					else
						dimensiones.setUnidadEspesor(this.comboBoxEspesor.getValue());
					if (this.comboBoxLargo.getSelectionModel().isEmpty())
						dimensiones.setUnidadLargo("mm");
					else
						dimensiones.setUnidadLargo(this.comboBoxLargo.getValue());
					
					if (componente != null) { 
						if (ComponenteDAO.createComponente(this.mainApp.getConnection(), this.componente)) {
							Notificacion.dialogoAlerta(AlertType.INFORMATION, "", "El registro se creo de forma correcta");
							this.mainApp.getEscenarioDialogos().close();
						} else
							Notificacion.dialogoAlerta(AlertType.INFORMATION, "", "No se pudo crear el registro, revisa que la información sea correcta");
					} else {
						Notificacion.dialogoAlerta(AlertType.INFORMATION, "", "Fabor de seleccionar algo o cancelar la operación");
					}
				} else if (this.opcion == EDITAR) {
					
					this.componente.setNumeroParte(this.campoTextoNumeroParte.getText());
					this.componente.setDescripcion(this.campoTextoDescripcion.getText());

					Dimensiones dimensiones = new Dimensiones();
					if (this.campoTextoLargo.getText().isEmpty())
						dimensiones.setLargo(0.0);
					else
						dimensiones.setLargo(Double.parseDouble(this.campoTextoLargo.getText()));
					if (this.campoTextoAncho.getText().isEmpty())
						dimensiones.setAncho(0.0);
					else
						dimensiones.setAncho(Double.parseDouble(this.campoTextoAncho.getText()));
					if (this.campoTextoAlto.getText().isEmpty())
						dimensiones.setAlto(0.0);
					else
						dimensiones.setAlto(Double.parseDouble(this.campoTextoAlto.getText()));
					if (this.campoTextoEspesor.getText().isEmpty())
						dimensiones.setEspesor(0.0);
					else
						dimensiones.setEspesor(Double.parseDouble(this.campoTextoEspesor.getText()));
					if (this.campoTextoDiametroExterior.getText().isEmpty())
						dimensiones.setDiametroExterior(0.0);
					else
						dimensiones.setDiametroExterior(Double.parseDouble(this.campoTextoDiametroExterior.getText()));
					if (this.campoTextoDiametroInterior.getText().isEmpty())
						dimensiones.setDiametroInterior(0.0);
					else
						dimensiones.setDiametroInterior(Double.parseDouble(this.campoTextoDiametroInterior.getText()));
					if (this.campoTextoAlto2.getText().isEmpty())
						dimensiones.setAlto2(0.0);
					else
						dimensiones.setAlto2(Double.parseDouble(this.campoTextoAlto2.getText()));
					if (this.campoAnchoTotal.getText().isEmpty())
						dimensiones.setAnchoTotal(0.0);
					else
						dimensiones.setAnchoTotal(Double.parseDouble(this.campoAnchoTotal.getText()));
					
					if (this.comboBoxAlto.getSelectionModel().isEmpty())
						dimensiones.setUnidadAlto("mm");
					else
						dimensiones.setUnidadAlto(this.comboBoxAlto.getValue());
					if (this.comboBoxAlto2.getSelectionModel().isEmpty())
						dimensiones.setUnidadAlto2("mm");
					else
						dimensiones.setUnidadAlto2(this.comboBoxAlto2.getValue());
					if (this.comboBoxAncho.getSelectionModel().isEmpty())
						dimensiones.setUnidadAncho("mm");
					else
						dimensiones.setUnidadAncho(this.comboBoxAncho.getValue());
					if (this.comboBoxAnchoTotal.getSelectionModel().isEmpty())
						dimensiones.setUnidadAnchoTotal("mm");
					else
						dimensiones.setUnidadAnchoTotal(this.comboBoxAnchoTotal.getValue());
					if (this.comboBoxDiametroExterior.getSelectionModel().isEmpty())
						dimensiones.setUnidadDExt("mm");
					else
						dimensiones.setUnidadDExt(this.comboBoxDiametroExterior.getValue());
					if (this.comboBoxDiametroInterior.getSelectionModel().isEmpty())
						dimensiones.setUnidadDInt("mm");
					else
						dimensiones.setUnidadDInt(this.comboBoxDiametroInterior.getValue());
					if (this.comboBoxEspesor.getSelectionModel().isEmpty())
						dimensiones.setUnidadEspesor("mm");
					else
						dimensiones.setUnidadEspesor(this.comboBoxEspesor.getValue());
					if (this.comboBoxLargo.getSelectionModel().isEmpty())
						dimensiones.setUnidadLargo("mm");
					else
						dimensiones.setUnidadLargo(this.comboBoxLargo.getValue());
					
					
					if (ComponenteDAO.updateComponente(this.mainApp.getConnection(), this.componente)) {
						Notificacion.dialogoAlerta(AlertType.INFORMATION, "", "El registro se actualizo de forma correcta");
						this.mainApp.getEscenarioDialogos().close();
					} else
						Notificacion.dialogoAlerta(AlertType.INFORMATION, "", "No se pudo actualizar el registro, revisa que la información sea correcta");
				}//FIN IF ELSE
			}//FIN METODO
		}
	}//FIN METODO
	
	@FXML private void manejadorBotonCerrar() {
		this.mainApp.getEscenarioDialogos().close();
	}//FIN METODO
	
}//FIN CLASE
