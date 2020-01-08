package mx.shf6.produccion.view;

import java.io.File;
import java.util.ArrayList;

import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import mx.shf6.produccion.MainApp;
import mx.shf6.produccion.model.Cliente;
import mx.shf6.produccion.model.Componente;
import mx.shf6.produccion.model.Proyecto;
import mx.shf6.produccion.model.dao.ComponenteDAO;
import mx.shf6.produccion.model.dao.ProyectoDAO;
import mx.shf6.produccion.utilities.AutoCompleteComboBoxListener;
import mx.shf6.produccion.utilities.Notificacion;
import mx.shf6.produccion.utilities.RestriccionTextField;

public class DialogoProyectosCliente {

	// PROPIEDADES
	private MainApp mainApp;
	private Proyecto proyecto;
	private Cliente cliente;
	private File renameRuta;
	private ArrayList<Componente> arrayListComponentesEnsambleCliente;
	private ArrayList<Componente> arrayListComponentes;
	private ArrayList<String> arrayListNumeroParteComponentes;
	private ArrayList<String> arrayListNumeroParteComponentesEnsambleCliente;

	// VARIABLES
	private int opcion;

	// CONSTANTES
	public static final int CREAR = 1;
	public static final int VER = 2;
	public static final int EDITAR = 3;

	// COMPONENTES INTERFAZ
	@FXML private TextField campoTextoCodigo;
	@FXML private TextField campoTextoDescripcion;
	@FXML private TextField campoTextoCarpeta;
	@FXML private TextField campoTextoEspecificacionTecnica;
	@FXML private TextField campoCostoDirecto;
	@FXML private TextField campoCostoIndirecto;
	@FXML private TextField campoPrecio;
	@FXML private ComboBox<String> comboBoxComponentes;
	@FXML private CheckBox campoMostrarTodos;

	// INICIA COMPONENTES INTERFAZ USUARIO
	@FXML
	private void initialize() {
		this.proyecto = new Proyecto();
		this.cliente = new Cliente();

		RestriccionTextField.limitarNumeroCaracteres(this.campoTextoCodigo, 16);
		RestriccionTextField.limitarPuntoDecimal(this.campoCostoDirecto);
		RestriccionTextField.limitarPuntoDecimal(this.campoCostoIndirecto);
		RestriccionTextField.limitarPuntoDecimal(this.campoPrecio);
	}// FIN METODO

