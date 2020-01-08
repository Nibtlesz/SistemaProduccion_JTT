package mx.shf6.produccion.view;

import java.sql.Connection;
import java.sql.Date;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import mx.shf6.produccion.MainApp;
import mx.shf6.produccion.model.Cardex;
import mx.shf6.produccion.model.DetalleCardex;
import mx.shf6.produccion.model.Existencia;
import mx.shf6.produccion.model.dao.AlmacenDAO;
import mx.shf6.produccion.model.dao.CardexDAO;
import mx.shf6.produccion.model.dao.CategoriaDAO;
import mx.shf6.produccion.model.dao.DetalleCardexDAO;
import mx.shf6.produccion.model.dao.ExistenciaDAO;
import mx.shf6.produccion.model.dao.FolioDAO;
import mx.shf6.produccion.utilities.GenerarDocumento;
import mx.shf6.produccion.utilities.Notificacion;
import mx.shf6.produccion.utilities.PTableColumn;
import mx.shf6.produccion.utilities.TransaccionSQL;

public class DialogoMovimientoInventario {

	// PROPIEDADES
	private MainApp mainApp;
	private Connection conexion;
	private ObservableList<String> observableListAlmacen;
	private ObservableList<String> observableListCategoria;
	private ArrayList<DetalleCardex> listaDetalleCardex;
	private DetalleCardex detalleCardex;
	private DecimalFormat decimalFormat;
	private Cardex cardex;
	private Existencia existencia;

	// VARIABLES
	private int tipoMovimiento;

	// CONSTANTES
	public final static int ENTRADA = 0;
	public final static int SALIDA = 1;
	public final static int TRASPASO = 2;

	// COMPONENTES INTERFAZ
	@FXML private TableView<DetalleCardex> tablaMovimientos;
	@FXML private PTableColumn<DetalleCardex, String> columnaNumeroParte;
	@FXML private PTableColumn<DetalleCardex, String> columnaComponente;
	@FXML private PTableColumn<DetalleCardex, Double> columnaExistenciaActual;
	@FXML private PTableColumn<DetalleCardex, Double> columnaCantidad;
	@FXML private PTableColumn<DetalleCardex, Double> columnaExistenciaNueva;
	@FXML private Label etiquetaTitulo;
	@FXML private ComboBox<String> comboBoxAlmacenOrigen;
	@FXML private ComboBox<String> comboBoxAlmacenDestino;
	@FXML private ComboBox<String> comboBoxCategoria;
	@FXML private MenuItem menuItemAgregarComponente;
	@FXML private MenuItem menuItemQuitarComponente;
	@FXML private TextField campoTextoReferencia;
	@FXML private DatePicker selectorFecha;
	@FXML private TextArea areaTextoNotas;

	// METODOS
	@FXML
	private void initialize() {
	}// FIN METODO

	public void setMainApp(MainApp mainApp, Integer tipoMovimiento) {
		this.mainApp = mainApp;
		this.conexion = this.mainApp.getConnection();
		this.tipoMovimiento = tipoMovimiento;
		this.observableListAlmacen = AlmacenDAO.readDescripcion(conexion);
		this.observableListCategoria = CategoriaDAO.readDescripciones(conexion);
		this.listaDetalleCardex = new ArrayList<DetalleCardex>();
		this.decimalFormat = new DecimalFormat("00000000");
		this.existencia = new Existencia();
		this.cardex = new Cardex();
		this.mostrarDatosInterfaz();
		inicializarTabla();
	}// FIN METODO

	public void mostrarDatosInterfaz() {
		if (this.tipoMovimiento == DialogoMovimientoInventario.ENTRADA){
			etiquetaTitulo.setText("Entrada");
			comboBoxAlmacenDestino.setDisable(true);
		}else if (this.tipoMovimiento == DialogoMovimientoInventario.SALIDA){
			etiquetaTitulo.setText("Salida");
			comboBoxAlmacenDestino.setDisable(true);
		}else if (this.tipoMovimiento == DialogoMovimientoInventario.TRASPASO)
			etiquetaTitulo.setText("Traspaso");

		areaTextoNotas.setWrapText(true);

		campoTextoReferencia.setText("MVI" + decimalFormat.format((FolioDAO.readSeriePorFolio(conexion, "MV") + 1)));
		campoTextoReferencia.setDisable(true);

		selectorFecha.setEditable(false);
		selectorFecha.setValue(new Date(System.currentTimeMillis()).toLocalDate());

		comboBoxAlmacenOrigen.setEditable(false);
		comboBoxAlmacenOrigen.setItems(observableListAlmacen);

		comboBoxAlmacenDestino.setEditable(false);
		comboBoxAlmacenDestino.setItems(observableListAlmacen);

		comboBoxCategoria.setEditable(false);
		comboBoxCategoria.setItems(observableListCategoria);

	}// FIN METODO

