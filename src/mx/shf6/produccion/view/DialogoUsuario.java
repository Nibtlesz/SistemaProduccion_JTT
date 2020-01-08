package mx.shf6.produccion.view;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import mx.shf6.produccion.MainApp;
import mx.shf6.produccion.model.GrupoUsuario;
import mx.shf6.produccion.model.Usuario;
import mx.shf6.produccion.model.dao.EmpleadoDAO;
import mx.shf6.produccion.model.dao.GrupoUsuarioDAO;
import mx.shf6.produccion.model.dao.UsuarioDAO;
import mx.shf6.produccion.utilities.AutoCompleteComboBoxListener;
import mx.shf6.produccion.utilities.Notificacion;
import mx.shf6.produccion.utilities.RestriccionTextField;

public class DialogoUsuario {

	//PROPIEDADES
	private MainApp mainApp;
	private Usuario usuario;
	private ArrayList<GrupoUsuario> listaGrupoUsuario;
	private ObservableList<String> observableListaGrupoUsuario;
	private ArrayList<Usuario> listaUsuarios;
	private ArrayList<String> listaEmpleados;

	//VARIABLES
	private int opcion;

	//CONSTANTES
	static final int CREAR = 1;
	static final int VER = 2;
	static final int EDITAR = 3;

	//COMPONENTES INTERFAZ
	@FXML private TextField campoTextoNombreUsuario;
	@FXML private PasswordField campoContrasena;
	@FXML private TextField campoCorreo;
	@FXML private ComboBox<String> comboGrupoUsuario;
	@FXML private ComboBox<String> comboStatus;
	@FXML private ComboBox<String> comboEmpleados;

	//INICIA COMPONENTES INTERFAZ DE USUARIO
	@FXML private void initialize() {
		RestriccionTextField.limitarNumeroCaracteres(campoTextoNombreUsuario, 32);
		RestriccionTextField.limitarNumeroCaracteres(campoContrasena, 30);
	}//FIN METODO

	//ACCESO A LA CLASE PRINCIPAL
	public void setMainApp(MainApp mainApp, Usuario usuario, int opcion) {
		this.mainApp = mainApp;
		this.usuario = usuario;
		this.opcion = opcion;
		this.observableListaGrupoUsuario = FXCollections.observableArrayList();
		this.listaGrupoUsuario = GrupoUsuarioDAO.readTodos(this.mainApp.getConnection());
		this.listaEmpleados = EmpleadoDAO.readEmpleadosSinUsuario(this.mainApp.getConnection());
		inicializarCombos();
		mostrarDatosInterfaz();
	}//FIN METODO

	//INICIALIZAR COMBOS
	private void inicializarCombos() {
		listaGrupoUsuario = GrupoUsuarioDAO.readTodos(this.mainApp.getConnection());
		for (GrupoUsuario grupo : listaGrupoUsuario) {
			this.observableListaGrupoUsuario.add(grupo.getNombre());
		}
		this.comboGrupoUsuario.setItems(this.observableListaGrupoUsuario);
		new AutoCompleteComboBoxListener(comboGrupoUsuario);

		ObservableList<String> status = FXCollections.observableArrayList("Bloqueado","Activo","Baja");
		this.comboStatus.setItems(status);
		this.comboEmpleados.setItems(FXCollections.observableArrayList(this.listaEmpleados));
	}//FIN METODO

	//VALIDA LA EXISTENCIA DE EL NOMBRE DE USUARIO
	private boolean compararNombreUsuario () {
		listaUsuarios = UsuarioDAO.readTodos(this.mainApp.getConnection());
		String nombre = this.campoTextoNombreUsuario.getText();
		for (Usuario nombres : listaUsuarios) {
			if (nombres.getUsuario().equals(nombre)) {
				return false;
			}//FIN IF
		}//FIN FOR
		return true;
	}//FIN METODO

	//VALIDA LA EXISTENCIA DE CORREO ELECTRONICO
	private boolean campararCorreo () {
		listaUsuarios = UsuarioDAO.readTodos(this.mainApp.getConnection());
		String correoE = this.campoCorreo.getText();
		for (Usuario correo : listaUsuarios) {
			if (correo.getCorreoElectronico().equals(correoE))
				return false;
		}//FIN FOR
		return true;
	}//FIN METODO