	// ACCESO CLASE PRINCIPAL
	public void setMainApp(MainApp mainApp, Proyecto proyecto, int opcion, Cliente cliente) {
		this.mainApp = mainApp;
		this.proyecto = proyecto;
		this.opcion = opcion;
		this.cliente = cliente;

		this.renameRuta = new File(MainApp.RAIZ_SERVIDOR + "Clientes\\" + this.cliente.getNombre() + "\\Proyectos\\"
				+ this.proyecto.getCodigo());
		
		this.arrayListComponentesEnsambleCliente = ComponenteDAO.readComponentesEnsambleCliente(this.mainApp.getConnection(), 
				this.cliente.getSysPK());
		this.arrayListNumeroParteComponentesEnsambleCliente = new ArrayList<String>();

		for (Componente componente : this.arrayListComponentesEnsambleCliente)
			this.arrayListNumeroParteComponentesEnsambleCliente.add(componente.getNumeroParte());

		this.comboBoxComponentes
				.setItems(FXCollections.observableArrayList(this.arrayListNumeroParteComponentesEnsambleCliente));

		campoMostrarTodos.selectedProperty().addListener((ov, oldValue, newValue) -> { 
			if (campoMostrarTodos.isSelected()) {
				this.arrayListComponentes = ComponenteDAO.readComponentesEnsambleAndPrimarias(this.mainApp.getConnection(), this.cliente.getSysPK());
				this.arrayListNumeroParteComponentes = new ArrayList<String>();
				
				for (Componente componente : this.arrayListComponentes) 
					this.arrayListNumeroParteComponentes.add(componente.getNumeroParte());
				
				this.comboBoxComponentes.setItems(FXCollections.observableArrayList(this.arrayListNumeroParteComponentes));
			} else {
				this.arrayListComponentesEnsambleCliente = ComponenteDAO.readComponentesEnsambleCliente(this.mainApp.getConnection(), 
						this.cliente.getSysPK());
				this.arrayListNumeroParteComponentesEnsambleCliente = new ArrayList<String>();

				for (Componente componente : this.arrayListComponentesEnsambleCliente)
					this.arrayListNumeroParteComponentesEnsambleCliente.add(componente.getNumeroParte());

				this.comboBoxComponentes
						.setItems(FXCollections.observableArrayList(this.arrayListNumeroParteComponentesEnsambleCliente));
			}//FIN IF ELSE
		});
		new AutoCompleteComboBoxListener(comboBoxComponentes);
		
		comboBoxComponentes.valueProperty().addListener((ChangeListener<String>) (ov, t, t1) -> {
			Componente componente = ComponenteDAO.readComponenteNumeroParte(this.mainApp.getConnection(), comboBoxComponentes.getValue());		
			if (!componente.equals(null)) {
				this.campoTextoCodigo.setText(componente.getNumeroParte());
				this.campoTextoCodigo.setDisable(true);
				this.campoTextoDescripcion.setText(componente.getDescripcion());
				this.campoTextoDescripcion.setDisable(true);
				this.campoCostoDirecto.setText(String.valueOf(componente.getCostoDirecto()));
				this.campoCostoDirecto.setDisable(true);
				this.campoCostoIndirecto.setText(String.valueOf(componente.getCostoIndirecto()));
				this.campoCostoIndirecto.setDisable(true);
				this.campoPrecio.setText(String.valueOf(componente.getCosto() + componente.getCostoIndirecto() + componente.getCostoDirecto()));
				this.campoPrecio.setDisable(true);
			}//FIN IF
		});//FIN METODO LISTENER
		this.inicializarComponentes();
	}// FIN METODO