	private void inicializarTabla() {
		this.columnaNumeroParte.setCellValueFactory(cellData -> cellData.getValue().noPartesComponenteProperty());
		this.columnaComponente.setCellValueFactory(cellData -> cellData.getValue().descripcionComponenteProperty());
		if (this.tipoMovimiento == DialogoMovimientoInventario.ENTRADA)
			this.columnaCantidad.setCellValueFactory(cellData -> cellData.getValue().entradaProperty());
		else
			this.columnaCantidad.setCellValueFactory(cellData -> cellData.getValue().salidaProperty());
		this.columnaExistenciaActual.setCellValueFactory(cellData -> cellData.getValue().existenciaComponenteProperty());
		 this.columnaExistenciaNueva.setCellValueFactory(cellData -> cellData.getValue().nuevaExistenciaProperty());
	}// FIN METODO

	private void actualizarTabla() {
		this.tablaMovimientos.setItems(null);
		this.tablaMovimientos.setItems(FXCollections.observableArrayList(listaDetalleCardex));
		this.tablaMovimientos.refresh();
	}//FIN METODO

	public boolean validacion() {
		if (this.comboBoxAlmacenOrigen.getSelectionModel().getSelectedIndex() == -1) {
			Notificacion.dialogoAlerta(AlertType.ERROR, "", "El campo \"Almacen Origen\" no puede estar vacio");
			return false;
		} else if ((this.comboBoxAlmacenDestino.getSelectionModel().getSelectedIndex() == -1) && (this.tipoMovimiento == 2)) {
			Notificacion.dialogoAlerta(AlertType.ERROR, "", "El campo \"Almacen Destino\" no puede estar vacio");
			return false;
		} else if (this.comboBoxCategoria.getSelectionModel().getSelectedIndex() == -1) {
			Notificacion.dialogoAlerta(AlertType.ERROR, "", "El campo \"Categoría\" no puede estar vacio");
			return false;
		} else if (this.areaTextoNotas.getText().isEmpty()) {
			Notificacion.dialogoAlerta(AlertType.ERROR, "", "El campo \"Notas\" no puede estar vacio");
			return false;
		}
		return true;
	}// FIN METODO

	private boolean generarCardex() {
		this.cardex.setFecha(Date.valueOf(this.selectorFecha.getValue()));
		this.cardex.setReferencia(this.campoTextoReferencia.getText());
		this.cardex.setNota(this.areaTextoNotas.getText());
		this.cardex.setTipo(this.tipoMovimiento);
		this.cardex.setCategoriaFK((CategoriaDAO.readPorDescripcion(conexion, this.comboBoxCategoria.getValue())).getSysPK());

		if(CardexDAO.create(conexion, cardex))
			return true;
		else
			return false;
	}//FIN METODO

	private boolean generarMovimiento(ArrayList<DetalleCardex> listaDetalleCardex) {
		int i = 0;
		int auxiliar = 2;
		int cardexFK = CardexDAO.ultimoSysPK(conexion);

		if (this.tipoMovimiento != DialogoMovimientoInventario.TRASPASO)
			auxiliar = 1;

		for (DetalleCardex detalleCardex : listaDetalleCardex) {
			do {
				if (i == 1 && this.tipoMovimiento == DialogoMovimientoInventario.TRASPASO) {
					detalleCardex.setEntrada(detalleCardex.getSalida());
					detalleCardex.setSalida(0.0);
					detalleCardex.setAlmacenFK(AlmacenDAO.readPorDescripcion(conexion, this.comboBoxAlmacenDestino.getValue()).getSysPK());
				}

				detalleCardex.setCardexFK(cardexFK);
				if (DetalleCardexDAO.create(conexion, detalleCardex)) {
					this.existencia = ExistenciaDAO.readExistencia(this.conexion, detalleCardex.getComponenteFK(), detalleCardex.getAlmacenFK());
					this.existencia.setComponenteFK(detalleCardex.getComponenteFK());
					this.existencia.setAlmacenFK(detalleCardex.getAlmacenFK());
					this.existencia.setExistencia(this.existencia.getExistencia() + detalleCardex.getEntrada() - detalleCardex.getSalida());
					if (this.existencia.getSysPk() > 0) {
						if (ExistenciaDAO.update(conexion, existencia)) {
						} else {
							return false;
						}//FIN IF
					} else {
						if (ExistenciaDAO.create(conexion, existencia)) {
						} else {
							return false;
						}//FIN IF
					} // FIN IF
				} else
					return false;
				i++;
			} while (i < auxiliar);
			i = 0;
		}//FIN FOR
		return true;
	}//FIN METODO

