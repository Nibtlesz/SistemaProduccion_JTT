package mx.shf6.produccion.view;

import java.sql.Connection;
import java.util.ArrayList;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import mx.shf6.produccion.MainApp;
import mx.shf6.produccion.model.GrupoUsuario;
import mx.shf6.produccion.model.Rol;
import mx.shf6.produccion.model.RolGrupoUsuario;
import mx.shf6.produccion.model.dao.RolDAO;
import mx.shf6.produccion.model.dao.RolGrupoUsuarioDAO;
import mx.shf6.produccion.utilities.PTableColumn;
import javafx.util.Callback;

public class DialogoEsquemaSeguridad {
	
	//PROPIEDADES
	private MainApp mainApp;
	private Connection connection;
	private GrupoUsuario grupoUsuario;
	private RolGrupoUsuario rolGrupoUsuario;
	private ArrayList<Rol> listaRol;
	private ArrayList<RolGrupoUsuario> listaRolGrupoUsuario;
	
	//VARIABLES
	
	//CONSTANTES
	
	//COMPONENTES INTERFAZ
	@FXML private TableView<Rol> TablaRol;
	@FXML private PTableColumn<Rol, String> columnaCodigo;
	@FXML private PTableColumn<Rol, String> columnaDescripcion;
	@FXML private PTableColumn<Rol, Boolean> columnaSeleccionador;
	
	//METODOS
	@FXML private void initialize() {
		this.rolGrupoUsuario = new RolGrupoUsuario();
		this.listaRol = new ArrayList<Rol>();
		this.listaRolGrupoUsuario = new ArrayList<RolGrupoUsuario>();
	}//FIN METODO
	
	public void setMainApp(MainApp mainApp, GrupoUsuario grupoUsuario) {
		this.mainApp = mainApp;
		this.connection = this.mainApp.getConnection();
		this.grupoUsuario = grupoUsuario;
		
		iniciarComponentes();
		actualizarTabla();
	}//FIN METODO
	
	private void iniciarComponentes() {
		this.columnaCodigo.setCellValueFactory(cellData -> cellData.getValue().codigoItemProperty());
		this.columnaDescripcion.setCellValueFactory(cellData -> cellData.getValue().descripcionProperty());
		this.columnaSeleccionador.setCellValueFactory(new Callback<CellDataFeatures<Rol, Boolean>, ObservableValue<Boolean>>(){
			@Override
			public ObservableValue<Boolean> call(CellDataFeatures<Rol, Boolean> param){
				Rol rol = param.getValue();
				SimpleBooleanProperty booleanProp = new SimpleBooleanProperty(rol.getSeleccionado());
				booleanProp.addListener(new ChangeListener<Boolean>() {
					@Override
					public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
						rol.setSeleccionado(newValue);
						asignarRolGrupoUsuario(rol);
					}//FIN METODO
				});//FIN SENTENCIA
				return booleanProp;
			}//FIN METODO
		});//FIN SENTENCIA
		
		this.columnaSeleccionador.setCellFactory(new Callback<TableColumn<Rol,Boolean>, TableCell<Rol,Boolean>>() {
			@Override
			public TableCell<Rol, Boolean> call(TableColumn<Rol, Boolean> rol) {
				CheckBoxTableCell<Rol, Boolean> cell = new CheckBoxTableCell<Rol,Boolean>();
				cell.setAlignment(Pos.CENTER);
				return cell;
			}//FIN METODO			
		});//FIN SENTENCIA
	}//FIN METODO
	
	private void actualizarTabla() {
		this.TablaRol.setItems(null);
		this.listaRol.clear();
		this.listaRol = RolDAO.readTodos(this.connection);
		this.recuperarRolesAsignados();
		this.TablaRol.setItems(FXCollections.observableArrayList(this.listaRol));
	}//FIN METODO
	
	private void recuperarRolesAsignados() {
		this.listaRolGrupoUsuario = RolGrupoUsuarioDAO.readPorGrupoUsuario(this.connection, this.grupoUsuario.getSysPk());
		for (Rol rol : this.listaRol) {
			for (RolGrupoUsuario rolgrupo : this.listaRolGrupoUsuario) {
				if (rol.getSysPk() == rolgrupo.getRolFk()) 
					rol.setSeleccionado(true);
			}//FIN FOR
		}//FIN FOR
	}//FIN METODO
	
	private boolean asignarRolGrupoUsuario(Rol rol) {
		this.rolGrupoUsuario.setGrupoUsuarioFk(this.grupoUsuario.getSysPk());
		this.rolGrupoUsuario.setRolFk(rol.getSysPk());
		if (rol.getSeleccionado() == true) {
			if (RolGrupoUsuarioDAO.create(this.connection, this.rolGrupoUsuario)) 
				return true;
			else
				return false;
		}else {
			if (RolGrupoUsuarioDAO.delete(this.connection, this.rolGrupoUsuario)) 
				return true;
			else
				return false;
		}//FIN IF-ELSE
	}//FIN METODO
	
	//MANEJADORES
	@FXML private void manejadorBotonCerrar() {
		this.mainApp.getEscenarioDialogosAlterno().close();
	}//FIN METODO
}//FIN CLASE 