	// INICIALIZA COMPONENTE
	private void inicializarComponentes() {
		if (this.opcion == CREAR) {

			this.campoTextoCodigo.setText("");
			this.campoTextoCodigo.setDisable(true);
			this.campoTextoDescripcion.setText("");
			this.campoTextoDescripcion.setDisable(true);
			this.campoMostrarTodos.setDisable(false);
			this.campoTextoCarpeta.setText(MainApp.RAIZ_SERVIDOR);
			this.campoTextoCarpeta.setDisable(true);
			this.campoTextoEspecificacionTecnica.setText("");
			this.campoTextoEspecificacionTecnica.setDisable(false);
			this.campoCostoDirecto.setText("");
			this.campoCostoDirecto.setDisable(true);
			this.campoCostoIndirecto.setText("");
			this.campoCostoIndirecto.setDisable(true);
			this.campoPrecio.setText("");
			this.campoPrecio.setDisable(true);

		} else if (this.opcion == VER) {
			this.campoTextoCodigo.setText(this.proyecto.getCodigo());
			this.campoTextoCodigo.setDisable(true);
			this.campoTextoDescripcion.setText(this.proyecto.getDescripcion());
			this.campoTextoDescripcion.setDisable(true);
			this.campoTextoCarpeta.setText(MainApp.RAIZ_SERVIDOR + "Clientes\\" + this.cliente.getNombre()
					+ "\\Proyectos\\" + this.proyecto.getCodigo());
			this.campoTextoCarpeta.setDisable(true);
			this.campoMostrarTodos.setDisable(true);
			this.campoTextoEspecificacionTecnica.setText(this.proyecto.getEspecificacionTecnica());
			this.campoTextoEspecificacionTecnica.setDisable(true);
			this.campoCostoDirecto.setText(String.valueOf(this.proyecto.getCostoDirecto()));
			this.campoCostoDirecto.setDisable(true);
			this.campoCostoIndirecto.setText(String.valueOf(this.proyecto.getCostoIndirecto()));
			this.campoCostoIndirecto.setDisable(true);
			this.campoPrecio.setText(String.valueOf(this.proyecto.getPrecio()));
			this.campoPrecio.setDisable(true);
			this.comboBoxComponentes.getSelectionModel().select(ComponenteDAO
					.readComponente(this.mainApp.getConnection(), this.proyecto.getComponenteFK()).getNumeroParte());
			this.comboBoxComponentes.setDisable(true);

		} else if (this.opcion == EDITAR) {
			this.campoTextoCodigo.setText(this.proyecto.getCodigo());
			this.campoTextoCodigo.setDisable(false);
			this.campoTextoDescripcion.setText(this.proyecto.getDescripcion());
			this.campoTextoDescripcion.setDisable(false);
			this.campoTextoCarpeta.setText(MainApp.RAIZ_SERVIDOR + "Clientes\\" + this.cliente.getNombre()
					+ "\\Proyectos\\" + this.proyecto.getCodigo());
			this.campoTextoCarpeta.setDisable(true);
			this.campoMostrarTodos.setDisable(false);
			this.campoTextoEspecificacionTecnica.setText(this.proyecto.getEspecificacionTecnica());
			this.campoTextoEspecificacionTecnica.setDisable(false);
			this.campoCostoDirecto.setText(String.valueOf(this.proyecto.getCostoDirecto()));
			this.campoCostoDirecto.setDisable(false);
			this.campoCostoIndirecto.setText(String.valueOf(this.proyecto.getCostoIndirecto()));
			this.campoCostoIndirecto.setDisable(false);
			this.campoPrecio.setText(String.valueOf(this.proyecto.getPrecio()));
			this.campoPrecio.setDisable(false);
			this.comboBoxComponentes.getSelectionModel().select(ComponenteDAO
					.readComponente(this.mainApp.getConnection(), this.proyecto.getComponenteFK()).getNumeroParte());
			this.comboBoxComponentes.setDisable(false);

		} // FIN METODO
	}// FIN METODO

	// VALIDAR DATOS
	private boolean validarDatos() {
		if (this.campoTextoCodigo.getText().isEmpty()) {
			Notificacion.dialogoAlerta(AlertType.ERROR, "", "El campo \"Código\" no puede estar vacio");
			return false;
		} else if (this.campoTextoDescripcion.getText().isEmpty()) {
			Notificacion.dialogoAlerta(AlertType.ERROR, "", "El campo \"Descripción\" no puede estar vacio");
			return false;
		} else if (this.campoTextoCarpeta.getText().isEmpty()) {
			Notificacion.dialogoAlerta(AlertType.ERROR, "", "El campo \"Carpeta\" no puede estar vacio");
			return false;
		} else if (this.campoCostoDirecto.getText().isEmpty()) {
			Notificacion.dialogoAlerta(AlertType.ERROR, "", "El campo \"Costo Directo\" no puede estar vacio");
			return false;
		} else if (this.campoCostoIndirecto.getText().isEmpty()) {
			Notificacion.dialogoAlerta(AlertType.ERROR, "", "El campo \"Costo Indirecto\" no puede estar vacio");
			return false;
		} else if (this.campoPrecio.getText().isEmpty()) {
			Notificacion.dialogoAlerta(AlertType.ERROR, "", "El campo \"Precio\" no puede estar vacio");
			return false;
		} else if (this.comboBoxComponentes.getSelectionModel().getSelectedItem().isEmpty()) {
			Notificacion.dialogoAlerta(AlertType.ERROR, "", "El campo \"Componente\" no puedes estar vacio");
			return false;
		} else if (this.comboBoxComponentes.getSelectionModel().getSelectedItem().isEmpty()) {
			Notificacion.dialogoAlerta(AlertType.ERROR, "", "El campo \"Componente\" no puedes estar vacio");
			return false;
		} // FIN IF/ESLE
		return true;
	}// FIN METODO