	private void accionarBotonAceptar() {
		if(listaDetalleCardex.size() == 0){
			Notificacion.dialogoAlerta(AlertType.INFORMATION, "", "No se pudo guardar el registro, ¡revisa que la información sea correcta!");
		}else {
			TransaccionSQL.setStatusTransaccion(this.mainApp.getConnection(), TransaccionSQL.AUTOCOMMIT_OFF);
			if (generarCardex()) {
				if (generarMovimiento(listaDetalleCardex)) {
					TransaccionSQL.setStatusTransaccion(this.conexion, TransaccionSQL.COMMIT_TRANSACTION);
					Notificacion.dialogoAlerta(AlertType.INFORMATION,"", "¡El registro se guardó de forma correcta!");
					this.mainApp.getEscenarioDialogos().close();
					if(tipoMovimiento != TRASPASO)
						GenerarDocumento.generaValeMovimientoInventario(conexion, listaDetalleCardex, tipoMovimiento);
				} else {
					Notificacion.dialogoAlerta(AlertType.INFORMATION, "", "No se pudo guardar el registro, ¡revisa que la información sea correcta!");
					TransaccionSQL.setStatusTransaccion(this.conexion, TransaccionSQL.ROLLBACK_TRANSACTION);
				}//FIN IF ELSE
			} else {
				Notificacion.dialogoAlerta(AlertType.INFORMATION, "", "No se pudo guardar el registro, ¡revisa que la información sea correcta!");
				TransaccionSQL.setStatusTransaccion(this.conexion, TransaccionSQL.ROLLBACK_TRANSACTION);
			}//FIN IF ELSE
		}//FIN IF ELSE
	}// FIN METODO

	private void agregarComponente(){
		if (validacion()) {
			boolean detalleCardexExistente = true;
			this.detalleCardex = new DetalleCardex();
			this.detalleCardex = this.mainApp.iniciarDialogoAgregarMovimientoComponente(comboBoxAlmacenOrigen.getValue(), this.tipoMovimiento);

			if (this.listaDetalleCardex.isEmpty() && !this.detalleCardex.getDescripcionComponente().isEmpty())
				this.listaDetalleCardex.add(this.detalleCardex);
			else if (!this.listaDetalleCardex.isEmpty() && !this.detalleCardex.getDescripcionComponente().isEmpty()){
				for (DetalleCardex detalleCardex1 : this.listaDetalleCardex) {
					if ((detalleCardex1.getDescripcionAlmacen().equals(this.detalleCardex.getDescripcionAlmacen())) && (detalleCardex1.getDescripcionComponente().equals(this.detalleCardex.getDescripcionComponente()))) {
						detalleCardex1.setSalida(this.detalleCardex.getSalida() + detalleCardex1.getSalida());
						detalleCardex1.setEntrada(this.detalleCardex.getEntrada() + detalleCardex1.getEntrada());
						detalleCardexExistente = true;
						break;
					}else{
						detalleCardexExistente = false;
					}//FIN ELSE/ IF
				}//FIN FOR
				if(detalleCardexExistente == false)
					this.listaDetalleCardex.add(this.detalleCardex);
			}//FIN IF ELSE
		}//FIN IF
		actualizarTabla();
	}//FIN METODO

	public void quitarComponente(){
		if (this.tablaMovimientos.getSelectionModel().getSelectedItem() != null)
			this.listaDetalleCardex.remove(this.tablaMovimientos.getSelectionModel().getSelectedItem());

		actualizarTabla();
	}//FIN METODO

	// MANEJADOR DE COMPONENTES
	@FXML
	private void manejadorAgregar() {
		agregarComponente();
	}// FIN METODO

	@FXML
	private void manejadorQuitar() {
		quitarComponente();
	}// FIN METODO

	@FXML
	private void manejadorBotonAceptar() {
		this.accionarBotonAceptar();
	}// FIN METODO

	@FXML
	private void manejadorBotonCancelar() {
		this.mainApp.getEscenarioDialogos().close();
	}// FIN METODO

	@FXML
	private void manejadorBotonCerrar() {
		this.mainApp.getEscenarioDialogos().close();
	}// FIN METODO
}//FIN CLASE