	//VALIDA FORMATO DEL CORREO
	private boolean revisarFormatoCorreo() {
		String correoE = this.campoCorreo.getText();
		Pattern pat = Pattern.compile("^[\\w-]+(\\.[\\w-]+)*@[\\w-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
		Matcher mat = pat.matcher(correoE);
		if (mat.matches()) {
			return true;
		} else
			return false;
	}//FIN METODO

	//INNICIALIZAR COMPONENTES
	private void mostrarDatosInterfaz() {
		if (this.opcion == CREAR) {
			this.campoTextoNombreUsuario.setUserData("");
			this.campoTextoNombreUsuario.setDisable(false);
			this.campoContrasena.setUserData("");
			this.campoContrasena.setDisable(false);
			this.campoCorreo.setUserData("");
			this.campoCorreo.setDisable(false);
			this.comboGrupoUsuario.getSelectionModel().select("");
			this.comboGrupoUsuario.setDisable(false);
			this.comboStatus.getSelectionModel().select("");
			this.comboStatus.setDisable(false);
			this.comboEmpleados.setDisable(false);
		} else if (this.opcion == EDITAR) {
			this.campoTextoNombreUsuario.setText(this.usuario.getUsuario());
			this.campoTextoNombreUsuario.setDisable(true);
			this.campoContrasena.setText(this.usuario.getContrasena());
			this.campoContrasena.setDisable(false);
			this.campoCorreo.setText(this.usuario.getCorreoElectronico());
			this.campoCorreo.setDisable(false);
			this.comboGrupoUsuario.setValue(this.usuario.getNombreGrupoUsuario());
			this.comboGrupoUsuario.setDisable(false);
			this.comboStatus.setValue(this.usuario.getDescripcionStatus());
			this.comboStatus.setDisable(false);
			this.comboEmpleados.setValue(this.usuario.getNombreEmpleado());
			this.comboEmpleados.setDisable(false);
		} else if (this.opcion == VER) {
			this.campoTextoNombreUsuario.setText(this.usuario.getUsuario());
			this.campoTextoNombreUsuario.setDisable(true);
			this.campoContrasena.setText(this.usuario.getContrasena());
			this.campoContrasena.setDisable(true);
			this.campoCorreo.setText(this.usuario.getCorreoElectronico());
			this.campoCorreo.setDisable(true);
			this.comboGrupoUsuario.setValue(this.usuario.getNombreGrupoUsuario());
			this.comboGrupoUsuario.setDisable(true);
			this.comboStatus.setValue(this.usuario.getDescripcionStatus());
			this.comboStatus.setDisable(true);
			this.comboEmpleados.setValue(this.usuario.getNombreEmpleado());
			this.comboEmpleados.setDisable(true);
		}//FIN IF ELSE
	}//FIN METODO

	//VALIDACIONES
	private boolean validarDatos() {
		if (this.opcion == CREAR) {
			if (this.campoTextoNombreUsuario.getText().isEmpty()) {
				Notificacion.dialogoAlerta(AlertType.ERROR, "", "El campo \"Nombre de usuario\" no puede estar vacio");
				return false;
			} else if (this.compararNombreUsuario() == false) {
				Notificacion.dialogoAlerta(AlertType.INFORMATION, "", "El nombre de usuario ya está siendo ocupado");
				return false;
			} else if (this.campoContrasena.getText().isEmpty()) {
				Notificacion.dialogoAlerta(AlertType.ERROR, "", "El campo \"Contraseña\" no puede estar vacio");
				return false;
			} else if (this.campoCorreo.getText().isEmpty()) {
				Notificacion.dialogoAlerta(AlertType.ERROR, "", "El campo \"Correo electrónico\" no puede estar vacio");
				return false;
			} else if (this.revisarFormatoCorreo() == false) {
				Notificacion.dialogoAlerta(AlertType.ERROR, "", "El formato del correo electrónico es incorrecto");
				return false;
			} else if (this.campararCorreo() == false) {
				Notificacion.dialogoAlerta(AlertType.ERROR, "", "El nombre del correo electrónico ya está siendo utilizado");
				return false;
			} else if (this.comboGrupoUsuario.getSelectionModel().getSelectedItem().isEmpty()) {
				Notificacion.dialogoAlerta(AlertType.ERROR, "", "El ComboBox \"Grupo de usuario\" no puede estar vacio");
				return false;
			} else if (this.comboStatus.getSelectionModel().getSelectedItem().isEmpty()) {
				Notificacion.dialogoAlerta(AlertType.ERROR, "", "El ComboBox \"Status\" no puede estar vacio");
				return false;
			} else if (this.comboEmpleados.getSelectionModel().getSelectedItem().isEmpty()){
				Notificacion.dialogoAlerta(AlertType.ERROR, "", "El ComboBox \"Empleado\" no puede estar vacio");
				return false;
			}//FIN IF ELSE
		} else if (this.opcion == EDITAR) {
			if (this.campoTextoNombreUsuario.getText().isEmpty()) {
				Notificacion.dialogoAlerta(AlertType.ERROR, "", "El campo \"Nombre de usuario\" no puede estar vacio");
				return false;
			} else if (this.campoContrasena.getText().isEmpty()) {
				Notificacion.dialogoAlerta(AlertType.ERROR, "", "El campo \"Contraseña\" no puede estar vacio");
				return false;
			} else if (this.campoCorreo.getText().isEmpty()) {
				Notificacion.dialogoAlerta(AlertType.ERROR, "", "El campo \"Correo electrónico\" no puede estar vacio");
				return false;
			} else if (this.revisarFormatoCorreo() == false) {
				Notificacion.dialogoAlerta(AlertType.ERROR, "", "El formato del correo electrónico es incorrecto");
				return false;
			} else if (this.comboGrupoUsuario.getSelectionModel().getSelectedItem().isEmpty()) {
				Notificacion.dialogoAlerta(AlertType.ERROR, "", "El ComboBox \"Grupo de usuario\" no puede estar vacio");
				return false;
			} else if (this.comboStatus.getSelectionModel().getSelectedItem().isEmpty()) {
				Notificacion.dialogoAlerta(AlertType.ERROR, "", "El ComboBox \"Status\" no puede estar vacio");
				return false;
			} else if (this.comboEmpleados.getSelectionModel().getSelectedItem().isEmpty()){
				Notificacion.dialogoAlerta(AlertType.ERROR, "", "El ComboBox \"Empleado\" no puede estar vacio");
				return false;
			}//FIN IF ELSE
		}//FIN IF ELSE
		return true;
	}//FIN METODO

	//BOTON ACEPTAR
	@FXML private void manejadorBotonAceptar() {
		if (this.validarDatos()) {
			if (this.opcion == CREAR) {
				this.usuario.setUsuario(this.campoTextoNombreUsuario.getText());
				this.usuario.setContrasena(this.campoContrasena.getText());
				this.usuario.setCorreoElectronico(this.campoCorreo.getText());
				if (comboStatus.getSelectionModel().getSelectedItem() == "Bloqueado")
					this.usuario.setStatus(0);
				if (comboStatus.getSelectionModel().getSelectedItem() == "Activo")
					this.usuario.setStatus(1);
				if (comboStatus.getSelectionModel().getSelectedItem() == "Baja")
					this.usuario.setStatus(2);
				this.usuario.setGrupoUsuarioFk(listaGrupoUsuario.get(comboGrupoUsuario.getSelectionModel().getSelectedIndex()).getSysPk());
				this.usuario.setEmpleadoFK(EmpleadoDAO.readEmpleadoPorNombre(this.mainApp.getConnection(), comboEmpleados.getSelectionModel().getSelectedItem()).getSysPK());

				if (UsuarioDAO.crear(this.mainApp.getConnection(), this.usuario)) {
					manejadorBotonCerrar();
					Notificacion.dialogoAlerta(AlertType.INFORMATION, "", "El registro se creo correctamente");
				} else {
					Notificacion.dialogoAlerta(AlertType.INFORMATION, "", "No se pudo crear el registro");
				}//FIN IF-ELSE
			} else if (this.opcion == EDITAR) {
				this.usuario.setUsuario(this.campoTextoNombreUsuario.getText());
				this.usuario.setContrasena(this.campoContrasena.getText());
				this.usuario.setCorreoElectronico(this.campoCorreo.getText());
				if (comboStatus.getSelectionModel().getSelectedItem() == "Bloqueado")
					this.usuario.setStatus(0);
				if (comboStatus.getSelectionModel().getSelectedItem() == "Activo")
					this.usuario.setStatus(1);
				if (comboStatus.getSelectionModel().getSelectedItem() == "Baja")
					this.usuario.setStatus(2);
				this.usuario.setGrupoUsuarioFk(listaGrupoUsuario.get(comboGrupoUsuario.getSelectionModel().getSelectedIndex()).getSysPk());
				this.usuario.setEmpleadoFK(EmpleadoDAO.readEmpleadoPorNombre(this.mainApp.getConnection(), comboEmpleados.getSelectionModel().getSelectedItem()).getSysPK());

				if (UsuarioDAO.update(this.mainApp.getConnection(), this.usuario)) {
					manejadorBotonCerrar();
					Notificacion.dialogoAlerta(AlertType.INFORMATION, "", "El registro se modifico correctamente");
				} else {
					Notificacion.dialogoAlerta(AlertType.INFORMATION, "", "No se pudo modificar el registro, revisa la información sea correcta");
				}//FIN IF-ELSE
			} else if (this.opcion == VER) {
				manejadorBotonCerrar();
			}//FIN IF ELSE
		}//FIN METODO
	}//FIN METODO

	//BOTON CERRAR
	@FXML private void manejadorBotonCerrar() {
		this.mainApp.getEscenarioDialogos().close();
	}//FIN METODO
}//FIN CLASE