	// MANEJADORES COMPONENTES
	@FXML
	private void vmanejadorBotonAceptar() {

		if (this.validarDatos()) {
			if (this.opcion == CREAR) {
				this.proyecto.setCodigo(this.campoTextoCodigo.getText());
				this.proyecto.setDescripcion(this.campoTextoDescripcion.getText());
				this.proyecto.setCarpeta(this.campoTextoCarpeta.getText());
				this.proyecto.setEspecificacionTenica(this.campoTextoEspecificacionTecnica.getText());
				this.proyecto.setCostoDirecto(Double.parseDouble(this.campoCostoDirecto.getText()));
				this.proyecto.setCostoIndirecto(Double.valueOf(this.campoCostoIndirecto.getText()));
				this.proyecto.setPrecio(Double.valueOf(this.campoPrecio.getText()));
				this.proyecto.setClienteFK(this.cliente.getSysPK());
				this.proyecto.setComponenteFK(ComponenteDAO
						.readComponenteNumeroParte(this.mainApp.getConnection(), comboBoxComponentes.getValue())
						.getSysPK());
				if (ProyectoDAO.createProyecto(this.mainApp.getConnection(), this.proyecto)) {
					File ruta = new File(MainApp.RAIZ_SERVIDOR + "Clientes\\" + this.cliente.getNombre()
							+ "\\Proyectos\\" + this.proyecto.getCodigo());
					ruta.mkdirs();
					Notificacion.dialogoAlerta(AlertType.INFORMATION, "", "El registro se creo de forma correcta");
					this.mainApp.getEscenarioDialogos().close();
				} else
					Notificacion.dialogoAlerta(AlertType.INFORMATION, "",
							"No se pudo crear el registro, revisa que la información sea correcta");
			} else if (this.opcion == EDITAR) {

				this.proyecto.setCodigo(this.campoTextoCodigo.getText());
				this.proyecto.setDescripcion(this.campoTextoDescripcion.getText());
				this.proyecto.setCarpeta(this.campoTextoCarpeta.getText());
				this.proyecto.setEspecificacionTenica(this.campoTextoEspecificacionTecnica.getText());
				this.proyecto.setCostoDirecto(Double.parseDouble(this.campoCostoDirecto.getText()));
				this.proyecto.setCostoIndirecto(Double.valueOf(this.campoCostoIndirecto.getText()));
				this.proyecto.setPrecio(Double.valueOf(this.campoPrecio.getText()));
				this.proyecto.setComponenteFK(ComponenteDAO
						.readComponenteNumeroParte(this.mainApp.getConnection(), comboBoxComponentes.getValue())
						.getSysPK());

				if (ProyectoDAO.updateProyecto(this.mainApp.getConnection(), this.proyecto)) {
					this.renameRuta.renameTo(new File(MainApp.RAIZ_SERVIDOR + "Clientes\\" + this.cliente.getNombre()
							+ "\\Proyectos\\" + this.campoTextoCodigo.getText()));
					Notificacion.dialogoAlerta(AlertType.INFORMATION, "", "El registro se actualizo de forma correcta");
					this.mainApp.getEscenarioDialogos().close();
				} else
					Notificacion.dialogoAlerta(AlertType.INFORMATION, "",
							"No se pudo actualizar el registro, revisa que la información sea correcta");
			} else if (this.opcion == VER) {
				this.mainApp.getEscenarioDialogos().close();
			} // FIN ELSE IF
		} // FIN IF

	}// FIN METODO

	@FXML
	private void manejadorBotonCerrar() {
		this.mainApp.getEscenarioDialogos().close();
	}// FIN METODO

}// FIN CLASE
